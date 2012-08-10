/**
 * 
 */
package com.pratikabu.pem.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.Constants;
import com.pratikabu.pem.client.Utility;
import com.pratikabu.pem.shared.model.PEMUser;

/**
 * @author pratsoni
 *
 */
public class LoginForm extends FlexTable {
	private TextBox firstName, lastName, city;
	private TextBox email, confirmEmail;
	private PasswordTextBox password;
	private ListBox genderBox, bdMonth, bdDay, bdYear;
	
	private Button signUpButton;
	
	private Label errorLabel;
	private String validationMessage;
	
	public LoginForm() {
		initializeComponents();
		placeComponents();
	}

	private void initializeComponents() {
		this.setCellSpacing(1);
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
		populateGender();
		
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
		
		errorLabel = Utility.getErrorLabel("");
	}

	private void placeComponents() {
		FlexCellFormatter cellFormatter = this.getFlexCellFormatter();
		int row = 0;

	    // Add a title to the form
	    this.setWidget(row, 0, Utility.getLabel("Sign Up! Its Free!", Constants.CSS_FORM_HEADING));
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.setWidget(++row, 0, Utility.getLabel("First Name"));
		this.setWidget(row, 1, firstName);
		this.setWidget(++row, 0, Utility.getLabel("Last Name"));
		this.setWidget(row, 1, lastName);
		this.setWidget(++row, 0, Utility.getLabel("Email"));
		this.setWidget(row, 1, email);
		this.setWidget(++row, 0, Utility.getLabel("Confrim Email"));
		this.setWidget(row, 1, confirmEmail);
		this.setWidget(++row, 0, Utility.getLabel("Password"));
		this.setWidget(row, 1, password);
		
		this.setWidget(++row, 0, Utility.getLabel("I am"));
		this.setWidget(row, 1, genderBox);
		this.setWidget(++row, 0, Utility.getLabel("Birthday"));
		this.setWidget(row, 1, Utility.addHorizontally(-1, bdMonth, bdDay, bdYear));
		
		this.setWidget(++row, 0, Utility.getLabel("City"));
		this.setWidget(row, 1, city);
		
		this.setWidget(++row, 1, signUpButton);
		
		// Add the error label
	    this.setWidget(++row, 0, errorLabel);
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    errorLabel.setVisible(false);
	}
	
	private void signUp() {
		validateData();
		
		if (null == validationMessage) {
			PEMUser u = new PEMUser();
			u.setFirstName(firstName.getText());
			u.setLastName(lastName.getText());
			u.setEmail(email.getText());
			u.setGender(genderBox.getValue(genderBox.getSelectedIndex()).charAt(0));
			u.setBdMonth(Integer.parseInt(bdMonth.getValue(bdMonth.getSelectedIndex())));
			u.setBdDate(Integer.parseInt(bdDay.getValue(bdDay.getSelectedIndex())));
			u.setBdYear(Integer.parseInt(bdYear.getValue(bdYear.getSelectedIndex())));
			
			Utility.getPemService().createUser(u, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
				}
				
				@Override
				public void onFailure(Throwable caught) {
					validationMessage = "There is some error from server side. Please try again.";
					showError();
				}
			});
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
		if(Utility.isEmptyValidation(firstName, lastName, email, password)) {
			validationMessage = "Please complete the form.";
		}
		
		if(null == validationMessage && !Utility.isValidEmail(email.getText())) {
			validationMessage = "Please fill the correct email address.";
		}
		
		if(null == validationMessage && !email.getText().equals(confirmEmail.getText())) {
			validationMessage = "Email and Confirm email doesnt't match.";
		}
		
		if(null == validationMessage && password.getText().length() >= 6) {
			validationMessage = "Password should be atleast 6 characters long.";
		}
	}

	private void populateGender() {
		genderBox.addItem("Male", "m");
		genderBox.addItem("Female", "f");
	}
	
	private void populateBirthday() {
		int mnth = 0;
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
}
