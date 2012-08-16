/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class IPaidDTO implements Serializable {
	private long transactionId;
	
	private Date transactionDate;
	
	private String transactionName, notes;
	
	private ArrayList<String> selectedTags;
	
	private long paymentMode;
	
	private double amount;
	
	private LinkedHashMap<AccountDTO, Double> amountDistribution;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ArrayList<String> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(ArrayList<String> selectedTags) {
		this.selectedTags = selectedTags;
	}

	public long getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(long paymentMode) {
		this.paymentMode = paymentMode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LinkedHashMap<AccountDTO, Double> getAmountDistribution() {
		return amountDistribution;
	}

	public void setAmountDistribution(
			LinkedHashMap<AccountDTO, Double> amountDistribution) {
		this.amountDistribution = amountDistribution;
	}
}
