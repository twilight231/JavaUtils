package com.zstax.util.常用类.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ��Բ
 */
public class Circle implements Shape {
	// Բ����ɫ
	private Color color;
	// Բ��λ�ã�x��y����
	private int x;
	private int y;
	// Բ�İ뾶
	private int radius;

	// ���췽��
	public Circle(int x, int y, int radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	// ��Բ
	public void draw(Graphics g) {
		// ���û�����ɫ
		g.setColor(color);
		// drawArc�����������Ŀ�Ⱥ͸߶�һ�������һ��ȴ�0��360��ʱ������Բ�ˡ�
		g.drawArc(x, y, radius, radius, 0, 360);
	}
}