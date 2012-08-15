/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class TgCellData implements Serializable {
	public static final int ET_OUTWARD_TG = 1, ET_INWARD_TG = 2, ET_TRIP = 3;
	
	private long tgId;
	private String name;
	private int entryType = ET_INWARD_TG;
	private double amount;
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEntryType() {
		return entryType;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}

	public long getTgId() {
		return tgId;
	}

	public void setTgId(long tgId) {
		this.tgId = tgId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
