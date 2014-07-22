package com.yang.base.net.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数学计算
 * @author Jinson Chen
 *
 */
public class MathUtil {
	
	public final static int DECIMAL_PLACE = 2;

	/**
	 * @Description: double 四舍五入
	 * @param val
	 * @param dp
	 * @return
	 */
	public static double round(double val, int dp) {
		return round(new BigDecimal(Double.toString(val)), dp);
	}
	
	/**
	 * @Description:  四舍五入
	 * @param bd
	 * @param dp
	 * @return
	 */
	private static double round(BigDecimal bd, int dp) {
		return bd.setScale(dp, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * @Description:  四舍五入
	 * @param val
	 * @param dp
	 * @return
	 */
	public static Double round(Double val, int dp) {
		if (val == null) return new Double(0.0);
		return new Double(round(new BigDecimal(val.toString()), dp));
	}
	
	/**
	 * @Description: 加法
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double add(double val1, double val2) {
		return round((new BigDecimal(Double.toString(val1))).
				add(new BigDecimal(Double.toString(val2))), DECIMAL_PLACE);
	}
	
	/**
	 * @Description: 加法
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static Double add(Double val1, Double val2) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).
				add(new BigDecimal(val2.toString())), DECIMAL_PLACE));
	}
	
	/**
	 * @Description: 加法
	 * @param val1
	 * @param val2
	 * @param decimalPlace
	 * @return
	 */
	public static Double add(Double val1, Double val2,int decimalPlace) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).
				add(new BigDecimal(val2.toString())), decimalPlace));
	}
	
	/**
	 * @Description: 相减
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double subtract(double val1, double val2) {
		return round((new BigDecimal(Double.toString(val1))).
				subtract(new BigDecimal(Double.toString(val2))), DECIMAL_PLACE);
	}
	
	/**
	 * @Description: 相减
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static Double subtract(Double val1, Double val2) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).subtract(
				new BigDecimal(val2.toString())), DECIMAL_PLACE));
	}
	
	/**
	 * @Description: 相减
	 * @param val1
	 * @param val2
	 * @param decimal
	 * @return
	 */
	public static Double subtract(Double val1, Double val2,int decimal) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).subtract(
				new BigDecimal(val2.toString())), decimal));
	}
	
	/**
	 * @Description: 相乘
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double multiply(double val1, double val2) {
		return round((new BigDecimal(Double.toString(val1))).
				multiply(new BigDecimal(Double.toString(val2))), DECIMAL_PLACE);
	}

	/**
	 * @Description: 相乘
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static Double multiply(Double val1, Double val2) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).
				multiply(new BigDecimal(val2.toString())), DECIMAL_PLACE));
	}
	
	public static Double multiply(Double val1, Double val2,int decimal) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		return new Double(round((new BigDecimal(val1.toString())).
				multiply(new BigDecimal(val2.toString())), decimal));
	}

	/**
	 * @Description: 除法
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double divide(double val1, double val2) {
		if (val2 == 0.0) return 0.0;
		return ((new BigDecimal(Double.toString(val1))).divide(
				new BigDecimal(Double.toString(val2)), DECIMAL_PLACE, 
				BigDecimal.ROUND_HALF_UP)).doubleValue();
	}

	/**
	 * @Description: 除法
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static Double divide(Double val1, Double val2) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		if (val2.doubleValue() == 0.0) return new Double(0.0);
		return new Double((new BigDecimal(val1.toString())).divide(
				new BigDecimal(val2.toString()), DECIMAL_PLACE, 
				BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public static Double divideRoundDown(Double val1, Double val2,int decimal) {
		if (val1 == null) val1 = new Double(0.0);
		if (val2 == null) val2 = new Double(0.0);
		if (val2.doubleValue() == 0.0) return new Double(0.0);
		return new Double((new BigDecimal(val1.toString())).divide(
				new BigDecimal(val2.toString()),decimal,BigDecimal.ROUND_HALF_DOWN).doubleValue());
	}
	
	/**
	 * @Description: 格式化
	 * @param val
	 * @return
	 */
	public static String format(double val) {
		double roundNum = round(val, DECIMAL_PLACE);
		if (new Double(roundNum).toString().endsWith(".0"))
			return ""+(new Double(roundNum).intValue());
		return ""+roundNum;
		
	}

	/**
	 * @Description: 格式化
	 * @param valString
	 * @return
	 */
	public static String format(String valString) {
		double val = Double.parseDouble(valString);
		double roundNum = round(val, DECIMAL_PLACE);
		if (new Double(roundNum).toString().endsWith(".0"))
			return ""+(new Double(roundNum).intValue());
		return ""+roundNum;
	}
	
	/**
	 * @Description: 解析
	 * @param valString
	 * @return
	 */
	public static double parse(String valString) {
		return (new Double(valString)).doubleValue();
	}

	/**
	 * @Description: 返回格式化字符
	 * @param val
	 * @param dps
	 * @param isTrimToInteger
	 * @return
	 */
	public static String format(double val, int dps, boolean isTrimToInteger) {
        String strDecimalPlace = "0.0";
        for (int i = 1; i<dps; i++){
        	strDecimalPlace += "0";
        }
		DecimalFormat decimalFormat = new DecimalFormat(strDecimalPlace);
		
		if(isTrimToInteger) {
			decimalFormat.setGroupingUsed(true);
			decimalFormat.setGroupingSize(3);
		}
		if (isTrimToInteger)
			return decimalFormat.format(val); 
			//trimZero(decimalFormat.format(val), dps);
		else
			return decimalFormat.format(val);
    }

	public static String format(Object val, int dps, boolean isTrimToInteger) {
		String strDecimalPlace = "0.0";
        for (int i = 1; i<dps; i++){
        	strDecimalPlace += "0";
        }
		DecimalFormat decimalFormat = new DecimalFormat(strDecimalPlace);
		
		if(isTrimToInteger) {
			decimalFormat.setGroupingUsed(true);
			decimalFormat.setGroupingSize(3);
		}
		if (isTrimToInteger)
			return decimalFormat.format(val); 
			//trimZero(decimalFormat.format(val), dps);
		else
			return	decimalFormat.format(val); 
			//decimalFormat.format(val);
    }
	
	public static String trimZero(String valString, int dps){
		String strDecimalZero = "." ;
		for (int i = 0; i < dps; i++){
			strDecimalZero += "0" ;
		}
		if (valString.endsWith(strDecimalZero)){
			for (int i = valString.length()-1; i > 0; i--){
				if (valString.charAt(i) == '0'){
					valString = valString.substring(0, valString.length() - 1);
				}else if (valString.charAt(i) == '.'){
					valString = valString.substring(0, valString.length() - 1);
					break;
				}
			}
		}
		return valString;
	}
	
	public static String ignoreSpecificSymbol(String valString, char valChar) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<valString.length(); i++) {
			if(valString.charAt(i) != valChar)
			buffer.append(valString.charAt(i));
		}
		return buffer.toString();
	} 
	
	
	public static BigDecimal round(String bigDecimal){
		if("".equals(bigDecimal)||bigDecimal==null)
			return new BigDecimal("0.00");
		BigDecimal a = new BigDecimal(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
		return a;
	}
	
	
	public static BigDecimal round(BigDecimal bigDecimal){
		if(bigDecimal==null)
			return new BigDecimal("0.00");
		return new BigDecimal(bigDecimal.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal roundScaleThird(BigDecimal bigDecimal){
		if("".equals(bigDecimal)||bigDecimal==null)
			return new BigDecimal("0.000");
		return new BigDecimal(bigDecimal.toString()).setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal roundScale(BigDecimal bigDecimal,int scale){
		if("".equals(bigDecimal)||bigDecimal==null)
			return new BigDecimal("0.00").setScale(scale, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(bigDecimal.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static String roundString(String string){
		if(string==null||"".equals(string))
			return "0.00";
		BigDecimal a = new BigDecimal(string).setScale(2, BigDecimal.ROUND_HALF_UP);
		return a.toString();
	}
	
	
	public static void main(String[] args){
		System.out.println(divideRoundDown(new Double(719), new Double(720),4));
		System.out.println(719/720);
		
		DecimalFormat df = new DecimalFormat("##0.00");  
		System.out.println(df.format((double)719/720));   //33.333 
		
		System.out.println(new Double(9).compareTo(new Double(10)));
		
	}
	
}
