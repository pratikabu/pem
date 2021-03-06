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
public class FilteredTransactionListData implements Serializable {
	private int count;
	
	private double totalInwadAmount, totalOutwardAmount;

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
