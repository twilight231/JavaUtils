package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��ȡ���ݿ�ͱ��Ԫ����
 * ʹ��DatabaseMetaData������ݿ��Ԫ����
 * ʹ��ResultSetMetaData��ñ��Ԫ����
 */
public class GetMetadata {
	/**
	 * ������ݿ��Ԫ����
	 * @param con	�����ݿ������
	 */
	public static void showDatabaseMetadata(Connection con){
		try {
			// �õ����ݿ��Ԫ����
			DatabaseMetaData md = con.getMetaData();
			System.out.println("���ݿ�" + md.getURL() + "��Ԫ�������£�");
			
			// ��ʾԪ������Ϣ
			System.out.println("����: " + md.getDriverName());
			System.out.println("�����汾��: " + md.getDriverVersion());
			System.out.println("��½�û���: " + md.getUserName());
			System.out.println("���ݿ��Ʒ��: " + md.getDatabaseProductName());
			System.out.println("���ݿ��Ʒ�汾��: " + md.getDatabaseProductVersion());
			System.out.println("֧�ֵ�SQL�ؼ���: ");
			System.out.println(md.getSQLKeywords());
			System.out.println("�������ֵĺ���: ");
			System.out.println(md.getNumericFunctions());
			System.out.println("�����ַ����ĺ���: ");
			System.out.println(md.getStringFunctions());
			System.out.println("ϵͳ����: ");
			System.out.println(md.getSystemFunctions());
			System.out.println("ʱ������ں���: ");
			System.out.println(md.getTimeDateFunctions());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ���ݱ��Ԫ���ݣ���Ҫ���е���Ϣ
	 * @param con	�����ݿ������
	 * @param tableName	 ���ݱ���
	 */
	public static void showTableMetadata(Connection con, String tableName){
		String sql = "SELECT * FROM " + tableName;
		Statement sm = null;
		try {
			// ���Ȼ�ñ����������
			sm = con.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			
			// �õ��������Ԫ����
			ResultSetMetaData md = rs.getMetaData();
			
			System.out.println("���ݱ�" + tableName + "��Ԫ�������£�");
			// �������
			int columnCount = md.getColumnCount();
			System.out.println("column count: " + columnCount);
			System.out.println();
			StringBuffer sb = new StringBuffer("");
			sb.append("sn\tname\t\t").append("type\t\t");
			sb.append("scale\t").append("isNullable");
			System.out.println(sb);
			sb.delete(0, sb.length());
			// ����е�������Ϣ
			for (int i=1; i<=columnCount; i++){
				sb.append(i).append("\t");
				sb.append(md.getColumnName(i)).append("\t\t");
				sb.append(md.getColumnTypeName(i)).append("\t\t");
				sb.append(md.getScale(i)).append("\t");
				sb.append(md.isNullable(i));
				System.out.println(sb);
				sb.delete(0, sb.length());
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ر�Statement
			if (sm != null){
				try {
					sm.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String tableName = "student_basic";
		String userName = "test";
		String password = "test";

		Connection con = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// ��ʾ���ݿ��Ԫ��Ϣ
			GetMetadata.showDatabaseMetadata(con);
			System.out.println();
			// ��ʾ���ݱ��Ԫ��Ϣ
			GetMetadata.showTableMetadata(con, tableName);
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// �ر����ݿ�����
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}