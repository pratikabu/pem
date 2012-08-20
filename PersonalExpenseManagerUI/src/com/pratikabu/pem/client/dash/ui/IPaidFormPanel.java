package com.pratikabu.pem.client.dash.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.client.dash.components.AccountsDatabase.AccountsLoadingDoneListener;
import com.pratikabu.pem.client.dash.components.AmountTextBox;
import com.pratikabu.pem.client.dash.components.AmountTextBox.AmountChangeListener;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase.Data;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.IPaidDTO;

public class IPaidFormPanel extends VerticalPanel implements DetailPaneable {
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
		amountBox.setAmountChangeListener(new AmountChangeListener() {
			@Override
			public void amountChanged(Double newValue) {
				pdp.distributeEqualAmount();
			}
		});
		
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
		
		pdp = new PaymentDistributionPanel(PaymentDistributionPanel.TYPE_IPAID);
		
		save = Utility.getActionButton("Save");
		save.getElement().getStyle().setFontSize(12, Unit.PX);
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				completeTransactionData();
				ServiceHelper.getPemservice().saveIPaidTransaction(dto, new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							if(null == dto.getAmountDistribution()) {
								PaneManager.gettList().addNewTransaction(dto);
							} else {
								PaneManager.gettList().updateTransaction(dto);
							}
							Utility.alert("saved");
						} else {
							Utility.alert("Error while saving data. Please try again.\n" +
									"If problem persist then kindly reload.");
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Utility.alert("Error while saving data. Please try again.\n" +
								"If problem persist then kindly reload.");
					}
				});
			}
		});
		
		cancel = Utility.getNormalButton("Cancel");
		cancel.getElement().getStyle().setFontSize(12, Unit.PX);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PaneManager.renderDTOObject(dto);
			}
		});
	}

	private void placeObjects() {
		///////////NORTH
		DockPanel dp = new DockPanel();
		dp.setStyleName("readerPanelStrip");
		dp.setWidth("100%");
		dp.setVerticalAlignment(ALIGN_MIDDLE);
		
		dp.setHorizontalAlignment(ALIGN_LEFT);
		Label lb = Utility.getLabel("iPaid Form", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(lb, DockPanel.WEST);
		dp.setHorizontalAlignment(ALIGN_RIGHT);
		dp.add(Utility.addHorizontally(5, save, cancel), DockPanel.EAST);
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
		
		ft.setWidget(++row, 0, Utility.getLabel("Amount (in " + OneTimeDataManager.getOTD().getCurrecnySymbol() + ")"));
		ft.setWidget(row, 1, amountBox);
		
		ft.setWidget(row, 2, Utility.getLabel("Payment Mode"));
		ft.setWidget(row, 3, paymentSource);
		
		this.add(ft);
		
		ft.setWidget(++row, 0, Utility.getLabel("People Associated:"));
		ft.setWidget(row, 2, Utility.getLabel("Select Tags (Tags help you associate your expense):"));
		cellFormatter.setColSpan(row, 2, 2);
		cellFormatter.setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		//////////////////add more people
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
		PaneManager.setInReaderPane(Utility.getLoadingWidget());
		if(null == obj || obj instanceof IPaidDTO) {
			this.dto = (IPaidDTO)obj;
			populateData();
			PaneManager.setInReaderPane(IPaidFormPanel.this);
		} else {
			long transactionId = (Long)obj;
			ServiceHelper.getPemservice().getTransactionDetail((Long) transactionId, new AsyncCallback<IPaidDTO>() {
				@Override
				public void onSuccess(IPaidDTO result) {
					renderRecord(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Utility.alert("Error fetching transaction details.");
				}
			});
		}
		
		transactionName.setFocus(true);
	}

	private void populateData() {
		if(null != dto) {
			this.transactionDate.setValue(dto.getDate());
			this.transactionName.setText(dto.getName());
			this.amountBox.setAmount(dto.getTotalAmount());
			this.notes.setText(dto.getNotes());
			
			for(String tag : dto.getSelectedTags()) {
				for(int i = 0; i < tagList.getItemCount(); i++) {
					if(tag.equals(tagList.getValue(i))) {
						tagList.setItemSelected(i, true);
					}
				}
			}
		
			pdp.updateData(dto.getAmountDistribution());
			
			for(int i = 0; i < paymentSource.getItemCount(); i++) {
				if((dto.getPaymentMode() + "").equals(paymentSource.getValue(i))) {
					paymentSource.setSelectedIndex(i);
					break;
				}
			}
		} else {
			transactionDate.setValue(new Date());
			
			for(int i = 0; i < tagList.getItemCount(); i++) {
				if("Others".equals(tagList.getValue(i))) {
					tagList.setItemSelected(i, true);
					break;
				}
			}
			
			dto = new IPaidDTO();
			
			pdp.updateData(dto.getAmountDistribution());
			AccountsDatabase.get().loadAccountsData(new AccountsLoadingDoneListener() {
				@Override
				public void accountsLoadingDone() {
					Data d = new Data();
					d.setAccount(AccountsDatabase.get().getMyself());
					d.setAmount(amountBox.getAmount());
					PaymentDistributionDatabase.get().addData(d);
				}
			});
		}
	}

	private void populateTagList() {
		for(String tag : OneTimeDataManager.getOTD().getTags()) {
			tagList.addItem(tag);
		}
	}

	private void populatePaymentMode() {
		for(AccountDTO a : OneTimeDataManager.getOTD().getUserSpecificPayableAccounts()) {
			paymentSource.addItem(a.getAccountName(), a.getAccountId() + "");
		}
	}
	
	public double getAmount() {
		return amountBox.getAmount();
	}

	private void completeTransactionData() {
		dto.setName(transactionName.getText());
		dto.setNotes(notes.getText());
		dto.setDate(transactionDate.getValue());
		// save payment mode
		dto.setPaymentMode(Long.parseLong(paymentSource.getValue(paymentSource.getSelectedIndex())));
		
		// save tags
		ArrayList<String> tags = new ArrayList<String>();
		for(int i = 0; i < tagList.getItemCount(); i++) {
			if(tagList.isItemSelected(i)) {
				tags.add(tagList.getValue(i));
			}
		}
		dto.setSelectedTags(tags);
		
		if(0 >= dto.getTransactionId()) {
			dto.setGroupId(PaneManager.gettList().getTransactionGroupId());
		}
		
		// copy distribution
		LinkedHashMap<AccountDTO, Double> amountDistribution = new LinkedHashMap<AccountDTO, Double>();
		
		for(Data d : PaymentDistributionDatabase.get().getDataProvider().getList()) {
			amountDistribution.put(d.getAccount(), d.getAmount());
		}
		
		dto.setAmountDistribution(amountDistribution);
	}

}
