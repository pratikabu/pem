package com.pratikabu.pem.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.client.dash.service.PEMService;
import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.Tag;
import com.pratikabu.pem.model.TransactionEntry;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.TransactionTable;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.AccountTypeDTO;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PEMServiceImpl extends RemoteServiceServlet implements PEMService {

	@Override
	public ArrayList<TransactionDTO> getAllTransactionsForGroupId(Long groupId) {
		ArrayList<TransactionDTO> tdto = null;
		List<TransactionTable> tTables = SearchHelper.getFacade().getTransactionsForUser(getCurrentUser(this.getThreadLocalRequest().getSession()),
				groupId, -1, -1, true);
		
		if(null != tTables) {
			tdto = new ArrayList<TransactionDTO>();
			for(TransactionTable tt : tTables) {
				TransactionDTO t = new TransactionDTO();
				t.setTransactionId(tt.getTxnGrpId());
				t.setDate(tt.getCreationDate());
				t.setEntryType(tt.getEntryType());
				t.setName(tt.getTgName());
				
				double totalAmount = 0;
				for(TransactionEntry te : tt.getTransactionEntries()) {
					totalAmount += te.getAmount();
				}
				t.setAmount(totalAmount);
				
				tdto.add(t);
			}
		}
		
		return tdto;
	}

	@Override
	public IPaidDTO getTransactionDetail(Long transactionId) {
		TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, transactionId, true);
		
		if(null == tt) {
			return null;
		}
		
		
		// check for userId is same or not
		if(getCurrentUser(this.getThreadLocalRequest().getSession()) != tt.getTrip().getUser().getUid()) {
			return null; // request for a transaction of a different user
		}
		
		IPaidDTO d = new IPaidDTO();
		d.setTransactionId(transactionId);
		d.setNotes(tt.getNotes());
		d.setDate(tt.getCreationDate());
		d.setName(tt.getTgName());
		
		ArrayList<String> tags = new ArrayList<String>();
		for(Tag t : tt.getTags()) {
			tags.add(t.getTagName());
		}
		d.setSelectedTags(tags);
		
		if(!tt.getTransactionEntries().isEmpty()) {
			Account acc = tt.getTransactionEntries().get(0).getOutwardAccount();
			d.setPaymentMode(acc.getAccountId());
			d.setPaymentModeString(acc.getAccName());
		}
		
		LinkedHashMap<AccountDTO, Double> amountDistribution = new LinkedHashMap<AccountDTO, Double>();
		for(TransactionEntry entry : tt.getTransactionEntries()) {
			AccountDTO acc = new AccountDTO();
			acc.setAccountId(entry.getInwardAccount().getAccountId());
			acc.setAccountName(entry.getInwardAccount().getAccName());
			
			amountDistribution.put(acc, entry.getAmount());
		}
		d.setAmountDistribution(amountDistribution);
		
		d.setGroupId(tt.getTrip().getTripId());
		d.setGroupName(tt.getTrip().getTripName());
		
		return d;
	}

	@Override
	public OneTimeData fetchOneTimeData() {
		long uid = getCurrentUser(this.getThreadLocalRequest().getSession());
		
		OneTimeData otd = new OneTimeData();
		otd.setCurrecnySymbol("Rs");
		
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
			dto.setId(tg.getTripId());
			dto.setTgName(tg.getTripName());
			
			criteria.put("trip.tripId", tg.getTripId());
			
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
	public Long saveIPaidTransaction(IPaidDTO dto) {
		List<Object> toBeSaved = new ArrayList<Object>();
		List<Object> toBeDeleted = new ArrayList<Object>();
		
		LinkedHashMap<Account, Double> oldAmountDistribution = new LinkedHashMap<Account, Double>();
		
		TransactionTable tt = null;
		if(dto.getTransactionId() > 0) { // old transaction
			tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, dto.getTransactionId(), true);
			
			Account outwardAcc = null;
			double totalAmount = 0d;
			for(TransactionEntry te : tt.getTransactionEntries()) {
				oldAmountDistribution.put(te.getInwardAccount(), te.getAmount());
				toBeDeleted.add(te);
				
				toBeSaved.add(updateCurrentBalance(te.getInwardAccount(), te.getAmount(), "sub"));
				
				totalAmount += te.getAmount();
				outwardAcc = te.getOutwardAccount();
			}
			toBeSaved.add(updateCurrentBalance(outwardAcc, totalAmount, "add"));
			
			// now remove all the te's from the tt collection
			for(Object te : toBeDeleted) {
				tt.getTransactionEntries().remove(te);
			}
		} else { // new transaction
			tt = new TransactionTable();
			tt.setTrip(SearchHelper.getFacade().readModelWithId(TransactionGroup.class, dto.getGroupId(), false));
		}
		
		tt.setTgName(dto.getName());
		tt.setEntryType(TransactionDTO.ET_OUTWARD_TG);
		tt.setCreationDate(dto.getDate());
		tt.setNotes(dto.getNotes());
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for(String tag : dto.getSelectedTags()) {
			Tag t = new Tag();
			t.setTagName(tag);
			tags.add(t);
		}
		tt.setTags(tags);
		
		toBeSaved.add(tt);
		
		Account outwardAccount = new Account();
		outwardAccount.setAccountId(dto.getPaymentMode());
		int index;
		if((index = toBeSaved.indexOf(outwardAccount)) != -1) {
			outwardAccount = (Account)toBeSaved.get(index);
		} else {
			outwardAccount = SearchHelper.getFacade().readModelWithId(Account.class, dto.getPaymentMode(), false);
		}
		
		double totalAmount = 0d;
		// add the transaction entries
		for(Entry<AccountDTO, Double> entry : dto.getAmountDistribution().entrySet()) {
			TransactionEntry te = new TransactionEntry();
			te.setAmount(entry.getValue());
			te.setTransactionGroup(tt);
			te.setOutwardAccount(outwardAccount);
			
			Account inwardAccount = new Account();
			inwardAccount.setAccountId(entry.getKey().getAccountId());
			
			if((index = toBeSaved.indexOf(inwardAccount)) != -1) {
				inwardAccount = (Account)toBeSaved.get(index);
			} else {
				inwardAccount = SearchHelper.getFacade().readModelWithId(Account.class, entry.getKey().getAccountId(), false);
			}
			
			te.setInwardAccount(inwardAccount);
			
			toBeSaved.add(te);
			
			toBeSaved.add(updateCurrentBalance(te.getInwardAccount(), te.getAmount(), "add"));
			
			totalAmount += te.getAmount();
		}
		toBeSaved.add(updateCurrentBalance(outwardAccount, totalAmount, "sub"));
		
		boolean b = SearchHelper.getFacade().saveDeleteModels(toBeSaved, toBeDeleted);
		
		return b ? tt.getTxnGrpId() : -1L;
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
		return userId == null ? 360448L : userId;
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
	
}
