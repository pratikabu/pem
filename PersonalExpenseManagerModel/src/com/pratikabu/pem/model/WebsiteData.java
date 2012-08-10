/**
 * 
 */
package com.pratikabu.pem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.pratikabu.pem.model.pk.WebsiteDataPk;

/**
 * @author pratsoni
 *
 */
@Entity
public class WebsiteData {
	@Id
	private WebsiteDataPk pk;
	
	private String meaning;

	public WebsiteDataPk getPk() {
		return pk;
	}

	public void setPk(WebsiteDataPk pk) {
		this.pk = pk;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
