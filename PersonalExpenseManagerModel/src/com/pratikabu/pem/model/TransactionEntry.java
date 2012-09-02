/**
 * 
 */
package com.pratikabu.pem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author pratsoni
 *
 */
@Entity
public class TransactionEntry {
	
	@Id
	@GeneratedValue
	private long txnEntryId;
	
	@ManyToOne
	@JoinColumn(name = "inwardAccount")
	private Account inwardAccount;
	
	@ManyToOne
	@JoinColumn(name = "outwardAccount")
	private Account outwardAccount;
	
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "txnId")
	private TransactionTable transaction;

	public long getTxnEntryId() {
		return txnEntryId;
	}

	public void setTxnEntryId(long txnEntryId) {
		this.txnEntryId = txnEntryId;
	}

	public Account getInwardAccount() {
		return inwardAccount;
	}

	public void setInwardAccount(Account inwardAccount) {
		this.inwardAccount = inwardAccount;
	}

	public Account getOutwardAccount() {
		return outwardAccount;
	}

	public void setOutwardAccount(Account outwardAccount) {
		this.outwardAccount = outwardAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionTable getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionTable transaction) {
		this.transaction = transaction;
	}
}
