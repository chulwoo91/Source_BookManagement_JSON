package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class CheckloginServlet
 */
@WebServlet("/checkLOG")
public class CheckloginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckloginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("============체크당==============");
		  
		  String callback = request.getParameter("callback");
		  String result = null;
		  
		  HttpSession session=request.getSession(true);
		  
		  JSONObject id = new JSONObject();
		  
		  id.put("id", session.getAttribute("id"));
		  
		  System.out.println("session_id: "+id);
		  
		  if( id == null ) {
		   System.out.println("세션이 존재하지 않아");
		  }
		  else{
		   result=id.toJSONString();
		   System.out.println("세션이 존재해");
		   System.out.println("result : "+result);
		  }

		  response.setContentType("text/plain; charset=utf8");
		  PrintWriter out = response.getWriter();
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
