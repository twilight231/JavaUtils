package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��ȡ���������ݵ����ݿ�
 */
public class BinaryData {
	
	/**
	 * д���������ݵ����ݿ�
	 * @param con
	 */
	public static void writeBinary(Connection con){
        String sql = "INSERT INTO student_address (name, address)"
        	+ " VALUES('john', ?)";
		PreparedStatement psm = null;
		try {
	        // ����һ��Statement�������¼�����ݿ�
			psm = con.prepareStatement(sql);
	        // ����Ҫд��Ķ���������
	        byte[] buffer = "Haidian district Beijing China.".getBytes();
	        // ����SQL�У���ֵ
	        psm.setBytes(1, buffer);
	        // ����
	        psm.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(psm);
	    }
	}
	/**
	 * �����ݿ��ж�����������
	 * @param con
	 */
	public static void readBinary(Connection con){
        String sql = "SELECT * FROM student_address where name = 'john'";
        Statement sm = null;
		try {
	        // ��ѯ���ݿ�
	        Statement stmt = con.createStatement();
	        ResultSet resultSet = stmt.executeQuery(sql);
	        while (resultSet.next()) {
	            // ȡֵ
	            byte[] bytes = resultSet.getBytes("address");
	            System.out.println(new String(bytes));
	        }
	        resultSet.close();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(sm);
	    }
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		
		Connection con = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// д����������
			BinaryData.writeBinary(con);
			// ������������
			BinaryData.readBinary(con);
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// �ر����ݿ�����
			OperateDB.closeConnection(con);
		}
	}
}