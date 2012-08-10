/**
 * 
 */
package com.pratikabu.pem.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratikabu.pem.shared.model.PEMUser;

/**
 * @author pratsoni
 *
 */
@RemoteServiceRelativePath("pemService")
public interface PEMService extends RemoteService {
	void createUser(PEMUser user);
}
