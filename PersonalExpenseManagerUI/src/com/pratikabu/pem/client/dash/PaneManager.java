/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.dash.ui.IPaidFormPanel;
import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;

/**
 * @author pratsoni
 *
 */
public class PaneManager {
	private static TransactionReaderPanel trp;
	private static IPaidFormPanel ipfp;
	
	public static void renderTransactionDetails(long txnId) {
		if(null == trp) {
			trp = new TransactionReaderPanel();
		}
		trp.renderRecord(txnId);
	}
	
	public static void editTransactionDetails(long txnId) {
		if(null == ipfp) {
			ipfp = new IPaidFormPanel();
		}
		setInReaderPane(ipfp);
		System.out.println("Editing transaction: " + txnId);
	}
	
	public static void setInReaderPane(Widget wid) {
		RootPanel.get("txnExpandViewContainer").clear();
		RootPanel.get("txnExpandViewContainer").add(wid);
	}
}
