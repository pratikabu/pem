/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.components.CentralEventHandler;
import com.pratikabu.pem.client.dash.components.CentralEventHandler.TransactionUpdateListener;
import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.client.dash.components.TransactionGroupDatabase;
import com.pratikabu.pem.shared.model.TransactionAndEntryDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionList extends VerticalPanel {
	private CellList<TransactionAndEntryDTO> tgList;
	
	private Long transactionGroupId;
	private Long actualTransactionGroupId;
	
	public TransactionList() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setWidth("100%");
		this.setHeight("100%");
		
		tgList = new CellList<TransactionAndEntryDTO>(new TransactionCell());

		tgList.setPageSize(30);
		tgList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		tgList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		tgList.setLoadingIndicator(Utility.getLoadingWidget());
		
		TransactionDatabase.get().addDataDisplay(tgList);
		
		CentralEventHandler.addListener(new TransactionUpdateListener() {
			@Override
			public void transactionUpdatedEvent(TransactionDTO dto, int action) {
				ViewerDialog.get().close();
				TransactionDatabase.get().refreshDisplays(null);
			}
		} );
	}

	private void placeObjects() {
		ScrollPanel sp = new ScrollPanel(tgList);
		sp.setHeight("100%");
		this.add(sp);
	}

	public class TransactionCell extends AbstractCell<TransactionAndEntryDTO> {

		@Override
		public void render(Context context,
				TransactionAndEntryDTO value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			TransactionDTO trans = value.getTransaction();
			TransactionEntryDTO ted = value.getEntry();
			
			String currencySymbol = OneTimeDataManager.getOTD().getCurrecnySymbol();
			String amountStr = Utility.get2DecimalAmount(ted.getAmount());
			String formattedDate = Utility.getDateFormatted(trans.getDate());
			String title = "You spent " + currencySymbol + " " + amountStr + " on " + formattedDate;
			String tgCellIconStyle = "tgCellIconOut";
			String heading = trans.getName();
			String tgTableStyle = "tgCellTable";
			char entrySymbol = '►';
			String helpingSentence = "Spent on <b>" + ted.getInwardAccount().getAccountName() + "</b> from <b>" +
					ted.getOutwardAccount().getAccountName() + "</b>.";
			
			if(TransactionDTO.ET_INWARD_TG == trans.getEntryType()) {
				tgCellIconStyle = "tgCellIconIn";
				entrySymbol = '◄';
				title = "You recieved " + currencySymbol + " " + amountStr + " on " + formattedDate;
				helpingSentence = "Received in <b>" + ted.getInwardAccount().getAccountName() + "</b> from <b>" +
						ted.getOutwardAccount().getAccountName() + "</b>.";
			}
			
			sb.appendHtmlConstant("<table align='center' class='" + tgTableStyle + "' cellspacing='0px' title='" + title + "'><tr>");
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle'><div class='" + tgCellIconStyle + "'>" + entrySymbol + "</div></td>");
			
			sb.appendHtmlConstant("<td align='center' class='tgCellTDStyle' width='5%'><span class='normalLabel' style='font-size: 12px;'>" +
					Utility.formatDate(trans.getDate(), "MMM") + "</span><br/><span class='normalLabel' style='font-size: 20px;'>" +
					Utility.formatDate(trans.getDate(), "dd") + "</span></td>");
			
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle' width='58%'><span class='tgCellNormalLabel'>" + heading +
					"</span><br/><span class='tgCellNormalLabel' style='font-size: 12px;'>" + helpingSentence + "</span></td>");
			
			sb.appendHtmlConstant("<td align='center' class='tgCellTDStyle' width='7%'><div class='actions'> <table cellspacing='0px'>");
			sb.appendHtmlConstant("<tr><td align='center'><input type='button' class='mybutton small green' style='width: 45px;' value='View' onclick='txnRequest(" +
					trans.getTransactionId() + ", 1)' />");
			sb.appendHtmlConstant("</td></tr>");
			sb.appendHtmlConstant("<tr><td align='center'><input type='button' class='mybutton small red' style='width: 45px;' value='Delete' onclick='txnRequest(" +
					ted.getTxnEntryId() + ", 2)' />");
			sb.appendHtmlConstant("</td></tr></table></div></td>");
			
			String right = null, left = null;
			if(TransactionDTO.ET_INWARD_TG != trans.getEntryType()) {
				right = amountStr;
				left = null;
			} else if(TransactionDTO.ET_OUTWARD_TG != trans.getEntryType()) {
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

	public Long getActualTransactionGroupId() {
		return actualTransactionGroupId;
	}
	
	public void showDataForTransactionGroup(Long transactionGroupId, String tgName) {
		this.transactionGroupId = transactionGroupId;
		this.actualTransactionGroupId = transactionGroupId;
		TransactionDatabase.get().refreshDisplays(transactionGroupId);
		
		if(null == transactionGroupId) {
			this.transactionGroupId = TransactionGroupDatabase.get().getDefault().getId();
		}
		
		if(null == tgName || -1 == transactionGroupId) {
			tgName = TransactionGroupDatabase.get().getTGAll().getTgName();
		}
	}
}
