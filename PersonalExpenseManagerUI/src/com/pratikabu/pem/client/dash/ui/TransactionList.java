/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.CentralEventHandler.TransactionUpdateListener;
import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.client.dash.components.TransactionGroupDatabase;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionList extends VerticalPanel {
	private CellList<TransactionDTO> tgList;
	
	private Long transactionGroupId;
	private Long actualId;
	
	public TransactionList() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setWidth("100%");
		this.setHeight("100%");
		
		tgList = new CellList<TransactionDTO>(new TransactionCell());

		tgList.setPageSize(30);
		tgList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		tgList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		tgList.setLoadingIndicator(Utility.getLoadingWidget());
		
		TransactionDatabase.get().addDataDisplay(tgList);
		
		CentralEventHandler.addListener(new TransactionUpdateListener() {
			@Override
			public void transactionUpdatedEvent(TransactionDTO dto, int action) {
				PaneManager.renderDTOObject(dto);
				if(dto instanceof IPaidDTO) {
					
				} else {
					
				}
			}
		} );
	}

	private void placeObjects() {
		ScrollPanel sp = new ScrollPanel(tgList);
		sp.setHeight("100%");
		this.add(sp);
	}

	public class TransactionCell extends AbstractCell<TransactionDTO> {

		@Override
		public void render(Context context,
				TransactionDTO value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			String currencySymbol = OneTimeDataManager.getOTD().getCurrecnySymbol();
			String amountStr = Utility.get2DecimalAmount(value.getAmount());
			String formattedDate = Utility.getDateFormatted(value.getDate());
			String title = "You spent " + currencySymbol + " " + amountStr + " on " + formattedDate;
			String tgCellIconStyle = "tgCellIconOut";
			String heading = value.getName();
			String tgTableStyle = "tgCellTable";
			char entrySymbol = '►';
			
			if(TransactionDTO.ET_INWARD_TG == value.getEntryType()) {
				tgCellIconStyle = "tgCellIconIn";
				entrySymbol = '◄';
				title = "You recieved " + currencySymbol + " " + amountStr + " on " + formattedDate;
			}
			
			sb.appendHtmlConstant("<table align='center' class='" + tgTableStyle + "' cellspacing='0px' title='" + title + "'><tr>");
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle'><div class='" + tgCellIconStyle + "'>" + entrySymbol + "</div></td>");
			
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle' width='5%' style='padding-left: 5px;'><table cellspacing='0px'>");
			sb.appendHtmlConstant("<tr><td align='center' class='tgCellTDStyle' style='padding-left: 5px;'><div class='normalLabel' style='font-size: 12px;'>" +
					Utility.formatDate(value.getDate(), "MMM") + "</div></td></tr>");
			sb.appendHtmlConstant("<tr><td align='center' class='tgCellTDStyle' style='padding-left: 5px;'><div class='normalLabel' style='font-size: 20px;'>" +
					Utility.formatDate(value.getDate(), "dd") + "</div></td></tr></table></td>");
			
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle' width='58%'><div class='tgCellNormalLabel'>" + heading + "</div></td>");
			
			sb.appendHtmlConstant("<td align='center' class='tgCellTDStyle' width='7%'><div class='actions'> <table cellspacing='0px'>");
			sb.appendHtmlConstant("<tr><td align='center'><input type='button' class='mybutton small green' style='width: 45px;' value='View' onclick='txnRequest(" +
					value.getTransactionId() + ", " + value.getEntryType() + ", 1)' />");
			sb.appendHtmlConstant("</td></tr>");
			sb.appendHtmlConstant("<tr><td align='center'><input type='button' class='mybutton small red' style='width: 45px;' value='Delete' onclick='txnRequest(" +
					value.getTransactionId() + ", " + value.getEntryType() + ", 2)' />");
			sb.appendHtmlConstant("</td></tr></table></div></td>");
			
			String right = null, left = null;
			if(TransactionDTO.ET_INWARD_TG != value.getEntryType()) {
				right = amountStr;
				left = null;
			} else if(TransactionDTO.ET_OUTWARD_TG != value.getEntryType()) {
				left = amountStr;
				right = null;
			}
			
			for(int i = 0; i < 2; i++) {
				sb.appendHtmlConstant("<td align='right' class='tgCellTDStyle' width='15%'>");
				if((i == 0 && null != left)) {
					sb.appendHtmlConstant("<span class='normalLabel' style='padding-right: 5px;'>" +
							currencySymbol + " <span style='font-weight: bold;'>" + left + "</span></span>");
				} else if((i == 1 && null != right)) {
					sb.appendHtmlConstant("<span class='normalLabel' style='padding-right: 5px;'>" +
							currencySymbol + " <span style='font-weight: bold;'>" + right + "</span></span>");
				}
				sb.appendHtmlConstant("</td>");
			}
			
			sb.appendHtmlConstant("</tr></table>");
		}

	}

	public Long getTransactionGroupId() {
		return transactionGroupId;
	}

	public Long getActualId() {
		return actualId;
	}
	
	public void showDataForTransactionGroup(Long transactionGroupId, String tgName) {
		this.transactionGroupId = transactionGroupId;
		this.actualId = transactionGroupId;
		TransactionDatabase.get().refreshDisplays(transactionGroupId);
		
		if(null == transactionGroupId) {
			this.transactionGroupId = TransactionGroupDatabase.get().getDefault().getId();
		}
		
		if(null == tgName || -1 == transactionGroupId) {
			tgName = TransactionGroupDatabase.get().getTGAll().getTgName();
		}
		
		HTML h = new HTML();
		h.setStyleName(Constants.CSS_NORMAL_LABEL);
		h.setText(tgName);
		PaneManager.setInId(h, "tgCurrentName");
	}
}
