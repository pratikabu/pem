package com.pratikabu.pem.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.server.PEMServiceImpl;

/**
 * Servlet implementation class ProcessAddressDetailsServlet
 */
public class TransactionGroupProcessingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionGroupProcessingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		long txnId = Long.parseLong(request.getParameter("txnId"));
		long uid = PEMServiceImpl.getCurrentUser(request.getSession());
		boolean userCorrect = true;
		
		TransactionGroup tg;
		if(-1 == txnId) {
			tg = new TransactionGroup();
			tg.setUser(SearchHelper.getFacade().readModelWithId(PEMUser.class, uid, false));
		} else {
			tg = SearchHelper.getFacade().readModelWithId(TransactionGroup.class, txnId, false);
			userCorrect = uid == tg.getUser().getUid();
		}
		
		tg.setTripName(request.getParameter("transactionGroupName"));
		
		response.setContentType("text/html");
		if(userCorrect && SearchHelper.getFacade().saveModel(tg)) {
			response.sendRedirect("SUCCESS");
		} else {
			response.getWriter().print("INVALID");
		}
	}

}
