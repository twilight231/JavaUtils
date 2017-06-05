package com.zstax.util.常用类.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
	    	OperateDB.closeStatement(cs);
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
			OperateDB.closeConnection(con);
		}
	}
}
