/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase.Data;
import com.pratikabu.pem.client.dash.ui.AccountChooserDialog.AccountSelectionListener;
import com.pratikabu.pem.shared.model.AccountDTO;

/**
 * @author pratsoni
 * 
 */
public class PaymentDistributionPanel extends VerticalPanel {
	public static final int TYPE_IPAID = 1, TYPE_IGOT = 2;
	private DataGrid<Data> dg;
	private Button removeSelected, addNew;
	
	private int sourceType;

	public PaymentDistributionPanel(int sourceType) {
		this.sourceType = sourceType;
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("350px");
		initDataGrid();
		
		removeSelected = Utility.getNormalButton("-");
		removeSelected.setTitle("Remove selected records.");
		removeSelected.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<Data> toBeRemoved = new ArrayList<PaymentDistributionDatabase.Data>();
				for(Data d : PaymentDistributionDatabase.get().getDataProvider().getList()) {
					if(d.isSelected()) {
						toBeRemoved.add(d);
					}
				}
				
				for(Data d : toBeRemoved) {
					PaymentDistributionDatabase.get().getDataProvider().getList().remove(d);
				}
				
				distributeEqualAmount();
			}
		});
		
		addNew = Utility.getNormalButton("+");
		addNew.setTitle("Add new record.");
		addNew.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AccountChooserDialog.chooseSingleAccount(AccountsDatabase.AT_PERSON, new AccountSelectionListener() {
					@Override
					public void accountSelected(AccountDTO account) {
						List<Data> dataList = PaymentDistributionDatabase.get().getDataProvider().getList();
						
						Data d = new Data();
						d.setAccount(account);
						
						if(dataList.contains(d)) {
							return;
						}
						
						PaymentDistributionDatabase.get().addData(d);
						distributeEqualAmount();
					}
				});
			}
		});
	}

	private void initDataGrid() {
		dg = new DataGrid<Data>(10, PaymentDistributionDatabase.KEY_PROVIDER);

		dg.setMinimumTableWidth(250, Unit.PX);
		dg.setHeight("170px");

		Column<Data, Boolean> checkBoxColumn = new Column<Data, Boolean>(
				new CheckboxCell()) {
			@Override
			public Boolean getValue(Data object) {
				return null;
			}
		};
		dg.addColumn(checkBoxColumn, "");
		dg.setColumnWidth(checkBoxColumn, "20px");
		checkBoxColumn.setFieldUpdater(new FieldUpdater<PaymentDistributionDatabase.Data, Boolean>() {
			@Override
			public void update(int index, Data object, Boolean value) {
				object.setSelected(value);
			}
		});

		Column<Data, String> accountColumn = new Column<Data, String>(new TextCell()) {
			@Override
			public String getValue(Data object) {
				return object.getAccount().getAccountName();
			}
		};
		dg.addColumn(accountColumn, "Account");
		dg.setColumnWidth(accountColumn, "100px");

		Column<Data, String> amountColumn = new Column<Data, String>(new EditTextCell()) {
			@Override
			public String getValue(Data object) {
				return Utility.get2DecimalAmount(object.getAmount());
			}
		};
		amountColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		amountColumn.setFieldUpdater(new FieldUpdater<Data, String>() {
			@Override
			public void update(int index, Data object, String value) {
				System.out.println("Old Data Object: " + object);
				System.out.println("I'm modified to the new value: " + value);
			}
		});
		dg.addColumn(amountColumn, "Amount (" + OneTimeDataManager.getOTD().getCurrecnySymbol() + ")");
		dg.setColumnWidth(amountColumn, "60px");
	}

	private void placeObjects() {
		this.add(dg);
		
		HorizontalPanel hp = (HorizontalPanel)Utility.addHorizontally(5, addNew, removeSelected);
		hp.setHorizontalAlignment(ALIGN_RIGHT);
		this.add(hp);
	}

	public void updateData(LinkedHashMap<AccountDTO, Double> paymentDistributionList) {
		if(null == paymentDistributionList) {
			paymentDistributionList = new LinkedHashMap<AccountDTO, Double>();
		}
		
		PaymentDistributionDatabase pddb = PaymentDistributionDatabase.get();
		
		pddb.clearData();
		
		for(Entry<AccountDTO, Double> entry : paymentDistributionList.entrySet()) {
			Data d = new Data();
			d.setAccount(entry.getKey());
			d.setAmount(entry.getValue());
			
			pddb.addData(d);
		}
		
		pddb.addDataDisplay(dg);
	}

	public void distributeEqualAmount() {
		List<Data> dataList = PaymentDistributionDatabase.get().getDataProvider().getList();
		int divisor = dataList.size();
		double dividend = TYPE_IPAID == sourceType ? PaneManager.getIpfp().getAmount() : 0;
		double distributedAmount = dividend / divisor;
		
		for(Data dt : dataList) {
			dt.setAmount(distributedAmount);
		}
		
		PaymentDistributionDatabase.get().refreshDisplays();
	}
}