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
public class TransactionGroup {
	@Id
	@GeneratedValue
	private long txnGrpId;
	
	private Date creationDate;
	private String tgName;
	private String notes;
	
	@ManyToOne
	@JoinColumn(name = "tripId")
	private Trip trip;
	
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

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
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
}
