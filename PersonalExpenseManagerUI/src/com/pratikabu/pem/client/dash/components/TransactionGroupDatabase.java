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
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.client.dash.ui.TransactionGroupDialog;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * The data source for contact information used in the sample.
 */
public class TransactionGroupDatabase {

	public static final ProvidesKey<TransactionGroupDTO> KEY_PROVIDER = new ProvidesKey<TransactionGroupDTO>() {
		@Override
		public Object getKey(TransactionGroupDTO item) {
			return item == null ? null : item.getId();
		}
	};

	/**
	 * The singleton instance of the database.
	 */
	private static TransactionGroupDatabase instance;

	/**
	 * Get the singleton instance of the contact database.
	 * 
	 * @return the singleton instance
	 */
	public static TransactionGroupDatabase get() {
		if (instance == null) {
			instance = new TransactionGroupDatabase();
		}
		
		return instance;
	}

	/**
	 * The provider that holds the list of contacts in the database.
	 */
	private ListDataProvider<TransactionGroupDTO> dataProvider = new ListDataProvider<TransactionGroupDTO>();

	/**
	 * Construct a new contact database.
	 */
	private TransactionGroupDatabase() {
		CentralEventHandler.addListener(new CentralEventHandler.TransactionGroupUpdateListener() {
			@Override
			public void transactionGroupUpdatedEvent(TransactionGroupDTO dto, int action) {
				List<TransactionGroupDTO> l = dataProvider.getList();
				if(CentralEventHandler.ACTION_CREATED == action) {
					addTransactionGroup(dto);
				} else if(CentralEventHandler.ACTION_EDITED == action) {
					l.remove(dto);
					addTransactionGroup(dto);
				} else if(CentralEventHandler.ACTION_DELETED == action) {
					l.remove(dto);
				}
				
				dataProvider.refresh();
			}
		});
	}

	/**
	 * Add a new contact.
	 * 
	 * @param contact
	 *            the contact to add.
	 */
	public void addTransactionGroup(TransactionGroupDTO contact) {
		List<TransactionGroupDTO> contacts = dataProvider.getList();
		// Remove the contact first so we don't add a duplicate.
		contacts.remove(contact);
		contacts.add(contact);
	}

	/**
	 * Add a display to the database. The current range of interest of the
	 * display will be populated with data.
	 * @param display a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<TransactionGroupDTO> display) {
		dataProvider.addDataDisplay(display);
	}
	
	public List<TransactionGroupDTO> getTransactionGroups() {
		return OneTimeDataManager.getOTD().getTransactionGroups();
	}
	
	public TransactionGroupDTO getDefault() {
		for(TransactionGroupDTO tg : OneTimeDataManager.getOTD().getTransactionGroups()) {
			if("Default".equals(tg.getTgName())) {
				return tg;
			}
		}
		return null;
	}

	public ListDataProvider<TransactionGroupDTO> getDataProvider() {
		return dataProvider;
	}

	public static void openSelectedProperties() {
		Long tgId = PaneManager.gettList().getActualTransactionGroupId();
		
		if(null == tgId || -1 == tgId) {
			Utility.alert("Cannot modify the selected entry.");
			return;
		}
		
		TransactionGroupDTO tg = get().getTG(tgId);
		if("Default".equals(tg.getTgName())) {
			Utility.alert("You cannot rename this Transaction Group.");
			return;
		}
		
		TransactionGroupDialog.show(tg);
	}

	public static void deleteSelected() {
		Long tgId = PaneManager.gettList().getActualTransactionGroupId();
		
		if(null == tgId || -1 == tgId) {
			Utility.alert("Cannot delete the selected entry.");
			return;
		}
		
		if("Default".equals(get().getTG(tgId).getTgName())) {
			Utility.alert("You cannot delete this Transaction Group.");
			return;
		}
		
		deleteTG(tgId);
	}

	public static void deleteTG(final Long tgId) {
		if(!Window.confirm("Are you sure you want to delete this Transaction Group?\n" +
				"All transactions under this will be deleted.\n" +
				"This step cannot be undone.")) {
			return;
		}
		
		ServiceHelper.getPemservice().deleteTransactionGroup(tgId, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Error while deleting Transaction Group.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result) {
					Utility.alert("Successfully Deleted.");
					CentralEventHandler.transactionGroupUpdated(get().getTG(tgId), CentralEventHandler.ACTION_DELETED);
				} else {
					Utility.alert("Error while deleting Transaction Group.");
				}
			}
		});
	}

	public void setTgList(ArrayList<TransactionGroupDTO> transactionGroups) {
		TransactionGroupDTO dto = getTGAll();
		
		transactionGroups.add(0, dto);
		dataProvider.setList(transactionGroups);
	}

	public TransactionGroupDTO getTGAll() {
		TransactionGroupDTO dto = new TransactionGroupDTO();
		dto.setId(-1L);
		dto.setTgName("All Transactions");
		
		return dto;
	}
	
	public TransactionGroupDTO getTG(Long tgId) {
		for(TransactionGroupDTO dto : dataProvider.getList()) {
			if(tgId == dto.getId()) {
				return dto;
			}
		}
		return null;
	}
	
}
