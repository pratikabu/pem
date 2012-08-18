package com.pratikabu.pem.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratikabu.pem.client.dash.service.PEMService;
import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.Tag;
import com.pratikabu.pem.model.TransactionEntry;
import com.pratikabu.pem.model.TransactionTable;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PEMServiceImpl extends RemoteServiceServlet implements PEMService {

	@Override
	public ArrayList<TransactionDTO> getAllTransactionsForGroupId(Long groupId) {
		ArrayList<TransactionDTO> tdto = null;
		List<TransactionTable> tTables = SearchHelper.getFacade().getTransactionsForUser(1L, groupId, -1, -1, true);
		
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
		long currentUserId = 1;
		if(currentUserId != tt.getTrip().getUser().getUid()) {
			return null; // request for a transaction of a different user
		}
		
		IPaidDTO d = new IPaidDTO();
		d.setTransactionId(transactionId);
		d.setNotes(tt.getNotes());
		d.setTransactionDate(tt.getCreationDate());
		d.setTransactionName(tt.getTgName());
		
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
		
		double totalAmount = 0;
		LinkedHashMap<AccountDTO, Double> amountDistribution = new LinkedHashMap<AccountDTO, Double>();
		for(TransactionEntry entry : tt.getTransactionEntries()) {
			AccountDTO acc = new AccountDTO();
			acc.setAccountId(entry.getInwardAccount().getAccountId());
			acc.setAccountName(entry.getInwardAccount().getAccName());
			
			amountDistribution.put(acc, entry.getAmount());
			
			totalAmount += entry.getAmount();
		}
		d.setSavedAmountDistribution(amountDistribution);
		
		d.setAmount(totalAmount);
		
		d.setGroupId(tt.getTrip().getTripId());
		d.setGroupName(tt.getTrip().getTripName());
		
		return d;
	}

	@Override
	public OneTimeData fetchOneTimeData() {
		OneTimeData otd = new OneTimeData();
		otd.setCurrecnySymbol("Rs");
		
		List<Tag> mtags = SearchHelper.getFacade().readAllObjects(Tag.class, false);
		ArrayList<String> tags = new ArrayList<String>();
		for(Tag tag : mtags) {
			tags.add(tag.getTagName());
		}
		otd.setTags(tags);
		
		ArrayList<AccountDTO> accs = new ArrayList<AccountDTO>();
		AccountDTO a = new AccountDTO();
		a.setAccountId(1);
		a.setAccountName("Main Balance");
		accs.add(a);
		
		a = new AccountDTO();
		a.setAccountId(2);
		a.setAccountName("Credit Card");
		accs.add(a);
		
		a = new AccountDTO();
		a.setAccountId(3);
		a.setAccountName("Sodexo");
		accs.add(a);
		
		otd.setUserSpecificPayableAccounts(accs);
		
		return otd;
	}

	@Override
	public ArrayList<AccountDTO> getAllAccounts() {
		ArrayList<AccountDTO> accounts = new ArrayList<AccountDTO>();
		
		for(Account a : SearchHelper.getFacade().getAccountsForUser(1L, 0, 0, false)) {
			AccountDTO ad = new AccountDTO();
			ad.setAccountId(a.getAccountId());
			ad.setAccountName(a.getAccName());
			ad.setAccountType(a.getAccountType().getAtCode());
			
			accounts.add(ad);
		}
		
		return accounts;
	}
	
}
