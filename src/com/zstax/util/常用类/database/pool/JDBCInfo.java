package com.zstax.util.常用类.database.pool;
/**
 * ����һ�����ݿ����ӳ���Ҫ����Ϣ���û��������롢�����������
 */
public class JDBCInfo {
    /**�������ݿ�������Ϣ*/
    private String driver;	//�������ݿ��������
    private String url;	//���ݿ��·��
    private String user;	//�û���
    private String password;//����
    private String name;	//���ݿ����ӳص�����
    private int maxconn;	//���������
    
    public JDBCInfo() {
        this.driver = "";
        this.url = "";
        this.name = "";	
        this.maxconn = 0;
    } 
    public JDBCInfo(String name, String driver, String url, 
    		String user, String password, int maxconn) {
        this.name = name;
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.maxconn = maxconn;
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public int getMaxconn() {
        return maxconn;
    }
    public void setMaxconn(int maxconn) {
        this.maxconn = maxconn;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}