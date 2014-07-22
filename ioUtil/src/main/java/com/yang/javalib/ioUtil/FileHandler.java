package com.yang.javalib.ioUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description: 两个文件夹之间的操作
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-28 上午10:58:49 
 */
public class FileHandler {
	private static Log log = LogFactory.getLog(FileHandler.class);

	private String srcFilePath = "";
	private String destFilePath = "";
	private static boolean delete;

	private static boolean mkdirs;

	private static boolean createNewFile;

	public FileHandler() {
	}

	public synchronized void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public synchronized void setDestFilePath(String destFilePath) {
		this.destFilePath = destFilePath;
	}

	public synchronized String getSrcFilePath() {
		return srcFilePath;
	}

	public synchronized String getDestFilePath() {
		return destFilePath;
	}

	/**
	 * 拷贝文件从源文件 到 目标目录(文件)
	 * 
	 * @param srcFileName
	 *            源文件名
	 * @param dest
	 *            目标
	 * @param path
	 *            指定 dest 是文件名 还是目录名
	 * @return
	 */
	public static boolean copyFile(String srcFileName, String dest, boolean path) {
		if (srcFileName == null || "".equals(srcFileName) || dest == null
				|| "".equals(dest)) {
			throw new IllegalArgumentException("源文件名 或 目标文件名(目录)不能为空.");
		}
		boolean bflag = false;
		if (path) {
			File srcFile = new File(srcFileName);
			bflag = FileHandler.copyFileByStream(srcFile, dest);
		} else {
			bflag = FileHandler.copyFileByStream(srcFileName, dest);
		}
		return bflag;
	}

