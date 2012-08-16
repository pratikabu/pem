/**
 * 
 */
package com.pratikabu.pem.client.dash.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

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
	ArrayList<TransactionDTO> getAllTransactionsForGroupId(Long groupId);
	
	IPaidDTO getTransactionDetail(Long transactionId);
}
