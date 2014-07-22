package com.yang.javalib.xmlUtil.mapkeystyle.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.xmlUtil.mapkeystyle.ParameterHolder;

public class TemplateManagerServiceFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 对于不存在的文件,会自动建立,
	 */
	@Test
	public void test1TemplateManagerServiceFactoryUseCase() {
		
		DefaultTemplateManagerService templateManagerService =	TemplateManagerServiceFactory.getTemplateManagerServiceInstance("test/usecase1.xml");
		ParameterHolder paramHolderTemplate =new ParameterHolder();
		paramHolderTemplate.put("aa", 11+"");
		paramHolderTemplate.put("bb", 22+"");
		paramHolderTemplate.put("cc", 33+"");
		
		templateManagerService.addOneTemplate("tempItem1", paramHolderTemplate);
		templateManagerService.saveTemplate() ;
		
		//TODO 
		
	}

}
