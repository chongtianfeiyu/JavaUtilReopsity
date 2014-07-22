package com.yang.javalib.module.jedis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @Description	: Jedis连接源
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-29 下午2:31:51 
 */
public class JedisSource {
	
	private static JedisSource jedisSource;
	
	private JedisPool jedisPool;
	
	/**
	 * @Fields redisIpPort : redis服务端ip与端口
	 */
	private final static String redisIpPort = "192.168.1.103_6379";

	/**
	 * 主从多台服务器，使用该连接池
	 */
	private ShardedJedis shardedJedis;
	
	private ShardedJedisPool shardedJedisPool;

	private JedisSource() {
		initialPool();
	}

	/**
	 * 单例模式，获取实例
	 * @return
	 */
	public static JedisSource getInstance() {
		if (jedisSource == null) {
			synchronized (JedisSource.class) {
				if (jedisSource == null) {
					jedisSource = new JedisSource();
				}
			}
		}
		return jedisSource;
	}
	
	/**
	 * 单台redis服务器，初始化
	 */
	private void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(20);
		config.setMaxWait(1000);
		config.setTestOnBorrow(false);
		String[] ipPort = redisIpPort.split("_");
		jedisPool = new JedisPool(config, ipPort[0], Integer.parseInt(ipPort[1]));
	}

	/**
	 * 多台主从redis服务器
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		config.setMaxIdle(5);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		
		String[] ipPort = redisIpPort.split("_");
		// master、slave
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(ipPort[0], Integer.parseInt(ipPort[1]), "master"));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	/**
	 * @return the jedis
	 */
	public static Jedis getJedis() {
		return getInstance().jedisPool.getResource();
	}
	
	/**
	 * 释放jedis连接
	 * @return the jedis
	 */
	public static void releaseJedis(Jedis jedis) {
		if(jedis != null) {
			try {
				getInstance().jedisPool.returnResource(jedis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
