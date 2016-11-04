package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		//DB처리를 여기서 해준다.
		//일반적으로 DB처리를 쉽게 하기 위해 
		//Tomcat의 경우 DBCP라는 것을 제공한다. 
		//추가적으로 간단한 library를 이용해 DB처리를 한다.
		//1. Driver loading(이미 설정이 되어있어서 따로 해줄 필요가 없다.)
		//2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result=null;
		try {
			String sql="select bisbn, bimgurl, btitle, bauthor, bprice from book where btitle like ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,  "%" + keyword + "%");
			rs=pstmt.executeQuery();
			JSONArray arr=new JSONArray();
			while(rs.next()){
				JSONObject obj=new JSONObject();
				obj.put("isbn",  rs.getString("bisbn"));
				obj.put("img",  rs.getString("bimgurl"));
				obj.put("title",  rs.getString("btitle"));
				obj.put("author",  rs.getString("bauthor"));
				obj.put("price",  rs.getString("bprice"));
				arr.add(obj);
			}
			result=arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean update(String isbn, String price, String title, String author) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);
			
			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean delete(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		
		boolean result=false;
		
		try {
			String sql="delete from book where bisbn=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, isbn);

			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean insert(String isbn, String price, String title, String author) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="insert into book (bisbn, btitle, bauthor, bprice) value (?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setInt(4, Integer.parseInt(price));
			
			
			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public String detail(String isbn, String page, String date, String translator, String supplement, String publisher) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result=null;
		
		try {
			String sql="select bisbn, bpage, bdate, btranslator, bsupplement, bpublisher from book where bisbn=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs=pstmt.executeQuery();
			//결과값은 영향을 받은 레코드의 수
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("date", rs.getString("bdate"));
				obj.put("page", rs.getString("bpage"));
				obj.put("translator", rs.getString("btranslator"));
				obj.put("supplement", rs.getString("bsupplement"));
				obj.put("publisher", rs.getString("bpublisher"));
				arr.add(obj);
			}
			
			result = arr.toJSONString();  
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean enroll(String user, String pass) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="insert into member (id, pw) value (?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			
			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean login(String user, String pass) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		boolean result=false;
		
		try {
			String sql="select id, pw from member where id=? and pw=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			
			rs=pstmt.executeQuery();
			//결과값은 영향을 받은 레코드의 수

			while(rs.next()) {
				result=true;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean reviewInsert(String isbn, String id, String title, String review) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="insert into book_comment (bisbn, cid, ctitle, ctext) value (?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, id);
			pstmt.setString(3, title);
			pstmt.setString(4, review);
			
			
			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public String reviewbook(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result=null;
		try {	
			String sql="select b.bisbn, c.cid, b.btitle, c.ctitle, c.ctext from book b join book_comment c on b.bisbn = c.bisbn where b.btitle like ? ";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs=pstmt.executeQuery();
			JSONArray arr=new JSONArray();
			while(rs.next()){
				JSONObject obj=new JSONObject();
				obj.put("isbn",  rs.getString("b.bisbn"));
				obj.put("title",  rs.getString("b.btitle"));
				obj.put("user",  rs.getString("c.cid"));
				obj.put("review",  rs.getString("ctext"));
				arr.add(obj);
			}
			result=arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public String reviewKW(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result=null;
		try {	
			String sql="select bisbn, cid, ctitle, ctext from book_comment where ctext like ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,  "%" + keyword + "%");
			rs=pstmt.executeQuery();
			JSONArray arr=new JSONArray();
			while(rs.next()){
				JSONObject obj=new JSONObject();
				obj.put("isbn",  rs.getString("bisbn"));
				obj.put("user",  rs.getString("cid"));
				obj.put("title",  rs.getString("ctitle"));
				obj.put("review",  rs.getString("ctext"));
				arr.add(obj);
			}
			result=arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public String deleteSearch(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result=null;
		try {	
			String sql="select bisbn, cid, ctitle, ctext from book_comment where cid like ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs=pstmt.executeQuery();
			JSONArray arr=new JSONArray();
			while(rs.next()){
				JSONObject obj=new JSONObject();
				obj.put("isbn",  rs.getString("bisbn"));
				obj.put("user",  rs.getString("cid"));
				obj.put("title",  rs.getString("ctitle"));
				obj.put("review",  rs.getString("ctext"));
				arr.add(obj);
			}
			result=arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean deletereview(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		
		boolean result=false;
		
		try {
			String sql="delete from book_comment where bisbn=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, isbn);

			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean loanlogin(String user, String pass) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="insert into book (rent) value (?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user);
			
			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

	public boolean loanlogout(String user, String pass) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;
		
		boolean result=false;
		
		try {
			String sql="delete from book rent where rent=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user);

			int count=pstmt.executeUpdate();
			//결과값은 영향을 받은 레코드의 수
			
			if(count==1){
				result=true;
				//정상처리이기 때문에 commit
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);			
		}
		return result;
	}

}
