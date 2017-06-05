package com.zstax.util.常用类.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * ��Appletʵ�ֵĴ�����Ϸ��ͬʱ�ж����ĸ���鲻�ϵ����£�
 * ��Ҫ������ĸ�����ϵ���ĸ�����ܵ÷֡�
 */
public class TypingGame extends Applet implements Runnable {

	// ��Ϸ����Ŀ�Ⱥ͸߶�
	int width, height;

	// ��Ϸ�Ļ����ͻ���
	public Image img;
	Graphics off;

	// ���̺�����¼�������
	MouseListener mouseListener = new MyMouseAdapter();
	KeyListener keyListener = new MyKeyAdapter();

	// �Ҽ��ĵ����˵�
	JPopupMenu popupMenu;

	// ��ĸ�����������ϵĲ�����ĸ��
	LetterMaker myLetterMaker;

	// ��һ��е���ĸ�Ĵ���
	int rightTypedSum;
	// ���©������ĸ��
	int omittedSum;
	// ��Ұ���Ĵ���
	int wrongTypedSum;
	// ��ȷ��
	float percent;
	
	// ��Ϸ��ˢ����
	public int FPS;

	// ��Ϸ�ĺ�̨�̺߳���Ϸ������״̬
	public Thread gameThread;
	public static boolean running;

	/**
	 * ��ʼ��Applet
	 */
	public void init() {
		this.setBackground(Color.pink);
		this.setLayout(new FlowLayout());
		// ��Applet������HTML�ж�ȡ����
		FPS = Integer.parseInt(getParameter("FPS"));
		rightTypedSum = omittedSum = wrongTypedSum = 0;
		percent = 0f;
		// ��ȡApplet�Ĵ�С����
		width = this.getWidth();
		height = this.getHeight();
		// ����Ϸ������С����ΪApplet��С
		img = this.createImage(width, height);
		off = img.getGraphics();

		running = false;
		// ������
		addComponents();
	}

	// ΪApplet�������
	private void addComponents() {
		// ΪApplet�������ͼ����¼�������
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);

