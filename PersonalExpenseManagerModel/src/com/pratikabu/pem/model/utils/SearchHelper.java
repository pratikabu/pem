/**
 * 
 */
package com.pratikabu.pem.model.utils;

import com.pratikabu.pem.model.utils.impl.SearchFacadeImpl;

/**
 * @author pratsoni
 *
 */
public class SearchHelper {
	private static SearchFacade facade;
	
	static {
		facade = new SearchFacadeImpl();
	}
	
	public static SearchFacade getFacade() {
		return facade;
	}
}
