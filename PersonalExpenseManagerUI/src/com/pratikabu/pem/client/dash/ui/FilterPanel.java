package com.pratikabu.pem.client.dash.ui;

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.MessageDialog;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.shared.model.FilterDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

public class FilterPanel extends VerticalPanel {
	private static FilterPanel filterPanel;
	
	private FilterDTO filter;
	
	private FlexTable ft;
	
	private DateBox startingDate, endingDate;
	private ListBox accountsList, showOnlyList;
	
	private Button apply, cancel;
	
	public static FilterPanel get() {
		if(null == filterPanel) {
			filterPanel = new FilterPanel();
			filterPanel.initFilter();
		}
		
		filterPanel.populateFilterData();
		return filterPanel;
	}
	
	private FilterPanel() {
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setWidth("100%");
		
		ft = new FlexTable();
		ft.setCellSpacing(15);
		Utility.updateNameAndId(this, "filterContainer");
		
		final String width = "280px";
		int tabIndex = 1;
		
		startingDate = Utility.getDateBox();
		Utility.updateNameAndId(startingDate, "startingDate");
		startingDate.setWidth(width);
		startingDate.setTabIndex(tabIndex++);
		
		endingDate = Utility.getDateBox();
		Utility.updateNameAndId(endingDate, "endingDate");
		endingDate.setWidth(width);
		endingDate.setTabIndex(tabIndex++);
		
		accountsList = Utility.getListBox(true);
		accountsList.setHeight(width);
		Utility.updateNameAndId(accountsList, "accountsList");
		accountsList.setTabIndex(tabIndex++);
		
		showOnlyList = Utility.getListBox(false);
		populateShowOnlyList();
		
		apply = Utility.getActionButton("Apply");
		apply.getElement().getStyle().setFontSize(12, Unit.PX);
		apply.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!isValidatedFormData()) {
					return;
				}
				completeTransactionData();
				
				TransactionDatabase.get().refreshDisplays(null);
				ViewerDialog.get().close();
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

	private void populateShowOnlyList() {
		showOnlyList.addItem("Both", "-1");
		showOnlyList.addItem("Inward Transactions", TransactionDTO.ET_INWARD_TG + "");
		showOnlyList.addItem("Outward Transactions", TransactionDTO.ET_OUTWARD_TG + "");
	}

	private boolean isValidatedFormData() {
		MessageDialog md = MessageDialog.get();
		md.setText("Errors while applying");
		boolean valid = true;
		
		if(null == startingDate.getValue()) {
			md.println("- Select any Starting Date.");
			valid = false;
		}
		
		if(null == endingDate.getValue()) {
			md.println("- Select any Ending Date.");
			valid = false;
		}
		
		if(valid && startingDate.getValue().after(endingDate.getValue())) {
			valid = false;
			md.print("- The Starting Date must be lesser than the Ending Date.");
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
		Label lb = Utility.getLabel("Filters", Constants.CSS_FORM_HEADING);
		lb.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		dp.add(lb, DockPanel.WEST);
		dp.setHorizontalAlignment(ALIGN_RIGHT);
		dp.add(Utility.addHorizontally(5, apply, cancel), DockPanel.EAST);
		this.add(dp);
		
		/////////////////////////////////////////////// CENTER
		
		int row = -1;
		
		ft.setWidget(++row, 0, Utility.getLabel("Starting Date"));
		ft.setWidget(row, 1, startingDate);
		
		ft.setWidget(++row, 0, Utility.getLabel("Ending Date"));
		ft.setWidget(row, 1, endingDate);
		
		ft.setWidget(++row, 0, Utility.getLabel("Show"));
		ft.setWidget(row, 1, showOnlyList);
		
		this.add(ft);
	}

	private void completeTransactionData() {
		filter.setStartingDate(startingDate.getValue());
		filter.setEndingDate(endingDate.getValue());
		filter.setDirection(Integer.parseInt(showOnlyList.getValue(showOnlyList.getSelectedIndex())));
	}
	
	private void populateFilterData() {
		startingDate.setValue(filter.getStartingDate());
		endingDate.setValue(filter.getEndingDate());
		
		for(int i = 0; i < showOnlyList.getItemCount(); i++) {
			if(filter.getDirection() == Integer.parseInt(showOnlyList.getValue(i))) {
				showOnlyList.setSelectedIndex(i);
				break;
			}
		}
	}

	private void initFilter() {
		filter = new FilterDTO();
		
		Date startingDate = new Date();
		CalendarUtil.setToFirstDayOfMonth(startingDate);
		filter.setStartingDate(startingDate);
		
		Date endindDate = new Date();
		filter.setEndingDate(endindDate);
		
		filter.setDirection(-1);
	}

	public FilterDTO getFilter() {
		return filter;
	}

}
