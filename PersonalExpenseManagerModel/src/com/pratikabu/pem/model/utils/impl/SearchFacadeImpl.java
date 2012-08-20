/**
 * 
 */
package com.pratikabu.pem.model.utils.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pratikabu.pem.model.Account;
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

	@Override
	public <T> T readModelWithId(Class<T> c, Serializable primaryKey, boolean loadLazyObjects) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();
		T t = (T)session.get(c, primaryKey);
		
		loadLazyObjects(c, loadLazyObjects, t);
		
		session.getTransaction().commit();
		
		return t;
	}

	@SuppressWarnings("rawtypes")
	private <T> void loadLazyObjects(Class<T> c, boolean loadLazzyObjects, T t) {
		if(loadLazzyObjects && null != t) {
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
	}
	
	@Override
	public boolean saveModel(Object... toBeSaved) {
		if(null == toBeSaved) {
			return false;
		}
		
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();//to let know hibernate that the object below exists we have begin the transaction
		try {
			for(Object obj : toBeSaved) {
				session.saveOrUpdate(obj);// it does not saves it here
			}
			session.getTransaction().commit();//it saves the object here
			
			return true;
		} catch(HibernateException he) {
			logger.error("Exception while saving: " + toBeSaved, he);
			session.getTransaction().rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteModel(Object... toBeDeleted) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();//to let know hibernate that the object below exists we have begin the transaction
		try {
			for(Object obj : toBeDeleted) {
				session.delete(obj);//it does not delete it here
			}
			session.getTransaction().commit();//it deletes the object here
			
			return true;
		} catch(HibernateException he) {
			logger.error("Exception while deleting: " + toBeDeleted, he);
			session.getTransaction().rollback();
			return false;
		}
	}

	@Override
	public boolean saveDeleteModels(List<Object> toBeSaved, List<Object> toBeDeleted) {
		Session session = HibernateConfiguration.getFactory().getCurrentSession();
		session.beginTransaction();//to let know hibernate that the object below exists we have begin the transaction
		try {
			for(Object t : toBeSaved) {
				session.saveOrUpdate(t);//it does not save it here
			}
			for(Object t : toBeDeleted) {
				session.delete(t);//it does not delete it here
			}
			session.getTransaction().commit();//it deletes the object here
			
			return true;
		} catch(HibernateException he) {
			logger.error("Exception while saving and deleting.", he);
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
		List<TransactionTable> transactions = null;
		
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

	@Override
	public <T> List<T> readAllObjects(Class<T> c, boolean loadLazyObjects) {
		Session s = HibernateConfiguration.getFactory().getCurrentSession();
		s.beginTransaction();
		Query query = s.createQuery("FROM " + c.getSimpleName());
		List<T> list = query.list();
		
		if(loadLazyObjects && null != list) {
			for(T t : list) {
				loadLazyObjects(c, loadLazyObjects, t);
			}
		}
		
		s.getTransaction().commit();
		
		return list;
	}

	@Override
	public List<Account> getAccountsForUser(Serializable pemUserPK,
			int startPosition, int offset, boolean loadLazyData) {
		List<Account> accounts = null;
		
		if(null == pemUserPK) {
			return null;
		}
		
		long userId = (Long) pemUserPK;
		
		Session s = HibernateConfiguration.getFactory().getCurrentSession();
		s.beginTransaction();
		Query query = null;
		
		final String orderBy = " ORDER BY creationDate DESC";
		
		// show all the transactions for current user
		query = s.createQuery("FROM Account WHERE uid=:userId)" + orderBy).setLong("userId", userId);
		
		applyPagination(startPosition, offset, query);
		
		accounts = query.list();
		
		if(loadLazyData) {
			for(Account t : accounts) {
				loadLazyObjects(Account.class, loadLazyData, t);
			}
		}
		
		s.getTransaction().commit();
		
		return accounts;
	}

	@Override
	public List<Account> getAccountsForUserOfType(Serializable pemUserPK, String... accTypes) {
		List<Account> accounts = null;
		
		if(null == pemUserPK) {
			return null;
		}
		
		long userId = (Long) pemUserPK;
		
		Session s = HibernateConfiguration.getFactory().getCurrentSession();
		s.beginTransaction();
		
		accounts = s.createCriteria(Account.class).
				add(Restrictions.eq("user.uid", userId)).
				add(Restrictions.in("accountType.atCode", accTypes)).
				list();
		
		s.getTransaction().commit();
		
		return accounts;
	}
}
