package com.zstax.util.常用类.database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.Predicate;
import javax.sql.rowset.WebRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.FilteredRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

/**
 * ������ʾ���ʹ��RowSet�ӿڡ�RowSet�̳�ResultSet����ResultSet�����ã�
 * ����JDK 1.5��������֮һ���ýӿ��м����ӽӿڣ�
 * ��1��CachedRowSet�����Բ���������Դ�������ڵ����ӣ�ֻ�е������ݿ��ȡ���ݣ�
 * ���������ݿ�д�����ݵ�ʱ��Ż������ݿ⽨�����ӣ����ṩ��һ���������ķ������ݿ�ķ�ʽ�������ݾ������ڴ��С�
 * ������ResultSet�ڹر�Statement��Connection���޷���ȡ���ݵ����⡣
 * ��2��JdbcRowSet����ResultSet�Ķ�����а�װ��ʹ�ÿ��Խ�ResultSet������Ϊһ��JavaBeans�����
 * ��3��FilteredRowSet���̳���CachedRowSet�����Ը������������õ����ݵ��Ӽ���
 * ��4��JoinRowSet���̳���CachedRowSet�����Խ����RowSet�������SQL Join���ĺϲ���
 * ��5��WebRowSet���̳���CachedRowSet�����Խ�WebRowSet���������XML��ʽ��
 */
