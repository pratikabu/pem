/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionEntryDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionAndEntry {
	private TransactionDTO transaction;
	
	private TransactionEntryDTO entry;

	public TransactionAndEntry() {
	}

	public TransactionAndEntry(TransactionDTO transaction,
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
