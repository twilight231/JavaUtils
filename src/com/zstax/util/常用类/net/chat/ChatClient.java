package com.zstax.util.常用类.net.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * �����ҵĿͻ��˳���GUI���档
 */
public class ChatClient extends JFrame implements ActionListener{
	
	// ��½�����ҵ����ֱ�ǩ�������
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(15);

	// ���ӺͶϿ����ӵİ�ť
	JButton connectButton = new JButton();
	JButton disConnectButton = new JButton();

	// ���������ݵ��ı���
	JTextArea chatContentTextArea = new JTextArea(9, 30);

	// ������Ϣ�İ�ť
	JButton sendMsgButton = new JButton();
	// ��Ϣ�����
	JTextField msgTextField = new JTextField(20);
	JLabel msglabel = new JLabel();
	// �������û��б�
	java.awt.List peopleList = new java.awt.List(10);

	/*���¶������������������*/
	Socket soc = null; 
	PrintStream ps = null; 
	
	// �ͻ���������������Ϣ���߳�
	ClentListener listener = null; 

	public ChatClient() {
		init();
	}

	//	��ʼ��ͼ�ν���
	public void init() {

		this.setTitle("�����ҿͻ���");
		
		// ��ʼ����ť�ͱ�ǩ
		nameLabel.setText("������");
		connectButton.setText("�� ��");
		connectButton.addActionListener(this);
		disConnectButton.setText("�� ��");
		disConnectButton.addActionListener(this);
		// �����������ݲ��ɱ༭
		chatContentTextArea.setEditable(false);
		sendMsgButton.setText("�� ��");
		sendMsgButton.addActionListener(this);
		msgTextField.setText("������������Ϣ");
		
		//panel1������������������������ť
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(nameLabel);
		panel1.add(nameTextField);
		panel1.add(connectButton);
		panel1.add(disConnectButton);
		
		//���ڷ���������Ϣ��ʾ��������Ա�б�
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JScrollPane pane1 = new JScrollPane(chatContentTextArea);
		pane1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "��������"));
		panel2.add(pane1);
		JScrollPane pane2 = new JScrollPane(peopleList);
		pane2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "�û��б�"));
		panel2.add(pane2);
		
		//���ڷ��÷�����Ϣ����
		JPanel panel3 = new JPanel(); 
		panel3.setLayout(new FlowLayout());
		panel3.add(msglabel);
		panel3.add(msgTextField);
		panel3.add(sendMsgButton);
		
		// �������ӵ�����
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel1, BorderLayout.NORTH);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.getContentPane().add(panel3, BorderLayout.SOUTH);
		
		this.pack();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر������ҿͻ����¼�
	 */
	protected void processWindowEvent(WindowEvent e){
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// ����ǹر������ҿͻ��ˣ���Ͽ�����
			disconnect();
			dispose();
			System.exit(0);
		}
	}
	
	/**
	 * ����ť�¼�
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == connectButton){
			// ���������Ӱ�ť
			if (soc == null) {
				try {
					// ʹ�ö˿�2525ʵ����һ�������׽���
					soc = new Socket(InetAddress.getLocalHost(), Constants.SERVER_PORT); 
					// �ڿ���̨��ӡʵ�����Ľ��
					System.out.println(soc); 
					//��psָ��soc�������
					ps = new PrintStream(soc.getOutputStream()); 
					//����һ���ַ�����洢������Ϣ
					StringBuffer info = new StringBuffer(Constants.CONNECT_IDENTIFER).append(Constants.SEPERATOR); 
					//����INFOΪ�ؼ����÷�����ʶ��Ϊ������Ϣ
					//����name��ip��"��"�ֿ����ڷ������˽���һ��
					//StringTokenizer������ȡ����
					String userinfo = nameTextField.getText() + Constants.SEPERATOR
							+ InetAddress.getLocalHost().getHostAddress();
					ps.println(info.append(userinfo));

					ps.flush();
					//���ͻ����߳�ʵ������������
					listener = new ClentListener(this, nameTextField.getText(), soc);   
					listener.start(); 
				} catch (IOException e) {
					System.out.println("Error:" + e);
					disconnect();
				}
			}
			
		} else if (source == disConnectButton){
			// �������Ͽ����Ӱ�ť
			disconnect();
			
		} else if (source == sendMsgButton) {
			//���������Ͱ�ť
			if (soc != null) {
				 //���岢ʵ����һ���ַ�����洢���͵�������Ϣ
				StringBuffer msg = new StringBuffer(Constants.MSG_IDENTIFER).append(Constants.SEPERATOR);
				//�ô�ӡ������������Ϣ
				ps.println(msg.append(msgTextField.getText())); 
				ps.flush();
			}
		}
	}

	/**
	 * �Ͽ��������������
	 */
	public void disconnect(){
		if (soc != null) {
			try {
				// �ô�ӡ������QUIT��Ϣ֪ͨ�������Ͽ��˴�ͨ��
				ps.println(Constants.QUIT_IDENTIFER); 
				ps.flush();
				soc.close(); //�ر��׽���
				listener.toStop();
				soc = null;
			} catch (IOException e) {
				System.out.println("Error:" + e);
			} 
		}
	}
	
	public static void main(String[] args){
		ChatClient client = new ChatClient();
		client.setVisible(true);
	}

	/**
	 * �ͻ����߳�������������������������Ϣ
	 */
	class ClentListener extends Thread	{
		//�洢�ͻ������Ӻ��name��Ϣ
		String name = null;
		//�ͻ��˽��ܷ��������ݵ�������
		BufferedReader br = null;
		//ʵ�ִӿͻ��˷������ݵ��������Ĵ�ӡ��
		PrintStream ps = null;

		//�洢�ͻ��˵�socket��Ϣ
		Socket socket = null;
		//�洢��ǰ���е�ChatClientʵ��
		ChatClient parent = null;

		boolean running = true;

		//���췽��
		public ClentListener(ChatClient p, String n, Socket s)	{

			//���ܲ���
			parent = p;
			name = n;
			socket = s;

			try {
				//ʵ��������������
				br = new BufferedReader(new InputStreamReader(s
						.getInputStream()));
				ps = new PrintStream(s.getOutputStream());

			} catch (IOException e) {
				System.out.println("Error:" + e);
				parent.disconnect();
			}
		} 
		
		// ֹͣ����
		public void toStop(){
			this.running = false;
		}

		//�߳����з���
		public void run(){
			String msg = null;
			while (running) {
				msg = null;
				try {
					// ��ȡ�ӷ�������������Ϣ
					msg = br.readLine();
					System.out.println("receive msg: " + msg);
				} catch (IOException e) {
					System.out.println("Error:" + e);
					parent.disconnect();
				}
				// ����ӷ�������������ϢΪ����Ͽ��˴�����
				if (msg == null) {
					parent.listener = null;
					parent.soc = null;
					parent.peopleList.removeAll();
					running = false;
					return;
				}
				
				//��StringTokenizer����ʵ�ֶ�ȡ�ֶ��ַ�
				StringTokenizer st = new StringTokenizer(msg, Constants.SEPERATOR); 
				//��ȡ��Ϣͷ���ؼ�������ʶ���Ǻ�����Ϣ
				String keyword = st.nextToken(); 

				if (keyword.equals(Constants.PEOPLE_IDENTIFER)) {
					//�����PEOPLE���Ƿ����������Ŀͻ�������Ϣ
					//��Ҫ����ˢ�¿ͻ��˵��û��б�
					parent.peopleList.removeAll();
					//����stȡ��Ŀǰ�����ӵĿͻ�
					while (st.hasMoreTokens()) 	{
						String str = st.nextToken();
						parent.peopleList.add(str);
					}
					
				} else if (keyword.equals(Constants.MSG_IDENTIFER)) {
					//����ؼ�����MSG���Ƿ�����������������Ϣ, 
					//��Ҫ����ˢ�¿ͻ���������Ϣ����ÿ���ͻ�������������ʾ����
					String usr = st.nextToken();
					parent.chatContentTextArea.append(usr);
					parent.chatContentTextArea.append(st.nextToken("\0"));
					parent.chatContentTextArea.append("\r\n");
					
				} else if (keyword.equals(Constants.QUIT_IDENTIFER)) {
					//����ؼ�����QUIT���Ƿ������رյ���Ϣ, �жϴ˴�����
					System.out.println("Quit");
					try {
						running = false;
						parent.listener = null;
						parent.soc.close();
						parent.soc = null;
					} catch (IOException e) {
						System.out.println("Error:" + e);
					} finally {
						parent.soc = null;
						parent.peopleList.removeAll();
					}
					
					break;
				}
			}
			//����û��б�
			parent.peopleList.removeAll();
		}
	} 
} 
