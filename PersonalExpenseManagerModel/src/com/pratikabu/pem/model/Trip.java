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
public class Trip {
	@Id
	@GeneratedValue
	private long tripId;
	
	private String aboutTrip;

	@OneToMany(targetEntity = TransactionGroup.class, fetch = FetchType.LAZY,
			mappedBy = "trip", cascade = CascadeType.ALL)
	private List<TransactionGroup> transactionGroups;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private PEMUser user;

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public String getAboutTrip() {
		return aboutTrip;
	}

	public void setAboutTrip(String aboutTrip) {
		this.aboutTrip = aboutTrip;
	}

	public List<TransactionGroup> getTransactionGroups() {
		return transactionGroups;
	}

	public void setTransactionGroups(List<TransactionGroup> transactionGroups) {
		this.transactionGroups = transactionGroups;
	}

	public PEMUser getUser() {
		return user;
	}

	public void setUser(PEMUser user) {
		this.user = user;
	}
}
