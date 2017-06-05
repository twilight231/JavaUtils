package com.zstax.util.常用类.applet.tower;


public class Constants {

	public static final int INIT_DISK_NUMS = 4;
	/***������Ϸ������Ŀ�Ⱥ͸߶�***/
	public static final int GAME_WIDTH = 440;
	public static final int GAME_HEIGHT = 325;
	
	/***��Ϸ�ϲ����²��ĸ߶�***/
	public static final int GAME_BOTTOM_HEIGHT = 25;
	public static final int GAME_TOP_HEIGHT = 71;
	
	/***�м���Ϸ��������ꡢ��ȡ��߶� ***/
	public static final int GAME_DISTRICT_XPOS = 2;
	public static final int GAME_DISTRICT_YPOS = GAME_TOP_HEIGHT;
	public static final int GAME_DISTRICT_WIDTH = GAME_WIDTH 
			- 2	* GAME_DISTRICT_XPOS;
	public static final int GAME_DISTRICT_HEIGHT = GAME_HEIGHT
			- GAME_BOTTOM_HEIGHT - GAME_DISTRICT_YPOS;
	
	/***������ģʽ**/
	// ONLY_DISK_DRAWMODE��ʾ����ֻ��Ҫ����
	public static final int ONLY_DISK_DRAWMODE = 1;
	// ALL_DRAWMODE��ʾ��Ҫ�������Ӻ���
	public static final int ALL_DRAWMODE = 2;
	
	/***���̵Ĳ���ģʽ***/
	// DELETEFROM_OP��ʾ�������ϵ���
	public static final int DELETEFROM_OP = 1;
	// ADDTO_OP��ʾ��������
	public static final int ADDTO_OP = 2;
	
	/***�̵ĸ߶Ⱥ������***/
	public static final int DISK_HEIGHT = 20;
	public static final int DISK_MAX_WIDTH = 120;
	public static final int DISK_CORNER_WIDTH = 10;
	
	// ��֮����X�����ϵľ���
	public static final int TOWER_X_DISTANCE = 140;
	// ��һ������X����
	public static final int TOWER_FIRST_XPOS = 80;
	
	/***�����Ŀ�Ⱥ͸߶ȣ��Լ����׵�Y����***/
	public static final int PILLAR_WIDTH = 15;
	public static final int PILLAR_HEIGHT = 220;
	public static final int PILLAR_BOTTOM_YPOS = GAME_HEIGHT
			- GAME_BOTTOM_HEIGHT;
	
	// ����ǩ��Y����
	public static final int TOWER_LABEL_YPOS = 95;
}
