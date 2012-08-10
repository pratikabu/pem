package com.pratikabu.pem.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratikabu.pem.client.service.PEMService;
import com.pratikabu.pem.shared.model.PEMUser;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PEMServiceImpl extends RemoteServiceServlet implements PEMService {

	@Override
	public void createUser(PEMUser user) {
		System.out.println(user);
	}
	
}
