package com.yang.javalib.json;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.json.dto.TaskDTO;
import com.yang.javalib.json.dto.TaskGroupDTO;
import com.yang.javalib.json.dto.User;
 

/**
 * @Description: JsonUtil测试用命工具类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-16 下午9:00:21 
 */
public class JsonUtilTest {
	private static final Logger log =Logger.getLogger(JsonUtilTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArray2Json() {

		
		//fail("Not yet implemented");
	}
	


	/**
	 * @Description: http://jiangzhengjun.iteye.com/blog/467334
	 */
	@Test
	public void testStr2JsonList() {

		
		//fail("Not yet implemented");
	}
	
	

	@Test
	public void testMsg2Json() {
		//fail("Not yet implemented");
	}

	@Test
	public void testObject2FormJson() {
		//fail("Not yet implemented");
	}

	/**
	 * @Description: 把简单json转换为 JSONObject对象,
	 *  1 .并且从对象中,取出某个属性
	 *  2.遍历对象.
	 */
	@Test
	public void testStr2JSONObject() {
		//String temp = "{\"opetartorType\":\"confirm\",\"alarmName\":\"自营期货委托行情 发生故障 20130320105000421\",\"alarmType\":\"4\",\"alarmLevel\":\"4\",\"alarmTime\":\"2013-03-20 11:58:24\",\"alarmSource\":\"168.8.90.135\",\"alarmDataSource\":\"\",\"alarmDesc\":\"自营期货委托行情 期货委托行情-405 发生故障,故障信息： 44[错误]2013-03-20 10:49:24:系统异常：Access violation at address 40006880 in module 'rtl60.bpl'. Read of address 00000004\",\"isconfirm\":\"1\"}";
		String temp = "{'opetartorType':'confirm','alarmName':'自营期货委托行情 发生故障 20130320105000421','alarmType':'4','alarmLevel':'4','alarmTime':'2013-03-20 11:58:24','alarmSource':'168.8.90.135','alarmDataSource':'','alarmDesc':'自营期货委托行情 期货委托行情-405 发生故障,故障信息： 44[错误]2013-03-20 10:49:24:系统异常：Access violation at address 40006880 in module . Read of address 00000004','isconfirm':'1'}";
		JSONObject object = JsonUtil.str2JSONObject(temp);
		
	    String _opetartorType =	(String)object.get("opetartorType");
		log.info("从字符串中得到一个属性(字符):"+_opetartorType);
		 
		log.info("遍历对象.");
		Set keySet  =object.keySet();
	 
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String _key = (String) iterator.next();
			String _value =(String)object.get(_key);
			 
			 log.info("得到的值 为- key:"+_key+"      value:"+_value);
			
		}
		 
		
		log.info(object);
	}
	

