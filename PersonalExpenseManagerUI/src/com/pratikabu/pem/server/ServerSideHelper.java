/**
 * 
 */
package com.pratikabu.pem.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratikabu.pem.client.dash.components.AccountTypeDatabase;
import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.utils.SearchHelper;

/**
 * @author pratsoni
 *
 */
public class ServerSideHelper {
	
	/**
	 * This method will return all the objects which are required once a user is created in the system.
	 * @param user
	 * @return
	 */
	public static List<Object> postUserCreationObjects(PEMUser user) {
		List<Object> toBeSaved = new ArrayList<Object>();
		
		// create myself account
		Account acc = new Account();
		acc.setAccName("Myself");
		acc.setCreationDate(new Date());
		acc.setEmail(PEMSecurity.decrypt(user.getEmail()));
		acc.setGender(user.getGender());
		acc.setUser(user);
		acc.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, AccountTypeDatabase.AT_PERSON, false));
		toBeSaved.add(acc);
		
		// create main balance account
		acc = new Account();
		acc.setAccName("Main Balance");
		acc.setCreationDate(new Date());
		acc.setUser(user);
		acc.setAccountType(SearchHelper.getFacade().readModelWithId(AccountType.class, AccountTypeDatabase.AT_MAIN, false));
		toBeSaved.add(acc);
		
		// create default transaction group entry
		TransactionGroup tg = new TransactionGroup();
		tg.setTripName("Default");
		tg.setUser(user);
		toBeSaved.add(tg);
		
		return toBeSaved;
	}
}
