package com.yang.javalib.module.jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author WeiQinyu
 * @version 1.0
 * @since 2012-6-15下午01:37:31
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ObjectByteUtils {

	/**
	 * 对象转换成字节数组,要求传入的对象必须实现序列号接口.
	 * @param obj
	 * @return byte[]
	 */
	public static byte[] objectToByte(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 字节数组转换成对象
	 * @param bytes
	 * @return Object 取得结果后强制转换成你存入的对象类型
	 */
	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	

}