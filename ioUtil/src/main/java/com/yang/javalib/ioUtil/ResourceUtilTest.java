package com.yang.javalib.ioUtil;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourceUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 得到包下的资源文件
	 * void
	 * @throws
	 */
	@Test
	public void testGetResourcePath() {
		System.out.println(ResourceUtil.getResourcePath("/log4j.properties")); 
	}
	
	@Test
	public void testGetResourceURL() {
		System.out.println(ResourceUtil.getResourceURL("/log4j.properties"));
	 
	}

}
