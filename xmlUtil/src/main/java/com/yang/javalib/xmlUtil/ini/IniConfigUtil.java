package com.yang.javalib.xmlUtil.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 +---------------------------------------------------------------------
使用方法：
 +---------------------------------------------------------------------
	Config.init("php.ini");
	Config instance = Config.getInstance();
	instance.getConfig("PHP", "short_open_tag");
 +---------------------------------------------------------------------
 *
 * @author yangxiaodon
 * @date 2011-10-19
 *
 */
public class IniConfigUtil {

	//默认节点
	private static String GLOBLE_CONFIG = "__globle";

	//默认注释符号
	private String[] comments = { "#", ";" };

	private Map<String, Map<String, String>> configMap;

	private static IniConfigUtil instance;
	
	public static void main(String[] args) {
		
		
		
	}

	/**
	 * 单例模式 
	 */
	private IniConfigUtil() {
		configMap = new HashMap<String, Map<String, String>>();
	}

	public static void init(String fileName) {
		init(fileName, null);
	}

	public static void init(String fileName, String[] comments) {
		instance = new IniConfigUtil();

		if (comments != null) {
			instance.setComments(comments);
		}

		boolean loaded = instance.loadConfig(IniConfigUtil.class
				.getClassLoader().getResource("").getPath()
				+ fileName)//
				|| instance.loadConfig(IniConfigUtil.class
						.getResourceAsStream("/WEB-INF/" + fileName))//
				|| instance.loadConfig(IniConfigUtil.class
						.getResourceAsStream("/" + fileName));//
		if (loaded) {
			System.out.println("load config success.");
		} else {
			System.out.println("load config failed.");
		}
	}

	/**
	 * @Description: 得到 IniConfigUtil实例
	 * @return
	 */
	public static IniConfigUtil getInstance() {
		if (instance == null) {
			System.out
					.println("init config first use function init(String fileName).");
		}
		return instance;
	}

	private void setComments(String[] comments) {
		this.comments = comments;
	}

	private boolean loadConfig(String path) {
		try {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				return loadConfig(new FileInputStream(file));
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * @Description: 加载Config文件
	 * @param is
	 * @return
	 */
	private boolean loadConfig(InputStream is) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			String line;
			Map<String, String> inMap;

			inMap = new HashMap<String, String>();
			configMap.put(GLOBLE_CONFIG, inMap);

			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0) {
					if (isComment(line)) {
						continue;
					} else if (isSection(line)) {
						String section = line.substring(1, line.indexOf("]"));
						if (configMap.containsKey(section)) {
							inMap = configMap.get(section);
						} else {
							inMap = new HashMap<String, String>();
							configMap.put(section, inMap);
						}
					} else {
						int index = line.indexOf("=");
						String key = line;
						String val = null;
						if (index != -1) {//何必这样为难自己呢.
							key = line.substring(0, index);
							val = line.substring(index + 1);
						}
						inMap.put(key == null ? key : key.trim(),
								val == null ? val : val.trim());
					}
				}
			}
			return true;
		} catch (Exception e) {
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (Exception e2) {
				}
		}
		return false;
	}

	/**
	 * @Description: 判断是否是节点信息
	 * @param line
	 * @return
	 */
	private boolean isSection(String line) {
		return line.startsWith("[") && line.indexOf("]") > 0;
	}

	/**
	 * @Description: 判断是否是注释信息
	 * @param line
	 * @return
	 */
	private boolean isComment(String line) {
		for (String comment : comments) {
			if (line.startsWith(comment)) {
				return true;
			}
		}
		return false;
	}

	public String getConfigIgnoreCase(String section, String key) {
		return getConfig(section, key, true);
	}

	public String getConfig(String section, String key) {
		return getConfig(section, key, false);
	}

	private String getConfig(String section, String key, boolean ignorecase) {
		String val = null;
		if (section == null) {
			section = GLOBLE_CONFIG;
		}
		if (ignorecase) {
			section = section.toLowerCase();
		}
		if (configMap != null && configMap.containsKey(section)) {
			Map<String, String> inMap = configMap.get(section);
			if (inMap != null) {
				val = inMap.get(key);
			}
		}
		return val;
	}

	/**
	 * @Description: 打印出配置信息
	 */
	public void printIniConfig() {

		for (Map.Entry<String, Map<String, String>> entry : configMap
				.entrySet()) {
			System.out.println("[" + entry.getKey() + "]");

			Map<String, String> subMap = entry.getValue();
			for (Map.Entry<String, String> entryItem : subMap.entrySet()) {

				String key = entryItem.getKey();
				String value = entryItem.getValue();
				System.out.println(key + "=" + value);
			}

		}

	}
}
