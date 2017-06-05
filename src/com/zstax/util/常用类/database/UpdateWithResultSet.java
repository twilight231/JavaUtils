package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ������ʾʹ��ResultSet�������ݿ������. �����޸ġ������ɾ��
 */
public class UpdateWithResultSet {
	/**
	 * ʹ��ResultSet���Ը������ݿ�����ݣ�ǰ������֮������Statementû�б��رա�
	 * @param con
	 */
	public static void update(Connection con){
		String sql = "SELECT * FROM student_basic";
		Statement sm = null;
		ResultSet rs = null;
		try {
			// ����Statement
			// ResultSet.TYPE_SCROLL_SENSITIVE��ʾ��ResultSet�п���������������ǰ����ƶ��α꣬
			// ͬʱResultSet��ֵ�����ı��ʱ�������Եõ��ı������µ�ֵ��
			// ResultSet.CONCUR_UPDATABLE��ʾ��ResultSet�е����ݼ�¼���������޸ģ�Ȼ����»����ݿ�
			sm = con.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = sm.executeQuery(sql);
			
			/***��ResultSet���µ�һ������***/
			// ָ���Ƶ���һ������
			rs.absolute(1);
			// �޸�����
			rs.updateDouble("score", 70);// �޸�score��
			rs.updateString(5, "updated by ResultSet!");// �޸ĵ�5��
			// �����ȡ���Ե�ǰ��¼���޸ģ�����ȡ��
			rs.cancelRowUpdates();
			System.out.println("׼����ResultSet�޸�һ����¼��");
			// ��������޸ģ���ʹ��updateRow�����ύ�޸�
			rs.absolute(1);// �����ٵ���һ��absolute����Ϊ��
			rs.updateDouble("score", 70);
			rs.updateString(5, "updated by ResultSet!");
			// ���޸��ύ������Դ
			rs.updateRow();
			OperateDB.showResultSet(rs);
			
			/***��ResultSet����һ������***/
			System.out.println("׼����ResultSet����һ����¼��");
			// ָ���ƶ��������
			rs.moveToInsertRow();
			// Ϊ����������ֵ
			rs.updateString("name", "mike");
			rs.updateInt("age", 18);
			rs.updateDouble("score", 88);
			// �������ύ������Դ
			rs.insertRow();
			OperateDB.showResultSet(rs);
			
			/***��ResultSetɾ��һ������***/
			rs.last();
			System.out.println("׼����ResultSetɾ�����һ����¼��" );
			rs.deleteRow();
			OperateDB.showResultSet(rs);
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ر�Statement
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
			// �������ݿ�
			UpdateWithResultSet.update(con);
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