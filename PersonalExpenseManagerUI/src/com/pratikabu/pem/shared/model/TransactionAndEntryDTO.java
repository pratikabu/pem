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
public class TransactionAndEntryDTO implements Serializable {
	private TransactionDTO transaction;
	
	private TransactionEntryDTO entry;

	public TransactionAndEntryDTO() {
	}

	public TransactionAndEntryDTO(TransactionDTO transaction,
			TransactionEntryDTO entry) {
		this.transaction = transaction;
		this.entry = entry;
	}

	public TransactionDTO getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionDTO transaction) {
		this.transaction = transaction;
	}

	public TransactionEntryDTO getEntry() {
		return entry;
	}

	public void setEntry(TransactionEntryDTO entry) {
		this.entry = entry;
	}
}
