package com.koreait.semipro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * �̱���(Singleton) -> �ڹ� ������ ����
 * ���ø����̼��� ���۵� �� � Ŭ������ ���� �ѹ��� �޸𸮿� �Ҵ��ϰ� �� �޸𸮿� �ν��Ͻ��� ����� ����ϴ� ������ ����
 * (�޸𸮸� �ִ��� �Ƴ�)
 * ����������
 * ����Ʈ��� ������ �� Ư�� �κп��� ���� �߻��ϴ� �������� �ذ��ϱ� ���� ���� ������ ������ ����� �����Ͽ� ���α׷��� ����� ���
 */

public class DBConn {
	private static Connection dbConn;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		if (dbConn == null) { // null - Connection ��ü�� dbConn�ȿ� ���� ��������� �ʾҴ�
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
