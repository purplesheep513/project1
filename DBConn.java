package com.koreait.semipro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 싱글톤(Singleton) -> 자바 디자인 패턴
 * 애플리케이션이 시작될 때 어떤 클래스가 최초 한번만 메모리에 할당하고 그 메모리에 인스턴스를 만들어 사용하는 디자인 패턴
 * (메모리를 최대한 아낌)
 * 디자인패턴
 * 소프트웨어를 설계할 때 특정 부분에서 자주 발생하는 문제들을 해결하기 위해 재사용 가능한 최적의 방법을 제시하여 프로그램을 만드는 방법
 */

public class DBConn {
	private static Connection dbConn;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		if (dbConn == null) { // null - Connection 객체인 dbConn안에 뭔가 만들어지지 않았다
			String url = "jdbc:mariadb://123.142.55.115:3306/db";
			String user = "root";
			String pw = "1234";
			Class.forName("org.mariadb.jdbc.Driver");
			dbConn = DriverManager.getConnection(url, user, pw);
		}
		return dbConn;
	}
	
	public static void close() throws SQLException {
		if(dbConn != null) {
			if(!dbConn.isClosed()) {
				dbConn.close();
			}
		}
	}
	
	
}
