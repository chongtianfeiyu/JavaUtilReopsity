package com.yang.javalib.xmlUtil.mapkeystyle;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @Description: 这一个是在CommonJDBCSingleProbe中的应用 , 主要是把 ParameterHolder再转换成为一个XML配置文件
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2012-11-16 下午3:46:45 
 */
public class CommonJDBCSingleProbe {

	private static final Logger logger = Logger
			.getLogger(CommonJDBCSingleProbe.class);

	private static String resultFlag = "n1";

	private static String dbStr = "SingleCommonJdbcDb";

	private String indicatorName = "";
	private String instanceName = "";
	private String driver = "";
	private String url = "";
	private String userName = "";
	private String passWord = "";
	private String sqlstr = "";
	private String sqlResult = "";

	public void start() throws Exception {
		ParameterHolder params = null;
		try {
			params.put("file", generateDom(params));

		} catch (Exception e) {
			logger.warn("", e);
		}
	}

	private String generateDom(ParameterHolder params) throws Exception {
		logger.info("开始构造XML文件");
		this.indicatorName = SharedParameters.parseString(params,
				"indicatorName");
		this.instanceName = SharedParameters
				.parseString(params, "instanceName");
		this.driver = SharedParameters.parseString(params, "driver");
		this.url = SharedParameters.parseString(params, "url");
		this.userName = SharedParameters.parseString(params, "userName");
		this.passWord = SharedParameters.parseString(params, "passWord");
		this.sqlstr = SharedParameters.parseString(params, "sqlstr");
		this.sqlResult = SharedParameters.parseString(params, "result");

		Map<String, String> attrNameValue = new HashMap<String, String>();

		Document doc = org.dom4j.DocumentHelper.createDocument();

		Element probe = doc.addElement("probe");
		Element config = probe.addElement("config");

		Element instanceNameEl = config.addElement("item");
		attrNameValue.put("name", "indicatorNames");
		attrNameValue.put("value", indicatorName);
		setAttrsToElement(instanceNameEl, attrNameValue);

		Element resourceNameEl = config.addElement("item");

		attrNameValue.put("name", "instanceName");
		attrNameValue.put("value", instanceName);
		setAttrsToElement(resourceNameEl, attrNameValue);

		addDbElements(config);
		addSqlElements(config);
		addResultElements(config);

		logger.info("开始构造XML文件完毕");
		if (logger.isDebugEnabled()) {
			logger.debug(doc.asXML());
		}

		return doc.asXML();
	}

	/**
	 * 添加数据库设置 部分
	 * @param config
	 */
	private void addDbElements(Element config) {
		Map<String, String> attrNameValue = new HashMap<String, String>();

		Element databaseEl = config.addElement("items");
		attrNameValue.put("name", "database");
		setAttrsToElement(databaseEl, attrNameValue);

		Element db1El = databaseEl.addElement("items");
		attrNameValue.put("name", dbStr);
		setAttrsToElement(db1El, attrNameValue);

		Element driverEl = db1El.addElement("item");
		attrNameValue.put("name", "driver");
		attrNameValue.put("value", driver);
		setAttrsToElement(driverEl, attrNameValue);
		Element urlEl = db1El.addElement("item");
		attrNameValue.put("name", "url");
		attrNameValue.put("value", url);
		setAttrsToElement(urlEl, attrNameValue);

		Element userNameEl = db1El.addElement("item");
		attrNameValue.put("name", "userName");
		attrNameValue.put("value", userName);
		setAttrsToElement(userNameEl, attrNameValue);

		Element passWordEl = db1El.addElement("item");
		attrNameValue.put("name", "passWord");
		attrNameValue.put("value", passWord);
		setAttrsToElement(passWordEl, attrNameValue);
	}

	/**
	 * 添加 数据库SQL语句部分
	 * @param config
	 */
	private void addSqlElements(Element config) {
		Map<String, String> attrNameValue = new HashMap<String, String>();

		Element sqlEl = config.addElement("items");
		attrNameValue.put("name", "sql");
		setAttrsToElement(sqlEl, attrNameValue);

		Element sql1El = sqlEl.addElement("items");
		attrNameValue.put("name", resultFlag);
		setAttrsToElement(sql1El, attrNameValue);

		Element dataBaseEl = sql1El.addElement("item");
		attrNameValue.put("name", "database");
		attrNameValue.put("value", dbStr);
		setAttrsToElement(dataBaseEl, attrNameValue);

		Element sqlStrEl = sql1El.addElement("item");
		attrNameValue.put("name", "sqlstr");
		attrNameValue.put("value", sqlstr);
		setAttrsToElement(sqlStrEl, attrNameValue);

	}

	/**
	 * 添加结果部分..
	 * @param config
	 */
	private void addResultElements(Element config) {
		Map<String, String> attrNameValue = new HashMap<String, String>();

		Element resultsEl = config.addElement("items");
		attrNameValue.put("name", "result");
		setAttrsToElement(resultsEl, attrNameValue);

		Element r1El = resultsEl.addElement("item");
		attrNameValue.put("name", "R1");
		attrNameValue.put("value", sqlResult);
		setAttrsToElement(r1El, attrNameValue);
	}

	private static void setAttrsToElement(Element element,
			final Map<String, String> attrNameValue) {

		for (Map.Entry<String, String> entry : attrNameValue.entrySet()) {
			String attrName = entry.getKey();
			String attrValue = entry.getValue();
			element.addAttribute(attrName, attrValue);
		}
		attrNameValue.clear();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		CommonJDBCSingleProbe singleCommonJDBCProbe = new CommonJDBCSingleProbe();
		String temp = singleCommonJDBCProbe.generateDom(null);
		System.out.println(temp);
	}

}
