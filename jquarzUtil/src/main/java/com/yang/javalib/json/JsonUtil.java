package com.yang.javalib.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 * @Description: 
 * 
使用JSON时，除了要导入JSON网站上面下载的
json-lib-2.2-jdk15.jar包之外，还必须有其它几个依赖包：
commons-beanutils.jar，
commons-httpclient.jar，
commons-lang.jar，
ezmorph.jar，
morph-1.0.1.jar

这几个包也是需要导入的．如果缺少里面的：ezmorph.jar包，则即出现上述异常
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-13 下午12:42:41 
 */
public class JsonUtil {

	public static void main(String[] args) {
		//JSONArray kSONArray =new JSONArray();
		//kSONArray.add("yang");

		String temp = "{\"opetartorType\":\"confirm\",\"alarmName\":\"自营期货委托行情 发生故障 20130320105000421\",\"alarmType\":\"4\",\"alarmLevel\":\"4\",\"alarmTime\":\"2013-03-20 11:58:24\",\"alarmSource\":\"168.8.90.135\",\"alarmDataSource\":\"\",\"alarmDesc\":\"自营期货委托行情 期货委托行情-405 发生故障,故障信息： 44[错误]2013-03-20 10:49:24:系统异常：Access violation at address 40006880 in module 'rtl60.bpl'. Read of address 00000004\",\"isconfirm\":\"1\"}";

		JSONObject object = JsonUtil.str2JSONObject(temp);

		// String temp  =	JsonUtil.array2Json(kSONArray, 1L);
		//字符及汉字要加  '' 
		//String temp = "{qw:12312,er:312312}";
		/*
		JsonUtil.str2JSONObject(temp); 
		JSONArray kSONArray = JsonUtil.string2JSONArray(temp);
		*/
		System.out.println(object.toString());
	}

	/**
	 * 转json
	 * 
	 * @param arr
	 * @param totalProperty
	 * @return
	 */
	public static String array2Json(JSONArray arr, Long totalProperty) {

		String json = "{success:true,totalProperty:" + totalProperty + ",root:"
				+ arr.toString() + "}";
		return json;
	}

	/**
	 * 返回提示json
	 * 
	 * @param success
	 * @param msg
	 * @return
	 */
	public static String msg2Json(boolean success, String msg) {
		JSONObject json = new JSONObject();
		json.put("success", success);
		if (msg != null && !"".equals(msg))
			json.put("msg", msg);

		return json.toString();
	}

	/**
	 * 返回form数据
	 * 
	 * @param success
	 * @param obj
	 * @return
	 */
	public static String object2FormJson(boolean success, JSONObject obj) {
		return "{success:" + success + ",data:" + obj.toString() + "}";
	}

	/**
	 * @Description: TODO
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject str2JSONObject(String jsonStr) { 
 	    String tempString  = string2Json(jsonStr) ;
		JSONObject object = JSONObject.fromObject(string2Json(jsonStr));
		//JSONObject object = JSONObject.fromObject(jsonStr); 
		return object;
	}

	/**
	 * 根据字符串返回JSONArray
	 * 
	 * @param arr
	 * @return
	 */
	public static JSONArray string2JSONArray(String arr) {
		String arrJson = "";
		if (arr == null) {
			arrJson = "[]";
		} else if (!arr.startsWith("[")) {
			arrJson = "[" + arr + "]";
		} else {
			arrJson = arr;
		}
		return JSONArray.fromObject(string2Json(arrJson));
	}

