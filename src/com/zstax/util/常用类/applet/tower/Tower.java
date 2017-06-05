package com.zstax.util.常用类.applet.tower;

import java.awt.Color;
import java.awt.Graphics;

/**
 * ����һ����
 */
public class Tower {
	// ����X����
	public int xPos;
	// ���ı�ǩ
	public char label;
	// ��������ܷŵ�����
	public int maxDisks;
	// �����̵�����
	public Disk diskArray[];
	// ���ϵ�ǰ������ 
	public int currentDisksNum;
	
	/**
	 * ����һ������
	 * @param xPos
	 * @param label
	 * @param maxDisks
	 */
	public Tower(int xPos, char label, int maxDisks) {
		this.maxDisks = maxDisks;
		this.xPos = xPos;
		this.label = label;
		// �½������̵�����
		diskArray = new Disk[maxDisks];
		// ��ǰ���ϻ�û���̡�
		currentDisksNum = -1;
	}
	/**
	 * �������Ϸ�һ����
	 * @param disk1
	 */
	public void insertDisk(Disk disk1) {
		diskArray[++currentDisksNum] = disk1;
	}
	/**
	 * ������������һ����
	 * @return
	 */
	public Disk removeDisk() {
		return diskArray[currentDisksNum--];
	}
	/**
	 * ��������ϵ��̣���������������
	 * @return
	 */
	public Disk peekDisk() {
		return diskArray[currentDisksNum];
	}
	/**
	 * �ж������Ƿ�����
	 * @return
	 */
	public boolean isEmpty() {
		return currentDisksNum == -1;
	}

	/**
	 * �ж����ǲ����Ѿ�������
	 * @return
	 */
	public boolean isFull() {
		return currentDisksNum == maxDisks - 1;
	}
	/**
	 * ����
	 * @param g
	 * @param mode	������ģʽ
	 * @param opCode	����ģʽ
	 */
	public void drawTower(Graphics g, int mode, int opCode) {
		// ���ֻ��Ҫ���̵�ģʽ
		if (mode == Constants.ONLY_DISK_DRAWMODE) {
			// ����Ǵ����������̣��������������
			if (opCode == Constants.DELETEFROM_OP) {
				eraseDisk(g, currentDisksNum + 1);
			} else 	if (opCode == Constants.ADDTO_OP) {
				// ������������Ϸ��̣�����������
				diskArray[currentDisksNum].drawDisk(g, xPos, currentDisksNum);
			}
		} else {
			// �����Ҫ���̺�����ģʽ
			
			// �����Ϊ15������
			g.setColor(Color.black);
			// ����������Ͻǵ�����
			int pillarXPos = xPos - Constants.PILLAR_WIDTH/2;
			int pillarYPos = Constants.PILLAR_BOTTOM_YPOS
					- Constants.PILLAR_HEIGHT;
			g.fillRect(pillarXPos, pillarYPos, Constants.PILLAR_WIDTH,
					Constants.PILLAR_HEIGHT);
			g.setColor(Color.white);
			// ������ǩ
			g.drawString(String.valueOf(label), pillarXPos + 4,
					Constants.TOWER_LABEL_YPOS);
			// �����ϵ���
			for (int index = 0; index <= currentDisksNum; index++) {
				if (diskArray[index] == null){
					break;
				}
				diskArray[index].drawDisk(g, xPos, index);
			}
		}
	}

	/**
	 * ������������
	 * @param g
	 * @param index
	 */
	private void eraseDisk(Graphics g, int index) {
		// ��һ����һ��ľ��Σ����̸�ס���̱㿴������
		int x = xPos - Constants.TOWER_X_DISTANCE / 2;
		int y = Constants.PILLAR_BOTTOM_YPOS - (index + 1)
				* Constants.DISK_HEIGHT;
		g.setColor(Color.lightGray);
		g.fillRect(x, y, Constants.TOWER_X_DISTANCE, Constants.DISK_HEIGHT);
		// ��ȥ���ǻ�Ҫ�����Ӳ���
		g.setColor(Color.black);
		int x2 = xPos - Constants.PILLAR_WIDTH / 2;
		g.fillRect(x2, y, Constants.PILLAR_WIDTH, Constants.DISK_HEIGHT);
	}
}