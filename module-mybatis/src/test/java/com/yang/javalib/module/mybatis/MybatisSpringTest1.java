package com.yang.javalib.module.mybatis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yang.javalib.module.mybatis.dao.UserDAO;
import com.yang.javalib.module.mybatis.mapper.UserMapper;
import com.yang.javalib.module.mybatis.pojo.User;

/**
 * @Description: 一种启动方式 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-2 下午9:24:57 
 */
public class MybatisSpringTest1 {
	
	private ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		  context = new ClassPathXmlApplicationContext("applicationContext_mybatis.xml");  
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 测试查询，使用的是基本的配置文件的方式．在实现类UserDAOImpl中
	 * void
	 * @throws
	 */
	@Test
	public void test1Query() {
		    UserDAO userDao = (UserDAO)context.getBean("userDAO");  
		    System.out.println(userDao.getClass().getName());  
		    User user = userDao.queryUserById(1);  
		    System.out.println(user.getUsername()); 
	}

	/**
	 * @Description: 以 数据映射器 wrapper的方式运行的结果　．
	 * void
	 * @throws
	 */
	@Test
	public void test2Wrapper() {
		UserMapper userMapper =(UserMapper)context.getBean("userMapper");  
		User user =userMapper.get(1);
	    System.out.println(user.getUsername()); 
	}
}
