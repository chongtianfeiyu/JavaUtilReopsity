package com.tan.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tan.model.Book;
import com.tan.service.BookService;

@Controller
@RequestMapping("/book.do")
public class BookController {

	private BookService bookService;
	/**
	 * 127.0.0.01:8080/springmvc/test/book.do?method=add
	 * 127.0.0.01:8080/springmvc//book.do?method=add
	 * @param book
	 * @return
	 */
	@RequestMapping(params = "method=add")
	public String add(Book book){
		System.out.println("bookname:"+book.getName());
		System.out.println("author:"+book.getAuthor());
		bookService.add(book);
		return "success";
	}
	
	/**
	 * ���Դ�request�еõ�����
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=request")
	public String getParaByRequest(HttpServletRequest request, HttpServletResponse response){
		String _name =request.getParameter("name");
		System.out.print(_name);
		
		String _author =request.getParameter("author");
		System.out.print(_author);
		
		return "success";
	}
	@RequestMapping(params = "method=update")
	public String update(Book book) {
		bookService.update(book);
		return "success";
	}
	public BookService getBookService() {
		return bookService;
	}
	@Resource
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
}
