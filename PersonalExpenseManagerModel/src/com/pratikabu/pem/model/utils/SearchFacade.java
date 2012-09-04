/**
 * 
 */
package com.pratikabu.pem.model.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.pratikabu.pem.model.Account;

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
	 * It gives you freedom to fetch the list of particular type without any constraints.
	 * @param c
	 * @param loadLazyObjects
	 * @param pemUserPK
	 * @return
	 */
	<T> List<T> readAllObjects(Class<T> c, boolean loadLazyObjects, Serializable pemUserPK);
	
	/**
	 * Read all objects with the specified conditions.
	 * @param c
	 * @param criteria
	 * @param startPosition
	 * @param offset
	 * @param loadLazyObjects
	 * @param orderBy
	 * @return
	 */
	<T> List<T> readAllObjects(Class<T> c, Map<String, Object> criteria, boolean customCriteria, int startPosition, int offset,
			boolean loadLazyObjects , Map<String, Integer> orderBy);
	
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
	 * @param customOperation
	 * @return
	 */
	<T> int getCount(Class<T> c, Map<String, Object> criteria, boolean customOperation);
	
	/**
	 * Apply any projection on the property which you've passed.
	 * @param c
	 * @param criteria
	 * @param property on which you want to apply the projection. Not always required.
	 * @param projectionType See <code>SearchHelper.PROJECTION_*</code> properties.
	 * @param customOperation
	 * @return
	 */
	<T> Object getProjection(Class<T> c, Map<String, Object> criteria, String property, int projectionType, boolean customOperation);
}
