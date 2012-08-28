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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountDTO;

/**
 * The data source for contact information used in the sample.
 */
public class AccountsDatabase {
	private List<AccountDTO> data;
	
	private String filterdAccountType;

	public static final ProvidesKey<AccountDTO> KEY_PROVIDER = new ProvidesKey<AccountDTO>() {
		@Override
		public Object getKey(AccountDTO item) {
			return item == null ? null : item.getAccountId();
		}
	};

	/**
	 * The singleton instance of the database.
	 */
	private static AccountsDatabase instance;

	/**
	 * Get the singleton instance of the contact database.
	 * 
	 * @return the singleton instance
	 */
	public static AccountsDatabase get() {
		if (instance == null) {
			instance = new AccountsDatabase();
		}
		
		return instance;
	}

	/**
	 * The provider that holds the list of contacts in the database.
	 */
	private ListDataProvider<AccountDTO> dataProvider = new ListDataProvider<AccountDTO>();

	/**
	 * Construct a new contact database.
	 */
	private AccountsDatabase() {
	}

	/**
	 * Add a new contact.
	 * 
	 * @param contact
	 *            the contact to add.
	 */
	public void addAccount(AccountDTO contact) {
		List<AccountDTO> contacts = dataProvider.getList();
		// Remove the contact first so we don't add a duplicate.
		contacts.remove(contact);
		contacts.add(contact);
	}

	/**
	 * Add a display to the database. The current range of interest of the
	 * display will be populated with data.
	 * @param display a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<AccountDTO> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Refresh all displays.
	 */
	public void refreshDisplays(String accountType) {
		this.filterdAccountType = accountType;
		
		loadAccountsData(new AccountsLoadingDoneListener() {
			@Override
			public void accountsLoadingDone() {
				setFilterdAccountType(filterdAccountType);
			}
		});
	}

	public void loadAccountsData(final AccountsLoadingDoneListener listener) {
		if(null != data) {
			if(null != listener) {
				listener.accountsLoadingDone();
			}
			return;
		}
		
		ServiceHelper.getPemservice().getAllAccounts(new AsyncCallback<ArrayList<AccountDTO>>() {
			@Override
			public void onSuccess(ArrayList<AccountDTO> result) {
				data = result;
				
				if(null != listener) {
					listener.accountsLoadingDone();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Error fetching transactions");
			}
		});
	}
	
	public List<AccountDTO> getAccountsOfType(String accountType) {
		if(AccountTypeDatabase.AT_ALL.equals(accountType)) {
			return data;
		}
		
		List<AccountDTO> l = new ArrayList<AccountDTO>();
		
		if(null != accountType) {
			for(AccountDTO a : data) {
				if(accountType.equals(a.getAccountType())) {
					l.add(a);
				}
			}
		}
		
		return l;
	}
	
	public void setFilterdAccountType(String accountType) {
		dataProvider.setList(getAccountsOfType(accountType));
		dataProvider.refresh();
	}

	public String getFilterdAccountType() {
		return filterdAccountType;
	}
	
	public AccountDTO getMyself() {
		for(AccountDTO a : data) {
			if(a.getAccountName().equals("Myself")) {
				return a;
			}
		}
		
		return null;
	}
	
	public static interface AccountsLoadingDoneListener {
		void accountsLoadingDone();
	}

}
