package com.zstax.util.常用类.net.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * �����ҵķ������˳���GUI����
 */
public class ChatServer extends JFrame {

	// ״̬����ǩ
	static JLabel statusBar = new JLabel();
	// ��ʾ�ͻ��˵�������Ϣ���б�
	static java.awt.List connectInfoList = new java.awt.List(10);

	// ���浱ǰ����ͻ�������Ĵ������߳�
	static Vector clientProcessors = new Vector(10);
	// ��ǰ��������
	static int activeConnects = 0;

	// ���췽��
	public ChatServer()	{
		init();
		try {
			// ���ý���ΪϵͳĬ�����
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(){

		this.setTitle("�����ҷ�����");
		statusBar.setText("");

		// ��ʼ���˵�
		JMenu fileMenu = new JMenu();
		fileMenu.setText("�ļ�");
		JMenuItem exitMenuItem = new JMenuItem();
		exitMenuItem.setText("�˳�");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		// ��������в���
		JPanel jPanel1 = new JPanel();
		jPanel1.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(connectInfoList);
		pane.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "�ͻ���������Ϣ"));
		jPanel1.add(new JScrollPane(pane), BorderLayout.CENTER);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.getContentPane().add(jPanel1, BorderLayout.CENTER);

		this.pack();
	}

	/**
	 * �˳��˵����¼�
	 * @param e
	 */
	public void exitActionPerformed(ActionEvent e){
		//	��ͻ��˷��ͶϿ�������Ϣ
		sendMsgToClients(new StringBuffer(Constants.QUIT_IDENTIFER)); 
		// �ر����е�����
		closeAll(); 
		System.exit(0);
	}

	/**
	 * �����ڹر��¼�
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			exitActionPerformed(null);
		}
	}

	/**
	 * ˢ�������ң�����ˢ��clientProcessors���������µ��û��б�
	 */
	public static void notifyRoomPeople(){
		StringBuffer people = new StringBuffer(Constants.PEOPLE_IDENTIFER);
		for (int i = 0; i < clientProcessors.size(); i++) {
			ClientProcessor c = (ClientProcessor) clientProcessors.elementAt(i);
			people.append(Constants.SEPERATOR).append(c.clientName);
		}
		//	��sendClients������ͻ��˷����û��б����Ϣ
		sendMsgToClients(people); 
	}

	/**
	 * �����пͻ���Ⱥ����Ϣ
	 * @param msg
	 */
	public static synchronized void sendMsgToClients(StringBuffer msg) {
		for (int i = 0; i < clientProcessors.size(); i++) {
			ClientProcessor c = (ClientProcessor) clientProcessors.elementAt(i);
			System.out.println("send msg: " + msg);
			c.send(msg);
		}
	}

	/**
	 * �ر���������
	 */
	public static void closeAll(){
		while (clientProcessors.size() > 0)	{
			ClientProcessor c = (ClientProcessor) clientProcessors.firstElement();
			try {
				// �ر�socket���Ӻʹ����߳�
				c.socket.close();
				c.toStop();
			} catch (IOException e) {
				System.out.println("Error:" + e);
			} finally {
				clientProcessors.removeElement(c);
			}
		}
	}

