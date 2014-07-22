package com.yang.javalib.module.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.binliy.common.bean.pojo.StatGoods;
import com.binliy.common.bean.pojo.StatMerchant;


/**
 * @author WeiQinyu
 * @version 1.0
 * @since  2013-12-16上午11:22:18 
 * @history 新建
 */
public class GoodsStoreByJedis {
	
	/**
	 * 汇总Count-PV访问量的jedis key标识
	 */
	public final static String GOODS_PV_COUNT_KEY = "GOODS01";
	
	/**
	 * 汇总Web-PV访问量的jedis key标识
	 */
	public final static String GOODS_PV_WEB_KEY = "GOODS02";
	
	/**
	 * 汇总App-PV的jedis key标识
	 */
	public final static String GOODS_PV_APP_KEY = "GOODS03";
	
	/**
	 * 汇总Count-UV访问量的jedis key标识
	 */
	public final static String GOODS_UV_COUNT_KEY = "GOODS04";
	
	/**
	 * 汇总Web-UV访问量的jedis key标识
	 */
	public final static String GOODS_UV_WEB_KEY = "GOODS05";
	
	/**
	 * 汇总App-UV的jedis key标识
	 */
	public final static String GOODS_UV_APP_KEY = "GOODS06";
	
	/**
	 * 汇总Count-IP访问量的jedis key标识
	 */
	public final static String GOODS_IP_COUNT_KEY = "GOODS07";
	
	/**
	 * 汇总Web-IP访问量的jedis key标识
	 */
	public final static String GOODS_IP_WEB_KEY = "GOODS08";
	
	/**
	 * 汇总App-IP的jedis key标识
	 */
	public final static String GOODS_IP_APP_KEY = "GOODS09";
	
	/**
	 * 统计Web端访问的所有请求
	 * @param yyyymmdd 年月日
	 * @param userId 登录用户userId不为空，非登录用户userId为空
	 * @param ip 不允许为空
	 * @param goodsId
	 */
	public static void setWebGOODS(String yyyymmdd, String userId, String ip, String goodsId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-web访问量+1
			jedis.incr(GOODS_PV_WEB_KEY + goodsId + yyyymmdd);
			
			if(userId != null) {
				// UV-web访问量累计
				jedis.sadd(GOODS_UV_WEB_KEY + goodsId + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-web访问量累计
				jedis.sadd(GOODS_IP_WEB_KEY + goodsId + yyyymmdd, userId);
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
	 * @param goodsId
	 */
	public static void setAppGOODS(String yyyymmdd, String userId, String ip, String goodsId) {
		Jedis jedis = JedisSource.getJedis();
		try {
			// PV-app访问量+1
			jedis.incr(GOODS_PV_APP_KEY + goodsId + yyyymmdd);
			
			if(userId != null) {
				// UV-app访问量累计
				jedis.sadd(GOODS_UV_APP_KEY + goodsId + yyyymmdd, userId);
			}
			
			if(ip != null) {
				// IP-app访问量累计
				jedis.sadd(GOODS_IP_APP_KEY + goodsId + yyyymmdd, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
	/**
	 * 获取GOODS统计数据
	 * @param yyyymmdd 年月日
	 * @param goodsId
	 * @return
	 */
	public StatGoods getGOODSData(String yyyymmdd, String goodsId) {
		Jedis jedis = JedisSource.getJedis();
		StatGoods pojo = new StatGoods();
		try {
			// Pv-web访问量
			Long webVisitor = Long.parseLong(jedis.get(GOODS_PV_WEB_KEY + goodsId + yyyymmdd));
			pojo.setWebVisitor(webVisitor);
			// Pv-app访问量
			Long appVisitor = Long.parseLong(jedis.get(GOODS_PV_APP_KEY + goodsId + yyyymmdd));  
			pojo.setAppVisitor(appVisitor);
			// Pv-总访问量
			pojo.setVisitorCount(webVisitor + appVisitor);
			
			// Uv-总访问量
			Set<String> userCount = jedis.sunion(GOODS_UV_WEB_KEY + goodsId + yyyymmdd, GOODS_UV_APP_KEY + goodsId + yyyymmdd);
			pojo.setUserCount((long)userCount.size());
			// Uv-web访问量
			pojo.setWebUser(jedis.scard(GOODS_UV_WEB_KEY + goodsId + yyyymmdd));
			// Uv-app访问量
			pojo.setAppUser(jedis.scard(GOODS_UV_APP_KEY + goodsId + yyyymmdd));
			
			// IP-总访问量
			Set<String> ipCount = jedis.sunion(GOODS_IP_WEB_KEY + goodsId + yyyymmdd, GOODS_IP_APP_KEY + goodsId + yyyymmdd);
			pojo.setIpCount((long)ipCount.size());
			// IP-web访问量
			pojo.setWebIp(jedis.scard(GOODS_IP_WEB_KEY + goodsId + yyyymmdd));
			// IP-app访问量
			pojo.setAppIp(jedis.scard(GOODS_IP_APP_KEY + goodsId + yyyymmdd));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
		return pojo;
	}
	
	/**
	 * 清理过期的GOODS统计数据
	 * @param yyyymmdd 年月日
	 * @param goodsId
	 */
	public void clearGOODSData(String yyyymmdd, String goodsId) {
		Jedis jedis = JedisSource.getJedis();
		StatMerchant pojo = new StatMerchant();
		try {
			// Pv-web访问量
			jedis.del(GOODS_PV_WEB_KEY + goodsId + yyyymmdd);
			// Pv-app访问量
			jedis.del(GOODS_PV_APP_KEY + goodsId + yyyymmdd);  
			// Uv-web访问量
			jedis.del(GOODS_UV_WEB_KEY + goodsId + yyyymmdd);
			// Uv-app访问量
			jedis.del(GOODS_UV_APP_KEY + goodsId + yyyymmdd);
			// IP-web访问量
			jedis.del(GOODS_IP_WEB_KEY + goodsId + yyyymmdd);
			// IP-app访问量
			jedis.del(GOODS_IP_APP_KEY + goodsId + yyyymmdd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisSource.releaseJedis(jedis);
		}
	}
	
}
