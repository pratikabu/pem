package com.pratikabu.pem.client.dash.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.MessageDialog;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.client.dash.components.AccountsDatabase;
import com.pratikabu.pem.client.dash.components.AccountsDatabase.AccountsLoadingDoneListener;
import com.pratikabu.pem.client.dash.components.AmountTextBox;
import com.pratikabu.pem.client.dash.components.AmountTextBox.AmountChangeListener;
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.LengthConstraintTextBox;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase;
import com.pratikabu.pem.client.dash.components.PaymentDistributionDatabase.Data;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

public class IPaidFormPanel extends VerticalPanel implements DetailPaneable {
	private TransactionDTO dto;
	
	private FlexTable ft;
	
	private LengthConstraintTextBox transactionName;
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
		
		transactionName = new LengthConstraintTextBox(Constants.MAX_TRANSACTION_NAME_CHARACTERS);
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
				if(!isValidatedFormData()) {
					return;
				}
				
				final int action =  CentralEventHandler.getActionForCreateUpdate(dto.getTransactionId());
				completeTransactionData();
				ServiceHelper.getPemservice().saveTransaction(dto, new AsyncCallback<Long>() {
					@Override
					public void onSuccess(Long result) {
						if(result != -1L) {
							dto.setTransactionId(result);
							CentralEventHandler.transactionUpdated(dto, action);
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
				if(dto.getTransactionId() > 0) {
					PaneManager.renderTransaction(dto);
				} else {
					PaneManager.renderTransaction(null);
				}
			}
		});
		
		CentralEventHandler.addListener(new CentralEventHandler.AccountUpdateListener() {
			@Override
			public void accountUpdatedEvent(AccountDTO dto, int action) {
				if(!(dto.getAccountType().equals(AccountTypeDatabase.AT_CREDIT) ||
						dto.getAccountType().equals(AccountTypeDatabase.AT_MAIN))) {
					return;
				}
				
				if(CentralEventHandler.ACTION_CREATED == action) {
					paymentSource.addItem(dto.getAccountName(), dto.getAccountId() + "");
				} else if(CentralEventHandler.ACTION_EDITED == action) {
					for(int i = 0; i < paymentSource.getItemCount(); i++) {
						if(paymentSource.getValue(i).equals(dto.getAccountId() + "")) {
							paymentSource.setItemText(i, dto.getAccountName());
						}
					}
				} else if(CentralEventHandler.ACTION_DELETED == action) {
					int index = -1;
					for(int i = 0; i < paymentSource.getItemCount(); i++) {
						if(paymentSource.getValue(i).equals(dto.getAccountId() + "")) {
							index = i;
							break;
						}
					}
					
					paymentSource.removeItem(index);
				}
			}
		});
	}

	private boolean isValidatedFormData() {
		MessageDialog md = MessageDialog.get();
		md.setText("Errors while saving");
		boolean valid = true;
		
		if(null == transactionDate.getValue()) {
			md.println("- Select any date.");
			valid = false;
		}
		
		if(!transactionName.isValid()) {
			valid = false;
			md.println(transactionName.getErrorMessage());
		}
		
		if(transactionName.getText().isEmpty()) {
			valid = false;
			md.println("- Transaction name cannot be empty.");
		}
		
		if(!amountBox.isValid()) {
			valid = false;
			md.print("- ");
			md.println(amountBox.getErrorMessage());
		}
		
		List<Data> dataList = PaymentDistributionDatabase.get().getDataProvider().getList();
		
		if(dataList.isEmpty()) {
			valid = false;
			md.println("- There should be atleast one transaction entry for the amount distribution.");
		} else {
			double totalAmount = 0d;
			for(Data d : dataList) {
				totalAmount += d.getAmount();
			}
			
			if(totalAmount != amountBox.getAmount()) {
				valid = false;
				md.print("- The amount is not distributed. There is a difference of ");
				md.println(Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(),
						(amountBox.getAmount() - totalAmount)));
			}
		}
		
		if(amountBox.getAmount() == 0) {
			valid = false;
			md.println("- Amount should be more than 0.00");
		}
		
		if(!valid) {
			md.show();
		}
		
		return valid;
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
		
		ft.setWidget(++row, 0, Utility.getLabel(OneTimeDataManager.getOTD().getCurrecnySymbol()));
		ft.setWidget(row, 1, amountBox);
		
		ft.setWidget(row, 2, Utility.getLabel("From"));
		ft.setWidget(row, 3, paymentSource);
		
		ft.setWidget(++row, 0, Utility.getLabel("Paid to:"));
		cellFormatter.setColSpan(row, 0, 2);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		ft.setWidget(row, 1, Utility.getLabel("Tags (Tags help you associate your expense):"));
		cellFormatter.setColSpan(row, 1, 2);
		cellFormatter.setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		//////////////////add more people
		ft.setWidget(++row, 0, pdp);
		cellFormatter.setColSpan(row, 0, 2);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		// add tags
		ft.setWidget(row, 2, tagList);
//		cellFormatter.setColSpan(row, 2, 2);
		cellFormatter.setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_TOP);
		
		this.add(ft);
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
		PaneManager.showLoading();
		
		this.dto = (TransactionDTO)obj;
		populateData();
		ViewerDialog.showWidget(IPaidFormPanel.this, "Edit Transaction Details");
		
		transactionName.setFocus(true);
		
		PaneManager.hideLoading();
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
			
			if(!dto.getTransactionEntries().isEmpty()) {
				AccountDTO outAcc =  dto.getTransactionEntries().get(0).getOutwardAccount();
				for(int i = 0; i < paymentSource.getItemCount(); i++) {
					if((outAcc.getAccountId() + "").equals(paymentSource.getValue(i))) {
						paymentSource.setSelectedIndex(i);
						break;
					}
				}
			} else {
				paymentSource.setSelectedIndex(0);
			}
			
			pdp.updateData(getLinkedHashMap());
		} else {
			dto = new TransactionDTO();
			dto.getSelectedTags().add("General");
			dto.setDate(new Date());
			dto.setName("");
			dto.setAmount(0);
			dto.setNotes("");
			dto.setEntryType(TransactionDTO.ET_OUTWARD_TG);
			
			populateData();// call itself to render the data
			
			// set Myself as default entry
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

	private LinkedHashMap<AccountDTO, Double> getLinkedHashMap() {
		LinkedHashMap<AccountDTO, Double> map = new LinkedHashMap<AccountDTO, Double>();
		
		if(null != dto) {
			for(TransactionEntryDTO ted : dto.getTransactionEntries()) {
				map.put(ted.getInwardAccount(), ted.getAmount());
			}
		}
		
		return map;
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
		AccountDTO outward = AccountsDatabase.get().getAccount(
				Long.parseLong(paymentSource.getValue(paymentSource.getSelectedIndex())));
		
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
		ArrayList<TransactionEntryDTO> tes = new ArrayList<TransactionEntryDTO>();
		for(Data d : PaymentDistributionDatabase.get().getDataProvider().getList()) {
			TransactionEntryDTO ted = new TransactionEntryDTO();
			ted.setAmount(d.getAmount());
			ted.setInwardAccount(d.getAccount());
			ted.setOutwardAccount(outward);
			
			tes.add(ted);
		}
		
		dto.setTransactionEntries(tes);
	}

}
