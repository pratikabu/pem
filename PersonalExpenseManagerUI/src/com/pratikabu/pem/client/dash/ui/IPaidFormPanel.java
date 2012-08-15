package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.AmountTextBox;
import com.pratikabu.pem.shared.OneTimeData;

public class IPaidFormPanel extends FlexTable implements DetailPaneable {
	private long transactionId;
	
	private TextBox transactionName;
	private AmountTextBox amountBox;
	private DateBox transactionDate;
	private TextArea notes;
	
	private Button save, cancel;
	
	public IPaidFormPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setCellSpacing(5);
		Utility.updateNameAndId(this, "iPaidFormContainer");
		
		final String width = "280px";
		
		transactionDate = Utility.getDateBox();
		Utility.updateNameAndId(transactionDate, "date");
		
		transactionName = Utility.getTextBox(null);
		Utility.updateNameAndId(transactionName, "transactionName");
		transactionName.setWidth(width);
		
		amountBox = new AmountTextBox();
		Utility.updateNameAndId(amountBox, "amount");
		amountBox.setWidth(width);
		
		notes = Utility.getTextArea();
		Utility.updateNameAndId(notes, "notes");
		notes.setWidth(width);
		
		save = Utility.getActionButton("Save");
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println(transactionDate.getValue());
			}
		});
		
		cancel = Utility.getNormalButton("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PaneManager.renderTransactionDetails(transactionId);
			}
		});
	}

	private void placeObjects() {
		int row = 0;
		
		this.setWidget(++row, 0, Utility.getLabel("Date"));
		this.setWidget(row, 1, transactionDate);
		
		this.setWidget(++row, 0, Utility.getLabel("Name"));
		this.setWidget(row, 1, transactionName);
		
		this.setWidget(++row, 0, Utility.getLabel("Amount (in " + OneTimeData.getCurrecnySymbol() + ")"));
		this.setWidget(row, 1, amountBox);
		
		this.setWidget(++row, 0, Utility.getLabel("Notes"));
		this.setWidget(row, 1, notes);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(save);
		hp.add(cancel);
		this.setWidget(++row, 1, hp);
	}

	@Override
	public boolean isSafeToClose() {
		return true;
	}

	@Override
	public void closing() {
	}

	@Override
	public Widget getMainWidget() {
		return this;
	}

	@Override
	public void renderRecord(Object obj) {
		transactionId = (Long)obj;
	}

}
