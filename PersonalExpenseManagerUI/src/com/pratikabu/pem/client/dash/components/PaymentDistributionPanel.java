/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase.Data;
import com.pratikabu.pem.shared.OneTimeData;

/**
 * @author pratsoni
 * 
 */
public class PaymentDistributionPanel extends VerticalPanel {
	private List<Data> paymentDistributionList;

	private DataGrid<Data> dg;
	private Button removeSelected, addNew;

	public PaymentDistributionPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("350px");
		initDataGrid();
		
		removeSelected = Utility.getNormalButton("-");
		removeSelected.setTitle("Remove selected records.");
		
		addNew = Utility.getNormalButton("+");
		addNew.setTitle("Add new record.");
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
				System.out.println(value);
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

		Column<Data, String> amountColumn = new Column<Data, String>(new MyAmountTC()) {
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
		dg.addColumn(amountColumn, "Amount (" + OneTimeData.getCurrecnySymbol() + ")");
		dg.setColumnWidth(amountColumn, "60px");
	}

	private void placeObjects() {
		this.add(dg);
		
		HorizontalPanel hp = (HorizontalPanel)Utility.addHorizontally(5, addNew, removeSelected);
		hp.setHorizontalAlignment(ALIGN_RIGHT);
		this.add(hp);
	}

	public static class MyAmountTC extends EditTextCell {
		public MyAmountTC() {

		}

		@Override
		protected void onEnterKeyDown(Context context, Element parent,
				String value, NativeEvent event,
				ValueUpdater<String> valueUpdater) {
			super.onEnterKeyDown(context, parent, value, event, valueUpdater);

			System.out.println("Enter pressed.");
		}
	}

	public void updateData(List<Data> paymentDistributionList) {
		this.paymentDistributionList = paymentDistributionList;
		PaymentDistributionDatabase.get().addDataDisplay(dg);
	}
}