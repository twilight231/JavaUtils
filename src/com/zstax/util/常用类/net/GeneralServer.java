package com.zstax.util.常用类.net;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * �����ʵ����һ�����ġ�֧�ֶ��̵߳ķ�������ͨ�ÿ�ܡ�
 * ���ܹ������κζ˿ڣ����յ�����ĳ���˿ڵ���������ʱ��
 * �����ӵ������������ݸ��ض��ķ�������ɷ������������
 * ֧��һ�������Ĳ������ʣ�֧����־���ܣ�����־д��������С�
 **/
public class GeneralServer {

	// ���������еķָ��
	// ����java�İ�ȫ���ƣ�System.getProperty("line.seperator")�ǲ��ܹ�ֱ��ȡ�õ�
	// ͨ������ķ�����ȡ
	public static final String LINE_SEPERATOR = (String) java.security.AccessController
			.doPrivileged(new sun.security.action.GetPropertyAction(
					"line.separator")); 
	// ������Ϣ��ָʾ����������������в�����
	// ����������
	// (1)�����������ķ���������������Ӧ�Ķ˿ں�
	// (2)�����Ҫ�Է��������п��ƣ�����Ҫָ����������Ͷ˿ڡ�
	public static final String HELP_MESSAGE = "Usage: java book.net.GeneralServer "
				+ "[-control <password> <port>] "
				+ "[<servicename> <port> ... ]";
	
	// �������������������˿ڵ�ӳ��
	Map services; 
	// ���浱ǰ��������Ϣ
	Set connections; 
	// ֧�ֵ���󲢷�������
	int maxConnections; 
	// ��������������������߳�
	ThreadGroup threadGroup;
	// ��־��Ϣ�������
	PrintWriter logStream;

	/**
	 * ���췽��
	 * ָ����־��Ϣ���������󲢷���������
	 **/
	public GeneralServer(OutputStream logStream, int maxConnections) {
		// ��ʼ����ʵ������
		this.setLogStream(logStream);
		this.log("Starting server");
		// ����һ���߳��飬�����������̶߳��ڸ�����
		this.threadGroup = new ThreadGroup(GeneralServer.class.getName());
		this.maxConnections = maxConnections;
		this.services = new HashMap();
		this.connections = new HashSet(maxConnections);
	}

	/** 
	 * ������־��Ϣ��������������Ϊnull
	 **/
	public synchronized void setLogStream(OutputStream out) {
		if (out != null){
			this.logStream = new PrintWriter(out);
		} else {
			this.logStream = null;
		}
	}
	/**
	 * д�ַ������͵���־��Ϣ����־�������
	 */
	protected synchronized void log(String s) {
		if (this.logStream != null) {
			this.logStream.println("[" + new Date() + "] " + s);
			this.logStream.flush();
		}
	}
	/**
	 * д�������͵���־��Ϣ����־�����
	 */
	protected void log(Object o) {
		this.log(o.toString());
	}

	/**
	 * ����������һ���·��񣬸÷������������ָ���Ķ˿��ϡ�
	 * @param service	�������ķ������
	 * @param port		�������ʹ�õĶ˿�
	 * @throws IOException
	 */
	public synchronized void addService(Service service, int port)
			throws IOException {
		// �����жϸö˿��Ƿ��Ѿ���������ʹ����
		Integer key = new Integer(port);
		if (this.services.get(key) != null)
			throw new IllegalArgumentException("Port " + port
					+ " already in use.");
		// Ϊ����Ͷ˿ڴ���һ����������������������
		Listener listener = new Listener(threadGroup, port, service);
		// ���˿ں�����������
		this.services.put(key, listener);
		// д��־
		this.log("Starting service " + service.getClass().getName() + " on port "
				+ port);
		// ����������
		listener.start();
	}

