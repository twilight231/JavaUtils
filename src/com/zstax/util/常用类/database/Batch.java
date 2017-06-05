package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ִ��һ��SQL��䣨������
 */
public class Batch {
	
	/**
	 * �ж����ݿ��Ƿ�֧��������
	 * @param con
	 * @return
	 */
	public static boolean supportBatch(Connection con){
		try {
			// �õ����ݿ��Ԫ����
			DatabaseMetaData md = con.getMetaData();
			return md.supportsBatchUpdates();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ִ��һ��SQL���
	 * @param con	���ݿ������
	 * @param sqls	��ִ�е�SQL����
	 * @return
	 */
	public static int[] goBatch(Connection con, String[] sqls){
		if (sqls == null){
			return null;
		}
		Statement sm = null;
		try {
			sm = con.createStatement();
			// �����е�SQL�����ӵ�Statement��
			for (int i=0; i<sqls.length; i++){
				sm.addBatch(sqls[i]);
			}
			// һ��ִ�ж���SQL���
			return sm.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OperateDB.closeStatement(sm);
		}
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String tableName = "student_basic";
		String userName = "test";
		String password = "test";
		String[] sqls = new String[3];
		sqls[0] = "UPDATE student_basic SET score=95 where name='john'";
		sqls[1] = "INSERT INTO student_basic (name, age, score) VALUES ('lisi', 17, 78)";
		sqls[2] = "DELETE FROM student_basic where name='zhangsan'";
		
		Connection con = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// �ж��Ƿ�֧��������
			boolean supportBatch = Batch.supportBatch(con);
			System.out.println("֧��Batch�� " + supportBatch);
			if (supportBatch){
				// ִ��һ��SQL���
				int[] results = Batch.goBatch(con, sqls);
				// ����ִ�еĽ��
				for (int i=0; i<sqls.length; i++){
					if (results[i] >= 0){
						System.out.println("���: " + sqls[i] + " ִ�гɹ���Ӱ����"
								+ results[i] + "������");
					} else if (results[i] == Statement.SUCCESS_NO_INFO){
						System.out.println("���: " + sqls[i] + " ִ�гɹ���Ӱ�������δ֪");
					} else if (results[i] == Statement.EXECUTE_FAILED){
						System.out.println("���: " + sqls[i] + " ִ��ʧ��");
					}
				}
			}
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