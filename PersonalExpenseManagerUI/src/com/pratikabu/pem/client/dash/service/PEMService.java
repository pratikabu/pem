/**
 * 
 */
package com.pratikabu.pem.client.dash.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.AccountTypeDTO;
import com.pratikabu.pem.shared.model.FilterDTO;
import com.pratikabu.pem.shared.model.FilteredTransactionListData;
import com.pratikabu.pem.shared.model.TransactionAndEntryDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.UserSettingsDTO;

/**
 * @author pratsoni
 *
 */
@RemoteServiceRelativePath("pemService")
public interface PEMService extends RemoteService {
	
	/**
	 * @param groupId if it is null then all the transactions for the user will be fetched
	 * @return
	 */
	ArrayList<TransactionAndEntryDTO> getAllTransactionsForGroupId(Long groupId, FilterDTO filter, int startPosition, int offset);
	
	TransactionDTO getTransactionDetail(Long transactionId);
	
	OneTimeData fetchOneTimeData();
	
	ArrayList<AccountDTO> getAllAccounts();
	
	Long saveTransaction(TransactionDTO dto);
	
	ArrayList<AccountTypeDTO> getAllAccountTypes();

	boolean deleteTransactionGroup(Long tgId);

	boolean deleteTransaction(long transactionId, boolean deleteFullTransaction);

	String deleteAccount(long accountId);

	LinkedHashMap<String, String> fetchWebsiteData(int type);

	UserSettingsDTO fetchUserSettings();

	boolean saveUserSettings(UserSettingsDTO dto);

	FilteredTransactionListData getFilterInfo(Long transactionGroup,
			FilterDTO filter);
}
