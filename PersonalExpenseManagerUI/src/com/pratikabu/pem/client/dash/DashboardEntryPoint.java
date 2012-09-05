package com.pratikabu.pem.client.dash;

import com.google.gwt.core.client.EntryPoint;
import com.pratikabu.pem.client.dash.components.PaginationManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardEntryPoint implements EntryPoint {
	
	public void onModuleLoad() {
		StaticJSFunctions.exportStaticMethod();// load JS methods
		
		// place the SelectBox for fetching the max no records
		PaginationManager.get().placePaginationComponents();
		
		// The initialization of Dashboard happens after the OneTime fetch operation
		OneTimeDataManager.fetchOTD();
	}
}
