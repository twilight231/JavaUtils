package com.zstax.util.常用类.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;

// ������
public class Rectangle implements Shape {
	// �������Ͻ�λ�õ����꣬x��yֵ
	private int x;
	private int y;
	
	// ���εĳ��Ϳ�
	private int width;
	private int height;
	
	// ���ε���ɫ
	private Color color;

	// ���췽��
	public Rectangle(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	// ������
	public void draw(Graphics g) {
		g.setColor(color);
		// drawRect������
		g.drawRect(x, y, width, height);
	}
}

