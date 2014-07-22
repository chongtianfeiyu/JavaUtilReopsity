package com.yang.javalib.xmlUtil.mapkeystyle;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set; 


public class ParamtersUtil {

	/**
	 * 得到一个分组的全部信息
	 * 
	 * <items name="group"> <item name="maxThread" value="maxThread"/> <item
	 * name="currentThreadCount" value="currentThreadCount"/> </items> 结果:
	 * (group,{maxThread,currentThreadCount}
	 * 
	 * @param params
	 * @return
	 * @throws ProbeException
	 */
	public static Map<String, String> getItems(ParameterHolder params)
			throws  Exception {
		// 这样就保证 了返回 的顺序.
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		Set<String> KeySet = params.keySet();
		 
		int keySize = KeySet.size();
		String groupName; 
		//String[] attrValues = new String[keySize]; 
		Iterator<String> iterator = KeySet.iterator();

		for (int i = 0; iterator.hasNext(); i++) {
			String attrName = iterator.next();
			String attrValue = SharedParameters.parseString(params, attrName); 
			resultMap.put(attrName, attrValue); 
		}
		return resultMap;
	} 
	/** 得到配置文件中 一个指定 的. ParameterHolder 即一个 items分组
	 * 对于分组不明确. ??? 适用范围 是?  不应该是这样, 应该是排除在外的 数传入.
	 * @param params
	 */
	public void getParameterHolderItem(ParameterHolder params, String groupName)
	{
		ParameterHolder parameterHolder ;
		Set<String> KeySet = params.keySet();
		Iterator<String> iterator = KeySet.iterator();
		
		for (int i = 0; iterator.hasNext(); i++) {
			String attrName = iterator.next();
			
			if(groupName.equals(attrName))
			{
				parameterHolder=params.getParameterHolder(attrName);  
			}
		}
		
		
		
	}
}
