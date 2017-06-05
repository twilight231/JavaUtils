package com.zstax.util.常用类.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * ʱ�ӳ���
 */
public class Clock extends JFrame {
	// ��������ڶ���
	private GregorianCalendar now = new GregorianCalendar();
	// ʱ�ӱ�ǩ�����滭����Բ��ʱ��
	private ClockLabel clockLabel = new ClockLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel weekLabel = new JLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel dateLabel = new JLabel();
	// Ʒ�Ʊ�ǩ
	private JLabel remarkLabel = new JLabel();
	// ʱ���ǩ��ָʾʱ��
	private JLabel timeLabel = new JLabel();
	public Clock() {
		// ��������������
		setTitle("ʱ��");
		setSize(500, 480);
		setLocation(100, 100);
		init();
		setResizable(false);
	}

	private void init() {

		// ��ʼ��Ʒ�Ʊ�ǩ
		remarkLabel.setText("MyClock");
		remarkLabel.setLocation(225, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		remarkLabel.setForeground(Color.darkGray);

		// ��ʼ�����ڱ�ǩ
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.darkGray);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// Ϊ���ڱ�ǩ��ֵ
		int week = now.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:
			weekLabel.setText("SUNDAY");
			break;
		case 2:
			weekLabel.setText("MONDAY");
			break;
		case 3:
			weekLabel.setText("TUESDAY");
			break;
		case 4:
			weekLabel.setText("WEDNESDAY");
			break;
		case 5:
			weekLabel.setText("THURSDAY");
			break;
		case 6:
			weekLabel.setText("FRIDAY");
			break;
		case 7:
			weekLabel.setText("SATURDAY");
			break;
		}

		// ��ʼ�����ڱ�ǩ
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(370, 190);
		dateLabel.setForeground(Color.darkGray);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		// �������ڱ�ǩ��ֵ
		dateLabel.setText("" + now.get(Calendar.DATE));

