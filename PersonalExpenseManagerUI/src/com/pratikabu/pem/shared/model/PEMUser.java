/**
 * 
 */
package com.pratikabu.pem.shared.model;

import java.io.Serializable;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class PEMUser implements Serializable {
	
	/**
	 * uid is created because uid will be stored in multiple table
	 * since email is a string it will take more space. so converting it to a long id.
	 */
	private long uid;
	
	// unique id
	private String email;
	
	private String firstName, lastName;
	
	private char gender;
	
	private int bdMonth, bdDate, bdYear;
	
	private String city, country;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getBdMonth() {
		return bdMonth;
	}

	public void setBdMonth(int bdMonth) {
		this.bdMonth = bdMonth;
	}

	public int getBdDate() {
		return bdDate;
	}

	public void setBdDate(int bdDate) {
		this.bdDate = bdDate;
	}

	public int getBdYear() {
		return bdYear;
	}

	public void setBdYear(int bdYear) {
		this.bdYear = bdYear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "PEMUser [uid=" + uid + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", bdMonth=" + bdMonth + ", bdDate=" + bdDate + ", bdYear="
				+ bdYear + ", city=" + city + ", country=" + country + "]";
	}
}
