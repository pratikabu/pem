/**
 * 
 */
package com.pratikabu.pem.model.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
@Embeddable
public class WebsiteDataPk implements Serializable {
	private int code;
	
	private String type;

	public WebsiteDataPk(int code, String type) {
		this.code = code;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
