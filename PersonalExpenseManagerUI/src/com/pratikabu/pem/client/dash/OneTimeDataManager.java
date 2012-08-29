/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.client.dash.components.TransactionGroupDatabase;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.OneTimeData;

/**
 * @author pratsoni
 *
 */
public class OneTimeDataManager {
	private static OneTimeData otd;
	private static int requestNumber;
	
	public static void fetchOTD() {
		
		ServiceHelper.getPemservice().fetchOneTimeData(new AsyncCallback<OneTimeData>() {
			@Override
			public void onSuccess(OneTimeData result) {
				otd = result;
				PaneManager.setInTListPane(PaneManager.gettList());
				PaneManager.gettList().showDataForTransactionGroup(null, null);// show all
				TransactionGroupDatabase.get().setTgList(otd.getTransactionGroups());
				AccountsDatabase.get();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Unable to fetch One Time Data.");
			}
		});
	}
	
	public static OneTimeData getOTD() {
		if(null == otd) {
			otd = new OneTimeData();
		}
		
		return otd;
	}

	public static int getLastRequestNumber() {
		return requestNumber;
	}

	public static int getNewRequestNumber() {
		return ++requestNumber;
	}
}
