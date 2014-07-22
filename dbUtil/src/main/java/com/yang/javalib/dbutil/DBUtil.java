package com.yang.javalib.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import com.yang.javalib.dbutil.common.DBConnectConfig;
 

public class DBUtil {
	private static Logger log = Logger.getLogger(DBUtil.class);
	private long connectTime;
	private String errorMsg;

	public DBUtil() {
		this.connectTime = 0L;
		this.errorMsg = "";
	}

	public Connection getConnection(DBConnectConfig vo) {
		Connection conn = null;
		long startTime = System.currentTimeMillis();
		try {
			Class.forName(vo.getDriverName()).newInstance();
			conn = DriverManager.getConnection(vo.getUrl(), vo.getUsername(),
					vo.getPassword());

			long endTime = System.currentTimeMillis();
			this.connectTime = (endTime - startTime);
		} catch (Exception ex) {
			log.error(ex);
			String s8 = ex.getMessage();
			if (s8.indexOf("NoRouteToHostException") != -1) {
				this.errorMsg = "服务器连接错误，请确定服务器是否正常！";
			} else if (s8.indexOf("ConnectException") != -1) {
				this.errorMsg = "数据库连接错误，请确定数据库是否正常！";
			} else if (s8.indexOf("Unknown database") != -1) {
				this.errorMsg = "数据库不存在，请确定配置文件的数据库名是否正确！";
			} else if ((s8.indexOf("Host") != -1)
					&& (s8.indexOf("not allowed to") != -1))
				this.errorMsg = "没有权限访问数据库，请确定数据库用户是否具有对应的权限！";
			else {
				this.errorMsg = "未知错误，请同系统管理员联系！";
			}
			this.connectTime = 0L;
		}
		return conn;
	}

	public static List query(Connection conn, String sql, ArrayList paraList)
			throws Exception {
		ArrayList valueList = new ArrayList();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int i = 0; i < paraList.size(); ++i) {
					ps.setObject(i + 1, paraList.get(i));
				}
			}
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsetMD = rs.getMetaData();
				int cols = rsetMD.getColumnCount();
				while (rs.next()) {
					HashMap valueMap = new HashMap();
					for (int i = 0; i < cols; ++i) {
						String colName = rsetMD.getColumnName(i + 1);
						String temps = rs.getString(colName);
						if ((temps != null)
								&& (temps.trim().length() != 0)
								&& (!(temps.trim().toLowerCase().equals("null")))) {
							valueMap.put(colName, temps);
						} else
							valueMap.put(colName, "");
					}

					valueList.add(valueMap);
				}
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null)
				ps.close();
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw  ex;
		}
		return valueList;
	}

	public static List query1(Connection conn, String sql, ArrayList paraList)
			throws Exception {
		ArrayList valueList = new ArrayList();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int i = 0; i < paraList.size(); ++i) {
					ps.setObject(i + 1, paraList.get(i));
				}
			}
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsetMD = rs.getMetaData();
				int cols = rsetMD.getColumnCount();
				while (rs.next()) {
					HashMap valueMap = new HashMap();
					for (int i = 0; i < cols; ++i) {
						String colName = rsetMD.getColumnName(i + 1);
						Object obj = rs.getObject(colName);
						valueMap.put(colName, obj);
					}
					valueList.add(valueMap);
				}
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null)
				ps.close();
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw  ex;
		}
		return valueList;
	}

	public static List query2(Connection conn, String sql, ArrayList paraList)
			throws Exception {
		ArrayList valueList = new ArrayList();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int i = 0; i < paraList.size(); ++i) {
					ps.setObject(i + 1, paraList.get(i));
				}
			}
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsetMD = rs.getMetaData();
				int cols = rsetMD.getColumnCount();
				while (rs.next()) {
					HashMap valueMap = new HashMap();
					for (int i = 0; i < cols; ++i) {
						String colName = rsetMD.getColumnName(i + 1);
						String temps = rs.getString(colName);
						if ((temps != null)
								&& (temps.trim().length() != 0)
								&& (!(temps.trim().toLowerCase().equals("null")))) {
							valueMap.put(colName.toLowerCase(), temps);
						} else
							valueMap.put(colName.toLowerCase(), "");
					}

					valueList.add(valueMap);
				}
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null)
				ps.close();
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw ex;
		}
		return valueList;
	}

	public static int update(Connection conn, String sql, ArrayList paraList)
			throws Exception {
		int result = 0;
		try {
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int i = 0; i < paraList.size(); ++i) {
					ps.setObject(i + 1, paraList.get(i));
				}
			}
			result = ps.executeUpdate();
			if (ps != null)
				ps.close();
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	public static void closeConnection(Connection conn) {
		try {
			if ((conn != null) && (!(conn.isClosed()))) {
				conn.close();
			}
			conn = null;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

	public long getConnectTime() {
		return this.connectTime;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.DBUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/