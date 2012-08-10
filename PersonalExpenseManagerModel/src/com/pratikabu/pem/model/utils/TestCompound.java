/**
 * 
 */
package com.pratikabu.pem.model.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * @author Pratik
 * Created on : 24-Apr-2011, 12:49:44 AM
 */
public class TestCompound {
	public static void main(String[] args) {
		initializeDB();
		updateData();
		
		System.out.println("Successfully updated/initialized.");
	}

	/**
	 * @param args d
	 */
	public static void initializeDB() {
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
	
	public static void updateData() {
	}

}
