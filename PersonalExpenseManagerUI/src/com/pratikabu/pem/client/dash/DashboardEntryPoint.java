package com.pratikabu.pem.client.dash;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratikabu.pem.client.dash.ui.TransactionGroupList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardEntryPoint implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get("txnListContainer").add(new TransactionGroupList());
		PaneManager.editTransactionDetails(0);
	}
}
