package com.pratikabu.pem.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.client.dash.service.PEMService;
import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.Tag;
import com.pratikabu.pem.model.TransactionEntry;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.TransactionTable;
import com.pratikabu.pem.model.UserSettings;
import com.pratikabu.pem.model.WebsiteData;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.AccountTypeDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;
import com.pratikabu.pem.shared.model.UserSettingsDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PEMServiceImpl extends RemoteServiceServlet implements PEMService {
	private static final Logger logger = Logger.getLogger(PEMServiceImpl.class);

	@Override
	public ArrayList<TransactionDTO> getAllTransactionsForGroupId(Long groupId, int startPosition, int offset) {
		ArrayList<TransactionDTO> tdto = null;
		List<TransactionTable> tTables = SearchHelper.getFacade().getTransactionsForUser(getCurrentUser(this.getThreadLocalRequest().getSession()),
				groupId, -1, -1, true);
		
		if(null != tTables) {
			tdto = new ArrayList<TransactionDTO>();
			for(TransactionTable tt : tTables) {
				tdto.add(getTransactionDTO(tt));
			}
		}
		
		return tdto;
	}

	@Override
	public TransactionDTO getTransactionDetail(Long transactionId) {
		TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, transactionId, true);
		
		return getTransactionDTO(tt);
	}

	private TransactionDTO getTransactionDTO(TransactionTable tt) {
		if(null == tt) {
			return null;
		}
		
		// check for userId is same or not
		if(getCurrentUser(this.getThreadLocalRequest().getSession()) != tt.getTransactionGroup().getUser().getUid()) {
			return null; // request for a transaction of a different user
		}
		
		TransactionDTO d = new TransactionDTO();
		d.setTransactionId(tt.getTxnId());
		d.setNotes(tt.getNotes());
		d.setDate(tt.getCreationDate());
		d.setName(tt.getTxnName());
		d.setEntryType(tt.getEntryType());
		
		for(Tag t : tt.getTags()) {
			d.getSelectedTags().add(t.getTagName());
		}
		
		for(TransactionEntry te : tt.getTransactionEntries()) {
			d.getTransactionEntries().add(getTransactionEntryDTO(te));
		}
		
		d.setGroupId(tt.getTransactionGroup().getTxnGroupId());
		d.setGroupName(tt.getTransactionGroup().getTgName());
		
		return d;
	}

	private TransactionEntryDTO getTransactionEntryDTO(TransactionEntry te) {
		TransactionEntryDTO d = new TransactionEntryDTO();
		d.setTxnEntryId(te.getTxnEntryId());
		d.setInwardAccount(getAccDTOFromAcc(te.getInwardAccount()));
		d.setOutwardAccount(getAccDTOFromAcc(te.getOutwardAccount()));
		d.setAmount(te.getAmount());
		return d;
	}

	private AccountDTO getAccDTOFromAcc(Account acc) {
		AccountDTO a = new AccountDTO();
		a.setAccountId(acc.getAccountId());
		a.setAccountName(acc.getAccName());
		return a;
	}

	@Override
	public OneTimeData fetchOneTimeData() {
		long uid = getCurrentUser(this.getThreadLocalRequest().getSession());
		
		OneTimeData otd = new OneTimeData();
		otd.setCurrecnySymbol(getCurrencySymbol());
		
		List<Tag> mtags = SearchHelper.getFacade().readAllObjects(Tag.class, false, null);
		ArrayList<String> tags = new ArrayList<String>();
		for(Tag tag : mtags) {
			tags.add(tag.getTagName());
		}
		otd.setTags(tags);
		
		ArrayList<AccountDTO> accs = new ArrayList<AccountDTO>();
		for(Account a : SearchHelper.getFacade().getAccountsForUserOfType(uid, AccountTypeDatabase.AT_MAIN,
				AccountTypeDatabase.AT_CREDIT)) {
			AccountDTO acc = new AccountDTO();
			acc.setAccountId(a.getAccountId());
			acc.setAccountName(a.getAccName());
			acc.setAccountType(a.getAccountType().getAtCode());
			
			accs.add(acc);
		}
		otd.setUserSpecificPayableAccounts(accs);
		
		ArrayList<TransactionGroupDTO> transactionGroups = new ArrayList<TransactionGroupDTO>();
		
		Map<String, Object> criteria = new LinkedHashMap<String, Object>();
		
		for(TransactionGroup tg : SearchHelper.getFacade().readAllObjects(TransactionGroup.class, false, uid)) {
			TransactionGroupDTO dto = new TransactionGroupDTO();
			dto.setId(tg.getTxnGroupId());
			dto.setTgName(tg.getTgName());
			
			criteria.put("transactionGroup.txnGroupId", tg.getTxnGroupId());
			
			dto.setNoOfRecords(SearchHelper.getFacade().getCount(TransactionTable.class, criteria));
			transactionGroups.add(dto);
		}
		
		otd.setTransactionGroups(transactionGroups);
		
		return otd;
	}

	@Override
	public ArrayList<AccountDTO> getAllAccounts() {
		ArrayList<AccountDTO> accounts = new ArrayList<AccountDTO>();
		
		for(Account a : SearchHelper.getFacade().getAccountsForUser(getCurrentUser(this.getThreadLocalRequest().getSession()), 0, 0, false)) {
			AccountDTO ad = new AccountDTO();
			ad.setAccountId(a.getAccountId());
			ad.setAccountName(a.getAccName());
			ad.setAccountType(a.getAccountType().getAtCode());
			
			accounts.add(ad);
		}
		
		return accounts;
	}

	@Override
	public Long saveTransaction(TransactionDTO dto) {
		List<Object> toBeSaved = new ArrayList<Object>();
		List<Object> toBeDeleted = new ArrayList<Object>();
		
		TransactionTable tt = null;
		if(dto.getTransactionId() > 0) { // old transaction
			tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, dto.getTransactionId(), true);
			
			Map<String, List<Object>> modifiedMap = modifedMapAfterDeletingTransaction(tt);
			toBeDeleted.addAll(modifiedMap.get("toBeDeleted"));
			toBeSaved.addAll(modifiedMap.get("toBeSaved"));
			
			// now remove all the te's from the tt collection
			for(Object te : toBeDeleted) {
				tt.getTransactionEntries().remove(te);
			}
		} else { // new transaction
			tt = new TransactionTable();
			tt.setTransactionGroup(SearchHelper.getFacade().readModelWithId(TransactionGroup.class, dto.getGroupId(), false));
		}
		
		// save general details
		tt.setTxnName(dto.getName());
		tt.setEntryType(dto.getEntryType());
		tt.setCreationDate(dto.getDate());
		tt.setNotes(dto.getNotes());
		
		// save tags
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for(String tag : dto.getSelectedTags()) {
			Tag t = new Tag();
			t.setTagName(tag);
			tags.add(t);
		}
		tt.setTags(tags);
		
		// save the TransactionEntries
		for(TransactionEntryDTO ted : dto.getTransactionEntries()) {
			Account outwardAccount = getAccountFromList(toBeSaved, ted.getOutwardAccount().getAccountId());
			Account inwardAccount = getAccountFromList(toBeSaved, ted.getInwardAccount().getAccountId());
			
			TransactionEntry te = new TransactionEntry();
			te.setAmount(ted.getAmount());
			te.setTransaction(tt);
			te.setOutwardAccount(outwardAccount);
			te.setInwardAccount(inwardAccount);
			
			toBeSaved.add(updateCurrentBalance(inwardAccount, ted.getAmount(), "add"));
			toBeSaved.add(updateCurrentBalance(outwardAccount, ted.getAmount(), "sub"));
			
			toBeSaved.add(te);
		}
		
		toBeSaved.add(tt);
		
		boolean b = SearchHelper.getFacade().saveDeleteModels(toBeSaved, toBeDeleted);
		
		return b ? tt.getTxnId() : -1L;
	}

	private Account getAccountFromList(List<Object> toBeSaved, long accId) {
		Account account = new Account();
		account.setAccountId(accId);
		int index;
		if((index = toBeSaved.indexOf(account)) != -1) {
			account = (Account)toBeSaved.get(index);
		} else {
			account = SearchHelper.getFacade().readModelWithId(Account.class, accId, false);
		}
		
		return account;
	}

	private Account updateCurrentBalance(Account a, double amount, String operation) {
		if("add".equals(operation)) {
			a.setCurrentBalance(a.getCurrentBalance() + amount);
		} else {
			a.setCurrentBalance(a.getCurrentBalance() - amount);
		}
		return a;
	}
	
	public static long getCurrentUser(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		return userId == null ? 65536L : userId;
	}

	@Override
	public ArrayList<AccountTypeDTO> getAllAccountTypes() {
		List<AccountType> accountTypes = SearchHelper.getFacade().readAllObjects(AccountType.class, false, null);
		
		ArrayList<AccountTypeDTO> dtos = new ArrayList<AccountTypeDTO>();
		
		for(AccountType at : accountTypes) {
			AccountTypeDTO dto = new AccountTypeDTO();
			dto.setAtCode(at.getAtCode());
			dto.setMeaninig(at.getMeaning());
			dto.setDescription(at.getDescription());
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public boolean deleteTransactionGroup(Long tgId) {
		TransactionGroup tg = SearchHelper.getFacade().readModelWithId(TransactionGroup.class, tgId, true);
		List<Object> toBeDeleted = new ArrayList<Object>();
		List<Object> toBeSaved = new ArrayList<Object>();
		
		for(TransactionTable tt : tg.getTransactions()) {
			toBeDeleted.add(tt);
			
			// TODO not working code.. since it is not required in this release.. so need to worry.. :)
			Map<String, List<Object>> modifiedMap = modifedMapAfterDeletingTransaction(getTransactionEntriesWithoutTT(tt.getTxnId()));
			toBeDeleted.addAll(modifiedMap.get("toBeDeleted"));
			toBeSaved.addAll(modifiedMap.get("toBeSaved"));
		}
		
		for(Object o : toBeDeleted) {
			if(o instanceof TransactionTable) {
				tg.getTransactions().remove(o);
			}
		}
		
		toBeDeleted.add(tg);
		
		return SearchHelper.getFacade().saveDeleteModels(toBeSaved, toBeDeleted);
	}

	private TransactionTable getTransactionEntriesWithoutTT(long txnGrpId) {
		// TODO write the readAllObjects method as we've written the getCount method
		return null;
	}

	private Map<String, List<Object>> modifedMapAfterDeletingTransaction(TransactionTable tt) {
		Map<String, List<Object>> map = new LinkedHashMap<String, List<Object>>();
		List<Object> toBeSaved = new ArrayList<Object>();
		List<Object> toBeDeleted = new ArrayList<Object>();
		
		for(TransactionEntry te : tt.getTransactionEntries()) {
			toBeDeleted.add(te);
			toBeSaved.add(updateCurrentBalance(te.getInwardAccount(), te.getAmount(), "sub"));
			toBeSaved.add(updateCurrentBalance(te.getOutwardAccount(), te.getAmount(), "add"));
		}
		
		map.put("toBeSaved", toBeSaved);
		map.put("toBeDeleted", toBeDeleted);
		return map;
	}

	@Override
	public boolean deleteTransaction(long transactionId, boolean deleteFullTransaction) {
		List<Object> toBeSaved = new ArrayList<Object>();
		List<Object> toBeDeleted = new ArrayList<Object>();
		
		if(deleteFullTransaction) { // delete the whole transaction
			TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, transactionId, true);
			
			if(getCurrentUser(this.getThreadLocalRequest().getSession()) != tt.getTransactionGroup().getUser().getUid()) {
				logger.error("Unauthorized removal of Transaction with id: " + transactionId +
						", User: " + tt.getTransactionGroup().getUser().getUid() +
						", Actual User: " + getCurrentUser(this.getThreadLocalRequest().getSession()));
				return false;
			}
			
			Map<String, List<Object>> modifiedMap = modifedMapAfterDeletingTransaction(tt);
			toBeDeleted.addAll(modifiedMap.get("toBeDeleted"));
			toBeSaved.addAll(modifiedMap.get("toBeSaved"));
			
			// now remove all the te's from the tt collection
			for(Object te : toBeDeleted) {
				tt.getTransactionEntries().remove(te);
			}
			
			toBeDeleted.add(tt);
		} else { // delete the specified transaction entry
			TransactionEntry te = SearchHelper.getFacade().readModelWithId(TransactionEntry.class, transactionId, false);
			
			if(getCurrentUser(this.getThreadLocalRequest().getSession()) != te.getTransaction().getTransactionGroup().getUser().getUid()) {
				logger.error("Unauthorized removal of Transaction with id: " + transactionId +
						", User: " + te.getTransaction().getTransactionGroup().getUser().getUid() +
						", Actual User: " + getCurrentUser(this.getThreadLocalRequest().getSession()));
				return false;
			}
			
			toBeSaved.add(updateCurrentBalance(te.getInwardAccount(), te.getAmount(), "sub"));
			toBeSaved.add(updateCurrentBalance(te.getOutwardAccount(), te.getAmount(), "add"));
			toBeDeleted.add(te);
		}
		
		return SearchHelper.getFacade().saveDeleteModels(toBeSaved, toBeDeleted);
	}

	@Override
	public String deleteAccount(long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, String> fetchWebsiteData(int type) {
		Map<String, Object> criteria = new LinkedHashMap<String, Object>();
		criteria.put("pk.type", type);
		
		List<WebsiteData> wdList = SearchHelper.getFacade().readAllObjects(WebsiteData.class, criteria, false);
		
		LinkedHashMap<String, String> val = new LinkedHashMap<String, String>();
		for(WebsiteData wd : wdList) {
			val.put(wd.getPk().getCode(), wd.getMeaning());
		}
		
		return val;
	}

	@Override
	public UserSettingsDTO fetchUserSettings() {
		UserSettings setting = SearchHelper.getFacade().readModelWithId(UserSettings.class,
				getCurrentUser(this.getThreadLocalRequest().getSession()), false);
		
		if(null == setting) {
			return null;
		}
		
		UserSettingsDTO dto = new UserSettingsDTO();
		dto.setCurrency(setting.getCurrency());
		dto.setSendMonthly(setting.isSenMonthlyUpdates());
		dto.setSendNews(setting.isSendNewsLetter());
		return dto;
	}

	@Override
	public boolean saveUserSettings(UserSettingsDTO dto) {
		UserSettings us = new UserSettings();
		us.setCurrency(dto.getCurrency());
		us.setSendNewsLetter(dto.isSendNews());
		us.setSenMonthlyUpdates(dto.isSendMonthly());
		us.setUserId(getCurrentUser(this.getThreadLocalRequest().getSession()));
		
		return SearchHelper.getFacade().saveModel(us);
	}

	private String getCurrencySymbol() {
		UserSettings setting = SearchHelper.getFacade().readModelWithId(UserSettings.class,
				getCurrentUser(this.getThreadLocalRequest().getSession()), false);
		
		Map<String, Object> criteria = new LinkedHashMap<String, Object>();
		criteria.put("pk.type", SearchHelper.WSD_CURRENCY);
		criteria.put("pk.code", setting.getCurrency());
		
		String meaning = SearchHelper.getFacade().readAllObjects(WebsiteData.class, criteria, false).get(0).getMeaning();
		return meaning.substring(meaning.indexOf(" - ") + 3);
	}
	
}