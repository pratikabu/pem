/**
 * 
 */
package com.pratikabu.pem.model.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pratikabu.pem.model.WebsiteData;
import com.pratikabu.pem.model.pk.WebsiteDataPk;

/**
 * @author pratsoni
 *
 */
public class CountryCurrencyDataLoad {
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	private static Map<String, String> currMap = new LinkedHashMap<String, String>();
	
	static {
		map.put("af", "Afghanistan");
		map.put("ax", "Aland Islands");
		map.put("al", "Albania");
		map.put("dz", "Algeria");
		map.put("as", "American Samoa");
		map.put("ad", "Andorra");
		map.put("ao", "Angola");
		map.put("ai", "Anguilla");
		map.put("aq", "Antarctica");
		map.put("ag", "Antigua and Barbuda");
		map.put("ar", "Argentina");
		map.put("am", "Armenia");
		map.put("aw", "Aruba");
		map.put("au", "Australia");
		map.put("at", "Austria");
		map.put("az", "Azerbaijan");
		map.put("bs", "Bahamas");
		map.put("bh", "Bahrain");
		map.put("bd", "Bangladesh");
		map.put("bb", "Barbados");
		map.put("by", "Belarus");
		map.put("be", "Belgium");
		map.put("bz", "Belize");
		map.put("bj", "Benin");
		map.put("bm", "Bermuda");
		map.put("bt", "Bhutan");
		map.put("bo", "Bolivia");
		map.put("ba", "Bosnia and Herzegovina");
		map.put("bw", "Botswana");
		map.put("bv", "Bouvet Island");
		map.put("br", "Brazil");
		map.put("io", "British Indian Ocean Territory");
		map.put("vg", "British Virgin Islands");
		map.put("bn", "Brunei");
		map.put("bg", "Bulgaria");
		map.put("bf", "Burkina Faso");
		map.put("bi", "Burundi");
		map.put("kh", "Cambodia");
		map.put("cm", "Cameroon");
		map.put("ca", "Canada");
		map.put("cv", "Cape Verde");
		map.put("ky", "Cayman Islands");
		map.put("cf", "Central African Republic");
		map.put("td", "Chad");
		map.put("cl", "Chile");
		map.put("cn", "China");
		map.put("cx", "Christmas Island");
		map.put("cc", "Cocos (Keeling) Islands");
		map.put("co", "Colombia");
		map.put("km", "Union of the Comoros");
		map.put("cg", "Congo");
		map.put("ck", "Cook Islands");
		map.put("cr", "Costa Rica");
		map.put("hr", "Croatia");
		map.put("cu", "Cuba");
		map.put("cy", "Cyprus");
		map.put("cz", "Czech Republic");
		map.put("cd", "Democratic Republic of Congo");
		map.put("dk", "Denmark");
		map.put("xx", "Disputed Territory");
		map.put("dj", "Djibouti");
		map.put("dm", "Dominica");
		map.put("do", "Dominican Republic");
		map.put("tl", "East Timor");
		map.put("ec", "Ecuador");
		map.put("eg", "Egypt");
		map.put("sv", "El Salvador");
		map.put("gq", "Equatorial Guinea");
		map.put("er", "Eritrea");
		map.put("ee", "Estonia");
		map.put("et", "Ethiopia");
		map.put("fk", "Falkland Islands");
		map.put("fo", "Faroe Islands");
		map.put("fm", "Federated States of Micronesia");
		map.put("fj", "Fiji");
		map.put("fi", "Finland");
		map.put("fr", "France");
		map.put("gf", "French Guyana");
		map.put("pf", "French Polynesia");
		map.put("tf", "French Southern Territories");
		map.put("ga", "Gabon");
		map.put("gm", "Gambia");
		map.put("ge", "Georgia");
		map.put("de", "Germany");
		map.put("gh", "Ghana");
		map.put("gi", "Gibraltar");
		map.put("gr", "Greece");
		map.put("gl", "Greenland");
		map.put("gd", "Grenada");
		map.put("gp", "Guadeloupe");
		map.put("gu", "Guam");
		map.put("gt", "Guatemala");
		map.put("gn", "Guinea");
		map.put("gw", "Guinea-Bissau");
		map.put("gy", "Guyana");
		map.put("ht", "Haiti");
		map.put("hm", "Heard Island and McDonald Islands");
		map.put("hn", "Honduras");
		map.put("hk", "Hong Kong");
		map.put("hu", "Hungary");
		map.put("is", "Iceland");
		map.put("in", "India");
		map.put("id", "Indonesia");
		map.put("ir", "Iran");
		map.put("iq", "Iraq");
		map.put("xe", "Iraq-Saudi Arabia Neutral Zone");
		map.put("ie", "Ireland");
		map.put("il", "Israel");
		map.put("it", "Italy");
		map.put("ci", "Ivory Coast");
		map.put("jm", "Jamaica");
		map.put("jp", "Japan");
		map.put("jo", "Jordan");
		map.put("kz", "Kazakhstan");
		map.put("ke", "Kenya");
		map.put("ki", "Kiribati");
		map.put("kw", "Kuwait");
		map.put("kg", "Kyrgyz Republic");
		map.put("la", "Laos");
		map.put("lv", "Latvia");
		map.put("lb", "Lebanon");
		map.put("ls", "Lesotho");
		map.put("lr", "Liberia");
		map.put("ly", "Libya");
		map.put("li", "Liechtenstein");
		map.put("lt", "Lithuania");
		map.put("lu", "Luxembourg");
		map.put("mo", "Macau");
		map.put("mk", "Macedonia");
		map.put("mg", "Madagascar");
		map.put("mw", "Malawi");
		map.put("my", "Malaysia");
		map.put("mv", "Maldives");
		map.put("ml", "Mali");
		map.put("mt", "Malta");
		map.put("mh", "Marshall Islands");
		map.put("mq", "Martinique");
		map.put("mr", "Mauritania");
		map.put("mu", "Mauritius");
		map.put("yt", "Mayotte");
		map.put("mx", "Mexico");
		map.put("md", "Moldova");
		map.put("mc", "Monaco");
		map.put("mn", "Mongolia");
		map.put("ms", "Montserrat");
		map.put("ma", "Morocco");
		map.put("mz", "Mozambique");
		map.put("mm", "Myanmar");
		map.put("na", "Namibia");
		map.put("nr", "Nauru");
		map.put("np", "Nepal");
		map.put("nl", "Netherlands");
		map.put("an", "Netherlands Antilles");
		map.put("nc", "New Caledonia");
		map.put("nz", "New Zealand");
		map.put("ni", "Nicaragua");
		map.put("ne", "Niger");
		map.put("ng", "Nigeria");
		map.put("nu", "Niue");
		map.put("nf", "Norfolk Island");
		map.put("kp", "North Korea");
		map.put("mp", "Northern Mariana Islands");
		map.put("no", "Norway");
		map.put("om", "Oman");
		map.put("pk", "Pakistan");
		map.put("pw", "Palau");
		map.put("ps", "Palestinian Territories");
		map.put("pa", "Panama");
		map.put("pg", "Papua New Guinea");
		map.put("py", "Paraguay");
		map.put("pe", "Peru");
		map.put("ph", "Philippines");
		map.put("pn", "Pitcairn Islands");
		map.put("pl", "Poland");
		map.put("pt", "Portugal");
		map.put("pr", "Puerto Rico");
		map.put("qa", "Qatar");
		map.put("re", "Reunion");
		map.put("ro", "Romania");
		map.put("ru", "Russia");
		map.put("rw", "Rwanda");
		map.put("sh", "Saint Helena and Dependencies");
		map.put("kn", "Saint Kitts & Nevis");
		map.put("lc", "Saint Lucia");
		map.put("pm", "Saint Pierre and Miquelon");
		map.put("vc", "Saint Vincent and the Grenadines");
		map.put("ws", "Samoa");
		map.put("sm", "San Marino");
		map.put("st", "Sao Tome and Principe");
		map.put("sa", "Saudi Arabia");
		map.put("sn", "Senegal");
		map.put("sc", "Seychelles");
		map.put("sl", "Sierra Leone");
		map.put("sg", "Singapore");
		map.put("sk", "Slovakia");
		map.put("si", "Slovenia");
		map.put("sb", "Solomon Islands");
		map.put("so", "Somalia");
		map.put("za", "South Africa");
		map.put("gs", "South Georgia and the South Sandwich Islands");
		map.put("kr", "South Korea");
		map.put("es", "Spain");
		map.put("pi", "Spratly Islands");
		map.put("lk", "Sri Lanka");
		map.put("sd", "Sudan");
		map.put("sr", "Suriname");
		map.put("sj", "Svalbard and Jan Mayen Islands");
		map.put("sz", "Swaziland");
		map.put("se", "Sweden");
		map.put("ch", "Switzerland");
		map.put("sy", "Syria");
		map.put("tw", "Taiwan");
		map.put("tj", "Tajikistan");
		map.put("tz", "Tanzania");
		map.put("th", "Thailand");
		map.put("tg", "Togo");
		map.put("tk", "Tokelau");
		map.put("to", "Tonga");
		map.put("tt", "Trinidad and Tobago");
		map.put("tn", "Tunisia");
		map.put("tr", "Turkey");
		map.put("tm", "Turkmenistan");
		map.put("tc", "Turks and Caicos Islands");
		map.put("tv", "Tuvalu");
		map.put("ug", "Uganda");
		map.put("ua", "Ukraine");
		map.put("ae", "United Arab Emirates");
		map.put("uk", "United Kingdom");
		map.put("us", "United States");
		map.put("um", "United States Minor Outlying Islands");
		map.put("uy", "Uruguay");
		map.put("vi", "US Virgin Islands");
		map.put("uz", "Uzbekistan");
		map.put("vu", "Vanuatu");
		map.put("va", "Vatican City");
		map.put("ve", "Venezuela");
		map.put("vn", "Vietnam");
		map.put("wf", "Wallis and Futuna Islands");
		map.put("eh", "Western Sahara");
		map.put("ye", "Yemen");
		map.put("zm", "Zambia");
		map.put("zw", "Zimbabwe");
		map.put("rs", "Serbia");
		map.put("me", "Montenegro");
		
		// currency
		currMap.put("IN", "India - ₹");
		currMap.put("US", "United States - $");
		currMap.put("AR", "Argentina - $");
		currMap.put("AU", "Australia - $");
		currMap.put("AT", "Austria - €");
		currMap.put("BD", "Bangladesh - Tk");
		currMap.put("BE", "Belgium - €");
		currMap.put("BO", "Bolivia - Bs.");
		currMap.put("BR", "Brazil - R$");
		currMap.put("CA", "Canada - $");
		currMap.put("CL", "Chile - $");
		currMap.put("CN", "China - ¥");
		currMap.put("CO", "Columbia - $");
		currMap.put("HR", "Croatia - HRK");
		currMap.put("CZ", "Czech Republic - Kč");
		currMap.put("DK", "Denmark - kr");
		currMap.put("FR", "France - €");
		currMap.put("DE", "Germany - €");
		currMap.put("GB", "Great Britain - £");
		currMap.put("GR", "Greece - €");
		currMap.put("HU", "Hungary - Ft");
		currMap.put("IE", "Ireland - €");
		currMap.put("IL", "Israel - ₪");
		currMap.put("IT", "Italy - €");
		currMap.put("JP", "Japan - ¥");
		currMap.put("MY", "Malaysia - MYR");
		currMap.put("MX", "Mexico - $");
		currMap.put("NA", "Namibia - $");
		currMap.put("NL", "Netherlands - €");
		currMap.put("NZ", "New Zealand - $");
		currMap.put("PK", "Pakistan - Rs");
		currMap.put("PH", "Philippines - ₱");
		currMap.put("PL", "Poland - PLN");
		currMap.put("PT", "Portugal - €");
		currMap.put("PR", "Puerto Rico - $");
		currMap.put("RO", "Romania - RON");
		currMap.put("RU", "Russia - руб.");
		currMap.put("SA", "Saudi Arabia - SR");
		currMap.put("SG", "Singapore - $");
		currMap.put("ZA", "South Africa - R");
		currMap.put("KR", "South Korea - ₩");
		currMap.put("ES", "Spain - €");
		currMap.put("LK", "Sri Lanka - Rs.");
		currMap.put("SE", "Sweden - kr");
		currMap.put("CH", "Switzerland - Fr.");
		currMap.put("TW", "Taiwan - NT$");
		currMap.put("TR", "Turkey - TL");
		currMap.put("AE", "United Arab Emirates - DH");
		currMap.put("VE", "Venezuela - Bs");
	}
	
