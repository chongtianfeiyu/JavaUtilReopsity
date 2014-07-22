package com.yang.javalib.httpclient4Util.HttpClient4Util;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.UriUtils;

import com.yang.javalib.httpclient4Util.dto.HttpResponseVO;

public class PageParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    private String localFile="d:/temp.html";  
      
    //使用httpChient来获取一个本地网页  
    public void testFetch01() {  
        try {  
            // HttpClient主要负责执行请求，可以把它看做是一个浏览器  
            HttpClient httpclient = new DefaultHttpClient();  
            // 利用HTTP GET向服务器发起请求  
            HttpGet get = new HttpGet("http://www.ibm.com/developerworks/cn/java/j-javaroundtable/index.html");  
            // 获得服务器响应的的所有信息  
            HttpResponse response = httpclient.execute(get);  
            // 获得服务器响应回来的消息体（不包括HTTP HEAD）  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {
                // 获得响应的字符集编码信息  
                // 即获取HTTP HEAD的：Content-Type:text/html;charset=UTF-8中的字符集信息  
                String charset = EntityUtils.getContentCharSet(entity);  
                InputStream is = entity.getContent();  
                IOUtils.copy(is, new FileOutputStream(localFile));  
            }  
            // 释放所有的链接资源，一般在所有的请求处理完成之后，才需要释放  
            httpclient.getConnectionManager().shutdown();  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    //使用HttpParser来提取网页中的图片链接地址  
    public void testParse01() throws Exception{  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
          
        //创建一个html解释器  
        Parser parser=new Parser();  
        parser.setInputHTML(html);  
          
        //提取所有<img>标签的地址  
        NodeList imageTags=parser.parse(new NodeClassFilter(ImageTag.class  ));  
        for(int i=0;i<imageTags.size();i++){  
            ImageTag it=(ImageTag) imageTags.elementAt(i);
            String imageUrl=it.getImageURL();  
            System.out.println(imageUrl);  
        }
    }
      
    //提取具有某种特征的标签  
    public void testParse02() throws Exception{  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
          
        //创建一个html解释器  
        Parser parser=new Parser();  
        parser.setInputHTML(html);  
          
        //提取name="title"的meta标签  
        NodeList metaTags=parser.parse(  
                new NodeFilter(){  
                    @Override  
                    public boolean accept(Node node) {  
                        if(node instanceof MetaTag){  
                            MetaTag mt=(MetaTag) node;  
                            if(mt.getMetaTagName()!=null && mt.getMetaTagName().equals("title")){  
                                return true;  
                            }  
                        }  
                        return false;  
                    }  
                }  
        );  
          
        for(int i=0; i<metaTags.size();i++){  
            MetaTag mt=(MetaTag) metaTags.elementAt(i);  
            System.out.println("文章的标题是："+mt.getMetaContent());  
        }  
          
    }  
      
    //提取文章的简介和关键字  
    public void testParse03() throws Exception{  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
          
        MetaTag metaTag=ParseUtils.parseTag(html, MetaTag.class, "name", "Abstract");  
        System.out.println("文章的简介是："+metaTag.getMetaContent());  
        metaTag=ParseUtils.parseTag(html, MetaTag.class, "name", "Keywords");  
        System.out.println("文章的关键字是："+metaTag.getMetaContent());  
    }  
      
    //提取文章的作者信息  
    public void testParse04() throws Exception{  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
  
        List<Div> authors=ParseUtils.parseTags(html, Div.class, "class", "author");
        for (Div div : authors) {  
            System.out.println(ParseUtils.parseTag(div.getStringText(), LinkTag.class).getStringText());  
        }
    }
      
    //提取文章的内容  
    public void testParse05() throws Exception{  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
          
        String content=StringUtils.substringBetween(html, "<!-- MAIN_COLUMN_BEGIN -->", "<!-- CMA");  
        System.out.println(content);  
    }
  
    //把文章内容中的图片下载到本地  
    @Test  
    public void testParse06() throws Exception{  
        HttpClient httpClient=new DefaultHttpClient();  
        //把文件的内容读出来  
        String html= IOUtils.toString(new FileInputStream(localFile),"UTF-8");    
        String content=StringUtils.substringBetween(html, "<!-- MAIN_COLUMN_BEGIN -->", "<!-- CMA");  
          
        //提取内容中的图片信息  
        Parser parser=new Parser();  
        parser.setInputHTML(content);  
          
        //提取所有<img>标签的地址  
        NodeList imageTags=parser.parse(new NodeClassFilter(ImageTag.class  ));  
        for(int i=0;i<imageTags.size();i++){  
            ImageTag it=(ImageTag) imageTags.elementAt(i);  
            String imageUrl=it.getImageURL();  
            String imageName=FilenameUtils.getName(imageUrl);  
            System.out.println(imageUrl);  
            String url="http://www.ibm.com/developerworks/cn/java/j-javaroundtable/"+imageUrl;  
            HttpClientUtil.downloadFile(url, "temp/"+imageName);
           // byte[] image=HttpUtils.getImage(httpClient, url);  
            //存储到本地的某个磁盘  
           //IOUtils.write(image, new FileOutputStream("d:/temp/"+imageName));  
        }  
        httpClient.getConnectionManager().shutdown();  
    }  
	
}
