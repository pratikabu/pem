/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.client.dash.ui.panel.AccountManagerPanel;

/**
 * @author pratsoni
 *
 */
public class AccountManagerDialog extends DialogBox {
	
	private static AccountManagerDialog ac;
	
	private AccountManagerPanel amp;
	
	private Button renameButton, deleteButton, closeButton;
	
	public static void showManager() {
		if(null == ac) {
			ac = new AccountManagerDialog();
		}
		
		ac.amp.updateData();
		ac.show();
	}
	
	private AccountManagerDialog() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setModal(true);
		this.setText("Select an Account");
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		int[] coordinates = Utility.getCenterOfWindow(500, 300);
		this.setPopupPosition(coordinates[1], coordinates[0]);
		
		amp = new AccountManagerPanel();
		
		closeButton = Utility.getNormalButton("Close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				amp.resetSelectedDTO();
				hide();
			}
		});
		
		renameButton = Utility.getActionButton("Rename");
		renameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(null == amp.getSelectedDTO()) {
					Utility.alert("Select any account");
					return;
				}
				
				if("Myself".equals(amp.getSelectedDTO().getAccountName())) {
					Utility.alert("You cannot rename this Account.");
					return;
				}
				
				AccountDialog.show(amp.getSelectedDTO());
			}
		});
		
		deleteButton = Utility.getNormalButton("Delete");
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(null == amp.getSelectedDTO()) {
					Utility.alert("Select any account");
					return;
				}
				
				if("Myself".equals(amp.getSelectedDTO().getAccountName())) {
					Utility.alert("You cannot delete this Account.");
					return;
				}
				AccountsDatabase.get().deleteAccount(amp.getSelectedDTO());
			}
		});
	}
	
	private void placeObjects() {
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(5);
		
		vp.add(amp);
		vp.add(Utility.addHorizontally(5, renameButton, deleteButton, closeButton));
		
		this.setWidget(vp);
	}
}
