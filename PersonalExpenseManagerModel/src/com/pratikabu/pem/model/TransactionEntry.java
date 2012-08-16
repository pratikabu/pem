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
	private long txnId;
	
	@ManyToOne
	@JoinColumn(name = "inwardAccount")
	private Account inwardAccount;
	
	@ManyToOne
	@JoinColumn(name = "outwardAccount")
	private Account outwardAccount;
	
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "txnGrpId")
	private TransactionTable transactionGroup;

	public long getTxnId() {
		return txnId;
	}

	public void setTxnId(long txnId) {
		this.txnId = txnId;
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

	public TransactionTable getTransactionGroup() {
		return transactionGroup;
	}

	public void setTransactionGroup(TransactionTable transactionGroup) {
		this.transactionGroup = transactionGroup;
	}
}
