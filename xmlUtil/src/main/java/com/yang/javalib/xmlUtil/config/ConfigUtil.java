package com.yang.javalib.xmlUtil.config;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @Description: 读取 conf/base.xml下的配置文件
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-4-23 上午8:16:57 
 */
public class ConfigUtil {
	private static final Logger logger = Logger.getLogger(ConfigUtil.class);
	private static String basePath;
	private static HashMap appConfigMap = new HashMap();
	private static String localIps;

	public static String getBasePath() {
		return basePath;
	}

	public static void init() throws Throwable {
		logger.info("开始读入系统配置文件......");

		basePath = Thread.currentThread().getContextClassLoader()
				.getResource("").toURI().getPath();
		if (basePath.endsWith("/bin/")) {
			basePath = basePath.substring(0, basePath.lastIndexOf("bin/"));
		}

		String configPath = basePath + "conf/base.xml";
		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new FileInputStream(configPath));
			Element root = doc.getRootElement();
			List rootList = root.getChildren();

			for (int i = 0; i < rootList.size(); ++i) {
				Element rootEle = (Element) rootList.get(i);
				List childList = rootEle.getChildren();

				if ((childList != null) && (childList.size() > 0)) {
					HashMap itemMap = new HashMap();

					for (int j = 0; j < childList.size(); ++j) {
						Element childEle = (Element) childList.get(j);
						String idKey = childEle.getAttribute("id").getValue();
						itemMap.put(idKey, childEle.getText());
					}

					appConfigMap.put(rootEle.getAttribute("id").getValue(),
							itemMap);
				}
			}

			Parameters.FILE_IN_PATH = getConfigValue("JobConfig", "FileInPath")
					.split(";");
			Parameters.FILE_TMP_PATH = getConfigValue("JobConfig",
					"FileTmpPath");
			Parameters.FILE_ERR_PATH = getConfigValue("JobConfig",
					"FileErrPath");
			Parameters.FILE_BAK_PATH = getConfigValue("JobConfig",
					"FileBakPath");

			Parameters.RESOURCE_STATUS_KPI_CLASS_CODE = getConfigValue(
					"SystemConfig", "resource_status_kpi_class_code");
			Parameters.RESOURCE_STATUS_KPI_CODE = getConfigValue(
					"SystemConfig", "resource_status_kpi_code");
			Parameters.WARN_FILE_PATH = getConfigValue("SystemConfig",
					"warnFilePath");
			Parameters.RESOURCE_STATUS_INVALID = getConfigValue("SystemConfig",
					"resource_status_invalid");
			Parameters.RESOURCE_STATUS_VALID = getConfigValue("SystemConfig",
					"resource_status_valid");
			Parameters.SEND_EVENT_INTERVAL = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"SendEventInterval")));

			Parameters.EVENT_DEAL_BLOCK_SIZE = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"EventDealBlockSize")));
			Parameters.EVENT_DEAL_THRESHOLD = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"EventDealThreshold")));
			Parameters.EVENT_SMALL_DATA_DEAL_INTERVAL = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"EventSmallDataDealInterval")));

			Parameters.PERF_DATA_DEAL_BLOCK_SIZE = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"PerfDataDealBlockSize")));
			Parameters.PERF_DATA_DEAL_THRESHOLD = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"PerfDataDealThreshold")));
			Parameters.PERFDATA_SMALL_DATA_DEAL_INTERVAL = Integer
					.valueOf(Integer.parseInt(getConfigValue("SystemConfig",
							"PerfDataSmallDataDealInterval")));

			Parameters.BALANCE_STATUS = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig", "BalanceStatus")));
			Parameters.REFRESH_CACHE_WHEN_START = Integer.valueOf(Integer
					.parseInt(getConfigValue("SystemConfig",
							"RefreshCacheWhenStart")));

			HashMap levelMappingMap = (HashMap) appConfigMap
					.get("LevelMapping");
			if ((levelMappingMap != null) && (levelMappingMap.size() > 0)) {
				Iterator iter = levelMappingMap.keySet().iterator();

				while (iter.hasNext()) {
					String key = (String) iter.next();
					Long value = Long.valueOf(Long.parseLong(levelMappingMap
							.get(key).toString()));
					Parameters.levelMappingMap.put(key, value);
				}
			}
		} catch (Throwable t) {
			throw t;
		}

		logger.info("系统配置文件读入成功......");
	}

	public static String getConfigValue(String mainKeyName, String detailKeyName) {
		if ((appConfigMap == null) || (appConfigMap.get(mainKeyName) == null)) {
			return "";
		}

		HashMap childConfigMap = (HashMap) appConfigMap.get(mainKeyName);
		if (childConfigMap.get(detailKeyName) == null) {
			return "";
		}

		return ((String) childConfigMap.get(detailKeyName));
	}
}
