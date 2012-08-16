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
public class AccountDTO implements Serializable {
	private long accountId;
	
	private String accountName;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
