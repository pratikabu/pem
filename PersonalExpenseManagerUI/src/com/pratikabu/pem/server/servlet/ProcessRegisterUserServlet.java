package com.pratikabu.pem.server.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.utils.SearchHelper;

/**
 * Servlet implementation class ProcessAddressDetailsServlet
 */
public class ProcessRegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessRegisterUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, Integer.parseInt(request.getParameter("bdDay")));
		cal.set(Calendar.MONTH, Integer.parseInt(request.getParameter("bdMonth")));
		cal.set(Calendar.YEAR, Integer.parseInt(request.getParameter("bdYear")));
		
		PEMUser user = new PEMUser();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setGender(request.getParameter("gender").charAt(0));
		user.setBirthday(cal.getTime());
		user.setCity(request.getParameter("city"));
		
		response.setContentType("text/html");
		if(SearchHelper.getFacade().saveModel(user)) {
			// TODO send a mail with activation link for this account
			response.sendRedirect("proceed.jsp");
		} else {
			response.getWriter().print("INVALID");
		}
	}

}
