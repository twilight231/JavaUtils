package com.zstax.util.常用类.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ��������Ϸ����������Ϸ�����е�һ�������ΰ�ťʱ��
 * ����Χ�������ΰ�ť����ɫ�ͻᷢ���仯����ȫ�������ΰ�ť����ɫ��Ϊһɫʱ�����ʤ����
 */
public class OpenWindows {
	public static void main(String[] args) {
		JFrame frame = new JFrame("��������Ϸ");
		frame.getContentPane().add(new MainPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

/**
 * �����
 */
class MainPanel extends JPanel {
	SquarePanel pc = new SquarePanel();
	ControlPanel ps = new ControlPanel(pc);
	public MainPanel() {
		this.setLayout(new BorderLayout());
		this.add(pc, "Center");
		this.add(ps, "South");
	}
}

/**
 * ��������
 */
class SquarePanel extends JPanel {
	//����25��������ť
	JButton[] winbutton = new JButton[25];
	Color c;
	public SquarePanel() {
		//���������񲼾ֹ�����
		this.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < 25; i++) {
			winbutton[i] = new JButton();
			winbutton[i].setActionCommand(String.valueOf(i));
			// ���Ĭ����ɫ
			c = winbutton[i].getBackground(); 
			winbutton[i].addActionListener(new OpenWindowListener());
			this.add(winbutton[i]);
		}
		//����СֵΪ300*300
		this.setPreferredSize(new Dimension(300, 300));
	}

	/**
	 * ����һ��������ťʱ���𿪴����¼�
	 */
	class OpenWindowListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			//��ȡ�����������ID
			int x = Integer.parseInt(a.getActionCommand());
			//���ñ����������ѡ��
			select(x);
			//�ж��Ƿ�ʤ��
			isWin();
		}

		// ��һ��������ѡ��ʱ���еĲ�������Ҫ�ı���Χ��������ɫ��
		private void select(int x) {
			if (x == 0) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x > 0 && x < 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x == 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 5]);
			} else if (x == 20) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x + 1]);
			} else if (x == 24) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
			} else if (x > 20 && x < 24) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
			} else if (x % 5 == 0) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x % 5 == 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 5]);
			} else {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			}
		}

		// �ı���Χ��ɫ������
		private void changeColor(JButton winbutton) {
			//�����������ɫ�ǳ�ʼ��ɫ�����ɰ�ɫ��
			if (winbutton.getBackground() == c){
				winbutton.setBackground(Color.white);
			} else {
				//�����������ɫ���ǳ�ʼ��ɫ�����ɳ�ʼ��ɫ
				winbutton.setBackground(c);
			}
		}

		// �ж��Ƿ�ʤ��
		private void isWin() {
			int a = 1;
			//��25����������ɰ�ɫʱ����ʤ
			for (int i = 0; i < 25; i++){
				if (winbutton[i].getBackground() == Color.white){
					a++;
				}
			}
			if (a > 25){
				JOptionPane.showMessageDialog(null, "��ϲ����");
			}
		}
	}
}

/**
 * �������
 */
class ControlPanel extends JPanel {
	JLabel label = new JLabel("��������Ϸ");
	//��Ϸ���¿�ʼ��ť
	JButton restart = new JButton("����");
	SquarePanel pc;
	/**
	 * ���캯��������Ϊ�����ƵĴ������
	 */
	public ControlPanel(SquarePanel pc) {
		this.pc = pc;
		restart.addActionListener(new Reset());
		this.add(label);
		this.add(restart);
	}

	/**
	 * ���贰����壬�����д�����ɳ�ʼ��ɫ
	 */
	class Reset implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			for (int i = 0; i < 25; i++) {
				pc.winbutton[i].setBackground(pc.c);
			}
		}
	}
}
