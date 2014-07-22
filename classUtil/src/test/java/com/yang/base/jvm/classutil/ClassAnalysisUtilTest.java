package com.yang.base.jvm.classutil;

import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.classUtil.ClassAnalysisUtil;

public class ClassAnalysisUtilTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	} 
		

	@Test
	public void testGetFullClassName() {
		  Class cls = java.util.Date.class;  
	        // 加载字节码 封装成一个Class对象  
		  System.out.println( ClassAnalysisUtil.getFullClassName(cls)); 
	}

	@Test
	public void testGetSimpleClassName() {
		  Class cls = java.util.Date.class;  
	        // 加载字节码 封装成一个Class对象  
		  System.out.println( ClassAnalysisUtil.getSimpleClassName(cls));  
	}
	@Test
	public void test2getClassModifiers() {
		ClassAnalysisUtil.getClassModifiers(LinkedHashMap.class);  
		
	} 
	
	@Test
	public void test3GetClassTypeVariable() { 
		   ClassAnalysisUtil.getClassTypeVariable(LinkedHashMap.class);  
		
	}
	@Test
	public void test4getClassDeclaredFields() {  
		ClassAnalysisUtil.getClassDeclaredFields(LinkedHashMap.class);  
		
	}

	@Test
	public void test5getClassSupperClasss() {  
		ClassAnalysisUtil.getClassSupperClasss(LinkedHashMap.class);  
		
	}
	@Test
	public void test6getClassInterfaces() {  
		ClassAnalysisUtil.getClassInterfaces(LinkedHashMap.class);  
		
	}
	@Test
	public void test7getInnerOuterClasses() {  
		ClassAnalysisUtil.getInnerOuterClasses(LinkedHashMap.class);  
		
	}
	

}
