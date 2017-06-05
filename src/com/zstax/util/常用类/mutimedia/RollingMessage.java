package com.zstax.util.常用类.mutimedia;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * ������Ϣ����˫���弼��
 */
public class RollingMessage extends Canvas implements Runnable {

	//ʹ��˫���弼����صı���
	Image offScreenImg; //��ű�����Ļ������
	Graphics offScreenG; //������Ļ�Ļ�ͼ�����Ļ���

	// ��ǰ�̣߳��Լ��Ƿ���Ҫֹͣ�̵߳ı�־λ
	Thread runThread;
	static boolean toStop;
	//ÿ֡�����ʱ�ӣ����룩	
	int delay; 
	
	//Ҫ��ʾ���ַ������䳤��
	String msg; 
	int msgLength; 
	//��ǰ��ʾ���ڼ����ַ�
	int x_character = 0; 

	//��Ϣ��ʾ������
	Font wordFont;
	
	int width, height;

	public RollingMessage(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	public void init() {
		wordFont = new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15);
		msg = "Welcome to Java world!";
		msgLength = msg.length();

		delay = 300;
		toStop = false;
		runThread = new Thread(this);
		runThread.start();
	}

	public void run() {
		while (!toStop) {
			if (x_character++ >= msgLength) {
				x_character = 0;
				// ����Ҫ���»�ʱ����������Ļ�Ļ�ͼ������Ϊnull
				offScreenG = null;
			}
			repaint();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}

	public void paint(Graphics g) {
		g.setFont(wordFont);
		g.setColor(Color.red);
		g.drawString(msg.substring(0, x_character), 10, height/2);
	}

	public void update(Graphics g) {
		// �������˫���弼�������Բ�������ļ���
		//		if (x_character == 0) {
		//			g.setColor(getBackground());
		//			g.fillRect(0, 0, 200, 100);
		//			g.setColor(getForeground());
		//		}
		//		paint(g);

		/**
		 * ����б�����Ļ���ͽ�������Ļ�Ļ�ͼ�����Ļ���offScreenG���ݸ� paint()������
		 * paint()���������������ݶ������ڱ�����Ļ�ϡ�
		 * Ȼ���ٵ��� drawImage()������������ĻoffScreenImg�е����ݻ�����ǰ��Ļ�ϡ�
		 */
		if (offScreenG != null) {
			//�ڱ�����Ļ�ϻ�
			paint(offScreenG);
			g.drawImage(offScreenImg, 0, 0, this);
		} else {
			// �ڵ�ǰ��Ļ�ϻ�
			paint(g);
			/**
			 * ����������Ļ
			 * 
			 * createImage��������������һ���յĿ�����������л�ͼ��Image����
			 * �����������Ͳ����ֱ��ʾ�������ĸ�Image����Ŀ�Ⱥ͸߶ȣ�����
			 * ����Ϊ��������Ŀ�͸ߣ��Ա㽫����Ϊһ��������Ļ��
			 */
			offScreenImg = createImage(width, height);//����������Ļ
			/**
			 * ����Image���� �е�getGraphics( )����������������ȡһ�������ڸ�Image�����Ͻ��л�ͼ�Ļ�ͼ����
			 * �Ļ���,���ǵ���offScreenG�е��κλ�ͼ ��������drawImage(
			 * )���������������ڱ�����Ļ��offScreenImg����
			 */
			offScreenG = offScreenImg.getGraphics(); //��ȡ������Ļ�Ļ�ͼ�����Ļ���
		}

	}

	public static void toStop() {
		toStop = true;
	}

	public static void main(String[] args) {
		int width = 200;
		int height = 100;
		RollingMessage test = new RollingMessage(width, height);
		JFrame frame = new JFrame("��������Ϣ");

		frame.setLocation(0, 0);
		frame.setSize(width, height);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				RollingMessage.toStop();
				System.exit(0);
			}
		});
		frame.getContentPane().add(test);
		frame.setVisible(true);
	}
}

