/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountTypeDTO;

/**
 * @author pratsoni
 *
 */
public class AccountTypeDatabase {
	public static final String AT_PERSON = "person", AT_CREDIT = "credit", AT_MAIN = "main", AT_OTHER = "other", AT_ALL = "A";
	
	private static ArrayList<AccountTypeDTO> accountTypes;
	
	public static void loadAccountTypes(final AccountTypeLoadListener listener) {
		if(null == listener) {
			return;
		}
		
		if(null != accountTypes) {
			listener.typesLoaded();
			return;
		}
		
		// load if it is not loaded already
		ServiceHelper.getPemservice().getAllAccountTypes(new AsyncCallback<ArrayList<AccountTypeDTO>>() {
			@Override
			public void onSuccess(ArrayList<AccountTypeDTO> result) {
				accountTypes = result;
				listener.typesLoaded();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Error fetching Account Types.");
				listener.typesLoaded();
			}
		});
	}
	
	public static ArrayList<AccountTypeDTO> getAccountTypes() {
		return accountTypes;
	}

	public static interface AccountTypeLoadListener {
		void typesLoaded();
	}
}
