package com.zstax.util.常用类.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * ʹ�öԻ���
 * ���ܽ��ܣ��������һ���ı���һ����ť�������ť����һ���Ի���
 * �ڶԻ�����������ַ��������ı�������ʾ��
 */
public class DialogWindow extends JFrame implements ActionListener {

	private SimpleDialog dialog;
	private JTextArea textArea;
	//�ı�������֮��ķָ���
	String lineSeparator;

	public DialogWindow() {
		super("�Ի���ʾ��");

		//���һ�������޸ĵ��ı�������ʾ5��30���ַ������ݡ�
		textArea = new JTextArea(5, 30);
		textArea.setEditable(false);
		getContentPane().add("Center", new JScrollPane(textArea));
		
		//���һ����ť�������ť�����Ի���
		JButton button = new JButton("�������");
		button.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(button);
		getContentPane().add("South", panel);
		//��ȡ�ı�������֮��ķָ��������������ϵͳ������
		lineSeparator = System.getProperty("line.separator");

		//�������岼�ִ�С
		this.pack();
	}

	public void actionPerformed(ActionEvent event) {
		//�����ťʱ��ʾ�Ի���
		if (dialog == null) {
			dialog = new SimpleDialog(this, "����Ի���");
		}
		dialog.setVisible(true);
	}

	/**
	 * ������ݵ��ı���ĺ��棬ÿ�ζ�����һ�С�
	 */
	public void setText(String text) {
		textArea.append(text + lineSeparator);
	}

	public static void main(String args[]) {
		DialogWindow window = new DialogWindow();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

/**
 * �Զ���Ի���
 * �Ի������һ��label��һ���ı����2����ť��
 */
class SimpleDialog extends JDialog implements ActionListener {

	//�ı������������ַ���
	JTextField field;
	//�Ի���ĸ����塣
	DialogWindow parent;
	//��ȷ������ť
	JButton setButton;
	
	/**
	 * ���캯��������Ϊ������ͶԻ���ı���
	 */
	SimpleDialog(JFrame prentFrame, String title) {
		//���ø���Ĺ��캯����
		//������������false��ʾ�������������塣Ϊtrue��ʾ���ܹ�������������
		super(prentFrame, title, false);
		parent = (DialogWindow) prentFrame;

		//���Label�������ı���
		JPanel p1 = new JPanel();
		JLabel label = new JLabel("������Ҫ��ӵ��ı�:");
		p1.add(label);
		field = new JTextField(30);
		field.addActionListener(this);
		p1.add(field);
		getContentPane().add("Center", p1);
		
		//���ȷ����ȡ����ť
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton("ȡ ��");
		cancelButton.addActionListener(this);
		setButton = new JButton("ȷ ��");
		setButton.addActionListener(this);
		p2.add(setButton);
		p2.add(cancelButton);
		getContentPane().add("South", p2);

		//�����Ի��򲼾ִ�С
		pack();
	}
	/**
	 * �¼�����
	 */
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if ((source == setButton)) {
			//���ȷ����ť�����£����ı�����ı���ӵ���������ı�����
			parent.setText(field.getText());
		}
		field.selectAll();
		//���ضԻ���
		setVisible(false);
	}
}