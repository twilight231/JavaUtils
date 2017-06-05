package com.zstax.util.常用类.graphic.jpg;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ����ʵ��һ���� JPG/JPEG ͼ���ļ��������Ŵ���ķ��� ������һ�� JPG �ļ�����������һ���� JPG �ļ�����Ӱͼ���ļ� (JPG ��ʽ )
 * �ṩ����������Ӱͼ��ķ����� 1 ��������Ӱ�ļ��Ŀ�ȣ��������õĿ�Ⱥ�Դͼ���ļ��Ĵ�С��ȷ������Ӱ�ļ��ĳ�����������Ӱͼ�� 2
 * ��������Ӱ�ļ��ĳ��ȣ��������õĳ��Ⱥ�Դͼ���ļ��Ĵ�С��ȷ������Ӱ�ļ��Ŀ����������Ӱͼ�� 3
 * ��������Ӱ�ļ������Դͼ���ļ��ı�����С������Դͼ���ļ��Ĵ�С�����õı�����ȷ������Ӱ�ļ��Ĵ�С��������Ӱͼ��
 * �����ɵ���Ӱͼ����Ա�ԭͼ�����ʱ���ǷŴ�Դͼ��
 */
public class JPGTransformer {
	//	 �����Ƿ񼺾���ʼ��
	private boolean isInitFlag = false;

	//	 ������Ŀ��ͼƬ�Ŀ�Ⱥ͸߶ȣ�����һ���Ϳ�����
	private int targetPicWidth = 0;
	private int targetPicHeight = 0;

	//	 ����Ŀ��ͼƬ�����ԭͼƬ�ı���
	private double picScale = 0;

	/**
	 * ���캯��
	 */
	public JPGTransformer() {
		this.isInitFlag = false;
	}
	/**
	 * ����JPGͼƬ������
	 */
	public void resetJPGTransformer() {
		this.picScale = 0;
		this.targetPicWidth = 0;
		this.targetPicHeight = 0;
		this.isInitFlag = false;
	}

	/**
	 * ����Ŀ��ͼƬ�����ԴͼƬ�����ű���
	 * @param scale
	 * @throws JPGException
	 */
	public void setPicScale(double scale) throws JPGException {
		if (scale <= 0) {
			throw new JPGException(" ���ű�������Ϊ0�͸����� ");
		}

		this.resetJPGTransformer();
		this.picScale = scale;
		this.isInitFlag = true;
	}

	/**
	 * ����Ŀ��ͼƬ�Ŀ��
	 * @param width
	 * @throws JPGException
	 */
	public void SetSmallWidth(int width) throws JPGException {
		if (width <= 0) {
			throw new JPGException(" ��ӰͼƬ�Ŀ�Ȳ���Ϊ 0 �͸����� ");
		}

		this.resetJPGTransformer();
		this.targetPicWidth = width;
		this.isInitFlag = true;
	}
	
	/**
	 * ����Ŀ��ͼƬ�ĸ߶�
	 * @param height
	 * @throws JPGException
	 */
	public void SetSmallHeight(int height) throws JPGException {
		if (height <= 0) {
			throw new JPGException(" ��ӰͼƬ�ĸ߶Ȳ���Ϊ 0 �͸����� ");
		}

		this.resetJPGTransformer();
		this.targetPicHeight = height;
		this.isInitFlag = true;
	}
	
	/**
	 * ��ʼ����ͼƬ
	 * @param srcPicFileName		ԴͼƬ���ļ���
	 * @param targetPicFileName		����Ŀ��ͼƬ���ļ���
	 * @throws JPGException
	 */
	public void transform(String srcPicFileName,
			String targetPicFileName) throws JPGException {

		if (!this.isInitFlag) {
			throw new JPGException(" �������û�г�ʼ���� ");
		}
		if (srcPicFileName == null || targetPicFileName == null) {
			throw new JPGException(" �����ļ�����·��Ϊ�գ� ");
		}
		if ((!srcPicFileName.toLowerCase().endsWith("jpg"))
				&& (!srcPicFileName.toLowerCase().endsWith("jpeg"))) {
			throw new JPGException(" ֻ�ܴ��� JPG/JPEG �ļ��� ");
		}
		if ((!targetPicFileName.toLowerCase().endsWith("jpg"))
				&& !targetPicFileName.toLowerCase().endsWith("jpeg")) {
			throw new JPGException(" ֻ�ܴ��� JPG/JPEG �ļ��� ");
		}

		//	 �½�ԴͼƬ������ͼƬ���ļ�����
		File fin = new File(srcPicFileName);
		File fout = new File(targetPicFileName);

		//	 ͨ���������ԴͼƬ�ļ�
		BufferedImage bSrc = null;
		try {
			// ��ȡ�ļ�����BufferedImage
			bSrc = ImageIO.read(fin);
		} catch (IOException ex) {
			throw new JPGException(" ��ȡԴͼ���ļ����� ");
		}
		//   ԴͼƬ�Ŀ�Ⱥ͸߶�
		int srcW = bSrc.getWidth();
		int srcH = bSrc.getHeight();

		//   ����Ŀ��ͼƬ��ʵ�ʿ�Ⱥ͸߶�
		int targetW = 0;
		int targetH = 0;
		if (this.targetPicWidth != 0) {
			// �����趨�Ŀ���������
			targetW = this.targetPicWidth;
			targetH = (targetW * srcH) / srcW;
		} else if (this.targetPicHeight != 0) {
			// �����趨�ĳ���������
			targetH = this.targetPicHeight;
			targetW = (targetH * srcW) / srcH;
		} else if (this.picScale != 0) {
			// �������õ����ű�������ͼ��ĳ��Ϳ�
			targetW = (int) ((float) srcW * this.picScale);
			targetH = (int) ((float) srcH * this.picScale);
		} else {
			throw new JPGException(" ���������ʼ������ȷ�� ");
		}

		System.out.println(" ԴͼƬ�ķֱ��ʣ� " + srcW + "��" + srcH);
		System.out.println(" Ŀ��ͼƬ�ķֱ��ʣ� " + targetW + "��" + targetH);
		//	Ŀ��ͼ��Ļ������
		BufferedImage bTarget = new BufferedImage(targetW, targetH,
				BufferedImage.TYPE_3BYTE_BGR);
		
		// ���Ŀ��ͼƬ��ԴͼƬ��ȡ��߶ȵı�����
		double sx = (double) targetW / srcW;
		double sy = (double) targetH / srcH;

		//	����ͼ��任����
		AffineTransform transform = new AffineTransform();
		//  ����ͼ��ת���ı���
		transform.setToScale(sx, sy);
		
		//	����ͼ��ת����������
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		//	ʵ��ת������bSrcת����bTarget
		ato.filter(bSrc, bTarget);

		//	 ���Ŀ��ͼƬ
		try {
			// ��Ŀ��ͼƬ��BufferedImageд���ļ���ȥ��jpegΪͼƬ�ĸ�ʽ
			ImageIO.write(bTarget, "jpeg", fout);
		} catch (IOException ex1) {
			throw new JPGException(" д������ͼ���ļ����� ");
		}
	}
	
	public static void main(String[] args) throws JPGException{
		JPGTransformer jpg = new JPGTransformer();
		// ��ԭͼƬ��Сһ��
		jpg.setPicScale(0.5);
		String srcFileName = "C:/temp/scenery.jpg";
		String targetFileName = "C:/temp/scenery_new.jpg";
		jpg.transform(srcFileName, targetFileName);
	}
}