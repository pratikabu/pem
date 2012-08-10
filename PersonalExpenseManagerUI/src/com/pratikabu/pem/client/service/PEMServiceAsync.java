/**
 * 
 */
package com.pratikabu.pem.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratikabu.pem.shared.model.PEMUser;


/**
 * @author pratsoni
 *
 */
public interface PEMServiceAsync {

	void createUser(PEMUser user, AsyncCallback<Void> callback);
	
}
