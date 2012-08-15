/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.user.client.ui.Widget;


/**
 * @author pratsoni
 *
 */
public interface DetailPaneable {
	
	boolean isSafeToClose();
	
	void closing();
	
	Widget getMainWidget();
	
	void renderRecord(Object obj);
}
