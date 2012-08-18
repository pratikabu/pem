package com.pratikabu.pem.model.utils;

import java.util.Date;
import java.util.List;

import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.TransactionTable;

public class TransactionTesting {
	public static void main(String[] args) {
//		readData(2, null);//65537L);
//		createTestData(2);
		
//		AccountType at = new AccountType();
//		at.setAtCode("main");
//		at.setMeaning("Main Balance");
//		SearchHelper.getFacade().saveModel(at);
		
//		at = new AccountType();
//		at.setAtCode("credit");
//		at.setMeaning("Credit Card");
//		SearchHelper.getFacade().saveModel(at);
//		
//		at = new AccountType();
//		at.setAtCode("person");
//		at.setMeaning("Person");
//		SearchHelper.getFacade().saveModel(at);
//		
//		at = new AccountType();
//		at.setAtCode("other");
//		at.setMeaning("Other");
//		SearchHelper.getFacade().saveModel(at);
		
		PEMUser user = SearchHelper.getFacade().readModelWithId(PEMUser.class, 1L, false);
		
//		Account ac = new Account();
//		ac.setAccName("Main Balance");
//		ac.setUser(user);
//		ac.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, "main", false));
//		ac.setCreationDate(new Date());
//		SearchHelper.getFacade().saveModel(ac);
//		
//		ac = new Account();
//		ac.setAccName("Credit Card Testing it is just a testing");
//		ac.setUser(user);
//		ac.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, "credit", false));
//		ac.setCreationDate(new Date());
//		SearchHelper.getFacade().saveModel(ac);
//		
//		ac = new Account();
//		ac.setAccName("Myself");
//		ac.setUser(user);
//		ac.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, "person", false));
//		ac.setCreationDate(new Date());
//		ac.setEmail("pratikabu@gmail.com");
//		ac.setGender('m');
//		SearchHelper.getFacade().saveModel(ac);
//		
//		ac = new Account();
//		ac.setAccName("Triumph Person");
//		ac.setUser(user);
//		ac.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, "person", false));
//		ac.setCreationDate(new Date());
//		ac.setEmail("triumph.person@gmail.com");
//		ac.setGender('m');
//		SearchHelper.getFacade().saveModel(ac);
		
//		Tag tag = new Tag();
//		tag.setTagName("Others");
//		SearchHelper.getFacade().saveModel(tag);
//		
//		tag = new Tag();
//		tag.setTagName("Entertainement");
//		SearchHelper.getFacade().saveModel(tag);
//		
//		tag = new Tag();
//		tag.setTagName("Clothing");
//		SearchHelper.getFacade().saveModel(tag);
//		
//		tag = new Tag();
//		tag.setTagName("Shopping");
//		SearchHelper.getFacade().saveModel(tag);
		
		TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, 327680L, true);
//		tt.getTags().remove(3);
//		tt.getTags().remove(2);
//		SearchHelper.getFacade().saveModel(tt);
		
//		TransactionEntry te = new TransactionEntry();
//		te.setTransactionGroup(tt);
//		te.setAmount(500);
//		te.setOutwardAccount(SearchHelper.getFacade().readModelWithId(Account.class, 262144L, false));
//		te.setInwardAccount(SearchHelper.getFacade().readModelWithId(Account.class, 262146L, false));
//		SearchHelper.getFacade().saveModel(te);
//		
//		te = new TransactionEntry();
//		te.setTransactionGroup(tt);
//		te.setAmount(5300.5);
//		te.setOutwardAccount(SearchHelper.getFacade().readModelWithId(Account.class, 262144L, false));
//		te.setInwardAccount(SearchHelper.getFacade().readModelWithId(Account.class, 262147L, false));
//		SearchHelper.getFacade().saveModel(te);
	}

	private static void readData(long userid, Long tripId) {
		List<TransactionTable> tList = SearchHelper.getFacade().getTransactionsForUser(userid, tripId, -1, -1, true);
		
		if(null != tList) {
			for(TransactionTable tt : tList) {
				System.out.println(tt.getTgName());
				
				System.out.println(tt.getTags());
				System.out.println(tt.getTransactionEntries());
			}
		} else {
			System.out.println("There is no data found.");
		}
	}
	
	static void createTestData(long userId) {
		PEMUser user = SearchHelper.getFacade().readModelWithId(PEMUser.class, userId, false);
		
		TransactionGroup tg = new TransactionGroup();
		tg.setTripName("Testing Transaction Group");
		tg.setUser(user);
		SearchHelper.getFacade().saveModel(tg);
		
		TransactionGroup tgd = new TransactionGroup();
		tgd.setTripName("Default");
		tgd.setUser(user);
		SearchHelper.getFacade().saveModel(tgd);
		
		TransactionTable tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(1);// outward
		tt.setNotes("This is a testing transaction");
		tt.setTgName("Txn Name 1" + userId);
		tt.setTrip(tgd);
		SearchHelper.getFacade().saveModel(tt);
		
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(1);// outward
		tt.setNotes("This is a testing transaction 2");
		tt.setTgName("Txn Name 2" + userId);
		tt.setTrip(tgd);
		SearchHelper.getFacade().saveModel(tt);
		
		// normal trip
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(1);// outward
		tt.setNotes("This is a testing transaction");
		tt.setTgName("Txn Name n1" + userId);
		tt.setTrip(tg);
		SearchHelper.getFacade().saveModel(tt);
		
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(2);// outward
		tt.setNotes("This is a testing transaction");
		tt.setTgName("Txn Name n2" + userId);
		tt.setTrip(tg);
		SearchHelper.getFacade().saveModel(tt);
	}
}
