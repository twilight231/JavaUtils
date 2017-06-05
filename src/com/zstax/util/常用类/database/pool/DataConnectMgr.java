package com.zstax.util.常用类.database.pool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * ���ݿ����ӹ��������ܹ�����ͬ�������ݿ�����ӳء�
 */
public class DataConnectMgr {
	//ȱʡ�����ݿ����ӳ������ļ�
	public static final String DEFAULT_DB_PROPERTIES = "./db.properties";
	// Ψһʵ��
	static private DataConnectMgr instance; 
	// ��ǰ���ӵ����������ӹ������ϵĿͻ�����Ŀ
	static private int clients; 
	//��������࣬����ÿ�����ݿ⣬����һ��������
	private Vector drivers = new Vector();
	//����Ѿ����������ӳأ�ÿ�����ݿⶼ��һ�����ӳ�
	private Hashtable pools = new Hashtable();

	/**
	 * ���캯��˽�У��Է�ֹ�������󴴽�����ʵ��
	 */
	private DataConnectMgr() {
		init();
	}
	private DataConnectMgr(LinkedList drivers, Hashtable jdbcs) {
		init2(drivers, jdbcs);
	}

	/**
	 * ����ģʽ������Ψһʵ��������ǵ�һ�ε��ô˷������򴴽�ʵ����
	 * ����ȱʡ�����ݿ����ӳ������ļ��������ӳ�
	 * @return DBConnectionManager Ψһʵ��
	 */
	public static synchronized DataConnectMgr getInstance() {
		if (instance != null) {
			clients++;
			return instance;
		} else {
			instance = new DataConnectMgr();
			return instance;
		}
	}
	/**
	 * ��ȡ���ݿ����ӹ�����ʵ������������ڣ��㴴��ʵ������ָ���˴������ӳصĲ���
	 * @param jdbcInfo	�������ݿ�Ĳ���
	 * @return
	 */
	public static synchronized DataConnectMgr getInstance(JDBCInfo jdbcInfo) {
		if (instance != null) {
			clients++;
			return instance;
		}
		LinkedList drivers = new LinkedList();
		drivers.add(jdbcInfo.getDriver());
		Hashtable jdbcs = new Hashtable();
		jdbcs.put(jdbcInfo.getName(), jdbcInfo);
		return getInstance(drivers, jdbcs);
	}

	/**
	 * ��ȡ���ݿ����ӹ�����ʵ������������ڣ��㴴��ʵ��������һ�δ���������ӳ�
	 * @param drivers	ÿ�����ӳص����ݿ�������
	 * @param jdbcs		ÿ�����ӳص����Ӳ���
	 * @return
	 */
	public static synchronized DataConnectMgr getInstance(LinkedList drivers,
			Hashtable jdbcs) {
		if (instance == null) {
			instance = new DataConnectMgr(drivers, jdbcs);
		}
		clients++;
		return instance;
	}

	/**
	 * ��ȡȱʡ�����ݿ����ӳ������ļ�������ȱʡֵ��ɳ�ʼ�����������ݿ����ӳ�
	 */
	private void init() {
		InputStream is = null;
		Properties dbProps = new Properties();
		try {
			is = new FileInputStream(DEFAULT_DB_PROPERTIES);
			dbProps.load(is);
		} catch (Exception e) {
			System.err.println("���ܹ���ȡĬ�����ݿ����ӳ������ļ�����ȷ���ļ��Ƿ���ڣ�"
							+ DEFAULT_DB_PROPERTIES);
			return;
		}
		loadDrivers(dbProps);
		createPools(dbProps);
	}

	/**
	 * ����������ݿ����ӳ�
	 * @param drivers	ÿ�����ӳص����ݿ�������
	 * @param jdbcInfo	ÿ�����ӳص����Ӳ���
	 */
	private void init2(LinkedList drivers, Hashtable jdbcInfo) {
		loadDrivers(drivers);
		createPools(jdbcInfo);
	}

