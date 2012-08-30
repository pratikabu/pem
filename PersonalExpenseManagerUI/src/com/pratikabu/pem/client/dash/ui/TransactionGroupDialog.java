/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.MessageDialog;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.LengthConstraintTextBox;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionGroupDialog extends DialogBox {
	private static TransactionGroupDialog tgd;
	private FormPanel form;
	private LengthConstraintTextBox transactionGroup;
	private Hidden transactionID, createWhat;
	
	private Button saveButton, cancelButton;
	
	public static void show(TransactionGroupDTO tg) {
		if(null == tgd) {
			tgd = new TransactionGroupDialog();
		}
		
		tgd.render(tg);
		tgd.show();
		tgd.transactionGroup.setFocus(true);
	}

	private TransactionGroupDialog() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setAutoHideEnabled(true);
		int[] pos = Utility.getCenterOfWindow(150, 330);
		this.setPopupPosition(pos[1], pos[0]);
		this.setAnimationEnabled(true);
		
		form = new FormPanel();
		form.setMethod(FormPanel.METHOD_POST);
		form.setAction(Constants.CREATE_SERVLET_ACTION_NAME);
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String result = event.getResults();
				if(null == result || "INVALID".equals(event.getResults())) {
					Utility.alert("Error while saving. Pleas try again.");
				} else {
					Utility.alert("Saved...");
					hide();
					
					TransactionGroupDTO dto = new TransactionGroupDTO();
					dto.setId(Long.parseLong(result));
					dto.setTgName(transactionGroup.getText());
					dto.setId(Long.parseLong(transactionID.getValue()));
					CentralEventHandler.transactionGroupUpdated(dto, CentralEventHandler.getActionForCreateUpdate(dto.getId()));
				}
			}
		});
		
		transactionGroup = new LengthConstraintTextBox(Constants.MAX_TRANSACTION_GROUP_NAME_CHARACTERS);
		transactionGroup.setWidth("280px");
		Utility.updateNameAndId(transactionGroup, "transactionGroupName");
		
		transactionID = new Hidden("txnId");
		
		createWhat = new Hidden(Constants.SERVLET_CREATE_WHAT, "transactionGroup");
		
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
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(5);
		vp.add(transactionGroup);
		vp.add(transactionID);
		vp.add(createWhat);
		vp.add(Utility.addHorizontally(5, saveButton, cancelButton));
		
		form.setWidget(vp);
		
		this.setWidget(form);
	}

	private void render(TransactionGroupDTO tgd) {
		if(null == tgd) {
			this.setText("New Transaction Group");
			transactionGroup.setText("");
			transactionID.setValue("-1");
		} else {
			this.setText("Edit Transaction Group");
			transactionID.setValue(tgd.getId() + "");
			transactionGroup.setText(tgd.getTgName());
			
		}
	}

	private boolean isValidatedForm() {
		MessageDialog md = MessageDialog.get();
		md.setText("Errors while saving");
		boolean valid = true;
		
		if(transactionGroup.getText().trim().isEmpty()) {
			md.println("- Transaction Name cannot be empty.");
			valid = false;
		}
		
		if(!transactionGroup.isValid()) {
			valid = false;
			md.print("- ");
			md.println(transactionGroup.getErrorMessage());
		}
		
		if("Default".equals(transactionGroup.getText())) {
			valid = false;
			md.print("- You cannot create a transaction group with Default name.");
		}
		
		if(!valid) {
			md.show();
		}
		
		return valid;
	}
}
