/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
	private String paymentModeString;
	
	private double amount;
	
	private long groupId;
	private String groupName;
	
	private LinkedHashMap<AccountDTO, Double> savedAmountDistribution, newAmountDistribution;
	
	private LinkedHashMap<AccountDTO, Double> amountDistributionUpdated;

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

	public LinkedHashMap<AccountDTO, Double> getSavedAmountDistribution() {
		return savedAmountDistribution;
	}

	public void setSavedAmountDistribution(
			LinkedHashMap<AccountDTO, Double> amountDistribution) {
		this.savedAmountDistribution = amountDistribution;
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

	public String getPaymentModeString() {
		return paymentModeString;
	}

	public void setPaymentModeString(String paymentModeString) {
		this.paymentModeString = paymentModeString;
	}

	public LinkedHashMap<AccountDTO, Double> getAmountDistributionUpdated() {
		return amountDistributionUpdated;
	}

	public void setAmountDistributionUpdated(
			LinkedHashMap<AccountDTO, Double> amountDistributionUpdated) {
		this.amountDistributionUpdated = amountDistributionUpdated;
	}

	public LinkedHashMap<AccountDTO, Double> getNewAmountDistribution() {
		if(null == newAmountDistribution) {
			copySavedToNewDistribution();
		}
		return newAmountDistribution;
	}
	
	public void copySavedToNewDistribution() {
		newAmountDistribution = new LinkedHashMap<AccountDTO, Double>();
		
		if(null == savedAmountDistribution) {
			return;
		}
		
		// copy saved data to new structure
		for(Entry<AccountDTO, Double> entry : savedAmountDistribution.entrySet()) {
			newAmountDistribution.put(entry.getKey(), new Double(entry.getValue()));
		}
	}
}
