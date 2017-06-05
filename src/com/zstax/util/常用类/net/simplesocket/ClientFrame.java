package com.zstax.util.常用类.net.simplesocket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * �ͻ��˵�ͼ�ν���
 */
public class ClientFrame extends JFrame implements ActionListener {
	// "����"��ť
	JButton sendButton; 
	// �������ݵ������
	JTextField inputField; 
	// �������������ݵ��ı���
	JTextArea outputArea;

	// �ͻ���socket����
	SimpleClient client;

	// �ڹ��캯�������ͼ�ν���ĳ�ʼ��
	public ClientFrame() {
		JLabel label1 = new JLabel("����: ");
		inputField = new JTextField(20);
		JPanel panel1 = new JPanel();
		panel1.add(label1);
		panel1.add(inputField);

		JLabel label2 = new JLabel("����������: ");
		outputArea = new JTextArea(6, 20); 
		JScrollPane crollPane = new JScrollPane(outputArea);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(label2, BorderLayout.NORTH);
		panel2.add(crollPane, BorderLayout.CENTER);

		sendButton = new JButton("�� ��");
		sendButton.addActionListener(this);
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new BorderLayout()); 
		panel.add(panel1, BorderLayout.NORTH);
		panel.add(sendButton, BorderLayout.CENTER);
		panel.add(panel2, BorderLayout.PAGE_END);

		setTitle("Socket �ͻ���");
		this.getContentPane().add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {
		// �ж��¼�Դ�ؼ��Ƿ���"����"��ť
		if (ae.getSource() == sendButton) {
			try {
				// �����ı����е��ı�
				client.sendRequest(inputField.getText()); 
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// ���շ�������Ӧ��д���ı���
			outputArea.append(client.getResponse() + "\n"); 
		}
	}

	public static void main(String[] args) {
		ClientFrame frame = new ClientFrame();
		frame.pack();
		// ���ӷ�����
		frame.client = new SimpleClient("127.0.0.1", 8888); 
		frame.setVisible(true);

	}

}

