package com.zstax.util.常用类.C19.JavaSource.book.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import book.database.DBConnector;
import book.servlet.page.PageableResultSet;
import book.servlet.page.PageableResultSetImpl;

import com.sun.rowset.CachedRowSetImpl;
/**
 * ��ѯ���ݿ��Servlet
 */
public class QueryDBServlet extends HttpServlet {
	
	// ���ݿ����ӱ���
	Connection con = null;
	Statement sm = null;
	/**
	 * ��ʼ��Servletʵ��ʱ���ø÷���
	 */
	public void init() throws ServletException {
		super.init();
		// ��web.xml�ж�ȡservlet�����ò���
		String dbURL = this.getServletConfig().getInitParameter("dbURL");
		String driver = this.getServletConfig().getInitParameter("driver");
		String username = this.getServletConfig().getInitParameter("username");
		String password = this.getServletConfig().getInitParameter("password");
		try {
			// �������ݿ�
			con = DBConnector.getConnection(driver, dbURL, username, password);
			sm = con.createStatement();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	/**
	 * ����doGet���󣬵�JSP���е�methodΪgetʱ������ø÷���
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	/**
	 * ����doPost���󣬵�JSP���е�methodΪpostʱ������ø÷���
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// ��request�л�ȡJSPҳ���ϱ����������
		String sql = request.getParameter("querySQL");
		int pageIndex = Integer.parseInt((String)request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt((String)request.getParameter("pageSize"));
		String servleturl = request.getParameter("servleturl");
		String errorurl = request.getParameter("errorurl");
		try {
			// �ж�SQL����Ƿ�Ϊ��ѯ��䣬�Է����޸����ݿ�
			sql = sql.trim();
			if (!sql.toUpperCase().startsWith("SELECT ")){
				throw new Exception("�����SQL�������ǲ�ѯ��䣡");
			}
			// ִ�в�ѯ
			ResultSet rs = sm.executeQuery(sql);
			
			// Ϊ�˷�ֹ���ݿ����ӱ��ر�ʱ��ResultSet�е������޷���ȡ������
			// �����ｫResultSetת����CachedRowSet�����������ڴ��У������������ݿ⡣
			CachedRowSet crs = new CachedRowSetImpl();
			// populate������ResultSet�е����ݵ��뵽CachedRowSet��
			crs.populate(rs);
			
			// �����ܹ���ҳ��PageableResultSet����
			PageableResultSet pageResultSet = new PageableResultSetImpl(crs);
			// ����ÿҳ�ļ�¼���͵�ǰҳ��
			pageResultSet.setPageSize(pageSize);
			pageResultSet.gotoPage(pageIndex);
			// ���������session��
			HttpSession session = request.getSession();
			session.setAttribute("pageResultSet", pageResultSet);
			
			// ��Servlet������ϣ�������ת������һҳ�档
			request.getRequestDispatcher(servleturl).forward(request, response);
		} catch (Exception e){
			// ��ѯ����ʱ�����Session����Ϣ
			HttpSession session = request.getSession();
			session.removeAttribute("pageResultSet");
			// �����쳣ʱ����¼�쳣��Ϣ
			request.setAttribute("errormsg", e.getMessage());
			// ������ת��������ҳ��
			request.getRequestDispatcher(errorurl).forward(request, response);
		}
	}
	/**
	 * ����Servletʵ��ʱ�����ø÷���
	 */
	public void destroy() {
		super.destroy();
		try {
			// �ر�Statement
			if (sm != null){
				sm.close();
			}
		} catch (Exception e1){
		}
		try {
			// �ر�Connection
			if (con != null){
				con.close();
			}
		} catch (Exception e1){
		}
	}
	
	public String getServletInfo() {
		return "Query Database Servlet";
	}
}
