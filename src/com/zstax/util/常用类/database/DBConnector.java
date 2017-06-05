package com.zstax.util.常用类.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ���Ӹ������ݿ�ķ���
 */
public class DBConnector {
	/**
	 * ������ݿ�����
	 * @param driverClassName	�������ݿ��õ��������������
	 * @param dbURL		���ݿ��URL
	 * @param userName	��½���ݿ���û���
	 * @param password	��½���ݿ������
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driverClassName,
			String dbURL, String userName, String password)
			throws ClassNotFoundException, SQLException {
		Connection con = null;

		// �����������ݿ��������
		Class.forName(driverClassName);
		// ���û����������������ݿ�
		con = DriverManager.getConnection(dbURL, userName, password);

		return con;
	}
	
	/**
	 * ���Oracle���ݿ������
	 * @param dricerClassName	�������ݿ��õ��������������
	 * @param serverHost	���ݿ����ڷ�������IP������
	 * @param serverPort	���ݿ����ڷ������Ķ˿�
	 * @param dbName		���ݿ���
	 * @param userName		��½���ݿ���û���
	 * @param password		��½���ݿ������
	 * @return
	 * @throws ClassNotFoundException		���ݿ��������޷��ҵ����׳����쳣
	 * @throws SQLException		��������ʱ�����׳����쳣
	 */
	public static Connection getOracleConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "oracle.jdbc.driver.OracleDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "1521";
		}
		// ��������Oracle���ݿ��URL
		String dbURL = "jdbc:oracle:thin:@" + serverHost + ":" + serverPort
				+ ":" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * ���DB2���ݿ������
	 */
	public static Connection getDB2Connection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "com.ibm.db2.jdbc.app.DB2Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5000";
		}
		// ��������DB2���ݿ��URL
		String dbURL = "jdbc:db2://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * ���SQL Server���ݿ������
	 */
	public static Connection getSQLServerConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "1433";
		}
		// ��������SQL Server���ݿ��URL
		String dbURL = "jdbc:microsoft:sqlserver://" + serverHost + ":" + serverPort
				+ "; DatabaseName=" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * ���MySQL���ݿ������
	 */
	public static Connection getMySQLConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "com.mysql.jdbc.Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "3306";
		}
		// ��������SQL Server���ݿ��URL
		String dbURL = "jdbc:mysql://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * ���Sybase���ݿ������
	 */
	public static Connection getSybaseConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "com.sybase.jdbc3.jdbc.SybDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5007";
		}
		// ��������SQL Server���ݿ��URL
		String dbURL = "jdbc:sybase:Tds:" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * ���PostgreSQL���ݿ������
	 */
	public static Connection getPostgreSQLConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// ���û���ṩ��Щ���Ӳ���������Ĭ��ֵ
		if (dricerClassName == null) {
			dricerClassName = "org.postgresql.Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5432";
		}
		// ��������SQL Server���ݿ��URL
		String dbURL = "jdbc:postgresql://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}

	public static void main(String[] args) throws ClassNotFoundException, 
			SQLException {
		// ��ñ���MySQL������ʵ����ʹ��MySQL��Ҫȥwww.mysql.com�������µ�MySQL��װ�����Java����
		// MySQL�ж������MySQL�������࣬��org.gjt.mm.mysql.Driver��
		// ����ʹ��MySQL�ٷ���վ���ṩ��������
		String mySQLDirver = "com.mysql.jdbc.Driver";
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		Connection con = DBConnector.getMySQLConnection(mySQLDirver,
				null, null, dbName, userName, password);
		System.out.println("����MySQL���ݿ�ɹ���");
		con.close();
		System.out.println("�ɹ��ر���MySQL���ݿ�����ӣ�");
		String url = "jdbc:mysql://127.0.0.1:3306/" +  dbName;
		con = DBConnector.getConnection(mySQLDirver, url, userName, password);
		System.out.println("����MySQL���ݿ�ɹ���");
		con.close();
		System.out.println("�ɹ��ر���MySQL���ݿ�����ӣ�");
	}
}
