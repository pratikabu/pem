package com.pratikabu.pem.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikabu.pem.model.utils.SearchHelper;

/**
 * Servlet implementation class ProcessAddressDetailsServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		if(SearchHelper.getFacade().isValidUser(request.getParameter("email"), request.getParameter("password"))) {
			response.getWriter().print("proceed.jsp");
		} else {
			response.getWriter().print("INVALID");
		}
	}

}
