/**
 * 
 */
package com.pratikabu.pem.model.utils.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pratikabu.pem.model.utils.HibernateConfiguration;
import com.pratikabu.pem.model.utils.SearchFacade;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("unchecked")
public class SearchFacadeImpl implements SearchFacade {
	private Logger logger = Logger.getLogger(SearchFacadeImpl.class);

	@Override
	public <T> T readModelWithId(Class<T> c, Serializable primaryKey) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();
		T t = (T)session.get(c, primaryKey);
		session.getTransaction().commit();
		
		return t;
	}
	
	@Override
	public <T> boolean saveModel(T... ts) {
		if(null == ts) {
			return false;
		}
		
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();//to let know hibernate that the object below exists we have begin the transaction
		try {
			for(T t : ts) {
				session.save(t);// it does not saves it here
			}
			session.getTransaction().commit();//it saves the object here
			
			return true;
		} catch(HibernateException he) {
			logger.error("Exception while saving: " + ts, he);
			session.getTransaction().rollback();
			return false;
		}
	}
	
	@Override
	public <T> boolean deleteModel(T t) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();//to let know hibernate that the object below exists we have begin the transaction
		try {
			session.delete(t);//it does not delete it here
			session.getTransaction().commit();//it deletes the object here
			
			return true;
		} catch(HibernateException he) {
			logger.error("Exception while deleting: " + t, he);
			session.getTransaction().rollback();
			return false;
		}
	}
}
