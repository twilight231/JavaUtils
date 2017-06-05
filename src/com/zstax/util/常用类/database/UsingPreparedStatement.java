package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ʹ��PreparedStatement���ݱ���
 */
public class UsingPreparedStatement {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		// SQL���ж���ʺţ���ʾ��Щ�ط���ֵ����ȷ��
		String sql = "INSERT INTO student_basic (name, age, score) VALUES (?,?,?)";
		Connection con = null;
		PreparedStatement psm = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			psm = con.prepareStatement(sql);
			
			// ��SQL����еģ���ֵ
			psm.setString(1, "wangwu");
			psm.setInt(2, 17);
			psm.setDouble(3, 98);
			psm.executeUpdate();
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			OperateDB.closeStatement(psm);
			// �ر����ݿ�����
			OperateDB.closeConnection(con);
		}
	}
}
