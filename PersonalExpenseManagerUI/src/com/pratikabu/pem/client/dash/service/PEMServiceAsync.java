/**
 * 
 */
package com.pratikabu.pem.client.dash.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;



/**
 * @author pratsoni
 *
 */
public interface PEMServiceAsync {

	void getAllTransactionsForGroupId(Long groupId,
			AsyncCallback<ArrayList<TransactionDTO>> callback);

	void getTransactionDetail(Long transactionId,
			AsyncCallback<IPaidDTO> callback);

	void fetchOneTimeData(AsyncCallback<OneTimeData> callback);

	void getAllAccounts(AsyncCallback<ArrayList<AccountDTO>> callback);

}
