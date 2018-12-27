package com.mindtree.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	public static final String CLASSNAME = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://104.211.163.253:3306/spring_user_db";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "Welcome123";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(CLASSNAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not loaded!!");
		} catch (SQLException e) {
			System.out.println("Connection not established!!");
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Connection not established!!");
			}
		}
	}
}
