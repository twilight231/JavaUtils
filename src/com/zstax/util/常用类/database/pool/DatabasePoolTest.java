package com.zstax.util.常用类.database.pool;

import java.sql.Connection;

public class DatabasePoolTest {

	public static JDBCInfo getJDBCInfo(){
		JDBCInfo jdbc = new JDBCInfo();
		jdbc.setDriver("com.mysql.jdbc.Driver");
		jdbc.setName("MySQL");
		jdbc.setUrl("jdbc:mysql://127.0.0.1:3306/studentdb");
		jdbc.setUser("test");
		jdbc.setPassword("test");
		jdbc.setMaxconn(10);
		return jdbc;
	}
	
	public static void main(String[] args) {
		JDBCInfo jdbc = getJDBCInfo();
		String sql = "SELECT * FROM student_basic";
		DataConnectMgr mgr =  DataConnectMgr.getInstance(jdbc);
		//�����ݿ����ӳ��л�ȡ����
		Connection con = mgr.getConnection(jdbc.getName());
        try {
        	System.out.println("����ʹ�øոջ�õ����ݿ�����");
            java.sql.Statement sm = con.createStatement();
            sm.executeQuery(sql);
            //do something
            sm.close();
        } catch (java.sql.SQLException e) {
        	System.err.println("�������ݿ����");
        } finally {
        	//�ͷ����ӵ����ݿ����ӳ�
        	mgr.freeConnection(jdbc.getName(), con);
        }
        mgr.release();
	}
}
