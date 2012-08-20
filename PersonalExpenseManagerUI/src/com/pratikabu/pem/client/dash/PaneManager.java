/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.dash.ui.IPaidFormPanel;
import com.pratikabu.pem.client.dash.ui.TransactionList;
import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;
import com.pratikabu.pem.shared.model.IPaidDTO;
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

	public static void renderTransactionDetails(long txnId, int entryType) {
		getTrp().renderRecord(new long[] {txnId, entryType});
	}
	
	public static void renderDTOObject(Object obj) {
		if(null == obj) {
			showEmptyArea();
		} else if(obj instanceof IPaidDTO) {
			getTrp().renderRecord(obj);
		} else {
			
		}
	}
	
	public static void editTransactionDetails(long txnId, int entryType) {
		if(TransactionDTO.ET_OUTWARD_TG == entryType) {
			getIpfp().renderRecord(txnId);
		} else if(TransactionDTO.ET_INWARD_TG == entryType) {
			
		}
	}
	
	public static void editDTOObject(Object obj) {
		if(obj instanceof IPaidDTO) {
			getIpfp().renderRecord(obj);
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
	
	public static void showEmptyArea() {
		setInReaderPane(null);
	}
	
	public static void setInTListPane(Widget wid) {
		setInId(wid, "txnListContainer");
	}
	
	public static void setInId(Widget wid, String id) {
		RootPanel.get(id).clear();
		RootPanel.get(id).add(wid);
	}
}
