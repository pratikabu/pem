/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.shared.model.AccountDTO;

/**
 * @author pratsoni
 *
 */
public class AccountChooserDialog extends DialogBox {
	
	private static AccountChooserDialog ac;
	
	private CellList<AccountDTO> accountList;
	
	private Button selectButton, closeButton;
	
	private AccountDTO selectedAccountDTO;
	private AccountSelectionListener listener;
	
	public static void chooseSingleAccount(String accountType, AccountSelectionListener listener) {
		get().listener = listener;
		get().show();
		AccountsDatabase.get().refreshDisplays(accountType);
	}
	
	public static AccountChooserDialog get() {
		if(null == ac) {
			ac = new AccountChooserDialog();
		}
		
		return ac;
	}
	
	private AccountChooserDialog() {
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
		
		accountList = new CellList<AccountDTO>(new AccountCell(), AccountsDatabase.KEY_PROVIDER);
		accountList.setPageSize(30);
		
		accountList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		accountList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		accountList.setLoadingIndicator(Utility.getLoadingWidget());
		
		AccountsDatabase.get().addDataDisplay(accountList);
		
		// Add a selection model so we can select cells.
		final SingleSelectionModel<AccountDTO> selectionModel = new SingleSelectionModel<AccountDTO>(
				AccountsDatabase.KEY_PROVIDER);
		accountList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedAccountDTO = selectionModel.getSelectedObject();
			}
		});
		
		selectButton = Utility.getActionButton("Select & Close");
		selectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(null == selectedAccountDTO) {
					Utility.alert("Please select any account.");
					return;
				}
				
				if(null != listener) {
					listener.accountSelected(selectedAccountDTO);
				}
				
				get().hide();
			}
		});
		
		closeButton = Utility.getNormalButton("Close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				get().hide();
			}
		});
	}
	
	private void placeObjects() {
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(5);
		
		ScrollPanel sp = new ScrollPanel(accountList);
		sp.setStyleName(Constants.CSS_NORMAL_TEXT);
		sp.setWidth("300px");
		sp.setHeight("300px");
		vp.add(sp);
		
		vp.add(Utility.addHorizontally(5, selectButton, closeButton));
		
		this.setWidget(vp);
	}
	
	private class AccountCell extends AbstractCell<AccountDTO> {

		@Override
		public void render(Context context,
				AccountDTO value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<span class='normalLabel' style='font-size: 16px'>");
			sb.appendHtmlConstant(value.getAccountName());
			sb.appendHtmlConstant("</span>");
		}

	}
	
	public static interface AccountSelectionListener {
		void accountSelected(AccountDTO account);
	}
}
