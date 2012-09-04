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
public class FilterDTO implements Serializable {
	private Date startingDate, endingDate;

	// inward, outward, both
	/** Use TransactionDTO directions */
	private int direction;
	
	private ArrayList<AccountDTO> selectedAccounts;
	
	public FilterDTO() {
		selectedAccounts = new ArrayList<AccountDTO>();
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public ArrayList<AccountDTO> getSelectedAccounts() {
		return selectedAccounts;
	}

	public void setSelectedAccounts(ArrayList<AccountDTO> selectedAccounts) {
		this.selectedAccounts = selectedAccounts;
	}
}
