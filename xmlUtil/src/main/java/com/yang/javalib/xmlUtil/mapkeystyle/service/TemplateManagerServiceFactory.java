package com.yang.javalib.xmlUtil.mapkeystyle.service;

public class TemplateManagerServiceFactory {
	
	/**
	 * @Description: 得到 一个TemplateManagerService的实例 . 
	 * @param tempLateFileLocPath
	 * @return
	 */
	public static DefaultTemplateManagerService  getTemplateManagerServiceInstance(String tempLateFileLocPath){ 
		return new DefaultTemplateManagerService(tempLateFileLocPath);
	}
	
	
}
