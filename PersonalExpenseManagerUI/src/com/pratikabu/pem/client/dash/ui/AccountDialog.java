/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.MessageDialog;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase.AccountTypeLoadListener;
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.LengthConstraintTextBox;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.AccountTypeDTO;

/**
 * @author pratsoni
 *
 */
public class AccountDialog extends DialogBox {
	private FormPanel form;
	private FlexTable ft;
	
	private static AccountDialog tgd;
	private LengthConstraintTextBox accountName;
	
	private ListBox accountType;
	private HTML content;
	
	private ListBox genderBox;
	private TextBox email;
	
	private Hidden accountId, createWhat, editAccType;
	
	private Button saveButton, cancelButton;
	
	private Label lbGender, lbEmail;
	
	public static void show(final AccountDTO acc) {
		if(null == tgd) {
			tgd = new AccountDialog();
		}
		
		AccountTypeDatabase.loadAccountTypes(new AccountTypeLoadListener() {
			@Override
			public void typesLoaded() {
				if(null == AccountTypeDatabase.getAccountTypes()) {
					// error loading account types
					return;
				}
				
				tgd.loadAccountTypes();
				tgd.render(acc);
				tgd.show();
				tgd.accountName.setFocus(true);
			}
		});
	}

	private AccountDialog() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setAutoHideEnabled(true);
		int[] pos = Utility.getCenterOfWindow(220, 430);
		this.setPopupPosition(pos[1], pos[0]);
		this.setAnimationEnabled(true);
		
		form = new FormPanel();
		form.setAction(Constants.CREATE_SERVLET_ACTION_NAME);
		form.setMethod(FormPanel.METHOD_POST);
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String result = event.getResults();
				if(null == result || "INVALID".equals(result)) {
					Utility.alert("Error while saving. Pleas try again.");
				} else {
					Utility.alert("Saved...");
					hide();
					
					AccountDTO acc = new AccountDTO();
					acc.setAccountId(Long.parseLong(result));
					acc.setAccountName(accountName.getText());
					acc.setAccountType(accountType.getValue(accountType.getSelectedIndex()));
					
					Long pk = Long.parseLong(accountId.getValue());
					CentralEventHandler.accountUpdated(acc, CentralEventHandler.getActionForCreateUpdate(pk));
				}
			}
		});
		
		ft = new FlexTable();
		ft.setCellSpacing(5);
		Utility.updateNameAndId(this, "accountFormContainer");
		
		accountId = new Hidden("accountId");
		
		createWhat = new Hidden(Constants.SERVLET_CREATE_WHAT, "account");
		editAccType = new Hidden("editAccType");
		
		accountName = new LengthConstraintTextBox(Constants.MAX_TRANSACTION_GROUP_NAME_CHARACTERS);
		accountName.setWidth("280px");
		Utility.updateNameAndId(accountName, "accountName");
		
		accountType = Utility.getListBox(false);
		Utility.updateNameAndId(accountType, "accountType");
		accountType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateContent();
			}
		});
		
		content = new HTML();
		Utility.updateNameAndId(content, "accountTypeHelp");
		
		lbGender = Utility.getLabel("Gender");
		
		this.genderBox = Utility.getListBox(false);
		Utility.updateNameAndId(genderBox, "gender");
		Utility.populateGender(genderBox);
		
		lbEmail = Utility.getLabel("Email");
		
		email = Utility.getTextBox(null);
		Utility.updateNameAndId(email, "email");
		email.setWidth("280px");
		
		saveButton = Utility.getActionButton("Save");
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(isValidatedForm()) {
					form.submit();
				}
			}
		});
		
		cancelButton = Utility.getNormalButton("Close");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}

	private void placeObjects() {
		int row = -1;
		
		ft.setWidget(++row, 0, Utility.getLabel("Account Name"));
		ft.setWidget(row, 1, accountName);
		
		ft.setWidget(++row, 0, Utility.getLabel("Account Type"));
		ft.setWidget(row, 1, accountType);
		
		ft.setWidget(++row, 1, content);
		
		ft.setWidget(++row, 0, lbGender);
		ft.setWidget(row, 1, genderBox);
		
		ft.setWidget(++row, 0, lbEmail);
		ft.setWidget(row, 1, email);
		
		ft.setWidget(++row, 0, accountId);
		ft.setWidget(++row, 0, createWhat);
		ft.setWidget(++row, 0, editAccType);
		
		ft.setWidget(++row, 1, Utility.addHorizontally(5, saveButton, cancelButton));
		
		form.add(ft);
		
		this.setWidget(form);
	}

	private void render(AccountDTO acc) {
		if(null == acc) {
			this.accountId.setValue("-1");
			this.setText("New Account");
			this.accountName.setText("");
			this.genderBox.setSelectedIndex(0);
			this.email.setText("");
			
			this.accountType.setEnabled(true);
		} else {
			this.accountId.setValue(acc.getAccountId() + "");
			this.setText("Edit Account");
			this.accountName.setText(acc.getAccountName());
			
			if(AccountTypeDatabase.AT_PERSON.equals(acc.getAccountType())) {
				for(int i = 0; i < genderBox.getItemCount(); i++) {
					if(this.genderBox.getValue(i).equals(acc.getGender() + "")) {
						this.genderBox.setSelectedIndex(i);
						break;
					}
				}
				this.email.setText(acc.getEmail());
			}
			
			for(int i = 0; i < this.accountType.getItemCount(); i++) {
				if(this.accountType.getValue(i).equals(acc.getAccountType())) {
					this.accountType.setSelectedIndex(i);
					updateContent();
					break;
				}
			}
			
			editAccType.setValue(acc.getAccountType());
			this.accountType.setEnabled(false);
		}
	}

	private boolean isValidatedForm() {
		MessageDialog md = MessageDialog.get();
		md.setText("Errors while saving");
		boolean valid = true;
		
		if(accountName.getText().trim().isEmpty()) {
			md.println("- Transaction Name cannot be empty.");
			valid = false;
		}
		
		if(!accountName.isValid()) {
			valid = false;
			md.print("- ");
			md.println(accountName.getErrorMessage());
		}
		
		if("Myself".equals(accountName.getText())) {
			valid = false;
			md.print("- You cannot create a account with this name.");
		}
		
		if(!valid) {
			md.show();
		}
		
		return valid;
	}

	private void loadAccountTypes() {
		if(accountType.getItemCount() == 0) {
			// load account types
			for(AccountTypeDTO dto : AccountTypeDatabase.getAccountTypes()) {
				accountType.addItem(dto.getMeaninig(), dto.getAtCode());
			}
		}
		
		accountType.setSelectedIndex(0);
		updateContent();
	}
	
	private void updateContent() {
		String strContent = null;
		for(AccountTypeDTO dto : AccountTypeDatabase.getAccountTypes()) {
			if(dto.getAtCode().equals(accountType.getValue(accountType.getSelectedIndex()))) {
				strContent = dto.getDescription();
				break;
			}
		}
		content.setHTML(Utility.getSafeHtml("<p class='readerPTag' style='margin: 0px; font-size: 12px;'>" + strContent + "</p>"));
		
		boolean isPersonSelected = AccountTypeDatabase.AT_PERSON.equals(accountType.getValue(accountType.getSelectedIndex()));
		lbGender.setVisible(isPersonSelected);
		genderBox.setVisible(isPersonSelected);
		lbEmail.setVisible(isPersonSelected);
		email.setVisible(isPersonSelected);
	}
}