	/**
	 * ��̬�������ݿ�����������
	 * @param mdrivers
	 */
	private void loadDrivers(LinkedList mdrivers) {
		for (int i = 0; i < mdrivers.size(); i++) {
			try {
				//�������ݿ����������࣬���÷�����ƴ������������
				Driver driver = (Driver) Class
						.forName((String) mdrivers.get(i)).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				System.out.println("�ɹ��������ݿ���������:  "
						+ mdrivers.get(i));
			} catch (Exception e) {
				System.err.println("�������ݿ���������ʧ��:  " 
						+ mdrivers.get(i) + ". ������Ϣ��" + e);
			}
		}
	}

	/**
	 * װ�غ�ע�����ݿ����������ļ������е�JDBC��������
	 * @param props ����
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = null;
		driverClasses = props.getProperty("drivers");

		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				// �½�������
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance();
				// ע������
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				System.out.println("�ɹ��������ݿ���������: "
						+ driverClassName);
			} catch (Exception e) {
				System.err.println("�������ݿ���������ʧ��: "
						+ driverClassName + ". ������Ϣ: " + e);
			}
		}
	}

	/**
	 * ����ָ�������ݿ����ӳ������ļ��������ӳ�ʵ��.
	 * @param props ���ӳ�����
	 */
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			// ����������ݿ�ĸ�������
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");

				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					System.err.println("�������������������: "
							+ maxconn + " .���ӳ���: " + poolName);
					max = 0;
				}
				System.out.println("׼���������ݿ����ӳأ�" + poolName);
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				pools.put(poolName, pool);
				System.out.println("�������ݿ����ӳ�: "
						+ poolName + "�ɹ�");
			}
		}
	}

	/**
	 * �������ݿ����ӳ�������Ϣ�������ӳ�
	 * @param jdbcInfos
	 */
	private void createPools(Hashtable jdbcInfos) {

		Iterator it = jdbcInfos.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			JDBCInfo info = (JDBCInfo) en.getValue();
			if (info.getUrl() == null) {
				continue;
			}
			System.out.println("׼���������ݿ����ӳ�: " + (String) en.getKey());
			DBConnectionPool pool = new DBConnectionPool((String) en.getKey(),
					info.getUrl(), info.getUser(), info.getPassword(), info
							.getMaxconn());
			pools.put(en.getKey(), pool);
		}
	}

	/**
	 * ���һ�����õ�(���е�)����.���û�п�������,������������С�����������
	 * ����,�򴴽�������������
	 * @param name �������ļ��ж�������ӳ�����
	 * @return Connection �������ӻ�null
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			System.out.println("�����ݿ����ӳأ�" + pool.getName() + "��ȡһ�����ӣ�");
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * ���һ����������.��û�п�������,������������С���������������,
	 * �򴴽�������������.����,��ָ����ʱ���ڵȴ������߳��ͷ�����.
	 *
	 * @param name ���ӳ�����
	 * @param time �Ժ���Ƶĵȴ�ʱ��
	 * @return Connection �������ӻ�null
	 */
	public Connection getConnection(String name, long time) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * �����Ӷ��󷵻ظ�������ָ�������ӳ�
	 * @param name �������ļ��ж�������ӳ����֣�������Դ��Ϣ�е�name�ֶ�
	 * @param con ���Ӷ���
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			System.out.println("�ͷ���һ�����ݿ����ӵ����ӳأ�" + pool.getName());
			pool.freeConnection(con);
		}
	}

	/**
	 * �ر���������,�������������ע�� ��
	 * ֻ�е����ӵ������ݿ����ӹ������Ŀͻ�����ĿΪ0ʱ�����ܹ���ɳ�����
	 */
	public synchronized void release() {
		// �ȴ�ֱ�����һ���ͻ��������
		if (--clients != 0) {
			return;
		}
		//�ͷ����ӳ�
		Enumeration allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			System.out.println("׼���ر����ݿ����ӳ�: " + pool.getName());
			pool.release();
			System.out.println("���ݿ����ӳ�: " + pool.getName() + "�Ѿ����رգ�");
		}
		//��ע���Ѿ�ע������ݿ�����������
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				System.out.println("���ݿ�����������" + driver.getClass().getName() + "�Ѿ���ע���ˣ�");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * ���ڲ��ඨ����һ�����ӳ�.���ܹ�����Ҫ�󴴽�������,ֱ��Ԥ������
	 * ��������Ϊֹ.�ڷ������Ӹ��ͻ�����֮ǰ,���ܹ���֤���ӵ���Ч��.
	 */
	class DBConnectionPool {
		private int checkedOut = 0;//��ǰ�Ѿ���ȡ�ߵ����ݿ���������Ҳ�������ڱ�ʹ�õ�������
		private Vector freeConnections = new Vector();//�����ӳ��п��õ����ݿ�����
		private int maxConn;//�����ӳ������������ݿ�������
		private String name;//���ݿ����ӳص�����
		private String user;//�������ݿ���û���
		private String password;//�������ݿ������
		private String URL;//���ݿ��URL

		//Ĭ�Ϲ��캯��
		public DBConnectionPool() {
			this.maxConn = 0;
			this.password = "";
			this.URL = "";
		}
		/**�����µ����ӳ�
		 * @param name ���ӳ�����
		 * @param URL ���ݿ��JDBC URL
		 * @param user ���ݿ��ʺ�,�� null
		 * @param password ����,�� null
		 * @param maxConn �����ӳ������������������
		 */
		public DBConnectionPool(String name, String URL, String user,
				String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
			this.initConnection();
		}
		/**
		 * �����µ�����
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL.trim());
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				System.out.println("���ӳ�" + this.name + "����һ���µ����ݿ�����, Ŀǰ����"
						+ this.checkedOut + "��������ʹ�ã�");
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return null;
			}
			return con;
		}
		/**
		 * Ԥ�ȴ�һ������
		 */
		private void initConnection() {
			Connection con = getConnection();
			freeConnections.addElement(con);
		}
		/**
		 * �����ӳػ��һ����������.��û�п��е������ҵ�ǰʹ�õ�������С���������
		 * ������,�򴴽�������.��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
		 * Ȼ��ݹ�����Լ��Գ����µĿ�������.
		 */
		public synchronized Connection getConnection() {
			Connection con = null;
			if (freeConnections.size() > 0) {
				// ��ȡ�����е�һ����������
				con = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);
				try {
					//�����ŵ���������Ѿ����ڻ��߲����ã��������ȡ
					if ((con == null) || (con.isClosed())) {
						con = getConnection();
					}
				} catch (SQLException e) {
					con = getConnection();
				}
			} else if (maxConn == 0 || checkedOut < maxConn) {
				//�����µ�����
				System.out.println("���ݿ����ӳ�: " + this.name + "׼������һ���µ�����");
				con = newConnection();
			} else {
				System.out.println("���ݿ����ӳ�" + this.name + "û�п��õ����ӣ�");
			}
			if (con != null){
				this.checkedOut++;
			}
			return con;
		}

		/**
		 * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ��
		 * �μ�ǰһ��getConnection()����.
		 *
		 * @param timeout �Ժ���Ƶĵȴ�ʱ������
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					//�ȴ�һ��ʱ�䣬�ڴ������ͻ����ͷ����ӡ�
					wait(timeout);
				} catch (InterruptedException e) {
				}
				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()���ص�ԭ���ǳ�ʱ����ʾû�еõ����õ����ӣ�����null
					return null;
				}
			}
			return con;
		}
		/**
		 * ������ʹ�õ����ӷ��ظ����ӳ�
		 * @param con �ͻ������ͷŵ�����
		 */
		public synchronized void freeConnection(Connection con) {
			// ��ָ�����Ӽ��뵽����ĩβ
			freeConnections.addElement(con);
			checkedOut--;
			notifyAll();
		}
		/**
		 * �ر���������
		 */
		public synchronized void release() {
			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = (Connection) allConnections.nextElement();
				try {
					con.close();
					System.out.println("�ر������ݿ����ӳأ�" + this.name + "�е�һ�����ݿ�����!");
				} catch (SQLException e) {
					System.err.println("�޷��ر����ӳ�" + this.name + "�е�����"
							+ e.getMessage());
				}
			}
			freeConnections.removeAllElements();
		}
		/**
		 * �������ݿ����ӳص�����
		 */
		public String getName(){
			return this.name;
		}
	}
}