	/**
	 * 借助于 字节流的相关类 实现文件的拷贝, 将 srcFileName 文件拷贝到 destFileName 文件
	 * 
	 * @param srcFileName
	 *            含路径源文件名
	 * @param destFileName
	 *            含路径目标文件名,目标目录不存在时创建
	 * @return 拷贝成功返回 true;否则,返回 false;
	 */
	public static boolean copyFileByStream(String srcFileName,
			String destFileName) {
		if (destFileName == null)
			return false;
		File dest = new File(destFileName);
		InputStream in = null;
		OutputStream out = null;
		BufferedInputStream bufRead = null;
		BufferedOutputStream bufWrite = null;
		try {
			File src = new File(srcFileName);
			if (!src.exists())
				return false;
			if (!dest.exists()) {
				@SuppressWarnings("unused")
				boolean delete2 = dest.delete();
				String parent = dest.getParent();
				File p = new File(parent);
				if (!p.exists()) {
					mkdirs = p.mkdirs();
				}
				createNewFile = dest.createNewFile();
				if (log.isDebugEnabled()) {
					log.debug(createNewFile);
					log.debug(mkdirs);
				}
			}

			in = new FileInputStream(src);
			bufRead = new BufferedInputStream(in, 512);
			out = new FileOutputStream(destFileName);
			bufWrite = new BufferedOutputStream(out, 512);
			int data = 0;
			while (data != -1) {
				data = bufRead.read();
				bufWrite.write(data);
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			log.error(e);
		} catch (IOException e1) {
			// e1.printStackTrace();
			log.error(e1);
		} finally {
			try {
				if (in != null)
					in.close();
				if (bufRead != null)
					bufRead.close();
				if (bufWrite != null)
					bufWrite.close();
				if (out != null)
					out.close();
				in = null;
				bufRead = null;
				bufWrite = null;
				out = null;
			} catch (IOException e) {
				// e.printStackTrace();
				log.error(e);

			}
		}
		return dest.exists() ? true : false;
	}

	/**
	 * 移动文件 先拷贝, 后删除源文件
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean moveFileByStream(String srcFileName,
			String destFileName) {
		if (destFileName == null)
			return false;
		File dest = new File(destFileName);
		InputStream in = null;
		OutputStream out = null;
		BufferedInputStream bufRead = null;
		BufferedOutputStream bufWrite = null;
		try {
			File src = new File(srcFileName);
			if (!src.exists())
				return false;

			if (!dest.exists()) {
				delete = dest.delete();
				if (log.isDebugEnabled())
					log.debug(delete);
				String parent = dest.getParent();
				File p = new File(parent);
				if (!p.exists()) {
					mkdirs = p.mkdirs();
				}
				createNewFile = dest.createNewFile();
			}
			in = new FileInputStream(src);
			bufRead = new BufferedInputStream(in, 512);
			out = new FileOutputStream(destFileName);
			bufWrite = new BufferedOutputStream(out, 512);
			int data = 0;
			while (data != -1) {
				data = bufRead.read();
				bufWrite.write(data);
			}
			if (src != null) {
				delete = src.delete();
			}
			if (log.isDebugEnabled())
				log.debug(delete);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.err.print(e);
		} catch (IOException e1) {
			System.err.print(e1);
		} finally {
			try {
				if (in != null)
					in.close();
				if (bufRead != null)
					bufRead.close();
				if (bufWrite != null)
					bufWrite.close();
				if (out != null)
					out.close();
				in = null;
				bufRead = null;
				bufWrite = null;
				out = null;
			} catch (IOException e) {
				// e.printStackTrace();
				System.err.print(e);
			}
		}
		return dest.exists() ? true : false;
	}

	/**
	 * 将源文件 srcFile 拷贝到 destPath 目录中
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean copyFileByStream(File srcFile, String destPath) {
		Log log = LogFactory.getLog(FileHandler.class);
		//		log.info("FileHandler::copyFileByStream()	srcFile=" + srcFile + "|destPath=" + destPath);
		if (destPath == null || "".equals(destPath)) {
			throw new IllegalArgumentException("源文件不存在 或 目标目录为空.");
		}
		if (srcFile == null || !srcFile.exists())
			return false;
		InputStream in = null;
		OutputStream out = null;
		BufferedInputStream bufRead = null;
		BufferedOutputStream bufWrite = null;
		try {
			File dest = new File(destPath);
			if (!dest.exists()) {
				delete = dest.delete();
				mkdirs = dest.mkdirs();
			}
			in = new FileInputStream(srcFile);
			bufRead = new BufferedInputStream(in, 512);
			out = new FileOutputStream(destPath + "/" + srcFile.getName());
			bufWrite = new BufferedOutputStream(out, 512);
			int data = 0;
			while (data != -1) {
				data = bufRead.read();
				bufWrite.write(data);
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			log.error(e);
			return false;
		} catch (IOException e1) {
			log.error(e1);
			return false;
		} finally {
			try {
				in.close();
				bufRead.close();
				bufWrite.close();
				out.close();
				in = null;
				bufRead = null;
				bufWrite = null;
				out = null;
			} catch (IOException e) {
				// e.printStackTrace();
				log.error(e);
			}
		}
		return true;
	}

	/**
	 * 将源文件 srcFile 移动到 destPath 目录中, 先拷贝后删除源文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean moveFileByStream(File srcFile, String destPath) {
		Log log = LogFactory.getLog(FileHandler.class);
		//		log.info("FileHandler::copyFileByStream()	srcFile=" + srcFile + "|destPath=" + destPath);
		if (destPath == null || "".equals(destPath)) {
			throw new IllegalArgumentException("源文件不存在 或 目标目录为空.");
		}
		if (srcFile == null || !srcFile.exists()) {
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		BufferedInputStream bufRead = null;
		BufferedOutputStream bufWrite = null;
		try {
			File dest = new File(destPath);
			if (!dest.exists()) {
				delete = dest.delete();
				mkdirs = dest.mkdirs();
			}
			in = new FileInputStream(srcFile);
			bufRead = new BufferedInputStream(in, 512);
			out = new FileOutputStream(destPath + "/" + srcFile.getName());
			bufWrite = new BufferedOutputStream(out, 512);
			int data = 0;
			while (data != -1) {
				data = bufRead.read();
				bufWrite.write(data);
			}

			if (srcFile != null) {
				delete = srcFile.delete();
			}

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			log.error(e);
			return false;
		} catch (IOException e1) {
			// e1.printStackTrace();
			log.error(e1);
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
				if (bufRead != null)
					bufRead.close();
				if (bufWrite != null)
					bufWrite.close();
				if (out != null)
					out.close();
				in = null;
				bufRead = null;
				bufWrite = null;
				out = null;
			} catch (IOException e) {
				// e.printStackTrace();
				log.error(e);
			}
		}
		return true;
	}

	/**
	 * 文件复制, 从源文件夹 srcFilePath 复制到 目标文件夹 destFilePath
	 * 
	 * @param srcFilePath
	 *            源文件夹
	 * @param destFilePath
	 *            目标文件夹
	 */
	private synchronized void copyFiles(String srcFilePath, String destFilePath) {
		Log log = LogFactory.getLog(FileHandler.class);
		// long start = System.currentTimeMillis();
		if (srcFilePath == null || "".equals(srcFilePath)
				|| destFilePath == null || "".equals(destFilePath)) {
			throw new IllegalArgumentException("源文件目录srcFilepath="
					+ srcFilePath + "] 或目标文件目录[destFilePath=" + destFilePath
					+ "]未指定.");
		}
		//		log.info("srcFilePath=" + srcFilePath + "|destFilePath=" + destFilePath);
		File srcPath = new File(srcFilePath);
		String tmpSrcFilePath = srcFilePath;
		String tmpDestFilePath = destFilePath;
		try {
			if (srcPath.exists()) {
				if (srcPath.isDirectory()) {
					String[] srcFileNames = srcPath.list();
					for (int i = 0; i < srcFileNames.length; i++) {
						String destFileName = tmpDestFilePath + "/"
								+ srcFileNames[i];
						// log.info("目标文件:"+destFileName);
						String srcSubName = tmpSrcFilePath + "/"
								+ srcFileNames[i];
						// log.info("源文件:"+srcSubName);
						File srcSubFile = new File(srcSubName);
						if (srcSubFile.isDirectory()) {
							File destPath = new File(destFileName);
							mkdirs = destPath.mkdirs();
							this.copyFiles(srcSubName, destFileName);
						} else {
							// log.info("srcSubFile="+srcSubFile.getAbsolutePath()+"/"+srcSubFile.getName());
							@SuppressWarnings("unused")
							boolean bflag = FileHandler.copyFile(srcSubName,
									tmpDestFilePath, true);
							// log.info("Copy file:"+bflag);
						}
					}
				} else if (srcPath.isFile()) {
					FileHandler.copyFileByStream(this.srcFilePath,
							this.destFilePath);
				} else {
					throw new IllegalArgumentException("给定的源文件目录有未知的异常,请修正."
							+ srcPath.getPath());
				}
			} else {
				throw new IllegalArgumentException("给定的源文件目录不存在,请修正."
						+ srcPath.getPath());
			}
		} catch (Exception except) {
			log.error(except);
			// except.printStackTrace();
		} finally {
			;
		}

		// long end = System.currentTimeMillis();
		// long used = end - start;
		// log.info("KpiWarningManager::copyFiles() 共耗时:["+used+" ms].");
		return;
	}

	/**
	 * 删除目标文件; 如果是目录, 则删除目标目录中的所有文件及其子目录; 但不删除dest目录
	 * 
	 * @param destPath
	 * @return
	 */
	/*
	 * public static boolean delete(String dest) { if (dest == null ||
	 * "".equals(dest)) { throw new IllegalArgumentException("目标目录不能为空."); }
	 * File file = new File(dest); if (file.exists() && file.isDirectory()) {
	 * File[] subFiles = file.listFiles(); if(subFiles!=null) for (int i = 0; i <
	 * subFiles.length; i++) { File subFile = subFiles[i]; if(subFile!=null){
	 * FileHandler.delete(subFile.getPath()); } } // file.delete(); //取掉该注释则删除
	 * dest 目录及其下所有东东 } else { if (file != null) { @SuppressWarnings("unused")
	 * boolean delete = file.delete(); } } return true; }
	 */

	/**
	 * 拷贝文件, 将 this.srcFilePath 中的所有东东都拷贝到 this.destFilePath/yyyyMMddHHmm 中
	 */
	public synchronized void copyFiles() {
		// long start = System.currentTimeMillis();
		if (this.srcFilePath == null || "".equals(this.srcFilePath)
				|| this.destFilePath == null || "".equals(this.destFilePath)) {
			throw new IllegalArgumentException("源文件目录srcFilepath="
					+ srcFilePath + "] 或目标文件目录[destFilePath=" + destFilePath
					+ "]未指定.");
		}
		File srcFile = new File(this.srcFilePath);
		String tmpdestFilePath = this.destFilePath;
		try {
			if (srcFile.exists()) {
				// log.info("tmpdestFilePath="+tmpdestFilePath);
				if (srcFile.isDirectory()) {
					this.copyFiles(this.srcFilePath, tmpdestFilePath);
				} else {
					// log.info("srcFile="+srcFile.getAbsolutePath()+"/"+srcFile.getName());
					FileHandler.copyFile(this.srcFilePath, tmpdestFilePath,
							true);
				}
			} else {
				throw new IllegalArgumentException("给定的源文件目录不存在或有同名的文件,请修正.");
			}
		} catch (Exception e) {
			log.error(e);
			// except.printStackTrace();
			// throw new
			// RuntimeException("移动文件[从'srcFilePath="+srcFilePath+"'目录到'destFilePath="+this.destFilePath+
			// File.separator+strTime+"'目录出现异常."+except.getMessage());
		}
		// long end = System.currentTimeMillis();
		// long used = end - start;
		// log.info("KpiWarningManager::moveFiles() 共耗时:["+used+" ms].");
		return;
	}

	/**
	 * 移动文件, 将 this.srcFilePath 中的所有东东都移动到 this.destFilePath/yyyyMMddHHmm 中
	 */
	/*
	 * public synchronized void moveFiles() { long start =
	 * System.currentTimeMillis(); if (this.srcFilePath == null ||
	 * "".equals(this.srcFilePath) || this.destFilePath == null ||
	 * "".equals(this.destFilePath)) { throw new
	 * IllegalArgumentException("源文件目录srcFilepath=" + srcFilePath + "]
	 * 或目标文件目录[destFilePath=" + destFilePath + "]未指定."); } File srcFile = new
	 * File(this.srcFilePath); // String strTime =
	 * TimeUtil.timeMillis2Format("yyyyMMddHHmm"); String tmpdestFilePath =
	 * this.destFilePath; try { if (srcFile.exists()) { //
	 * log.info("tmpdestFilePath="+tmpdestFilePath); if (srcFile.isDirectory()) {
	 * this.copyFiles(this.srcFilePath, tmpdestFilePath); } else { //
	 * log.info("srcFile="+srcFile.getAbsolutePath()+"/"+srcFile.getName());
	 * FileHandler.copyFile(this.srcFilePath, tmpdestFilePath, true); } } else {
	 * throw new IllegalArgumentException("给定的源文件目录不存在或有同名的文件,请修正."); }
	 * FileHandler.delete(this.srcFilePath); } catch (Exception except) {
	 * log.error(except); // except.printStackTrace(); // throw new //
	 * RuntimeException("移动文件[从'srcFilePath="+srcFilePath+"'目录到'destFilePath="+this.destFilePath+ //
	 * File.separator+strTime+"'目录出现异常."+except.getMessage()); } finally { ; }
	 * 
	 * long end = System.currentTimeMillis(); long used = end - start;
	 * log.debug("KpiWarningManager::moveFiles() 共耗时:[" + String.valueOf(used) + "
	 * ms]."); return; }
	 */

	/**
	 * 郭飞 16:58:21
	 */
	@SuppressWarnings("unchecked")
	public List searchFile(String str, String path) {
		String searchStr = str.toUpperCase();
		File file = new File(path);
		List<File> list = new ArrayList<File>();
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					this.getFiles(list, file, searchStr);
				} else {
					if (file.getName().toUpperCase().indexOf(searchStr) > -1) {
						list.add(file);
					}
				}
			}
			return list.size() < 1 ? null : list;
		} catch (Exception except) {
			log.error(except);
			// except.printStackTrace();
			return null;
		}
	}

	private List<File> getFiles(List<File> list, File f, String str) {
		File[] file = f.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				this.getFiles(list, file[i], str);
			else if (file[i].getName().toUpperCase().indexOf(str) > -1)
				list.add(file[i]);
		}
		return list;
	}
}
