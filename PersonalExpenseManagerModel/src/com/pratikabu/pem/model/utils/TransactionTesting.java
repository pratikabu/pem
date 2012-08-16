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
		TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, 32768L, true);
		System.out.println(tt.getTags());
		System.out.println(tt.getTransactionEntries());
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
		tt.setAmount(5000);
		SearchHelper.getFacade().saveModel(tt);
		
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(1);// outward
		tt.setNotes("This is a testing transaction 2");
		tt.setTgName("Txn Name 2" + userId);
		tt.setTrip(tgd);
		tt.setAmount(7000);
		SearchHelper.getFacade().saveModel(tt);
		
		// normal trip
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(1);// outward
		tt.setNotes("This is a testing transaction");
		tt.setTgName("Txn Name n1" + userId);
		tt.setTrip(tg);
		tt.setAmount(23400);
		SearchHelper.getFacade().saveModel(tt);
		
		tt = new TransactionTable();
		tt.setCreationDate(new Date());
		tt.setEntryType(2);// outward
		tt.setNotes("This is a testing transaction");
		tt.setTgName("Txn Name n2" + userId);
		tt.setTrip(tg);
		tt.setAmount(65000.6);
		SearchHelper.getFacade().saveModel(tt);
	}
}
