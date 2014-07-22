package com.yang.javalib.mavenUtil.mavenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yang.javalib.mavenUtil.pojo.MavenJarVO;
 

/**
 * @Description	: maven库输助工具.
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-21 上午9:02:11 
 */
public class MavenUtil {
	
	private static String baseRepoPath="D:/maven_resource_myeclipse/repository/";

	public static boolean delFailedPath(String[] failPaths){
		 
		//String baseRepoPath ="D:/maven_resource_myeclipse/repository/";
		
		//String[] failPath=new String[]{"artifact org.mvel:mvel2:jar:2.1.3.Final"};
		
		List<MavenJarVO>  mavenJarList =handlePath(baseRepoPath,failPaths);
		
		for (MavenJarVO mavenJarVO : mavenJarList) {
			File fileDir =new File(mavenJarVO.getJarDir());
			  
			if(fileDir.isDirectory()) {
				if(fileDir.delete()) {
					System.out.println("成功删除目录:"+mavenJarVO.getJarDir());
				}else {
					 File[] subFile =	fileDir.listFiles();
					
					 for (File file : subFile) {
						 //if(!file.getName().toUpperCase().endsWith("JAR")){
							 if(file.delete()){ 
									System.out.println("成功删除子文件"+file.getAbsolutePath());
							 }
						// }
					}
				}
			}
			
		}
		
		return true; 
	}
	

	/**
	 * @Description: 把相应的路径转换成为MavenJarVO,
	 * @param basePath
	 * @param mavenPath
	 * @return
	 * List<MavenJarVO>
	 * @throws
	 */
	private static List<MavenJarVO> handlePath(String basePath,String[] mavenPath){
		List<MavenJarVO> path =new ArrayList<MavenJarVO>(mavenPath.length) ;
		  
		for (String mavenItem : mavenPath) {
			String[] items =mavenItem.split(":");
			
			String mavenJarPath =items[0].replace(".", "/");
			String mavenJarName =items[1];
			String mavenJartype =items[2];
			String mavenJarVersion =items[3]; 
			String jarDir =basePath+mavenJarPath+"/"+mavenJarName+"/"+mavenJarVersion;
			String jarAbstractPath =basePath+mavenJarPath+"/"+mavenJarName+"/"+mavenJarVersion+"/"+mavenJarName+"-"+mavenJarVersion+"."+mavenJartype; 
			System.out.println(jarAbstractPath);
	
			MavenJarVO mavenJarVO =new MavenJarVO(mavenJarName,jarDir,mavenJarVersion,jarAbstractPath) ;
			path.add(mavenJarVO) ; 
			System.out.println(mavenJarVO.toString());
		}
		 
		return path ; 
	}
	
}
