package com.pratikabu.pem.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.server.PEMSecurity;

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
		// login
		response.setContentType("text/html");
		
		String encEmail = PEMSecurity.encrypt(request.getParameter("email"));
		String encPass = PEMSecurity.hashData(request.getParameter("password"));
		
		PEMUser user = SearchHelper.getFacade().getUserInfoFromEmail(encEmail);
		if(null != user && encPass.equals(user.getPassword())) {
			request.getSession().setAttribute("userId", user.getUid());
			response.getWriter().print("/Dashboard.jsp");
		} else {
			response.getWriter().print("INVALID");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String logout = request.getParameter("logout");
		
		if(null != logout) {
			request.getSession().removeAttribute("userId");
			request.getSession().invalidate();
			response.sendRedirect("index.jsp?logout=success");
		}
	}

}
