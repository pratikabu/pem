/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.pratikabu.pem.client.dash.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

/**
 * The data source for contact information used in the sample.
 */
public class TransactionDatabase {

	public static final ProvidesKey<TransactionAndEntry> KEY_PROVIDER = new ProvidesKey<TransactionAndEntry>() {
		@Override
		public Object getKey(TransactionAndEntry item) {
			return item == null ? null : item.getEntry().getTxnEntryId();
		}
	};

	/**
	 * The singleton instance of the database.
	 */
	private static TransactionDatabase instance;

	/**
	 * Get the singleton instance of the contact database.
	 * 
	 * @return the singleton instance
	 */
	public static TransactionDatabase get() {
		if (instance == null) {
			instance = new TransactionDatabase();
		}
		return instance;
	}

	/**
	 * The provider that holds the list of contacts in the database.
	 */
	private ListDataProvider<TransactionAndEntry> dataProvider = new ListDataProvider<TransactionAndEntry>();

	/**
	 * Add a new contact.
	 * 
	 * @param contact
	 *            the contact to add.
	 */
	public void addTransactionEntry(TransactionAndEntry contact) {
		List<TransactionAndEntry> contacts = dataProvider.getList();
		// Remove the contact first so we don't add a duplicate.
		contacts.remove(contact);
		contacts.add(contact);
	}

	/**
	 * Add a display to the database. The current range of interest of the
	 * display will be populated with data.
	 * @param display a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<TransactionAndEntry> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Refresh all displays.
	 */
	public void refreshDisplays(Long transactionGroup) {
		if(null == transactionGroup || -1L == transactionGroup) {
			transactionGroup = null;
		}
		
		ServiceHelper.getPemservice().getAllTransactionsForGroupId(transactionGroup, -1, -1, new AsyncCallback<ArrayList<TransactionDTO>>() {
			@Override
			public void onSuccess(ArrayList<TransactionDTO> result) {
				dataProvider.setList(getTransactionAndEntry(result));
				dataProvider.refresh();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Error fetching transactions");
			}
		});
	}

	public static void deleteSelected() {
		Long tId = TransactionReaderPanel.getTransaction().getTransactionId();
		
		if(null == tId) {
			Utility.alert("Cannot delete the selected entry.");
			return;
		}
		
		deleteT(tId, true);
	}

	public static void deleteT(final Long tId, boolean deleteFullTransaction) {
		if(!Window.confirm("Are you sure you want to delete this Transaction" +
				(deleteFullTransaction ? "" : " Entry") + "?\nThis step cannot be undone.")) {
			return;
		}
		
		ServiceHelper.getPemservice().deleteTransaction(tId, deleteFullTransaction,
				new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result) {
					Utility.alert("Successfully Deleted.");
					// TODO get the transaction value to pass
					CentralEventHandler.transactionUpdated(null, CentralEventHandler.ACTION_DELETED);
				} else {
					Utility.alert("Error while deleting Transaction.");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Error while deleting Transaction.");
			}
		});
	}
	
	public TransactionDTO getTransactionDTO(long txnId) {
		List<TransactionAndEntry> data = dataProvider.getList();
		for(TransactionAndEntry tae : data) {
			if(txnId == tae.getTransaction().getTransactionId()) {
				return tae.getTransaction();
			}
		}
		return null;
	}
	
	public static List<TransactionAndEntry> getTransactionAndEntry(
			ArrayList<TransactionDTO> result) {
		ArrayList<TransactionAndEntry> list = new ArrayList<TransactionAndEntry>();
		
		for(TransactionDTO t : result) {
			for(TransactionEntryDTO ted : t.getTransactionEntries()) {
				list.add(new TransactionAndEntry(t, ted));
			}
		}
		
		return list;
	}

}
