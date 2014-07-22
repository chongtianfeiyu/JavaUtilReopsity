package com.yang.javalib.ioUtil.rwopt;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	

	/**
	 * @Description: 测试 BufferedReader MarkReset 方法
	 * @throws IOException
	 */
	@Test
	public void testMarkReset1() throws IOException {
			String s = "test";  
	    char buf[] = new char[s.length()];  
	    s.getChars(0, s.length(), buf, 0);  
	              
	    System.out.println(buf);          
	    System.out.println("字符长度　："+buf.length);
	    
	    CharArrayReader in = new CharArrayReader(buf);  
	    BufferedReader f = new BufferedReader(in);  
	    int c, d = 0;  
	    f.mark(s.length()+1);   
	                              
	    while ((c = f.read()) != -1) {  
	        System.out.println(c);  
	        d++;  
	    }  
	    System.out.println("d = " + d);  
	    f.reset();   //这里报错。 java.io.IOException: Mark invalid
	}
	

	/**
	 * @Description: TODO
	 * mark(int readlimit)方法表示，标记当前位置，并保证在mark以后最多可以读取readlimit字节数据，mark标记仍有效。
	 * 如果在mark后读取超过readlimit字节数据，mark标记就会失效，调用reset()方法会有异常。 
	 * @throws IOException
	 */
	@Test
	public void testMarkReset2() throws IOException {
		 String s = "This is the internal StringReader buffer.";  
	        StringReader stringReader = new StringReader(s);  
	        BufferedReader bufReader = new BufferedReader(stringReader);  
	  
	        // Read from the underlying StringReader.  
	        char[] arr = new char[s.length()];  
	        bufReader.read(arr, 0, arr.length - 14);  
	        System.out.println(arr);  
	  
	        // Call mark after having read all but the last 10  
	        // characters from the StringReader.  
	        if(bufReader.markSupported()) {  
	            System.out.println("mark supported.");  
	            bufReader.mark(15);// change to 3, Mark invalid occurs  
	        }  
	        bufReader.read(arr, arr.length - 14, 14);  
	        bufReader.read();  
	          
	        System.out.println(arr);  
	        bufReader.reset();  
	  
	        stringReader.close();  
	        bufReader.close();  
	}

	/**
	 * @Description: http://www.blogjava.net/iLinux/archive/2010/03/29/316841.html
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMarkReset3() throws IOException {
	  String s = "Message.show(Message.error, ioe.getMessage()).一";
      char buf[] = new char[s.length()];
      s.getChars(0, s.length(), buf, 0);
      CharArrayReader in = new CharArrayReader(buf);
      BufferedReader f = new BufferedReader(in);
      String d = "";
      int c;
      System.out.println(s.length() );
      f.mark(s.length() +1);
      while ((c = f.read()) != -1) {
          d += (char)c;
      }
      f.reset();
      System.out.println(d);

	}
}
