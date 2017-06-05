package com.zstax.util.常用类.C19.JavaSource.book.servlet.page;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * ��һ��SQL��ѯ��������ΪHTML�ַ���
 */
public class HtmlResultSet {
	// �����
	private ResultSet rs;

	public HtmlResultSet(ResultSet rs) {
		this.rs = rs;
	}
	/**
	 * ���������������HTML
	 */
	public String toString(){
		try {
			rs.last();
			int numrows = rs.getRow();
			return toString(1, numrows);
		} catch (SQLException e) {
			StringBuffer out = new StringBuffer();
			out.append("<TABLE border=\"1\">\n");
			out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage() + "\n");
			return out.toString();
		}
	}

	/**
	 * ֻΪ��������ּ�¼����HTML
	 */
	public String toString(int begin, int end) {
		StringBuffer out = new StringBuffer();
		// �������������ʾ��HTML��Table��
		out.append("<TABLE border=\"1\">\n");

		try {
			if ((begin <= 0)|| (end <= 0) ||(begin > end)){
				throw new Exception("��������");
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			// �������ͷ
			int numcols = rsmd.getColumnCount();
			out.append("<TR>");
			for (int i = 1; i <= numcols; i++) {
				out.append("<TH  align=\"center\">" + rsmd.getColumnLabel(i));
			}
			out.append("</TR>\n");
			
			rs.absolute(begin);
			// �����������
			do {
				// ÿ����¼ռ��һ��
				out.append("<TR>");
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD  align=\"center\">");
					Object obj = rs.getObject(i);
					if (obj != null) {
						out.append(obj.toString());
					} else {
						out.append("&nbsp;");
					}
				}
				out.append("</TR>\n");
				if (rs.getRow() == end){
					break;
				}
			} while (rs.next());

			// ������
			out.append("</TABLE>\n");
		} catch (Exception e) {
			out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage() + "\n");
		}

		return out.toString();
	}
}
