package com.yang.javalib.xmlUtil.mapkeystyle;

import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder; 

public class ProbeProperty {

	private static final Logger logger = Logger.getLogger(ProbeProperty.class);

	private ParameterHolder config;

	public ProbeProperty(final org.dom4j.Element element) throws Exception {
		this.config = createParameterHolder(element.element("config").asXML());
	}

	/**
	 * @Description: 将XML 形式的转换为, ParameterHolder
	 *   item  items
	 * @param config_xml
	 * @return
	 */
	private static ParameterHolder createParameterHolder(String config_xml) {
		Element root = null;
		try {
			SAXBuilder builder = new SAXBuilder(
					"org.apache.xerces.parsers.SAXParser");
			Document document = builder.build(new StringReader(config_xml));
			root = document.getRootElement();
		} catch (Exception ex) {
			logger.error("解析用户输入的采集器配置时出错：" + ex.getMessage(), ex);
			return null;
		}

		ParameterHolder cfg = null;

		try {
			cfg = ParameterHolderUtil.toParameterHolder(parseByElemnt(root));
		} catch (Exception ex) {
			logger.error("解析采集器配置信息时出错：" + ex.getMessage(), ex);
			return null;
		}

		return cfg;
	}

	/**
	 * 解析用户输入的采集器配置信息。如果不符合格式则抛出异常。
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map parseByElemnt(Element root) throws Exception {
		Map cfgMap = new LinkedHashMap();
		List children = root.getChildren();
		for (Iterator it = children.iterator(); it.hasNext();) {
			Element item = (Element) it.next();
			String type = item.getName().trim();
			if (type.equalsIgnoreCase("item")) {
				String name = item.getAttribute("name").getValue();
				String value = item.getAttribute("value").getValue();
				cfgMap.put(name, value);
			} else if (type.equalsIgnoreCase("items")) {
				String name = item.getAttribute("name").getValue();
				Map subMap = parseByElemnt(item);
				cfgMap.put(name, subMap);
			} else {
				throw new Exception("Illegal xml format,unkown label: " + type);
			}
		}

		return cfgMap;
	}

	public ParameterHolder getConfig() {
		return config;
	}

	@Override
	public String toString() {
		return "ProbeProperty [config=" + config + "]";
	}

}
