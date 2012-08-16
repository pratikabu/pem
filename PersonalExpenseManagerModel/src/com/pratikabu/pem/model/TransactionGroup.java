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
	private long tripId;
	
	private String tripName;

	@OneToMany(targetEntity = TransactionTable.class, fetch = FetchType.LAZY,
			mappedBy = "trip", cascade = CascadeType.ALL)
	private List<TransactionTable> transactionGroups;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private PEMUser user;

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public List<TransactionTable> getTransactionGroups() {
		return transactionGroups;
	}

	public void setTransactionGroups(List<TransactionTable> transactionGroups) {
		this.transactionGroups = transactionGroups;
	}

	public PEMUser getUser() {
		return user;
	}

	public void setUser(PEMUser user) {
		this.user = user;
	}
}
