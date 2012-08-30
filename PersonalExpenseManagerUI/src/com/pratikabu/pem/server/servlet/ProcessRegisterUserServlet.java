package com.pratikabu.pem.server.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.utils.SearchHelper;
import com.pratikabu.pem.server.PEMSecurity;
import com.pratikabu.pem.server.PEMServiceImpl;
import com.pratikabu.pem.server.ServerSideHelper;

/**
 * Servlet implementation class ProcessAddressDetailsServlet
 */
@SuppressWarnings("serial")
public class ProcessRegisterUserServlet extends HttpServlet {
       
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
		response.setContentType("text/html");
		String createWhat = request.getParameter(Constants.SERVLET_CREATE_WHAT);
		
		if(null == createWhat) {
			createNewUser(request, response);
		} else if("account".equals(createWhat)) {
			createNewAccount(request, response);
		} else if("transactionGroup".equals(createWhat)) {
			createNewTransactionGroup(request, response);
		}
	}

	private void createNewUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, Integer.parseInt(request.getParameter("bdDay")));
		cal.set(Calendar.MONTH, Integer.parseInt(request.getParameter("bdMonth")));
		cal.set(Calendar.YEAR, Integer.parseInt(request.getParameter("bdYear")));
		
		PEMUser user = new PEMUser();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setEmail(PEMSecurity.encrypt(user.getEmail()));// encrypt email
		user.setPassword(request.getParameter("password"));
		user.setPassword(PEMSecurity.hashData(user.getPassword()));// hash the password
		user.setGender(request.getParameter("gender").charAt(0));
		user.setBirthday(cal.getTime());
		user.setCity(request.getParameter("city"));
		
		List<Object> toBeSaved = ServerSideHelper.postUserCreationObjects(user);
		toBeSaved.add(user);
		
		if(SearchHelper.getFacade().saveModel(toBeSaved.toArray())) {
			// TODO send a mail with activation link for this account
			response.sendRedirect("proceed.jsp");
		} else {
			response.getWriter().print("INVALID");
		}
	}

	private void createNewAccount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long txnId = Long.parseLong(request.getParameter("accountId"));
		long uid = PEMServiceImpl.getCurrentUser(request.getSession());
		boolean userCorrect = true;
		
		Account acc;
		if(-1 == txnId) {
			acc = new Account();
			acc.setUser(SearchHelper.getFacade().readModelWithId(PEMUser.class, uid, false));
			acc.setCreationDate(new Date());
		} else {
			acc = SearchHelper.getFacade().readModelWithId(Account.class, txnId, false);
			userCorrect = uid == acc.getUser().getUid();
		}
		
		acc.setAccName(request.getParameter("accountName"));
		
		String accType = request.getParameter("accountType");
		System.out.println(accType);
		if(null == accType) {
			accType = request.getParameter("editAccType");
			System.out.println(accType);
		}
		acc.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class,
				accType, false));
		
		if(AccountTypeDatabase.AT_PERSON.equals(acc.getAccountType().getAtCode())) {
			acc.setGender(request.getParameter("gender").charAt(0));
			acc.setEmail(request.getParameter("mail"));
		}
		
		if(userCorrect && SearchHelper.getFacade().saveModel(acc)) {
			response.getWriter().print(acc.getAccountId() + "");
		} else {
			response.getWriter().print("INVALID");
		}
	}
	
	private void createNewTransactionGroup(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
			response.getWriter().print(tg.getTripId() + "");
		} else {
			response.getWriter().print("INVALID");
		}
	}

}
