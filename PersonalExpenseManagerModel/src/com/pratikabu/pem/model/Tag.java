/**
 * 
 */
package com.pratikabu.pem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author pratsoni
 *
 */
@Entity
public class Tag {
	@Id
	private String tagName;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private PEMUser user;
	
	@ManyToMany
	@JoinTable(name = "JOIN_TXN_TAG",
		joinColumns = {@JoinColumn(name = "tagName")},
		inverseJoinColumns = {@JoinColumn(name = "txnId")}
	)
	private List<TransactionTable> transactionGroups;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public PEMUser getUser() {
		return user;
	}

	public void setUser(PEMUser user) {
		this.user = user;
	}

	public List<TransactionTable> getTransactionGroups() {
		return transactionGroups;
	}

	public void setTransactionGroups(List<TransactionTable> transactionGroups) {
		this.transactionGroups = transactionGroups;
	}
}
