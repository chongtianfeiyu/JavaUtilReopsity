package com.yang.javalib.xmlUtil.mapkeystyle.service;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import com.yang.javalib.xmlUtil.mapkeystyle.ParameterHolder;
import com.yang.javalib.xmlUtil.mapkeystyle.ParameterHolderUtil;

/**
 * @Description: 模板管理服务. 
 * 使用.ParameterHolder 存储起来 ;
 * 目前只适用于列表类型,不存在层级结构
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-19 下午1:18:36 
 */
public class DefaultTemplateManagerService {

	private static final Logger log = Logger
			.getLogger(DefaultTemplateManagerService.class);

	private  ParameterHolder templateStore = new ParameterHolder();

	private  String tempLateFileLocPath ;
	 
	public  String getTempLateFileLocPath() {
		return tempLateFileLocPath;
	}

	public DefaultTemplateManagerService(String tempLateFileLocPath) {
		super();
		this.tempLateFileLocPath = tempLateFileLocPath;
		reLoadTemplate();
	}

	public  void setTempLateFileLocPath(String tempLateFileLocPath) {
		this.tempLateFileLocPath = tempLateFileLocPath;
	}

	/**
	 * @Description: 重新加载模板
	 * @param templateXMlFileLoc
	 */
	public  void reLoadTemplate() {

		try {
			File tempsLoc =new File( tempLateFileLocPath);
			if(tempsLoc.exists()){
				String smlContent = FileUtils.readFileToString(tempsLoc, "UTF-8"); 
				templateStore = ParameterHolderUtil.toParameterHolder(smlContent);
			}else {
				log.info("文件不存在 ");
			}
		} catch (IOException e) {
			log.warn("", e);
		} catch (JDOMException e1) {
			log.warn("", e1);
		}
	}

	/**
	 * @Description: 存储模板
	 * @param templateXMlFileLoc
	 */
	public  void saveTemplate() {
		String xmlStr = ParameterHolderUtil.toXML(templateStore);
		try {
			FileUtils.writeStringToFile(new File(tempLateFileLocPath),
					xmlStr, "UTF-8");
		} catch (IOException e) {
			log.warn("", e);
		}
	}

	/**
	 * @Description: 添加一个模板
	 * @param paramHolderTemplate
	 */
	public  Boolean addOneTemplate(String templateName,
			ParameterHolder paramHolderTemplate) {

		if (!templateStore.containsKey(templateName)) //不包含才能添加. 
		{
			templateStore.put(templateName, paramHolderTemplate);
			saveTemplate();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description: 添加一个模板
	 * @param paramHolderTemplate
	 */
	public  String[] getAllTemplateKey() {
		if (templateStore == null)
			return new String[] {};

		Set<String> templateKeyCol = templateStore.keySet();
		return templateKeyCol.toArray(new String[] {});

	}

	/**
	 * @Description: 删除一个模板
	 * @param templateKey
	 */
	public  Boolean deleteOneTemplate(String templateKey) {

		if (templateStore.containsKey(templateKey)) //包含才删除. 
		{
			templateStore.remove(templateKey);
			saveTemplate();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description: 取得一个模板
	 * @param templateKey
	 * @return
	 */
	public  ParameterHolder getOneTemplate(String templateKey) {

		if (templateStore.containsKey(templateKey)) //包含才可以取得. 
		{
			return templateStore.getParameterHolder(templateKey);

		} else {
			return null;
		}
	}

	/**
	 * @Description: 验证是否已包含某一模板名称
	 * @param templateKey
	 */
	public Boolean isContainKey(String templateKey) {
		return templateStore.containsKey(templateKey);
	}
}
