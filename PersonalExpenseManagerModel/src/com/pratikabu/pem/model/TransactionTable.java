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
	private long txnGrpId;
	
	private Date creationDate;
	private String tgName;
	private String notes;
	
	/** This should be of type outward = 2 or inward = 1 */
	private int entryType;
	
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "tripId")
	private TransactionGroup trip;
	
	@OneToMany(targetEntity = TransactionEntry.class, fetch = FetchType.LAZY,
			mappedBy = "transactionGroup", cascade = CascadeType.ALL)
	private List<TransactionEntry> transactionEntries;
	
	@ManyToMany
	@JoinTable(name = "JOIN_TXNGRP_TAG",
		joinColumns = {@JoinColumn(name = "txnGrpId")},
		inverseJoinColumns = {@JoinColumn(name = "tagName")}
	)
	private List<Tag> tags;

	public long getTxnGrpId() {
		return txnGrpId;
	}

	public void setTxnGrpId(long txnGrpId) {
		this.txnGrpId = txnGrpId;
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

	public TransactionGroup getTrip() {
		return trip;
	}

	public void setTrip(TransactionGroup trip) {
		this.trip = trip;
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

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public int getEntryType() {
		return entryType;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
