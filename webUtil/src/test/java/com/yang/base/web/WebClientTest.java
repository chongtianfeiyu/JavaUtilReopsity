package com.yang.base.web;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WebClientTest {
	private static final Logger logger =Logger.getLogger(WebClientTest.class) ;
	  
	@Before
	public void setUp() throws Exception {
		 
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 测试带登录 的 HTTP GET 请求方法.
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testGetHttpClient() throws HttpException, IOException {
		//String targetURL ="http://login.cjsc.com.cn/cas/login;jsessionid=7F979A949F685BDF0649707334DAEE23?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf?open";
		//String targetURL ="http://www.baidu.com";
		String targetURL ="http://www.qianbao666.com";
	
		String userName="servicedesk";
		String passWord="servicedesk";
		GetMethod getMethod = WebClient.getGetMethod(targetURL, userName, passWord);
		 
		logger.info(getMethod.getResponseBodyAsString());
		
		//HttpResponseVO httpResponseVO = WebClient.getPostMethod("www.qianbao.com");
		
		//fail("Not yet implemented");
	}
	
	/**
	 * @Description: 使用代理访问  
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testProxyGetHttpClient() throws HttpException, IOException {
		String targetURL ="http://www.qianbao666.com";
	
		//String prosyIp="127.0.0.1";
		//int proxyPort =8087 ; 
		//GetMethod getMethod = WebClient.getGetMethodProxy(targetURL, prosyIp, proxyPort); 
		//log.info(getMethod.getResponseBodyAsString()); 
	}
	
	
	
	

	@Test
	public void testGet2HttpClient() throws HttpException, IOException {
		String targetURL ="http://www.qianbao666.com";
		HttpClientParams params =new HttpClientParams() ;

		params = new HttpClientParams();
		try {
			params.setSoTimeout(2000);
		} catch (Exception e) {
			params.setSoTimeout(2000);
		}
		
		HttpResponseVO httpResponseVO = WebClient.getGetMethod(targetURL,params); 
		logger.info(httpResponseVO.getContent());
		
		
		
		//fail("Not yet implemented");
	}

	@Test
	public void testGet2HttpsGetMethod() throws HttpException, IOException {
		String targetURL ="https://www.qianbao666.com";
		HttpClientParams params =new HttpClientParams() ;

		params = new HttpClientParams();
		try {
			params.setSoTimeout(2000);
		} catch (Exception e) {
			params.setSoTimeout(2000);
		}
		
		HttpResponseVO httpResponseVO = WebClient.getHttpsGetMethod(targetURL, params);
		logger.info(httpResponseVO.getContent());
		
		
		
		//fail("Not yet implemented");
	}

	@Test
	public void TestGetJson(){
		String targetURL ="http://www.qianbao666.com/task/listUserTaskInProcess.html";
		HttpClientParams params =new HttpClientParams() ;

		params = new HttpClientParams();
		try {
			params.setSoTimeout(2000);
		} catch (Exception e) {
			params.setSoTimeout(2000);
		}
		
		HttpResponseVO httpResponseVO = WebClient.getHttpsGetMethod(targetURL, params);
		String jsonStr  =httpResponseVO.getContent();
		logger.info("得到 JSON字符串: "+jsonStr);
		
		 
	}
	

	@Test
	public void testGetGetMethodStringStringString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetGetMethodProxy() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetGetMethodStringHttpClientParams() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetHttpsGetMethod() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPostMethodStringStringString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCasLoginResponse() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetQianBaoLoginResponse() {
		//fail("Not yet implemented");
	}

}
