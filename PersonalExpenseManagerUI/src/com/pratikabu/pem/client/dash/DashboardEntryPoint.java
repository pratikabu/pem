package com.pratikabu.pem.client.dash;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratikabu.pem.client.dash.ui.TransactionList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardEntryPoint implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get("txnListContainer").add(new TransactionList());
		PaneManager.editTransactionDetails(0);
	}
}
