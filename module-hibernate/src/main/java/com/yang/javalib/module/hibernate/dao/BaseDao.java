package com.yang.javalib.module.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
 
public interface BaseDao  {

	/**
	 * 查询用户列表
	 * @param hql		hql语句
	 * @param params	hql语句对应的参数列表
	 * @param startRow	开始行号
	 * @param pageSize	每页显示
	 * @return			用户列表
	 */
	public List<?> search(final String hql, final List<Object> params, final int startRow, final int pageSize);

	/**
	 * 查询列表
	 * 
	 * @param detachedCriteria
	 * @param startRow	开始行号
	 * @param pageSize	每页显示
	 * @return			用户列表
	 */
	public List<?> search(final DetachedCriteria detachedCriteria, final int startRow, final int pageSize);
	
	/**
	 * 查询用户个数
	 * @param hql		hql语句
	 * @param params	hql语句对应的参数列表
	 * @return			用户个数
	 */
	public int count(final String hql, final List<Object> params);
	
	/**
	 * 查询用户个数
	 * 
	 * @param detachedCriteria
	 * @return	用户个数
	 */
	public int count(final DetachedCriteria detachedCriteria);
	
	
	/**
	 * 通过DetachedCriteria 查询
	 * @param detachedCriteria
	 * @return
	 */
	public List findByCriteria(final DetachedCriteria detachedCriteria) ;
	
	/**
	 * 查询记录数
	 * 
	 * @param sql		sql语句
	 * @param params	sql语句对应的参数列表
	 * @return		记录数
	 */
	public int countBySql(final String sql, final List<Object> params);
	
	/**
	 * 查询List
	 * 
	 * @param sql		sql语句
	 * @param params	sql语句对应的参数列表
	 * @param startRow	开始行号
	 * @param pageSize	每页显示
	 * @return			用户列表
	 */
	public List<?> searchBySql(final String sql, final List<Object> params, final int startRow, final int pageSize);
	
 
	/**
	 * 执行查询SQL查询
	 * @param Sql
	 * @return
	 */
	public  List<?> executeSql(String Sql) ;

	

	/**
	 * @Description: 执行更新SQL查询
	 * @param Sql
	 * @return
	 * List<?>
	 * @throws
	 */
	public int executeUpdateSql(String Sql) ; 
	
	
	public  Integer executeSql4GetCount(String Sql) throws IllegalArgumentException ;
	 
	
	public  List<?> executeHSql(String HSql) ;
	
	/**
	 * 执行SQL查询
	 * @param Sql
	 * @return
	 */
	public  List<?> executeSqlToMap(String Sql) ;
	   
}