	/**
	 * ������ֹͣһ��������������ֹ�κ��Ѿ������˵����ӣ�
	 * ���ǻ�ʹ������ֹͣ���ܹ��ڸö˿ڵ���������
	 * @param port	��ֹͣ����Ķ˿�
	 */
	public synchronized void removeService(int port) {
		// �ҵ��ö˿��ϵ�������
		Integer key = new Integer(port);
		final Listener listener = (Listener) services.get(key);
		// ��������ֹͣ
		if (listener == null) {
			return;
		}
		listener.pleaseStop();
		// ���˿��ϵķ���ȥ��
		this.services.remove(key);
		// д��־
		this.log("Stopping service " + listener.service.getClass().getName()
				+ " on port " + port);
	}

	/**
	 * �����������ķ�������Ҫ���ò�����
	 */
	public static void main(String[] args) {
		
		try {
			// ������Ŀ������ڵ���2��
			if (args.length < 2) // Check number of arguments
				throw new IllegalArgumentException("Must specify a service");

			// ����ʹ�ñ�׼�������������־��Ϣ�������ͬʱ���������Ϊ10
			GeneralServer server = new GeneralServer(System.out, 10);

			// ��������
			int i = 0;
			while (i < args.length) {
				// ����-control����
				if (args[i].equals("-control")) {
					i++;
					// ��ȡ���Ƶ�����
					String password = args[i++];
					// ��ȡ���ƵĶ˿�
					int port = Integer.parseInt(args[i++]);
					// ���ؿ��Ʒ���ʵ�����ڶ˿��Ϲ�����
					server.addService(new Control(server, password), port);
				} else {
					// �����ʼ�����ķ������������̬���ط���ʵ��
					// ��ȡ���������
					String serviceName = args[i++];
					// ���ݷ�����������ʵ��
					Class serviceClass = Class.forName(serviceName);
					Service service = (Service) serviceClass.newInstance();
					// ��ȡ�˿�
					int port = Integer.parseInt(args[i++]);
					// ��������
					server.addService(service, port);
				}
			}
		} catch (Exception e) {
			// ��������
			System.err.println("Server: " + e);
			System.err.println(HELP_MESSAGE);
			System.exit(1);
		}
	}
	
	/**
	 * ����һ�����ӡ�
	 * ���������յ��ͻ��˵���������ʱ������ø÷�����
	 * ����ᴴ��һ�����Ӷ��󣬲����棬�����������������ر����ӡ�
	 * @param s		���ӵĿͻ���socket
	 * @param service	��������ķ���
	 */
	protected synchronized void addConnection(Socket s, Service service) {
		// �ж��������Ƿ�����
		if (this.connections.size() >= this.maxConnections) {
			try {
				// �ܾ��ͻ���
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.print("Connection refused; "
						+ "the server is busy; please try again later." + LINE_SEPERATOR);
				out.flush();
				// �ر�socket����
				s.close();
				// д��־
				this.log("Connection refused to "
						+ s.getInetAddress().getHostAddress() + ":"
						+ s.getPort() + ": max connections reached.");
			} catch (IOException e) {
				this.log(e);
			}
		} else {
			// ���������û�����������������
			// ����һ������Connection����
			Connection c = new Connection(s, service);
			// ���沢д��־
			this.connections.add(c);
			this.log("Connected to " + s.getInetAddress().getHostAddress() + ":"
					+ s.getPort() + " on port " + s.getLocalPort()
					+ " for service " + service.getClass().getName());
			// ���������߳�
			c.start();
		}
	}

	/**
	 * ����һ������
	 * @param c
	 */
	protected synchronized void endConnection(Connection c) {
		// �������б������
		this.connections.remove(c);
		this.log("Connection to " + c.client.getInetAddress().getHostAddress() + ":"
				+ c.client.getPort() + " closed.");
	}

	/**
	 * ���÷������Ĳ�����������
	 * @param max
	 */
	public synchronized void setMaxConnections(int max) {
		this.maxConnections = max;
	}
	
