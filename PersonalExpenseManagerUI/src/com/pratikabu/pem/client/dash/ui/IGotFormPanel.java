package com.pratikabu.pem.client.dash.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
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
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.LengthConstraintTextBox;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

public class IGotFormPanel extends VerticalPanel implements DetailPaneable {
	private TransactionDTO dto;
	
	private FlexTable ft;
	
	private LengthConstraintTextBox transactionName;
	private AmountTextBox amountBox;
	private ListBox paymentToList, accountFromList;
	private DateBox transactionDate;
	private TextArea notes;
	
	private Button save, cancel;
	
	public IGotFormPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("100%");
		
		ft = new FlexTable();
		ft.setCellSpacing(15);
		Utility.updateNameAndId(this, "iGotFormContainer");
		
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
		
		String widhtList = "290px";
		
		accountFromList = Utility.getListBox(false);
		accountFromList.setWidth(widhtList);
		Utility.updateNameAndId(accountFromList, "accountFrom");
		populateAccountFrom();
		accountFromList.setTabIndex(tabIndex++);
		
		paymentToList = Utility.getListBox(false);
		paymentToList.setWidth(widhtList);
		Utility.updateNameAndId(paymentToList, "paymentTo");
		populateAccountIn();
		paymentToList.setTabIndex(tabIndex++);
		
		amountBox = new AmountTextBox(false);
		Utility.updateNameAndId(amountBox, "amount");
		amountBox.setWidth(width);
		amountBox.setTabIndex(tabIndex++);
		
		notes = Utility.getTextArea();
		Utility.updateNameAndId(notes, "notes");
		notes.setWidth(width);
		notes.setHeight("70px");
		notes.setTabIndex(tabIndex++);
		
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
					paymentToList.addItem(dto.getAccountName(), dto.getAccountId() + "");
				} else if(CentralEventHandler.ACTION_EDITED == action) {
					for(int i = 0; i < paymentToList.getItemCount(); i++) {
						if(paymentToList.getValue(i).equals(dto.getAccountId() + "")) {
							paymentToList.setItemText(i, dto.getAccountName());
						}
					}
				} else if(CentralEventHandler.ACTION_DELETED == action) {
					int index = -1;
					for(int i = 0; i < paymentToList.getItemCount(); i++) {
						if(paymentToList.getValue(i).equals(dto.getAccountId() + "")) {
							index = i;
							break;
						}
					}
					
					paymentToList.removeItem(index);
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
		Label lb = Utility.getLabel("iGot Form", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(lb, DockPanel.WEST);
		dp.setHorizontalAlignment(ALIGN_RIGHT);
		dp.add(Utility.addHorizontally(5, save, cancel), DockPanel.EAST);
		this.add(dp);
		
		/////////////////////////////////////////////// CENTER
		
		int row = -1;
		
		ft.setWidget(++row, 0, Utility.getLabel("Date"));
		ft.setWidget(row, 1, transactionDate);
		
		ft.setWidget(++row, 0, Utility.getLabel("Name"));
		ft.setWidget(row, 1, transactionName);
		
		ft.setWidget(++row, 0, Utility.getLabel("From"));
		ft.setWidget(row, 1, accountFromList);
		
		ft.setWidget(++row, 0, Utility.getLabel("In"));
		ft.setWidget(row, 1, paymentToList);
		
		ft.setWidget(++row, 0, Utility.getLabel(OneTimeDataManager.getOTD().getCurrecnySymbol()));
		ft.setWidget(row, 1, amountBox);
		
		ft.setWidget(++row, 0, Utility.getLabel("Notes"));
		ft.setWidget(row, 1, notes);
		
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
		ViewerDialog.showWidget(IGotFormPanel.this, "Edit Transaction Details");
		
		transactionName.setFocus(true);
		
		PaneManager.hideLoading();
	}

	private void populateData() {
		if(null != dto) {
			this.transactionDate.setValue(dto.getDate());
			this.transactionName.setText(dto.getName());
			this.amountBox.setAmount(dto.getTotalAmount());
			this.notes.setText(dto.getNotes());
			
			// select outward account
			if(!dto.getTransactionEntries().isEmpty()) {
				AccountDTO inAcc =  dto.getTransactionEntries().get(0).getOutwardAccount();
				for(int i = 0; i < accountFromList.getItemCount(); i++) {
					if((inAcc.getAccountId() + "").equals(accountFromList.getValue(i))) {
						accountFromList.setSelectedIndex(i);
						break;
					}
				}
			} else {
				accountFromList.setSelectedIndex(0);
			}
			
			// select inward account
			if(!dto.getTransactionEntries().isEmpty()) {
				AccountDTO outAcc =  dto.getTransactionEntries().get(0).getInwardAccount();
				for(int i = 0; i < paymentToList.getItemCount(); i++) {
					if((outAcc.getAccountId() + "").equals(paymentToList.getValue(i))) {
						paymentToList.setSelectedIndex(i);
						break;
					}
				}
			} else {
				paymentToList.setSelectedIndex(0);
			}
		} else {
			dto = new TransactionDTO();
			dto.getSelectedTags().add("General");
			dto.setDate(new Date());
			dto.setName("");
			dto.setAmount(0);
			dto.setNotes("");
			dto.setEntryType(TransactionDTO.ET_INWARD_TG);
			
			populateData();// call itself to render the data
		}
	}

	private void populateAccountIn() {
		for(AccountDTO a : OneTimeDataManager.getOTD().getUserSpecificPayableAccounts()) {
			paymentToList.addItem(a.getAccountName(), a.getAccountId() + "");
		}
	}

	private void populateAccountFrom() {
		AccountsDatabase.get().loadAccountsData(new AccountsLoadingDoneListener() {
			@Override
			public void accountsLoadingDone() {
				List<AccountDTO> accs = AccountsDatabase.get().getAccountsOfType(AccountTypeDatabase.AT_PERSON, AccountTypeDatabase.AT_OTHER);
				for(AccountDTO a : accs) {
					accountFromList.addItem(a.getAccountName(), a.getAccountId() + "");
				}
			}
		});
	}
	
	public double getAmount() {
		return amountBox.getAmount();
	}

	private void completeTransactionData() {
		dto.setName(transactionName.getText());
		dto.setNotes(notes.getText());
		dto.setDate(transactionDate.getValue());
		// save payment mode
		AccountDTO inward = AccountsDatabase.get().getAccount(
				Long.parseLong(paymentToList.getValue(paymentToList.getSelectedIndex())));
		
		AccountDTO outward = AccountsDatabase.get().getAccount(
				Long.parseLong(accountFromList.getValue(accountFromList.getSelectedIndex())));
		
		if(0 >= dto.getTransactionId()) {
			dto.setGroupId(PaneManager.gettList().getTransactionGroupId());
		}
		
		// copy distribution
		ArrayList<TransactionEntryDTO> tes = new ArrayList<TransactionEntryDTO>();
		
		TransactionEntryDTO ted = new TransactionEntryDTO();
		ted.setAmount(amountBox.getAmount());
		ted.setInwardAccount(inward);
		ted.setOutwardAccount(outward);
		
		tes.add(ted);
		
		dto.setTransactionEntries(tes);
	}

}
