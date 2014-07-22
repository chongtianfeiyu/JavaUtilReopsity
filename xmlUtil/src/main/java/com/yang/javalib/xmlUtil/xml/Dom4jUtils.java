package com.yang.javalib.xmlUtil.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @Description: Dom4j 工具 类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-5 下午2:03:20 
 */
public class Dom4jUtils {

	private static final Logger log = Logger.getLogger(Dom4jUtils.class);

	public static void main(String[] args) throws DocumentException {

		Document xmlDoc = Dom4jUtils.getEmptyDoc();

		Element configs = Dom4jUtils.addElement(xmlDoc, "configs");
		Element config = Dom4jUtils.addElement(configs, "config",
				"id=cjsc_hawkEyeLogConfig", "");

		Dom4jUtils.addElement(config, "item", "id=lastDate", "12312");
		Dom4jUtils.addElement(config, "item", "id=ReadCount", "123123");

		try {
			Dom4jUtils.saveDom(xmlDoc, "conf/logConfig.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 保存 配置
	 * @param xmlDoc
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static void saveDom(Document xmlDoc, String filePath)
			throws IOException {

		org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(
				new FileOutputStream(filePath));
		xmlWriter.write(xmlDoc);
		xmlWriter.close();

	}

	public static String getXpathStr(Element element, String xpathStr) {
		Node node = element.selectSingleNode(xpathStr);
		String content = element.selectSingleNode(xpathStr).getText();

		return content;
	}

	/**
	 * @Description: 得到xml文件对应的一个XPATH配置信息.
	 选取第一个 book 的 title Node : /bookstore/book[1]/title
	下面的例子选取 price 节点中的所有文本:
	
	 * @param xmlPath
	 * @param xpathStr
	 * @return
	 * @throws DocumentException
	 */
	public static String getXmlXpathValue(String xmlPath, String xpathStr)
			throws DocumentException {
		return getXpathStr(getDoc(xmlPath), xpathStr);
	}

	/**
	 * @Description: TODO
	 * @param root
	 * @param attrValue  属性键值=对. 多个以_@@_分隔 
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	public static Element addElement(Element root, String elementName,
			String attrValue, String text) {
		Element subElement = root.addElement(elementName);

		if (StringUtils.isNotBlank(attrValue)) {
			String[] attrsValueArray = attrValue.split("_@@_");

			for (String itemStr : attrsValueArray) {
				String[] attrValueitem = itemStr.split("=");
				subElement.addAttribute(attrValueitem[0], attrValueitem[1]);
			}
		}
		if (StringUtils.isNotBlank(text))
			subElement.setText(text);

		return subElement;
	}

	public static Element addElement(Document xmlDoc, String elementName) {
		Element rootElement = xmlDoc.addElement(elementName);
		return rootElement;
	}

	/**
	 * @Description: 从XML文件生成Document类．
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDoc(String xmlPath) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		return document;
	}

	/**
	 * @Description: 得到一个空的
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static Document getEmptyDoc() throws DocumentException {
		return org.dom4j.DocumentHelper.createDocument();
	}

	/**
	 * @Description:  得到Element中对应xpath的Text内容
	 * @param element
	 * @param xpathStr :fg: "//driver/@value"
	 * @return
	 */
	public static String getXpathStr(Document document, String xpathStr) {

		Node node = document.selectSingleNode(xpathStr);
		if(node!=null) 
			return node.getText();
		else {
			return null ;
		}
	}

	public static Node getXpathNode(Document document, String xpathStr) {

		return document.selectSingleNode(xpathStr);
	}

	/**
	 * doc2String 将xml文档内容转为String *
	 * 
	 * @return 字符串
	 * @param document @
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//使用utf-8编码
			OutputFormat format = new OutputFormat("  ", true, "utf-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("utf-8");
		} catch (Exception ex) {
			// ex.printStackTrace();
			log.error(ex);
		}
		return s;
	}

}