	/**
	 * ��ʾ������״̬�������ڵ��ԺͿ��Ʒ�����
	 * @param out	״̬��Ϣ�������
	 */
	public synchronized void displayStatus(PrintWriter out) {
		// ��ʾ�������ṩ�����з������Ϣ
		Iterator keys = services.keySet().iterator();
		while (keys.hasNext()) {
			Integer port = (Integer) keys.next();
			Listener listener = (Listener) services.get(port);
			out.print("SERVICE " + listener.service.getClass().getName()
					+ " ON PORT " + port + LINE_SEPERATOR);
		}

		// ��ʾ��������ǰ������������
		out.print("MAX CONNECTIONS: " + this.maxConnections + LINE_SEPERATOR);

		// ��ʾ��ǰ�������ӵ���Ϣ
		Iterator conns = this.connections.iterator();
		while (conns.hasNext()) {
			Connection c = (Connection) conns.next();
			out.print("CONNECTED TO "
					+ c.client.getInetAddress().getHostAddress() + ":"
					+ c.client.getPort() + " ON PORT "
					+ c.client.getLocalPort() + " FOR SERVICE "
					+ c.service.getClass().getName() + LINE_SEPERATOR);
		}
	}

	/** 
	 * �ڲ��࣬ʵ�������������������˿ڵ���������ʹ����ServerSocket
	 * ���յ�һ����������ʱ������Server��addConnection�����������Ƿ������������
	 * ��������ÿ��������һ����������
	 **/
	public class Listener extends Thread {
		// �������ӵ�socket
		ServerSocket listen_socket;
		// �����˿�
		int port;
		// �ڸö˿��ϵķ���
		Service service; 

		/**
		 * ��ʾ�Ƿ���Ҫֹͣ����
		 * ʹ��volatile �����ı�����ֵ��ʱ��ϵͳ�������´������ڵ��ڴ��ȡ���ݣ�
		 * ��ʹ��ǰ���ָ��ոմӸô���ȡ�����ݡ����Ҷ�ȡ���������̱�����
		 */
		volatile boolean stop = false; 
	
		/**
		 * ���췽��
		 * ������һ���̣߳�������������߳����С�
		 * ����һ��ServerSocket������������ָ���˿ڡ�
		 * @param group		�߳���
		 * @param port		�˿�
		 * @param service	�˿��ϵķ���
		 * @throws IOException
		 */
		public Listener(ThreadGroup group, int port, Service service)
				throws IOException {
			super(group, "Listener:" + port);
			listen_socket = new ServerSocket(port);
			// ���10����û���յ���������ServerSocket�Զ��ر�
			listen_socket.setSoTimeout(600000);
			this.port = port;
			this.service = service;
		}

		/** 
		 * ֹͣ����������
		 ***/
		public void pleaseStop() {
			// ����ֹͣ��־
			this.stop = true;
			// �жϽ��ܲ���
			this.interrupt();
			try {
				// �ر�ServerSocket
				listen_socket.close();
			} catch (IOException e) {
			}
		}

		/**
		 * ���������߳��壬�ȴ��������󣬽������ӡ�
		 **/
		public void run() {
			// �����ʶҪֹͣ����������һֱ����
			while (!stop) {
				try {
					// �ȴ���������
					Socket client = listen_socket.accept();
					// ���յ���������뵽��������
					addConnection(client, service);
				} catch (InterruptedIOException e) {
				} catch (IOException e) {
					log(e);
				}
			}
		}
	}

	/**
	 * �ڲ��࣬�������ӣ�����ͻ��˺ͷ���֮�����ӡ�
	 * ��Ϊÿ�����Ӷ����̣߳����Զ������С�
	 * ����ʵ�ַ�����֧�ֶ��̵߳Ĺؼ��㡣
	 */
	public class Connection extends Thread {
		// ���ӵĿͻ���
		Socket client;
		// �ͻ�������ķ���
		Service service;

		/**
		 * ���췽�������������̵߳��ã������������߳����ڷ������߳��飬
		 * �������ӵ��߳�Ҳ���ڷ������߳��顣
		 * @param client	
		 * @param service	
		 */
		public Connection(Socket client, Service service) {
			super("Server.Connection:"
					+ client.getInetAddress().getHostAddress() + ":"
					+ client.getPort());
			this.client = client;
			this.service = service;
		}
		/**
		 * ���ӵ��߳���
		 * ��ȡ���Կͻ��˵����������������Ȼ����÷����serve������
		 * ������������󣬹ر����ӡ�
		 */
		public void run() {
			try {
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				// ���þ���ķ���
				service.serve(in, out);
			} catch (IOException e) {
				log(e);
			} finally {
				// �ر�����
				endConnection(this);
			}
		}
	}

