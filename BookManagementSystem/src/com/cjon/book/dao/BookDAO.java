package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	public boolean update(String isbn, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt=null;

		boolean result=false;
		
		try {
			String sql="update book set bprice=? where bisbn=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(price));
			pstmt.setString(2, isbn);
			
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
