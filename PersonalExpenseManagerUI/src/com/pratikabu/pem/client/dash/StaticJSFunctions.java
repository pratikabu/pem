/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * @author pratsoni
 *
 */
public class StaticJSFunctions {

	public static native void exportStaticMethod() /*-{
		$wnd.editTransaction = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::editTransaction());
		$wnd.openRequest = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::openRequest(Ljava/lang/String;));
	}-*/;

	public static void editTransaction() {
		PaneManager.editDTOObject(TransactionReaderPanel.getiPaidDTO());
	}

	public static void openRequest(String toBeOpened) {
		if(null == toBeOpened) {
			return;
		}
		
		// First menu for transactions
		if("tnip".equals(toBeOpened)) {
			// open New iPaid
			PaneManager.createNewForm(TransactionDTO.ET_OUTWARD_TG);
		} else if("tnig".equals(toBeOpened)) {
			// open New iGot
		} else if("tnis".equals(toBeOpened)) {
			// open New iSaved
		} else if("tnsal".equals(toBeOpened)) {
			// open New Salary
		}
		
		// Second menu for Accounts
		else if("ama".equals(toBeOpened)) {
			// open Manage Accounts
		} else if("anprsn".equals(toBeOpened)) {
			// open New Person A/C
		} else if("anpay".equals(toBeOpened)) {
			// open New Payment A/C
		} else if("ansav".equals(toBeOpened)) {
			// open New Saving A/C
		}
		
		// third menu for Tools
		else if("toaccsetting".equals(toBeOpened)) {
			// open Account Settings
		} else if("toart".equals(toBeOpened)) {
			// open Add Recursive Transaction
		} else if("todtg".equals(toBeOpened)) {
			// open Default Transaction Group settings
		}
		
		// fourth menu for Reminders
		else if("ryourself".equals(toBeOpened)) {
			// open Remind Yourself
		} else if("rprsn".equals(toBeOpened)) {
			// open Remind Person
			
		}
	}
}
