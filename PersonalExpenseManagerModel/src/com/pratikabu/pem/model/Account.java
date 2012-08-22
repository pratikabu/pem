/**
 * 
 */
package com.pratikabu.pem.model;

import java.util.Date;

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
public class Account {
	
	@Id
	@GeneratedValue
	private long accountId;
	
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "atCode")
	private AccountType accountType;
	
	private String accName;
	private double currentBalance;
	
	private char gender;
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private PEMUser user;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PEMUser getUser() {
		return user;
	}

	public void setUser(PEMUser user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj || !(obj instanceof Account)) {
			return false;
		}
		
		return accountId == ((Account) obj).accountId;
	}
}
