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
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
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
		
		// Add a selection model so we can select cells.
		final SingleSelectionModel<TransactionDTO> selectionModel = new SingleSelectionModel<TransactionDTO>(
				TransactionDatabase.KEY_PROVIDER);
		tgList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				TransactionDTO dto = selectionModel.getSelectedObject();
				if(null == dto) {
					return;
				}
				PaneManager.renderTransactionDetails(dto.getTransactionId(), dto.getEntryType());
			}
		});
		
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
			String tgTableStyle = "tgCellTableOut";
			char entrySymbol = '►';
			
			if(TransactionDTO.ET_INWARD_TG == value.getEntryType()) {
				tgCellIconStyle = "tgCellIconIn";
				tgTableStyle = "tgCellTableIn";
				entrySymbol = '◄';
				title = "You recieved " + currencySymbol + " " + amountStr + " on " + formattedDate;
			}
//			else if(TransactionDTO.ET_TRIP == value.getEntryType()) {
//				tgCellIconStyle = "tgCellIconTrip";
//				tgTableStyle = "tgCellTableTrip";
//				entrySymbol = '♦';
//				
//				title = "You created a trip on " + formattedDate;
//			}
			
			sb.appendHtmlConstant("<table align='center' class='" + tgTableStyle + "' cellspacing='0px' title='" + title + "'><tr>");
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle'><div class='" + tgCellIconStyle + "'>" + entrySymbol + "</div></td>");
			sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle' width='70%'><div class='tgCellNormalLabel'>" +
					heading + "</div></td>");
			sb.appendHtmlConstant("<td align='center' class='tgCellTDStyle' width='30%'>");
			sb.appendHtmlConstant("<table align='center' cellspacing='0px' width='100%'><tr><td align='right'>");
//			if(TransactionDTO.ET_TRIP != value.getEntryType()) {
				sb.appendHtmlConstant("<span class='normalLabel' style='font-size: 12px;'>" + currencySymbol + " <span style='font-weight: bold;" +
						" padding-right: 5px;'>" + amountStr + "</span></span>");
				sb.appendHtmlConstant("</td></tr><tr><td align='right'>");
//			}
			sb.appendHtmlConstant("<span class='normalLabel' style='font-size: 10px; padding-right: 5px;'>" + formattedDate + "</span>");
			sb.appendHtmlConstant("</td></tr></table>");
			sb.appendHtmlConstant("</td></tr></table>");
		}

	}

	public Long getTransactionGroupId() {
		return transactionGroupId;
	}

	public void setTransactionGroupId(Long transactionGroupId) {
		this.transactionGroupId = transactionGroupId;
	}
	
	public void showDataForTransactionGroup(Long transactionGroupId) {
		this.transactionGroupId = transactionGroupId;
		TransactionDatabase.get().refreshDisplays(transactionGroupId);
		
		if(null == transactionGroupId) {
			this.transactionGroupId = TransactionGroupDatabase.get().getDefault().getId();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setSelection(TransactionDTO dto) {
		SingleSelectionModel<TransactionDTO> selectionModel = (SingleSelectionModel<TransactionDTO>)
				tgList.getSelectionModel();
		if(null != selectionModel.getSelectedObject()) {
			selectionModel.setSelected(selectionModel.getSelectedObject(), false);
		}
		
		if(null != dto) {
			selectionModel.setSelected(dto, true);
		}
	}
}