package com.yang.javalib.module.spring.mvc.control.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yang.javalib.module.spring.mvc.control.BaseControl;

@Controller  
@RequestMapping("testBase2")
public class TestControl2 extends BaseControl {
	 
	/**
	 * 127.0.0.01:8080/springmvc/testBase?method=test1
	 * @return
	 */
	@RequestMapping(params = "method=test1")
	public String test1(){  
		String tt  =this.request.getParameter("name"); 
		System.out.println(tt);
		return "success";
	}


    /**
     *  测试 与类定义不一致时, 怎么弄?
     *  还是组合的.
     *  127.0.0.01:8080/springmvc/testBase?method=test1
     *  127.0.0.01:8080/springmvc/testBase/paySucess
     * @return
     */
    @RequestMapping(value="/paySucess")
	public String test2(){  
		String tt  =this.request.getParameter("name"); 
		System.out.println(tt);
		return "success";
	}
}
