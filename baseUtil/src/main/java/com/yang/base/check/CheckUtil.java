package com.yang.base.check;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.yang.base.net.NetworkUtil;
import com.yang.javalib.baseUtil.date.DateUtil_1;
import com.yang.javalib.baseUtil.date.EmptyValueException;

/**
 * @Description: 检测工具
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-22 上午10:29:48 
 */
public class CheckUtil {

	public final static int NULL_INT = -1;
	public final static long NULL_LONG = -1L;
	public final static float NULL_FLOAT = -1.0F;
	public final static double NULL_DOUBLE = -1.0;

	public static boolean areEqual(Object obj1, Object obj2) {
		if (isEmpty(obj1) && isEmpty(obj2))
			return true;
		if (!isEmpty(obj1) && !isEmpty(obj2))
			return obj1.equals(obj2);
		return false;
	}

	public static int compare(Comparable obj1, Comparable obj2) {
		if (areEqual(obj1, obj2))
			return 0;
		if (isEmpty(obj1) && !isEmpty(obj2))
			return -1;
		if (!isEmpty(obj1) && isEmpty(obj2))
			return 1;
		return obj1.compareTo(obj2);
	}

	public static void checkEmpty(String name, Object obj)
			throws EmptyValueException {
		if (isEmpty(obj))
			throw new EmptyValueException(name);
	}

	public static boolean isEmpty(Object obj) {
		try {
			Validate.notNull(obj);
		} catch (IllegalArgumentException exc) {
			return true;
		}
		return ((obj instanceof String) ? StringUtils.isEmpty((String) obj)
				: (obj instanceof List) ? (((List) obj).size() == 0)
						: (obj instanceof Set) ? ((Set) obj).isEmpty()
								: (obj instanceof Integer) ? isEmpty(((Integer) obj)
										.intValue())
										: (obj instanceof Long) ? isEmpty(((Long) obj)
												.longValue())
												: (obj instanceof Double) ? isEmpty(((Double) obj)
														.doubleValue())
														: (obj instanceof Float) ? isEmpty(((Float) obj)
																.longValue())
																: (obj instanceof Map) ? (((Map) obj)
																		.size() == 0)
																		: (obj instanceof Object[]) ? (((Object[]) obj).length == 0)
																				: false); // Default Not Empty
	}

	public static boolean checkNull(Object obj) {

		try {
			if (obj instanceof String) {
				if (((String) obj).equals("null"))
					return true;
			}
			return isEmpty(obj);
		} catch (Exception exc) {
			return false;
		}

	}

	public static void checkEmpty(String name, int val)
			throws EmptyValueException {
		if (isEmpty(val))
			throw new EmptyValueException(name);
	}

	public static boolean isEmpty(int val) {
		if (val == NULL_INT)
			return true;
		return false;
	}

