package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * �������ݿ⣬������ɾ�Ĳ����ݿ�ļ�¼
 */
public class OperateDB {

	/**
	 * ��ѯ���ݿ�
	 * @param sm	�����ݿ����ӵ�Statement
	 * @param sql	��ѯSQL���
	 * @return ����һ��ResultSet�����
	 */
	public static ResultSet queryDB(Statement sm, String sql){
		ResultSet rs = null;
		try {
			// ���Ȼ�ñ����������
			rs = sm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}
	/**
	 * �޸����ݿ�
	 * @param con	���ݿ������
	 * @param sql	�޸�SQL���
	 * @return	�����޸�Ӱ���������Ϊ0��ʾһ�����ݶ�û�б��޸�
	 * 			ΪStatement.EXECUTE_FAILED��ʾִ��ʧ�ܡ�
	 */
	public static int updateDB(Connection con, String sql){
		Statement sm = null;
		int affectRows = 0;
		try {
			// ���Ȼ�ñ����������
			sm = con.createStatement();
			affectRows = sm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			// ��������쳣�����ʾִ��ʧ�ܡ�
			affectRows = Statement.EXECUTE_FAILED;
		} finally {
			// �ر�Statement
			closeStatement(sm);
		}
		return affectRows;
	}
	/**
	 * ��ʾһ��ResultSet�����
	 * ����ʾ֮ǰ���뱣֤�����ڵ�Statement�ǻ��ŵ�
	 * @param rs
	 */
	public static void showResultSet(ResultSet rs){
		if (rs == null){
			return;
		}
		try {
			ResultSetMetaData md = rs.getMetaData();
			// ��ø�ResultSet�е�����
			int columnCount = md.getColumnCount();
			
			// ����������ָ��û��λ�ڵ�һ����¼��ǰ��
			// ���������ָ��ָ���һ����¼��ǰ��
			if(!rs.isBeforeFirst()){
				rs.beforeFirst();
			}

			// ��ǰ������ƶ������ָ�룬����ÿ����¼
			while (rs.next()){
				// ÿ����¼������columnCount����
				for (int i=1; i<columnCount; i++){
					// ���ڲ�֪�����е����ͣ�������getObject������ȡֵ
					System.out.print(rs.getObject(i) + "\t");
				}
				System.out.print(rs.getObject(columnCount) + "\r\n");
			}
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ر�Statement
	 * @param sm
	 */
	public static void closeStatement(Statement sm){
		if (sm != null){
			try {
				sm.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	} 
	
	/**
	 * �ر�����
	 * @param con
	 */
	public static void closeConnection(Connection con){
		if (con != null){
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	} 
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		String querySQL = "SELECT * FROM student_basic";
		String updateSQL = "UPDATE student_basic SET score=82 where name='mary'";
		String insertSQL = "INSERT INTO student_basic (name, age, score)"
			+ " VALUES ('zhangsan', 17, 86)";
		String deleteSQL = "DELETE FROM student_basic where name='wade'";
		
		Connection con = null;
		Statement sm = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			sm = con.createStatement();
			// ��ѯ
			ResultSet rs = OperateDB.queryDB(sm, querySQL);
			System.out.println("�޸����ݱ�֮ǰ�����ݣ�");
			OperateDB.showResultSet(rs);
			// �޸�
			OperateDB.updateDB(con, updateSQL);
			OperateDB.updateDB(con, insertSQL);
			OperateDB.updateDB(con, deleteSQL);
			System.out.println();
			System.out.println("�޸����ݱ�֮������ݣ�");
			// �ٲ�ѯ
			rs = OperateDB.queryDB(sm, querySQL);
			OperateDB.showResultSet(rs);
			System.out.println();
			
			// ��ResultSet����ȡֵʱ����ָ������������
			// �������ָ��ָ���һ������
			rs.absolute(1);
			System.out.print("name: " + rs.getString("name") + "\t");
			System.out.println("age: " + rs.getInt("age"));
			
			rs.absolute(3);
			System.out.print("name: " + rs.getString("name") + "\t");
			System.out.println("age: " + rs.getInt("age"));
			// �رս����
			rs.close();
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// �ر����ݿ�����
			closeStatement(sm);
			closeConnection(con);
		}
	}
	// TODO ��Ҫȷ��statement����connection�رպ�ResultSet�Ƿ�����
	/***
	 * ע�⣺
	 * ��1��һ��Statement����ͬʱֻ����һ��������ڻ.��ʹû�е���ResultSet��close()����,
	 * ֻҪ�򿪵ڶ���������������Ŷ���һ��������Ĺر�.�����������ͬʱ�Զ�����������,
	 * ��Ҫ�������Statement����,�������Ҫͬʱ����,
	 * ��ô������һ��Statement������˳�������������.
	 * 
	 * ��2��TODO
	 * �������ջ��ƿ����Զ��ر�����
2.Statement�رջᵼ��ResultSet�ر�
3.Connection�رղ��ᣨ��������Statement�ر�
4.�����������յ��̼߳�������͵ģ�Ϊ�˳���������ݿ���Դ���б�Ҫ��ʽ�ر����ǣ�������ʹ��Connection Pool��ʱ��
5.���ž����ǰ���ResultSet��Statement��Connection��˳��ִ��close
6.���һ��Ҫ����ResultSet��Ӧ��ʹ��RowSet��RowSet���Բ�������Connection��Statement��
Java���ݵ������ã������������ResultSet����᲻֪��Statement��Connection��ʱ�رգ���֪��ResultSet��ʱ��Ч
	 * 
	 */
}
