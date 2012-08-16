package com.pratikabu.pem.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratikabu.pem.client.dash.service.PEMService;
import com.pratikabu.pem.model.TransactionTable;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.shared.model.IPaidDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PEMServiceImpl extends RemoteServiceServlet implements PEMService {

	@Override
	public ArrayList<TransactionDTO> getAllTransactionsForGroupId(Long groupId) {
		ArrayList<TransactionDTO> tdto = null;
		List<TransactionTable> tTables = SearchHelper.getFacade().getTransactionsForUser(1L, groupId, -1, -1, false);
		
		if(null != tTables) {
			tdto = new ArrayList<TransactionDTO>();
			for(TransactionTable tt : tTables) {
				TransactionDTO t = new TransactionDTO();
				t.setTransactionId(tt.getTxnGrpId());
				t.setAmount(0);
				t.setDate(tt.getCreationDate());
				t.setEntryType(tt.getEntryType());
				t.setName(tt.getTgName());
				
				tdto.add(t);
			}
		}
		
		return tdto;
	}

	@Override
	public IPaidDTO getTransactionDetail(Long transactionId) {
		TransactionTable tt = SearchHelper.getFacade().readModelWithId(TransactionTable.class, transactionId, true);
		
		if(null != tt) {
			// check for userId is same or not
			
			IPaidDTO d = new IPaidDTO();
			d.setTransactionId(transactionId);
			d.setNotes(tt.getNotes());
			d.setTransactionDate(tt.getCreationDate());
			d.setAmount(tt.getAmount());
			d.setTransactionName(tt.getTgName());
			System.out.println(tt.getTags());
			System.out.println(tt.getTransactionEntries());
		}
		
		return null;
	}
	
}
