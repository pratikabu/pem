package com.pratikabu.pem.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratikabu.pem.client.ui.LoginForm;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PersonalExpenseManager implements EntryPoint {

	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(new LoginForm());
	}
}
