package com.pratikabu.pem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author pratsoni
 *
 */
@Entity
public class AccountType {
	@Id
	private String atCode;
	
	private String meaning;

	public String getAtCode() {
		return atCode;
	}

	public void setAtCode(String atCode) {
		this.atCode = atCode;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
