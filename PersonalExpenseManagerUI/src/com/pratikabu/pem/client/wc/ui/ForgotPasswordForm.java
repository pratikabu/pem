/**
 * 
 */
package com.pratikabu.pem.client.wc.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.wc.WelcomeEntryPoint;

/**
 * @author pratsoni
 *
 */
public class ForgotPasswordForm extends FormPanel {
	private FlexTable ft;
	
	private TextBox email;
	
	private Button sendEmail;
	
	private String validationMessage;
	private HTML infoHtml, errorHtml;
	
	public ForgotPasswordForm() {
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
					Utility.navigateRelative("login.jsp");
				} else if("INVALID".equals(event.getResults())) {
					validationMessage = "User not registered.";
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
					sendReset();
				}
			}
		});
		
		// We can add style names to widgets
		sendEmail = Utility.getActionButton("Reset");
		sendEmail.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendReset();
			}
		});
		
		infoHtml = new HTML();
		infoHtml.setStyleName(Constants.CSS_NORMAL_LABEL);
		infoHtml.setHTML(Utility.getSafeHtml("Recovery steps will be sent in mail."));
		
		errorHtml = new HTML();
	}

	private void placeComponents() {
		FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
		int row = -1;

	    // Add a title to the form
	    ft.setWidget(++row, 0, Utility.getLabel("Reset Password", Constants.CSS_FORM_HEADING));
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		ft.setWidget(++row, 0, Utility.getLabel("Email"));
		ft.setWidget(row, 1, email);
		
		ft.setWidget(++row, 1, infoHtml);
		
		ft.setWidget(++row, 1, sendEmail);
	    
	    this.add(ft);
	}
	
	private void sendReset() {
		validateData();
		
		if (null == validationMessage) {
//			this.submit();
		}
		validationMessage = "For now this functionality is disabled.";
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
		if(Utility.isEmptyValidation(email)) {
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
