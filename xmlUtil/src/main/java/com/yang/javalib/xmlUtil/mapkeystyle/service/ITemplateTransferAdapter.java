package com.yang.javalib.xmlUtil.mapkeystyle.service;

import com.yang.javalib.xmlUtil.mapkeystyle.ParameterHolder;

/**
 * @Description: 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-30 下午7:33:08 
 */
public interface ITemplateTransferAdapter <T> {
	
	/**
	 * @Description: 把自定义的对象转换为ParameterHolder 方便 存储
	 * @param object
	 * @return
	 */
	public ParameterHolder changObject2ParameterHolder(T  object); 
	
	/**
	 * @Description: 把ParameterHolder存储对象转换为使用的对象 . ,
	 * @param parameterHolder
	 * @return
	 */
	public T changParameterHolder2Object(ParameterHolder parameterHolder); 

}
