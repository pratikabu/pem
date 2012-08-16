package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.AmountTextBox;
import com.pratikabu.pem.client.dash.components.PaymentDistributionPanel;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

public class IPaidFormPanel extends VerticalPanel implements DetailPaneable {
	private long transactionId;
	private IPaidDTO dto;
	
	private FlexTable ft;
	
	private TextBox transactionName;
	private AmountTextBox amountBox;
	private ListBox paymentSource;
	private DateBox transactionDate;
	private TextArea notes;
	
	private PaymentDistributionPanel pdp;
	
	private ListBox tagList;
	
	private Button save, cancel;
	
	public IPaidFormPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("100%");
		
		ft = new FlexTable();
		ft.setCellSpacing(15);
		Utility.updateNameAndId(this, "iPaidFormContainer");
		
		final String width = "280px";
		int tabIndex = 1;
		
		transactionDate = Utility.getDateBox();
		Utility.updateNameAndId(transactionDate, "date");
		transactionDate.setWidth(width);
		transactionDate.setTabIndex(tabIndex++);
		
		transactionName = Utility.getTextBox(null);
		Utility.updateNameAndId(transactionName, "transactionName");
		transactionName.setWidth(width);
		transactionName.setTabIndex(tabIndex++);
		
		amountBox = new AmountTextBox(false);
		Utility.updateNameAndId(amountBox, "amount");
		amountBox.setWidth(width);
		amountBox.setTabIndex(tabIndex++);
		
		notes = Utility.getTextArea();
		Utility.updateNameAndId(notes, "notes");
		notes.setWidth("270px");
		notes.setHeight("100%");
		notes.setTabIndex(tabIndex++);
		
		paymentSource = Utility.getListBox(false);
		paymentSource.setWidth(width);
		Utility.updateNameAndId(paymentSource, "paymentSource");
		populatePaymentMode();
		paymentSource.setTabIndex(tabIndex++);
		
		tagList = Utility.getListBox(true);
		tagList.setHeight("200px");
		Utility.updateNameAndId(tagList, "transactionTags");
		populateTagList();
		tagList.setTabIndex(tabIndex++);
		
		save = Utility.getActionButton("Save");
		save.getElement().getStyle().setFontSize(12, Unit.PX);
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println(transactionDate.getValue());
			}
		});
		
		cancel = Utility.getNormalButton("Cancel");
		cancel.getElement().getStyle().setFontSize(12, Unit.PX);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PaneManager.renderTransactionDetails(transactionId, TransactionDTO.ET_OUTWARD_TG);
			}
		});
	}

	private void placeObjects() {
		///////////NORTH
		DockPanel dp = new DockPanel();
		dp.setStyleName("readerPanelStrip");
		dp.setWidth("100%");
		dp.setVerticalAlignment(ALIGN_MIDDLE);
		
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.setSpacing(5);
		hp1.add(save);
		hp1.add(cancel);
		
		dp.setHorizontalAlignment(ALIGN_LEFT);
		Label lb = Utility.getLabel("iPaid Form", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(lb, DockPanel.WEST);
		dp.setHorizontalAlignment(ALIGN_RIGHT);
		dp.add(hp1, DockPanel.EAST);
		this.add(dp);
		
		/////////////////////////////////////////////// CENTER
		
		FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
		int row = -1;
		
		ft.setWidget(++row, 0, Utility.getLabel("Date"));
		ft.setWidget(row, 1, transactionDate);
		
		ft.setWidget(row, 2, Utility.getLabel("Notes"));
		ft.setWidget(row, 3, notes);
		cellFormatter.setRowSpan(row, 3, 2);
		cellFormatter.setVerticalAlignment(row, 3, HasVerticalAlignment.ALIGN_TOP);
		
		ft.setWidget(++row, 0, Utility.getLabel("Name"));
		ft.setWidget(row, 1, transactionName);
		
		ft.setWidget(++row, 0, Utility.getLabel("Amount (in " + OneTimeData.getCurrecnySymbol() + ")"));
		ft.setWidget(row, 1, amountBox);
		
		ft.setWidget(row, 2, Utility.getLabel("Payment Mode"));
		ft.setWidget(row, 3, paymentSource);
		
		this.add(ft);
		
		ft.setWidget(++row, 0, Utility.getLabel("People Associated:"));
		ft.setWidget(row, 2, Utility.getLabel("Select Tags (Tags help you associate your expense):"));
		cellFormatter.setColSpan(row, 2, 2);
		cellFormatter.setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		//////////////////add more people
		pdp = new PaymentDistributionPanel();
		pdp.updateData(null);

		ft.setWidget(++row, 0, pdp);
		cellFormatter.setColSpan(row, 0, 2);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		// add tags
		ft.setWidget(row, 2, tagList);
		cellFormatter.setColSpan(row, 2, 2);
		cellFormatter.setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_TOP);
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
		transactionName.setFocus(true);
	}

	private void populateTagList() {
		tagList.addItem("Others", "other");
		tagList.addItem("Entertainement", "entr");
		tagList.addItem("Clothing", "clothing");
		tagList.addItem("Shopping", "shop");
		tagList.addItem("Sanyo", "san");
		tagList.addItem("Sanyo1", "san1");
		tagList.addItem("Sanyo2", "san2");
		tagList.addItem("Sany3o", "san3");
		
		tagList.setItemSelected(0, true);
	}

	private void populatePaymentMode() {
		paymentSource.addItem("Main Balance", "other");
		paymentSource.addItem("Credit Card", "entr");
		paymentSource.addItem("Sodexo", "clothing");
	}

}
