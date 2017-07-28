package com.chat.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * 数据库连接，单例模式
 * 
 * @author 贲睿
 * 
 */
public class DataBaseHelper {
	private static Connection conn = null;
	private static DataBaseHelper db = null;

	private DataBaseHelper() {
		// TODO Auto-generated constructor stub
	}

	private static DataBaseHelper getInstance() {
		if (db == null) {
			db = new DataBaseHelper();
			db.setConnection();
		}
		return db;
	}

	public static Connection getConnection() {
		return getInstance().conn;
	}

	private static void setConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/chat";
			String user = "root";
			String password = "123456";
			conn = (Connection) DriverManager.getConnection(url, user,
					password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
