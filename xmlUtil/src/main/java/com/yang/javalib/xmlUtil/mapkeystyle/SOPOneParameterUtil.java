package com.yang.javalib.xmlUtil.mapkeystyle;
 
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

/**
 * 用于解析参数配置。
 * 
 * @author zhangrongc
 */
public class SOPOneParameterUtil {

	/**
	 * 配置文件的内容。
	 */
	public static final String PARAMETER_CONFIG = "file";

	/**
	 * 解析出 config。
	 * 
	 * @param parameterHolder the parameter holder
	 * @return the parameter holder
	 * @throws CollectorException the collector exception
	 */
	public static ParameterHolder getParameterHolder(
			final ParameterHolder parameterHolder) throws Exception {
		String fileContent = parameterHolder.getString(PARAMETER_CONFIG);

		if (StringUtils.isBlank(fileContent))
			throw new Exception("file 属性没有内容。");

		try {
			return SOPOneParameterUtil.getParameterHolder(fileContent);
		} catch (DocumentException e) {
			throw new Exception("参数解析时出现问题", e);
		} catch (Exception e) {
			throw new Exception("参数解析时出现问题", e);
		}
	}

	/**
	 * 解析出 config。
	 * 
	 * @param fileContent
	 * @return
	 * @throws CollectorException
	 */
	private static ParameterHolder getParameterHolder(final String fileContent)
			throws Exception {
		if (StringUtils.isBlank(fileContent))
			throw new Exception("没有文件内容。");

		ParameterHolder parameterHolder;

		org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
		try {
			org.dom4j.Document document = reader.read(new StringReader(
					fileContent));

			org.dom4j.Element element = (org.dom4j.Element) document
					.selectSingleNode("//probe");

			ProbeProperty probeProperty = new ProbeProperty(element);

			parameterHolder = probeProperty.getConfig();
		} catch (Exception e) {
			throw new Exception("参数解析时出现问题", e);
		}

		return parameterHolder;
	}

}
