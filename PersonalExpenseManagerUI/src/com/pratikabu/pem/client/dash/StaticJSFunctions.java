/**
 * 
 */
package com.pratikabu.pem.client.dash;

import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.client.dash.ui.AccountDialog;
import com.pratikabu.pem.client.dash.ui.AccountManagerDialog;
import com.pratikabu.pem.client.dash.ui.FilterPanel;
import com.pratikabu.pem.client.dash.ui.TransactionReaderPanel;
import com.pratikabu.pem.client.dash.ui.UserSettingsPanel;
import com.pratikabu.pem.client.dash.ui.ViewerDialog;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * @author pratsoni
 *
 */
public class StaticJSFunctions {

	public static native void exportStaticMethod() /*-{
		$wnd.editTransaction = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::editTransaction());
		$wnd.deleteTransaction = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::deleteTransaction());
		$wnd.openRequest = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::openRequest(Ljava/lang/String;));
		$wnd.txnRequest = $entry(@com.pratikabu.pem.client.dash.StaticJSFunctions::txnRequest(DI));
	}-*/;

	public static void editTransaction() {
		PaneManager.editTransaction(TransactionReaderPanel.getTransaction());
	}

	public static void deleteTransaction() {
		TransactionDatabase.deleteSelected();
	}
	
	/**
	 * 
	 * @param txnId
	 * @param operation 1= View, 2= Delete
	 */
	public static void txnRequest(double txnId, int operation) {
		long transactionId = (long) txnId;
		if(1 == operation) {// view
			PaneManager.renderTransactionDetails(transactionId);
		} else if(2 == operation) {// delete
			// transactionId is actually txnEntryId
			TransactionDatabase.deleteT(transactionId, false);
		}
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
			PaneManager.createNewForm(TransactionDTO.ET_INWARD_TG);
		} else if("tnis".equals(toBeOpened)) {
			// open New iSaved
		} else if("tnsal".equals(toBeOpened)) {
			// open New Salary
		}
		
		// Second menu for Accounts
		else if("ama".equals(toBeOpened)) {
			// open Manage Accounts
			AccountManagerDialog.showManager();
		} else if("anacc".equals(toBeOpened)) {
			// open New Person A/C
			AccountDialog.show(null);
		}
		
		// third menu for Tools
		else if("toaccsetting".equals(toBeOpened)) {
			// open Account Settings
			ViewerDialog.showWidget(UserSettingsPanel.get(), "User specific settings");
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
		
		// for filter
		else if("filter".equals(toBeOpened)) {
			ViewerDialog.showWidget(FilterPanel.get(), "Filter Options");
		}
	}
}