	/**
	 * @Description: 把简单json转换为 JSONObject对象,
	 *  1 .并且从对象中,取出某个{}属性 
	 *  2.遍历对象.
	 */
	@Test
	public void testStr3JSONObject() { 
		//String temp = "{\"opetartorType\":\"confirm\",\"alarmName\":\"自营期货委托行情 发生故障 20130320105000421\",\"alarmType\":\"4\",\"alarmLevel\":\"4\",\"alarmTime\":\"2013-03-20 11:58:24\",\"alarmSource\":\"168.8.90.135\",\"alarmDataSource\":\"\",\"alarmDesc\":\"自营期货委托行情 期货委托行情-405 发生故障,故障信息： 44[错误]2013-03-20 10:49:24:系统异常：Access violation at address 40006880 in module 'rtl60.bpl'. Read of address 00000004\",\"isconfirm\":\"1\"}";
		String temp = "{datas:{aa:11,bb:22,cc:33},'opetartorType':'confirm','alarmName':'自营期货委托行情 发生故障 20130320105000421','alarmType':'4','alarmLevel':'4','alarmTime':'2013-03-20 11:58:24','alarmSource':'168.8.90.135','alarmDataSource':'','alarmDesc':'自营期货委托行情 期货委托行情-405 发生故障,故障信息： 44[错误]2013-03-20 10:49:24:系统异常：Access violation at address 40006880 in module . Read of address 00000004','isconfirm':'1'}";
		JSONObject object = JsonUtil.str2JSONObject(temp);
		
	    String _opetartorType =	(String)object.get("opetartorType");
		log.info("从字符串中得到一个属性(字符):"+_opetartorType);
		 
		log.info("遍历对象.");
		Set keySet  =object.keySet();
	 
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String _key = (String) iterator.next();
			Object tt = object.get(_key);
			
			if(tt instanceof JSONObject){// datas 会走这里.
				System.out.println(_key+"   是 JSONObject");
				 
			}
			
			if(tt instanceof JSONArray){
				System.out.println(_key+ "  是JSONArray");
				
			}
			  if(tt instanceof String){  //
					String _value =(String)object.get(_key);
					 log.info("得到的值 为- key:"+_key+"      value:"+_value);
			  }
			
			
		}
		 
		
		log.info(object);
	}

	/**
	 * @Description: 把json字符串转化为对象．　
	 * void
	 * @throws
	 */
	@Test
	public void testString2DtoObject() {

	 String jsonString ="{\"message\":null,\"data\":{\"aaData\":[ {\"id\":100702,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B2C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":1297,\"days\":\"7天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"200,000\",\"activeFlag\":1,\"penalty\":\"546\",\"totalReward\":\"1,092\",\"totalNum\":\"21\",\"showTime\":\"5秒\",\"taskName\":\"我买网-顶级初榨橄榄油\",\"oneDayNum\":\"3\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":1011,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100706,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B4C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-5.htm\",\"taskCount\":427,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"500,000\",\"activeFlag\":1,\"penalty\":\"1,967\",\"totalReward\":\"3,933\",\"totalNum\":\"20\",\"showTime\":\"5秒\",\"taskName\":\"我买网手机客户端\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":637,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100704,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B3C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":421,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"300,000\",\"activeFlag\":1,\"penalty\":\"1,175\",\"totalReward\":\"2,350\",\"totalNum\":\"20\",\"showTime\":\"4秒\",\"taskName\":\"中粮-节日里的家之盛宴\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":635,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-15\",\"cpaUrl\":null,\"cpaImgUrl\":null}],\"itotalRecords\":84,\"itotalDisplayRecords\":84,\"secho\":null},\"success\":true}" ;
	 TaskGroupDTO taskGroupDTO =	parseTaskGroupDTO(jsonString);
	 System.out.println(taskGroupDTO.getItotalRecords());
	 
	}
	

	@Test
	public void testString2DtoObject泛型方法() {

	 String jsonString ="{\"message\":null,\"data\":{\"aaData\":[ {\"id\":100702,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B2C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":1297,\"days\":\"7天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"200,000\",\"activeFlag\":1,\"penalty\":\"546\",\"totalReward\":\"1,092\",\"totalNum\":\"21\",\"showTime\":\"5秒\",\"taskName\":\"我买网-顶级初榨橄榄油\",\"oneDayNum\":\"3\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":1011,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100706,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B4C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-5.htm\",\"taskCount\":427,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"500,000\",\"activeFlag\":1,\"penalty\":\"1,967\",\"totalReward\":\"3,933\",\"totalNum\":\"20\",\"showTime\":\"5秒\",\"taskName\":\"我买网手机客户端\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":637,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100704,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B3C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":421,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"300,000\",\"activeFlag\":1,\"penalty\":\"1,175\",\"totalReward\":\"2,350\",\"totalNum\":\"20\",\"showTime\":\"4秒\",\"taskName\":\"中粮-节日里的家之盛宴\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":635,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-15\",\"cpaUrl\":null,\"cpaImgUrl\":null}],\"itotalRecords\":84,\"itotalDisplayRecords\":84,\"secho\":null},\"success\":true}" ;

		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("aaData", TaskDTO.class);

		JSONObject jsonObject = JSONObject.fromObject(jsonString); 
		 
		JSONArray array= jsonObject.getJSONObject("data").getJSONArray("aaData");
		
		List<TaskDTO> result =	JsonUtil.getDTOList(array, new TaskDTO());
		
		for (TaskDTO taskDTO : result) {
			System.out.println(taskDTO.toString());
			
		}
		 
	 
	}
	
	
	/**
	 * @Description: 解析任务列表
	 * 测试数据	//String jsonString ="{\"message\":null,\"data\":{\"aaData\":[ {\"id\":100702,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B2C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":1297,\"days\":\"7天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"200,000\",\"activeFlag\":1,\"penalty\":\"546\",\"totalReward\":\"1,092\",\"totalNum\":\"21\",\"showTime\":\"5秒\",\"taskName\":\"我买网-顶级初榨橄榄油\",\"oneDayNum\":\"3\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":1011,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100706,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B4C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-5.htm\",\"taskCount\":427,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"500,000\",\"activeFlag\":1,\"penalty\":\"1,967\",\"totalReward\":\"3,933\",\"totalNum\":\"20\",\"showTime\":\"5秒\",\"taskName\":\"我买网手机客户端\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":637,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-11\",\"cpaUrl\":null,\"cpaImgUrl\":null},{\"id\":100704,\"description\":\"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B3C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm\",\"taskCount\":421,\"days\":\"10天\",\"taskType\":\"2\",\"businessCirclesId\":null,\"margins\":\"300,000\",\"activeFlag\":1,\"penalty\":\"1,175\",\"totalReward\":\"2,350\",\"totalNum\":\"20\",\"showTime\":\"4秒\",\"taskName\":\"中粮-节日里的家之盛宴\",\"oneDayNum\":\"2\",\"isAbnormal\":0,\"tagConfValue\":\"设置推荐人奖励\",\"tagId\":null,\"tagConfStatus\":null,\"tagFlag\":null,\"adsType\":0,\"videoUrl\":null,\"taskSeq\":635,\"starNum\":4,\"bussinessCirclesName\":null,\"couponId\":null,\"couponName\":null,\"couponContent\":null,\"couponReceiveCount\":0,\"isNew\":1,\" nowShowDate\":\"2014-04-15\",\"cpaUrl\":null,\"cpaImgUrl\":null}],\"itotalRecords\":84,\"itotalDisplayRecords\":84,\"secho\":null},\"success\":true}" ;
	 * @param jsonMsg
	 * @return
	 */
	public static TaskGroupDTO parseTaskGroupDTO(String jsonMsg) {

		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("aaData", TaskDTO.class);

		JSONObject jsonObject = JSONObject.fromObject(jsonMsg);
		JSONObject _data = jsonObject.getJSONObject("data");
		TaskGroupDTO taskGroupDTO = (TaskGroupDTO) _data.toBean(_data, TaskGroupDTO.class, classMap);

		return taskGroupDTO;
	}
	
	/**
	 * @Description: 字符变为JSONArray
	 */
	@Test
	public void testString2JSONArray() { 
		String jsonStr ="[{aa:11,bb:22,cc:33},{dd:44,ee:55,ff:66}]";
		 
		JSONArray  jSONArray =JsonUtil.string2JSONArray(jsonStr); 
		 
	}
	
	 /** 
     * 从数据中减去不需要穿的属性 
     */  
    @Test  
    public void jsonTest()  
    {  
        //数据模拟  
        List<User> list = new ArrayList<User>();  
          
        User user1 = new User(1,"sha",16);  
        User user2 = new User(2,"sha",16);  
        User user3 = new User(3,"sha",16);  
          
        list.add(user1);  
        list.add(user2);  
        list.add(user3);  
          
        //核心代码  
        //1.创建JsonConfig对象  
        JsonConfig jsonConfig = new JsonConfig();   
        //2.设置排除的属性  
        jsonConfig.setExcludes(new String[] { "id"});  
        //3.转换  
        JSONArray jsonArray = JSONArray.fromObject( list ,jsonConfig);   
        System.out.println(jsonArray);  
    }  

}
