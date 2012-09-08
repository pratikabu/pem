package com.pratikabu.pem.server.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pratikabu.pem.model.UserSettings;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.server.PEMServiceImpl;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class AccountInitializeFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        
		boolean settingAvailable = false;
		
		String requestedURL = req.getQueryString();
		settingAvailable = requestedURL != null && requestedURL.contains("showSetInitializeAccount");
		
		if (!settingAvailable) {
			// pull the data from database
			settingAvailable = null != SearchHelper.getFacade().readModelWithId(
					UserSettings.class, PEMServiceImpl.getCurrentUser(session), false);
		}
        
        if(settingAvailable) {
        	chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse)response;
            res.sendRedirect("Dashboard.html?showSetInitializeAccount=true");
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
