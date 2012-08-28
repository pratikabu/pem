/**
 * 
 */
package com.pratikabu.pem.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class OneTimeData implements Serializable {
	private String currecnySymbol = "$";
	
	private ArrayList<String> tags;
	
	private ArrayList<AccountDTO> userSpecificPayableAccounts;
	
	private ArrayList<TransactionGroupDTO> transactionGroups;

	public String getCurrecnySymbol() {
		return currecnySymbol;
	}

	public void setCurrecnySymbol(String currecnySymbol) {
		this.currecnySymbol = currecnySymbol;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public ArrayList<AccountDTO> getUserSpecificPayableAccounts() {
		return userSpecificPayableAccounts;
	}

	public void setUserSpecificPayableAccounts(
			ArrayList<AccountDTO> userSpecificPayableAccounts) {
		this.userSpecificPayableAccounts = userSpecificPayableAccounts;
	}

	public ArrayList<TransactionGroupDTO> getTransactionGroups() {
		return transactionGroups;
	}

	public void setTransactionGroups(
			ArrayList<TransactionGroupDTO> transactionGroups) {
		this.transactionGroups = transactionGroups;
	}
}
