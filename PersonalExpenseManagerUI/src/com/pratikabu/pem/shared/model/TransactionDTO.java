/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class TransactionDTO implements Serializable {
	public static final int ET_OUTWARD_TG = 1, ET_INWARD_TG = 2;
	
	private long transactionId;
	private String name;
	private int entryType = ET_INWARD_TG;
	private double amount;
	private Date date;
	
	private String notes;
	
	private ArrayList<String> selectedTags;
	private ArrayList<TransactionEntryDTO> transactionEntries;
	
	private long groupId;
	private String groupName;

	public TransactionDTO() {
		selectedTags = new ArrayList<String>();
		transactionEntries = new ArrayList<TransactionEntryDTO>();
	}

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

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long tgId) {
		this.transactionId = tgId;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<String> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(ArrayList<String> selectedTags) {
		this.selectedTags = selectedTags;
	}

	public ArrayList<TransactionEntryDTO> getTransactionEntries() {
		return transactionEntries;
	}

	public void setTransactionEntries(
			ArrayList<TransactionEntryDTO> transactionEntries) {
		this.transactionEntries = transactionEntries;
	}

	public double getTotalAmount() {
		double ta = 0d;
		for(TransactionEntryDTO dto : transactionEntries) {
			ta += dto.getAmount();
		}
		
		return ta;
	}
}
