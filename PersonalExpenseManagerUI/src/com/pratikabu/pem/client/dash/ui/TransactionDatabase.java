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

package com.pratikabu.pem.client.dash.ui;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Random;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.pratikabu.pem.shared.model.TgCellData;

/**
 * The data source for contact information used in the sample.
 */
public class TransactionDatabase {

	public static final ProvidesKey<TgCellData> KEY_PROVIDER = new ProvidesKey<TgCellData>() {
		@Override
		public Object getKey(TgCellData item) {
			return item == null ? null : item.getTgId();
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
	private ListDataProvider<TgCellData> dataProvider = new ListDataProvider<TgCellData>();

	/**
	 * Construct a new contact database.
	 */
	private TransactionDatabase() {
		// Generate initial data.
		generateContacts(20);
	}

	/**
	 * Add a new contact.
	 * 
	 * @param contact
	 *            the contact to add.
	 */
	public void addContact(TgCellData contact) {
		List<TgCellData> contacts = dataProvider.getList();
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
	public void addDataDisplay(HasData<TgCellData> display) {
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
		List<TgCellData> tgCellsData = dataProvider.getList();
		for (int i = 0; i < count; i++) {
			tgCellsData.add(createTgCellData(i));
		}
	}

	public ListDataProvider<TgCellData> getDataProvider() {
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
	private TgCellData createTgCellData(int id) {
		TgCellData tgData = new TgCellData();
		tgData.setTgId(id);
		tgData.setEntryType(1 + Random.nextInt(3));
		tgData.setAmount(Random.nextInt(5000000) + Random.nextDouble());
		Date date = new Date();
		date.setDate(Random.nextInt(31));
		date.setMonth(Random.nextInt(12));
		tgData.setDate(date);
		
		tgData.setName("Hello Transaction");
		return tgData;
	}

}
