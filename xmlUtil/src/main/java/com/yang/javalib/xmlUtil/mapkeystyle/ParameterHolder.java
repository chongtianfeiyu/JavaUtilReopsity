package com.yang.javalib.xmlUtil.mapkeystyle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParameterHolder implements Serializable {

	/**
	 * serialUID.
	 */
	private static final long serialVersionUID = 3795186789994473769L;

	/**
	 * 内部使用Map进行存储，使用LinkedHashMap。
	 */
	private Map map;

	public ParameterHolder() {
		map = new LinkedHashMap(); 
	}

	/**
	 * ParameterHolder容纳的数据数目.
	 * 
	 * @return
	 */
	public int size() {
		return map.size();
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 返回映射表是否为空。
	 * 
	 * @return 空返回true，否则返回false
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * 查看映射表中是否包含指定的名字
	 * 
	 * @param key
	 *            名字
	 * @return 包含返回true，否则返回false
	 */
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	/**
	 * 返回映射表中的名字列表。
	 * 
	 * @return 列表中的键列表。
	 */
	public Set keySet() {
		return map.keySet();
	}
	
	/**
	 * @Description: 遍历的新方式
	 * @return
	 */
	public Set<Map.Entry<String, Object>> entrySet() {
		
		return map.entrySet();
	}
	
	
	

	/**
	 * 获取指定名称对应的String对象。
	 * 
	 * @param key
	 *            名称
	 * @return String对象
	 */
	public String getString(String key) {
		return (String) map.get(key);
	}

	/**
	 * 得到名称所对应的ParameterHolder值.
	 * 
	 * @param key
	 * @return ParameterHolder对象.
	 */
	public ParameterHolder getParameterHolder(String key) {
		return (ParameterHolder) map.get(key);
	}

	/**
	 * 判断某个名称对应的值是否ParameterHolder类型．
	 * 
	 * @param key
	 * @return
	 */
	public boolean isParameterHolder(String key) {
		Object obj = map.get(key);
		if (obj instanceof String)
			return false;
		else
			return true;
	}

	/**
	 * 从参数映射表中删除一个名和值的映射。
	 * 
	 * @param key
	 *            名字
	 * @return 被删除的对象
	 */
	public Object remove(String key) {
		return map.remove(key);
	}

	/**
	 * 添加一个新的名值对。如果新添的名字存在，就用新的值覆盖原来的值。<br>
	 * 
	 * @param key
	 *            名字<br>
	 *            <p>
	 *            <li>名字必须为大小写字母(a-z,A-Z),数字(0-9),'_','.','-'不能包括其它字符.</li>
	 *            </p>
	 * @param value
	 *            值
	 * @return 如果名字存在，返回旧值，否则返回null
	 */
	public Object put(String key, String value) {
		if (value == null)
			value = "";
		// ParameterHolderUtil.checkKey(key);
		return map.put(key, value);
	}

	public ParameterHolder putObject(String key, Object object) {
		return ((ParameterHolder) this.map.put(key, object));
	}

	public Object getObject(String key) {
		return this.map.get(key);
	}

	/**
	 * 添加一个新的名值对。如果新添的名字存在，就用新的值覆盖原来的值。
	 * 
	 * @param key
	 *            名字 <br>
	 *            <p>
	 *            <li>名字必须为大小写字母(a-z,A-Z),数字(0-9),'_','.','-'不能包括其它字符.</li>
	 *            </p>
	 * @param value
	 *            字符串形式的值
	 * @return 如果名字存在，返回旧值，否则返回null
	 */
	public ParameterHolder put(String key, ParameterHolder value) {
		// ParameterHolderUtil.checkKey(key);

		return (ParameterHolder) map.put(key, value);
	}

	public String toString() {
		return ParameterHolderUtil.toXML(this);
	}

}