		// �����Ҽ��ĵ����˵�
		popupMenu = new JPopupMenu();
		// ��ʼ��Ϸ�˵�
		JMenuItem menuItem = new JMenuItem("��ʼ��Ϸ");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightTypedSum = omittedSum = wrongTypedSum = 0;
				running = true;
				start();
			}
		});
		popupMenu.add(menuItem);
		// ������Ϸ�˵�
		menuItem = new JMenuItem("������Ϸ");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				running = false;
			}
		});
		popupMenu.add(menuItem);
		// ������ĸ���ֲ˵�
		popupMenu.addSeparator();
		menuItem = new JMenuItem("������ĸ����");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myLetterMaker.currentLetterNumOnScreen < 9){
					myLetterMaker.currentLetterNumOnScreen++;
				}
				myLetterMaker.refreshLetters();
			}
		});
		popupMenu.add(menuItem);
		// �ӿ������ٶȲ˵�
		menuItem = new JMenuItem("�ӿ������ٶ�");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speedUp();
			}
		});
		popupMenu.add(menuItem);
		popupMenu.addSeparator();
		// ������ĸ����
		menuItem = new JMenuItem("������ĸ����");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myLetterMaker.currentLetterNumOnScreen > 1){
					myLetterMaker.currentLetterNumOnScreen--;
				}
				myLetterMaker.refreshLetters();
			}
		});
		popupMenu.add(menuItem);
		// ���������ٶ�
		menuItem = new JMenuItem("���������ٶ�");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speedDown();
			}
		});
		popupMenu.add(menuItem);
	}

	/**
	 * ����Applet
	 */
	public void start() {
		// �½���Ϸ�߳�
		gameThread = new Thread(this);
		gameThread.start();
		// �½���ĸ������
		myLetterMaker = new LetterMaker(this, off);
		myLetterMaker.refreshLetters();
	}

	public void stop() {
		running = false;
		gameThread = null;
	}

	public void run() {
		while (running) {
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}

	public void paint(Graphics g) {
		int sum;
		int showPercent = 0;
		// ʹ��˫���弼�������Ƚ�ͼ�λ���Image�����ϣ��ٽ�Image��������Ļ�ϡ�
		if (running) {
			//off��������Image����ģ��������������ж�������Image��
			off.setColor(this.getBackground());
			off.fillRect(0, 0, width, height);
			myLetterMaker.paintLetters(g);
			off.setColor(Color.blue);
			// ������ҵĸ���ָ��
			sum = rightTypedSum + wrongTypedSum + omittedSum;
			if (sum == 0) {
				percent = 0f;
				showPercent = 0;
			} else {
				percent = (float) rightTypedSum / sum;
				showPercent = (int) (percent * 100);
			}
			off.drawString("����" + rightTypedSum + "  ���" + wrongTypedSum + "  ©��"
					+ omittedSum + "  ��ȷ��" + showPercent + "%", 200, 200);
			// g��������Applet����Image������Applet�ϡ�
			g.drawImage(img, 0, 0, width, height, this);
		} else {
			off.setColor(this.getBackground());
			off.fillRect(0, 0, width, height);
			off.drawString("����" + rightTypedSum + "  ���" + wrongTypedSum + "  ©��"
					+ omittedSum + "  ��ȷ��" + showPercent + "%", 200, 200);
			g.drawImage(img, 0, 0, width, height, this);
		}
	}

	public void update(Graphics g) {
		this.paint(g);
	}
	/**
	 * ������ĸ�����ٶ�
	 */
	public void speedDown(){
		Letter letter = null;
		for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
			letter = (Letter)myLetterMaker.currentLetters.get(i);
			if (letter.speed > 1){
				letter.speed --;
			}
		}
	}
	/**
	 * �����ĸ�����ٶ�
	 */
	public void speedUp(){
		Letter letter = null;
		for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
			letter = (Letter)myLetterMaker.currentLetters.get(i);
			if (letter.speed < 8){
				letter.speed ++;
			}
		}
	}

	// ����¼�������
	class MyMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		// �����Applet�ϵ�����Ҽ����򵯳��˵�
		public void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// ��ʾ�˵�
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	// �����¼�������
	class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// ��ñ����ļ����ַ�
			char key = e.getKeyChar();
			Letter letter = null;
			for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
				letter = (Letter)myLetterMaker.currentLetters.get(i);
				if ((char) (key - 32) == letter.getChar()) {
					rightTypedSum++;
					myLetterMaker.resetLetter(i);
					return;
				}
			}
			wrongTypedSum++;
		}
	}
}

/**
 * ��Ϸ����ĸ�Ĳ�������
 */
class LetterMaker {
	// ��������Ϸ����ͻ���
	TypingGame game;
	Graphics off;

	// ��Ļ�������ͬʱ��ʾ����ĸ��
	final int MAX_LETTER_NUM = 9;
	// ��ǰ��Ļ�ϵ���ĸ��
	int currentLetterNumOnScreen;
	// ��ŵ�ǰ��Ļ�ϵ���ĸ����
	List currentLetters = new ArrayList();
	// ��ĸ����ɫ
	final Color[] letterColors = { Color.red, Color.green };

	// ��ĸ��X�����ϵĻ�׼���ꡣ����������Xֵ
	int trackXPosition[];
	// �����������
	Random random;
	
	/**
	 * ���췽��
	 * @param game    ��Ϸ����
	 * @param off    ����
	 */
	public LetterMaker(TypingGame game, Graphics off) {
		this.off = off;
		this.game = game;
		initTrack();
		// һ��ʼ����3����ĸ
		currentLetterNumOnScreen = 3;
		random = new Random();
	}

