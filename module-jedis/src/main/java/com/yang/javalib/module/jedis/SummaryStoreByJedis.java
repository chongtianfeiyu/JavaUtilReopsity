package com.yang.javalib.module.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.binliy.common.bean.pojo.StatSummary;


/**
 * @author WeiQinyu
 * @version 1.0
 * @since  2013-12-16上午11:22:18 
 * @history 新建
 */
public class SummaryStoreByJedis {
	
	/**
	 * 汇总Count-PV访问量的jedis key标识
	 */
	public final static String SUMMARY_PV_COUNT_KEY = "SUMMARY01";
	
	/**
	 * 汇总Web-PV访问量的jedis key标识
	 */
	public final static String SUMMARY_PV_WEB_KEY = "SUMMARY02";
	
	/**
	 * 汇总App-PV的jedis key标识
	 */
	public final static String SUMMARY_PV_APP_KEY = "SUMMARY03";
	
	/**
	 * 汇总Count-UV访问量的jedis key标识
	 */
	public final static String SUMMARY_UV_COUNT_KEY = "SUMMARY04";
	
	/**
	 * 汇总Web-UV访问量的jedis key标识
	 */
	public final static String SUMMARY_UV_WEB_KEY = "SUMMARY05";
	
	/**
	 * 汇总App-UV的jedis key标识
	 */
	public final static String SUMMARY_UV_APP_KEY = "SUMMARY06";
	
	/**
	 * 汇总Count-IP访问量的jedis key标识
	 */
	public final static String SUMMARY_IP_COUNT_KEY = "SUMMARY07";
	
	/**
	 * 汇总Web-IP访问量的jedis key标识
	 */
	public final static String SUMMARY_IP_WEB_KEY = "SUMMARY08";
	
	/**
	 * 汇总App-IP的jedis key标识
	 */
	public final static String SUMMARY_IP_APP_KEY = "SUMMARY09";
	
	/**
	 * 汇总Count-LOGIN访问量的jedis key标识
	 */
	public final static String SUMMARY_LOGIN_COUNT_KEY = "SUMMARY10";
	
	/**
	 * 汇总Web-LOGIN访问量的jedis key标识
	 */
	public final static String SUMMARY_LOGIN_WEB_KEY = "SUMMARY11";
	
	/**
	 * 汇总App-LOGIN的jedis key标识
	 */
	public final static String SUMMARY_LOGIN_APP_KEY = "SUMMARY12";
	
	/**
	 * 统计Web端访问的所有请求
	 * @param yyyymmdd 年月日
	 * @param userId 登录用户userId不为空，非登录用户userId为空
	 * @param ip 不允许为空
	 */
	public static void setWebSummary(String yyyymmdd, String userId, String ip) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-web访问量+1
			jedis.incr(SUMMARY_PV_WEB_KEY + yyyymmdd);
			
