package com.yang.javalib.xmlUtil.mapkeystyle;
  

/**
 * 描述采集器核配置信息中，一些常用的配置参数名称和相应的读取方法。
 * <p>
 * 本类中所有方法都为静态方法。
 * </p>
 * 
 * @author James Gao
 * @version 1.3 2007-12-3
 */
public class SharedParameters {

	/**
	 * 采集器核配置变量标识，是否在启动采集器后，立即执行一次调度任务，之后在由 调度服务按照调度配置执行调度。
	 */
	public static final String KEY_RUN_IMMEDIATE = "run immediate";

	/**
	 * 采集器核配置变量标识，用户名称。
	 */
	public static final String KEY_USERNAME = "username";
	public static final String KEY_USERNAME_1 = "username1";
	public static final String KEY_USERNAME_2 = "username2";
	public static final String KEY_FAMILYNAME = "familyname";
	/**
	 * 采集器核配置变量标识，用户密码。
	 */
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_PASSWORD_1 = "password1";
	public static final String KEY_PASSWORD_2 = "password2";

	/**
	 * 采集器核配置变量标识，主机名称（或主机IP地址）。
	 */
	public static final String KEY_HOSTNAME = "hostname";

	/**
	 * 采集器核配置变量标识，网络端口号。
	 */
	public static final String KEY_PORT = "port";

	/**
	 * 采集器核配置变量标识，执行动作的名称。
	 */
	public static final String KEY_ACTION = "action";

	/**
	 * 采集器核配置变量标识，被管理对象的标识。
	 * <p>
	 * 采集到的性能数据，与该被管理对象相关。
	 * </p>
	 */
	public static final String KEY_MOID = "moid";

	/**
	 * 采集器核配置变量标识，连接和响应等待超时时间，单位为ms。
	 * <p>
	 * 如：与Telnet服务器，HTTP服务器的超时时间。
	 * </p>
	 */
	public static final String KEY_TIMEOUT = "timeout";

	/**
	 * 采集器核配置变量标识，采集器依赖的资源信息的标识。
	 * <p>
	 * 采集器通常情况下，将依赖一个系统已配置的资源信息进行数据采集，该变量 标识用于记录该资源信息的唯一标识。
	 * </p>
	 * <p>
	 * 根据数据采集的需求不同，采集器也可以使用多个资源的信息，这是需要具体 采集器定义自身的资源标识的保存结构。
	 * </p>
	 */
	public static final String KEY_RESOURCE_ID = "resourceId";

	/**
	 * 在采集器配置信息中包含资源信息，标识资源的类型。
	 */
	public static final String KEY_INVENTORY_TYPE = "inventory-type";

	/**
	 * 在采集器配置信息中包含资源信息，标识资源的名称。
	 */
	public static final String KEY_INVENTORY_NAME = "inventory-name";

	/**
	 * 在采集器配置信息中包含资源信息，标识资源的属性名和值的映射表。
	 */
	public static final String KEY_INVENTORY_ATTRIBUTES = "inventory-attributes";

	/**
	 * 采集器核配置变量标识，采集器执行的采集命令：验证资源是否可用。
	 */
	public static final String KEY_AVAILABILITY_TEST = "availability-test";

	/**
	 * 标识采集容器的名字。
	 */
	public static final String KEY_CONTAINER_NAME = "SP-CONTAINER";

	/**
	 * 禁止外部实例化本类，因为本类包含的方法都为静态方法。
	 */
	private SharedParameters() {
	}

	/**
	 * 从采集器配置信息中，解析岀是否在采集器启动后立即执行一次调度任务的值。
	 * 
	 * @param params
	 *            采集器配置信息。
	 * @return 如果配置信息中，名称为KEY_RUN_IMMEDIATE的变量的值为“true”时，
	 *         那么将返回true；如果KEY_RUN_IMMEDIATE变量的值为“false”时，那么将返回
	 *         false。如果以上两种情况都不是，那么将抛出异常。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_RUN_IMMEDIATE
	 *             变量的值，或者值不为“true”或者“false”，那么将抛出该异常。
	 */
	public static boolean parseRunImmediate(ParameterHolder params)
			throws Exception {
		return parseBoolean(params, KEY_RUN_IMMEDIATE);
	}

	/**
	 * 从采集器配置信息中，解析岀用户名。
	 * 
	 * @param params
	 *            配置信息。
	 * @return 用户名。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_USERNAME变量 的值，那么将抛出该异常。
	 */
	public static String parseUsername(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseString(params, KEY_USERNAME);
	}

	/**
	 * 从采集器配置信息中，解析用户密码信息。
	 * <p>
	 * 解析岀的用户密码信息，有可能为空字符串。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @return 用户密码信息。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_PASSWORD变量的 值，那么将抛出该异常。
	 */
	public static String parsePassword(ParameterHolder params)
			throws Exception {
		if (!params.containsKey(KEY_PASSWORD)) {
			throw new Exception(KEY_PASSWORD
					+ " is not found in parameters.");
		}
		String retVal = (String) params.getString(KEY_PASSWORD);
		if (retVal == null) {
			throw new Exception(KEY_PASSWORD
					+ " should not be empty");
		}
		return retVal;
	}

	/**
	 * 从采集器配置信息中，解析岀主机名。
	 * 
	 * @param params
	 *            配置信息。
	 * @return 主机名称。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_HOSTNAME变量的 值，那么将抛出该异常。
	 */
	public static String parseHostname(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseString(params, KEY_HOSTNAME);
	}

	public static String parseFamilyname(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseString(params, KEY_FAMILYNAME);
	}

