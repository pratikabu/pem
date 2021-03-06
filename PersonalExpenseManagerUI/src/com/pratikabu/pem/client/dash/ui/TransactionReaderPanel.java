package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.OneTimeDataManager;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

/**
 * 
 * @author pratsoni
 *
 */
public class TransactionReaderPanel extends HTML implements DetailPaneable {
	private static TransactionDTO transaction;
	
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
		
		TransactionDTO result = null;
		
		if(obj instanceof Long) {
			result = TransactionDatabase.get().getTransactionDTO((Long)obj);
		} else {
			result = (TransactionDTO) obj;
		}
		
		String content = "";
		if(TransactionDTO.ET_OUTWARD_TG ==result.getEntryType()) {
			content = getIPaidReaderHTML(result);
		} else if(TransactionDTO.ET_INWARD_TG ==result.getEntryType()) {
			content = getIGotReaderHTML(result);
		}
		
		setHTML(Utility.getSafeHtml(content));
		ViewerDialog.showWidget(TransactionReaderPanel.this, "Transaction Details");
		TransactionReaderPanel.transaction = result;
		
		PaneManager.hideLoading();
	}

	private static String getIPaidReaderHTML(TransactionDTO dto) {
		if(null == dto) {
			return "<p class='readerPTag'>There is some problem fetchind data from the server.</p>";
		}
		
		String content = "<table cellspacing='0px' width='100%' style='background-color: #F8F8F8; border-bottom: 1px" +
				" SOLID #CBCBCB;'><tr> <td width='50%'><table cellspacing='0px' class='readerPTag'><tr><td align='left'" +
				" class='readerHeader'>"+ Utility.getDateFormatted(dto.getDate()) + "</td></tr> <tr>" +
				"<td align='left' class='readerHeader'>" + dto.getName() +
				"</td></tr> </table></td> <td width='50%' align='right' style='vertical-align: middle;'>" +
				"<table cellspacing='0px' class='readerPTag'> <tr><td align='right'><input type='button' id='readerDelete' value='Delete' " +
				"class='normalButton' style='font-size: 12px; margin-right: 5px;' onclick='deleteTransaction()' />" +
				"<input type='button' id='readerEdit' value='Edit'" +
				" class='actionButton' style='font-size: 12px; margin-right: 5px;' onclick='editTransaction()' /></td></tr> </table></td>" +
				" </tr></table><p class='readerPTag'> iPaid a total amount of <strong>" +
				Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(), dto.getTotalAmount()) +
				"</strong> using <strong>" + dto.getTransactionEntries().get(0).getOutwardAccount().getAccountName() +
				"</strong>.<br/> I spent it on: ";
		
		boolean isFirst = true;
		for(String tag : dto.getSelectedTags()) {
			if(isFirst) {
				isFirst = false;
			} else {
				content += ", ";
			}
			content += tag;
		}
		
		content += "<br/><br/>" +
				"  <span style='text-decoration: underline;'>Who all were there:</span></p><table cellspacing='0px' class='readerPTag'>";
				
		for(TransactionEntryDTO ted : dto.getTransactionEntries()) {
			content += "<tr><td width='150px' align='left' class='readerPTag'>" + ted.getInwardAccount().getAccountName() + "</td>" +
					"<td align='right' class='readerPTag'> " + Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(),
							ted.getAmount()) + "</td></tr>";
		}
		
		content += "</table><p class='readerPTag'> <span style='text-decoration: underline;'>" +
				"Description:</span><br/>" + dto.getNotes() + "</p>";
				
		return content;
	}
	
	private String getIGotReaderHTML(TransactionDTO dto) {
		if(null == dto) {
			return "<p class='readerPTag'>There is some problem fetchind data from the server.</p>";
		}
		
		TransactionEntryDTO ted = dto.getTransactionEntries().get(0);
		
		String content = "<table cellspacing='0px' width='100%' style='background-color: #F8F8F8; border-bottom: 1px" +
				" SOLID #CBCBCB;'><tr> <td width='50%'><table cellspacing='0px' class='readerPTag'><tr><td align='left'" +
				" class='readerHeader'>"+ Utility.getDateFormatted(dto.getDate()) + "</td></tr> <tr>" +
				"<td align='left' class='readerHeader'>" + dto.getName() +
				"</td></tr> </table></td> <td width='50%' align='right' style='vertical-align: middle;'>" +
				"<table cellspacing='0px' class='readerPTag'> <tr><td align='right'><input type='button' id='readerDelete' value='Delete' " +
				"class='normalButton' style='font-size: 12px; margin-right: 5px;' onclick='deleteTransaction()' />" +
				"<input type='button' id='readerEdit' value='Edit'" +
				" class='actionButton' style='font-size: 12px; margin-right: 5px;' onclick='editTransaction()' /></td></tr> </table></td>" +
				" </tr></table><p class='readerPTag'> iGot a total amount of <strong>" +
				Utility.getPrintableAmountWithSign(OneTimeDataManager.getOTD().getCurrecnySymbol(), dto.getTotalAmount()) +
				"</strong> from <strong>" + ted.getOutwardAccount().getAccountName() +
				"</strong> in <strong>" + ted.getInwardAccount().getAccountName() + "</strong>.<br/></p>";
		
		
		content += "<p class='readerPTag'> <span style='text-decoration: underline;'>" +
				"Description:</span><br/>" + dto.getNotes() + "</p>";
				
		return content;
	}

	public static TransactionDTO getTransaction() {
		return transaction;
	}
	
}
