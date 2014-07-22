package com.yang.javalib.classUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @Description	: 类操作工具类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午12:22:29 
 */
public class ClassLoadUtil { 
	
	private static final Logger log =Logger.getLogger(ClassLoadUtil.class) ;
	

	/**
	 * @Description: 通过Class.forName("类全名")的方式实例化一个Class类。     这种方式在框架中较为多见，另外jdbc技术中也用这个方式来加载所需的驱动程序到容器中。
	 * @param classPath
	 * @throws ClassNotFoundException
	 * void
	 * @throws
	 */
	public static void addCLass(String classPath) throws ClassNotFoundException{ 
		Class.forName(classPath); 
	}
	
	/**
	 * @Description:  
	 * 注意加载linux .so 动态库时, 会自动 在库名前加一个lib 
	 * http://rwl6813021.iteye.com/blog/195814 
	 * @param dir
	 * @throws IOException
	 */
	public static void addLibrary(String dir) throws IOException {
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] paths = (String[]) (String[]) field.get(null);
			for (int i = 0; i < paths.length; ++i) {
				if (dir.equals(paths[i])) {
					return;
				}
			}
			String[] tmp = new String[paths.length + 1];
			System.arraycopy(paths, 0, tmp, 0, paths.length);
			tmp[paths.length] = dir;
			field.set(null, tmp);
		} catch (IllegalAccessException e) {
			throw new IOException(
					"Failed to get permissions to set library path");
		} catch (NoSuchFieldException e) {
			throw new IOException(
					"Failed to get field handle to set library path");
		}
	}
 
	/**
	 * @Description: 打印库路径. 
	 */
	public static void printLibraryPath() {
		String property = System.getProperty("java.library.path");
		StringTokenizer parser = new StringTokenizer(property, ":");
		log.info("java.library.path：");
		while (parser.hasMoreTokens()) {
			log.info(parser.nextToken());
		}
	}
	
	 
	 /**
	 * @Description:  获取指定包名下的所有类
	 * @param packageName
	 * @param isRecursive 递归 :是否查询子包
	 * @return
	 * List<Class<?>>
	 * @throws
	 */
	public static List<Class<?>> getClassList(String packageName, boolean isRecursive) {
	     List<Class<?>> classList = new ArrayList<Class<?>>();
	     try {
	         Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
	         while (urls.hasMoreElements()) {
	             URL url = urls.nextElement();
	             if (url != null) {
	                 String protocol = url.getProtocol();
	                 if (protocol.equals("file")) {
	                     String packagePath = url.getPath();
	                     addClass(classList, packagePath, packageName, isRecursive);
	                 } else if (protocol.equals("jar")) {
	                     JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
	                     JarFile jarFile = jarURLConnection.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while (jarEntries.hasMoreElements()) {
	                         JarEntry jarEntry = jarEntries.nextElement();
	                         String jarEntryName = jarEntry.getName();
	                         if (jarEntryName.endsWith(".class")) {
	                             String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
	                             if (isRecursive || className.substring(0, className.lastIndexOf(".")).equals(packageName)) {
	                                 classList.add(Class.forName(className));
	                             }
	                         }
	                     }
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return classList;
	 }

	 // 获取指定包名下指定注解的所有类
	 public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
	     List<Class<?>> classList = new ArrayList<Class<?>>();
	     try {
	         Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
	         while (urls.hasMoreElements()) {
	             URL url = urls.nextElement();
	             if (url != null) {
	                 String protocol = url.getProtocol();
	                 if (protocol.equals("file")) {
	                     String packagePath = url.getPath();
	                     addClassByAnnotation(classList, packagePath, packageName, annotationClass);
	                 } else if (protocol.equals("jar")) {
	                     JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
	                     JarFile jarFile = jarURLConnection.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while (jarEntries.hasMoreElements()) {
	                         JarEntry jarEntry = jarEntries.nextElement();
	                         String jarEntryName = jarEntry.getName();
	                         if (jarEntryName.endsWith(".class")) {
	                             String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
	                             Class<?> cls = Class.forName(className);
	                             if (cls.isAnnotationPresent(annotationClass)) {
	                                 classList.add(cls);
	                             }
	                         }
	                     }
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return classList;
	 }

	 // 获取指定包名下指定父类的所有类
	 public static List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass) {
	     List<Class<?>> classList = new ArrayList<Class<?>>();
	     try {
	         Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
	         while (urls.hasMoreElements()) {
	             URL url = urls.nextElement();
	             if (url != null) {
	                 String protocol = url.getProtocol();
	                 if (protocol.equals("file")) {
	                     String packagePath = url.getPath();
	                     addClassBySuper(classList, packagePath, packageName, superClass);
	                 } else if (protocol.equals("jar")) {
	                     JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
	                     JarFile jarFile = jarURLConnection.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while (jarEntries.hasMoreElements()) {
	                         JarEntry jarEntry = jarEntries.nextElement();
	                         String jarEntryName = jarEntry.getName();
	                         if (jarEntryName.endsWith(".class")) {
	                             String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
	                             Class<?> cls = Class.forName(className);
	                             if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
	                                 classList.add(cls);
	                             }
	                         }
	                     }
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return classList;
	 }

	 // 获取指定包名下指定接口的所有实现类
	 public static List<Class<?>> getClassListByInterface(String packageName, Class<?> interfaceClass) {
	     List<Class<?>> classList = new ArrayList<Class<?>>();
	     try {
	         Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
	         while (urls.hasMoreElements()) {
	             URL url = urls.nextElement();
	             if (url != null) {
	                 String protocol = url.getProtocol();
	                 if (protocol.equals("file")) {
	                     String packagePath = url.getPath();
	                     addClassByInterface(classList, packagePath, packageName, interfaceClass);
	                 } else if (protocol.equals("jar")) {
	                     JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
	                     JarFile jarFile = jarURLConnection.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while (jarEntries.hasMoreElements()) {
	                         JarEntry jarEntry = jarEntries.nextElement();
	                         String jarEntryName = jarEntry.getName();
	                         if (jarEntryName.endsWith(".class")) {
	                             String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
	                             Class<?> cls = Class.forName(className);
	                             if (interfaceClass.isAssignableFrom(cls) && !interfaceClass.equals(cls)) {
	                                 classList.add(cls);
	                             }
	                         }
	                     }
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return classList;
	 }

	 /**
	 * @Description: TODO
	 * @param classList
	 * @param packagePath
	 * @param packageName
	 * @param isRecursive
	 * void
	 * @throws
	 */
	private static void addClass(List<Class<?>> classList, String packagePath, String packageName, boolean isRecursive) {
	     try {
	         File[] files = getClassFiles(packagePath);
	         if (files != null) {
	             for (File file : files) {
	                 String fileName = file.getName();
	                 if (file.isFile()) {
	                     String className = getClassName(packageName, fileName);
	                     classList.add(Class.forName(className));
	                 } else {
	                     if (isRecursive) {
	                         String subPackagePath = getSubPackagePath(packagePath, fileName);
	                         String subPackageName = getSubPackageName(packageName, fileName);
	                         addClass(classList, subPackagePath, subPackageName, isRecursive);
	                     }
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }

	 private static File[] getClassFiles(String packagePath) {
	     return new File(packagePath).listFiles(new FileFilter() {
	         @Override
	         public boolean accept(File file) {
	             return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
	         }
	     });
	 }

	 /**
	 * @Description: 由包名与文件路径 名,得到对应类的全名.
	 * com.yang
	 * test.java
	 * 
	 * 得到 com.yang.test
	 * @param packageName
	 * @param fileName
	 * @return
	 * String
	 * @throws
	 */
	private static String getClassName(String packageName, String fileName) {
	     String className = fileName.substring(0, fileName.lastIndexOf("."));
	     if (StringUtils.isNotEmpty(packageName)) {
	         className = packageName + "." + className;
	     }
	     return className;
	 }

	 /**
	 * @Description: 得到子包路径 .
	 * @param packagePath
	 * @param filePath
	 * @return
	 * String
	 * @throws
	 */
	private static String getSubPackagePath(String packagePath, String filePath) {
	     String subPackagePath = filePath;
	     if (StringUtils.isNotEmpty(packagePath)) {
	         subPackagePath = packagePath + "/" + subPackagePath;
	     }
	     return subPackagePath;
	 }

	 /**
	 * @Description: TODO
	 * @param packageName
	 * @param filePath
	 * @return
	 * String
	 * @throws
	 */
	private static String getSubPackageName(String packageName, String filePath) {
	     String subPackageName = filePath;
	     if (StringUtils.isNotEmpty(packageName)) {
	         subPackageName = packageName + "." + subPackageName;
	     }
	     return subPackageName;
	 }

	 /**
	 * @Description: 通过
	 * @param classList
	 * @param packagePath
	 * @param packageName
	 * @param annotationClass
	 * void
	 * @throws
	 */
	private static void addClassByAnnotation(List<Class<?>> classList, String packagePath, String packageName, Class<? extends Annotation> annotationClass) {
	     try {
	         File[] files = getClassFiles(packagePath);
	         if (files != null) {
	             for (File file : files) {
	                 String fileName = file.getName();
	                 if (file.isFile()) {
	                     String className = getClassName(packageName, fileName);
	                     Class<?> cls = Class.forName(className);
	                     if (cls.isAnnotationPresent(annotationClass)) {//验证是否包含某个 Annotation
	                         classList.add(cls);
	                     }
	                 } else {
	                     String subPackagePath = getSubPackagePath(packagePath, fileName);
	                     String subPackageName = getSubPackageName(packageName, fileName);
	                     addClassByAnnotation(classList, subPackagePath, subPackageName, annotationClass);
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }

	 private static void addClassBySuper(List<Class<?>> classList, String packagePath, String packageName, Class<?> superClass) {
	     try {
	         File[] files = getClassFiles(packagePath);
	         if (files != null) {
	             for (File file : files) {
	                 String fileName = file.getName();
	                 if (file.isFile()) {
	                     String className = getClassName(packageName, fileName);
	                     Class<?> cls = Class.forName(className);
	                     if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
	                         classList.add(cls);
	                     }
	                 } else {
	                     String subPackagePath = getSubPackagePath(packagePath, fileName);
	                     String subPackageName = getSubPackageName(packageName, fileName);
	                     addClassBySuper(classList, subPackagePath, subPackageName, superClass);
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }

	 private static void addClassByInterface(List<Class<?>> classList, String packagePath, String packageName, Class<?> interfaceClass) {
	     try {
	         File[] files = getClassFiles(packagePath);
	         if (files != null) {
	             for (File file : files) {
	                 String fileName = file.getName();
	                 if (file.isFile()) {
	                     String className = getClassName(packageName, fileName);
	                     Class<?> cls = Class.forName(className);
	                     if (interfaceClass.isAssignableFrom(cls) && !interfaceClass.equals(cls)) {
	                         classList.add(cls);
	                     }
	                 } else {
	                     String subPackagePath = getSubPackagePath(packagePath, fileName);
	                     String subPackageName = getSubPackageName(packageName, fileName);
	                     addClassByInterface(classList, subPackagePath, subPackageName, interfaceClass);
	                 }
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }


}
