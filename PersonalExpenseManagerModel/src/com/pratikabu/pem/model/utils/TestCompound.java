/**
 * 
 */
package com.pratikabu.pem.model.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.Tag;

/**
 * @author Pratik
 * Created on : 24-Apr-2011, 12:49:44 AM
 */
public class TestCompound {
	private static InitializerEvent event;
	
	public static void executeInitUpdate(InitializerEvent event) {
		try {
			TestCompound.event = event;
			
			print("initializing db...");
			initializeDB();
			print("db initializing done.");
			print("updating db...");
			updateData();
			print("db updation done.");
			
			print("loading country data...");
			CountryCurrencyDataLoad.saveData();
			print("country data loaded.");
			
			print("Successfully updated/initialized.");
		} catch(Throwable t) {
			print("Exception occured while executing the last step.");
			print("--- " + t.getMessage());
		}
	}

	/**
	 * @param args
	 */
	private static void initializeDB() {
		//to execute the configuration
		new SchemaExport(HibernateConfiguration.getCfg()).create(true, true);
		//create(true, true) script: to print sqls in sql logs, export: to send sqls to db server
		
		//to run sqls we need the session factory so that we can get session objects
		SessionFactory factory = HibernateConfiguration.getFactory();
		
		Session session = factory.getCurrentSession();
		
		//to let know hibernate that the object below exists we have begin the transaction
		session.beginTransaction();
		
//		session.save(acc);//it does not saves it here
		
		session.getTransaction().commit();//it saves the object here
	}
	
	private static void updateData() {
		Tag tag = null;
		
		tag = new Tag();
		tag.setTagName("General");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("Entertainment");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("Shopping");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("Travelling");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("Rent");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("TV/Internet");
		SearchHelper.getFacade().saveModel(tag);
		
		tag = new Tag();
		tag.setTagName("Groceries");
		SearchHelper.getFacade().saveModel(tag);
		
		//////////////Account Type
		AccountType at = null;
		
		at = new AccountType();
		at.setAtCode("main");
		at.setMeaning("Main Balance");
		at.setDescription("Main Balance");
		SearchHelper.getFacade().saveModel(at);
		
		at = new AccountType();
		at.setAtCode("credit");
		at.setMeaning("Credit Card");
		at.setDescription("Credit Card");
		SearchHelper.getFacade().saveModel(at);
		
		at = new AccountType();
		at.setAtCode("saving");
		at.setMeaning("Savings");
		at.setDescription("Savings");
		SearchHelper.getFacade().saveModel(at);
		
		at = new AccountType();
		at.setAtCode("person");
		at.setMeaning("Person");
		at.setDescription("Person");
		SearchHelper.getFacade().saveModel(at);
		
		at = new AccountType();
		at.setAtCode("other");
		at.setMeaning("Others");
		at.setDescription("Others");
		SearchHelper.getFacade().saveModel(at);
	}
	
	public static interface InitializerEvent {
		void executionOutput(String out);
	}

	private static void print(String output) {
		if(null != event) {
			event.executionOutput(output);
		}
	}

}
