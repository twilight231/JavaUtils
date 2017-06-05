package com.zstax.util.常用类.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * ����һ��Բ�εİ�ťʱ����Ҫ�������£�
 * ��һ����������һ���ʵ��Ļ滭�����Ի���һ��Բ�Ρ�
 * �ڶ�����������һЩ�¼�ʹ��ֻ�е�����Բ�ΰ�ť�ķ�Χ�е�ʱ�ť�Ż�������Ӧ
 */
public class CircleButton extends JButton {
	// ���캯��
	public CircleButton(String label) {
		super(label);

		// ��ȡ��ť����Ѵ�С
		Dimension size = getPreferredSize();
		// ������ť��С��ʹ֮���һ�����Ρ�
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		//ʹJButton����������������ʾ���α��������������ǻ�һ��Բ�ı�����
		setContentAreaFilled(false);
	}

	// ��Բ�İ�ť�ı����ͱ�ǩ
	protected void paintComponent(Graphics g) {
		// getModel������������ģ��ButtonModel��
		// �����갴�°�ť����ButtonModel��armed����Ϊ��
		if (getModel().isArmed()) {
			// �����ťʱ����lightGray��ɫ��ʾ��ť
			g.setColor(Color.lightGray);
		} else {
			// �����¼���Ĭ�ϵı���ɫ��ʾ��ť
			g.setColor(getBackground());
		}
		// fillOval������һ�����ε�������Բ������ʹ����������Բ��
		// ������Ϊ������ʱ����������Բ����Բ
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		// ���ø����paintComponent����ť�ı�ǩ�ͽ������ڵ�С���Ρ�
		super.paintComponent(g);
	}

	//�ü򵥵Ļ�����ť�ı߽硣
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		// drawOval���������ε�������Բ��������䡣ֻ����һ���߽�
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	//shape�������ڱ��水ť����״�����������������ť�¼�
	Shape shape;
	
	// �ж�����Ƿ���ڰ�ť��
	public boolean contains(int x, int y) {
		//�����ť�߿�λ�÷����ı䣬�����һ���µ���״����
		if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
			// ����һ����Բ�ζ���
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		// �ж�����x��y�����Ƿ����ڰ�ť��״�ڡ�
		return shape.contains(x, y);
	}

	public static void main(String[] args) {
		// ����һ��Բ�ΰ�ť��    
		JButton button = new CircleButton("Click me");
		// ���ñ�����ɫΪ��ɫ
		button.setBackground(Color.green);

		// ����һ���������ʾ�����ť��
		JFrame frame = new JFrame("Բ�ΰ�ť");
		frame.getContentPane().setBackground(Color.yellow);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(button);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * ����������Ҫ��һ��Բ�ı���������ͨ������paintComponent()����ʵ�ֵġ�
	 * ʹ��Graphics.fillOval()������һ��ʵ�ĵ�Բ��
	 * Ȼ��paintComponent()��������super.paintComponent()�����ʵ��Բ�����滭��һ����ǩ��
	 * 
	 * ������ӻ�������paintBorder()��������Բ�ΰ�ť�ı߽��ϻ�һ���ߡ�
	 * ����㲻��Ҫ�߿���Ҳ���Բ��������������
	 * �������������Graphics.drawOval()��������Բ�ı߽��ϻ�һ��ϸ�ı߿�
	 */
}