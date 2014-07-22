package com.yang.javalib.xmlUtil.xml;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
 

/**
 * @Description: 读取日志时,配置管理器
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-4-13 下午2:31:41 
 */
public class XmlConfigManager {

	private static final Logger log = Logger.getLogger(XmlConfigManager.class);

	private String Key_config_fileLoc = "conf/logConfig.xml";

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Document xmlDoc;
	  

	public XmlConfigManager(String xml_path) throws Exception {
		super();
		this.Key_config_fileLoc = xml_path;
		init();
	}

	/**
	 * @Description: 如果XML文件 不存在就初始化, 如果存在就加载.
	 * @throws Exception
	 */
	private  void init() throws Exception {
		if (!new File(Key_config_fileLoc).exists()) {
			//第一次运行
			xmlDoc = Dom4jUtils.getEmptyDoc();
			Dom4jUtils.addElement(xmlDoc, "configs");//初始始化根节点. 
			Dom4jUtils.saveDom(xmlDoc, Key_config_fileLoc);
		} else {
			xmlDoc = Dom4jUtils.getDoc(Key_config_fileLoc);
		}
	}

	/**
	 * @throws IOException 
	 * @Description: 保存配置
	 * @throws DocumentException
	 */
	private   void saveConfig() throws IOException {
		Dom4jUtils.saveDom(xmlDoc, Key_config_fileLoc);
	}

	/**
	 * @Description: 不同采集器信息当然不一样了.
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public   void addProbeConfig(String collectorId, String readedCharCount)
			throws IOException {
		Element configs = xmlDoc.getRootElement();

		Element config = Dom4jUtils.addElement(configs, "config", "id="
				+ collectorId, "");

		Dom4jUtils.addElement(config, "item", "id=lastDate",
				 simpleDateFormat.format(new Date()));
		Dom4jUtils.addElement(config, "item", "id=num", readedCharCount + "");
		saveConfig();
	}

	/**
	 * @Description: 更新日志采集器最后时间配置 .
	 * @param collectorId
	 * @param readedCharCount
	 * @throws IOException
	 */
	public  void updateProbeConfig(String collectorId,
			String readedCharCount) throws IOException {
		Node dateNode = getCollectorDateNode(collectorId);
		dateNode.setText(simpleDateFormat.format(new Date()));
		Node countNode = getCollectorCountNode(collectorId);
		countNode.setText(readedCharCount);
		saveConfig();
	}

	/**
	 * @Description: 得到时间 的NODE. 
	 * @param collectorID
	 * @return
	 */
	public   Node getCollectorDateNode(String collectorID) {
 
		Node date = Dom4jUtils.getXpathNode(xmlDoc, "/configs/config[@id='"
				+ collectorID + "']/item[@id='lastDate']");
		return date;
	}

	/**
	 * @Description: 得到时间 的. 
	 * @param collectorID
	 * @return
	 */
	public   String getCollectorDateStr(String collectorID) {
	 
		String content  = Dom4jUtils.getXpathStr(xmlDoc, "/configs/config[@id='"
				+ collectorID + "']/item[@id='lastDate']");
		return content;
	}

	public  Node getCollectorCountNode(String collectorID) {

		Node count = Dom4jUtils.getXpathNode(xmlDoc, "/configs/config[@id='"
				+ collectorID + "']/item[@id='num']");
		return count;
	}
	public  String getCollectorCountStr(String collectorID) {

		String count = Dom4jUtils.getXpathStr(xmlDoc, "/configs/config[@id='"
				+ collectorID + "']/item[@id='num']");
		return count;
	}
}
