package com.yang.base.net.math;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArithUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 测试下float数据
	 * void
	 * @throws
	 */
	@Test
	public void test() {
		BigDecimal priceRebate= new BigDecimal(Float.toString(0f));
		BigDecimal tt= new BigDecimal(Float.toString(1.99F));
		//BigDecimal bigDecimal =new BigDecimal();
	 
		priceRebate.add(tt);
		
		System.out.println(priceRebate.toString());
		System.out.println(1.99F);
		System.out.println(priceRebate.floatValue());
		
	}

	/**
	 * @Description: float、double两种基本类型在运算的时候容易引起精度丢失。以float为例说明
	 * 所以 在计算时,应该转换为BigDecimal进行计算. 
	 * void
	 * @throws
	 */
	@Test
	public void testFloatSub1() {
	   float x = 1.0f;
	   float y = 0.8f;
	   float z = x - y;
	   System.out.println(z);
	}
	/**
	 * @Description: 为什么 不显示 出来了呢?
	 * float＊整数　出现浮点的情况了  都转换为BigDecimal 使用Mutify进行计算
	 * void
	 * @throws
	 */
	@Test
	public void testFloatMutify2() {
	   float x = 0.1f;
	   Integer num =5;
	   float xx = x * num;
	   
	   
	   Float rr = 0.1F;
	    System.out.println(rr*4);
	   
	    System.out.println(xx);
	    BigDecimal tt= new BigDecimal(Float.toString(x));
	    
	    System.out.println(tt.floatValue());
	}

	@Test
	public void test2() {
		   float x = 1.0f;
		   float y = 0.8f;
		 
		   BigDecimal x1 = new BigDecimal(Float.toString(x));
		   BigDecimal x2 = new BigDecimal(Float.toString(y));
		   float z = x1.subtract(x2).floatValue();
		   System.out.println(z);
	}
	
	
	
	@Test
	public void testAddDoubleDouble() { 
		//ArithUtil.add(v1, v2);
		fail("Not yet implemented");
	}

	@Test
	public void testSubDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testMulDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivDoubleDoubleInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFloatFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubFloatFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testMulFloatFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivFloatFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivFloatFloatInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatAmount() {
		fail("Not yet implemented");
	}
	
	/**
	 * @Description: 格式化数字
	 * void
	 * @throws
	 */
	@Test
	public void testDecimalFormat() {
		//假如你不关心国际化，可以直接使用Decimalformat
		
		NumberFormat nf1 =NumberFormat.getInstance();
		System.out.println(nf1.format(1234.56)); //1,234.56

		NumberFormat nf2 =NumberFormat.getInstance(Locale.GERMAN);
		System.out.println(nf2.format(1234.56)); //1.234,56
		
		//得到本地的缺省格式
		DecimalFormat df1 = new DecimalFormat("####.000"); 
		System.out.println(df1.format(1234.56));//1234.560
		// 得到德国的格式 
		Locale.setDefault(Locale.GERMAN); 
		DecimalFormat df2 = new DecimalFormat("####.000"); 
		System.out.println(df2.format(1234.56)); //1234.560
		
		

		//指数形式的格式
		DecimalFormat df = new DecimalFormat("0.000E0000"); 
		
		System.out.println(df.format(1234.56)); // 1.235E0003 
		//对于百分数：
		NumberFormat nf = NumberFormat.getPercentInstance(); 
	
		System.out.println(nf.format(0.47)); // 47% 
		
	}
	

	/**
	 * @Description: 基于格式的解析 
	 * void
	 * @throws
	 */
	@Test
	public void testParseDecimalFormat() {
		NumberFormat nf1 = NumberFormat.getInstance();

		Object obj1 = null;
		// 基于格式的解析  
		try {
			obj1 = nf1.parse("1234,56");
		} catch (ParseException e1) { 
			System.err.println(e1);
		}
		System.out.println(obj1);//　 123456 
		// 德国格式 
		NumberFormat nf2 =NumberFormat.getInstance(Locale.GERMAN);

		Object obj2 = null;

		// 基于格式的解析 

		try {
			obj2 = nf2.parse("1234,56");
		} catch (ParseException e2) {

			System.err.println(e2);

		}
		System.out.println(obj2); //1234.56 
	}
	

}
