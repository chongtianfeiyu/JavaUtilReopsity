package com.yang.javalib.ioUtil.rwopt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
 
/**
 * @Description: BufferReader 使用, 这个是一行一行的读取 的, 不像FileUtils.readLines(file),全部加载入内存中
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-1 下午2:31:51 
 */
public class BufferReaderUtil {
	
	private static final Logger logger =Logger.getLogger(BufferReaderUtil.class) ;
	
	/**
	 * @Fields : 读取文件的编码 格式 
	 */
	private String CHARSET = "GBK";
	
	private BufferedReader br; 
	
	/**
	 * @Fields : 上一次读取后读取的字条总数量(包含换行)
	 */
	private long lastReadedCharCount = 0L;
	
	private BufferReaderUtil(){
		this.lastReadedCharCount =0L;
	}

	private BufferReaderUtil(String CHARSET){
		this.lastReadedCharCount =0L;
		this.CHARSET =CHARSET;
	}
	 
	
	

	/**
	 * @Description: 生成BufferReaderUtil对象
	 * @param fileLoc
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static BufferReaderUtil buildBufferReader(String fileLoc) throws FileNotFoundException, UnsupportedEncodingException{ 
		return buildBufferReader(fileLoc,"GBK");
	}

	public static BufferReaderUtil buildBufferReader(String fileLoc,String CHARSET) throws FileNotFoundException, UnsupportedEncodingException{
		BufferReaderUtil bufferReaderUtil =new  BufferReaderUtil();
		InputStream ios =new FileInputStream(new File(fileLoc)) ; 
		InputStreamReader isr = new InputStreamReader(ios, CHARSET); 
		bufferReaderUtil.br =new BufferedReader(isr); 
		return bufferReaderUtil ;
	}
	
/*
	*//**
	 * @Description: 开始处理 文件内容 . 
	 * @throws IOException
	 *//*
	public void dealLine() throws IOException {
		 String temp;
		 
		 if(lineHandle==null){
			 logger.warn("lineHandle未设定, 不进行任务操作. ");
			 return  ;
		 }
		 
			while ((temp = br.readLine()) != null) {//判断是否包含关键字,否则不 
				if (lineHandle.isKeyLine(temp))
					lineHandle.dealLineContent(temp);
				 
				lastReadedCharCount += (temp.length() + 2); //换行符 win 与linux的区别 TODO
				//logger.info("读取一行后:读取的文件字符数为:" + lastReadedCharCount);
			}
	}*/
	
	/**
	 * @Description: 读取一行. 
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException{
		String temp =  br.readLine();
		if( temp!=null)
			lastReadedCharCount += (temp.length() + 2);
		return temp;
	}
	
	 
	/**
	 * @Description: 跳到指定的位置, 可以是上一次文件读取 的位置 ,这样可以增量读取 
	 * 注意 只能向前跳, 不能向后跳[即已经处理 过的不能再处理 了. ],. 
	 * @param skpi2Loc
	 * @return
	 * @throws IOException
	 */
	public long  skip2Loc(long skpi2Loc) throws IOException{ 
		logger.info("跳到上一次读取后的位置:"+skpi2Loc); 
		return br.skip(skpi2Loc); 
	}
	
	/**
	 * @Description: 重置,到上一次读取的标记点
	 * http://www.cnblogs.com/zhang-qiang/articles/2050885.html


根据JAVA官方文档的描述，mark(int readlimit)方法表示，标记当前位置，
并保证在mark以后最多可以读取readlimit字节数据，mark标记仍有效。
如果在mark后读取超过readlimit字节数据，mark标记就会失效，调用reset()方法会有异常。
	 * @return
	 * @throws IOException
	 */
	public void reset() throws IOException { 
		br.reset();
	}
	
	/**
	 * @Description: 做一个标记,用于返回 reset时, 返回到此处
	 * 
	 * http://www.cnblogs.com/zhang-qiang/articles/2050885.html  
根据JAVA官方文档的描述，mark(int readlimit)方法表示，标记当前位置，
并保证在mark以后最多可以读取readlimit字节数据，mark标记仍有效。
如果在mark后读取超过readlimit字节数据，mark标记就会失效，调用reset()方法会有异常。
	 * @param readAheadLimit 标记位置 ,已读取到的字符数
	 * 
	 * @throws IOException
	 */
	public void mark(int readAheadLimit) throws IOException {  
		br.mark(readAheadLimit);
	}
}
