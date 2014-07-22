package com.yang.base.net.math;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @Description	: 计算类，对于浮点数
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-15 下午7:42:00 
 */
public class ArithUtil {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(ArithUtil.class);
	
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * 两个Double数相加
	 * 
	 * @param v1 被加数   
	 * @param v2 加数
	 * @return Double 两个参数的和
	 */
	public static Double add(Double v1, Double v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(Double, Double) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double returnDouble = b1.add(b2).doubleValue();
		if (logger.isDebugEnabled()) {
			logger.debug("add(Double, Double) - end"); //$NON-NLS-1$
		}
		return returnDouble;
	}

	/**
	 * 提供精确的减法运算
	 * 两个Double数相减 v1 -v2
	 * 
	 * @param v1 被减数
	 * @param v2 减数 
	 * @return Double 两个参数的差
	 */
	public static Double sub(Double v1, Double v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("sub(Double, Double) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double returnDouble = b1.subtract(b2).doubleValue();
		if (logger.isDebugEnabled()) {
			logger.debug("sub(Double, Double) - end"); //$NON-NLS-1$
		}
		return returnDouble;
	}

	/**
	 * 提供精确的乘法运算。
	 * 两个Double数相乘
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return Double 两个参数的积 
	 */
	public static Double mul(Double v1, Double v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("mul(Double, Double) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString()); 
		Double returnDouble = b1.multiply(b2).doubleValue();
		if (logger.isDebugEnabled()) {
			logger.debug("mul(Double, Double) - end"); //$NON-NLS-1$
		}
		return returnDouble;
	}

	/**
	 * 两个Double数相除
	 * 两个Double数相除，并保留scale位小数
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1  被除数
	 * @param v2  除数  
	 * @param scale
	 * @return Double 两个参数的商  
	 */
	public static Double div(Double v1, Double v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("div(Double, Double) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double returnDouble = b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
		if (logger.isDebugEnabled()) {
			logger.debug("div(Double, Double) - end"); //$NON-NLS-1$
		}
		return returnDouble;
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到  
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1  被除数
	 * @param v2  除数  
	 * @param scale
	 * @return Double 两个参数的商  
	 */ 
	public static Double div(Double v1, Double v2, int scale) {
		if (logger.isDebugEnabled()) {
			logger.debug("div(Double, Double, int) - start"); //$NON-NLS-1$
		}

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double returnDouble = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		if (logger.isDebugEnabled()) {
			logger.debug("div(Double, Double, int) - end"); //$NON-NLS-1$
		}
		return returnDouble;
	}
	
	/**
	 * 两个Float数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Float
	 */
	public static Float add(Float v1, Float v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(Float, Float) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Float returnFloat = b1.add(b2).floatValue();
		if (logger.isDebugEnabled()) {
			logger.debug("add(Float, Float) - end"); //$NON-NLS-1$
		}
		return returnFloat;
	}

	/**
	 * 两个Float数相减 v1 -v2
	 * 
	 * @param v1
	 * @param v2
	 * @return Float
	 */
	public static Float sub(Float v1, Float v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("sub(Float, Float) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Float returnFloat = b1.subtract(b2).floatValue();
		if (logger.isDebugEnabled()) {
			logger.debug("sub(Float, Float) - end"); //$NON-NLS-1$
		}
		return returnFloat;
	}

	/**
	 *  提供精确的乘法运算
	 *  两个Float数相乘
	 * 
	 * @param v1 被乘数 
	 * @param v2 乘数  
	 * @return Float 两个参数的积 
	 */
	public static Float mul(Float v1, Float v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("mul(Float, Float) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString()); 
		Float returnFloat = b1.multiply(b2).floatValue();
		if (logger.isDebugEnabled()) {
			logger.debug("mul(Float, Float) - end"); //$NON-NLS-1$
		}
		return returnFloat;
	}

	/**
	 * 两个Float数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return Float
	 */
	public static Float div(Float v1, Float v2) {
		if (logger.isDebugEnabled()) {
			logger.debug("div(Float, Float) - start"); //$NON-NLS-1$
		}

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Float returnFloat = b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).floatValue();
		if (logger.isDebugEnabled()) {
			logger.debug("div(Float, Float) - end"); //$NON-NLS-1$
		}
		return returnFloat ;
	}

	/**
	 * 两个Float数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return Float
	 */
	public static Float div(Float v1, Float v2, int scale) {
		if (logger.isDebugEnabled()) {
			logger.debug("div(Float, Float, int) - start"); //$NON-NLS-1$
		}

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Float returnFloat = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
		if (logger.isDebugEnabled()) {
			logger.debug("div(Float, Float, int) - end"); //$NON-NLS-1$
		}
		return returnFloat;
	}
	

	/**  
	* 提供精确的小数位四舍五入处理。  
	* @param v 需要四舍五入的数字  
	* @param scale 小数点后保留几位  
	* @return 四舍五入后的结果  
	*/  
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}   
	
	/**
	 * 格式化数据 #.00
	 * @param s
	 * @return
	 */
	public static float formatNumber(Float s){
		if (logger.isDebugEnabled()) {
			logger.debug("formatNumber(Float) - start"); //$NON-NLS-1$
		}

		DecimalFormat df =  new DecimalFormat("#.00");
		float num = Float.parseFloat(df.format(s));

		if (logger.isDebugEnabled()) {
			logger.debug("formatNumber(Float) - end"); //$NON-NLS-1$
		}
		return num;
	}

	/**
	 * 格式化数据
	 * @param s
	 * @return
	 */
	public static float formatAmount(Float s){
		if (logger.isDebugEnabled()) {
			logger.debug("formatAmount(Float) - start"); //$NON-NLS-1$
		}

		String style = "######.##";
		DecimalFormat df =  new DecimalFormat();
		df.applyPattern(style);
		float num = Float.parseFloat(df.format(s));

		if (logger.isDebugEnabled()) {
			logger.debug("formatAmount(Float) - end"); //$NON-NLS-1$
		}
		return num;
	}
	
}
