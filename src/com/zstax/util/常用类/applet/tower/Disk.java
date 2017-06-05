package com.zstax.util.常用类.applet.tower;

import java.awt.Color;
import java.awt.Graphics;

/**
 * ��ŵ����Ϸ�е���
 */
public class Disk {

	// �̵Ŀ��
	public int width;
	// �̵���ɫ
	public Color color;
	// �̵ı�ǩ
	public String label;
	
	// ���췽��
	public Disk(int width, Color color, String label) {
		this.width = width;
		this.color = color;
		this.label = label;
	}
	/**
	 * ���̣��̵��м�Ϊ���Σ�����ΪԲ��
	 * @param g		����
	 * @param xPos		���������ڵ�X����
	 * @param indexInTower		���������Ĳ�Σ�����µ��̵����Ϊ0
	 */
	public void drawDisk(Graphics g, int xPos, int indexInTower) {

		// ������̾��β������Ͻǵ�����
		int x = xPos - width / 2;
		int y = Constants.PILLAR_BOTTOM_YPOS - (indexInTower + 1)
				* Constants.DISK_HEIGHT;
		// �м仭����
		g.setColor(Color.black);
		g.drawRect(x, y, width - 1, Constants.DISK_HEIGHT - 1);

		// ���߻�Բ������ɫΪ��ɫ����������Բ�������Ͻǵ�����
		int x1 = x - Constants.DISK_CORNER_WIDTH;
		int y1 = y;
		g.drawOval(x1, y1, Constants.DISK_HEIGHT - 1,
						Constants.DISK_HEIGHT - 1);
		// ������ұ�Բ�������Ͻǵ�����
		int x2 = x + width - Constants.DISK_CORNER_WIDTH - 1;
		int y2 = y;
		g.drawOval(x2, y2, Constants.DISK_HEIGHT - 1,
						Constants.DISK_HEIGHT - 1);

		// ���̵���ɫ�������ľ��κ�Բ��
		g.setColor(color);
		g.fillRect(x + 1, y + 1, width - 2, Constants.DISK_HEIGHT - 2);
		g.fillOval(x1 + 1, y1 + 1, Constants.DISK_HEIGHT - 2,
				Constants.DISK_HEIGHT - 2);
		g.fillOval(x2 + 1, y2 + 1, Constants.DISK_HEIGHT - 2,
				Constants.DISK_HEIGHT - 2);
		// ����ɫ�ı�ǩ
		g.setColor(Color.black);
		g.drawString(label, x, y + Constants.DISK_HEIGHT / 2 + 4);
	}
}