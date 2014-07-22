package com.yang.javalib.module.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.binliy.common.bean.pojo.StatMerchant;


/**
 * @author WeiQinyu
 * @version 1.0
 * @since  2013-12-16上午11:22:18 
 * @history 新建
 */
public class MerchantStoreByJedis {
	
	/**
	 * 汇总Count-PV访问量的jedis key标识
	 */
	public final static String MERCHANT_PV_COUNT_KEY = "MERCHANT01";
	
	/**
	 * 汇总Web-PV访问量的jedis key标识
	 */
	public final static String MERCHANT_PV_WEB_KEY = "MERCHANT02";
	
	/**
	 * 汇总App-PV的jedis key标识
	 */
	public final static String MERCHANT_PV_APP_KEY = "MERCHANT03";
	
	/**
	 * 汇总Count-UV访问量的jedis key标识
	 */
	public final static String MERCHANT_UV_COUNT_KEY = "MERCHANT04";
	
	/**
	 * 汇总Web-UV访问量的jedis key标识
	 */
	public final static String MERCHANT_UV_WEB_KEY = "MERCHANT05";
	
	/**
	 * 汇总App-UV的jedis key标识
	 */
	public final static String MERCHANT_UV_APP_KEY = "MERCHANT06";
	
	/**
	 * 汇总Count-IP访问量的jedis key标识
	 */
	public final static String MERCHANT_IP_COUNT_KEY = "MERCHANT07";
	
	/**
	 * 汇总Web-IP访问量的jedis key标识
	 */
	public final static String MERCHANT_IP_WEB_KEY = "MERCHANT08";
	
	/**
	 * 汇总App-IP的jedis key标识
	 */
	public final static String MERCHANT_IP_APP_KEY = "MERCHANT09";
	
	/**
	 * 统计Web端访问的所有请求
	 * @param yyyymmdd 年月日
	 * @param userId 登录用户userId不为空，非登录用户userId为空
	 * @param ip 不允许为空
	 * @param merchantId
	 */
	public static void setWebMERCHANT(String yyyymmdd, String userId, String ip, String merchantId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-web访问量+1
			jedis.incr(MERCHANT_PV_WEB_KEY + merchantId + yyyymmdd);
			
			if(userId != null) {
				// UV-web访问量累计
				jedis.sadd(MERCHANT_UV_WEB_KEY + merchantId + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-web访问量累计
				jedis.sadd(MERCHANT_IP_WEB_KEY + merchantId + yyyymmdd, userId);
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
	 * @param merchantId
	 */
	public static void setAppMERCHANT(String yyyymmdd, String userId, String ip, String merchantId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-app访问量+1
			jedis.incr(MERCHANT_PV_APP_KEY + merchantId + yyyymmdd);
			
			if(userId != null) {
				// UV-app访问量累计
				jedis.sadd(MERCHANT_UV_APP_KEY + merchantId + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-app访问量累计
				jedis.sadd(MERCHANT_IP_APP_KEY + merchantId + yyyymmdd, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
	/**
	 * 获取MERCHANT统计数据
	 * @param yyyymmdd 年月日
	 * @param merchantId
	 * @return
	 */
	public StatMerchant getMERCHANTData(String yyyymmdd, String merchantId) {
		Jedis jedis = JedisSource.getJedis();
		StatMerchant pojo = new StatMerchant();
		try {
			// Pv-web访问量
			Long webVisitor = Long.parseLong(jedis.get(MERCHANT_PV_WEB_KEY + merchantId + yyyymmdd));
			pojo.setWebVisitor(webVisitor);
			// Pv-app访问量
			Long appVisitor = Long.parseLong(jedis.get(MERCHANT_PV_APP_KEY + merchantId + yyyymmdd));  
			pojo.setAppVisitor(appVisitor);
			// Pv-总访问量
			pojo.setVisitorCount(webVisitor + appVisitor);
			
			// Uv-总访问量
			Set<String> userCount = jedis.sunion(MERCHANT_UV_WEB_KEY + merchantId + yyyymmdd, MERCHANT_UV_APP_KEY + merchantId + yyyymmdd);
			pojo.setUserCount((long)userCount.size());
			// Uv-web访问量
			pojo.setWebUser(jedis.scard(MERCHANT_UV_WEB_KEY + merchantId + yyyymmdd));
			// Uv-app访问量
			pojo.setAppUser(jedis.scard(MERCHANT_UV_APP_KEY + merchantId + yyyymmdd));
			
			// IP-总访问量
			Set<String> ipCount = jedis.sunion(MERCHANT_IP_WEB_KEY + merchantId + yyyymmdd, MERCHANT_IP_APP_KEY + merchantId + yyyymmdd);
			pojo.setIpCount((long)ipCount.size());
			// IP-web访问量
			pojo.setWebIp(jedis.scard(MERCHANT_IP_WEB_KEY + merchantId + yyyymmdd));
			// IP-app访问量
			pojo.setAppIp(jedis.scard(MERCHANT_IP_APP_KEY + merchantId + yyyymmdd));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
		return pojo;
	}
	
	/**
	 * 清理过期的MERCHANT统计数据
	 * @param yyyymmdd 年月日
	 * @param merchantId
	 */
	public void clearMERCHANTData(String yyyymmdd, String merchantId) {
		Jedis jedis = JedisSource.getJedis();
		StatMerchant pojo = new StatMerchant();
		try {
			// Pv-web访问量
			jedis.del(MERCHANT_PV_WEB_KEY + merchantId + yyyymmdd);
			// Pv-app访问量
			jedis.del(MERCHANT_PV_APP_KEY + merchantId + yyyymmdd);  
			// Uv-web访问量
			jedis.del(MERCHANT_UV_WEB_KEY + merchantId + yyyymmdd);
			// Uv-app访问量
			jedis.del(MERCHANT_UV_APP_KEY + merchantId + yyyymmdd);
			// IP-web访问量
			jedis.del(MERCHANT_IP_WEB_KEY + merchantId + yyyymmdd);
			// IP-app访问量
			jedis.del(MERCHANT_IP_APP_KEY + merchantId + yyyymmdd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
}
