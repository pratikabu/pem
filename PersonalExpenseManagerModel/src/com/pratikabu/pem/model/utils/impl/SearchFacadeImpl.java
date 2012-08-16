/**
 * 
 */
package com.pratikabu.pem.model.utils.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.pratikabu.pem.model.PEMUser;
import com.pratikabu.pem.model.TransactionGroup;
import com.pratikabu.pem.model.TransactionTable;
import com.pratikabu.pem.model.utils.HibernateConfiguration;
import com.pratikabu.pem.model.utils.SearchFacade;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("unchecked")
public class SearchFacadeImpl implements SearchFacade {
	private Logger logger = Logger.getLogger(SearchFacadeImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public <T> T readModelWithId(Class<T> c, Serializable primaryKey, boolean loadLazzyObjects) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();
		T t = (T)session.get(c, primaryKey);
		
		if(loadLazzyObjects) {
			for(Method m : c.getMethods()) {
				if(m.getReturnType().getSimpleName().equals(List.class.getSimpleName())) {
					try {
						List l = (List)m.invoke(t, new Object[] {});
						l.size();
					} catch (Exception e) {
						logger.error("While invoking method on " + t, e);
					}
				}
			}
		}
		
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

	@Override
	public boolean isValidUser(String email, String password) {
		Session s = HibernateConfiguration.getFactory().getCurrentSession();
		s.beginTransaction();
		Query query = s.createQuery("FROM PEMUser WHERE email=:email");
		query.setString("email", email);
		PEMUser user = (PEMUser)query.uniqueResult();
		s.getTransaction().commit();
		
		return null != user && password.equals(user.getPassword());
	}

	@Override
	public List<TransactionTable> getTransactionsForUser(Serializable pemUserPK, Serializable transactionGroupPK,
			int startPosition, int offset, boolean loadLazyData) {
		List<TransactionTable> transactions = new ArrayList<TransactionTable>();
		
		if(null == pemUserPK) {
			return null;
		}
		
		long userId = (Long) pemUserPK;
		
		Session s = HibernateConfiguration.getFactory().getCurrentSession();
		s.beginTransaction();
		Query query = null;
		
		final String orderBy = " ORDER BY creationDate DESC";
		if(null != transactionGroupPK) {
			// check whether this transaction group pk belongs to the supplied user.
			query = s.createQuery("FROM TransactionGroup WHERE tripId=:tripId");
			query.setLong("tripId", (Long)transactionGroupPK);
			TransactionGroup tg = (TransactionGroup)query.uniqueResult();
			
			if(null != tg && tg.getUser().getUid() == userId) {
				query = s.createQuery("FROM TransactionTable WHERE tripId=:tripId" + orderBy);
				query.setLong("tripId", (Long)transactionGroupPK);
			} else {
				return null;
			}
		} else {
			// show all the transactions for current user
			query = s.createQuery("FROM TransactionTable WHERE tripId IN " +
					"(SELECT tripId FROM TransactionGroup WHERE uid=:userId)" + orderBy);
			query.setLong("userId", userId);
		}
		
		applyPagination(startPosition, offset, query);
		
		transactions = query.list();
		
		if(loadLazyData) {
			for(TransactionTable tt : transactions) {
				tt.getTags().size();
				tt.getTransactionEntries().size();
			}
		}
		
		s.getTransaction().commit();
		
		return transactions;
	}

	private void applyPagination(int startPosition, int offset, Query query) {
		// filter result if asked
		if(-1 != startPosition) {
			query.setFetchSize(offset);
			query.setFirstResult(startPosition);
		}
	}
}
