/**
 * 
 */
package com.pratikabu.pem.client.wc.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.wc.WelcomeEntryPoint;

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
	
	private String validationMessage;
	private HTML forgetPasswordHtml, errorHtml, registerNewAccount;
	
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
				if("SUCCESS".equals(event.getResults())) {
					Utility.navigateRelative("Dashboard.jsp");
				} else if("INVALID".equals(event.getResults())) {
					validationMessage = "Email or password invalid. Try again.";
					showError();
				} else {
					validationMessage = "There is some problem from server side. Please try again.";
					showError();
				}
			}
		});
		
		ft = new FlexTable();
		ft.setStyleName("loginContainerStyle");
		ft.setCellSpacing(10);
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
		keepMeLoggedIn.setValue(false);
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
		
		forgetPasswordHtml = new HTML();
		forgetPasswordHtml.setStyleName(Constants.CSS_NORMAL_LABEL);
		forgetPasswordHtml.setHTML(Utility.getSafeHtml("<a href='forgotPassword.jsp'>Forgot you password?</a>"));
		
		registerNewAccount = new HTML();
		registerNewAccount.setStyleName(Constants.CSS_NORMAL_LABEL);
		registerNewAccount.setHTML(Utility.getSafeHtml("Not a member. <a href='signup.jsp'>Join Now</a>."));
		
		errorHtml = new HTML();
	}

	private void placeComponents() {
		FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
		int row = -1;

	    // Add a title to the form
	    ft.setWidget(++row, 0, Utility.getLabel("Members Login", Constants.CSS_FORM_HEADING));
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		ft.setWidget(++row, 0, Utility.getLabel("Email"));
		ft.setWidget(row, 1, email);
		ft.setWidget(++row, 0, Utility.getLabel("Password"));
		ft.setWidget(row, 1, password);
		ft.setWidget(++row, 1, keepMeLoggedIn);
		
		/// Login and register
		DockPanel dp = new DockPanel();
		dp.setWidth("100%");
		dp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		dp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		Label lb = Utility.getLabel("iPaid Form", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(loginButton, DockPanel.WEST);
		dp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dp.add(registerNewAccount, DockPanel.EAST);
		/// ends
		ft.setWidget(++row, 1, dp);
		ft.setWidget(++row, 1, forgetPasswordHtml);
	    
	    this.add(ft);
	}
	
	private void login() {
		validateData();
		
		if (null == validationMessage) {
			this.submit();
		}
		
		showError();
	}
	
	private void showError( ) {
		errorHtml.setVisible(null != validationMessage);
		if(null != validationMessage) {
			errorHtml.setHTML(validationMessage);
			WelcomeEntryPoint.setWidgetInId(errorHtml, "errorLabel");
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

	public void focusOn() {
		email.setFocus(true);
	}
}
