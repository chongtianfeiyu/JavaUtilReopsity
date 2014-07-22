package com.yang.javalib.threadUtil;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ThreadPoolUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		ThreadPoolUtil threadPoolUtil =ThreadPoolUtil.getInstance();
		threadPoolUtil.add(new Runnable() {
			public void run() {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
			}
		}) ;
		threadPoolUtil.add(new Runnable() {
			public void run() {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
			}
		}) ;
		 
	}

}
