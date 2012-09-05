/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class FilteredTransactionListData implements Serializable {
	private ArrayList<TransactionAndEntryDTO> transactionAndEntries;
	
	private int count;
	
	private double totalInwadAmount, totalOutwardAmount;

	public ArrayList<TransactionAndEntryDTO> getTransactionAndEntries() {
		return transactionAndEntries;
	}

	public void setTransactionAndEntries(ArrayList<TransactionAndEntryDTO> transactionAndEntries) {
		this.transactionAndEntries = transactionAndEntries;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotalInwadAmount() {
		return totalInwadAmount;
	}

	public void setTotalInwadAmount(double totalInwadAmount) {
		this.totalInwadAmount = totalInwadAmount;
	}

	public double getTotalOutwardAmount() {
		return totalOutwardAmount;
	}

	public void setTotalOutwardAmount(double totalOutwardAmount) {
		this.totalOutwardAmount = totalOutwardAmount;
	}
}
