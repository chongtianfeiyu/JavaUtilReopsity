package com.yang.javalib.ioUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @Description: 文件操作工具类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-28 上午11:07:43 
 */
public class FileUtil {
	private static final Logger log = Logger.getLogger(FileUtil.class);
	//private static final String zipEntrySep = "/";

	/**
	 * @Fields : 错误信息
	 */
	private String message;

	public String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			//InputStreamReader isr;
			if (encoding.equals(""))
				isr = new InputStreamReader(fs);
			else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					if (data.trim().length() > 0)
						;
					str.append(data + "\r\n");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	public String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			File myFilePath = new File(txt);
			txt = folderPath;
			if (!(myFilePath.exists()))
				myFilePath.mkdir();
		} catch (Exception e) {
			this.message = "";
		}
		return txt;
	}

	public String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); ++i) {
				String txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1)
					txts = createFolder(txts + txt);
				else
					txts = createFolder(txts + txt + "/");
			}
		} catch (Exception e) {
			this.message = "";
		}
		return txts;
	}

	public void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!(myFilePath.exists())) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.print(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			this.message = "";
		}
	}

	/**
	 * @Description: 探测文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		File myFilePath = new File(filePath);
		if (myFilePath.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void createFile(String filePathAndName, String fileContent,
			String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!(myFilePath.exists())) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.print(strContent);
			myFile.close();
		} catch (Exception e) {
			this.message = "";
		}
	}

	/**
	 * @Description: 删除文件
	 * @param filePathAndName
	 * @return
	 */
	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				this.message = filePathAndName + "<br>";
			}
		} catch (Exception e) {
			this.message = e.toString();
		}
		return bea;
	}

	/**
	 * @Description: 删除文件
	 * @param file
	 * @return
	 */
	public boolean delFile(File file) {
		boolean bea = false;
		try {
			if (file.exists()) {
				file.delete();
				bea = true;
			} else {
				bea = false;
			}
		} catch (Exception e) {
			log.error("删除文件失败", e);
		}
		return bea;
	}

	/**
	 * @Description: 删除目录
	 * @param folderPath
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			this.message = "";
		}
	}

	/**
	 * @Description: 删除目录 下的全部文件 
	 * @param path
	 * @return
	 */
	public boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!(file.exists())) {
			return bea;
		}
		if (!(file.isDirectory())) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; ++i) {
			if (path.endsWith(File.separator))
				temp = new File(path + tempList[i]);
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * @Description: copy文件
	 * @param oldPathFile
	 * @param newPathFile
	 */
	public void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPathFile);
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			this.message = "";
		}
	}

	public void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; ++i) {
				if (oldPath.endsWith(File.separator))
					temp = new File(oldPath + file[i]);
				else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + temp.getName().toString());

					byte[] b = new byte[5120];
					int len;

					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		} catch (Exception e) {
			this.message = "";
		}
	}

	/**
	 * @Description: 将文件转移到其他位置
	 * @param oldPath
	 * @param newPath
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * @Description: 将文件夹转移到其他位置
	 * @param oldPath
	 * @param newPath
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * @Description: 得到错误信息
	 * @return
	 */
	public String getMessage() {
		return this.message;
	}

	public File createFile(String filePath, byte[] fileContent) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filePath);
			fileOut.write(fileContent);
			File localFile = new File(filePath);

			return localFile;
		} catch (FileNotFoundException e) {
			log.error("找不到文件" + filePath, e);
		} catch (IOException e) {
			log.error("写文件失败", e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					log.error("生成文件失败", e);
				}
			}
		}

		return null;
	}

	/**
	 * @Description: 得到文件内容,最大2147483647个字节
	 * @param file
	 * @return
	 */
	public byte[] getFileContent(File file) {
		FileInputStream input = null;
		byte[] b;
		try {
			input = new FileInputStream(file);
			int len = (int) file.length();
			if (len > 2147483647) {
				log.error("文件超过处理限制大小：2147483647字节");
				return null;
			}

			b = new byte[len];
			input.read(b);
			return b;
		} catch (Exception e) {
			log.error("获取文件内容失败", e);
			return null;
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (Exception e) {
					log.error("", e);
				}
		}
	}

	/**
	 * @Description: 创建文件
	 * @param destFileName
	 * @return
	 */
	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
			return false;
		}

		if (destFileName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}

		if (!file.getParentFile().exists()) {
			System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目录文件所在的目录失败！");
				return false;
			}
		}

		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				System.out.println("创建单个文件" + destFileName + "失败！");
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + destFileName + "失败！");
			return false;
		}

	}

	/**
	 * @Description: 在指定位置创建目录
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;

		//创建单个目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "成功！");
			return false;
		}

	}

	/**
	 * @Description: 创建 临时文件
	 * @param prefix  The prefix argument must be at least three characters long. It is recommended that the prefix be a short, meaningful string such as "hjb" or "mail". 
	 * @param suffix The suffix argument may be null, in which case the suffix ".tmp" will be used. 
	 * @param dirName 临时文件夹位置, 可为空
	 * @return
	 */
	public static String createTempFile(String prefix, String suffix,
			String dirName) {

		File tempFile = null;
		try {
			if (dirName == null) {
				// 在默认文件夹下创建临时文件
				tempFile = File.createTempFile(prefix, suffix);
				return tempFile.getCanonicalPath();
			} else {
				File dir = new File(dirName);
				// 如果临时文件所在目录不存在，首先创建
				if (!dir.exists()) {
					if (!FileUtil.createDir(dirName)) {
						System.out.println("创建临时文件失败，不能创建临时文件所在目录！");
						return null;
					}
				}
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建临时文件失败" + e.getMessage());
			return null;
		}

	}

	/**
	 * @Description:  得到包内资源路径
	 * FG:getInPageFile("com/yang/module/mail/test.txt"); 
	 * .getPath()   如果 是在JAR包内, 还能通过 路径找到吗 ?
	 *  http://www.iteye.com/topic/483115 参考 这个.
	 *  templetPath =templetPath.replaceFirst("/", "");
	 * @param fileName
	 */
	public static URL getInPageFilePath(String fileName) {

		return FileUtil.class.getClassLoader().getResource(fileName);
	}

	/**
	 * @Description: 得到包内文件流
	 * @param fileName
	 * @return
	 */
	public static InputStream getInPageFileStream(String fileName) {
		InputStream io = FileUtil.class.getClassLoader().getResourceAsStream(
				fileName);
		return io;
	}

	public static void main(String[] args) {

		URL temp = FileUtil.getInPageFilePath("com/yang/module/mail/test.txt");
		System.out.println(temp.getPath());

	}

}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.FileUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/
