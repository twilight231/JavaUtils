package com.zstax.util.常用类.applet;

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
/**
 * ��ʵ������Applet�е����ͼ��̲���
 */
public class MouseAndKeyApplet extends Applet {

	// ����ڽ��и��ֲ���ʱ����Ϣ
	String mouseDownStr = null;
	String mouseUpStr = null;
	String mouseDragStr = null;

	// �����ڽ��и��ֲ���ʱ����Ϣ
	String keyUpStr = null;
	String keyDownStr = null;
	String keyboardStateStr = null;

	// ���ΰ�ť������ͳ��ȡ����
	int button_X;
	int button_Y;
	int buttonHeight;
	int buttonWidth;

	//��ʼ��
	public void init() {
		button_X = 100;
		button_Y = 80;
		buttonHeight = 35;
		buttonWidth = 60;
		
		clearInfos();
	}
	
	public void start(){
	}
	public void stop(){
	}
	public void destroy(){
	}
	// ��ո�����Ϣ
	public void clearInfos(){
		mouseDownStr = "";
		mouseUpStr = "";
		mouseDragStr = "";
		keyDownStr = "";
		keyUpStr = "";
		keyboardStateStr = "";
	}
	
	public void paint(Graphics g) {
		// ��Applet�ϻ��ַ���
		g.drawString("MouseInfo", 20, 20);
		g.drawString("KeyInfo", 200, 20);
		if (mouseDownStr != null){
			g.drawString(mouseDownStr, 20, 50);
		}
		if (mouseUpStr != null){
			g.drawString(mouseUpStr, 20, 50);
		}
		if (mouseDragStr != null){
			g.drawString(mouseDragStr, 20, 50);
		}
		if (keyUpStr != null){
			g.drawString(keyUpStr, 200, 50);
		}
		if (keyDownStr != null){
			g.drawString(keyDownStr, 200, 50);
		}

		clearInfos();

		// ��һ�����ΰ�ť
		g.drawRect(button_X, button_Y, buttonWidth, buttonHeight);
		// �������ϵı�ǩ
		g.drawString("EXIT", button_X + 15, button_Y + 20);
	}

	/**
	 * ����ɿ��¼���x��yΪ��������
	 */
	public boolean mouseUp(Event event, int x, int y) {
		mouseUpStr = "mouseUp: " + x + ", " + y;
		repaint();
		// �ж����λ���Ƿ��ڰ�ť��
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseUpStr = "EXIT Button Selected";
			repaint();
		}
		return true;
	}

	/**
	 * ��갴���¼�
	 */
	public boolean mouseDown(Event event, int x, int y) {
		mouseDownStr = "mouseDown: " + x + ", " + y;
		repaint();
		// �ж����λ���Ƿ��ڰ�ť��
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseDownStr = "EXIT Button Selected";
			repaint();
		}
		return true;
	}

	/**
	 * ����϶��¼�
	 */
	public boolean mouseDrag(Event event, int x, int y) {
		mouseDragStr = "mouseDrag: " + x + ", " + y;
		repaint();
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseDragStr = "EXIT Button Selected";
			repaint();
		}

		return true;
	}

	/**
	 * �����ɿ��¼�
	 */
	public boolean keyUp(Event event, int letter) {
		// �ж���ϼ�
		if ((event.modifiers & Event.SHIFT_MASK) != 0){
			keyboardStateStr += "Shift ";
		}
		if ((event.modifiers & Event.CTRL_MASK) != 0){
			keyboardStateStr += "Ctrl ";
		}
		if ((event.modifiers & Event.ALT_MASK) != 0){
			keyboardStateStr += "Alt ";
		}
		// �жϹ��ܼ�
		if (event.id == Event.KEY_ACTION) {//has problem in this line?
			keyUpStr = "Function key released";
		}else {
			// ��ͨ��
			if (letter == 27){ //ESC key
				keyUpStr = keyboardStateStr + "Esc key released";
			} else {
				keyUpStr = "KeyUp: " + keyboardStateStr + (char) letter;
			}
		}
		repaint();
		return true;
	}

	/**
	 * ���̰����¼�
	 */
	public boolean keyDown(Event event, int letter) {
		if (event.id == Event.KEY_ACTION){
			keyDownStr = "Function key pressed";
		} else {
			if (letter == 27){ //ESC key
				keyDownStr = "Esc key pressed";
			} else {
				keyDownStr = "KeyDown: " + (char) letter;
			}
		}
		repaint();
		return true;
	}
}