		// ��ʼ��ʱ���ǩ
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 400);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));

		// ����������뵽��������
		Container con = getContentPane();
		con.setBackground(Color.white);
		con.setLayout(null);
		con.add(weekLabel);
		con.add(dateLabel);
		con.add(remarkLabel);
		con.add(timeLabel);
		con.add(clockLabel);
	}

	public static void main(String[] args) {
		Clock clock = new Clock();
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock.setVisible(true);
	}

	// �Զ���ʱ�ӱ�ǩ����һ��Բ�ε�ʱ��
	class ClockLabel extends JLabel implements Runnable {
		// ʱ�ӱ�ǩ�Ŀ�Ⱥ͸߶�
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// Բ��ʱ�ӵ�X�뾶��Y�뾶
		private final int CIRCLE_X_RADIUS = 150;
		private final int CIRCLE_Y_RADIUS = 155;

		// Բ��ʱ�ӵ�ԭ��
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// Բ��ʱ��ָ��ĳ���
		private final int HOUR_LENGTH = 50;
		private final int MIN_LENGTH = 80;
		private final int SEC_LENGTH = 100;

		// ��ǰʱ�������ĽǶ�
		double arcHour = 0;
		// ��ǰ���������ĽǶ�
		double arcMin = 0;
		// ��ǰ���������ĽǶ�
		double arcSec = 0;

		// ��ɫ��͸���ȣ�
		// ����ɫ��alphaֵΪ1����255ʱ����ʾ��͸��������ֵʱ͸��
		int alpha = 50;
		// ��ʶ��ɫ͸���ȱ仯�ķ���
		// Ϊtrueʱ��ʾԽ��Խ͸����Ϊfalseʱ��ʾ����Խ��͸��
		boolean flag = false;
		// ����ͼƬ��id���ֻ���ʾ����ͼƬʱʹ��
		int imageID = 0;
		// ����ͼƬ��������
		Image img[] = new Image[5];
		// ����ͼƬURL
		URL url[] = new URL[] { ClockLabel.class.getResource("image/1.jpg"),
				ClockLabel.class.getResource("image/2.jpg"),
				ClockLabel.class.getResource("image/3.jpg"),
				ClockLabel.class.getResource("image/4.jpg"),
				ClockLabel.class.getResource("image/5.jpg"),
				ClockLabel.class.getResource("image/6.jpg") };

		// һ�����л�������ͼ�����
		BufferedImage bufferedImage = null;
		int imageSize = 2 * Math.max(CIRCLE_X_RADIUS, CIRCLE_Y_RADIUS);
		// ΪbufferedImage������Graphics����������bufferedImage�ϻ�ͼ
		Graphics bufferedImageGraphics = null;
		// ʱ���̣߳���Ϊʱ��ÿ���Ӷ�Ҫ��һ�Σ��������߳�
		Thread clockThread = null;
		
		// ���췽��
		public ClockLabel() {

			// ����ʱ�ӱ�ǩ�Ĵ�С
			this.setSize(WIDTH, HEIGHT);

			// ��ȡʱ�롢���롢���뵱ǰ�ĽǶ�
			arcHour = now.get(Calendar.HOUR) * 30; // һ��30��
			arcMin = now.get(Calendar.MINUTE) * 6; // һ��6��
			arcSec = now.get(Calendar.SECOND) * 6; // һ��6��

			// ����ͼƬURL����ͼƬ����
			Toolkit tk = this.getToolkit();
			img[0] = tk.createImage(url[0]);
			img[1] = tk.createImage(url[1]);
			img[2] = tk.createImage(url[2]);
			img[3] = tk.createImage(url[3]);
			img[4] = tk.createImage(url[4]);
			try {
				// ʹ��MediaTracker����ͼƬ����
				// MediaTracker ����һ�����ٶ���ý�����״̬��ʵ�ù�����,
				// ý�������԰�����Ƶ������ͼ�񣬵�Ŀǰ��֧��ͼ��.
				MediaTracker mt = new MediaTracker(this);
				mt.addImage(img[0], 0);
				mt.addImage(img[1], 0);
				mt.addImage(img[2], 0);
				mt.addImage(img[3], 0);
				mt.addImage(img[4], 0);
				// ����ý������������е�ͼ��
				mt.waitForAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ����һ���л����Image����
			bufferedImage = new BufferedImage(imageSize, imageSize,
					BufferedImage.TYPE_INT_ARGB);
			// ΪBufferedImage����Graphics2D����
			// �Ժ��ø�Graphics2D���󻭵�ͼ���ử��BufferedImage��
			bufferedImageGraphics = bufferedImage.createGraphics();

			// �����߳�
			clockThread = new Thread(this);
			clockThread.start();
		}

		public void paint(Graphics g1) {
			// Graphics2D�̳�Graphics����Graphics�ṩ���ḻ�ķ���
			Graphics2D g = (Graphics2D) g1;

			/** ***��Բ��ʱ�ӵĿ̶ȣ�ÿ6�ȱ���һ���̶�**** */
			for (int i = 0; i < 360; i = i + 6) {
				// ���û��ʵ���ɫΪ��ɫ
				g.setColor(Color.blue);
				// ���û��ʵĿ��Ϊ2
				g.setStroke(new BasicStroke(2));

				if (i % 90 == 0) {
					// ����0��3��6��9��λ�ã�ʹ��һ�������ɫ�̶�
					g.setStroke(new BasicStroke(5));// ���ʿ��Ϊ5
					// �������յ�һ��ʱ�����ľ��ǵ�
					g.drawLine(CIRCLE_X 
						+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y 
						+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X 
						+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
						+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else if (i % 30 == 0) {
					// ����Ƕȴ���Сʱ��λ�ã����һ�����0��3��6��9��ʱ������ɫ��С�̶�
					g.setColor(Color.red);
					g.drawLine(CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
								CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else {
					// ����λ�þͻ���ɫ��С�̶�
					g.drawLine(CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
								CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				}
			}

			/** ****** ��ʱ�ӵ�ָ�� ******** */
			// ��ʱ��
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// ���û��ʿ�Ⱥ���ɫ
			g.setStroke(new BasicStroke(6));
			g.setColor(new Color(0, 128, 0));
			// ����Graphics2D��draw��������
			g.draw(lh);

			// ������
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 128, 192));
			g.draw(lm);

			// ������
			Line2D.Double ls = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcSec - 90) * Math.PI / 180) * SEC_LENGTH,
					CIRCLE_Y + Math.sin((arcSec - 90) * Math.PI / 180)
							* SEC_LENGTH);
			g.setStroke(new BasicStroke(1));
			// �������ɫ�����ʹ����Ч�����ԡ�
			g.setColor(new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255)));
			g.draw(ls);

			/** **** ��ʱ�ӱ�����������͸������ ******* */
			// ����ʹ��bufferedImageGraphics����ͼ�񣬶�����bufferedImage�ϣ�
			// drawImage�����Ĳ�������ֱ��ǣ�����ͼƬ����Ŀ��λ�õ�һ���ǵ�X��Y���ꡢĿ��λ�õڶ����ǵ�X��Y���ꡢ
			// Դλ�õ�һ���ǵ�X��Y���ꡢԴλ�õڶ����ǵ�X��Y���ꡢͼ�����ı�ʱ��֪ͨ������
			bufferedImageGraphics.drawImage(img[imageID], 0, 0, imageSize,
					imageSize, 0, 0, imageSize + 10, imageSize + 10, this);
			// ������ͼƬ͸����
			for (int j = 0; j < imageSize; j++) {
				for (int i = 0; i < imageSize; i++) {
					// ��ñ���ͼ����(i, j)�������ɫֵ
					int pix = bufferedImage.getRGB(i, j);
					Color c = new Color(pix);
					int r = c.getRed();
					int gr = c.getGreen();
					int b = c.getBlue();
					// ͨ��Color�����alpha��ʹ��ɫ͸����
					int newpix = new Color(r, gr, b, alpha).getRGB();
					// �������ñ���ͼ������ص����ɫ
					bufferedImage.setRGB(i, j, newpix);
				}
			}

			/**���ϻ������������ǻ���bufferedImage�ϵģ�����Ҫ��bufferedImage����ClockLabel**/
			// ����ǰ�û������� Clip ����Բ�����ཻ������ Clip ����Ϊ���õĽ���
			g.clip(new Ellipse2D.Double(95, 45, imageSize, imageSize));
			g.setColor(Color.white);
			// ���û���������bufferedImage
			g.drawImage(bufferedImage, 95, 45, this);
		}

		public void run() {
			try {
				// 
				while (clockThread != null) {
					// ������������ڸ�ʽ
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// ��ʽ����ǰʱ��
					String s = df.format(new Date());
					timeLabel.setText(s);
					// ÿ��һ�ζ�ʱ��ָ��ĽǶȽ��е���
					arcSec += 6;
					arcMin += 0.1;
					arcHour += 0.1 / 60;
					// ���Ƕ���һȦʱ����0
					if (arcSec >= 360) {
						arcSec = 0;
					}
					if (arcMin >= 360) {
						arcMin = 0;
					}
					if (arcHour >= 360) {
						arcHour = 0;
					}
					// ʵ�ֱ���͸���Ƚ���
					// ��ǳ�����������ǳ��
					if (flag) {
						alpha += 10;
						if (alpha == 50) {
							flag = !flag;
						}
					} else {
						alpha -= 10;
						if (alpha == 0) {
							flag = !flag;
							// ��͸���ȱ仯һ���ֻ�ʱ����һ�ű���ͼ
							imageID++;
							if (imageID == 4) {
								imageID = 0;
							}
						}
					}
					// �ػ�ʱ�ӱ�ǩ
					repaint();
					// �ȴ�1����
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}