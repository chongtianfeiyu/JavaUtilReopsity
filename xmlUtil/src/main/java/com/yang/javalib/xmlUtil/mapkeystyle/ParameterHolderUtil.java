package com.yang.javalib.xmlUtil.mapkeystyle;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 对ParameterHolder类进行序列化和反序列化的类.
 * @author Games Gao
 * @version 1.0 2006-5-24
 */
public class ParameterHolderUtil {
	private static final Logger logger = Logger
			.getLogger(ParameterHolderUtil.class);

	/**
	 * ParameterHolder的key值中可以包括的字符.
	 */
	private static final char[] PARAMETERHOLDER_KEY_CHAR = { 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '_', '.', '-' };

	/**
	 * 将ParameterHolder实例转化为一个xml字符串.
	 * 根节点是config.接下来就是Map 了.
	 *<config>   这种形式的. 
     *<i k="1" t="p">
	 * @param paramHolder
	 * @return
	 */
	public static String toXML(ParameterHolder paramHolder) {
		Element rootElement = new Element("config");
		Document document = new Document(rootElement);
		generateXML(paramHolder, rootElement);

		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));

		return outputter.outputString(document);

	}
	
	/**根据字符XML生成Map
	 * /config/i
	 * @param xml
	 * @return
	 */
	public static Map toMap(String xml){
		Map map=new HashMap();
		try {
			if(xml!=null&&!"".equals(xml)){
				org.dom4j.Document document= DocumentHelper.parseText(xml);
				List iList = document.selectNodes("/config/i");
				Iterator iterator = iList.iterator();
				while(iterator.hasNext()){
					org.dom4j.Element ele = (org.dom4j.Element)iterator.next();
					if(ele.getText()!=null&&!"".equals(ele.getText())){
					  map.put(ele.attributeValue("k"), ele.getText());
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将一个XML字符串转化为一个ParameterHolder实例.
	 *
	 * @param xml
	 * @return
	 */
	public static ParameterHolder toParameterHolder(String xml)
			throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(xml));
		Element rootElement = document.getRootElement();

		return generateParameterHolder(rootElement);
	}

	private static ParameterHolder generateParameterHolder(Element parent) {
		ParameterHolder paramHolder = new ParameterHolder();
		List children = parent.getChildren();
		int size = children.size();
		//child结构:<i k="" t="">value</i>
		for (int i = 0; i < size; i++) {
			Element ch = (Element) children.get(i);
			String key = ch.getAttributeValue("k");

			String type = ch.getAttributeValue("t");

			if (type.equals("s")) {
				String value = ch.getTextTrim();
				paramHolder.put(key, value);
			} else if (type.equals("p")) {
				ParameterHolder holder = generateParameterHolder(ch);
				paramHolder.put(key, holder);
			} else {
				//默认为String
				String value = ch.getTextTrim();
				paramHolder.put(key, value);
			}

		}

		return paramHolder;
	}

	/**
	 * Element的格式:
	 * <p>
	 * 	   <i k="" t="">value</key>
	 * 例如:<i k="ip" t="String">10.1.3.20</i>,其中t表示类型.
	 * </p>
	 * @param paramHolder
	 * @return
	 */
	private static void generateXML(ParameterHolder paramHolder, Element parent) {
		for (Iterator it = paramHolder.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();

			Object value = null;
			Element propElement = new Element("i");
			propElement.setAttribute("k", key);
			if (paramHolder.isParameterHolder(key)) {

				value = paramHolder.getParameterHolder(key);
				propElement.setAttribute("t", "p");
				generateXML((ParameterHolder) value, propElement);
			} else {
				value = paramHolder.getString(key);
				propElement.setAttribute("t", "s");
				propElement.setText(value.toString());
			}

			//			if (value instanceof Integer) {
			//				propElement.setAttribute("t", "i4");
			//				propElement.setText("" + ((Integer) value).intValue());
			//			} else if (value instanceof Long) {
			//				propElement.setAttribute("t", "i8");
			//				propElement.setText("" + ((Long) value).longValue());
			//			} else if (value instanceof Double) {
			//				propElement.setAttribute("t", "f8");
			//				propElement.setText("" + ((Double) value).doubleValue());
			//			} else if (value instanceof Float) {
			//				propElement.setAttribute("t", "f4");
			//				propElement.setText("" + ((Float) value).floatValue());
			//			} else if (value instanceof Short) {
			//				propElement.setAttribute("t", "i2");
			//				propElement.setText("" + ((Short) value).shortValue());
			//			} else if (value instanceof Byte) {
			//				propElement.setAttribute("t", "i1");
			//				propElement.setText("" + ((Byte) value).byteValue());
			//			} else if (value instanceof Boolean) {
			//				propElement.setAttribute("t", "bo");
			//				propElement.setText("" + ((Boolean) value).booleanValue());
			//			} else if (value instanceof BigDecimal) {
			//				propElement.setText(((BigDecimal) value).toString());
			//				propElement.setAttribute("t", "bd");
			//			} else if (value instanceof BigInteger) {
			//				propElement.setText(((BigInteger) value).toString());
			//				propElement.setAttribute("t", "bi");
			//			} else if (value instanceof String) {
			//				propElement.setText(value.toString());
			//				propElement.setAttribute("t", "s");
			//			} else if (value instanceof Timestamp) {
			//				propElement.setText("" + ((Timestamp) value).getTime());
			//				propElement.setAttribute("t", "t");
			//			} else if (value instanceof Date) {
			//				propElement.setText("" + ((Date) value).getTime());
			//				propElement.setAttribute("t", "d");
			//			} else if (value instanceof ParameterHolder) {
			//				propElement.setAttribute("t", "p");
			//				generateXML((ParameterHolder) value, propElement);
			//			} else {
			//				//默认为String
			//				propElement.setAttribute("t", "s");
			//				propElement.setText(value.toString());
			//			}

			parent.addContent(propElement);
		}
	}

	/**
	 * 将ParameterHolder变成一个java.util.Map对象．
	 *
	 * @param paramHolder
	 * @return
	 */
	public static Map toMap(ParameterHolder paramHolder) {

		return generateMap(paramHolder);
	}

	/**
	 * 将ParameterHolder变成一个java.util.Map对象．采用迭代和递归的方法实现．
	 *
	 * @param paramHolder
	 * @return
	 */
	private static Map generateMap(ParameterHolder paramHolder) {
		Map holderMap = new LinkedHashMap();
		for (Iterator it = paramHolder.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();

			if (paramHolder.isParameterHolder(key)) {

				holderMap.put(key, generateMap(paramHolder
						.getParameterHolder(key)));
			} else {
				holderMap.put(key, paramHolder.getString(key));
			}
		}

		return holderMap;

	}

	/**
	 * 将一个java.util.Map对象变成一个ParameterHolder对象．
	 *
	 * @param cfg
	 * @return
	 */
	public static ParameterHolder toParameterHolder(Map cfg) {
		return generateParameterHolder(cfg);
	}

	/**
	 * 将一个java.util.Map对象变成一个ParameterHolder对象,采用迭代和递归的方法实现．
	 *
	 * @param cfg
	 * @return
	 */
	private static ParameterHolder generateParameterHolder(Map cfg) {
		ParameterHolder holder = new ParameterHolder();
		for (Iterator it = cfg.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();

			Object value = cfg.get(key);
			if (value instanceof Map) {
				holder.put(key, generateParameterHolder((Map) value));
			} else {
				holder.put(key, (String) value);
			}
		}
		return holder;
	}

	/**
	 * 检查key中是否有非法的字符,返回字符的位置.
	 *
	 * @param key
	 * @return 字符的位置.
	 * @throws
	 */
	public static void checkKey(String key) throws IllegalArgumentException {
		if (key == null)
			return;

		for (int i = 0; i < key.length(); i++) {
			char rv = key.charAt(i);
			boolean flag = false;
			for (int j = 0; j < PARAMETERHOLDER_KEY_CHAR.length; j++) {
				if (rv == PARAMETERHOLDER_KEY_CHAR[j]) {
					flag = true;
					break;
				}
			}
			//如果找到非法的字符,则应该抛出异常.
			if (!flag) {
				throw new IllegalArgumentException("Unexpect character : "
						+ key.charAt(i) + ", {" + key + "} locate at "
						+ (i + 1) + " .");
			}
		}

	}
}