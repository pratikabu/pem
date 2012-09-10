package com.pratikabu.pem.client.wc;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.wc.ui.ForgotPasswordForm;
import com.pratikabu.pem.client.wc.ui.LoginForm;
import com.pratikabu.pem.client.wc.ui.UserCreationForm;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WelcomeEntryPoint implements EntryPoint {
	
	private LoginForm loginForm;
	private UserCreationForm signUpForm;
	private ForgotPasswordForm forgotForm;
	
	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		
		String path = Location.getPath();
		
		if(path.contains("signup")) {
			showSignupPage();
		} else if(path.contains("forgot")) {
			showForgotPage();
		} else {
			showLoginPage();
		}
	}
	
	public void showLoginPage() {
		setWidgetInId(getLoginForm(), "loginFormContainer");
		getLoginForm().focusOn();
	}
	
	public void showSignupPage() {
		setWidgetInId(getSignUpForm(), "loginFormContainer");
		getSignUpForm().focusOn();
	}
	
	public void showForgotPage() {
		setWidgetInId(getForgotForm(), "loginFormContainer");
		getForgotForm().focusOn();
	}

	private LoginForm getLoginForm() {
		if(null == loginForm) {
			loginForm = new LoginForm();
		}
		
		return loginForm;
	}

	private UserCreationForm getSignUpForm() {
		if(null == signUpForm) {
			signUpForm = new UserCreationForm();
		}
		
		return signUpForm;
	}
	
	private ForgotPasswordForm getForgotForm() {
		if(null == forgotForm) {
			forgotForm = new ForgotPasswordForm();
		}
		
		return forgotForm;
	}

	public static void setWidgetInId(Widget wid, String id) {
		RootPanel.get(id).clear();
		RootPanel.get(id).add(wid);
	}
}
