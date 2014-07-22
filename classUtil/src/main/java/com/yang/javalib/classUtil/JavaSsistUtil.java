package com.yang.javalib.classUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

public class JavaSsistUtil {
	 
	

	public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, ClassNotFoundException {
 
		
		/*
		DynamicCreateObject dco = new DynamicCreateObject();

		
		Object student1 = null, team = null;
		
		Map<String, Object> fieldMap = new HashMap<String, Object>();//属性-取值map
		fieldMap.put("name", "xiao ming");
		fieldMap.put("age", 27);
		
		
		student1 = dco.addField("Student", fieldMap);//创建一个名称为Student的类
		Class c = Class.forName("Student");
		Object s1 = c.newInstance();//创建Student类的对象
		Object s2 = c.newInstance();
		dco.setFieldValue(s1, "name", " xiao ming ");//创建对象s1赋值
		dco.setFieldValue(s2, "name", "xiao zhang");
		fieldMap.clear();
		
		List<Object> students = new ArrayList<Object>();
		students.add(s1);
		students.add(s2);
		fieldMap.put("students", students);
		
		team = dco.addField("Team", fieldMap);// //创建一个名称为Team的类
		
		Field[] fields = team.getClass().getDeclaredFields();
		if (fields != null) {
			for (Field field : fields)
				System.out.println(field.getName() + "="
						+ dco.getFieldValue(team, field.getName()));
		}*/
	}

}

class DynamicCreateObject {

	/**
	     * 为对象动态增加属性，并同时为属性赋值
	     * @param className 需要创建的java类的名称
	     * @param fieldMap  字段-字段值的属性map，需要添加的属性
	     * @return
	     * @throws NotFoundException
	     * @throws CannotCompileException
	     */
	public Object addField(String className, Map<String, Object> fieldMap)
			throws NotFoundException, CannotCompileException,
			IllegalAccessException, InstantiationException {
		ClassPool pool = ClassPool.getDefault();//获取javassist类池

		CtClass ctClass = null ;//TODO 
				//pool.makeClass(className,pool.get(DObject.class.getName()));//创建javassist类
		// 为创建的类ctClass添加属性
		Iterator it = fieldMap.entrySet().iterator();
		while (it.hasNext()) { // 遍历所有的属性
			Map.Entry entry = (Map.Entry) it.next();
			String fieldName = (String) entry.getKey();
			Object fieldValue = entry.getValue();
			// 增加属性，这里仅仅是增加属性字段
			String fieldType = fieldValue.getClass().getName();
			CtField ctField = new CtField(pool.get(fieldType), fieldName,
					ctClass);
			ctField.setModifiers(Modifier.PUBLIC);
			ctClass.addField(ctField);
		}
		Class c = ctClass.toClass();// 为创建的javassist类转换为java类
		Object newObject = c.newInstance();// 为创建java对象

		// 为创建的类newObject属性赋值
		it = fieldMap.entrySet().iterator();
		while (it.hasNext()) { // 遍历所有的属性
			Map.Entry entry = (Map.Entry) it.next();
			String fieldName = (String) entry.getKey();
			Object fieldValue = entry.getValue();
			// 为属性赋值
			this.setFieldValue(newObject, fieldName, fieldValue);
		}
		return newObject;
	}

	/**
	 * 获取对象属性赋值
	 * @param dObject
	 * @param fieldName 字段别名
	 * @return
	 */
	public Object getFieldValue(Object dObject, String fieldName) {
		Object result = null;
		try {
			Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
			try {
				fu.setAccessible(true); // 设置对象属性域的访问属性
				result = fu.get(dObject); // 获取对象属性域的属性值
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 给对象属性赋值
	 * @param dObject
	 * @param fieldName
	 * @param val
	 * @return
	 */
	public Object setFieldValue(Object dObject, String fieldName, Object val) {
		Object result = null;
		try {
			Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
			try {
				fu.setAccessible(true); // 设置对象属性域的访问属性
				fu.set(dObject, val); // 设置对象属性域的属性值
				result = fu.get(dObject); // 获取对象属性域的属性值
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}

}
