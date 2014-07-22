package com.yang.javalib.module.hibernate.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
 
import com.binliy.web.dao.BaseDao;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	
	private static final Logger logger=Logger.getLogger(BaseDaoImpl.class) ;

	/**
	 * 查询用户列表
	 * 
	 * @param hql		hql语句
	 * @param params	hql语句对应的参数列表
	 * @param startRow	开始行号
	 * @param pageSize	每页显示
	 * @return			用户列表
	 */
	public List<?> search(final String hql, final List<Object> params, final int startRow, final int pageSize){
		//设置查询缓存
		getHibernateTemplate().setCacheQueries(true);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@SuppressWarnings("rawtypes")
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(params != null && params.size() > 0){
					for(int i=0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				if(startRow != -1){
					query.setFirstResult(startRow);
				}
				if(pageSize != -1){
					query.setMaxResults(pageSize);
				}
				return query.list();
			}
		});
	}
	
	/**
	 * 查询用户列表
	 * 
	 * @param detachedCriteria
	 * @param startRow	开始行号
	 * @param pageSize	每页显示
	 * @return			用户列表
	 */
	@SuppressWarnings("rawtypes")
	public List<?> search(final DetachedCriteria detachedCriteria, final int startRow, final int pageSize) {
		//设置查询缓存
       getHibernateTemplate().setCacheQueries(true);
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				criteria.setProjection(null);
				criteria = criteria.setFirstResult(startRow).setMaxResults(pageSize);
				criteria = criteria.addOrder(Order.desc("id"));
//				return criteria.setCacheable(true).list();
				return criteria.list();
			}
		});
	}
	
	/**
	 * 查询用户个数
	 * 
	 * @param hql		hql语句
	 * @param params	hql语句对应的参数列表
	 * @return		用户个数
	 */
	@SuppressWarnings("rawtypes")
	public int count(final String hql, final List<Object> params) {
		//设置查询缓存
		getHibernateTemplate().setCacheQueries(true);
		return (getHibernateTemplate().executeFind(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(params != null && params.size() > 0){
					for(int i=0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				return query.list();
			}
		})).size();
	}
	
	/**
	 * 查询用户个数
	 * 
	 * @param detachedCriteria
	 * @return	用户个数
	 */
	public int count(final DetachedCriteria detachedCriteria){
		//设置查询缓存
		getHibernateTemplate().setCacheQueries(true);
		int count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return (Integer) criteria.setCacheable(false).setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count;
	}
	

	@Override
	@SuppressWarnings("rawtypes")
	public List findByCriteria(final DetachedCriteria detachedCriteria) {
		//设置查询缓存
		getHibernateTemplate().setCacheQueries(true);
		List typeList=(ArrayList)getHibernateTemplate().execute(
				new HibernateCallback()
				{	@Override
					public Object doInHibernate(Session session)
							throws HibernateException,SQLException
					{
						//Transaction txn = session.beginTransaction();
						Criteria criteria=detachedCriteria.getExecutableCriteria(session); 
						//criteria.setProjection(null); 
						List list=criteria.list();
						session.close();
						return list;
					} 
				});
		return typeList;
	} 
	

	 /**
		 * 查询List
		 * 
		 * @param sql		sql语句
		 * @param params	sql语句对应的参数列表
		 * @param startRow	开始行号
		 * @param pageSize	每页显示
		 * @return			用户列表
		 */
		public List<?> searchBySql(final String sql, final List<Object> params, final int startRow, final int pageSize){
			//设置查询缓存
			getHibernateTemplate().setCacheQueries(true);
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				@SuppressWarnings("rawtypes")
				public List doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = getSession().createSQLQuery(sql);
					if(params != null && params.size() > 0){
						for(int i=0; i < params.size(); i++){
							query.setParameter(i, params.get(i));
						}
					}
					query.setFirstResult(startRow);
					query.setMaxResults(pageSize);
					return query.list();
				}
			});
		}
		
		
		/**
		 * 查询记录数
		 * 
		 * @param sql		sql语句
		 * @param params	sql语句对应的参数列表
		 * @return		记录数
		 */
		@SuppressWarnings("rawtypes")
		public int countBySql(final String sql, final List<Object> params) {
			//设置查询缓存
			getHibernateTemplate().setCacheQueries(true);
			return (getHibernateTemplate().executeFind(new HibernateCallback() {
				public List doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = getSession().createSQLQuery(sql);
					if(params != null && params.size() > 0){
						for(int i=0; i < params.size(); i++){
							query.setParameter(i, params.get(i));
						}
					}
					List list = query.list();
					return list;
				}
			})).size();
		}
	
	/* (non-Javadoc) TODO . 适配方法，不然就把baseDaoImpl 改成抽象类
	 * @see com.binliy.web.dao.IBaseDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		  
		return null;
	}

	@Override
	public List findAll() { 
		return null;
	}

	/**
	 * 执行SQL返回 List<?>
	 * @param accountMerchant
	 * @return
	 */
	public  List<?> executeSql(String Sql) {
		try {
			logger.info("执行sql语句：" + Sql);
			//设置查询缓存
			Query queryObject = getSession().createSQLQuery(Sql);
			return queryObject.list();
		} catch (RuntimeException re) {
			logger.error("find executeSql  failed", re);
			throw re;
		}
	}

	public int executeUpdateSql(String Sql) {
		try {
			logger.info("执行sql语句：" + Sql);
			//设置查询缓存
			Query queryObject = getSession().createSQLQuery(Sql);
			return queryObject.executeUpdate(); 
		} catch (RuntimeException re) {
			logger.error("find executeSql  failed", re);
			throw re;
		}
	}
	

	public  Integer executeSql4GetCount(String Sql) throws IllegalArgumentException {
		if (logger.isDebugEnabled()) {
			logger.debug("executeSql4GetCount(String) - start 执行SQL："+Sql); //$NON-NLS-1$
		}

		try {
			//设置查询缓存
			Query queryObject = getSession().createSQLQuery(Sql); 
			try {
				List tt = queryObject.list();  
				logger.info("类型:"+tt.get(0));
				if(tt.get(0) instanceof BigInteger) {
					BigInteger temp =(BigInteger)tt.get(0); 
					return temp.intValue();
				}
				else {
					throw new IllegalArgumentException("1查询值不正确!");
				}
			} catch (Exception e) {
				logger.error("executeSql4GetCount(String)", e); //$NON-NLS-1$ 
				throw new IllegalArgumentException("2查询值不正确!");
			}
		} catch (RuntimeException re) {
			logger.error("executeSql4GetCount(String)", re); //$NON-NLS-1$ 
			throw new IllegalArgumentException("查询出现异常!",re); 
		}
	}
	
	/**
	 * 执行SQL返回 List<?>
	 * @param accountMerchant
	 * @return
	 */
	public  List<?> executeHSql(String HSql) {
		try {
			logger.info("执行sql语句：" + HSql);
			//设置查询缓存
			Query queryObject = getSession().createQuery(HSql);
			return queryObject.list();
		} catch (RuntimeException re) {
			logger.error("find executeSql  failed", re);
			throw re;
		}
	}

	/* (非 Javadoc)
	* <p>Title: executeSqlToMap</p>
	* <p>Description: </p>
	* @param Sql
	* @return
	* @see com.binliy.web.dao.BaseDao#executeSqlToMap(java.lang.String)
	*/
	@Override
	public List<?> executeSqlToMap(String Sql) {
		try {
			logger.info("执行sql语句：" + Sql);
			//设置查询缓存
			Query queryObject = getSession().createSQLQuery(Sql);
			queryObject.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			logger.error("find executeSql  failed", re);
			throw re;
		}
	}
 
}