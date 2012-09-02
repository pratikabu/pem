/**
 * 
 */
package com.pratikabu.pem.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author pratsoni
 *
 */
@Entity
public class TransactionTable {
	@Id
	@GeneratedValue
	private long txnId;
	
	private Date creationDate;
	private String txnName;
	private String notes;
	
	/** This should be of type outward = 1 or inward = 2 */
	private int entryType;
	
	@ManyToOne
	@JoinColumn(name = "txnGroupId")
	private TransactionGroup transactionGroup;
	
	@OneToMany(targetEntity = TransactionEntry.class, fetch = FetchType.LAZY,
			mappedBy = "transaction", cascade = CascadeType.ALL)
	private List<TransactionEntry> transactionEntries;
	
	@ManyToMany
	@JoinTable(name = "JOIN_TXN_TAG",
		joinColumns = {@JoinColumn(name = "txnId")},
		inverseJoinColumns = {@JoinColumn(name = "tagName")}
	)
	private List<Tag> tags;

	public long getTxnId() {
		return txnId;
	}

	public void setTxnId(long txnGrpId) {
		this.txnId = txnGrpId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public TransactionGroup getTransactionGroup() {
		return transactionGroup;
	}

	public void setTransactionGroup(TransactionGroup transactionGroup) {
		this.transactionGroup = transactionGroup;
	}

	public List<TransactionEntry> getTransactionEntries() {
		return transactionEntries;
	}

	public void setTransactionEntries(List<TransactionEntry> transactionEntries) {
		this.transactionEntries = transactionEntries;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String tgName) {
		this.txnName = tgName;
	}

	public int getEntryType() {
		return entryType;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}
}
