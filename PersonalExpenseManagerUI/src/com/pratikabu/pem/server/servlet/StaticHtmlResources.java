/**
 * 
 */
package com.pratikabu.pem.server.servlet;

/**
 * @author pratsoni
 *
 */
public class StaticHtmlResources {
	public static String getIconHeader(boolean bigIcon) {
		String html = "<table style=\"width: 100%;\"><tr><td width=\"50%\" align=\"left\"><a href=\"index.html\" " +
				"title=\"Because its your Money\"><img src=\"static/";
		
		if(bigIcon) {
			html += "bucks_big.png";
		} else {
			html += "bucks_small.png";
		}
		
		html += "\" /></a></td><td width=\"50%\" align=\"right\"><a href=\"http://www.pratikabu.com\" title=\"Eighty_Coffee\"> " +
				"<img src=\"static/E_C_grey.png\" onmouseout=\"this.src='static/E_C_grey.png'\" onmouseover=\"this.src='static/" +
				"E_C_red.png'\"></a></td> </tr> </table>";
		
		return html;
	}
	
	public static String getMenuHeader() {
		return "<div id='cssmenu'> <ul><li><a href='/index.jsp'><span>Home</span></a></li>" +
				"<li><a href='/aboutPEM.jsp'><span>About PEM</span></a></li><li><a href='/contactus.jsp'>" +
				"<span>Contact Us</span></a></li><li><a href='/privacyPolicy.jsp'><span>Privacy Policy</span>" +
				"</a></li></ul></div>";
	}
}
