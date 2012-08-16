/**
 * 
 */
package com.pratikabu.pem.model.utils;

import java.io.Serializable;
import java.util.List;

import com.pratikabu.pem.model.TransactionTable;

/**
 * @author pratsoni
 *
 */
public interface SearchFacade {
	
	<T> T readModelWithId(Class<T> c, Serializable primaryKey, boolean loadLazzyObjects);

	/**
	 * This method will save the model in the database.
	 * @param t
	 * @return
	 */
	<T> boolean saveModel(T... t);
	
	/**
	 * This method will remove the 
	 * @param t
	 * @return
	 */
	<T> boolean deleteModel(T t);
	
	boolean isValidUser(String email, String password);
	
	/**
	 * This method will return a list of transactions for a particular user and for particular transactionGroup.
	 * The list will be sorted based on transaction date in descending order.
	 * @param pemUserPK PK of user for which we want the data.
	 * @param transactionGroupPK PK of the txn group for which we want the data. If null the all txn groups data will be returned.
	 * @param startPosition if you want pagination pass some non negative value, pass -1 if you don't want pagination to happen
	 * @param offset number of records to be fetched
	 * @param loadLazyData
	 * @return
	 */
	List<TransactionTable> getTransactionsForUser(Serializable pemUserPK, Serializable transactionGroupPK,
			int startPosition, int offset, boolean loadLazyData);
}
