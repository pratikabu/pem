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
	public static final int WSD_COUNTRY = 1;
	public static final int WSD_CURRENCY = 2;
	
	public static final int ORDERBY_DESC = -1, ORDERBY_ASC = 1;
	
	public static final int PROJECTION_COUNT = 1, PROJECTION_SUM = 2;
	
	private static SearchFacade facade;
	
	static {
		facade = new SearchFacadeImpl();
	}
	
	public static SearchFacade getFacade() {
		return facade;
	}
}
