package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;


@WebServlet("/enroll")
public class UserEnrollServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String age=request.getParameter("age");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String user=request.getParameter("user");
		String pass=request.getParameter("pass");
		String callback=request.getParameter("callback");
		
		BookService service=new BookService();
		boolean result=service.userEnroll(name, sex, age, email, address, user, pass);
		
		
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out=response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

}
