/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.dash.ui.IPaidFormPanel;
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
		if(obj instanceof IPaidDTO) {
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
		RootPanel.get("txnExpandViewContainer").clear();
		RootPanel.get("txnExpandViewContainer").add(wid);
	}
	
	public static void createNewForm(int entryType) {
		if(TransactionDTO.ET_OUTWARD_TG == entryType) {
			getIpfp().renderRecord(null);
		}
	}
}
