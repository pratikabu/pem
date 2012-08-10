/**
 * 
 */
package com.pratikabu.pem.model.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.pratikabu.pem.model.Account;
import com.pratikabu.pem.model.AccountType;
import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.Tag;
import com.pratikabu.pem.model.TransactionEntry;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.Trip;
import com.pratikabu.pem.model.WebsiteData;

/**
 * @author pratsoni
 *
 */
public class HibernateConfiguration {
	private static AnnotationConfiguration cfg;
	
	private static SessionFactory factory;

	/**
	 * This method returns the configuration object which is used for the current application.
	 * @return
	 */
	public static AnnotationConfiguration getCfg() {
		if(cfg == null) {
			cfg = new AnnotationConfiguration();
			cfg.addPackage("mws.model");
			populateModel();
			cfg.configure();
		}
		
		return cfg;
	}

	/**
	 * This method will return the heavy object of SessionFactory configured for the current instance of configuration.
	 * @return
	 */
	public static SessionFactory getFactory() {
		if(factory == null) {
			factory = getCfg().buildSessionFactory();
		}
		
		return factory;
	}
	
	private static void populateModel() {
		HibernateConfiguration.getCfg().addAnnotatedClass(AccountType.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(Account.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(TransactionEntry.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(TransactionGroup.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(Trip.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(Tag.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(PEMUser.class);
		HibernateConfiguration.getCfg().addAnnotatedClass(WebsiteData.class);
	}
}