	/**
	 * ����Ľӿڶ��塣�����������з��񶼱���ʵ�ָýӿڡ�
	 * ���ڷ�����ʹ���˷������ͨ����������޲������췽�����������ʵ����
	 * �������еķ���ʵ���඼�����ṩһ���޲����Ĺ��췽����
	 */
	public interface Service {
		/**
		 * ���񷽷�
		 * @param in  �ͻ��˵�������
		 * @param out	�ͻ��˵������
		 * @throws IOException
		 */
		public void serve(InputStream in, OutputStream out) throws IOException;
	}

	/**
	 * һ���򵥵ķ�����ͻ���֪ͨ�������ϵĵ�ǰʱ��
	 **/
	public static class Time implements Service {
		public void serve(InputStream i, OutputStream o) throws IOException {
			PrintWriter out = new PrintWriter(o);
			out.print(new Date() + LINE_SEPERATOR);
			out.close();
			i.close();
		}
	}

	/**
	 * �����ַ����ķ��񡣽��ͻ���������ַ���������󷵻ء�
	 * ���ͻ�������һ��"."ʱ���ر����ӡ�
	 */
	public static class Reverse implements Service {
		public void serve(InputStream i, OutputStream o) throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(i));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(o)));
			out.print("Welcome to the line reversal server." + LINE_SEPERATOR);
			out.print("Enter lines.  End with a '.' on a line by itself." + LINE_SEPERATOR);
			for (;;) {
				out.print("> ");
				out.flush();
				// �ӿͻ��˵���������ȡ��һ��
				String line = in.readLine();
				if ((line == null) || line.equals(".")){
					break;
				}
				// ���ַ������򷵻�
				for (int j = line.length() - 1; j >= 0; j--){
					out.print(line.charAt(j));
				}
				out.print(LINE_SEPERATOR);
			}
			out.close();
			in.close();
		}
	}


	/**
	 * �����������������ͨ��ʵ������������ʸ÷������������
	 * ÿһ�����Ӷ�����������1��
	 **/
	public static class UniqueID implements Service {
		public int id = 0;

		public synchronized int nextId() {
			return id++;
		}

		public void serve(InputStream i, OutputStream o) throws IOException {
			PrintWriter out = new PrintWriter(o);
			out.print("You are client #: " + nextId() + LINE_SEPERATOR);
			out.close();
			i.close();
		}
	}

	/**
	 * ���Ʒ������ķ���ͨ��������֤��
	 * �ͻ����ṩ����÷���ִ��������Ʒ�������״̬��
	 * �����У�
	 * ��1��password: �������룬ֻ��������ȷ���ܹ�ִ����������
	 * ��2��add: ���ӷ������������ŷ������Ͷ˿ں�
	 * ��3��remove: ɾ���������������˿ں�
	 * ��4��max: �޸ķ���������󲢷�������
	 * ��5��status: ��ʾ��������״̬
	 * ��6��help: ��ʾ������Ϣ
	 * ��7��quit: �˳����Ʒ���
	 * һ��������ͬʱֻ����һ���ͻ������ӵ����Ŀ��Ʒ����ϡ�
	 */
	public static class Control implements Service {
		// �����Ƶķ�����
		GeneralServer server;
		// ��ȷ������
		String password;
		// ��ʶ�Ƿ��Ѿ��пͻ������ӵ��÷�����
		boolean connected = false; 
		// ������Ϣ
		String helpMsg = "COMMANDS:" + LINE_SEPERATOR + "\tpassword <password>" + LINE_SEPERATOR
				+ "\tadd <service> <port>" + LINE_SEPERATOR + "\tremove <port>" + LINE_SEPERATOR
				+ "\tmax <max-connections>" + LINE_SEPERATOR + "\tstatus" + LINE_SEPERATOR 
				+ "\thelp" + LINE_SEPERATOR	+ "\tquit" + LINE_SEPERATOR;

		// Ĭ�Ϲ��췽����˽�з�����
		// ��ʾ�÷����ܱ���������̬���أ�ֻ�ܹ�������������ʱ��̬����
		private Control(){
			// do nothig
		}
	
		/**
		 * ������Ʒ��񡣿����ض��ķ�������
		 * ����������֤�ͻ����Ƿ���Ȩ�޿��Ʒ�������
		 * @param server
		 * @param password
		 */
		public Control(GeneralServer server, String password) {
			this.server = server;
			this.password = password;
		}

		/**
		 * �ṩ�ķ��񡣶�ȡ�ͻ��˵����룬ʹ��java.util.StringTokenizer�������
		 * ����������÷������Ŀ��Ʋ�����
		 **/
		public void serve(InputStream i, OutputStream o) throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(i));
			PrintWriter out = new PrintWriter(o);
			String line; 
			// ��ʶ�Ƿ񾭹�����֤
			boolean authorized = false;

			// ����ʹ����ͬ������
			// ��ʶ����пͻ������ӵ��˸÷��������ͻ��˽����ܽ���÷���
			synchronized (this) {
				if (connected) {
					out.print("ONLY ONE CONTROL CONNECTION ALLOWED." + LINE_SEPERATOR);
					out.close();
					return;
				} else {
					connected = true;
				}
			}

			// �������ִ������
			for (;;) { 
				// ���ͻ���һ����ʾ��
				out.print("> "); 
				out.flush(); 
				// ��ȡ�ͻ�������
				line = in.readLine();
				if (line == null){
					// ���û�����룬����
					break;
				}
				try {
					// ʹ��StringTokenizer��������
					StringTokenizer t = new StringTokenizer(line);
					if (!t.hasMoreTokens()){
						// �����һ���մ����������һ��ѭ��
						continue;
					}
					// ��ȡ��һ������
					String command = t.nextToken().toLowerCase();
					//������������ͬ�Ĵ���
					if (command.equals("password")) {
						// ��ȡ���������
						String p = t.nextToken();
						// ƥ������
						if (p.equals(this.password)) { 
							// ƥ��ɹ�
							out.print("OK" + LINE_SEPERATOR);
							authorized = true;
						} else {
							// ƥ��ʧ��
							out.print("INVALID PASSWORD" + LINE_SEPERATOR); 
						}
						
					} else if (command.equals("add")) { 
						// ���ӷ���
						// �����ж��Ƿ��Ѿ�ͨ����֤
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							// ��ȡ������������̬����
							String serviceName = t.nextToken();
							Class serviceClass = Class.forName(serviceName);
							Service service;
							try {
								service = (Service) serviceClass.newInstance();
							} catch (NoSuchMethodError e) {
								throw new IllegalArgumentException(
										"Service must have a "
												+ "no-argument constructor");
							}
							// ��ȡ�˿ں�
							int port = Integer.parseInt(t.nextToken());
							// ��ӵ�������
							server.addService(service, port);
							out.print("SERVICE ADDED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("remove")) {
						// ɾ������
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							// ��ȡ�˿ں�
							int port = Integer.parseInt(t.nextToken());
							// �ӷ�������ɾ��
							server.removeService(port);
							out.print("SERVICE REMOVED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("max")) {
						// ���÷�������󲢷�������
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							int max = Integer.parseInt(t.nextToken());
							server.setMaxConnections(max);
							out.print("MAX CONNECTIONS CHANGED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("status")) {
						// ��ʾ������״̬
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							server.displayStatus(out);
						}
						
					} else if (command.equals("help")) { 
						// ��ʾ������Ϣ
						out.print(helpMsg);
						
					} else if (command.equals("quit")) {
						// �˳�����
						break; 
						
					} else {
						out.print("UNRECOGNIZED COMMAND" + LINE_SEPERATOR); 
					}
				} catch (Exception e) {
					out.print("ERROR WHILE PARSING OR EXECUTING COMMAND:" + LINE_SEPERATOR
							+ e	+ LINE_SEPERATOR);
				}
			}
			// ִ����ͻ�������󣬽���־λ��Ϊfalse�������ͻ��˱�ɷ��ʸ÷����ˡ�
			connected = false;
			out.close();
			in.close();
		}
	}
}