			if(userId != null) {
				// UV-web访问量累计
				jedis.sadd(SUMMARY_UV_WEB_KEY + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-web访问量累计
				jedis.sadd(SUMMARY_IP_WEB_KEY + yyyymmdd, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
	/**
	 * 统计App端访问的所有请求
	 * @param yyyymmdd 年月日
	 * @param userId 登录用户userId不为空，非登录用户userId为空
	 * @param ip 不允许为空
	 */
	public static void setAppSummary(String yyyymmdd, String userId, String ip) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-app访问量+1
			jedis.incr(SUMMARY_PV_APP_KEY + yyyymmdd);
			
			if(userId != null) {
				// UV-app访问量累计
				jedis.sadd(SUMMARY_UV_APP_KEY + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-app访问量累计
				jedis.sadd(SUMMARY_IP_APP_KEY + yyyymmdd, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
	/**
	 * 统计Web端登录的所有用户
	 * @param yyyymmdd 年月日
	 * @param userId
	 */
	public static void setLoginWebSummary(String yyyymmdd, String userId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// WEB总登录量累计
			jedis.sadd(SUMMARY_LOGIN_WEB_KEY + yyyymmdd, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
		
	}
	
	/**
	 * 统计App端登录的所有用户
	 * @param yyyymmdd 年月日
	 * @param userId
	 */
	public static void setLoginAppSummary(String yyyymmdd, String userId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// APP总登录量累计
			jedis.sadd(SUMMARY_LOGIN_APP_KEY + yyyymmdd, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
	/**
	 * 获取Summary统计数据
	 * @param yyyymmdd 年月日
	 * @return
	 */
	public StatSummary getSummaryData(String yyyymmdd) {
		Jedis jedis = JedisSource.getJedis();
		StatSummary pojo = new StatSummary();
		try {
			// Pv-web访问量
			Long webVisitor = Long.parseLong(jedis.get(SUMMARY_PV_WEB_KEY + yyyymmdd));
			pojo.setWebVisitor(webVisitor);
			// Pv-app访问量
			Long appVisitor = Long.parseLong(jedis.get(SUMMARY_PV_APP_KEY + yyyymmdd));  
			pojo.setAppVisitor(appVisitor);
			// Pv-总访问量
			pojo.setVisitorCount(webVisitor + appVisitor);
			
			// Uv-总访问量
			Set<String> userCount = jedis.sunion(SUMMARY_UV_WEB_KEY + yyyymmdd, SUMMARY_UV_APP_KEY + yyyymmdd);
			pojo.setUserCount((long)userCount.size());
			// Uv-web访问量
			pojo.setWebUser(jedis.scard(SUMMARY_UV_WEB_KEY + yyyymmdd));
			// Uv-app访问量
			pojo.setAppUser(jedis.scard(SUMMARY_UV_APP_KEY + yyyymmdd));
			
			// IP-总访问量
			Set<String> ipCount = jedis.sunion(SUMMARY_IP_WEB_KEY + yyyymmdd, SUMMARY_IP_APP_KEY + yyyymmdd);
			pojo.setIpCount((long)ipCount.size());
			// IP-web访问量
			pojo.setWebIp(jedis.scard(SUMMARY_IP_WEB_KEY + yyyymmdd));
			// IP-app访问量
			pojo.setAppIp(jedis.scard(SUMMARY_IP_APP_KEY + yyyymmdd));
			
			// 总登录量
			Set<String> loginCount = jedis.sunion(SUMMARY_LOGIN_WEB_KEY + yyyymmdd, SUMMARY_LOGIN_APP_KEY + yyyymmdd);
			pojo.setLoginCount((long)loginCount.size());
			// web登录量
			pojo.setWebIp(jedis.scard(SUMMARY_LOGIN_WEB_KEY + yyyymmdd));
			// app登录量
			pojo.setAppIp(jedis.scard(SUMMARY_LOGIN_APP_KEY + yyyymmdd));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
		return pojo;
	}
	
	/**
	 * 清理过期的Summary统计数据
	 * @param yyyymmdd 年月日
	 */
	public void clearSummaryData(String yyyymmdd) {
		Jedis jedis = JedisSource.getJedis();
		StatSummary pojo = new StatSummary();
		try {
			// Pv-web访问量
			jedis.del(SUMMARY_PV_WEB_KEY + yyyymmdd);
			// Pv-app访问量
			jedis.del(SUMMARY_PV_APP_KEY + yyyymmdd);  
			// Uv-web访问量
			jedis.del(SUMMARY_UV_WEB_KEY + yyyymmdd);
			// Uv-app访问量
			jedis.del(SUMMARY_UV_APP_KEY + yyyymmdd);
			// IP-web访问量
			jedis.del(SUMMARY_IP_WEB_KEY + yyyymmdd);
			// IP-app访问量
			jedis.del(SUMMARY_IP_APP_KEY + yyyymmdd);
			// web登录量
			jedis.del(SUMMARY_LOGIN_WEB_KEY + yyyymmdd);
			// app登录量
			jedis.del(SUMMARY_LOGIN_APP_KEY + yyyymmdd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
}
