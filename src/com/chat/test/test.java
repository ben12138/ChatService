package com.chat.test;

import java.sql.SQLException;

import com.chat.database.DataBaseHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class test {
	public static void main(String[] args) throws SQLException {
		//test1();
		//test2();
		int b = Test2.getA();
		b = 5;
		System.out.println(Test2.getA());
	}
	
	public static void test1() throws SQLException{
		Connection conn = DataBaseHelper.getConnection();
		String sql = "insert into userinf(name,password,nickname,birthday,sex,school,introduction,photo,isOnline)values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
		for(int i = 0;i < 50; i++){
			state.setString(1, "ben"+i);
			state.setString(2, "ben"+i);
			state.setString(3, "êÚî£"+i);
			state.setString(4, "1234");
			state.setInt(5, 0);
			state.setString(6, "êÚî£"+i);
			state.setString(7, "¾ÍÊÇË§");
			state.setString(8, "headImage\\m13814545863@163.com.jpg");
			if(i%2==0){
				state.setInt(9, 0);
			}else{
				state.setInt(9, 1);
			}
			
			state.executeUpdate();
		}
	}
	
	public static void test2() throws SQLException{
		Connection conn = DataBaseHelper.getConnection();
		String sql = "insert into 1234friendlist(myName,friendName,remark)values(?,?,?)";
		PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
		for(int i = 0;i < 33; i++){
			state.setString(1, "1234");
			state.setString(2, "ben"+i);
			state.setString(3, "êÚî£"+i);			
			state.executeUpdate();
		}
	}
	
}
