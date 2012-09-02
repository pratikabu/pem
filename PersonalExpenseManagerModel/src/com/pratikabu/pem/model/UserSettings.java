/**
 * 
 */
package com.pratikabu.pem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author pratsoni
 *
 */
@Entity
public class UserSettings {
	@Id
	private long userId;
	
	private String currency;
	
	private boolean sendNewsLetter, senMonthlyUpdates;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isSendNewsLetter() {
		return sendNewsLetter;
	}

	public void setSendNewsLetter(boolean sendNewsLetter) {
		this.sendNewsLetter = sendNewsLetter;
	}

	public boolean isSenMonthlyUpdates() {
		return senMonthlyUpdates;
	}

	public void setSenMonthlyUpdates(boolean senMonthlyUpdates) {
		this.senMonthlyUpdates = senMonthlyUpdates;
	}
}
