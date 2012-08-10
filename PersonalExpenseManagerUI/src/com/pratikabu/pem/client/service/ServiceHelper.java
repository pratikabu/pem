/**
 * 
 */
package com.pratikabu.pem.client.service;

import com.google.gwt.core.client.GWT;

/**
 * @author pratsoni
 *
 */
public class ServiceHelper {
	private static final PEMServiceAsync pemService = GWT.create(PEMService.class);

	public static PEMServiceAsync getPemservice() {
		return pemService;
	}
}
