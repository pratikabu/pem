
package com.pratikabu.pem.client.dash;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window.Location;
import com.pratikabu.pem.client.dash.components.PaginationManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardEntryPoint implements EntryPoint {
	
	private static boolean showAccountInitBox;

	public void onModuleLoad() {
		StaticJSFunctions.exportStaticMethod();// load JS methods
		
		showAccountInitBox = Location.getParameter("showSetInitializeAccount") != null;
		
		if(showAccountInitBox) {
			StaticJSFunctions.openRequest("toaccsetting");
		} else {// load proper dashboard
			// place the SelectBox for fetching the max no records
			PaginationManager.get().placePaginationComponents();
			
			// The initialization of Dashboard happens after the OneTime fetch operation
			OneTimeDataManager.fetchOTD();
		}
	}

	public static boolean isShowAccountInitBox() {
		return showAccountInitBox;
	}
}
