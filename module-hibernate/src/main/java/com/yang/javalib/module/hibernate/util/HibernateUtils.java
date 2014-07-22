package com.yang.javalib.module.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @Description: 启动hibernate框架类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-31 下午1:50:28 
 */
public class HibernateUtils {

	private static SessionFactory factory;
	
	static {
		try {
			Configuration cfg = new Configuration().configure();
			factory = cfg.buildSessionFactory();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
	
	public static Session getSession() {
		return factory.openSession();
	}
	
	public static void closeSession(Session session) {
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
}
