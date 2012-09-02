/**
 * 
 */
package com.pratikabu.pem.client.dash.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.AccountTypeDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.UserSettingsDTO;

/**
 * @author pratsoni
 *
 */
public interface PEMServiceAsync {

	void getAllTransactionsForGroupId(Long groupId, int startPosition, int offset,
			AsyncCallback<ArrayList<TransactionDTO>> callback);

	void getTransactionDetail(Long transactionId,
			AsyncCallback<TransactionDTO> callback);

	void fetchOneTimeData(AsyncCallback<OneTimeData> callback);

	void getAllAccounts(AsyncCallback<ArrayList<AccountDTO>> callback);

	void saveTransaction(TransactionDTO dto, AsyncCallback<Long> callback);

	void getAllAccountTypes(AsyncCallback<ArrayList<AccountTypeDTO>> callback);

	void deleteTransactionGroup(Long tgId, AsyncCallback<Boolean> callback);

	void deleteTransaction(long transactionId, boolean deleteFullTransaction,
			AsyncCallback<Boolean> asyncCallback);

	void deleteAccount(long accountId, AsyncCallback<String> asyncCallback);

	void fetchWebsiteData(int type,
			AsyncCallback<LinkedHashMap<String, String>> asyncCallback);

	void fetchUserSettings(AsyncCallback<UserSettingsDTO> asyncCallback);

	void saveUserSettings(UserSettingsDTO dto,
			AsyncCallback<Boolean> asyncCallback);

}
