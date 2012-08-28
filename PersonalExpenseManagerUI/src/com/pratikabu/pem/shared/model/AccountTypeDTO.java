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
public class AccountTypeDTO implements Serializable {
	private String atCode, meaninig, description;

	public String getAtCode() {
		return atCode;
	}

	public void setAtCode(String atCode) {
		this.atCode = atCode;
	}

	public String getMeaninig() {
		return meaninig;
	}

	public void setMeaninig(String meaninig) {
		this.meaninig = meaninig;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
