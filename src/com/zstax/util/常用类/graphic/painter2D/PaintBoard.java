package com.zstax.util.常用类.graphic.painter2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
/**
 * ���壬�ڻ����ϻ�ͼ�Σ��̳�Canvas��
 * �ṩ�˳����ͻָ��Ĺ���
 */
public class PaintBoard extends Canvas implements java.awt.event.MouseListener,
		java.awt.event.MouseMotionListener {
	// ��¼���������ʼλ�ã�x��y����
	int beginX = 0;
	int beginY = 0;
	// ��¼������ĵ�ǰλ�ã�x��y����
	int currentX = 0;
	int currentY = 0;

	// ��ʾ��ǰ����Ƿ񱻰���
	boolean bMousePressing = false;
	
	// ���浱ǰ�����е�ͼ�Σ��ڳ����ͻָ�ʱʹ��
	java.util.Stack vShapes = new java.util.Stack();
	// �����Ѿ�ɾ������ͼ��
	java.util.Stack vDelShapes = new java.util.Stack();
	// ��¼��ǰ������ǻ�Բ�����ߡ����ǻ�����
	private int command = 0;
	// ���ʵ���ɫ
	private Color color;
	/**
	 * ���췽��
	 */
	public PaintBoard() {
		// ����¼�������
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	/**
	 * ����������ȡ���ոջ���ͼ�Ρ�
	 */
	public void undo() {
		if (vShapes.size() > 0) {
			// ��vShapes�е���󻭵�һ��ͼ��ȡ��
			Object shape = vShapes.pop();
			// ���뵽vDelShapes��
			vDelShapes.push(shape);
			// �ػ�����
			repaint();
		}
	}
	/**
	 * �ָ�������ȡ���ոճ�����ͼ��
	 */
	public void redo() {
		if (vDelShapes.size() > 0) {
			// ��vDelShapes�����һ����ɾ����ͼ��ȡ��
			Object shape = vDelShapes.pop();
			// �ŵ�vShapes��
			vShapes.push(shape);
			// �ػ�����
			repaint();
		}
	}
	/**
	 * ����������ߡ���Բ��������
	 * @param command
	 */
	public void setCommand(int command) {
		this.command = command;
	}
	/**
	 * ���û�����ɫ
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * ��java��repaint()��ͨ������������ʵ��ˢ�¹��ܵģ�����������public void update()��ˢ����Ļ��
	 * ����ٵ���paint(Graphcis g)���ػ���Ļ��������������˸���ر���һЩ��Ҫ�ػ������ĳ���
	 * �����һ��ͼ�������ȫ������һ��ͼ��Ļ����������дupdate����������������˸�� 
	 * public void update(Graphics g){ paint(g) }ͬ������repaint()�ػ���Ļ��
	 * ����ֱ����д������repaint������Graphics g=getGraphics(); paint(g)����ʵ���ػ���Ļ��
	 */
	/**
	 * ��дupdate������������˸��
	 */
	public void update(Graphics g){
		paint(g);
	}
	/**
	 * ������
	 */
	public void paint(Graphics g) {
		/**** ���ջ��� **/
		// ����������������
		Dimension size = getSize();
		int width = size.width;
		int height = size.height;
		// ���û������ɫΪ��ɫ
		g.setColor(Color.white);
		// �ð�ɫ��仭������
		g.fillRect(0, 0, width, height);

		/*** �������ϵ�ͼ�� ***/
		Shape shape = null;
		// һ��ȡ�û����б����ͼ��
		java.util.Enumeration shapes = vShapes.elements();
		while (shapes.hasMoreElements()) {
			shape = (Shape) shapes.nextElement();
			// ����ͼ�ε�draw����
			shape.draw(g);
		}
		
		// �����ǰ��껹û���ɿ�
		if (bMousePressing) {
			// ���û�����ɫ
			g.setColor(color);
			switch (command) {
			case Command.LINE:
				// ���ߣ��������ʼλ�ú͵�ǰλ��֮�仭һ��ֱ��
				g.drawLine(beginX, beginY, currentX, currentY);
				break;
			case Command.RECTANGLE:
				// ������
				if (currentX < beginX) {
					// �����굱ǰλ������ʼλ�õ����Ϸ���������굱ǰλ��Ϊ���ε����Ͻ�λ�á�
					if (currentY < beginY) {
						g.drawRect(currentX, currentY, beginX - currentX, beginY
								- currentY);
					} else {
						// �����굱ǰλ������ʼλ�õ����·���
						// ���Ե�ǰλ�õĺ��������ʼλ�õ�������Ϊ���ε����Ͻ�λ��
						g.drawRect(currentX, beginY, beginX - currentX, currentY
								- beginY);
					}
				} else {
					// �����굱ǰλ������ʼλ�õ����Ϸ���
					// ���������ʼλ�õĺ�����͵�ǰ��������Ϊ���ε����Ͻ�λ��
					if (currentY < beginY) {
						g.drawRect(beginX, currentY, currentX - beginX, beginY
								- currentY);
					} else {
						// �����굱ǰλ������ʼλ�õ����·���������ʼλ��Ϊ���ε����Ͻ�λ��
						g.drawRect(beginX, beginY, currentX - beginX, currentY - beginY);
					}
				}
				break;
			case Command.CIRCLE:
				// ��Բ����ȡ�뾶���뾶����a*a + b*b��ƽ����
				int radius = (int) Math.sqrt((beginX - currentX)
						* (beginX - currentX) + (beginY - currentY)
						* (beginY - currentY));
				// ��Բ��
				g.drawArc(beginX - radius / 2, beginY - radius / 2, radius, radius, 0, 360);
				break;
			}//End switch(command)
		}

	}
	// MouseListener�ӿڶ���ķ�����������굥���¼���
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}
	
	// MouseListener�ӿڶ���ķ�����������갴���¼���
	public void mousePressed(MouseEvent e) {
		// ���������ʼλ��
		beginX = e.getX();
		beginY = e.getY();
		// ����bMousePressingΪ�棬��ʾ��갴���ˡ�
		bMousePressing = true;
	}
	
	//MouseListener�ӿڶ���ķ�������������ɿ��¼���
	public void mouseReleased(MouseEvent e) {
		// ��ȡ��굱ǰλ��
		currentX = e.getX();
		currentY = e.getY();
		// ��������Ѿ��ɿ��ˡ�
		bMousePressing = false;
		
		// �ɿ����ʱ�����ոջ���ͼ�α��浽vShapes��
		switch (command) {
		case Command.LINE:
			// �½�ͼ��
			Line line = new Line(beginX, beginY, currentX, currentY, color);
			vShapes.push(line);
			break;
		case Command.RECTANGLE:
			Rectangle rectangle = null;
			if (currentX < beginX) {
				if (currentY < beginY) {
					rectangle = new Rectangle(currentX, currentY,
							beginX - currentX, beginY - currentY, color);
				} else {
					rectangle = new Rectangle(currentX, beginY, beginX - currentX,
							currentY - beginY, color);
				}
			} else {
				if (currentY < beginY) {
					rectangle = new Rectangle(beginX, currentY, currentX - beginX,
							beginY - currentY, color);
				} else {
					rectangle = new Rectangle(beginX, beginY, currentX - beginX,
							currentY - beginY, color);
				}
			}
			vShapes.push(rectangle);
			break;
		case Command.CIRCLE:
			int radius = (int) Math.sqrt((beginX - currentX)
					* (beginX - currentX) + (beginY - currentY) * (beginY - currentY));
			Circle circle = new Circle(beginX - radius / 2, beginY - radius / 2, radius,
					color);
			vShapes.push(circle);
			break;
		} //End switch(command)
		
		//�ػ�����
		repaint();
	}
	
	//MouseListener�ӿڶ���ķ�����������������¼���
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}
	
	//MouseListener�ӿڶ���ķ�������������Ƴ��¼���
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
	
	//MouseListener�ӿڶ���ķ�����������갴ס�����϶��¼���
	public void mouseDragged(MouseEvent e) {
		// ��ס����϶�ʱ�����ϵĻ�ȡ��굱ǰλ�ã����ػ�����
		currentX = e.getX();
		currentY = e.getY();
		repaint();
	}
	
	//MouseListener�ӿڶ���ķ�������������ƶ��¼���
	public void mouseMoved(MouseEvent e) {
		// do nothing
	}
}