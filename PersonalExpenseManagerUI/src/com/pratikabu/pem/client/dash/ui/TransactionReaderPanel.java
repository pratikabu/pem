package com.pratikabu.pem.client.dash.ui;

import java.util.Map.Entry;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * 
 * @author pratsoni
 *
 */
public class TransactionReaderPanel extends HTML implements DetailPaneable {
	private static IPaidDTO iPaidDTO;
	
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
		
		if(obj instanceof IPaidDTO) {
			IPaidDTO result = (IPaidDTO) obj;
			setHTML(Utility.getSafeHtml(getIPaidReaderHTML(result)));
			PaneManager.setInReaderPane(TransactionReaderPanel.this);
			TransactionReaderPanel.iPaidDTO = result;
		} else {
			long[] data = (long[]) obj;
			long transactionId = data[0];
			int entryType = (int)data[1];
			
			if(TransactionDTO.ET_OUTWARD_TG == entryType) {
				ServiceHelper.getPemservice().getTransactionDetail(transactionId, new AsyncCallback<IPaidDTO>() {
					@Override
					public void onSuccess(IPaidDTO result) {
						renderRecord(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						PaneManager.showEmptyArea();
						Utility.alert("Error fetching transaction details.");
					}
				});
			}
		}
	}
	
	private static String getIPaidReaderHTML(IPaidDTO d) {
		if(null == d) {
			return "<p class='readerPTag'>There is some problem fetchind data from the server.</p>";
		}
		
		String content = "<table cellspacing='0px' width='100%' style='background-color: #F8F8F8; border-bottom: 1px" +
				" SOLID #CBCBCB;'><tr> <td width='50%'><table cellspacing='0px' class='readerPTag'><tr><td align='left'" +
				" class='readerHeader'>"+ Utility.getDateFormatted(d.getDate()) + "</td></tr> <tr>" +
				"<td align='left' class='readerHeader'>" + d.getName() +
				", in " + d.getGroupName() + "</td></tr> </table></td> <td width='50%' align='right' style='vertical-align: middle;'>" +
				"<table cellspacing='0px' class='readerPTag'> <tr><td align='right'><input type='button' id='readerEdit' value='Edit'" +
				" class='actionButton' style='font-size: 12px; margin-right: 5px;' onclick='editTransaction()' /></td></tr> </table></td>" +
				" </tr></table><p class='readerPTag'> iPaid a total amount of <strong>" +
				Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(), d.getTotalAmount()) +
				"</strong> using <strong>" + d.getPaymentModeString() +
				"</strong>.<br/> I spent it on: ";
		
		boolean isFirst = true;
		for(String tag : d.getSelectedTags()) {
			if(isFirst) {
				isFirst = false;
			} else {
				content += ", ";
			}
			content += tag;
		}
		
		content += "<br/><br/>" +
				"  <span style='text-decoration: underline;'>Who all were there:</span></p><table cellspacing='0px' class='readerPTag'>";
				
		for(Entry<AccountDTO, Double> entry : d.getAmountDistribution().entrySet()) {
			content += "<tr><td width='150px' align='left' class='readerPTag'>" + entry.getKey().getAccountName() + "</td>" +
					"<td align='right' class='readerPTag'> " + Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(),
							entry.getValue()) + "</td></tr>";
		}
		
		content += "</table><p class='readerPTag'> <span style='text-decoration: underline;'>" +
				"Description:</span><br/>" + d.getNotes() + "</p>";
				
		return content;
	}

	public static IPaidDTO getiPaidDTO() {
		return iPaidDTO;
	}
	
}
