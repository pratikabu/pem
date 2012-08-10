/**
 * 
 */
package com.pratikabu.pem.model.utils;

import java.io.Serializable;

/**
 * @author pratsoni
 *
 */
public interface SearchFacade {

	public <T> T readModelWithId(Class<T> c, Serializable primaryKey);
	
	/**
	 * This method will save the model in the database.
	 * @param t
	 * @return
	 */
	public <T> boolean saveModel(T... t);
	
	/**
	 * This method will remove the 
	 * @param t
	 * @return
	 */
	public <T> boolean deleteModel(T t);
}
