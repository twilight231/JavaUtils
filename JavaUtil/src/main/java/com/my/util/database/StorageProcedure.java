package com.my.util.database;

import java.sql.*;

/**
 * ��ȡ���������������ݿ�Ĵ洢����
 */
public class StorageProcedure {

	/**
	 * �г����ݿ������еĴ洢������
	 * @param con	���ݿ������
	 */
	public static void listStorageProcedureName(Connection con){
		   try {
	        // ������ݿ��Ԫ����
	        DatabaseMetaData md = con.getMetaData();
	        // ������еĴ洢���̵�����
	        ResultSet resultSet = md.getProcedures(null, null, "%");
	    
	        //��ʾ�洢��������λ�ڽ�����ĵ������ֶ�
	        System.out.println("���ݿ����еĴ洢������Ϊ��");
	        while (resultSet.next()) {
	            String procName = resultSet.getString(3);
	            System.out.print(procName + "\t");
	        }
	        System.out.println();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	/**
	 * ���ô洢����
	 * @param con
	 */
	public static void callStorageProcedure(Connection con){
	    CallableStatement cs = null;
	    try {
	      /*** �����޲����Ĵ洢���� ***/
	    	// �ô洢���������ݱ��в���һ������
	        cs = con.prepareCall("{call my_insert_proc()}");
	        cs.execute();
	    
	      /**** ������һ����������Ĵ洢���� ****/
	        // �ô洢���������ݱ��в���һ�����ݣ�������һ�е�ֵΪ����ֵ
	        cs = con.prepareCall("{call my_insert_proc1(?)}");
	        //���ò���
	        cs.setInt(1, 18);
	        // ִ��
	        cs.execute();
	    
	      /*** ������һ����������Ĵ洢���� ****/
	        // �ô洢���̷������ݱ��еļ�¼��
	        cs = con.prepareCall("{call my_count_proc1(?)}");
	        // ע���������������
	        cs.registerOutParameter(1, Types.INTEGER);
	        // ִ��
	        cs.execute();
	        // ��ȡ���������ֵ
	        int outParam = cs.getInt(1);
	        System.out.println("my_count_proc1() ִ�н����" + outParam);
	    
	      /***	������һ�����������һ����������Ĵ洢����	***/
	        // �ô洢���̷������ݱ���score>��������ļ�¼��
	        cs = con.prepareCall("{call my_count_proc(?,?)}");
	        // ע���������������
	        cs.registerOutParameter(2, Types.INTEGER);
	        // �������������ֵ
	        cs.setInt(1, 90);
	        // ִ��
	        cs.execute();
	        // ��ȡ���������ֵ
	        outParam = cs.getInt(2);
	        System.out.println("my_count_proc ִ�н����" + outParam);
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	closeStatement(cs);
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

		Connection con = null;
		try {
			// ������ݿ�����
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// �г����ݿ�����д洢������
			StorageProcedure.listStorageProcedureName(con);
			// ���ô洢����
			StorageProcedure.callStorageProcedure(con);
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// �ر����ݿ�����
			closeConnection(con);
		}
	}
}
