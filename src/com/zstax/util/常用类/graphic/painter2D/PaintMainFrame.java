package com.zstax.util.常用类.graphic.painter2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 2Dͼ�λ����������
 */
public class PaintMainFrame extends JFrame {
	// ����������壬���û�Բ�����ߵ�����İ�ť
	JPanel commandPanel = new JPanel();
	// �����ɫ����塣
	JPanel colorPanel = new JPanel();
	// ����������ɫ�������
	JPanel mainPanel = new JPanel();
	
	// ��ɫ��ť���к�ɫ����ɫ����ɫ
	JButton btnRed = new JButton("Red");
	JButton btnGreen = new JButton("Green");
	JButton btnBlue = new JButton("Blue");

	// ���ť�����ߡ������Ρ���Բ���������ָ����˳�
	JButton btnLine = new JButton("Line");
	JButton btnRectangle = new JButton("Rectangle");
	JButton btnCircle = new JButton("Circle");
	JButton btnUndo = new JButton("Undo");
	JButton btnRedo = new JButton("Redo");
	JButton btnExit = new JButton("Exit");
	
	// ���壬�ڻ����ϻ�ͼ��
	PaintBoard paintBoard = new PaintBoard();

	// ���췽��
	public PaintMainFrame() {
		// ���ò��ֹ�����
		this.getContentPane().setLayout(new BorderLayout());

		// Ϊ���ť����¼�������
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLine_actionPerformed(e);
			}
		});
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRectangle_actionPerformed(e);
			}
		});
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCircle_actionPerformed(e);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_actionPerformed(e);
			}
		});
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUndo_actionPerformed(e);
			}
		});
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRedo_actionPerformed(e);
			}
		});
		
		// �����������Ĳ��ֹ�����
		commandPanel.setLayout(new FlowLayout());
		// ������ť�����������
		commandPanel.add(btnLine);
		commandPanel.add(btnRectangle);
		commandPanel.add(btnCircle);
		commandPanel.add(btnUndo);
		commandPanel.add(btnRedo);
		commandPanel.add(btnExit);

		// Ϊ��ɫ��ť����¼�������
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRed_actionPerformed(e);
			}
		});
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGreen_actionPerformed(e);
			}
		});
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBlue_actionPerformed(e);
			}
		});

		// ������ɫ���Ĳ��ֹ�����
		colorPanel.setLayout(new FlowLayout());
		// �����ɫ��ť����ɫ���
		colorPanel.add(btnRed, null);
		colorPanel.add(btnGreen, null);
		colorPanel.add(btnBlue, null);

		// �����
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(commandPanel, BorderLayout.NORTH);
		mainPanel.add(colorPanel, BorderLayout.CENTER);

		// �������ͻ���ӵ�JFrame��������
		this.getContentPane().add(mainPanel, BorderLayout.SOUTH);
		this.getContentPane().add(paintBoard, BorderLayout.CENTER);

		// ���ó�ʼ������Ϊ���ߣ�����ѡ�е����ť��ǰ��ɫ�ú�ɫ��ʾ
		btnLine.setForeground(Color.red);
		paintBoard.setCommand(Command.LINE);
		
		// ���ó�ʼ����ɫΪ����ɫ������ѡ�е���ɫ��ť��ǰ��ɫ�ú�ɫ��ʾ
		btnRed.setForeground(Color.red);
		paintBoard.setColor(Color.red);
	}

	/******�¼�����****/
	void btnExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	void btnUndo_actionPerformed(ActionEvent e) {
		paintBoard.undo();
	}

	void btnRedo_actionPerformed(ActionEvent e) {
		paintBoard.redo();
	}
	void btnLine_actionPerformed(ActionEvent e) {
		// ���ѡ���˻��ߣ��򽫻��ߵ����ťǰ��ɫ�ú�ɫ��ʾ
		btnLine.setForeground(Color.red);
		// ������Բ�ͻ����ΰ�ťǰ��ɫ�ú�ɫ��ʾ
		btnRectangle.setForeground(Color.black);
		btnCircle.setForeground(Color.black);
		paintBoard.setCommand(Command.LINE);
	}

	void btnRectangle_actionPerformed(ActionEvent e) {
		btnLine.setForeground(Color.black);
		btnRectangle.setForeground(Color.red);
		btnCircle.setForeground(Color.black);
		paintBoard.setCommand(Command.RECTANGLE);
	}

	void btnCircle_actionPerformed(ActionEvent e) {
		btnLine.setForeground(Color.black);
		btnRectangle.setForeground(Color.black);
		btnCircle.setForeground(Color.red);
		paintBoard.setCommand(Command.CIRCLE);
	}

	void btnRed_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.red);
		btnGreen.setForeground(Color.black);
		btnBlue.setForeground(Color.black);
		paintBoard.setColor(Color.red);
	}

	void btnGreen_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.black);
		btnGreen.setForeground(Color.red);
		btnBlue.setForeground(Color.black);
		paintBoard.setColor(Color.green);
	}

	void btnBlue_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.black);
		btnGreen.setForeground(Color.black);
		btnBlue.setForeground(Color.red);
		paintBoard.setColor(Color.blue);
	}

	public static void main(String[] args) {
		PaintMainFrame paintApp = new PaintMainFrame();
		paintApp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		paintApp.setSize(600, 500);
		paintApp.setTitle("�ҵ�2D����");
		paintApp.setVisible(true);
	}
}
