package com.yang.javalib.ioUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * @Description: 文件压缩常用类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-1 上午8:36:39 
 */
public class FileZipUtil {

	private static final Logger log = Logger.getLogger(FileUtil.class);

	public static void main(String[] args) {

	}

	/**
	 * @Description: 把文件解压到指定位置 
	 * @param f 压缩文件???只能ZIP类型的吗 
	 * @param extPlace 解压到目录
	 * @return
	 */
	public static List<File> unzip(File f, String extPlace) {
		ZipFile zipFile = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String zipEntryPath;
		try {
			zipFile = new ZipFile(f);
			if ((!(f.exists())) && (f.length() <= 0L)) {

				return null;
			}
			File extDirectory = new File(extPlace);
			if (!(extDirectory.exists()))
				extDirectory.mkdirs();
			String strPath = extDirectory.getAbsolutePath();
			Enumeration e = zipFile.entries();
			List files = new ArrayList();
			while (e.hasMoreElements()) {
				ZipEntry zipEnt = (ZipEntry) e.nextElement();
				zipEntryPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					String strtemp = strPath + File.separator + zipEntryPath;
					File dir = new File(strtemp);
					dir.mkdirs();
				}

				InputStream is = zipFile.getInputStream(zipEnt);
				bis = new BufferedInputStream(is);
				zipEntryPath = zipEnt.getName();
				String strtemp = strPath + File.separator + zipEntryPath;

				if (zipEntryPath.indexOf("/") != -1) {
					for (int i = 0; i < zipEntryPath.length(); ++i) {
						if (zipEntryPath.substring(i, i + 1).equalsIgnoreCase(
								"/")) {
							String temp = strPath + File.separator
									+ zipEntryPath.substring(0, i);
							File subdir = new File(temp);
							if (!(subdir.exists()))
								subdir.mkdir();
						}
					}
				}
				FileOutputStream fos = new FileOutputStream(strtemp);
				bos = new BufferedOutputStream(fos);
				int c;
				while ((c = bis.read()) != -1) {

					bos.write((byte) c);
				}
				bos.close();
				bis.close();
				File zipEntryFile = new File(strtemp);
				files.add(zipEntryFile);
			}
			return files;
		} catch (Exception e) {
			log.error("解压文件失败", e);
			return null;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (Exception e) {
					log.error("", e);
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					log.error("", e);
				}
			}
			if (bos != null)
				try {
					bos.close();
				} catch (Exception e) {
					log.error("", e);
				}
		}
	}

	/**
	 * @Description: 从byte数组 建立文件
	 * @param filePath
	 * @param fileContent
	 * @return
	 */
	public static File createFile(String filePath, byte[] fileContent) {
		FileOutputStream fileOut = null;
		File file = new File(filePath);
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			fileOut = new FileOutputStream(filePath);
			fileOut.write(fileContent);
			File localFile1 = file;
			return localFile1;
		} catch (FileNotFoundException e) {
			log.error("找不到文件(Can not find file)" + filePath, e);
		} catch (IOException e) {
			log.error("写文件失败(Write file fails)", e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					log.error("生成文件失败(Generated file fails)", e);
				}
			}
		}
		return null;
	}

	/**  
	* 把一个文件转化为字节  
	* @param file  
	* @return   byte[]  
	* @throws Exception  
	*/
	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) //当文件的长度超过了int的最大值  
			{
				System.out.println("this file is max ");
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			//如果得到的字节长度和file实际的长度不一致就可能出错了  
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return null;
			}
			is.close();
		}
		return bytes;
	}

	/**
	 * @Description: TODO 这个要提取出来
	 * @param zipFileName
	 * @param extPlace  解压到这个文件夹下
	 * @return
	 */
	public static List<File> unzip(String zipFileName, String extPlace) {
		return unzip(new File(zipFileName), extPlace);
	}

	public static File zip(String filePath, String zipFilePath) {

		List<String> tt = new ArrayList<String>();
		tt.add(filePath);
		return zip(tt, zipFilePath);
	}

	/**
	 * @Description: 把文件压缩到指定位置
	 * @param filePaths
	 * @param zipFilePath
	 * @return
	 */
	public static File zip(List<String> filePaths, String zipFilePath) {
		if ((filePaths == null) || (filePaths.isEmpty()))
			return null;
		ZipOutputStream zos = null;
		FileInputStream inputStream = null;
		int len = 0;
		byte[] buffer = new byte[1024];
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (file.exists()) {
					zos.putNextEntry(new ZipEntry(file.getName()));
					inputStream = new FileInputStream(file);
					while ((len = inputStream.read(buffer)) != -1) {
						zos.write(buffer, 0, len);
					}
					inputStream.close();
				} else {
					return null;
				}
			}

			return new File(zipFilePath);
		} catch (Exception e) {
			log.error("压缩文件失败", e);
			return null;
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (Exception e) {
					log.error("", e);
				}
			}
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					log.error("", e);
				}
		}
	}
}
