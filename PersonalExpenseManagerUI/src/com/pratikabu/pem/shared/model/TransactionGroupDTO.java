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
public class TransactionGroupDTO implements Serializable {
	private Long id;
	private String tgName;
	private int noOfRecords;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String getTgNameWithCount() {
		return getTgName() + " (" + getNoOfRecords() + ")";
	}
}
