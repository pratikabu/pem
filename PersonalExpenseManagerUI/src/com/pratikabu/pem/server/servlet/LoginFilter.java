package com.pratikabu.pem.server.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pratikabu.pem.server.PEMServiceImpl;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

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
        
        long pemUserId = PEMServiceImpl.getCurrentUser(session);
        String requestedURL = req.getRequestURL().toString();
        if(-1 != pemUserId) {
        	chain.doFilter(request, response);
        } else {
                HttpServletResponse res = (HttpServletResponse)response;
                String query = req.getQueryString();
                query = query == null ? "" : ("?" + query);
                requestedURL = requestedURL.substring(requestedURL.lastIndexOf('/') + 1) +  query;
                requestedURL = URLEncoder.encode(requestedURL, "UTF-8");
                res.sendRedirect("login.jsp?resource=" + requestedURL);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
