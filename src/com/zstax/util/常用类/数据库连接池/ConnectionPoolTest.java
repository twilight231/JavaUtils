package com.zstax.util.常用类.数据库连接池;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionPoolTest {

	public static void main(String args[]) {
		
		//��ʼ�����ݿ����ӳ���
		ConnectionPool connPool = new ConnectionPool("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test", "root", "admin");

		try {
			//�������ݿ����ӳ�
			connPool.createPool();
			
			//���ӳص���Ӧ��Ϣ
			System.out.println("*************************************************");
			System.out.println("���ӳ��Զ����ӵĴ�С��" + connPool.getIncrementalConnections());
			System.out.println("���ӳس�ʼ��С��" + connPool.getInitialConnections());
			System.out.println("���ӳ��������������" + connPool.getMaxConnections());
			System.out.println("*************************************************");
			
			//ͨ�����ӳػ��һ�����ݿ�����
			Connection conn = connPool.getConnection();
			
			String sql = "select * from user";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				
				System.out.println("username is :" + rs.getString("username"));
				System.out.println("password is :" + rs.getString("password"));
			}
			rs.close();
			pstmt.close();
			connPool.returnConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
