package com.yang.javalib.ioUtil.rwopt;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-4 下午3:50:57 
 */
public class RandomAccessFileUtil {
	
	private RandomAccessFile randomAccessFile;
	
	
	private RandomAccessFileUtil(RandomAccessFile randomAccessFile) {
		super();
		this.randomAccessFile = randomAccessFile;
	}


	/**
	 * @throws FileNotFoundException 
	 * @Description: TODO
	 * @param filePath
	 * @param mode one of <tt>"r"</tt>, <tt>"rw"</tt>, <tt>"rws"</tt>, or <tt>"rwd"</tt>
	 * @return
	 * RandomAccessFileUtil
	 * @throws
	 */
	public static RandomAccessFileUtil buildRandomAccessFileUtil(String filePath, String mode) throws FileNotFoundException{ 
		RandomAccessFile fromRd = new RandomAccessFile(filePath, mode);
		RandomAccessFileUtil randomAccessFileUtil =new RandomAccessFileUtil(fromRd) ; 
		return  randomAccessFileUtil ; 
	}

}
