package com.yang.javalib.swtUtil.common.file;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @Description: 文件选择器工具类.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-2 下午11:41:15 
 */
public class FileChooseUtil { 
	
	private static final Logger logger =Logger.getLogger(FileChooseUtil.class);
	
	/**
	 * @Description: 选择一个文件
	 * @param parent
	 * @return
	 */
	public static String chooseFile(Shell parent) {

		 
		FileDialog dlg = new FileDialog(parent, SWT.OPEN);
		
		
		dlg.getFileNames();
		String fileName = dlg.open();
		if (fileName != null) {
			logger.info("选择文件为:" + fileName);
			return fileName;
		}
		return "";

	}

	/**
	 * @Description: 选择多个文件.
	 * @param parent
	 * @return
	 */
	public static String[] chooseMultiFile(Shell parent) {
 
		FileDialog dlg = new FileDialog(parent, SWT.OPEN|SWT.MULTI);
		String fileName = dlg.open();//返回最后一个选择文件的全路径
		String[] fileNames = dlg.getFileNames();//返回所有选择的文件名，不包括路径
		String path = dlg.getFilterPath();//返回选择的路径，这个和fileNames配合可以得到所有的文件的全路径
		  
		if (fileNames != null) {
			
			for (int i = 0; i < fileNames.length; i++) {
				String string = fileNames[i];
				fileNames[i]= path+File.separatorChar +fileNames[i];
				logger.info("选择文件为:" + fileNames[i]);
			} 
			return fileNames;
		}
		return null;

	}

	/**
	 * @Description: 保存文件对话框
	 * @param parent
	 * @return
	 */
	public static String saveFile(Shell parent) {
		FileDialog dialog = new FileDialog(parent,SWT.SAVE);
		 String fileName = dialog.open();  //获得保存的文件名
		 return  fileName;
	}
	
	

	/**
	 * @Description: 选择一个文件夹
	 * @param parent
	 * @return
	 */
	public static String chooseDirectory(Shell parent) {
		DirectoryDialog dlg = new DirectoryDialog(parent,SWT.OPEN);
		String dirPath = dlg.getFilterPath();
		logger.info("选择文件为:" + dirPath);
		return dirPath;
	}
	
	

}