	public static boolean isEmpty(float val) {
		if (val == NULL_FLOAT) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(double val) {
		if (val == NULL_DOUBLE) {
			return true;
		} else {
			return false;
		}
	}

	public static void checkEmpty(String name, long val)
			throws EmptyValueException {
		if (isEmpty(val))
			throw new EmptyValueException(name);
	}

	public static boolean isEmpty(long val) {
		if (val == NULL_LONG)
			return true;
		return false;
	}

	public static boolean containSpace(String val) {
		if (isEmpty(val))
			return false;
		if (val.trim().length() != val.length())
			return true;
		StringTokenizer st = new StringTokenizer(val, " ");
		return (st.countTokens() > 1);
	}

	public static boolean isTooLong(String val, int len) {
		if (isEmpty(val))
			return false;
		return (val.length() > len);
	}

	public static boolean isInvalidLength(String val, int len) {
		if (isEmpty(val))
			return false;
		return (val.length() != len);
	}

	public static boolean isNotFloat(String val) {
		if (isEmpty(val))
			return true;
		try {
			Float.parseFloat(val);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	public static boolean isNotInteger(String val) {
		if (isEmpty(val))
			return true;
		try {
			Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	public static boolean isIntegerValueNotBetween(String val, int min, int max) {
		if (isNotInteger(val))
			return true;
		int i = Integer.parseInt(val);
		if (i >= min && i <= max)
			return false;
		else
			return true;
	}

	public static boolean isNotDigit(String val) {
		if (isEmpty(val))
			return true;
		for (int i = 0; i < val.length(); i++) {
			int digit = val.charAt(i);
			if ((digit < '0') || (digit > '9')) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNumber(String val) {
		boolean pass = true;
		try {
			int i = Integer.parseInt(val);
			pass = false;
		} catch (NumberFormatException e) {
		}
		try {
			float f = Float.parseFloat(val);
			pass = false;
		} catch (NumberFormatException e) {
		}
		try {
			double d = Double.parseDouble(val);
			pass = false;
		} catch (NumberFormatException e) {
		}

		return pass;
	}

	public static boolean isMonth(String val) {
		return DateUtil_1.isValidDate(val, "yyyy-MM");
	}

	public static boolean isDay(String val) {
		return DateUtil_1.isValidDate(val, DateUtil_1.DATE);
	}

	public static boolean isNotDate(String val) {
		return !DateUtil_1.isValidDate(val, DateUtil_1.DATE);
	}

	public static boolean isNotDateTime(String val) {
		return !DateUtil_1.isValidDate(val, DateUtil_1.DATETIME);
	}

	public static boolean isNotHour(String val) {
		return !DateUtil_1.isValidDate(val, DateUtil_1.HOUR);
	}

	public static boolean containChars(char[] charArr, char[] charStrArr) {
		boolean isContain = false;
		checking: for (int i = 0; i < charArr.length; i++) {
			for (int j = 0; j < charStrArr.length; j++) {
				if (charArr[i] == charStrArr[j]) {
					isContain = true;
					break checking;
				}
			}
		}
		return isContain;
	}

	public static boolean containChar(String str, String charStr) {
		return containChars(str.toCharArray(), charStr.toCharArray());
	}

	/*     */ 
	/*     */   public static boolean checkIp(String ipAddress)
	/*     */   {
	/*  97 */     boolean isValid = true;
	/*     */     try {
	/*  99 */       StringTokenizer st = new StringTokenizer(ipAddress, ".");
	/* 100 */       int len = st.countTokens();
	/* 101 */       if (len != 4) return false;
	/*     */ 
	/* 103 */       int ipSegment = 0;
	/* 104 */       for (int i = 0; i < len; ++i) {
	/* 105 */         ipSegment = Integer.parseInt(st.nextToken());
	/* 106 */         if ((ipSegment < 0) || (ipSegment > 255)) {
	/* 107 */           isValid = false;
	/* 108 */           break;
	/*     */         }
	/*     */       }
	/*     */     } catch (Exception e) {
	/* 112 */       return false;
	/*     */     }
	/* 114 */     return isValid;
	/*     */   }

	/*     */ 
	/*     */   public static boolean isNetAddress(String ipAddress, String netMask)
	/*     */   {
	/* 145 */     int[] ips = NetworkUtil.parseIp(ipAddress);
	/* 146 */     int[] masks = NetworkUtil.parseIp(netMask);
	/* 147 */     String result = null;
	/* 148 */     for (int i = 0; i < 4; ++i) {
	/* 149 */       if (result == null)
	/* 150 */         result = "" + (ips[i] & masks[i]);
	/*     */       else {
	/* 152 */         result = result + "." + (ips[i] & masks[i]);
	/*     */       }
	/*     */     }
	/* 155 */     return (result.equals(ipAddress));
	/*     */   }

	/*     */ 
	/*     */   public static boolean isValidIP(long netAddress, long netMask, String ipAddress)
	/*     */   {
	/* 186 */     long ipAddressLong = NetworkUtil.ipToLong(ipAddress);
	/* 187 */     long ipTotalOfSubnet = NetworkUtil.ipTotal - netMask;
	/*     */ 
	/* 189 */     return ((ipAddressLong > netAddress) && (ipAddressLong < netAddress + ipTotalOfSubnet));
	/*     */   }

	/*     */ 
	/*     */   public static boolean isValidIP(String netAddress, String netMask, String ipAddress)
	/*     */   {
	/* 195 */     return isValidIP(NetworkUtil.ipToLong(netAddress), NetworkUtil.ipToLong(netMask), ipAddress);
	/*     */   }

}
