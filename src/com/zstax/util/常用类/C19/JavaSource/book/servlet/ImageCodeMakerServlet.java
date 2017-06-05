package com.zstax.util.常用类.C19.JavaSource.book.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����ͼ����֤���Servlet
 */
public class ImageCodeMakerServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("�Ͷ����������");
		// ��������ҳ�治����
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		// ����ͼƬ�Ŀ�Ⱥ͸߶�
		int width = 90, height = 40;
		// ����һ��ͼ�����
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// �õ�ͼ��Ļ�������
		Graphics g = image.createGraphics();

		Random random = new Random();
		// �������ɫ���ͼ�񱳾�
		g.setColor(getRandColor(180, 250));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < 5; i++) {
			g.setColor(getRandColor(50, 100));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawOval(x, y, 4, 4);
		}
		// �������壬����׼���������
		g.setFont(new Font("", Font.PLAIN, 40));
		// ������ַ���
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			// �����ĸ������ַ�
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// ���������ɫ
			g.setColor(new Color(20 + random.nextInt(80), 20 + random
					.nextInt(100), 20 + random.nextInt(90)));
			// ��������ֻ���ͼ����
			g.drawString(rand, (17 + random.nextInt(3)) * i + 8, 34);

			// ���ɸ�����
			for (int k = 0; k < 12; k++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(9);
				int yl = random.nextInt(9);
				g.drawLine(x, y, x + xl, y + yl);
			}
		}

		// �����ɵ���������ַ���д��Session
		req.getSession().setAttribute("randcode", sRand);
		// ʹͼ����Ч
		g.dispose();
		// ���ͼ��ҳ��
		ImageIO.write(image, "JPEG", resp.getOutputStream());
	}

	/**
	 * ����һ���������ɫ
	 * @param fc	��ɫ������Сֵ
	 * @param bc	��ɫ�������ֵ
	 * @return
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255){
			fc = 255;
		}
		if (bc > 255){
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