	/**
	 * 从采集器配置信息中，解析网络端口号。
	 * 
	 * @param params
	 *            配置信息。
	 * @return 网络端口号。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_PORT变量的值， 那么将抛出该异常。
	 */
	public static int parsePort(ParameterHolder params) throws Exception {
		// 网络端口号
		int retVal = parseInt(params, KEY_PORT);
		if (retVal < 1 || retVal > 65535) {
			throw new Exception(KEY_PORT
					+ " should be set between 1 and 65535");
		}
		return retVal;
	}

	/**
	 * 从采集器配置信息中，解析岀执行动作的名称。
	 * 
	 * @param params
	 *            配置信息。
	 * @return 执行动作的名称。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_ACTION变量的值， 那么将抛出该异常。
	 */
	public static String parseAction(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseString(params, KEY_ACTION);
	}

	/**
	 * 从采集器配置信息中，解析岀被管理对象的标识。
	 * <p>
	 * 采集到的性能数据，与该被管理对象相关。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @return 被管理对象的标识。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_MOID 变量的值，那么 将抛出该异常。
	 */
	public static long parseMoid(ParameterHolder params) throws Exception {
		return SharedParameters.parseLong(params, KEY_MOID);
	}

	/**
	 * 从采集器配置信息中，解析岀采集器依赖的资源信息的标识。
	 * <p>
	 * 采集器通常情况下，将依赖一个系统已配置的资源信息进行数据采集，该变量 标识用于记录该资源信息的唯一标识。
	 * </p>
	 * <p>
	 * 根据数据采集的需求不同，采集器也可以使用多个资源的信息，这是需要具体 采集器定义自身的资源标识的保存结构。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @return 资源信息的标识。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_RESOURCE_ID变量的 值，那么将抛出该异常。
	 */
	public static long parseResourceId(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseLong(params, KEY_RESOURCE_ID);
	}

	/**
	 * 从采集器配置信息中，解析岀连接和响应等待超时时间，单位为ms。
	 * <p>
	 * 如：与Telnet服务器，HTTP服务器的超时时间。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @return 超时时间。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含KEY_TIMEOUT变量的值， 那么将抛出该异常。
	 */
	public static int parseTimeout(ParameterHolder params)
			throws Exception {
		return SharedParameters.parseInt(params, KEY_TIMEOUT);
	}

	/**
	 * 从采集器配置信息中，读取指定键对应的字符串类型的值。
	 * <p>
	 * 本方法仅在配置信息的第一个层次中查找指定的键值。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @param key
	 *            配置信息的键。
	 * @return 配置值。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含指定的键的值，那么 将抛出该异常。
	 */
	public static String parseString(ParameterHolder params, String key)
			throws Exception {
		if (!params.containsKey(key)) {
			throw new Exception(key + " is not found in parameters");
		}
		String retVal = (String) params.getString(key);
		return retVal;
	}

	/**
	 * 从采集器配置信息中，读取指定键对应的字符串类型的值。
	 * <p>
	 * 本方法仅在配置信息的第一个层次中查找指定的键值。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @param key
	 *            配置信息的键。
	 * @return 配置值。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含指定的键的值，那么 将抛出该异常。
	 */
	public static int parseInt(ParameterHolder params, String key)
			throws Exception {
		String value = parseString(params, key);
		int retVal = -1;
		try {
			retVal = Integer.parseInt(value);
		} catch (Exception e) {
			throw new Exception(key + " should be integer");
		}
		return retVal;
	}

	/**
	 * 从采集器配置信息中，读取指定键对应的短整型类型的值。
	 * <p>
	 * 本方法仅在配置信息的第一个层次中查找指定的键值。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @param key
	 *            配置信息的键。
	 * @return 配置值。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含指定的键的值，那么 将抛出该异常。
	 */
	public static short parseShort(ParameterHolder params, String key)
			throws Exception {
		String value = parseString(params, key);
		short retVal = -1;
		try {
			retVal = Short.parseShort(value);
		} catch (Exception e) {
			throw new Exception(key + " should be short");
		}
		return retVal;
	}

	/**
	 * 从采集器配置信息中，读取指定键对应的长类型的值。
	 * <p>
	 * 本方法仅在配置信息的第一个层次中查找指定的键值。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @param key
	 *            配置信息的键。
	 * @return 配置值。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含指定的键的值，那么 将抛出该异常。
	 */
	public static long parseLong(ParameterHolder params, String key)
			throws Exception {
		String value = parseString(params, key);
		long retVal = -1;
		try {
			retVal = Long.parseLong(value);
		} catch (Exception e) {
			throw new Exception(key + " should be long");
		}
		return retVal;
	}

	/**
	 * 从采集器配置信息中，读取指定键对应的布尔类型的值。
	 * <p>
	 * 本方法仅在配置信息的第一个层次中查找指定的键值。
	 * </p>
	 * 
	 * @param params
	 *            配置信息。
	 * @param key
	 *            配置信息的键。
	 * @return 配置值。
	 * @throws Exception
	 *             如果采集器配置信息中，不包含指定的键的值，那么 将抛出该异常。
	 */
	public static boolean parseBoolean(ParameterHolder params, String key)
			throws Exception {
		String value = parseString(params, key);
		boolean retVal = false;
		if (value.equalsIgnoreCase("true")) {
			retVal = true;
		} else if (value.equalsIgnoreCase("false")) {
			retVal = false;
		} else {
			throw new Exception(key + " is illeagal, the value is "
					+ value);
		}
		return retVal;
	}

}
