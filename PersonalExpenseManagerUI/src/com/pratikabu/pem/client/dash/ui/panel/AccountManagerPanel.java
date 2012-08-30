/**
 * 
 */
package com.pratikabu.pem.client.dash.ui.panel;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.shared.model.AccountDTO;

/**
 * @author pratsoni
 * 
 */
public class AccountManagerPanel extends VerticalPanel {
	// TODO the pagination as if the accounts count is more than 100 than it will not show it.
	
	private AccountDTO selectedDTO;
	
	private DataGrid<AccountDTO> dg;
	
	public AccountManagerPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("470px");
		initDataGrid();
	}

	private void initDataGrid() {
		dg = new DataGrid<AccountDTO>(100, AccountsDatabase.KEY_PROVIDER);

		dg.setMinimumTableWidth(450, Unit.PX);
		dg.setHeight("400px");

		Column<AccountDTO, String> accountColumn = new Column<AccountDTO, String>(new TextCell()) {
			@Override
			public String getValue(AccountDTO object) {
				return object.getAccountName();
			}
		};
		dg.addColumn(accountColumn, "Account");
		dg.setColumnWidth(accountColumn, "150px");

		Column<AccountDTO, String> accTypeColumn = new Column<AccountDTO, String>(new TextCell()) {
			@Override
			public String getValue(AccountDTO object) {
				return object.getAccountType();
			}
		};
		dg.addColumn(accTypeColumn, "Type");
		dg.setColumnWidth(accTypeColumn, "150px");
		
		Column<AccountDTO, String> currentBalanceColumn = new Column<AccountDTO, String>(new TextCell()) {
			@Override
			public String getValue(AccountDTO object) {
				return Utility.get2DecimalAmount(object.getCurrentBalance());
			}
		};
		currentBalanceColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dg.addColumn(currentBalanceColumn, "Curr. Balance (" + OneTimeDataManager.getOTD().getCurrecnySymbol() + ")");
		dg.setColumnWidth(currentBalanceColumn, "150px");
		
		final SingleSelectionModel<AccountDTO> ssm = new SingleSelectionModel<AccountDTO>(
				AccountsDatabase.KEY_PROVIDER);
		dg.setSelectionModel(ssm);
		ssm.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedDTO = ssm.getSelectedObject();
			}
		});
	}

	private void placeObjects() {
		ScrollPanel sp = new ScrollPanel(dg);
		this.add(sp);
	}

	public void updateData() {
		AccountsDatabase.get().addDataDisplay(dg);
		AccountsDatabase.get().refreshDisplays(AccountTypeDatabase.AT_ALL);
	}

	public AccountDTO getSelectedDTO() {
		return selectedDTO;
	}
	
	public void resetSelectedDTO() {
		selectedDTO = null;
	}
}