public class UsingRowSet {
	/**
	 * ʹ��CachedRowSet��
	 * һ��������ݣ�CachedRowSet�Ϳ��ԶϿ������ݿ�����ӣ�
	 * ֱ�������ݿ�д�����ݵ�ʱ����轨�����ӡ�
	 */
	public static void usingCachedRowSet() throws SQLException{
		CachedRowSet crs = new CachedRowSetImpl();
		// ����CachedRowSet�����ԣ���������ֱ�������ݿ�
        crs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        crs.setUsername("test");
        crs.setPassword("test");
        crs.setCommand("select * from student_basic where score > ?");
        crs.setDouble(1, 60);

		try {
			// ��Ҫ�ȼ���������������ʹ��ִ��ʱ���Ҳ���������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// CachedRowSet��execute����ִ��SQL��䣬
		// ���Ȼᴴ�������ݿ�����ӣ�Ȼ�󽫽����ȡ�������ٹر�����
		crs.execute();
		// ��ʱ��CachedRowSet�����ݿ��ǶϿ����ӵ�
        System.out.println("ʹ��CachedRowSet��������ǰ��");
        OperateDB.showResultSet(crs);
        
        // �ڶϿ����ӵ�����¿��Բ�������
        crs.beforeFirst();
        while (crs.next()) {
            if (crs.getString("name").equals("mary")) {
                crs.updateDouble("score",75);
                // Ҫ�뽫���µ������ύ�����ݿ⣬���������������
                // ����ȷ��Ҫ�޸�
                crs.updateRow();
                // �ٵ���acceptChanges������������ݿ�����ӣ����޸��ύ�����ݿ�
                crs.acceptChanges();
                break;
            }
        }
        System.out.println("ʹ��CachedRowSet�������ݺ�");
        OperateDB.showResultSet(crs);
        
        crs.close();
	}
	
	/**
	 * ʹ��JdbcRowSet��
	 * JdbcRowSet������ResultSet����,JdbcRowSet�ڲ���ʱ���������ݿ������,
	 * JdbcRowSet���صĽ��Ĭ���ǿ������¹����Ϳɸ��µġ�
	 */
	public static void usingJdbcRowSet() throws SQLException{
		JdbcRowSet jdbcrs = new JdbcRowSetImpl();
		// ����JdbcRowSet�����ԣ���������ֱ�������ݿ�
        jdbcrs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        jdbcrs.setUsername("test");
        jdbcrs.setPassword("test");
        jdbcrs.setCommand("select * from student_basic where score > ?");
        jdbcrs.setDouble(1, 70);

		try {
			// ��Ҫ�ȼ���������������ʹ��ִ��ʱ���Ҳ���������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// JdbcRowSet��execute����ִ��SQL��䣬���Ȼ�����ݿ����ӣ��ٻ�ȡ�������
		// ��CachedRowSet��execute������ͬ��ִ����������ر����ӣ�����һֱ���ָ����ӣ�ֱ������close������
        jdbcrs.execute();
        System.out.println("ʹ��JdbcRowSet��������ǰ��");
        OperateDB.showResultSet(jdbcrs);
        
        // Ȼ���������
        jdbcrs.beforeFirst();
        while (jdbcrs.next()) {
            if (jdbcrs.getString("name").equals("mary")) {
                jdbcrs.updateDouble("score",85);
                // �ύ�����ݿ�
                jdbcrs.updateRow(); 
                // ��Ϊ�����������ӵ����ݿ�ģ����Ժ�CachedRowSet��ͬ��������Ҫ�ٴλ�����ӡ�
                break;
            }
        }
        System.out.println("ʹ��JdbcRowSet�������ݺ�");
        OperateDB.showResultSet(jdbcrs);
        
        // �رս��������ʱ�ر����ݿ�����
        jdbcrs.close();
	}
	
	/**
	 * ʹ��FilteredRowSet��
	 * FilteredRowSet�ӿ��й涨�˿����趨������������˽ӿ�ΪPredicate�ӿڣ�
	 * ����ʵ��Predicate�ӿ��е�evaluate����
	 */
	public static void usingFilteredRowSet() throws SQLException{
		FilteredRowSet frs = new FilteredRowSetImpl();
		// ����FilteredRowSet�����ԣ���������ֱ�������ݿ�
        frs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        frs.setUsername("test");
        frs.setPassword("test");
        frs.setCommand("select * from student_basic where score > ?");
        frs.setDouble(1, 80);

		try {
			// ��Ҫ�ȼ���������������ʹ��ִ��ʱ���Ҳ���������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// FilteredRowSet�̳�CachedRowSet��
		// ����execute����������CachedRowSet��execute����һ��

		frs.execute();
		// ��ʱ��CachedRowSet�����ݿ��ǶϿ����ӵ�
        System.out.println("ʹ��FilteredRowSet��������֮ǰ��");
        OperateDB.showResultSet(frs);
        // ���ù�����������������ʵ��Predicate�ӿڶ��������execute������
        frs.setFilter(new Predicate(){
        	public boolean evaluate(RowSet rs){
        		CachedRowSet crs=(CachedRowSet)rs;
        		// �������Ϊmary�򷵻�true
        		try {
        			if (crs.getString("name").equals("mary")){
        				return true;
        			} 
        		} catch (SQLException e){
        		}
        		return false;
        	}
        	public boolean evaluate(Object arg0, int arg1) throws SQLException{
        		return false;
        	}
        	public boolean evaluate(Object arg0, String arg1) throws SQLException{
        		return false;
        	}
        });

        System.out.println("ʹ��FilteredRowSet��������֮��");
        OperateDB.showResultSet(frs);
        
        frs.close();
	}
	
	/**
	 * ʹ��JoinRowSet��
	 * JoinRowSet���Խ����RowSet�������join�ϲ���
	 * Join���п���ͨ��ÿ��RowSetͨ������setMatchColumn���������á�
	 * setMatchColumn��ʽ��Joinable�ӿڶ���ķ������������͵�RowSet�涨����Ҫʵ�ָýӿڡ�
	 * JoinRowSet�̳�CachedRowSet��Ҳ����Ҫ���������ݿ�����ӡ�
	*/
	public static void usingJoinRowSet() throws SQLException{
		JoinRowSet joinrs = new JoinRowSetImpl();
		
		CachedRowSet crs = new CachedRowSetImpl();
		// ����CachedRowSet�����ԣ���������ֱ�������ݿ�
		crs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
		crs.setUsername("test");
		crs.setPassword("test");
		crs.setCommand("select * from student_basic");
		try {
			// ��Ҫ�ȼ���������������ʹ��ִ��ʱ���Ҳ���������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// ��ȡ�����
		crs.execute();
		// ���ý������Joinʱƥ���������
		crs.setMatchColumn("name");
		// ����������ݷ���JoinRowSet
		joinrs.addRowSet(crs);
		crs.close();
		
		// ������һ����
		crs.setCommand("select name, address from student_address");
		crs.execute();
		crs.setMatchColumn("name");
		joinrs.addRowSet(crs);
		crs.close();
		
		System.out.println("ʹ��JoinRowSet�Զ�����������Join������");
		// ��ʱ����������Ѿ�Join��һ���ˣ�
		// ��student_basic��name�к�student_address��name�н���ƥ��
		while (joinrs.next()){
			// name���Թ��У�score����Ϊ��student_basic����
			System.out.print(joinrs.getString("name") + "\t");
			System.out.print(joinrs.getDouble("score") + "\t");
			// address����Ϊstudent_address����
			System.out.println(new String(joinrs.getBytes("address")));
		}

        joinrs.close();
	}
	
	/**
	 * ʹ��WebRowSet��
	 * WebRowSet�̳���CachedRowSet��֧��XML��ʽ�Ĳ�ѯ�����µȲ�����
	 * ���潫WebRowSet���������XML��ʽ���ļ���
	*/
	public static void usingWebRowSet() throws SQLException{
		WebRowSet wrs = new WebRowSetImpl();
		
		// ����CachedRowSet�����ԣ���������ֱ�������ݿ�
		wrs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
		wrs.setUsername("test");
		wrs.setPassword("test");
		wrs.setCommand("select * from student_basic");
		try {
			// ��Ҫ�ȼ���������������ʹ��ִ��ʱ���Ҳ���������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// ��ȡ�����
		wrs.execute();
		
		// �����XML�ļ�
		try {
			FileOutputStream out = new FileOutputStream("student_basic_data.xml");
			wrs.writeXml(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		wrs.close();
	}

	public static void main(String[] args) throws SQLException {
		UsingRowSet.usingCachedRowSet();
		UsingRowSet.usingJdbcRowSet();
		UsingRowSet.usingFilteredRowSet();
		UsingRowSet.usingJoinRowSet();
		UsingRowSet.usingWebRowSet();
	}
}
