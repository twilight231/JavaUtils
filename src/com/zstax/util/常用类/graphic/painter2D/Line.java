package com.zstax.util.常用类.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ����
 */
public class Line implements Shape {
	// �ߵ����λ�ã�x��y����
	private int x1;
	private int y1;
	
	// �ߵ��յ�λ�ã�x��y����
	private int x2;
	private int y2;

	// �ߵ���ɫ
	private Color color;
	
	// ���췽��
	public Line(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	// ��ֱ��
	public void draw(Graphics g) {
		// ���û�����ɫ
		g.setColor(color);
		// drawLine��������
		g.drawLine(x1, y1, x2, y2);
	}
}