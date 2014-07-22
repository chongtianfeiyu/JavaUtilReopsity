package com.yang.javalib.mavenUtil.mavenUtil;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MavenUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 处理下载失败的库文件. 不把这些删除掉,maven就不会下载新的. 
	 * void
	 * @throws
	 */
	@Test
	public void testDelFailedPath() {

		//String baseRepoPath ="D:/maven_resource_myeclipse/repository/";
		//对应 的绝对路径 为: D:\maven_resource_myeclipse\repository\org\mvel\mvel2\2.1.3.Final\mvel2-2.1.3.Final.jar
		
		String[] failPath=new String[]{"org.olap4j:olap4j:jar:0.9.7.309-JS-3"};
		
		MavenUtil.delFailedPath(failPath);
		
		
		
	}

}
