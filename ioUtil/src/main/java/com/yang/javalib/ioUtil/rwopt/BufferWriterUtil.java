package com.yang.javalib.ioUtil.rwopt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description: 先调  buildBufferedWriter write  flush close
 * 只能写字符类的哦. 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-2 下午4:47:16 
 */
public class BufferWriterUtil {
	
	private  BufferedWriter bWriter;
	
	private BufferWriterUtil(BufferedWriter bWriter){
		this.bWriter =bWriter;
	}
	
	public static BufferWriterUtil buildBufferedWriter(String outPutFilePath) throws IOException{
		FileWriter fw = new FileWriter(outPutFilePath);   
		 
		// OutputStreamWriter ,"UTF-8"　
		BufferedWriter bufw = new BufferedWriter(fw);
		BufferWriterUtil BufferedWriterUtil =new BufferWriterUtil(bufw) ;
	
		return BufferedWriterUtil ;
	}

	public void write(char cbuf[]) throws IOException{
		bWriter.write(cbuf);
		
	}
	public void write(String content) throws IOException{
		bWriter.write(content);
		
	}
	public void writeLine(String content) throws IOException{
		bWriter.write(content);
		bWriter.newLine(); 
	}
	public void newLine() throws IOException{
		bWriter.newLine();  
	}
	
	public void flush() throws IOException{
		bWriter.flush();
	}
	
	public void close() throws IOException{
		bWriter.close();
	}

}
