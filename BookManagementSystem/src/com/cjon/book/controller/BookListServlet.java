package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class BookListServlet
 */
@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 입력
		String keyword=request.getParameter("keyword"); //책에 대한 keyword를 받는 부분
		String callback=request.getParameter("callback"); //JSONP 처리를 위해서 사용
		
		//2. 로직처리(DB처리 포함)
		// Servlet은 입력을 받고 출력에 대한 지정을 담당하고 로직처리를 하지 않는다.
		// 로직처리하는 객체를 일반적으로 Service 객체라고 부른다. 이 객체를 사용해 결과를 받아오는 구조를 만든다.
		// 로직처리를 하기 위해서 Service객체를 생성한다.
		BookService service=new BookService();
		//결과로 가져올 값은 DB처리를 한 뒤 나온 책에 대한 JSON data이다.
		String result=service.getList(keyword);
		
		//3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out=response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
