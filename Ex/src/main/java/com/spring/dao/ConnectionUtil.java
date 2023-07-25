package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

	public static Connection getConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "library";
		String pw = "1234";
		
		Connection conn = null;		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);			
		} catch (ClassNotFoundException e) {
			System.err.println("라이브러리를 확인하세요.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
		return conn;
	}
	
	public void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			System.err.println("conn.rollback() 실패");
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			System.err.println("conn.close() 실패");
			e.printStackTrace();
		}
	}

	public static void close(Connection conn, Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) stmt.close();
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			System.err.println("stmt.close() 실패");
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
			if(stmt!=null && !stmt.isClosed()) stmt.close();
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			System.err.println("rs.close() 실패");
			e.printStackTrace();
		}
	}

}
