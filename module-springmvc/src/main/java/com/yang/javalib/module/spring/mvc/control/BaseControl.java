package com.yang.javalib.module.spring.mvc.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基础control
 * @author Administrator
 *
 */
public abstract class BaseControl {

	private static final Logger log =Logger.getLogger(BaseControl.class);
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
      
    /**
     * 在方法调用前,都会调用 些方法,
     * @param request
     * @param response
     */
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
    	log.info("调用方法:setReqAndRes(),初始化request,response,session");
    	this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }
    
    /**
     * 便捷方法通过此处 从request中得到参数
     * @param paramKey
     * @return
     */
    public String  getParameterByRequest(String paramKey){ 
		String tt  =this.request.getParameter(paramKey); 
		if(StringUtils.isEmpty(tt)) 
			throw new IllegalArgumentException("没有此参数:"+paramKey); 
		return  tt ; 
    }
    
    /**
     * 便捷方法通过此处 从request中得到Integer类型参数
     * @param paramKey
     * @return
     */
    public Integer getIntegerByRequest(String paramKey){
    	String _temp  =getParameterByRequest(paramKey);
    	if(NumberUtils.isNumber(_temp))
			throw new IllegalArgumentException("参数格式错误,应该是integer类型:"+paramKey +"   "+_temp);  
    	
    	return Integer.parseInt(_temp);
    } 
    

    /**
     * 便捷方法通过此处 从request中得到Float类型参数
     * @param paramKey
     * @return
     */
    public Float getFloatByRequest(String paramKey){
    	String _temp  =getParameterByRequest(paramKey);
    	if(NumberUtils.isNumber(_temp))
			throw new IllegalArgumentException("参数格式错误,应该是integer类型:"+paramKey +"   "+_temp);  
    
    	return 	Float.parseFloat(_temp);
    }
    //得到数组类型,多个值的.
    

    /**
     * 在父类中声明 , 在子类中看能不能访问到. 尤其是多个子类,并且子类访问路径定义不一致的情况 
     * 对 子类TestControl:访问     http://127.0.0.1:8080/springmvc/testBase/paySucess123
     * 对子类 TestControl      testBase2
     * @return
     */
    @RequestMapping(value="/paySucess123")
	public String test3(){  
		String tt  =this.request.getParameter("name"); 
		System.out.println(tt);
		return "success";
	}
	
    
     
}
