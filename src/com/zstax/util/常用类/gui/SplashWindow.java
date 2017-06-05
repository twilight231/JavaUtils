package com.zstax.util.常用类.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * ��������ʱ�ֵ�Ӧ�ö���һ����ӭ��Ļ����ӭ��Ļ����������Ʒ�ķ���֮һ��
 * �����ڳ�ʱ���Ӧ�����������У���ӭ��Ļ��������ʾӦ������׼�������С�
 */


/**
 * ������ʵ��һ����ӭ��Ļ��������Ӧ��������������档
 */
public class SplashWindow extends JWindow {
	/**
	 * ���캯��
	 * @param filename	��ӭ��Ļ���õ�ͼƬ
	 * @param frame		��ӭ��Ļ�����Ĵ���
	 * @param waitTime	��ӭ��Ļ��ʾ���¼�
	 */
	public SplashWindow(String filename, JFrame frame, int waitTime) {
		super(frame);
		
		// ����һ����ǩ����ǩ����ʾͼƬ��
		JLabel label = new JLabel(new ImageIcon(filename));
		// ����ǩ���ڻ�ӭ��Ļ�м�
		getContentPane().add(label, BorderLayout.CENTER);
		pack();
		// ��ȡ��Ļ�ķֱ��ʴ�С
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// ��ȡ��ǩ��С
		Dimension labelSize = label.getPreferredSize();
		// ����ӭ��Ļ������Ļ�м�
		setLocation(screenSize.width / 2 - (labelSize.width / 2),
				screenSize.height / 2 - (labelSize.height / 2));
		// ����һ������¼�������������û���������˻�ӭ��Ļ����رա�
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		final int pause = waitTime;
		/**
		 * Swing�߳���ͬһʱ�̽��ܱ�һ���߳������ʡ�һ����˵������߳����¼��ɷ��̣߳�event-dispatching thread���� 
		 * �����Ҫ���¼�����event-handling������ƴ�������ĵط�����UI��
		 * ��ô����ʹ��SwingUtilities���invokeLater()��invokeAndWait()������
		 */
		// �رջ�ӭ��Ļ���߳�
		final Runnable closerRunner = new Runnable() {
			public void run() {
				setVisible(false);
				dispose();
			}
		};
		// �ȴ��رջ�ӭ��Ļ���߳�
		Runnable waitRunner = new Runnable() {
			public void run() {
				try {
					// ����ʾ��waitTime�󣬳��Թرջ�ӭ��Ļ
					Thread.sleep(pause);
					SwingUtilities.invokeAndWait(closerRunner);
//					SwingUtilities.invokeLater(closerRunner);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		setVisible(true);
		//�����ȴ��رջ�ӭ��Ļ���߳�
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		splashThread.start();
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("��ӭ��Ļ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SplashWindow splash = new SplashWindow("C:/temp/scenery.jpg", frame, 60000);
		frame.pack();
		frame.setVisible(true);
	}
}