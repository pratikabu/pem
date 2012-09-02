/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class UserSettingsDTO implements Serializable {
	private String currency;
	
	private boolean sendMonthly, sendNews;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isSendMonthly() {
		return sendMonthly;
	}

	public void setSendMonthly(boolean sendMonthly) {
		this.sendMonthly = sendMonthly;
	}

	public boolean isSendNews() {
		return sendNews;
	}

	public void setSendNews(boolean sendNews) {
		this.sendNews = sendNews;
	}
}
