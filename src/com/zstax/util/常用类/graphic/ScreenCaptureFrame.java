package com.zstax.util.常用类.graphic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * ������Ļ���൱��Windows��PrintScreen����
 * ������
 */
public class ScreenCaptureFrame extends JFrame implements ActionListener {

	// ������Ļ�Ĺ����࣬��һ���Զ����ࡣ
	private ScreenCaptureUtil scrCaptureUtil = null;
	// �������Զ����࣬���ڻ����񵽵���Ļͼ��
	private PaintCanvas canvas = null;

	// ���캯��
	public ScreenCaptureFrame() {
		super("Screen Capture");
		init();
	}
	// ��ʼ������
	private void init()	{
		// �½�ץ������
		scrCaptureUtil = new ScreenCaptureUtil();
		// �½�����
		canvas = new PaintCanvas(scrCaptureUtil);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(canvas, BorderLayout.CENTER);
		// ���һ����ť�����ڴ���ץ���¼�
		JButton capButton = new JButton("ץ ��");
		c.add(capButton, BorderLayout.SOUTH);
		capButton.addActionListener(this);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// �����ץ������ťʱ���ڻ����ϻ���Ļͼ��
	public void actionPerformed(ActionEvent e) {
		canvas.drawScreen();
	}

	public static void main(String[] args) {
		new ScreenCaptureFrame();
	}
}

/**
 * ץ��������
 */
class ScreenCaptureUtil {
	// ץ������Ҫ������
	private Robot robot = null;
	// ��Ļ�ľ���ͼ��
	private Rectangle scrRect = null;

	// ���캯��
	public ScreenCaptureUtil() {
		try {
			// �½�һ��ץ������
			robot = new Robot();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ȡ��Ļ�ľ���ͼ��
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		scrRect = new Rectangle(0, 0, scrSize.width, scrSize.height);
	}
	
	// ץ������������һ��ͼ��
	public BufferedImage captureScreen() {
		BufferedImage scrImg = null;
		try {
			// robot��createScreenCapture����ʵ��ץ��
			// ����ָ����Ҫץ���ķ�Χ������scrRect��������Ļ�ľ��Σ����ԣ�����ץ����ȫ��ͼ
			// ��ץȡ����Ļͼ�񷵻�
			scrImg = robot.createScreenCapture(scrRect);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return scrImg;
	}
}
/**
 * �����࣬������ʾץ���õ���ͼ��
 */
class PaintCanvas extends JPanel {
	// ץ������
	private ScreenCaptureUtil scrCaptureUtil = null;
	// ������ͼ��
	private BufferedImage scrImg = null;
	
	//���췽��������ץ�����߶���
	public PaintCanvas(ScreenCaptureUtil screenUtil) {
		this.scrCaptureUtil = screenUtil;
	}
	// ����JPanel��paintComponent�����ڻ�����
	protected void paintComponent(Graphics g) {
		if (scrImg != null) {
			int iWidth = this.getWidth();
			int iHeight = this.getHeight();
			g.drawImage(scrImg, 0, 0, iWidth, iHeight, 0, 0, scrImg.getWidth(),
					scrImg.getHeight(), null);
		}
	}
	// ����Ļͼ��ķ���
	public void drawScreen() {
		Graphics g = this.getGraphics();
		// ץ������ȡ��Ļͼ��
		scrImg = scrCaptureUtil.captureScreen();
		if (scrImg != null) {
			// ��ͼ
			this.paintComponent(g);
		}
		// �ͷ���Դ
		g.dispose();
	}

}