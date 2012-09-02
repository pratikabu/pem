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
	private int type;
	
	private String code;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		
		if(obj instanceof WebsiteDataPk) {
			return type == ((WebsiteDataPk)obj).type &&
					code.equals(((WebsiteDataPk)obj).code);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		if(null == code) {
			return type;
		}
		
		return code.hashCode() + type;
	}
}
