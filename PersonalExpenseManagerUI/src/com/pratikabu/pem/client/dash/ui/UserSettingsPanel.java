package com.pratikabu.pem.client.dash.ui;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.MessageDialog;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.UserSettingsDTO;

public class UserSettingsPanel extends VerticalPanel implements DetailPaneable {
	private UserSettingsDTO dto;
	
	private FlexTable ft;
	
	private ListBox currency;
	private CheckBox sendNewsLetter, sendMonthlyUpdates;
	
	private Button save, cancel;
	
	private static UserSettingsPanel usp;
	
	public static UserSettingsPanel get() {
		if(null == usp) {
			usp = new UserSettingsPanel();
		}
		
		usp.fetchSettings();
		return usp;
	}

	private UserSettingsPanel() {
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
		
		currency = Utility.getListBox(false);
		currency.setWidth(width);
		Utility.updateNameAndId(currency, "paymentSource");
		populateCurrency();
		currency.setTabIndex(tabIndex++);
		
		sendMonthlyUpdates = new CheckBox("Send Me Monthly Updates");
		sendMonthlyUpdates.setStyleName(Constants.CSS_NORMAL_LABEL);
		Utility.updateNameAndId(sendMonthlyUpdates, "monthlyUpdates");
		sendMonthlyUpdates.setTabIndex(tabIndex++);
		
		sendNewsLetter = new CheckBox("Send Me News Letter from PEM");
		sendNewsLetter.setStyleName(Constants.CSS_NORMAL_LABEL);
		Utility.updateNameAndId(sendNewsLetter, "newsLetter");
		sendNewsLetter.setTabIndex(tabIndex++);
		
		save = Utility.getActionButton("Save");
		save.getElement().getStyle().setFontSize(12, Unit.PX);
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!isValidatedFormData()) {
					return;
				}
				
				completeData();
				ServiceHelper.getPemservice().saveUserSettings(dto, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						Utility.alert("Unable to save. Try again.");
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							Utility.alert("Settings saved succussfully.");
							ViewerDialog.get().close();
						}
					}
				});
			}
		});
		
		cancel = Utility.getNormalButton("Cancel");
		cancel.getElement().getStyle().setFontSize(12, Unit.PX);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ViewerDialog.get().close();
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
		Label lb = Utility.getLabel("Settings", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(lb, DockPanel.WEST);
		dp.setHorizontalAlignment(ALIGN_RIGHT);
		dp.add(Utility.addHorizontally(5, save, cancel), DockPanel.EAST);
		this.add(dp);
		
		/////////////////////////////////////////////// CENTER
		
		int row = -1;
		
		ft.setWidget(++row, 0, Utility.getLabel("Currency"));
		ft.setWidget(row, 1, currency);
		
		ft.setWidget(++row, 1, sendMonthlyUpdates);
		ft.setWidget(++row, 1, sendNewsLetter);
		
		this.add(ft);
	}

	protected void completeData() {
		dto.setCurrency(currency.getValue(currency.getSelectedIndex()));
		dto.setSendMonthly(sendMonthlyUpdates.getValue());
		dto.setSendNews(sendNewsLetter.getValue());
	}

	private boolean isValidatedFormData() {
		MessageDialog md = MessageDialog.get();
		md.setText("Errors while saving");
		boolean valid = true;
		
		if(!valid) {
			md.show();
		}
		
		return valid;
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
	}

	private void populateCurrency() {
		ServiceHelper.getPemservice().fetchWebsiteData(2, new AsyncCallback<LinkedHashMap<String, String>>() {
			@Override
			public void onFailure(Throwable caught) {
				Utility.alert("Unable to fetch currencies.");
			}

			@Override
			public void onSuccess(LinkedHashMap<String, String> result) {
				for(Entry<String, String> entry : result.entrySet()) {
					currency.addItem(entry.getValue(), entry.getKey());
				}
			}
		});
	}
	
	private void fetchSettings() {
		if(null != dto) {
			renderSettings(dto);
		} else {
			ServiceHelper.getPemservice().fetchUserSettings(new AsyncCallback<UserSettingsDTO>() {
				@Override
				public void onFailure(Throwable caught) {
					Utility.alert("Unable to fetch settings.");
				}
	
				@Override
				public void onSuccess(UserSettingsDTO result) {
					renderSettings(result);
				}
			});
		}
	}

	private void renderSettings(UserSettingsDTO result) {
		dto = result;
		if(null == result) {
			dto = new UserSettingsDTO();
			dto.setCurrency("IN");
			dto.setSendMonthly(true);
			dto.setSendNews(true);
		}
		
		for(int i = 0; i < currency.getItemCount(); i++) {
			if(dto.getCurrency().equals(currency.getValue(i))) {
				currency.setSelectedIndex(i);
				break;
			}
		}
		
		sendMonthlyUpdates.setValue(dto.isSendMonthly());
		sendNewsLetter.setValue(dto.isSendNews());
	}
}
