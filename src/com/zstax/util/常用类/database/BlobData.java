package com.zstax.util.常用类.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��ȡBLOB���ݵ����ݿ⡣
 */
public class BlobData {
	/**
	 * дBlob���ݵ����ݿ�
	 * @param con	
	 * @param infilePath	���浽���ݿ���ļ�	
	 */
	public static void writeBlob(Connection con, String infilePath){
		FileInputStream fis = null;
		PreparedStatement psm = null;
		try {
			//�������ļ�
			File file = new File(infilePath);
			fis = new FileInputStream(file);
			psm = con.prepareStatement(
					"insert into student_photo(name, filename, filedata)"
					+ " values('mary', ?, ?)");
			//��һ������Ϊ�ļ���
			psm.setString(1, file.getName());
			//�ڶ�������Ϊ�ļ��Ķ�������
			psm.setBinaryStream(2, fis, fis.available());
			// ִ��
			psm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//�رմ򿪵Ķ���
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			OperateDB.closeStatement(psm);
		}
	}
	/**
	 * �����ݿ��ж�Blob����
	 * @param con
	 * @param srcFileName	Ҫ��ȡ���ļ���
	 * @param outFilePath	���浽���ص��ļ�
	 */
	public static void readBlob(Connection con, String srcFileName, 
			String outFilePath){
        Statement sm = null;
        FileOutputStream fos = null;
        InputStream is = null;
		try {
	        sm = con.createStatement();
	        ResultSet rs = sm.executeQuery(
	        		"SELECT * FROM student_photo where name = 'mary'"
	        		+ " and filename = '" + srcFileName + "'");
	        if (rs.next()) {
	            // �����ж�ȡBlob����
	            Blob blob = rs.getBlob("filedata");
	    
	            // ��ȡBlob���ֽ�������long���ͣ���ʾ�ֽ����ܶࡣ
	            long blobLength = blob.length();
	            // ����ͨ��blob��getBytes������Blob��������ȡ�ֽ�����
	            // Ҳͨ��Blob����Ķ�����������������
	            is = blob.getBinaryStream();
				byte[] buffer = new byte[4096];
				int size;
				File file = new File(outFilePath);
				try {
					fos = new FileOutputStream(file);
					while ((size = is.read(buffer)) != -1){
						fos.write(buffer, 0, size);
					}
				} catch (IOException eee) {
					eee.printStackTrace();
				}
	        }
	        rs.close();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(sm);
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			// дBlob����
			BlobData.writeBlob(con, "C:/temp/mary_photo.jpg");
			// ��Blob����
			BlobData.readBlob(con, "mary_photo.jpg", "C:/temp/mary_photo_db.jpg");
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
