/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class TransactionEntryDTO implements Serializable {
	private long txnEntryId;
	
	private AccountDTO inwardAccount, outwardAccount;
	
	private double amount;

	public long getTxnEntryId() {
		return txnEntryId;
	}

	public void setTxnEntryId(long txnEntryId) {
		this.txnEntryId = txnEntryId;
	}

	public AccountDTO getInwardAccount() {
		return inwardAccount;
	}

	public void setInwardAccount(AccountDTO inwardAccount) {
		this.inwardAccount = inwardAccount;
	}

	public AccountDTO getOutwardAccount() {
		return outwardAccount;
	}

	public void setOutwardAccount(AccountDTO outwardAccount) {
		this.outwardAccount = outwardAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
