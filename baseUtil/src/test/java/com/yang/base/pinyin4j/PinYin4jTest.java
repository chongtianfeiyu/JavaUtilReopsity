package com.yang.base.pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PinYin4jTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 把汉字 转化为拼音
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@Test
	public void test() throws BadHanyuPinyinOutputFormatCombination {
		HanyuPinyinOutputFormat hanyuPinyinOutputFormat =new HanyuPinyinOutputFormat();
		String tempString  =PinyinHelper.toHanyuPinyinString("杨晓东", hanyuPinyinOutputFormat, "#");
		  System.out.println(tempString);
		  
		  //输出  yang2#xiao3#dong1
	}

}
