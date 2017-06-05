package com.zstax.util.常用类.net.ftp.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import book.string.ChangeCharset;

import sun.net.ftp.FtpClient;

public class MainFrame extends JFrame {
	//���ӷ������İ�ť
	JButton connectButton = new JButton("�� ��");
	// FTP������IP��ַ�����
	JLabel IPLabel = new JLabel("IP��");
	JTextField serverIPTextField = new JTextField(16);
	// /�û�����ǩ�������
	JLabel userNameLabel = new JLabel("�û�����");
	JTextField userNameTextField = new JTextField(16);
	// �����ǩ�������
	JLabel passwordLabel = new JLabel("���룺");
	JTextField passwordTextField = new JTextField(16);
	// ��ǰĿ¼��ǩ�������
	JLabel currentDirLabel = new JLabel("��ǰĿ¼��");
	JTextField currentDirTextField = new JTextField(30);
	// ת����һ��Ŀ¼�İ�ť
	JButton upLevelButton = new JButton("��һ��Ŀ¼");
	// ���غ������ļ��İ�ť
	JButton downloadButton = new JButton("����");
	JButton uploadButton = new JButton("�ϴ�");
	// �Ͽ����˳��İ�ť
	JButton disconnectButton = new JButton("�Ͽ�");
	JButton exitButton = new JButton("�ر�");

	// �������ݣ�������ʾFTP�������ϵ���Ϣ
	String[] columnname = { "�ļ���", "�ļ���С", "�޸�����" };
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable ftpFileInfosTable = new JTable();
	JScrollPane ftpFileScrollPane1 = new JScrollPane(ftpFileInfosTable);

	// ���غ��ϴ����ļ��Ի���
	FileDialog saveFileDialog = new FileDialog(this, "Download To ..",
			FileDialog.SAVE);
	FileDialog openFileDialog = new FileDialog(this, "Upload File ..",
			FileDialog.LOAD);

	FtpClient ftpClient = null;
	// IP��ַ���û��������롢FTP·��
	String ip = "";
	String username = "";
	String password = "";
	String path = "";

	// ��ǰFTPĿ¼�µ��ļ���Ŀ¼��Ϣ
	Vector files = new Vector();
	Vector fileSizes = new Vector();
	Vector fileTypes = new Vector();
	Vector fileDates = new Vector();

	// ��ǰѡ��ı���е��кź��к�
	int row = 0;
	int column = 0;

	// ��ǰ����ִ��������������߳�
	Vector performTaskThreads = new Vector();
	// ��ʾ��ǰ�������б�
	List taskList = new List();
	// ��ʾFTP�ͻ��˳���Ŀ���̨��Ϣ
	JTextArea consoleTextArea = new JTextArea();

	// ��Ϊ��FTP��ȡ�ñ�����ISO-8859-1��Ϊ��֧�����ģ����Խ���ת����
	// ��������ڽ��ַ������½��ж���ģ�����ֱ��ʹ���ˡ�
	ChangeCharset changeCharset = new ChangeCharset();
	
