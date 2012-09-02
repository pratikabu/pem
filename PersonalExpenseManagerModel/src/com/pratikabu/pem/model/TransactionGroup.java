/**
 * 
 */
package com.pratikabu.pem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author pratsoni
 *
 */
@Entity
public class TransactionGroup {
	@Id
	@GeneratedValue
	private long txnGroupId;
	
	private String tgName;

	@OneToMany(targetEntity = TransactionTable.class, fetch = FetchType.LAZY,
			mappedBy = "transactionGroup", cascade = CascadeType.ALL)
	private List<TransactionTable> transactions;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private PEMUser user;

	public long getTxnGroupId() {
		return txnGroupId;
	}

	public void setTxnGroupId(long tripId) {
		this.txnGroupId = tripId;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tripName) {
		this.tgName = tripName;
	}

	public List<TransactionTable> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionTable> transactionGroups) {
		this.transactions = transactionGroups;
	}

	public PEMUser getUser() {
		return user;
	}

	public void setUser(PEMUser user) {
		this.user = user;
	}
}
