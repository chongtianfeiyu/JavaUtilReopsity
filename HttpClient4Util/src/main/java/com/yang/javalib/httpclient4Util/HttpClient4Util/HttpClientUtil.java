package com.yang.javalib.httpclient4Util.HttpClient4Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.yang.javalib.httpclient4Util.dto.HttpResponseVO;

/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-21 上午9:23:10 
 */
public class HttpClientUtil {

	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

	public static void main(String[] args) throws ParseException, IOException {
		HttpClientUtil client = new HttpClientUtil();
		client.doGet("http://www.baidu.com/s?wd=HttpClient");

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();// 设置表格参数  
		formparams.add(new BasicNameValuePair("usrname", "admin"));
		formparams.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");//获取实体对象  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		client.doPost("http://localhost:8080/U2_project/login.do", uefEntity);
	}

	/**
	 * @Description: 获取HttpClient示例：
	 * @return
	 * HttpClient
	 * @throws
	 */
	public static HttpClient getClient() {
		HttpClient client = new DefaultHttpClient();// 获取HttpClient对象  
		return client;
	}

	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * @Description: 使用get方法请求：
	 * @param uri
	 * void
	 * @throws
	 */
	public HttpResponseVO doGet(String uri) throws ParseException, IOException {// get方法提交  
		HttpGet getMethod = null;
		getMethod = new HttpGet(uri);// 获取HttpGet对象，使用该对象提交get请求  
		
		return exctueRequest(getMethod);//执行请求，获取HttpResponse对象

	}

	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * @Description: 使用post方法请求,赞呀 这个方法可以得到返回值  ,爽呀. 哈哈. 
	 * @param uri
	 * @param entity
	 * void
	 * @throws
	 */
	public HttpResponseVO doPost(String uri, HttpEntity entity) throws ParseException, IOException {// post方法提交  
		HttpPost postMethod = null;
		
		postMethod = new HttpPost(uri);

		postMethod.setEntity(entity);//设置请求实体，例如表单数据  
		return exctueRequest(postMethod); // 执行请求，获取HttpResponse对象  
	}

	public HttpResponseVO doPost(String uri, HttpEntity entity,String ip,Integer port) throws ParseException, IOException {// post方法提交
		HttpPost postMethod = null;

		postMethod = new HttpPost(uri);
        HttpHost httpHost =new HttpHost(ip,port) ;
		postMethod.setEntity(entity);//设置请求实体，例如表单数据
		return exctueRequest(postMethod,httpHost); // 执行请求，获取HttpResponse对象
	}

	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * @Description: 执行请求，获取HttpResponse对象
	 * @param request
	 * @return
	 * HttpResponse
	 * @throws
	 */
	private HttpResponseVO exctueRequest(HttpRequestBase request) throws ParseException, IOException {
            return exctueRequest(request,null);
    }

	private HttpResponseVO exctueRequest(HttpRequestBase request,HttpHost proxy) throws ParseException, IOException {
		HttpResponse response = null;
		long time = System.currentTimeMillis(); 
		String responseBody = "";
		int statuscode;
		try {
			logger.debug("excute request:" + request.getURI());//获取请求uri  
			logger.debug("-----------------------------------");

            HttpClient client = this.getClient();
            if(proxy!=null){
                client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
/*
                client.getHostConfiguration()
                        .setProxy(proxy);
*/
                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  5000);//连接时间20s
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  6000);//数据传输时间60s
            }
			response = client.execute(request);//执行请求，获取HttpResponse对象
			time = System.currentTimeMillis() - time;
			//showResponse(response);//打印Response信息  
			 statuscode = response.getStatusLine().getStatusCode();//根据相应码处理URI重定向  
			if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

