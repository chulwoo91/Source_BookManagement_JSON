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
		

		String user=request.getParameter("user");
		String pass=request.getParameter("pass");
		String callback=request.getParameter("callback");
		
		BookService service=new BookService();
		boolean result=service.userEnroll(user, pass);
		
		
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out=response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

}
