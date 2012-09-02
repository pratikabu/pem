/**
 * 
 */
package com.pratikabu.pem.model.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.TransactionTable;

/**
 * @author pratsoni
 *
 */
public interface SearchFacade {
	
	<T> T readModelWithId(Class<T> c, Serializable primaryKey, boolean loadLazyObjects);

	/**
	 * This method will save the model in the database.
	 * @param t
	 * @return
	 */
	boolean saveModel(Object... toBeSaved);
	
	/**
	 * This method will remove the 
	 * @param t
	 * @return
	 */
	boolean deleteModel(Object... toBeDeleted);
	
	/**
	 * This method will save and delete objects passed in the method.
	 * @param toBeSaved Objects to be saved.
	 * @param toBeDeleted Objects to be deleted.
	 * @return
	 */
	boolean saveDeleteModels(List<Object> toBeSaved, List<Object> toBeDeleted);
	
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
	
	/**
	 * It gives you freedom to fetch the list of particular type without any constraints.
	 * @param c
	 * @param loadLazyObjects
	 * @param pemUserPK
	 * @return
	 */
	<T> List<T> readAllObjects(Class<T> c, boolean loadLazyObjects, Serializable pemUserPK);
	
	/**
	 * Read all objects with the specified conditions
	 * @param c
	 * @param criteria
	 * @param loadLazyObjects
	 * @return
	 */
	<T> List<T> readAllObjects(Class<T> c, Map<String, Object> criteria, boolean loadLazyObjects);
	
	/**
	 * This method fetches user for the supplied user. It will fetch all kind of users.
	 * @param pemUserPK
	 * @param startPosition
	 * @param offset
	 * @param loadLazyData
	 * @return
	 */
	List<Account> getAccountsForUser(Serializable pemUserPK, int startPosition, int offset, boolean loadLazyData);

	List<Account> getAccountsForUserOfType(Serializable pemUserPK, String... accTypes);

	/**
	 * This method will return the count of all the records provided by the passed criteria.
	 * @param c
	 * @param criteria
	 * @return
	 */
	<T> int getCount(Class<T> c, Map<String, Object> criteria);
}
