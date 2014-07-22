package com.yang.javalib.ioUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author Jinson
 *
 */

public class FileCompare {
	
	private static final Logger log = Logger.getLogger(FileCompare.class);
	
	/**
	 * 获取单个文件的MD5值！
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 获取文件夹中文件的MD5值
	 * @param file
	 * @param listChild ;true递归子目录中的文件
	 * @return
	 */
	public static Map<String, String> getDirMD5(File file, boolean listChild) {
		if (!file.isDirectory()) {
			return null;
		}
		//<filepath,md5>
		Map<String, String> map = new HashMap<String, String>();
		String md5;
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory() && listChild) {
				map.putAll(getDirMD5(f, listChild));
			} else {
				md5 = getFileMD5(f);
				if (md5 != null) {
					map.put(f.getPath(), md5);
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 文件是否相同（空行不进行对比判断）
	 * @param fileName1
	 * @param fileName2
	 * @return
	 */
	public static boolean compareFileSize(String fileName1, String fileName2) {
		boolean compareFlag = false;
        try {
			File file1 = new File(fileName1);
			File file2 = new File(fileName2);
			if(file1.length()==file2.length()){
				compareFlag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return compareFlag;
	}
	
	/**
	 * 文件是否相同（空行不进行对比判断）
	 * @param fileName1
	 * @param fileName2
	 * @return
	 */
	public static boolean compareFile(String fileName1, String fileName2, String[] filterArray) {
		boolean compareFlag = false;
        BufferedReader reader1 = null;
        BufferedReader reader2 = null;
        try {
        	
        	//先比较文件MD5码
        	if(getFileMD5(new File(fileName1)).equals(getFileMD5(new File(fileName2)))){
        		return true;
        	}
        	
        	reader1 = new BufferedReader(new FileReader(new File(fileName1)));
            reader2 = new BufferedReader(new FileReader(new File(fileName2)));
            
            Boolean isBetweenAnnotation1 = false;
            StringBuffer endFilter1 = new StringBuffer();
            String lineContent1 = null;
            StringBuffer sb1 = new StringBuffer();
            StringBuffer annotationSb1 = new StringBuffer();  //注释缓存内容
             
            Boolean isBetweenAnnotation2 = false;
            StringBuffer endFilter2 = new StringBuffer();
            String lineContent2 = null;
            StringBuffer sb2 = new StringBuffer();
            StringBuffer annotationSb2 = new StringBuffer();
            
            int line1 = 1;
            while ((lineContent1 = reader1.readLine()) != null) {
            	isBetweenAnnotation1 = appendFileContent(isBetweenAnnotation1, endFilter1, line1, sb1, annotationSb1, lineContent1, filterArray);
            	line1++;
            }
            log.debug("------------------------------------------------------");
            log.debug("文件1注释:\n");
            log.debug(annotationSb1.toString());
            log.debug("------------------------------------------------------");
            
            int line2 = 1;
            while ((lineContent2 = reader2.readLine()) != null) {
            	isBetweenAnnotation2 = appendFileContent(isBetweenAnnotation2, endFilter2, line2, sb2, annotationSb2, lineContent2, filterArray);
            	line2 ++;
            }
            log.debug("------------------------------------------------------");            
            log.debug("文件1注释:\n");
            log.debug(annotationSb2.toString());
            log.debug("------------------------------------------------------");
            
            reader1.close();
            reader2.close();
            
            if(sb1.toString().equals(sb2.toString())){
            	compareFlag = true;
            }
            log.debug("文件1内容:\n");
            log.debug(sb1.toString());
            log.debug("------------------------------------------------------");
            log.debug("文件2内容:\n");
            log.debug(sb2.toString());
            log.debug("------------------------------------------------------");
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                	reader1.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
            if (reader2 != null) {
                try {
                	reader2.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
        return compareFlag;
    }
	
	
	public static boolean compareFile(String fileName1, String fileName2, String filterStr) {
		String[] filterArray = splitFileFilterString(filterStr);
		return compareFile(fileName1,fileName2,filterArray);
	}
	
	
	/**
	 * 文件过滤条件判断
	 * @param sb
	 * @param lineContent
	 * @param filterStrings
	 */
	
	public static Boolean appendFileContent(Boolean isBetweenAnnotation,StringBuffer endFilter, int line, StringBuffer sb,StringBuffer annotationSb, String lineContent,String[] filterArray){
		if(isBetweenAnnotation){//注释行
			annotationSb.append(lineContent).append(System.getProperty("line.separator"));
			if(endFilter!=null && lineContent.trim().endsWith(endFilter.toString())){
				isBetweenAnnotation = false;
				log.debug("注释行止["+line+"]："+lineContent);				
			}
			
		}else {
			
			if(lineContent.trim().equals("")){//空行
	    		log.debug("line " + line + ": " + lineContent);
	    	} else {
	    		if(filterArray!=null){//注释符比较
	    			boolean isAnnotationTmp = false;
	    			for (int i = 0; i < filterArray.length; i++) {
	    				String filter = filterArray[i];
	    				if(!isAnnotationTmp){
	    					if(filterArray[i].indexOf(",")!=-1){//注释  /*,*/
		    					String pre = filter.substring(0,filter.indexOf(","));
		    					String sub = filter.substring(filter.indexOf(",")+1);
		    					if(lineContent.trim().startsWith(pre) && lineContent.trim().endsWith(sub)){
		    						log.debug("注释行["+line+"]："+lineContent);
		    						isAnnotationTmp = true;
		    						annotationSb.append(lineContent).append(System.getProperty("line.separator"));
		    					} else if(lineContent.trim().startsWith(pre) && (!lineContent.trim().endsWith(sub))){
		    						log.debug("注释行起["+line+"]："+lineContent);
		    						isAnnotationTmp = true;
		    						isBetweenAnnotation = Boolean.TRUE;
		    						if(endFilter!=null&&!"".equals(endFilter.toString())){
		    							endFilter.delete(0, endFilter.length());
		    						}		    						
		    						endFilter.append(sub);
		    						annotationSb.append(lineContent).append(System.getProperty("line.separator"));
		    					}else {
		    						isAnnotationTmp = false;
		    						if(i==(filterArray.length-1)){//最后匹配
		    							sb.append(lineContent).append(System.getProperty("line.separator"));
			    						break;			    						
		    						}
								}
		    					
		    				}else{
		    					if(lineContent.startsWith(filter)){//非注释符开始  // # ; 等
		    						log.debug("注释行["+line+"]："+lineContent);
		    						isAnnotationTmp = true;
		    						annotationSb.append(lineContent).append(System.getProperty("line.separator"));
		    					}else{
		    						isAnnotationTmp = false;
		    						if(i==(filterArray.length-1)){//最后匹配
		    							sb.append(lineContent).append(System.getProperty("line.separator"));
		    							break;
		    						}
		    						
		    						
			    				}
		    				}	    					
	    				}	    				
					}
	    			
	    		} else {//无注释符比较条件
	    			sb.append(lineContent).append(System.getProperty("line.separator"));
				}    		
			}
			
		}
		
		return isBetweenAnnotation;
		
	}
	
	
	/**
	 * 选择文件指定内容
	 * @param fileName
	 * @return
	 */
	public static String getFileAnnotationContent(String content, String[] filterArray) {
		String annotationContent = null;
        BufferedReader reader1 = null;
        try {
        	InputStream is = new ByteArrayInputStream(content.getBytes());
        	reader1 = new BufferedReader(new InputStreamReader(is));
        	//reader1 = new BufferedReader(new FileReader(new File(fileName1)));
            
            Boolean isBetweenAnnotation1 = false;
            StringBuffer endFilter1 = new StringBuffer();
            String lineContent1 = null;
            StringBuffer sb1 = new StringBuffer();
            StringBuffer annotationSb1 = new StringBuffer();  //注释缓存内容
                         
            int line1 = 1;
            while ((lineContent1 = reader1.readLine()) != null) {
            	isBetweenAnnotation1 = appendFileContent(isBetweenAnnotation1, endFilter1, line1, sb1, annotationSb1, lineContent1, filterArray);
            	line1++;
            }
            log.debug("------------------------------------------------------");
            log.debug("文件注释:\n");
            log.debug(annotationSb1.toString());
            log.debug("------------------------------------------------------");            
            reader1.close();
            
            log.debug("文件内容:\n");
            log.debug(sb1.toString());
            log.debug("------------------------------------------------------");
            
            annotationContent = annotationSb1.toString();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                	reader1.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
        return annotationContent;
    }
	
	
	
	
	/**
	 * 读取指定行文件内容
	 * @param fileName
	 * @param lineNumber
	 * @throws IOException
	 */
	public static String readLineContent(String fileName, int lineNumber) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String lineContent = reader.readLine();
        try {
        	if (lineNumber < 0 || lineNumber > getTotalLines(fileName)) {
        		lineContent = null;
                log.debug("不在文件的行数范围之内。");
            }
            int num = 0;
            while (lineContent != null) {
                if (lineNumber == ++num) {
                    log.debug("line  " + lineNumber + ":   " + lineContent);
                }
                lineContent = reader.readLine();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
        return lineContent;
    }
        

	/**
	 * 文件内容的总行数 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static int getTotalLines(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}
	
	
	/**
	 * 文件字节对比
	 * @param sourceFile
	 * @param outputFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean fileCompare(File sourceFile, File outputFile) throws FileNotFoundException, IOException {
		InputStreamReader s1 = new InputStreamReader(new FileInputStream(sourceFile));
		InputStreamReader s2 = new InputStreamReader(new FileInputStream(outputFile));
		char str1[] = new char[10 * 1024]; //文件大小不超过10K
		char str2[] = new char[10 * 1024];
		if(s1.read(str1)!=-1 && s2.read(str2)!=-1){
			
		}
		
		int len1 = s1.read(str1);
		int len2 = s2.read(str2);
		int i;
		for (i = 0; i < len1 && i < len2; i++) {
			if (str1[i] != str2[i])
				return false;
		}
		if (i == len1 && i == len2)
			return true;
		else
			return false;
	}
	
	/**
	 * 消除注释空格
	 * @param filterStr
	 * @return
	 */
	public static String[] splitFileFilterString(String filterStr){
		String[] newArr = null;
		if(filterStr!=null && !filterStr.trim().equals("")){
			String[] splitArr = filterStr.trim().split("\n");
			if(splitArr!=null){
				newArr = new String[splitArr.length];
				for (int i = 0; i < splitArr.length; i++) {
					newArr[i] = splitArr[i].trim();
				}
			}
		}
		return newArr;
	}
	

	public static void main(String[] args) throws IOException {
		
		String a = "﻿//aa", b="/";
		log.debug(a.startsWith(b));
		
	    //log.debug("fileMD5:"+getFileMD5(new File("c:/a.txt")));
		String[] filterArray = {"//","/**,*/","/*,*/","#",";"};
		log.debug(compareFile("c:/a.txt","c:/aa.txt",filterArray));
		System.exit(0);
	}

}
