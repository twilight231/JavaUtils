package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * �ж����ݿ��Ƿ�֧���������֧�֣����ʵ��������ύ��ع���
 * MySQL�����Ҫʹ���������ʹ��InnoDB�洢���棬�ڴ�����ʱ���������ENGINE=InnoDB��
 * MySQLĬ�ϵĴ洢������MyISAM����֧������
 */
public class Transaction {

	/**
	 * �ж����ݿ��Ƿ�֧������
	 * @param con	���ݿ������
	 * @return
	 */
	public static boolean supportTransaction(Connection con){
		try {
			// �õ����ݿ��Ԫ����
			DatabaseMetaData md = con.getMetaData();
			return md.supportsTransactions();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ��һ��SQL������һ��������ִ�У�Ҫĳȫ��ִ��ͨ����Ҫĳȫ����ִ��
	 * @param con	���ݿ������
	 * @param sqls	��ִ�е�SQL����
	 */
	public static void goTransaction(Connection con, String[] sqls){
		if (sqls == null){
			return ;
		}
		Statement sm = null;
		try {
			// ����ʼ
			System.out.println("����ʼ��");
			// �������Ӳ��Զ��ύ�����ø����ӽ��еĲ����������µ����ݿ�
			con.setAutoCommit(false);
			sm = con.createStatement();
			for (int i=0; i<sqls.length; i++){
				// ִ��SQL��䣬����û���µ����ݿ�
				sm.execute(sqls[i]);
			}
			// �ύ���������µ����ݿ�
			System.out.println("�����ύ��");
			con.commit();
			System.out.println("���������");
			// �������
		} catch (SQLException e) {
			try {
				// �����쳣ʱ�����лع���ȡ��ǰ��ִ�еĲ���
				System.out.println("����ִ��ʧ�ܣ����лع���");
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			OperateDB.closeStatement(sm);
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		String[] sqls = new String[3];
		sqls[0] = "UPDATE student_basic_innodb SET score=93 where name='john'";
		sqls[1] = "INSERT INTO student_basic_innodb (name, age, score)"
			+ " VALUES ('zhangsan', 17, 86)";
		// ִ�������������������Ϊ��student_basic_innodbû��xxxxxxx��
		sqls[2] = "DELETE FROM student_basic_innodb where xxxxxxx='wade'";
		
		Connection con = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// �ж��Ƿ�֧��������
			boolean supportTransaction = Transaction.supportTransaction(con);
			System.out.println("֧������ " + supportTransaction);
			if (supportTransaction){
				// ִ������
				Transaction.goTransaction(con, sqls);
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