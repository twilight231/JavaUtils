package com.zstax.util.常用类.C19.JavaSource.book.servlet.page;

import java.sql.ResultSet;
/**
 * ֧�ַ�ҳ�Ľ�����Ľӿڡ�
 */
public interface PageableResultSet extends ResultSet {
	/** ������ҳ��*/
	int getPageCount();

	/**���ص�ǰҳ�ļ�¼���� */
	int getPageRowsCount();

	/** ���ط�ҳ��С */
	int getPageSize();

	/** ת��ָ��ҳ */
	void gotoPage(int page);

	/** ���÷�ҳ��С */
	void setPageSize(int pageSize);

	/** �����ܼ�¼���� */
	int getRowsCount();

	/** ת����ǰҳ�ĵ�һ����¼
	 * @exception java.sql.SQLException
	 */
	void pageFirst() throws java.sql.SQLException;

	/**
	 * ת����ǰҳ�����һ����¼
	 * @exception java.sql.SQLException
	 */
	void pageLast() throws java.sql.SQLException;

	/**���ص�ǰҳ��*/
	int getCurPage();
}
