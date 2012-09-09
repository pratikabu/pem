/**
 * 
 */
package com.pratikabu.pem.server.servlet;

import javax.servlet.http.HttpSession;

import com.pratikabu.pem.server.PEMServiceImpl;

/**
 * @author pratsoni
 *
 */
public class StaticHtmlResources {
	public static String getIconHeader(boolean bigIcon) {
		String html = "<a href=\"index.jsp\" " +
				"title=\"Because its your Money\"><img src=\"static/";
		
		if(bigIcon) {
			html += "bucks_big.png";
		} else {
			html += "bucks_small.png";
		}
		
		html += "\" /></a>";
		
		return html;
	}
	
	public static String getMenuHeader() {
		return "<div id='cssmenu'><ul>" +
				"<li><a href=\"privacyPolicy.jsp\"><span>Privacy Policy</span></a></li>" +
				"<li><a href=\"contactus.jsp\"><span>Contact Us</span></a></li>" +
				"<li><a href=\"aboutPEM.jsp\"><span>About PEM</span></a></li>" +
				"<li><a href=\"index.jsp\"><span>Home</span></a></li>" +
				"</ul></div>";
	}
	
	public static String getFooterHtml() {
		return "<div id=\"footerContainer\" align=\"center\" class=\"footerSlot\" style=\"height: 46px; padding-top: " +
				"20px; padding-bottom: 20px;\"> <table width=\"900px\" border=\"0\" class=\"footerText\"> <tr> <td width=" +
				"\"50%\" align=\"left\"> <a href=\"http://www.facebook.com/pratikabu\" title=\"Subscribe me on Facebook" +
				"\"><img src=\"static/facebook_16.png\" width=\"16\" height=\"16\"></a> <a href=\"http://twitter.com/#!/" +
				"pratikabu\" title=\"Follow me on Twitter\"><img src=\"static/twitter_16.png\" width=\"16\" height=\"16\"></a>" +
				"</td> <td width=\"50%\" align=\"right\"> <a href=\"#\">About</a> | <a href=\"#\">FAQ</a> | <a href=\"#" +
				"\">Privacy Policy</a> | <a href=\"#\">Mobile</a> | <a href=\"#\" class=\"tipholder\" title=\"" +
				"Google Chrome Web App\"><img src=\"static/chrome_16.png\" align=\"top\"><span style=\"" +
				"padding-left: 5px;\">Chrome Webstore</span></a> </td> </tr> </table> <span class=\"footerText\">&copy; 2012 " +
				"<a href=\"http://www.pratikabu.com\" title=\"Eighty_Coffee\">Eighty_Coffee</a>.</span> </div>";
	}
	
	public static String getLoggedInHtml(HttpSession session) {
		String str = "<span class=\"footerText\">";
		
		if(-1 == PEMServiceImpl.getCurrentUser(session)) {
			str += "<strong><a href=\"login.jsp\">Login</a></strong> | <strong><a href=\"login.jsp\">Join</a></strong>";
		} else {
			str += "Welcome to Bucks! <strong><a href=\"accounts.jsp\">Account</a></strong> | " +
					"<strong><a href=\"loginServlet?logout=true\">Logout</a></strong>";
		}
		
		str += "</span>";
		
		return str;
	}
}
