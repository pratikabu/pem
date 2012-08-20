/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class IPaidDTO extends TransactionDTO {
	private ArrayList<String> selectedTags;
	
	private long paymentMode;
	private String paymentModeString;
	
	private LinkedHashMap<AccountDTO, Double> amountDistribution;

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

	public LinkedHashMap<AccountDTO, Double> getAmountDistribution() {
		return amountDistribution;
	}

	public void setAmountDistribution(
			LinkedHashMap<AccountDTO, Double> amountDistribution) {
		this.amountDistribution = amountDistribution;
	}

	public String getPaymentModeString() {
		return paymentModeString;
	}

	public void setPaymentModeString(String paymentModeString) {
		this.paymentModeString = paymentModeString;
	}

	public double getTotalAmount() {
		double ta = 0d;
		if(null != amountDistribution) {
			for(Entry<AccountDTO, Double> entry : amountDistribution.entrySet()) {
				ta += entry.getValue();
			}
		}
		return ta;
	}
}
