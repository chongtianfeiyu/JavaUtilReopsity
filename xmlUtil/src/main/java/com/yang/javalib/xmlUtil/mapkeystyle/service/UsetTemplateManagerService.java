package com.yang.javalib.xmlUtil.mapkeystyle.service;

import com.yang.javalib.xmlUtil.mapkeystyle.ParameterHolder;

/**
 * @Description: 用户对象可以继承的类,实现 以下两个方法就可以了.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-30 下午10:07:23 
 * @param <T>
 */
public abstract class UsetTemplateManagerService<T> extends DefaultTemplateManagerService implements ITemplateTransferAdapter<T> {

	public UsetTemplateManagerService(String tempLateFileLocPath) {
		super(tempLateFileLocPath); 
	}
	
	public Boolean addOneTemplate(String templateName,
			T t) {
		return super.addOneTemplate(templateName, changObject2ParameterHolder(t));
	}
	  
	public T getOneTemplateT(String templateKey) {
		return changParameterHolder2Object(super.getOneTemplate(templateKey));
	}
  
	
	/*
	@Override
	public ParameterHolder changObject2ParameterHolder(T object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T changParameterHolder2Object(ParameterHolder parameterHolder) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	
}
