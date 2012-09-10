/**
 * 
 */
package com.pratikabu.pem.client.wc.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.wc.WelcomeEntryPoint;

/**
 * @author pratsoni
 *
 */
public class UserCreationForm extends FormPanel {
	private FlexTable ft;
	
	private TextBox firstName, lastName, city;
	private TextBox email, confirmEmail;
	private PasswordTextBox password;
	private ListBox genderBox, bdMonth, bdDay, bdYear;
	
	private Button signUpButton;
	
	private String validationMessage;
	
	private HTML errorHtml;
	
	public UserCreationForm() {
		initializeComponents();
		placeComponents();
	}

	private void initializeComponents() {
		this.setAction(Constants.CREATE_SERVLET_ACTION_NAME);
		this.setMethod(METHOD_POST);
		this.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if(event.getResults().startsWith("SUCCESS")) {
					Utility.navigateRelative("login.jsp");
				} else if(event.getResults().startsWith("INVALID")) {
					validationMessage = event.getResults().substring("INVALID".length());
					showError();
				} else {
					validationMessage = "Some problem at server side. Please try again.";
					showError();
				}
			}
		});
		
		ft = new FlexTable();
		ft.setStyleName("loginContainerStyle");
		ft.setCellSpacing(10);
		Utility.updateNameAndId(this, "signUpContainer");
		
		final String width = "280px";
		
		firstName = Utility.getTextBox(null);
		firstName.setWidth(width);
		Utility.updateNameAndId(firstName, "regFirstName", "firstName");
		
		lastName = Utility.getTextBox(null);
		lastName.setWidth(width);
		Utility.updateNameAndId(lastName, "regLastName", "lastName");
		
		email = Utility.getTextBox(null);
		email.setWidth(width);
		Utility.updateNameAndId(email, "regEmail", "email");
		
		confirmEmail = Utility.getTextBox(null);
		confirmEmail.setWidth(width);
		Utility.setAutoCompleteOff(confirmEmail.getElement());
		Utility.updateNameAndId(confirmEmail, "regConfirmEmail", "confirmEmail");
		
		password = new PasswordTextBox();
		password.setStyleName(Constants.CSS_NORMAL_TEXT);
		password.setWidth(width);
		Utility.updateNameAndId(password, "regPassword", "password");
		
		genderBox = Utility.getListBox(false);
		Utility.updateNameAndId(genderBox, "regGender", "gender");
		Utility.populateGender(genderBox);
		
		bdMonth = Utility.getListBox(false);
		Utility.updateNameAndId(bdMonth, "regBDMonth", "bdMonth");
		
		bdDay = Utility.getListBox(false);
		Utility.updateNameAndId(bdDay, "regBDDay", "bdDay");
		
		bdYear = Utility.getListBox(false);
		Utility.updateNameAndId(bdYear, "regBDYear", "bdYear");
		
		populateBirthday();
		
		city = Utility.getTextBox(null);
		city.setWidth(width);
		Utility.updateNameAndId(city, "regCity", "city");

		// We can add style names to widgets
		signUpButton = Utility.getActionButton("Sign Up");
		signUpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				signUp();
			}
		});
		
		errorHtml = new HTML();
	}

	private void placeComponents() {
		FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
		int row = 0;

	    // Add a title to the form
		ft.setWidget(row, 0, Utility.getLabel("Sign Up! Its Free!", Constants.CSS_FORM_HEADING));
		cellFormatter.setColSpan(row, 0, 2);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);

		ft.setWidget(++row, 0, Utility.getLabel("First Name"));
		ft.setWidget(row, 1, firstName);
		ft.setWidget(++row, 0, Utility.getLabel("Last Name"));
		ft.setWidget(row, 1, lastName);
		ft.setWidget(++row, 0, Utility.getLabel("Email"));
		ft.setWidget(row, 1, email);
		ft.setWidget(++row, 0, Utility.getLabel("Confrim Email"));
		ft.setWidget(row, 1, confirmEmail);
		ft.setWidget(++row, 0, Utility.getLabel("Password"));
		ft.setWidget(row, 1, password);
		
		ft.setWidget(++row, 0, Utility.getLabel("I am"));
		
		cellFormatter.setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		ft.setWidget(row, 1, genderBox);
		ft.setWidget(++row, 0, Utility.getLabel("Birthday"));
		ft.setWidget(row, 1, Utility.addHorizontally(-1, bdMonth, bdDay, bdYear));
		
		ft.setWidget(++row, 0, Utility.getLabel("City"));
		ft.setWidget(row, 1, city);
		
		ft.setWidget(++row, 1, signUpButton);
		
	    this.add(ft);
	}
	
	private void signUp() {
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
		if(Utility.isEmptyValidation(firstName, lastName, email, password)) {
			validationMessage = "Please complete the form.";
		}
		
		
		if(null == validationMessage && !Utility.isValidEmail(email.getText())) {
			validationMessage = "Please fill the correct email address.";
		}
		
		if(null == validationMessage && !email.getText().equals(confirmEmail.getText())) {
			validationMessage = "Email and Confirm email doesnt't match.";
		}
		
		if(null == validationMessage && password.getText().length() < 6) {
			validationMessage = "Password should be atleast 6 characters long.";
		}
	}
	
	private void populateBirthday() {
		int mnth = -1;
		bdMonth.addItem("Jaunary", (++mnth) + "");
		bdMonth.addItem("February", (++mnth) + "");
		bdMonth.addItem("March", (++mnth) + "");
		bdMonth.addItem("April", (++mnth) + "");
		bdMonth.addItem("May", (++mnth) + "");
		bdMonth.addItem("June", (++mnth) + "");
		bdMonth.addItem("July", (++mnth) + "");
		bdMonth.addItem("August", (++mnth) + "");
		bdMonth.addItem("September", (++mnth) + "");
		bdMonth.addItem("October", (++mnth) + "");
		bdMonth.addItem("November", (++mnth) + "");
		bdMonth.addItem("December", (++mnth) + "");
		
		for(int i = 1; i <= 31; i++) {
			bdDay.addItem(i + "");
		}
		
		int currentYear = 2012; // TODO current year logic has to reworked
		int startingYear = currentYear - 100;
		for(int i = startingYear; i <= currentYear; i++)
		bdYear.addItem(i + "");
		bdYear.setSelectedIndex(currentYear - startingYear - 18);
	}

	public void focusOn() {
		firstName.setFocus(true);
	}
}
