package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {
	
	//list를 가져오는 일을 하는 method
	public String getList(String keyword){
		//일반적인 로직처리가 나온다.
		
		//추가적으로 DB처리가 나올 수 있다.
		//DB처리는 Service에서 처리하는 것이 아니라 다른 객체를 이용해서 한다.
		BookDAO dao=new BookDAO();
		String result=dao.select(keyword);
		return result;
	}

	public boolean updateBook(String isbn, String price, String title, String author) {
	
		BookDAO dao=new BookDAO();
		boolean result=dao.update(isbn, price, title, author);
		return result;
	}

	public boolean bookDelete(String isbn){
		BookDAO dao=new BookDAO();
		boolean result=dao.delete(isbn);
		return result;
	}
	
	public boolean bookInsert(String isbn, String price, String title, String author){
		BookDAO dao=new BookDAO();
		boolean result=dao.insert(isbn, price, title, author);
		return result;
	}

	public String bookDetail(String isbn, String page, String date, String translator, String supplement, String publisher) {
		BookDAO dao=new BookDAO();
		String result=dao.detail(isbn, page, date, translator, supplement, publisher);
		return result;
	}

	public boolean userEnroll(String user, String pass) {
		BookDAO dao=new BookDAO();
		boolean result=dao.enroll(user, pass);
		return result;
	}

	public boolean logIn(String user, String pass) {
		BookDAO dao=new BookDAO();
		boolean result=dao.login(user, pass);
		return result;
	}
}
