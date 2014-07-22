package com.yang.javalib.security.encode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MD5Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 解密: http://www.cmd5.com/
	 */
	@Test
	public void testEncode() {
		System.out.println(MD5.encode("servicedesk"));
		
	}

}
