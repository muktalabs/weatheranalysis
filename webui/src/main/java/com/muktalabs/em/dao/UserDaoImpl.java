package com.muktalabs.em.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.muktalabs.em.model.User;

@Service
public class UserDaoImpl  implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	private Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

	@Override
	@Transactional
	public int saveOrUpdate(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		Serializable id = session.getIdentifier(user);
		session.close();
		return (Integer) id;
	}

	@Override
	public List<User> list() {
		
		
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}

	@Override
	public List<User> list(User criteria) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}
 
	@Override
	public List<User> list(String filterColumnName, 
			String filterColumnValue) {
		
		Session session = sessionFactory.openSession();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from User");
		if("*".equals(filterColumnValue)
				|| StringUtils.isEmpty(filterColumnName)
				|| StringUtils.isEmpty(filterColumnValue)){
		} else {
			sbQuery.append(" where ");
			sbQuery.append(filterColumnName);
			sbQuery.append(" like '%");
			sbQuery.append(filterColumnValue.trim());
			sbQuery.append("%'");
		}
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery(sbQuery.toString()).list();
		session.close();
		return userList;
	}
	
	@Override
	public User getById(int id) {
		Session session = sessionFactory.openSession();
		User user = (User) session.load(User.class, id);
		return user;
	}

	@Override
	public int delete(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.load(User.class, id);
		session.delete(user);
		tx.commit();
		Serializable ids = session.getIdentifier(user);
		session.close();
		return (Integer) ids;
	}
	
	@Override
	public User getUserByUsername(String username) {
		Session session = sessionFactory.openSession();
		 Transaction tx = null;
		 User user = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from User where login_id='"+username+"'");
			 user = (User)query.uniqueResult();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 logger.warning("Error: getUserByUsername() - "+e.getMessage());
		 } finally {
			 session.close();
		 }
		 return user;
	}
	
}