	/**
	 * �жϿͻ����Ƿ�Ϸ���
	 * ������ͬһ�ͻ����ظ���½����νͬһ�ͻ�����ָIP�����ֶ���ͬ��
	 * @param newclient
	 * @return
	 */
	public static boolean checkClient(ClientProcessor newclient){
		if (clientProcessors.contains(newclient)){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �Ͽ�ĳ�����ӣ����Ҵ������б���ɾ��
	 * @param client
	 */
	public static void disconnect(ClientProcessor client){
		disconnect(client, true);
	}
	
	/**
	 * �Ͽ�ĳ�����ӣ�����Ҫ������Ƿ�������б���ɾ��
	 * @param client
	 * @param toRemoveFromList
	 */
	public static synchronized void disconnect(ClientProcessor client, boolean toRemoveFromList){
		try {
			 //�ڷ������˳����list������ʾ�Ͽ���Ϣ
			connectInfoList.add(client.clientIP + "�Ͽ�����");

			ChatServer.activeConnects--; //����������1
			String constr = "Ŀǰ��" + ChatServer.activeConnects + "�ͻ�����";
			statusBar.setText(constr);
			//��ͻ����ͶϿ�������Ϣ
			client.send(new StringBuffer(Constants.QUIT_IDENTIFER)); 
			client.socket.close();

		} catch (IOException e) {
			System.out.println("Error:" + e);
		} finally {
			//��clients������ɾ���˿ͻ������socket����Ϣ�� ��ֹͣ�̡߳�
			if (toRemoveFromList) {
				clientProcessors.removeElement(client);
				client.toStop();
			}
		}
	}
	
	public static void main(String[] args) {

		ChatServer chatServer1 = new ChatServer();
		chatServer1.setVisible(true);
		System.out.println("Server starting ...");

		ServerSocket server = null;
		try {
			// �������˿�ʼ����
			server = new ServerSocket(Constants.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Error:" + e);
			System.exit(1);
		}
		while (true) {
			// �����ǰ�ͻ�����С��MAX_CLIENT��ʱ������������
			if (clientProcessors.size() < Constants.MAX_CLIENT) {
				Socket socket = null;
				try {
					// �յ��ͻ��˵�����
					socket = server.accept();
					if (socket != null) {
						System.out.println(socket + "����");
					}
				} catch (IOException e) {
					System.out.println("Error:" + e);
				}

				//	���岢ʵ����һ��ClientProcessor�߳��࣬���ڴ���ͻ��˵���Ϣ
				ClientProcessor c = new ClientProcessor(socket);
				if (checkClient(c)) {
					// ��ӵ��б�
					clientProcessors.addElement(c);
					// ����ͻ��˺Ϸ��������
					int connum = ++ChatServer.activeConnects;
					// ��״̬������ʾ������
					String constr = "Ŀǰ��" + connum + "�ͻ�����";
					ChatServer.statusBar.setText(constr);
					// ���ͻ�socket��Ϣд��list��
					ChatServer.connectInfoList.add(c.clientIP + "����"); 
					c.start();
					// ֪ͨ���пͻ����û��б����仯
					notifyRoomPeople();
				} else {
					//����ͻ��˲��Ϸ�
					c.ps.println("�������ظ���½");
					disconnect(c, false);
				}

			} else {
				//����ͻ��˳�����MAX_CLIENT������ȴ�һ��ʱ���ٳ��Խ�������
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

/**
 * ����ͻ��˷��͵�������߳�
 */
class ClientProcessor extends Thread {
	//�洢һ�����ӿͻ��˵�socket��Ϣ
	Socket socket;
	//�洢�ͻ��˵���������
	String clientName;

	//�洢�ͻ��˵�ip��Ϣ
	String clientIP;
	
	//����ʵ�ֽ��ܴӿͻ��˷�����������
	BufferedReader br;
	//����ʵ����ͻ��˷�����Ϣ�Ĵ�ӡ��
	PrintStream ps; 

	boolean running = true;
	
	/**
	 * ���췽��
	 * @param s
	 */
	public ClientProcessor(Socket s) {
		socket = s;
		try {
			//	��ʼ�����������
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			ps = new PrintStream(socket.getOutputStream()); 
			// ��ȡ�յ�����Ϣ����һ����Ϣ�ǿͻ��˵����֡�IP��Ϣ
			String clientInfo = br.readLine();
			
			// ��ȡ��Ϣ��������Ϣ�ָ���������Ϣ
			StringTokenizer stinfo = new StringTokenizer(clientInfo, Constants.SEPERATOR);
			String head = stinfo.nextToken(); 
			if (head.equals(Constants.CONNECT_IDENTIFER)){
				if (stinfo.hasMoreTokens()){
					//�ؼ��ֺ�ĵڶ��������ǿͻ�����Ϣ
					clientName = stinfo.nextToken(); 
				}
				if (stinfo.hasMoreTokens()){
					//�ؼ��ֺ�ĵ����������ǿͻ�ip��Ϣ
					clientIP = stinfo.nextToken(); 
				}
				System.out.println(head); //�ڿ���̨��ӡͷ��Ϣ
			}
		} catch (IOException e) {
			System.out.println("Error:" + e);
		}
	}

	/**
	 * ��ͻ��˷�����Ϣ
	 * @param msg
	 */
	public void send(StringBuffer msg)	{
		ps.println(msg); 
		ps.flush();
	}
	
	//�߳����з���
	public void run() {

		while (running) {
			String line = null;
			try {
				//��ȡ�ͻ��˷�����������
				line = br.readLine();

			} catch (IOException e) {
				System.out.println("Error" + e);
				ChatServer.disconnect(this);
				ChatServer.notifyRoomPeople();
				return;
			}
			 //�ͻ����뿪
			if (line == null){
				ChatServer.disconnect(this);
				ChatServer.notifyRoomPeople();
				return;
			}

			StringTokenizer st = new StringTokenizer(line, Constants.SEPERATOR);
			String keyword = st.nextToken();

			// ����ؼ�����MSG���ǿͻ��˷�����������Ϣ
			if (keyword.equals(Constants.MSG_IDENTIFER)){
				StringBuffer msg = new StringBuffer(Constants.MSG_IDENTIFER).append(Constants.SEPERATOR);
				msg.append(clientName);
				msg.append(st.nextToken("\0"));
				//	�ٽ�ĳ���ͻ�������������Ϣ���͵�ÿ�����ӿͻ�����������
				ChatServer.sendMsgToClients(msg); 
				
			} else if (keyword.equals(Constants.QUIT_IDENTIFER)) {
				//	����ؼ�����QUIT���ǿͻ��˷����Ͽ����ӵ���Ϣ
				
				//	�������Ͽ�������ͻ�������
				ChatServer.disconnect(this); 
				//	�������������Ҳ�ˢ�������ͻ�����������list
				ChatServer.notifyRoomPeople(); 
				running = false;
			}
		}
	}
	
	public void toStop(){
		running = false;
	}
	
	// ����Object���equals����
	public boolean equals(Object obj){
		if (obj instanceof ClientProcessor){
			ClientProcessor obj1 = (ClientProcessor)obj;
			if (obj1.clientIP.equals(this.clientIP) && 
					(obj1.clientName.equals(this.clientName))){
				return true;
			}
		}
		return false;
	}
	
	// ����Object���hashCode����
	public int hashCode(){
		return (this.clientIP + Constants.SEPERATOR + this.clientName).hashCode();
	}
} 