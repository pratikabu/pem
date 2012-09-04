/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.ui.IPaidFormPanel;
import com.pratikabu.pem.client.dash.ui.TransactionList;
import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;
import com.pratikabu.pem.client.dash.ui.ViewerDialog;
import com.pratikabu.pem.shared.model.FilteredTransactionListData;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * @author pratsoni
 *
 */
public class PaneManager {
	private static TransactionReaderPanel trp;
	private static IPaidFormPanel ipfp;
	private static TransactionList tList;
	
	public static TransactionList gettList() {
		if(null == tList) {
			tList = new TransactionList();
		}
		
		return tList;
	}

	public static TransactionReaderPanel getTrp() {
		if(null == trp) {
			trp = new TransactionReaderPanel();
		}
		return trp;
	}

	public static IPaidFormPanel getIpfp() {
		if(null == ipfp) {
			ipfp = new IPaidFormPanel();
		}
		return ipfp;
	}

	public static void renderTransactionDetails(long txnId) {
		getTrp().renderRecord(txnId);
	}
	
	public static void renderTransaction(TransactionDTO dto) {
		if(null == dto) {
			ViewerDialog.get().close();
		} else if(TransactionDTO.ET_OUTWARD_TG == dto.getEntryType()) {
			getTrp().renderRecord(dto);
		} else {
			
		}
	}
	
	public static void editTransactionDetails(long txnId, int entryType) {
		if(TransactionDTO.ET_OUTWARD_TG == entryType) {
			getIpfp().renderRecord(txnId);
		} else if(TransactionDTO.ET_INWARD_TG == entryType) {
			
		}
	}
	
	public static void editTransaction(TransactionDTO dto) {
		if(TransactionDTO.ET_OUTWARD_TG == dto.getEntryType()) {
			getIpfp().renderRecord(dto);
		} else {
			
		}
	}
	
	public static void setInReaderPane(Widget wid) {
		setInId(wid, "txnExpandViewContainer");
	}
	
	public static void createNewForm(int entryType) {
		if(TransactionDTO.ET_OUTWARD_TG == entryType) {
			getIpfp().renderRecord(null);
		}
	}
	
	public static void setInTListPane(Widget wid) {
		setInId(wid, "txnListContainer");
	}
	
	public static void setInId(Widget wid, String id) {
		RootPanel.get(id).clear();
		
		if(null != wid) {
			RootPanel.get(id).add(wid);
		}
	}

	public static void showLoading() {
		// TODO Auto-generated method stub
		
	}

	public static void hideLoading() {
		// TODO Auto-generated method stub
		
	}

	public static void updateBalance(FilteredTransactionListData result, int countOfTransactionEntries) {
		setInId(getTotalAmountHtml(result.getTotalInwadAmount()), "leftTotal");
		setInId(getTotalAmountHtml(result.getTotalOutwardAmount()), "rightTotal");
		
		String entries = "Entr", transactions = "Transaction";
		
		if(countOfTransactionEntries > 1) {
			entries += "ies";
		} else {
			entries += "y";
		}
		
		if(result.getCount() > 1) {
			transactions += "s";
		}
		
		HTML h = new HTML();
		h.setStyleName(Constants.CSS_NORMAL_LABEL);
		h.setText("Showing " + countOfTransactionEntries + " " + entries + " from " +
		result.getCount() + " " + transactions + ". This result is filtered.");
		setInId(h, "tgCurrentName");
	}

	private static HTML getTotalAmountHtml(double amount) {
		HTML h = new HTML();
		h.setHeight("100%");
		h.setWidth("100%");
//		h.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		h.setHTML(Utility.getSafeHtml(OneTimeDataManager.getOTD().getCurrecnySymbol() +
				" <b>" + Utility.get2DecimalAmount(amount) + "</b>"));
		return h;
	}
}
