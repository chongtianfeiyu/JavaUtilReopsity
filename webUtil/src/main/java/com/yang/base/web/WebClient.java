package com.yang.base.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yang.base.regular.RegUtil.RegUtil;

/**
 * OK 可以使用.
 *
 * @author Yourname
 * @since ECMP 1.0 2011-9-13
 */
public class WebClient {

	private static Logger logger = Logger.getLogger(WebClient.class);

	static {
		Protocol myhttps = new Protocol("https",
				new MySecureProtocolSocketFactory(), 443);

		Protocol.registerProtocol("https", myhttps);
		/*
		Protocol.registerProtocol("https", new Protocol("https",
				new EasySSLProtocolSocketFactory(), 443));*/
	}

	/**
	 * @Description: 得到HTTPClient
	 * @return
	 */
	public HttpClient getHttpClient() {

		HttpClient client = new HttpClient();
		return client;
	}

	/**
	 * get方式访问 带密码验证
	 *
	 * @param targetURL
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static GetMethod getGetMethod(String targetURL, String userName,
			String passWord) throws HttpException, IOException {
		HttpClient client = new HttpClient();

		logger.info("开始访问:" + targetURL);
		GetMethod oneGetMethod = new GetMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		oneGetMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gb2312");

		UsernamePasswordCredentials upc = new UsernamePasswordCredentials(
				userName, passWord);
		client.getState().setCredentials(AuthScope.ANY, upc);

		try {
			client.executeMethod(oneGetMethod);
			if (oneGetMethod.getStatusCode() == HttpStatus.SC_OK) {
				return oneGetMethod;
			} else {
				if (logger.isDebugEnabled())
					logger.debug("访问WEB方法,执行过程中出现了错误");
			}
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误" + e.getMessage());
			throw e;
		}
		return null;
	}

	/**
	 * get方式访问 不 带密码验证
	 *
	 * @param targetURL
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static GetMethod getGetMethodProxy(String targetURL, String IP,
			int port) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setProxy(IP, port);

		List<Header> headers = new ArrayList<Header>();
		/*headers.add(new Header("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		*/
		headers.add(new Header("User-Agent",
				"Opera/9.63 (Windows NT 5.1; U; Edition IBIS; zh-cn) Presto/2.1.1)"));

		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);

		logger.info("开始访问:" + targetURL);
		GetMethod oneGetMethod = new GetMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		oneGetMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gb2312");

		try {
			client.executeMethod(oneGetMethod);
			if (oneGetMethod.getStatusCode() == HttpStatus.SC_OK) {
				return oneGetMethod;
			} else {
				if (logger.isDebugEnabled())
					logger.debug("访问WEB方法,执行过程中出现了错误");
			}
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误" + e.getMessage());
			throw e;
		}
		return null;
	}

	public static HttpResponseVO getGetMethod(String targetURL){
		
		return getGetMethod(targetURL,null);
	}
	/**
	 * get方式访问不 带密码验证
	 *
	 * @param targetURL
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponseVO getGetMethod(String targetURL,
			HttpClientParams params) {
		HttpClient client = new HttpClient();

		HttpConnectionManagerParams managerParams = client
				.getHttpConnectionManager().getParams();
		if(params!=null){
		//  连接超时 MS 
		managerParams.setConnectionTimeout(params.getSoTimeout());
		// 读数据超时
		managerParams.setSoTimeout(params.getSoTimeout());
		}else {
			//  连接超时 MS 
			managerParams.setConnectionTimeout(20000);
			// 读数据超时
			managerParams.setSoTimeout(20000); 
		}
		
		
		logger.info("开始访问:" + targetURL);
		GetMethod oneGetMethod = new GetMethod(targetURL);

		long time = System.currentTimeMillis();
		String responseBody = "";
		int statusCode = HttpStatus.SC_NOT_FOUND;

		try {
			client.executeMethod(oneGetMethod);
			time = System.currentTimeMillis() - time;
			byte[] contentBytes = IOUtils.toByteArray(oneGetMethod
					.getResponseBodyAsStream());

			responseBody = new String(contentBytes, "utf-8");

			String charSet = null;
			if (responseBody.toLowerCase().contains("charset=utf-8"))
				charSet = null;
			else if (responseBody.toLowerCase().contains("charset=gbk"))
				charSet = "gbk";
			else if (responseBody.toLowerCase().contains("charset=gb2312"))
				charSet = "gb2312";
			else if (responseBody.toLowerCase().contains("charset=iso-8859-1"))
				charSet = null;

			if (charSet != null)
				responseBody = new String(contentBytes, charSet);

			if (StringUtils.isBlank(responseBody.trim())) {
				Header[] Header = oneGetMethod.getResponseHeaders();
				for (Header header : Header)
					responseBody += header;
			}
			statusCode = oneGetMethod.getStatusCode();
		} catch (HttpException e) {
			time = System.currentTimeMillis() - time;
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误", e);
		} catch (IOException e) {
			time = System.currentTimeMillis() - time;
			logger.warn("读取xml文件流时, 发生IOException错误", e);
		} finally {
			oneGetMethod.releaseConnection();
		}

		return new HttpResponseVO(responseBody, statusCode, time);
	}

	public static HttpResponseVO getHttpsGetMethod(String targetURL,
			HttpClientParams params) {

		HttpClient client = new HttpClient();
		client.setParams(params);

		logger.info("开始访问:" + targetURL);
		GetMethod oneGetMethod = new GetMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		oneGetMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gb2312");

		long time = System.currentTimeMillis();
		String responseBody = "";
		int statusCode = HttpStatus.SC_NOT_FOUND;

		try {
			client.executeMethod(oneGetMethod);
			responseBody = oneGetMethod.getResponseBodyAsString();
			statusCode = oneGetMethod.getStatusCode();
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误", e);
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误", e);
		} finally {
			oneGetMethod.releaseConnection();
		}

		time = System.currentTimeMillis() - time;

		return new HttpResponseVO(responseBody, statusCode, time);
	}

	/**
	 * post方式访问 带密码验证
	 *
	 * @param targetURL
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponseVO getPostMethod(String targetURL, String userName,
			String passWord) throws HttpException, IOException {
		HttpClient client = new HttpClient();

		logger.info("开始访问:" + targetURL);
		PostMethod onePostMethod = new PostMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		/*
		 * onePostMethod.addRequestHeader("Content-Type",
		 * "application/x-www-form-urlencoded;charset=gb2312");
		 */
		// --------------
		Credentials defaultcreds = new UsernamePasswordCredentials(userName,
				passWord);// 设置basic的用户名,和密码.
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		// -------------

		String content   ="" ;
		int statusCode = HttpStatus.SC_NOT_FOUND; 
		long time = System.currentTimeMillis(); 
		
		try {
			client.executeMethod(onePostMethod);
			time = System.currentTimeMillis() - time;
			if (onePostMethod.getStatusCode() == HttpStatus.SC_OK) { 
				statusCode=onePostMethod.getStatusCode();
				content=onePostMethod.getResponseBodyAsString();
			} else {
				if (logger.isDebugEnabled())
					logger.debug("访问WEB方法,执行过程中出现了错误"); 
			}
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误" + e.getMessage());
			throw e;
		} 

		return new HttpResponseVO(content, statusCode, time);
	}

	/**
	 * @Description: 长江证券的CAS 系统认证. 注意 LT 值 是实时取的, 在其他 的CAS系统可能会有不同. 
	 * 大致访问过程,先访问CAS页面,取得动态 的LT ,然后 再访问 CASSE,返回302 , 再跳转到要访问的页面.
	 * @param casServiceUrl  其中 service 是可以自定义指向的. 在长江OA中, 首页是一个框架,所以 我跳过框架,直接访问 了页面内容. 
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponseVO getCasLoginResponse(String casServiceUrl,
			HttpClientParams params, String userName, String passWord) {

		long time = System.currentTimeMillis();
		String responseBody = "";

		HttpClient client = new HttpClient();

		HttpConnectionManagerParams managerParams = client
				.getHttpConnectionManager().getParams();
		//  连接超时 MS 
		managerParams.setConnectionTimeout(params.getSoTimeout());
		// 读数据超时
		managerParams.setSoTimeout(params.getSoTimeout());

		// 防止 Cookie rejected:   domain must start with a dot
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		//"http://login.cjsc.com.cn/cas/login?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf?open"); 
		//"http://login.cjsc.com.cn/cas/login?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf/FM_Index?readform"
		PostMethod onePostMethod = new PostMethod( //直接访问 框架里的内容 
				casServiceUrl);
		//
		String ServiceUrl = RegUtil.getMatchStr(casServiceUrl,
				"\\?service=(.*)$", 1);
		logger.info("服务URL 是:" + ServiceUrl);

		//找到临时ID值. 把LT 这个参数写入请求中去
		GetMethod oneGetMethod = null;

		String temp = "";
		try {
			client.executeMethod(onePostMethod);
			temp = onePostMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			logger.warn("访问URL +" + casServiceUrl + "出现问题", e);
		} catch (IOException e) {
			logger.warn("读取HTTP返回数据 +" + casServiceUrl + "出现问题", e);
		}

		String lt = RegUtil.getMatchStr(temp,

		"<input type=\"hidden\" name=\"lt\" value=\"(.*)\" />", 1);

		logger.info("得到参数lt 的值 为:" + lt);
		onePostMethod.addParameter("username", userName);
		onePostMethod.addParameter("password", passWord);
		onePostMethod.addParameter("_eventId", "submit");
		onePostMethod.addParameter("lt", lt); //登  录
		onePostMethod.addParameter("submit", "登  录");//中文会出现乱码. 设置后就不会出现了
		//onePostMethod.addParameter("submit", "%E7%99%BB++%E5%BD%95");//中文会出现乱码.
		onePostMethod.addParameter("warn", "false");
		NameValuePair[] nvs = onePostMethod.getParameters();
		int lengthtemp = 0;//计算表单中数据的长度. 
		String temppostDate = "";
		for (NameValuePair nameValuePair : nvs) {
			temppostDate = temppostDate + nameValuePair.getName() + "="
					+ nameValuePair.getValue() + "&";
		}
		//分别代表 & 与 = 
		logger.info("temppostDate=" + temppostDate);
		temppostDate = temppostDate.replace(" ", "+");
		int length = 0;
		try {
			temppostDate = temppostDate.substring(0, temppostDate.length() - 1);
			// 下面的空格 是用%20代替,  而发送出去的编码 空格 是用+代替 
			// 应该还会有其他的不同.  暂时先这样写
			logger.info("temppostDate编码后值 为: "
					+ URIUtil.encodeQuery(temppostDate));
			length = URIUtil.encodeQuery(temppostDate, "UTF-8").length();
			logger.info("temppostDate长度 "
					+ URIUtil.encodeQuery(temppostDate, "UTF-8").length());
		} catch (URIException e1) {
			e1.printStackTrace();
		}

		onePostMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		onePostMethod.setRequestHeader("Host", "login.cjsc.com.cn");

		//int length = 154 + userName.length() + passWord.length();

		//logger.info("length" + length);

		onePostMethod.setRequestHeader("Content-Length", length + ""); // 176 168  
		//onePostMethod.setRequestHeader("Cookie", CookieStr); //抓包分析后发现,Cookie会从client中自动加入
		onePostMethod.getParams().setContentCharset("UTF-8");
		onePostMethod
				.addRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		onePostMethod.addRequestHeader("Accept-Charset",
				"GBK,utf-8;q=0.7,*;q=0.3");
		onePostMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		onePostMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		onePostMethod.addRequestHeader("Cache-Control", "max-age=0");
		onePostMethod.addRequestHeader("Connection", "keep-alive");
		onePostMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		/*
		  //登陆单个系统 可以 的例子
		PostMethod onePostMethod = new PostMethod(
				"http://10.106.200.222:8280/monWeb/security_login");

		onePostMethod.addParameter("j_username", "admin");
		onePostMethod.addParameter("j_password", "password123");
		*/
		try {
			client.executeMethod(onePostMethod);
		} catch (HttpException e) {
			logger.warn("访问URL +" + casServiceUrl + "出现问题", e);
		} catch (IOException e) {
			logger.warn("读取" + casServiceUrl + "返回数据出现问题", e);
		}

		int statusCode = onePostMethod.getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = onePostMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {

				String ticket = RegUtil.getMatchStr(locationHeader.getValue(),
						"ticket=.*");
				if (ServiceUrl.contains("?")) {
					location = ServiceUrl + "&" + ticket;
				} else {
					location = ServiceUrl + "?" + ticket;
				}

				//RegUtil.getMatchStr(locationHeader.getValue(), "?[a-zA-Z0-9]=")

				oneGetMethod = new GetMethod(location);
				try {
					client.executeMethod(oneGetMethod);
					temp = oneGetMethod.getResponseBodyAsString();
					if (logger.isDebugEnabled()) {
						logger.debug("访问系统" + location + " 得到页面内容:\n\r" + temp);
					}
					return new HttpResponseVO(temp,
							oneGetMethod.getStatusCode(),
							System.currentTimeMillis() - time);
				} catch (HttpException e) {
					logger.warn("访问URL +" + location + "出现问题", e);
				} catch (IOException e) {
					logger.warn("读取" + location + "返回数据出现问题", e);
				}
			} else {
				System.err.println("Location field value is null.");
				return new HttpResponseVO(responseBody, statusCode,
						System.currentTimeMillis() - time);
			}
		}
		return new HttpResponseVO("从CAS跳转到失败. !~", 404,
				System.currentTimeMillis() - time);
	}
	

	/**
	 * @Description: 得到钱宝网,登录 的
	 * @param client
	 * @param casServiceUrl
	 * @param params
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static HttpResponseVO getQianBaoLoginResponse(HttpClient client,String casServiceUrl,
			HttpClientParams params, String userName, String passWord) {

		long time = System.currentTimeMillis();
		String responseBody = "";
 

		HttpConnectionManagerParams managerParams = client
				.getHttpConnectionManager().getParams();
		//  连接超时 MS 
		managerParams.setConnectionTimeout(params.getSoTimeout());
		// 读数据超时
		managerParams.setSoTimeout(params.getSoTimeout());

		// 防止 Cookie rejected:   domain must start with a dot
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		//"http://login.cjsc.com.cn/cas/login?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf?open"); 
		//"http://login.cjsc.com.cn/cas/login?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf/FM_Index?readform"
		PostMethod onePostMethod = new PostMethod( //直接访问 框架里的内容 
				casServiceUrl);
		//
		String ServiceUrl = RegUtil.getMatchStr(casServiceUrl,
				"\\?service=(.*)$", 1);
		logger.info("服务URL 是:" + ServiceUrl);

		//找到临时ID值. 把LT 这个参数写入请求中去
		GetMethod oneGetMethod = null;

		String temp = "";
		try {
			client.executeMethod(onePostMethod);
			temp = onePostMethod.getResponseBodyAsString();
			logger.info(temp);
		} catch (HttpException e) {
			logger.warn("访问URL +" + casServiceUrl + "出现问题", e);
		} catch (IOException e) {
			logger.warn("读取HTTP返回数据 +" + casServiceUrl + "出现问题", e);
		}

		String lt = RegUtil.getMatchStr(temp,

		"<input type=\"hidden\" name=\"lt\" value=\"(.*)\" />", 1);

		logger.info("得到参数lt 的值 为:" + lt);
		onePostMethod.addParameter("username", userName);
		onePostMethod.addParameter("password", passWord);
		onePostMethod.addParameter("_eventId", "submit");
		onePostMethod.addParameter("j_captcha_response", "submit");
		onePostMethod.addParameter("lt", lt); //登  录
		onePostMethod.addParameter("submit", "登录");//中文会出现乱码. 设置后就不会出现了
		//onePostMethod.addParameter("submit", "%E7%99%BB++%E5%BD%95");//中文会出现乱码.
		//onePostMethod.addParameter("warn", "false");
		NameValuePair[] nvs = onePostMethod.getParameters();
		int lengthtemp = 0;//计算表单中数据的长度. 
		String temppostDate = "";
		for (NameValuePair nameValuePair : nvs) {
			temppostDate = temppostDate + nameValuePair.getName() + "="
					+ nameValuePair.getValue() + "&";
		}
		//分别代表 & 与 = 
		logger.info("temppostDate=" + temppostDate);
		temppostDate = temppostDate.replace(" ", "+");
		int length = 0;
		try {
			temppostDate = temppostDate.substring(0, temppostDate.length() - 1);
			// 下面的空格 是用%20代替,  而发送出去的编码 空格 是用+代替 
			// 应该还会有其他的不同.  暂时先这样写
			logger.info("temppostDate编码后值 为: "
					+ URIUtil.encodeQuery(temppostDate));
			length = URIUtil.encodeQuery(temppostDate, "UTF-8").length();
			logger.info("temppostDate长度 "
					+ URIUtil.encodeQuery(temppostDate, "UTF-8").length());
		} catch (URIException e1) {
			e1.printStackTrace();
		}

		onePostMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		onePostMethod.setRequestHeader("Host", "www.qianwang365.com");

		//int length = 154 + userName.length() + passWord.length();

		//logger.info("length" + length);

		onePostMethod.setRequestHeader("Content-Length", length + ""); // 176 168  
		//onePostMethod.setRequestHeader("Cookie", CookieStr); //抓包分析后发现,Cookie会从client中自动加入
		onePostMethod.getParams().setContentCharset("UTF-8");
		onePostMethod.addRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		onePostMethod.addRequestHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.3");
		onePostMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		onePostMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		onePostMethod.addRequestHeader("Cache-Control", "max-age=0");
		onePostMethod.addRequestHeader("Connection", "keep-alive");
		onePostMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		/*
		  //登陆单个系统 可以 的例子
		PostMethod onePostMethod = new PostMethod(
				"http://10.106.200.222:8280/monWeb/security_login");

		onePostMethod.addParameter("j_username", "admin");
		onePostMethod.addParameter("j_password", "password123");
		*/
		try {
			client.executeMethod(onePostMethod);
		} catch (HttpException e) {
			logger.warn("访问URL +" + casServiceUrl + "出现问题", e);
		} catch (IOException e) {
			logger.warn("读取" + casServiceUrl + "返回数据出现问题", e);
		}

		int statusCode = onePostMethod.getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = onePostMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {

				String ticket = RegUtil.getMatchStr(locationHeader.getValue(),
						"ticket=.*");
				if (ServiceUrl.contains("?")) {
					location = ServiceUrl + "&" + ticket;
				} else {
					location = ServiceUrl + "?" + ticket;
				}

				//RegUtil.getMatchStr(locationHeader.getValue(), "?[a-zA-Z0-9]=")

				oneGetMethod = new GetMethod(location);
				try {
					client.executeMethod(oneGetMethod);
					temp = oneGetMethod.getResponseBodyAsString();
					if (logger.isDebugEnabled()) {
						logger.debug("访问系统" + location + " 得到页面内容:\n\r" + temp);
					}
					return new HttpResponseVO(temp,
							oneGetMethod.getStatusCode(),
							System.currentTimeMillis() - time);
				} catch (HttpException e) {
					logger.warn("访问URL +" + location + "出现问题", e);
				} catch (IOException e) {
					logger.warn("读取" + location + "返回数据出现问题", e);
				}
			} else {
				System.err.println("Location field value is null.");
				return new HttpResponseVO(responseBody, statusCode,
						System.currentTimeMillis() - time);
			}
		}
		return new HttpResponseVO("从CAS跳转到失败. !~", 404,
				System.currentTimeMillis() - time);
	}

	/**
	 * post方式访问 不 带密码验证
	 *
	 * @param targetURL
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponseVO getPostMethod(String targetURL)
			throws HttpException, IOException {
		HttpClient client = new HttpClient();

		logger.info("开始访问:" + targetURL);
		PostMethod onePostMethod = new PostMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		onePostMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gb2312");

		String content   ="" ;
		int statusCode = HttpStatus.SC_NOT_FOUND; 
		long time = System.currentTimeMillis(); 
		try {
			client.executeMethod(onePostMethod);
			logger.info(onePostMethod.getStatusCode());
			if (onePostMethod.getStatusCode() == HttpStatus.SC_OK) {

				client.executeMethod(onePostMethod);
				time = System.currentTimeMillis() - time;
			} else {
				if (logger.isDebugEnabled())
					logger.debug("访问WEB方法,执行过程中出现了错误");
			}
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误" + e.getMessage());
			throw e;
		} finally {
			onePostMethod.releaseConnection();
		}

		return new HttpResponseVO(content, statusCode, time);
	}
	
	

	public static HttpResponseVO getPostMethod(String targetURL,Map<String, String> postParams)
			throws HttpException, IOException {
		HttpClient client = new HttpClient();

		logger.info("开始访问:" + targetURL);
		PostMethod onePostMethod = new PostMethod(targetURL);
		// 设置传输字符集，如果需要传输中文这需要设置为中文字符集
		onePostMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gb2312");

		onePostMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		onePostMethod.setRequestHeader("Host", "www.qianwang365.com");

		//int length = 154 + userName.length() + passWord.length();

		//logger.info("length" + length);

		//onePostMethod.setRequestHeader("Content-Length", length + ""); // 176 168  
		//onePostMethod.setRequestHeader("Cookie", CookieStr); //抓包分析后发现,Cookie会从client中自动加入
		onePostMethod.getParams().setContentCharset("UTF-8");
		onePostMethod.addRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		onePostMethod.addRequestHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.3");
		onePostMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		onePostMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		onePostMethod.addRequestHeader("Cache-Control", "max-age=0");
		onePostMethod.addRequestHeader("Connection", "keep-alive");
		onePostMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		for (Entry<String, String> entry : postParams.entrySet()) { 
			onePostMethod.addParameter(entry.getKey(), entry.getValue()); 
		}
		
		
		String content   ="" ;
		
		int statusCode = HttpStatus.SC_NOT_FOUND; 
		long time = System.currentTimeMillis(); 
		try {
			client.executeMethod(onePostMethod);
			logger.info(onePostMethod.getStatusCode());
			if (onePostMethod.getStatusCode() == HttpStatus.SC_OK) {

				client.executeMethod(onePostMethod);
				time = System.currentTimeMillis() - time;
			} else {
				if (logger.isDebugEnabled())
					logger.debug("访问WEB方法,执行过程中出现了错误");
			}
		} catch (HttpException e) {
			logger.warn("访问WEB方法,执行过程中出现了HttpException错误" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.warn("读取xml文件流时, 发生IOException错误" + e.getMessage());
			throw e;
		} finally {
			onePostMethod.releaseConnection();
		}

		return new HttpResponseVO(content, statusCode, time);
	}

	private static final void proxyTest() throws HttpException, IOException,
			InterruptedException {
       String targetUrl="http://m.nanhunnvjia.com/m/minfo/7.html";

        HttpClient client = new HttpClient();





        @SuppressWarnings("unchecked")
		List<String> pros = FileUtils.readLines(new File("conf/prox.txt"));

		for (String item : pros) {
			String[] ipPort = item.split(":");
			if (ipPort.length != 2)
				continue;
			try {
				GetMethod getMethod = getGetMethodProxy(
						"http://user.qzone.qq.com/407809334?ptlang=2052",
						ipPort[0].trim(), Integer.parseInt(ipPort[1].trim()));
				logger.info(RegUtil.getMatchStr(
						getMethod.getResponseBodyAsString(), "已有.*人访问过本站"));

			} catch (Exception e) {
				logger.info("出错" + item);
			}
			logger.info("IP--" + item + "_OK");
			Thread.sleep(2000);
		}
	}

	public static void main(String[] args) throws HttpException, IOException {

		/*	String targetURL ="http://login.cjsc.com.cn/cas/login;jsessionid=7F979A949F685BDF0649707334DAEE23?service=http://oa.cjsc.com.cn/lks/koa/lks_workplace.nsf?open";
			String userName="servicedesk";
			String passWord="servicedesk";
			GetMethod getMethod = getGetMethod(targetURL, userName, passWord);
			 
			logger.info(getMethod.getResponseBodyAsString());
			*/

		//HttpClientParams params = new HttpClientParams();
		///params.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

		//HttpResponseVO http = getGetMethod("http://bbs.zhangle.com/", params); 
		//System.out.println(http);

		/*	while (true) {
				//getPostMethod("http://192.168.201.24:8080/monWeb/consoleManagerAction!findAllAlarmEventsList.action", "admin","password123");
				//getPostMethod("http://192.168.201.24:8080/monWeb", "admin","password123");
			
				 // getPostMethod("http://192.168.201.24:8080/monWeb"); 
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
	}

}
