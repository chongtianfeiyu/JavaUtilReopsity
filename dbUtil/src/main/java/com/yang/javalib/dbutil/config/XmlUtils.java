package com.yang.javalib.dbutil.config;

import java.io.File;

import org.dom4j.Element;

import com.yang.javalib.dbutil.MyDbPool;
import com.yang.javalib.dbutil.common.BaseConnBean;
 

/**
 * @Description: 读取XML 配置文件 ，使用dom4j 的xpath方式
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-20 下午2:13:53 
 */
public class XmlUtils {

	static final String configPath = "DbConfig.xml";
 
	public static BaseConnBean getConnBean() throws Exception {

		org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
		org.dom4j.Document document = reader.read(new File(configPath));

		Element rootDataBase = document.getRootElement();

		String driver = rootDataBase.selectSingleNode("//driver/@value")
				.getText();
		String jdbcurl = rootDataBase.selectSingleNode("//url/@value")
				.getText();
		String username = rootDataBase.selectSingleNode("//uid/@value")
				.getText();
		String password = rootDataBase.selectSingleNode("//pwd/@value")
				.getText();

		BaseConnBean baseConnBean = new BaseConnBean("PiLiangShiZhi", driver,
				jdbcurl, username, password);
		MyDbPool.addConnBean(baseConnBean);

		return baseConnBean;
	}

}
