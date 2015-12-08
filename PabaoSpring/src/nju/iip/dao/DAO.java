package nju.iip.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAO.class);
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal SESSION_LOCAL = new ThreadLocal();
	
	private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	protected DAO() {
	}
	
	/**
	 * 取得当前线程的会话
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Session getSession() {
		Session session = (Session)DAO.SESSION_LOCAL.get();
		if(session == null) {
			session = sessionFactory.openSession();
			DAO.SESSION_LOCAL.set(session);
		}
		return session;
	}
	
	protected void begin() {
		getSession().beginTransaction();
	}
	
	protected void commit() {
		getSession().getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static void close(){
		getSession().close();
		DAO.SESSION_LOCAL.set(null);
	}
	
	
	@SuppressWarnings("unchecked")
	protected void rollback() {
		try {
			getSession().beginTransaction().rollback();
		}catch(HibernateException e) {
			logger.info("rollback failed!",e);
		}
		try {
			getSession().close();
		}catch(HibernateException e) {
			logger.info("close session failed!",e);
		}
		DAO.SESSION_LOCAL.set(null);
	}
	
	

}
