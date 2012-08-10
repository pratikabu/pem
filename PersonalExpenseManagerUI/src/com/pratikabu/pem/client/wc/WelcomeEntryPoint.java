package com.pratikabu.pem.client.wc;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratikabu.pem.client.wc.ui.LoginForm;
import com.pratikabu.pem.client.wc.ui.UserCreationForm;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WelcomeEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("signUpFormContainer").add(new UserCreationForm());
		RootPanel.get("loginFormContainer").add(new LoginForm());
	}
}