	public static void saveData() {
		List<Object> tobesaved = new ArrayList<Object>();
		
		for(Entry<String, String> entry : map.entrySet()) {
			WebsiteDataPk pk = new WebsiteDataPk();
			pk.setCode(entry.getKey());
			pk.setType(SearchHelper.WSD_COUNTRY);
			
			WebsiteData wd = new WebsiteData();
			wd.setMeaning(entry.getValue());
			wd.setPk(pk);
			
			tobesaved.add(wd);
		}
		
		for(Entry<String, String> entry : currMap.entrySet()) {
			WebsiteDataPk pk = new WebsiteDataPk();
			pk.setCode(entry.getKey());
			pk.setType(SearchHelper.WSD_CURRENCY);
			
			WebsiteData wd = new WebsiteData();
			wd.setMeaning(entry.getValue());
			wd.setPk(pk);
			
			tobesaved.add(wd);
		}
		
		SearchHelper.getFacade().saveModel(tobesaved.toArray());
	}
	
	public static void main(String[] args) {
		for(Entry<String, String> entry : currMap.entrySet()) {
			String str = "INSERT INTO WebsiteData(code, type, meaning) VALUES('" + entry.getKey() + "', 2, '" + entry.getValue() + "');";
			System.out.println(str);
		}
	}
}
