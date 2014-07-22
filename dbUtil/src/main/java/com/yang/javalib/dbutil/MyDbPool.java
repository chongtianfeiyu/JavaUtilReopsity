package com.yang.javalib.dbutil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.yang.javalib.dbutil.common.BaseConnBean;
 
 

public class MyDbPool {

	private static final Logger logger = Logger.getLogger(MyDbPool.class);

	private static ConcurrentMap<BaseConnBean, BasicDataSource> pools = new ConcurrentHashMap<BaseConnBean, BasicDataSource>();
	private static final String ORACLE_JDBC_URL = "jdbc:oracle";
	private static final String MYSQL_JDBC_URL = "jdbc:mysql";
	private static final String SQL_JDBC_URL = "jdbc:sqlserver";
	private static final String POSTGRE_JDBC_URL = "jdbc:postgresql";

	// jdbc:sqlserver://127.0.0.1:1433;databaseName=oiw

	/**
	 * 关闭连接池
	 */
	public static void ShutdownPool() {
		for (BasicDataSource ds : pools.values()) {
			try {
				ds.close();
			} catch (SQLException e) {
				logger.warn("", e);
			}
		}
		pools.clear();
	}

	/**
	 * 增加数据库连接信息
	 * 
	 * @param poolsParams
	 *            BaseConnBean集合
	 */
	public static void addConnBean(Collection<BaseConnBean> poolsParams) {
		if (CollectionUtils.isEmpty(poolsParams))
			return;

		for (BaseConnBean baseConnBean : poolsParams) {
			if (pools.containsKey(baseConnBean))
				continue;

			BasicDataSource ds = new BasicDataSource();
			ds.setMaxActive(baseConnBean.getMaxActive());
			ds.setMaxWait(baseConnBean.getMaxWait());
			ds.setInitialSize(baseConnBean.getInitialSize());
			ds.setMinIdle(baseConnBean.getMinIdle());
			ds.setUrl(baseConnBean.getUrl());
			ds.setDriverClassName(baseConnBean.getDriverClassName());
			ds.setUsername(baseConnBean.getUsername());
			ds.setPassword(baseConnBean.getPassword());
			ds.setRemoveAbandoned(false);

			if (baseConnBean.getUrl().startsWith(ORACLE_JDBC_URL)) {
				ds.setValidationQuery("select sysdate from dual");
				doCheck(baseConnBean, ds);
			} else if (baseConnBean.getUrl().startsWith(SQL_JDBC_URL)) {
				ds.setValidationQuery("select 1");
				doCheck(baseConnBean, ds);
			} else if (baseConnBean.getUrl().startsWith(MYSQL_JDBC_URL)) {
				ds.setValidationQuery("select 1");
				doCheck(baseConnBean, ds);
			} else if (baseConnBean.getUrl().startsWith(POSTGRE_JDBC_URL)) {
				ds.setValidationQuery("select 1");
				doCheck(baseConnBean, ds);
			}

			pools.putIfAbsent(baseConnBean, ds);

			DbUtils.loadDriver(baseConnBean.getDriverClassName());
		}
	}

	public static void addConnBean(BaseConnBean baseConnBean) {
		if (pools.containsKey(baseConnBean))
			return;

		BasicDataSource ds = new BasicDataSource();
		ds.setMaxActive(baseConnBean.getMaxActive());
		ds.setMaxWait(baseConnBean.getMaxWait());
		ds.setInitialSize(baseConnBean.getInitialSize());
		ds.setMinIdle(baseConnBean.getMinIdle());
		ds.setUrl(baseConnBean.getUrl());
		ds.setDriverClassName(baseConnBean.getDriverClassName());
		ds.setUsername(baseConnBean.getUsername());
		ds.setPassword(baseConnBean.getPassword());
		ds.setRemoveAbandoned(false);

		if (baseConnBean.getUrl().startsWith(ORACLE_JDBC_URL)) {
			ds.setValidationQuery("select sysdate from dual");
			doCheck(baseConnBean, ds);
		} else if (baseConnBean.getUrl().startsWith(SQL_JDBC_URL)) {
			ds.setValidationQuery("select 1");
			doCheck(baseConnBean, ds);
		} else if (baseConnBean.getUrl().startsWith(MYSQL_JDBC_URL)) {
			ds.setValidationQuery("select 1");
			doCheck(baseConnBean, ds);
		} else if (baseConnBean.getUrl().startsWith(POSTGRE_JDBC_URL)) {
			ds.setValidationQuery("select 1");
			doCheck(baseConnBean, ds);
		}

		pools.putIfAbsent(baseConnBean, ds);

		DbUtils.loadDriver(baseConnBean.getDriverClassName());

	}

	private static void doCheck(BaseConnBean baseConnBean, BasicDataSource ds) {
		ds.setTestOnBorrow(true);
		ds.setTestWhileIdle(true);
		ds.setTimeBetweenEvictionRunsMillis(30000);
		ds.setNumTestsPerEvictionRun(baseConnBean.getMaxActive());
	}

	/**
	 * 获得数据库连接
	 * 
	 * @param baseConnBean
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection(BaseConnBean baseConnBean)
			throws SQLException {
		if (baseConnBean == null)
			return null;

		DataSource ds = pools.get(baseConnBean);

		if (ds == null)
			return null;

		return ds.getConnection();// 这里出错..用户名和密码错误 的情况
	}

}
