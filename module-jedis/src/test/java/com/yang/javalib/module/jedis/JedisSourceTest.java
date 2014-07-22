package com.yang.javalib.module.jedis;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisSourceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test操作string类型() { 
		Jedis jedis = JedisSource.getJedis();
		if(!jedis.exists("yangxiaodong")) 
			jedis.sadd("yangxiaodong", "hello yangxiaodong");   //可以放多个？类似于list结构了。
		System.out.println(jedis.get("yangxiaodong"));
	}
	
	
	
	/**
	 * @Description: 测试scard, 返回一个集合的数量
	 * void
	 * @throws
	 */
	@Test
	public void test操作返回集合数量() { 
		Jedis jedis = JedisSource.getJedis(); 
		jedis.sadd("yangxiaodong", "yangxiaodong:11","yangxiaodong:22","yangxiaodong:33");
		jedis.sadd("yangxiaodong1", "yangxiaodong1:11","yangxiaodong1:22","yangxiaodong1:33");
		jedis.sadd("yangxiaodong2", "yangxiaodong2:11","yangxiaodong2:22","yangxiaodong2:33"); 
		Set<String> userCount = jedis.sunion("yangxiaodong", "yangxiaodong1","yangxiaodong2");
		Long numOfKey =	jedis.scard("yangxiaodong");
		System.out.println(numOfKey);
	}
	 

	@Test
	public void test操作union集合类型() { 
		Jedis jedis = JedisSource.getJedis(); 
		jedis.sadd("yangxiaodong", "yangxiaodong:11","yangxiaodong:22","yangxiaodong:33");
		jedis.sadd("yangxiaodong1", "yangxiaodong1:11","yangxiaodong1:22","yangxiaodong1:33");
		jedis.sadd("yangxiaodong2", "yangxiaodong2:11","yangxiaodong2:22","yangxiaodong2:33"); 
		Set<String> userCount = jedis.sunion("yangxiaodong", "yangxiaodong1","yangxiaodong2");
		
		for (String string : userCount) {
			System.out.println(string);
		} 
	}

	@Test
	public void test过期设置() { 
		Jedis jedis = JedisSource.getJedis(); 
		jedis.sadd("yangxiaodong", "yangxiaodong:11","yangxiaodong:22","yangxiaodong:33");
		jedis.expire("yangxiaodong", 5);//5秒过期 
		 
		
		//jedis.expireAt(key, unixTime) //指定， 何时过期
	}
	


	@Test
	public void test产生randomKey() { 
		Jedis jedis = JedisSource.getJedis();  
		System.out.println(jedis.randomKey());
	}
	
	@Test
	public void testlistPopPush() { 
		Jedis jedis = JedisSource.getJedis(); 
		jedis.sadd("yangxiaodong", "yangxiaodong:11","yangxiaodong:22","yangxiaodong:33");
		
		System.out.println(jedis.lpop("yangxiaodong")); 
		jedis.lpush("yangxiaodong", "yangxiaodong:44", "yangxiaodong:55");
		//jedis.expireAt(key, unixTime) //指定， 何时过期
	}
	 
	@Test
	public void test操作返回目前DB中KEY的数量() { 
		Jedis jedis = JedisSource.getJedis();
		System.out.println(jedis.dbSize()); 
	}

	 
	@Test
	public void test操作Long类型() { 

		Jedis jedis = JedisSource.getJedis();

		// PV-web访问量+1
		jedis.incr("yangxiaodon"); //如果没有这个key则从0开始。 
		jedis.incr("yangxiaodon"); //如果没有这个key则从0开始。 
		jedis.incr("yangxiaodon"); //如果没有这个key则从0开始。 

		Long webVisitor = Long.parseLong(jedis.get("yangxiaodon"));
		System.out.println(webVisitor);
		jedis.decr("yangxiaodon"); //如果没有这个key则从0开始。 
		jedis.decr("yangxiaodon"); //如果没有这个key则从0开始。 
		jedis.decr("yangxiaodon"); //如果没有这个key则从0开始。  
		
		webVisitor = Long.parseLong(jedis.get("yangxiaodon"));
		System.out.println(webVisitor);
		
		 
	}
	
	
	
	@Test
	public void test删除key() { 
		Jedis jedis = JedisSource.getJedis();

		if(!jedis.exists("yangxiaodong")) 
			jedis.sadd("yangxiaodong", "hello yangxiaodong");  
		//返回值 是被 删除的数目
		Long delNum =	jedis.del("yangxiaodong");
		 
		System.out.println(delNum);
	}
	
	
	

}
