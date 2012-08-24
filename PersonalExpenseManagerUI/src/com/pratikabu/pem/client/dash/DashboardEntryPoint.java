package com.pratikabu.pem.client.dash;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardEntryPoint implements EntryPoint {
	public void onModuleLoad() {
		StaticJSFunctions.exportStaticMethod();// load JS methods
		
		// The initialization of Dashboard happens after the OneTime fetch operation
		OneTimeDataManager.fetchOTD();
	}
}
