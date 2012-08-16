package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.service.ServiceHelper;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * 
 * @author pratsoni
 *
 */
public class TransactionReaderPanel extends HTML implements DetailPaneable {
	public final static String content = "<table cellspacing='0px' width='100%' style='background-color: #F8F8F8; border-bottom: 1px" +
			" SOLID #CBCBCB;'><tr> <td width='50%'><table cellspacing='0px' class='readerPTag'> <tr><td align='left'" +
			" class='readerHeader'>15 Aug, 2012</td></tr> <tr><td align='left' class='readerHeader'>Transaction Name," +
			" In which trip</td></tr> </table></td> <td width='50%' align='right' style='vertical-align: middle;'><table" +
			" cellspacing='0px' class='readerPTag'> <tr><td align='right'><input type='button' id='readerEdit' value='Edit'" +
			" class='actionButton' style='font-size: 12px; margin-right: 5px;' onclick='editTransaction()' /></td></tr> </table></td>" +
			" </tr></table>  <p class='readerPTag'> iPaid a total amount of <strong>$ 3000.00</strong> using <strong>Main Balance" +
			"</strong>.<br/> I spent it on: <a href='#'>entertainement</a>, <a href='#'>clothes</a>, <a href='#'>food</a><br/><br/>" +
			"  <span style='text-decoration: underline;'>Who all were there:</span> </p>  <table cellspacing='0px' class='readerPTag'>" +
			" <tr> <td width='150px' align='left'>Pratik (Me)</td> <td align='right'> $ 500.00</td> </tr> <tr> <td width='150px'" +
			" align='left'>User1</td> <td align='right'> $ 1500.00</td> </tr> <tr> <td width='150px' align='left'>User 2</td>" +
			" <td align='right'> $ 500.00</td> </tr> </table>  <p class='readerPTag'> <span style='text-decoration: underline;'>" +
			"Description:</span><br/> So we were about to go to the best institue in this world but fortunately we found this so" +
			" we enjoyed a very great dinner.  </p>";
	
	private static long transactionId;
	
	public TransactionReaderPanel() {
		exportStaticMethod();
	}
	
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
		long[] data = (long[]) obj;
		transactionId = data[0];
		
		PaneManager.setInReaderPane(Utility.getLoadingWidget());
		
		if(TransactionDTO.ET_OUTWARD_TG == data[1]) {
			ServiceHelper.getPemservice().getTransactionDetail((Long) transactionId, new AsyncCallback<IPaidDTO>() {
				@Override
				public void onSuccess(IPaidDTO result) {
					setHTML(Utility.getSafeHtml(content));
					PaneManager.setInReaderPane(TransactionReaderPanel.this);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error fetching transaction details.");
				}
			});
		}
	}

	public static void editTransaction() {
		PaneManager.editTransactionDetails(transactionId);
	}

	public static native void exportStaticMethod() /*-{
		$wnd.editTransaction = $entry(@com.pratikabu.pem.client.dash.ui.TransactionReaderPanel::editTransaction());
	}-*/;
	
}
