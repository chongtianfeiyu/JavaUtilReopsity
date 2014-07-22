package com.yang.javalib.ioUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * @Description:  
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-7 上午10:53:56 
 */
public class NioFileUtil {

	private static final Logger log = Logger.getLogger(NioFileUtil.class);

	public static void main(String[] args) throws IOException {

	}

	/**
	 * @Description: 将文件的一部分映射入内存中
	 * byte =char(FG:a,b c,d )  (byte) 97:a   (byte) 122):Z
	 * byte就是最基本的单位 了, 如果是字符点 1byte , 如果 是int占2 byte,.... 
	 * @param filePath
	 * @param start
	 * @param size
	 * @throws IOException
	 */
	public static void mappedReadFile(String filePath, int start, int size)
			throws IOException {
		//start = 0;
		//size = 1024;

		RandomAccessFile raf = new RandomAccessFile("C:\\usemappedfile.txt",
				"rw");
		FileChannel fc = raf.getChannel();

		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start,
				size);
		//可以用put方法, get方法设置相应的值
		mbb.put(0, (byte) 97);//将位置 致为相应值
		mbb.put(1023, (byte) 122);//将位置 致为相应值
		//mbb.flip()   

		raf.close();
	}

	/**
	 * @Description: 向文件 中写入内容,直接读二进制,不存在编码问题
	  FG:
	  
		String filePath = "D:/temp.txt";

		String temp = FileUtils.readFileToString(new File("D:\\radware指标.txt"),
				"GBK");
		log.info("得到内容 是:" + temp);

		byte[] message = temp.getBytes("GBk");
		/*
		 * 如果 不加编码方式, 会用这个. 默认使用ISO-8859-1 编码 所以会造成乱码问题
		public byte[] getBytes() {
		return StringCoding.encode(value, offset, count);
		} 
		NioFileUtil.writeFile(filePath, message);
	 * @param filePath
	 * @param message
	 * @throws IOException
	 */
	public static void mappedWriteFile(String filePath, byte[] message)
			throws IOException {

		FileOutputStream fout = new FileOutputStream(new File(filePath));
		FileChannel fc = fout.getChannel();

		final int bufferSize = 1024;

		ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

		if (message.length > 1024) {

			int yushu = message.length % 1024;
			int yeshu = message.length / 1024;
			log.info("总大小:" + message.length);
			log.info("页数:" + yeshu);
			log.info("余数:" + yushu);

			for (int i = 0; i < yeshu; i++) {
				buffer.clear(); //把position设为0，把limit设为capacity，一般在把数据写入Buffer前调用。
				byte[] temp = ArrayUtils.subarray(message, i * bufferSize,
						(i + 1) * bufferSize - 1);
				log.info("页码:" + i * bufferSize + "------" + ((i + 1) * bufferSize - 1));
				buffer.put(temp); //放入数据后, position 也应该变了.  	buffer.position(); 
				buffer.flip();//把limit设为当前position，把position设为0，一般在从Buffer读出数据前调用。
				fc.write(buffer);
			}

			if (yushu != 0) {
				buffer.clear(); //把position设为0，把limit设为capacity，一般在把数据写入Buffer前调用。
				byte[] temp = ArrayUtils.subarray(message, yeshu * bufferSize,
						message.length - 1);
				log.info("页码:" + yeshu * bufferSize + "------"
						+ (message.length - 1));
				buffer.put(temp); //放入数据后, position 也应该变了.  	buffer.position(); 
				buffer.flip();//把limit设为当前position，把position设为0，一般在从Buffer读出数据前调用。
				fc.write(buffer);
			}
		} else { //一次就够 了.
			for (int i = 0; i < message.length; ++i) {
				buffer.put(message[i]);
			}
			buffer.flip(); //这一步是什么意思 ? 
			//最后一步是写入缓冲区中：  
			fc.write(buffer);
		}

	}

}