	public MainFrame() {
		init();
		setLocation(100, 150);
		setTitle("FTP �ͻ���");
		pack();
		try {
			// ���ý���ΪϵͳĬ�����
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init(){
		// ��ʼ�������ı���
		serverIPTextField.setText("127.0.0.1");
		userNameTextField.setText("");
		passwordTextField.setText("");
		currentDirTextField.setText("");
		currentDirTextField.setEditable(false);

		// ��IP���û��������롢���ӱ�ǩ�������������panel1��
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(IPLabel);
		panel1.add(serverIPTextField);
		panel1.add(userNameLabel);
		panel1.add(userNameTextField);
		panel1.add(passwordLabel);
		panel1.add(passwordTextField);
		panel1.add(connectButton);

		// ����ǰĿ¼����һ��Ŀ¼�ı�ǩ�������Ͱ�ť�������panel2��
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(currentDirLabel);
		panel2.add(currentDirTextField);
		panel2.add(upLevelButton);
		
		JPanel panelA = new JPanel();
		panelA.setLayout(new BorderLayout());
		panelA.add(panel1, BorderLayout.NORTH);
		panelA.add(panel2, BorderLayout.CENTER);

		ftpFileScrollPane1.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)),
				"FTP���������ļ���Ϣ"));

		// �����ء��ϴ����Ͽ����˳���ť����panel3��
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.add(downloadButton);
		panel3.add(uploadButton);
		panel3.add(disconnectButton);
		panel3.add(exitButton);

		// �������б�����й������������
		JScrollPane taskScrollPane = new JScrollPane(taskList);
		taskScrollPane.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)), "�����ض���"));
		
		// ������̨��Ϣ���ı�����ڹ���������У���������Ϊ���ɱ༭
		consoleTextArea.setBackground(SystemColor.control);
		consoleTextArea.setEditable(false);
		consoleTextArea.setText("");
		consoleTextArea.setRows(5);
		JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
		consoleScrollPane.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)), "��������Ϣ"));
		
		JPanel panelB = new JPanel();
		panelB.setLayout(new BorderLayout());
		panelB.add(panel3, BorderLayout.NORTH);
		panelB.add(taskScrollPane, BorderLayout.CENTER);
		panelB.add(consoleScrollPane, BorderLayout.SOUTH);
		
		// �����������ӵ��������
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelA, BorderLayout.NORTH);
		this.getContentPane().add(ftpFileScrollPane1, BorderLayout.CENTER);
		this.getContentPane().add(panelB, BorderLayout.SOUTH);
		
		// Ϊ��ť����¼�������
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connectButton_actionPerformed(e);
			}
		});
		upLevelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				upLevelButton_actionPerformed(e);
			}
		});
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				downloadButton_actionPerformed(e);
			}
		});
		uploadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				uploadButton_actionPerformed(e);
			}
		});
		disconnectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				disconnectButton_actionPerformed(e);
			}
		});
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitButton_actionPerformed(e);
			}
		});
		ftpFileInfosTable.addMouseListener(new MainFrame_Table_mouseAdapter(
				this));

	}

	/**
	 * �����ڹر��¼�
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			disconnection();
			System.exit(0);
		}
	}
	/**
	 * �������Ӱ�ť�������¼�
	 * @param e
	 */
	void connectButton_actionPerformed(ActionEvent e) {
		// ���IP���û���������
		ip = serverIPTextField.getText();
		username = userNameTextField.getText();
		if (username.equals("")){
			username = "anonymous";
		}
		password = passwordTextField.getText();
		if (password.equals("")){
			password = "anonymous";
		}
		if (ip.equals(""))
			return;

		try {
			// ���ӷ�����
			Connector conn = new Connector(this, ip, username, password);
			conn.start();
		} catch (Exception ee) {
		}
	}

	/**
	 * ת����һ��Ŀ¼��ť�¼�����
	 * @param e
	 */
	void upLevelButton_actionPerformed(ActionEvent e) {
		upDirectory();
	}

	/**
	 * ���ذ�ť�¼�����
	 * @param e
	 */
	void downloadButton_actionPerformed(ActionEvent e) {
		try {
			// ��ñ�����б�ѡ����ļ���
			String str = (String) files.elementAt(row);
			// �����ѡ�����һ���ļ��������ء�
			String t = (String) fileTypes.elementAt(row);
			if (t.startsWith("-")){
				// �����Ǹ��ļ�����
				download(str, "");
			}
		} catch (Exception ee) {
			consoleTextArea.append("file download has problems!\n");
		}

	}
	/**
	 * �ϴ���ť�¼�����
	 * @param e
	 */
	void uploadButton_actionPerformed(ActionEvent e) {
		upload();

	}
	/**
	 * �Ͽ����Ӱ�ť�¼�����
	 * @param e
	 */
	void disconnectButton_actionPerformed(ActionEvent e) {
		disconnection();
	}

	/**
	 * �˳�FTP�ͻ��˰�ť�¼�����
	 * @param e
	 */
	void exitButton_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * �ϴ��ļ���FTP
	 */
	void upload() {
		try {
			// ��ʾ�ϴ��ļ��ĶԻ���
			openFileDialog.show();
			String directory = openFileDialog.getDirectory();
			if (directory == null || directory.equals("")){
				return;
			}
			// ��ñ�ѡ����ļ���
			String file = openFileDialog.getFile();
			if (file == null || file.equals("")){
				return;
			}
			String filename = directory + file;
			// �½�һ���ϴ��ļ��������̣߳�������
			UploadFileThread up = new UploadFileThread(this, ip, username,
					password, path, file, filename);
			up.start();
			// ���浽�����߳�����
			performTaskThreads.addElement(up);

		} catch (Exception ee) {
			consoleTextArea.append("file upload has problems!\n ");
			return;
		}
	}

	/**
	 * �����ļ�������
	 * @param filename
	 * @param directory
	 */
	void download(String filename, String directory) {
		try {
			// ��ô���ļ��ı���Ŀ¼���ļ���
			if (directory.equals("")) {
				saveFileDialog.setFile(filename);
				saveFileDialog.show();
				// ����Ŀ¼���ļ���
				directory = saveFileDialog.getDirectory();
				String file = saveFileDialog.getFile();
				if (directory == null || directory.equals("")){
					return;
				}
				if (file == null || file.equals("")){
					file = filename;
				} else {
					// �Ȼ�ô����ص��ļ����ĺ�׺
					int index = filename.lastIndexOf(".");
					String extn = filename.substring(index + 1);
					// ��������ļ����ʹ������ļ����ĺ�׺��һ����
					// �򽫱����ļ���������׷�Ӵ������ļ����ĺ�׺
					index = file.lastIndexOf(".");
					String extn1 = file.substring(index + 1);
					if (!extn.equals(extn1)){
						file = file + "." + extn;
					}
				}
				directory = directory + file;
			} else {
				directory += filename;
			}
			// ����һ�������ļ����̣߳�������
			DownloadFileThread down = new DownloadFileThread(this, ip,
					username, password, path, filename, directory);
			down.start();
			performTaskThreads.add(down);
		} catch (Exception ee) {
			consoleTextArea.append("file" + filename + "has problems!");
		}
	}
	/**
	 * �Ͽ�FTP�ͻ�������
	 */	
	public void disconnection() {
		try {
			// ������е������߳�
			if (performTaskThreads.size() != 0) {
				if ((JOptionPane.showConfirmDialog(this, "��������û��ִ���꣬ȷ���˳���",
						"�Ͽ�����", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)) {
					return;
				}
				try {
					for (int i = 0; i < performTaskThreads.size(); i++) {
						Object thread = performTaskThreads.elementAt(i);
						if (thread instanceof DownloadFileThread){
							DownloadFileThread down = (DownloadFileThread)thread;
							down.toStop();
						} else if (thread instanceof UploadFileThread){
							UploadFileThread upload = (UploadFileThread)thread;
							upload.toStop();
						}

					}
				} catch (Exception ee) {
					System.out.println(ee.toString());
				}
				taskList.removeAll();
			}
			//��������һЩ����
			files.removeAllElements();
			fileTypes.removeAllElements();
			fileSizes.removeAllElements();
			fileDates.removeAllElements();

			serverIPTextField.setText("");
			userNameTextField.setText("");
			passwordTextField.setText("");
			currentDirTextField.setText("");
			consoleTextArea.append("server " + ip + " disconnected!\n");

			ip = "";
			username = "";
			password = "";
			path = "";
			
			// ������
			String[][] disdata = null;
			String[] col = null;
			tableModel.setDataVector(disdata, col);
			ftpFileInfosTable.setModel(tableModel);

			// �ر�FTP�ͻ���
			ftpClient.closeServer();
		} catch (Exception ee) {
			consoleTextArea.append("server " + ip
					+ " dicconect has problems!\n");
		}
	}

	// ���ñ������
	void setTableData() {
		try {
			// ��������ļ��б���Ϣ
			if (files.size() != 0) {
				files.removeAllElements();
				fileTypes.removeAllElements();
				fileSizes.removeAllElements();
				fileDates.removeAllElements();
			}
			String list = "";
			// �л�����ǰĿ¼
			ftpClient.cd("/");
			if (!path.equals("")){
				ftpClient.cd(path);
			}
			// List��ǰĿ¼�µ����ݡ�����Ŀ¼���ļ���
			InputStream is = ftpClient.list();
			int c;
			while ((c = is.read()) != -1) {
				String s = (new Character((char) c)).toString();
				list += s;
			}
			is.close();
			if (!list.equals("")) {
				// ����List����õ������ݣ��õ���ǰĿ¼�µ�Ŀ¼���ļ�������С������
				StringTokenizer st = new StringTokenizer(list, "\n");
				int count = st.countTokens();
				for (int i = 0; i < count; i++) {
					String s = st.nextToken();
					StringTokenizer sst = new StringTokenizer(s, " ");
					c = sst.countTokens();

					String datastr = "";
					String filestr = "";

					for (int j = 0; j < c; j++) {
						String ss = sst.nextToken();
						if (j == 0){
							fileTypes.addElement(
									changeCharset.changeCharset(
											ss,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
						} else if (j == 4) {
							System.out.println(ss);
							fileSizes.addElement(ss);
						} else if (j == 5) {
							datastr = ss;
						} else if (j == 6) {
							datastr += " " + ss;
						} else if (j == 7) {
							datastr += " " + ss;
							fileDates.addElement(
									changeCharset.changeCharset(
											datastr,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
						} else if (j == 8) {
							if (c == 9){
								files.addElement(
										changeCharset.changeCharset(
												ss,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
							} else {
								filestr = ss;
							}
						} else if (j > 8) {
							filestr += " " + ss;
							if (j == (c - 1)){
								files.addElement(
										changeCharset.changeCharset(
												filestr,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
							}
						}
					}
				}
				int cc = files.size();
				String[][] mydata = new String[cc][3];

				for (int i = 0; i < cc; i++) {
					mydata[i][0] = (String) files.elementAt(i);
					mydata[i][1] = (String) fileSizes.elementAt(i);
					mydata[i][2] = (String) fileDates.elementAt(i);
				}
				// ���±��
				tableModel.setDataVector(mydata, columnname);
				ftpFileInfosTable.setModel(tableModel);
			}
		} catch (Exception ee) {
			consoleTextArea.append("Read directory has problem !\n");
			return;
		}
	}

	/**
	 * ת����һ��Ŀ¼
	 */
	void upDirectory() {
		// ����Ѿ��������Ŀ¼���򲻴���
		if (path.equals("/")){
			return;
		}
		try {
			// ��Ŀ¼���Լ�����
			StringTokenizer st = new StringTokenizer(path, "/");
			int count = st.countTokens();

			String s = "";
			for (int i = 0; i < count - 1; i++){
				s += st.nextToken() + "/";
			}
			if (s.length() != 0){
				path = s.substring(0, s.length() - 1);
			} else {
				path = "";
			}
			currentDirTextField.setText(path);
			setTableData();
		} catch (Exception ee) {
			consoleTextArea.append("go to parent directory has problems!");
		}
	}

	/**
	 * ѡ�����е������ǵ��¼�����
	 * @param e
	 */
	void table_mousePressed(MouseEvent e) {
		// ֻ�������������¼�
		if (SwingUtilities.isLeftMouseButton(e)) {
			// �������λ��
			Point point = e.getPoint();
			// �õ�ѡ����к��е�ֵ
			row = ftpFileInfosTable.rowAtPoint(point);
			column = ftpFileInfosTable.columnAtPoint(point);

			try {
				// �ж�������Ĵ���
				int count = e.getClickCount();
				// ���ѡ����ǵ�0�У����ʾѡ������ļ�����Ŀ¼��
				if (column == 0) {
					// ��ø�λ���ϵ��ļ���
					String str = (String) files.elementAt(row);
					// ˫��
					if (count == 2) {
						// ���ѡ����ļ�����"."����Ϊת���Ŀ¼����
						if (str.equals(".")) {
							path = "/";
							setTableData();
							return;
						} else if (str.equals("..")) {
							// ���ѡ����ļ�����".."����ת�����Լ�Ŀ¼
							upDirectory();
							return;
						}
						// ���ѡ����Ŀ¼��������Ŀ¼
						String s = (String) fileTypes.elementAt(row);
						if (s.startsWith("d")) {
							if (path.equals("")){
								path = "/" + str;
							} else {
								path += "/" + str;
							}
							currentDirTextField.setText(path);
							setTableData();
						} else if (s.startsWith("-")) {
							//�����ѡ������ļ���������
							download(str, "");
						}
					}
				}
			} catch (Exception ee) {
				consoleTextArea.append("download or read file has problems!\n");
			}

		}
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
}

class MainFrame_Table_mouseAdapter extends java.awt.event.MouseAdapter {
	MainFrame adaptee;

	MainFrame_Table_mouseAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void mousePressed(MouseEvent e) {
		adaptee.table_mousePressed(e);
	}

}
