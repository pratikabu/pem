/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.client.dash.ui.TransactionList;
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
				RootPanel.get("txnListContainer").add(new TransactionList());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Unable to fetch One Time Data.");
				caught.printStackTrace();
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