	/** 
	 * JSON字符串特殊字符处理，比如：“\A1;1300”  换行　
	 * 这一步操作很有必要. 
	 * @param s 
	 * @return String 
	 */
	private static String string2Json(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
//  http://www.cnblogs.com/NF-CHH/archive/2012/08/21/JsonUtil.html

    /**页面传至后台时，json数据在request的参数名称*/  
    public final static String JSON_ATTRIBUTE = "json";  
    public final static String JSON_ATTRIBUTE1 = "json1";  
    public final static String JSON_ATTRIBUTE2 = "json2";  
    public final static String JSON_ATTRIBUTE3 = "json3";  
    public final static String JSON_ATTRIBUTE4 = "json4";  
      
    /** 
     * 从一个JSON 对象字符格式中得到一个java对象，形如： 
     * {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}} 
     * @param object 
     * @param clazz 
     * @return 
     */
    public static Object getDTO(String jsonString, Class clazz){  
        JSONObject jsonObject = null;  
        try{  
            setDataFormat2JAVA();   
            jsonObject = JSONObject.fromObject(jsonString);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return JSONObject.toBean(jsonObject, clazz);  
    }  
      
    /** 
     * 从一个JSON 对象字符格式中得到一个java对象，其中beansList是一类的集合，形如： 
     * {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, 
     * beansList:[{}, {}, ...]} 
     * @param jsonString 
     * @param clazz 
     * @param map 集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg: ("beansList" : Bean.class) 
     * @return 
     */  
    public static Object getDTO(String jsonString, Class clazz, Map map){  
        JSONObject jsonObject = null;  
        try{  
            setDataFormat2JAVA();   
            jsonObject = JSONObject.fromObject(jsonString);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return JSONObject.toBean(jsonObject, clazz, map);  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象数组，形如： 
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param object 
     * @param clazz 
     * @return 
     */  
    public static Object[] getDTOArray(String jsonString, Class clazz){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        Object[] obj = new Object[array.size()];  
        for(int i = 0; i < array.size(); i++){  
            JSONObject jsonObject = array.getJSONObject(i);  
            obj[i] = JSONObject.toBean(jsonObject, clazz);  
        }  
        return obj;  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象数组，形如： 
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param object 
     * @param clazz 
     * @param map 
     * @return 
     */  
    public static Object[] getDTOArray(String jsonString, Class clazz, Map map){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        Object[] obj = new Object[array.size()];  
        for(int i = 0; i < array.size(); i++){  
            JSONObject jsonObject = array.getJSONObject(i);  
            obj[i] = JSONObject.toBean(jsonObject, clazz, map);  
        }  
        return obj;  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象集合 
     * @param object 
     * @param clazz 
     * @return 
     */  
    public static List getDTOList(String jsonString, Class clazz){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString); 
        List list = new ArrayList();  
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add(JSONObject.toBean(jsonObject, clazz));  
        }
        return list;  
    }

    /**
     * @Description: 把JSONArray , 转换为对应 的list对象
     * @param array
     * @param clazz
     * @return
     * List
     * @throws
     */
   /* public static List getDTOList(JSONArray array, Class clazz){  
        setDataFormat2JAVA();
        List list = new ArrayList();  
         
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add(JSONObject.toBean(jsonObject, clazz));  
        }  
        return list;  
    }*/
    

    /**
     * @Description: 使用泛型方法,得到确定的返回对象
     * @param array
     * @param clazz
     * @return
     * List<T>
     * @throws
     */
    public static<T> List<T> getDTOList(JSONArray array, T clazz){  
        setDataFormat2JAVA();
        List<T> list = new ArrayList<T>();  
         
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add((T)JSONObject.toBean(jsonObject, clazz.getClass()));  
        }  
        return list;  
    }
      
    /** 
     * 从一个JSON数组得到一个java对象集合，其中对象中包含有集合属性 
     * @param object 
     * @param clazz 
     * @param map 集合属性的类型 
     * @return 
     */  
    public static List getDTOList(String jsonString, Class clazz, Map map){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString); 
        
        List list = new ArrayList();  
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add(JSONObject.toBean(jsonObject, clazz, map));  
        }  
        return list;  
    }  
      
    /** 
     * 从json HASH表达式中获取一个map，该map支持嵌套功能 
     * 形如：{"id" : "johncon", "name" : "小强"} 
     * 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap 
     * @param object 
     * @return 
     */  
    public static Map getMapFromJson(String jsonString) {  
        setDataFormat2JAVA();  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Map map = new HashMap();  
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){  
            String key = (String)iter.next();  
            map.put(key, jsonObject.get(key));  
        }  
        return map;  
    }  
      
    /** 
     * 从json数组中得到相应java数组 
     * json形如：["123", "456"] 
     * @param jsonString 
     * @return 
     */  
    public static Object[] getObjectArrayFromJson(String jsonString) {  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        return jsonArray.toArray();  
    }  
  
  
    /** 
     * 把数据对象转换成json字符串 
     * DTO对象形如：{"id" : idValue, "name" : nameValue, ...} 
     * 数组对象形如：[{}, {}, {}, ...] 
     * map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...} 
     * @param object 
     * @return 
     */  
    public static String getJSONString(Object object) throws Exception{  
        String jsonString = null;  
        //日期值处理器  
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());  
        if(object != null){  
            if(object instanceof Collection || object instanceof Object[]){  
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();  
            }else{  
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();  
            }  
        }  
        return jsonString == null ? "{}" : jsonString;  
    }  
      
    private static void setDataFormat2JAVA(){  
        //设定日期转换格式  
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));  
    }   
}
class JsonDateValueProcessor implements JsonValueProcessor {  
	  
    private String format = "yyyy-MM-dd HH:mm:ss";  
  
    public JsonDateValueProcessor() {  
  
    }  
  
    public JsonDateValueProcessor(String format) {  
        this.format = format;  
    }  
  
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
        return process(value, jsonConfig);  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
        return process(value, jsonConfig);  
    }
  
    private Object process(Object value, JsonConfig jsonConfig) {  
        if (value instanceof Date) {  
            String str = new SimpleDateFormat(format).format((Date) value);  
            return str;  
        }  
        return value == null ? null : value.toString();  
    }  
  
    public String getFormat() {  
        return format;  
    }  
  
    public void setFormat(String format) {  
        this.format = format;  
    }  
  
}  