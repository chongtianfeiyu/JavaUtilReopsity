package com.yang.javalib.httpclient4Util.HttpClient4Util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.UriUtils;

import com.yang.javalib.httpclient4Util.dto.HttpResponseVO;

public class HttpClientUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: OK, 发出httpClient请求, 这个也是可以 的.赞. 
	 * void
	 * @throws
	 */
	@Test
	public void test1DoGet() { 
		HttpClientUtil client = new HttpClientUtil(); 
		//client.doGet("http://www.baidu.com/s?wd=HttpClient"); //OK  百度查询关键字　HttpClient
	 

		//client.doGet("http://www.qianbao666.com/ntask/home.html?co=0&po=0&ti=1&re=0&mr=0&mrb=&mre=&ty=0&p=1");
		 
	
	
	}

	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * @Description: 这一个下载 的是JSON值 . 赞. 赞赞. 
	 * void
	 * @throws
	 */
	@Test
	public void test2DoPost() throws ParseException, IOException {
		HttpClientUtil client = new HttpClientUtil();
		
		  List<NameValuePair> formparams = new ArrayList<NameValuePair>();// 设置表格参数  
	        formparams.add(new BasicNameValuePair("usrname", "admin"));  
	        formparams.add(new BasicNameValuePair("password", "123456"));  
	        UrlEncodedFormEntity uefEntity = null;  
	        try {
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");//获取实体对象  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }
		 
	        
		client.doPost("http://www.qianbao666.com/task/listTask.html", uefEntity);
	}

	/**
	 * @Description: 可以使用，　赞，　就是文件格式，这个问题，　是固定　的吗　？
	 * @throws Exception
	 * void
	 * @throws
	 */
	@Test
	public void test3DownloadFile() throws Exception {
		String imgUrl="http://www.lashou.com/account/captcha";
		String imgSavePath="temp/yz.png";
		HttpClientUtil.downloadFile(imgUrl, imgSavePath);
	}

	@Test
	public void test403下载图片() throws Exception {

		HttpClientUtil client = new HttpClientUtil(); 
		HttpResponseVO httpResponseVO =client.doGet("http://www.03iii.com/zipai/list_2_1.html");
		 System.out.println("列表内容："+httpResponseVO.getContent());
		System.out.println("########################");
		
		httpResponseVO =client.doGet("http://www.03iii.com/zipai/2014/0522/7374.html");
		
		 System.out.println("单个文章内容："+httpResponseVO.getContent());
			System.out.println("########################");
		    String tt ="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%98O%C6%B7%C9%ED%B2%C4%B5%C4%D0%A1%D9%E2%F7%C8%C1%A6%CA%AE%D7%E3%C5%B688P%2F10%2Ejpg" ;
		   
			java.net.URLEncoder.encode(tt);
			System.out.println(java.net.URLDecoder.decode(tt));
			
			// http://hi.baidu.com/water_1221/item/90314616439c01048fbde46f
		String imgUrl="http://www.lashou.com/account/captcha";
		String imgSavePath="temp/yz.jpg";
		HttpClientUtil.downloadFile(tt, imgSavePath);
	}
	 

	@Test
	public void test403Decode() throws Exception {
		 String tt ="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%98O%C6%B7%C9%ED%B2%C4%B5%C4%D0%A1%D9%E2%F7%C8%C1%A6%CA%AE%D7%E3%C5%B688P%2F10%2Ejpg" ;
		 String url = "http://www.example.com/chapter1/%3Fref%3Dsomething%26term%3D?ref=xyz";
		 //URIUtils.extractHost(uri);
		// URI myUri = new URI(UriUtils.encodeHttpUrl(url)) ;
	    //System.out.println(java.net.URLDecoder.decode(tt));

			tt="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%CE%D2%BC%D2%B5%C4%B4%F3%B2%A8%C5%AE%BA%BA%D7%D319P%2F1%2Ejpg";
			
			//URLEncodedUtils.parse(entity)
			System.out.println(UriUtils.decode("%98O", "GBK")); //UriUtils.decode(tt, "GBK")

			//使用spring中的util可以 , 但是使用jdk的就不行. 难不成还得引入spring的. 

			System.out.println(UriUtils.decode("%98O", "GBK"));
			System.out.println(UriUtils.decode(tt, "GBK"));
			System.out.println(java.net.URLDecoder.decode(tt, "gb2312"));
			System.out.println(java.net.URLDecoder.decode(tt, "GB18030"));
	}
	
	@Test
	public void test403下载图片jpg() throws Exception {
		String tt = "http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%98O%C6%B7%C9%ED%B2%C4%B5%C4%D0%A1%D9%E2%F7%C8%C1%A6%CA%AE%D7%E3%C5%B688P%2F10%2Ejpg";

		java.net.URLEncoder.encode(tt);

		// http://hi.baidu.com/water_1221/item/90314616439c01048fbde46f
		String imgUrl = "http://www.lashou.com/account/captcha";
		String imgSavePath = "temp/jingcaituijian06.jpg";
		HttpClientUtil.downloadFile(tt, imgSavePath);

		tt="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%CE%D2%BC%D2%B5%C4%B4%F3%B2%A8%C5%AE%BA%BA%D7%D319P%2F1%2Ejpg";
		
		//URLEncodedUtils.parse(entity)
		System.out.println(UriUtils.decode("%98O", "GBK")); //UriUtils.decode(tt, "GBK")

		//使用spring中的util可以 , 但是使用jdk的就不行. 难不成还得引入spring的. 

		System.out.println(UriUtils.decode("%98O", "GBK"));
		System.out.println(UriUtils.decode(tt, "GBK"));
		System.out.println(java.net.URLDecoder.decode(tt, "gb2312"));
		System.out.println(java.net.URLDecoder.decode(tt, "GB18030"));
		System.out.println(java.net.URLDecoder.decode(tt, "utf-8"));
		System.out.println(java.net.URLDecoder.decode(tt, "GBK"));
		System.out.println(java.net.URLDecoder.decode(tt, "Big5"));
		System.out.println(java.net.URLDecoder.decode(tt, "Unicode"));
		System.out.println(java.net.URLDecoder.decode(tt, "GB18030"));
	}

	@Test
	public void test503文章处理() throws Exception {

	String	tt="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F%CE%D2%BC%D2%B5%C4%B4%F3%B2%A8%C5%AE%BA%BA%D7%D319P%2F1%2Ejpg";
	//tt="http://up.10gi.com/uploads/140501/%CD%B5%C5%C4%D7%D4%C5%C4%5B1%5D%2F10%2D25%D7%EE%CF%B2%BB%B6%C5%AE%D3%D1%A3%AC%BA%AC%C7%E9%C4%AC%C4%AC%B5%D8%CE%AA%CE%D2%B4%B5%F3%EF%A3%A115P%2F1%2Ejpg";
	
	
	String imgUrl =	UriUtils.decode(tt, "GBK");
	HttpClientUtil.downloadFile(tt, "temp/ttt2.jpg");
	
	   /*HttpClient client = new HttpClient();  
       GetMethod get = new GetMethod("http://images.sohu.com/uiue/sohu_logo/beijing2008/2008sohu.gif");  
       client.executeMethod(get);  
       File storeFile = new File("c:/2008sohu.gif");  
       FileOutputStream output = new FileOutputStream(storeFile);  
       //得到网络资源的字节数组,并写入文件  
       output.write(get.getResponseBody());  
       output.close();  */
	

	
	
	}
	
	
}