	/**
	 * ��ʼ����ĸ��������
	 */
	private void initTrack(){
		//����ʼ����ĸ��X�����Ϲ��
		trackXPosition = new int[MAX_LETTER_NUM];
		// ��Ҫ����Applet�Ŀ�ȣ����ȵķֲ����
		int width = game.getWidth();
		int interval = width / MAX_LETTER_NUM;
		int trackX = interval/2;
		for (int i = 0; i < MAX_LETTER_NUM; i++) {
			trackXPosition[i] = trackX;
			trackX += interval;
		}
	}
	
	/**
	 * ˢ�µ�ǰ��Ļ�ϵ���ĸ���󣬼���������һ����ĸ����
	 */
	public void refreshLetters(){
		// ������յ�ǰ��Ļ����ĸ����
		currentLetters.clear();
		// �ٴ���currentLetterNumOnScreen���¶���
		for (int i=0; i<currentLetterNumOnScreen; i++){
			// ���뵽currentLetters
			currentLetters.add(createNewLetter(null));
		}
	}
	/**
	 * ����������ĸ������ĸ������ͷ����
	 * @param index
	 */
	public void resetLetter(int index){
		// �������õ���ĸȡ����
		Letter letter = (Letter)currentLetters.remove(index);
		// ���¸�ֵ���ٷŽ�ȥ
		currentLetters.add(index, createNewLetter(letter));
	}
	/**
	 * ����һ�����ظ�������ĸ����
	 * @param letter
	 * @return
	 */
	private Letter createNewLetter(Letter letter){
		int x = trackXPosition[random.nextInt(MAX_LETTER_NUM)];
		int y = random.nextInt(11) - 10;
		int speed = random.nextInt(8) + 1;
		String value = String.valueOf((char) (random.nextInt(26) + 65));
		if (letter == null){
			letter = new Letter(x, y, speed, value);
		} else {
			letter.x = x;
			letter.y = y;
			letter.speed = speed;
			letter.value = value;
		}
		while (currentLetters.contains(letter)){
			value = String.valueOf((char) (random.nextInt(26) + 65));
			letter.value = value;
		}
		return letter;
	}

	/**
	 * �û��ʻ���ĸ��
	 * @param g
	 */
	public void paintLetters(Graphics g) {
		Letter letter = null;
		for (int index = 0; index < currentLetterNumOnScreen; index++) {
			// �����ĸ����
			letter = (Letter)currentLetters.get(index);
			// ����ĸ����
			letter.draw(off, letterColors[random.nextInt(2)]);
			// ÿ��һ����ĸ�������䣬�������ȡ���������ٶ�
			letter.y += letter.speed;
			// ����ĸ����Applet�ײ���ʧʱ
			if (letter.y > game.height){
				// ��ʾ����ĸ��©����
				game.omittedSum++;
				// ����λ���ϵ��½�һ����ĸ��������
				resetLetter(index);
			}
		}
	}
}
/**
 * ��ĸ���󣬶�������ĸ�������ꡢ�����ٶȺ���ĸֵ
 */
class Letter{
	int x;
	int y;
	int speed;
	String value;

	public Letter(int x, int y, int speed, String value){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.value = value;
	}
	
	public void draw(Graphics g, Color bgColor){
		// ���û��ʵ���ɫ
		g.setColor(bgColor);
		// ��һ������3D�������Σ�������ĸд��ȥ
		g.fill3DRect(this.x, this.y, 20, 20, true);
		g.setColor(Color.blue);
		g.drawString(this.value, this.x + 5, this.y + 15);
	}
	// �����ĸ������ַ�
	public char getChar(){
		return this.value.charAt(0);
	}
	
	//Ϊ�˷����ж���ĸ�Ƿ���ֹ������ﶨ��������equals������hashCode������
	public boolean equals(Object obj){
		if (obj instanceof Letter){
			Letter objL = (Letter)obj;
			if (this.value.equals(objL.value)){
				return true;
			}
		}
		return false;
	}
	public int hashCode(){
		return this.value.hashCode();
	}
}