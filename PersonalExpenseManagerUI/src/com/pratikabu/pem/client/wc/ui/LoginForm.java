/**
 * 
 */
package com.pratikabu.pem.client.wc.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;

/**
 * @author pratsoni
 *
 */
public class LoginForm extends FormPanel {
	private FlexTable ft;
	
	private TextBox email;
	private PasswordTextBox password;
	private CheckBox keepMeLoggedIn;
	
	private Button loginButton;
	
	private Label errorLabel;
	private String validationMessage;
	
	public LoginForm() {
		initializeComponents();
		placeComponents();
	}

	private void initializeComponents() {
		this.setAction("loginServlet");
		this.setMethod(METHOD_POST);
		this.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if("INVALID".equals(event.getResults())) {
					validationMessage = "Email or password invalid. Try again.";
					showError();
				} else {
					Utility.navigateRelative(event.getResults());
				}
			}
		});
		
		ft = new FlexTable();
		ft.setCellSpacing(1);
		Utility.updateNameAndId(this, "loginContainer");
		
		final String width = "280px";
		
		email = Utility.getTextBox(null);
		email.setWidth(width);
		Utility.updateNameAndId(email, "email");
		email.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(Constants.KEY_ENTER == event.getNativeKeyCode()) {
					password.setFocus(true);
					password.selectAll();
				}
			}
		});
		
		password = new PasswordTextBox();
		password.setStyleName(Constants.CSS_NORMAL_TEXT);
		password.setWidth(width);
		Utility.updateNameAndId(password, "password");
		password.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(Constants.KEY_ENTER == event.getNativeKeyCode()) {
					login();
				}
			}
		});
		
		keepMeLoggedIn = new CheckBox("Keep me logged in");
		keepMeLoggedIn.setValue(true);
		keepMeLoggedIn.setStyleName(Constants.CSS_NORMAL_LABEL);
		Utility.updateNameAndId(keepMeLoggedIn, "keepMeLoggedIn");
		
		// We can add style names to widgets
		loginButton = Utility.getActionButton("Login");
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
		
		errorLabel = Utility.getErrorLabel("");
	}

	private void placeComponents() {
		FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
		int row = -1;

	    // Add a title to the form
	    ft.setWidget(++row, 0, Utility.getLabel("Login with PEM account", Constants.CSS_FORM_HEADING));
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		ft.setWidget(++row, 0, Utility.getLabel("Email"));
		ft.setWidget(row, 1, email);
		ft.setWidget(++row, 0, Utility.getLabel("Password"));
		ft.setWidget(row, 1, password);
		ft.setWidget(++row, 1, keepMeLoggedIn);
		
		ft.setWidget(++row, 1, loginButton);
		
		// Add the error label
	    ft.setWidget(++row, 0, errorLabel);
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    errorLabel.setVisible(false);
	    
	    this.add(ft);
	}
	
	private void login() {
		validateData();
		
		if (null == validationMessage) {
			this.submit();
		} else {
			showError();
		}
	}
	
	private void showError( ) {
		if(null != validationMessage) {
			errorLabel.setVisible(true);
			errorLabel.setText(validationMessage);
		}
		
		validationMessage = null;// refresh it
	}
	
	private void validateData() {
		if(Utility.isEmptyValidation(email, password)) {
			validationMessage = "Please complete the form.";
		}
		
		if(null == validationMessage && !Utility.isValidEmail(email.getText())) {
			validationMessage = "Please fill the correct email address.";
		}
	}
}
