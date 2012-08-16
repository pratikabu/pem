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

import java.util.List;

import com.google.gwt.user.client.Random;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.sun.corba.se.pept.transport.ContactInfo;

/**
 * The data source for contact information used in the sample.
 */
public class PaymentDistributionDatabase {

	public static final ProvidesKey<Data> KEY_PROVIDER = new ProvidesKey<Data>() {
		@Override
		public Object getKey(Data item) {
			return item == null ? null : item.getAccount().getAccountId();
		}
	};

	/**
	 * The singleton instance of the database.
	 */
	private static PaymentDistributionDatabase instance;

	/**
	 * Get the singleton instance of the contact database.
	 * 
	 * @return the singleton instance
	 */
	public static PaymentDistributionDatabase get() {
		if (instance == null) {
			instance = new PaymentDistributionDatabase();
		}
		return instance;
	}

	/**
	 * The provider that holds the list of contacts in the database.
	 */
	private ListDataProvider<Data> dataProvider = new ListDataProvider<Data>();

	/**
	 * Construct a new contact database.
	 */
	private PaymentDistributionDatabase() {
		// Generate initial data.
		generateContacts(5);
	}

	/**
	 * Add a new contact.
	 * 
	 * @param contact
	 *            the contact to add.
	 */
	public void addContact(Data contact) {
		List<Data> contacts = dataProvider.getList();
		// Remove the contact first so we don't add a duplicate.
		contacts.remove(contact);
		contacts.add(contact);
	}

	/**
	 * Add a display to the database. The current range of interest of the
	 * display will be populated with data.
	 * 
	 * @param display
	 *            a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<Data> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Generate the specified number of contacts and add them to the data
	 * provider.
	 * 
	 * @param count
	 *            the number of contacts to generate.
	 */
	public void generateContacts(int count) {
		List<Data> tgCellsData = dataProvider.getList();
		for (int i = 0; i < count; i++) {
			tgCellsData.add(createTgCellData(i));
		}
	}

	public ListDataProvider<Data> getDataProvider() {
		return dataProvider;
	}

	/**
	 * Refresh all displays.
	 */
	public void refreshDisplays() {
		dataProvider.refresh();
	}

	/**
	 * Create a new random {@link ContactInfo}.
	 * 
	 * @return the new {@link ContactInfo}.
	 */
	private Data createTgCellData(int id) {
		Data tgData = new Data();
		AccountDTO acc = new AccountDTO();
		acc.setAccountId(id);
		acc.setAccountName("Acc Name " + id);
		tgData.setAccount(acc);
		tgData.setAmount(Random.nextInt(30000) + Random.nextDouble());
		return tgData;
	}
	
	public class Data {
		private boolean selected;
		private AccountDTO account;
		private Double amount;

		public AccountDTO getAccount() {
			return account;
		}

		public void setAccount(AccountDTO account) {
			this.account = account;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public String toString() {
			return "Data [selected=" + selected + ", account=" + account
					+ ", amount=" + amount + "]";
		}
	}

}
