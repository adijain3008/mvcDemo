package com.mindtree.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	public static final String CLASSNAME = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://18.219.184.115:3306/spring_user_db?allowPublicKeyRetrieval=true&useSSL=FALSE";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "root";

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
