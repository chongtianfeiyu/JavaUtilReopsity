package com.yang.javalib.ioUtil;

public class FilePathUtil {

	
	public static String getFileNameFromFullPath(String fullFilePath){
		
		String _filePath =fullFilePath.replaceAll("/", "/");
		int lastSplitIndex =_filePath.lastIndexOf("/");
		int lastSpotIndex =_filePath.lastIndexOf(".");
		return fullFilePath.substring(lastSplitIndex+1); 
	}
	
	
	public static void main(String[] args) {
		System.out.println(FilePathUtil.getFileNameFromFullPath("http://www.tesetu.com/tupian/UploadFiles_5281/201109/20110915120556883.jpg"));
	}
	
}