				Header redirectLocation = response.getFirstHeader("Location");//从响应头中获取重定向的uri  
				String newuri = redirectLocation.getValue();
				if ((newuri != null) || (!newuri.equals(""))) {
					logger.debug("redirect to " + newuri);
					request.setURI(new URI(newuri));//重新设置uri  
					response = this.getClient().execute(request);
					//showResponse(response);//打印response信息  
				} else {
					logger.debug("Invalid redirect");
				}
			} 
			Header[]  headers =response.getHeaders("Content-Type");
			ContentType contentType =ContentType.getOrDefault(response.getEntity());
			
			 if(contentType.getCharset()!=null){ 

					logger.info("网页内容编码方式:"+contentType.getCharset());
					responseBody= EntityUtils.toString(response.getEntity(),contentType.getCharset());
			 }else{
				//如果没有得到的话就用老办法 了.
				 // <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

					String charSet = null;
					byte[] contentBytes = IOUtils.toByteArray(response.getEntity().getContent());
				    
					responseBody = new String(contentBytes, "utf-8"); 
				 
					if (responseBody.toLowerCase().contains("charset=utf-8"))
						charSet="utf-8";
					else if (responseBody.toLowerCase().contains("charset=gbk"))
						charSet="gbk"; 
					else if (responseBody.toLowerCase().contains("charset=gb2312"))
						charSet="gb2312";
					else if (responseBody.toLowerCase().contains("charset=iso-8859-1")){
						charSet="iso-8859-1"; 
					}

					logger.info("网页内容编码方式:"+charSet);
					 
					if (charSet != null)
						responseBody = new String(contentBytes, charSet);
			 }
			 
			 
		} catch (Exception e) {
			logger.error("", e);
			
		} finally {
			releaseConnection(request);//释放连接,无论成功与否都需要释放连接  
		}
		 
		return  new HttpResponseVO(responseBody,response.getStatusLine().getStatusCode(),time);

	}

	/**
	 * @Description: 打印响应信息
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 * void
	 * @throws
	 */
	private void showResponse(HttpResponse response) throws ParseException, IOException {
		logger.debug("requset result:");
		logger.debug(response.getStatusLine().toString());// 响应状态  
		logger.debug("-----------------------------------");

		Header[] heard = response.getAllHeaders();// 响应头  
		logger.debug("response heard:");
		for (int i = 0; i < heard.length; i++) {
			logger.debug(heard[i]);
		}
		logger.debug("-----------------------------------");
		HttpEntity entity = response.getEntity();// 响应实体/内容  
		logger.debug("response content length:" + entity.getContentLength());
		logger.debug("response content:");
		logger.debug(EntityUtils.toString(entity));

	}

	/**
	 * @Description:  释放连接
	 * @param request
	 * void
	 * @throws
	 */
	private static void releaseConnection(HttpRequestBase request) {
		if (request != null) {
			request.releaseConnection();
		}
	}

	/**
	 * @Description: 下载一个文件到本地（本示范中为一个验证码图片）
	 * @throws Exception
	 * void
	 * @throws
	 */
	public static void downloadFile(String imgUrl,String imgSavePath) throws Exception {
		//有格式,这个是必需的. TODO 
		HttpClient httpclient =getClient() ; //new DefaultHttpClient();
		 // 设置超时时间(毫秒)
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		   httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		         60000);
		
		  
		   
		HttpGet httpget = new HttpGet(imgUrl);
		
	    // 　为目标文件创建目录
        int lastSptAt = imgSavePath.replace("\\", "/").lastIndexOf("/");
        File dir = new File(imgSavePath.substring(0, lastSptAt));
        if(!dir.exists()){
        	dir.mkdirs();
        	logger.info("新建不存在的文件夹路径:"+dir.getAbsolutePath());
        }
		
		File file = new File(imgSavePath);
		if (file == null ||!file.exists()) {
			file.createNewFile();  
			//file.delete();
		}

		HttpResponse response = httpclient.execute(httpget);
		
		 if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	         //请求成功
			 logger.info("成功下载图片["+imgUrl+"],把图片存储于:"+imgSavePath);
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				try {
					FileOutputStream fout = new FileOutputStream(file);
					int length = -1;
					byte[] tmp = new byte[2048];
					while ((length = in.read(tmp)) != -1) {
						fout.write(tmp,0,length);
					}
					fout.flush();
					fout.close();
				} finally {
					// 在用InputStream处理HttpEntity时，切记要关闭低层流。
					in.close();
				} 

	            if (entity != null) {  
	                entity.consumeContent();  
	            }  
		 }else {
	            System.out.println("[" + imgUrl + "] 未找到.");
	            return ;
	         }
		
		releaseConnection(httpget); 
	}
}
