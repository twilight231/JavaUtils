package com.zstax.util.常用类.arrayset;

import java.text.DecimalFormat;

/**
 * ����ʹ�ö�ά����
 */
public class Matrix implements Cloneable {
	/** ��������� */
	private double[][] matrixData;
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Matrix() {
		this.init();
	}
	/**
	 * �ö�ά�����ʼ���������
	 * @param data
	 */
	public Matrix(double[][] data) {
		if (!this.canConvert2Matrix(data)) {
			this.init();
		} else {
			// ��data�����ݿ���������
			// ���������Ϊdata.length������Ϊdata[0].length
			this.matrixData = this.cloneArray(data);
		}
	}
	/**
	 * ����ӷ����㡣 
	 * ����A��B�ɼӵ������Ǿ���A���������ھ���B��������A����������B������
	 * c[i][j] = a[i][j] + b[i][j]
	 * @param b  ����
	 * @return
	 */
	public Matrix add(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if ((this.matrixData.length != bData.length)
				|| (this.matrixData[0].length != bData[0].length)) {
			System.out.println("��������Ĵ�С��һ�£�������ɼӷ�����");
			return c;
		}
		// ������������
		double[][] cData = new double[this.matrixData.length][this.matrixData[0].length];
		for (int i = 0; i < this.matrixData.length; i++) {
			for (int j = 0; j < this.matrixData[0].length; j++) {
				// �������Ӧλ�õ��������ӷ�
				cData[i][j] = this.matrixData[i][j] + bData[i][j];
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * ����������㡣 
	 * ����A��B�ɼ��������Ǿ���A���������ھ���B��������A����������B������
	 * c[i][j] = a[i][j] + b[i][j]
	 * @param b ����
	 * @return
	 */
	public Matrix sub(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if ((this.matrixData.length != bData.length)
				|| (this.matrixData[0].length != bData[0].length)) {
			System.out.println("��������Ĵ�С��һ�£�������ɼ�������");
			return c;
		}
		// ������������
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				// �������Ӧλ�õ�����������
				cData[i][j] = this.matrixData[i][j] - bData[i][j];
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * ��������ˣ������������������
	 * c[i][j] = num * a[i][j]
	 * @param num
	 * @return
	 */
	public Matrix multiplyNum(double num) {
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = num * this.matrixData[i][j];
			}
		}
		return new Matrix(cData);
	}
	/**
	 * ����˷����㡣
	 * ����A��B�ɳ˵������Ǿ���A���������ھ���B��������
	 * ��A��һ��p��q�ľ���B��һ��q��r�ľ�������˻�C=AB��һ��p��r�ľ���
	 * C[i][j] = ("A[i][k] * B[k][j]"���ۼ�)
	 * @param b  ����
	 * @return
	 */
	public Matrix multiply(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if (this.matrixData[0].length != bData.length) {
			System.out.println("���˷�ʱ����a������Ҫ��b��������ȣ�");
			return c;
		}
		// �����������ݣ�������������Ϊa������������Ϊb������
		int cRow = this.matrixData.length;
		int cColumn = bData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = 0;
				for (int k = 0; k < this.matrixData[0].length; k++) {
					cData[i][j] += this.matrixData[i][k] * bData[k][j];
				}
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * ����������� 
	 * A/B�ȼ���A����B�������
	 * @param b
	 * @return
	 */
	public Matrix divide(Matrix b) {
		if (b == null) {
			return null;
		}
		if (!this.isSquareMatrix() || (!b.isSquareMatrix())
				|| (this.matrixData.length != b.getMatrixData().length)) {
			System.out.println("����ĳ���Ҫ�����������Ƿ��󣬶���������ȣ�");
			return null;
		}
		// ���ر�������b�������ĳ˻�
		return this.multiply(b.inverseMatrix());
	}
	/**
	 * �����������
	 * Ϊ�����Ҽ�һ����λ�������г����б任������߱�ɵ�λ����ʱ���ұ߾�����õ������
	 * ����ĳ����б任����
	 * ��1�������任����������
	 * ��2�������任����һ�����ݳ���һ����0����
	 * ��3�������任����һ������Ԫ�ص�k���ӵ���һ�еĶ�ӦԪ����ȥ
	 * �����������е��л�����ͬ����Ч
	 * ֻ�з���ſ����������
	 * @return
	 */
	public Matrix inverseMatrix() {
		if (!this.isSquareMatrix()) {
			System.out.println("���Ƿ���û�������");
			return null;
		}
		// �����ұ߼���һ����λ����
		Matrix tempM = this.appendUnitMatrix();
		// �ٽ��г��ȱ任������߲��ֱ�ɵ�λ����
		double[][] tempData = tempM.getMatrixData();
		int tempRow = tempData.length;
		int tempCol = tempData[0].length;
		//�Խ���������Ϊ0ʱ�����ڽ������к�
		int line = 0;
		//�Խ��������ֵĴ�С
		double bs = 0;
		//һ����ʱ���������ڽ�������ʱ���м�����
		double swap = 0;
		for (int i = 0; i < tempRow; i++) {
			// ����߲��ֶԽ����ϵ����ݵ���0���������н��н���
			if (tempData[i][i] == 0) {
				if (++line >= tempRow) {
					System.out.println("�˾���û�������");
					return null;
				}

				for (int j = 0; j < tempCol; j++) {
					swap = tempData[i][j];
					tempData[i][j] = tempData[line][j];
					tempData[line][j] = swap;
				}

				//��ǰ�У���i�У����line�н��н�������Ҫ���¶Ե�i�н��д���
				//��ˣ���Ҫ���б�i��1����Ϊ��forѭ���лὫi��1��
				i--;
				//������i�д�����ʱ��i�е�������ԭ����line�е����ݡ�
				continue;
			}

			//����߲��־���Խ����ϵ����ݱ��1.0
			if (tempData[i][i] != 1) {
				bs = tempData[i][i];
				for (int j = tempCol - 1; j >= 0; j--) {
					tempData[i][j] /= bs;
				}
				//����߲��־������϶ԽǾ���
				//��ν�϶ԽǾ����Ǿ�������½�Ԫ��ȫΪ0
				for (int iNow = i + 1; iNow < tempRow; iNow++) {
					for (int j = tempCol - 1; j >= i; j--) {
						tempData[iNow][j] -= tempData[i][j] * tempData[iNow][i];
					}
				}
			}
		}

		//����߲��־�����϶ԽǾ����ɵ�λ���󣬼�����������Ͻ�Ԫ��Ҳ��Ϊ0
		for (int i = 0; i < tempRow - 1; i++) {
			for (int iNow = i; iNow < tempRow - 1; iNow++) {
				for (int j = tempCol - 1; j >= 0; j--) {
					tempData[i][j] -= tempData[i][iNow + 1]
							* tempData[iNow + 1][j];
				}
			}
		}

		// �ұ߲��־������������
		Matrix c = null;
		int cRow = tempRow;
		int cColumn = tempCol / 2;
		double[][] cData = new double[cRow][cColumn];
		// ���ұ߲��ֵ�ֵ����cData
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = tempData[i][cColumn + j];
			}
		}
		// �õ�����󣬷���
		c = new Matrix(cData);
		return c;
	}
	/**
	 * ת�þ���ת�þ������������ԭ�������������������ԭ���������
	 * c[i][j] = a[j][i]
	 * 
	 * @return
	 */
	public Matrix transposeMatrix() {
		int cRow = this.matrixData[0].length;
		int cColumn = this.matrixData.length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = this.matrixData[j][i];
			}
		}
		return new Matrix(cData);
	}
	/**
	 * �жϾ����Ƿ�Ϊ����
	 * @return
	 */
	public boolean isSquareMatrix() {
		if (this.matrixData.length == this.matrixData[0].length) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @return ���� matrixData��ֵ��
	 */
	public double[][] getMatrixData() {
		// �û����ܻ��޸�ͨ�����ô˷����õ������飬�������Ƕ���
		// �û����޸Ļ�Ӱ�쵽����������ݣ���ˣ�
		// ���ﷵ�ص��Ǳ��������ݵĿ�¡���������ã��Է�ֹ�޸ı���������ݡ�
		return cloneArray(this.matrixData);
	}
	/**
	 * ���þ����matrixDataֵ
	 * @param data  ��ǰ����
	 */
	public void setMatrixData(double[][] data) {
		if (this.canConvert2Matrix(data)){
			//��getMatrixData����һ���������ǽ���ǰ���ݵĿ�¡һ�ݣ�����������
			//�Է�ֹ�û��޸ĵ�ǰ���ݣ����±����������Ҳ�����仯
			this.matrixData = this.cloneArray(data);
		}
	}
	/**
	 * ������ַ�����ʽ
	 */
	public String toString(){
		//�Ѿ�������ݻ����ַ�������
		return this.arrayToString(this.matrixData);
	}
	/**
	 * �Ծ������ʽ���
	 */
	public void display() {
		System.out.println(this.toString());
	}
	/**
	 * �ȽϾ����Ƿ���ȣ�������Object���equals����
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Matrix) {
			Matrix objM = (Matrix) obj;
			double[][] objMData = objM.getMatrixData();
			for (int i = 0; i < objMData.length; i++) {
				// һ��һ�еıȽ�
				for (int j = 0; j < objMData[0].length; j++) {
					// ��Ӧλ�õ��������Ƚϣ�������ȣ��ͷ���false
					if (this.matrixData[i][j] != objMData[i][j]) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * ��ȡ�����hashCode��������Object���hashCode����
	 */
	public int hashCode() {
		// ʹ�þ�����ַ�����ʽ��hashCode��Ϊ�����hashCode
		return this.toString().hashCode();
	}
	/**
	 * ��¡һ�����󣬸�����Object���clone����
	 */
	public Object clone() {
		try {
			Matrix matrix = (Matrix) super.clone();
			matrix.setMatrixData(this.matrixData);
			return matrix;
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}
	/**
	 * �õ�λ�����ʼ��
	 */
	private void init() {
		this.matrixData = new double[][] { { 1.0, 0.0, 0.0 },
				{ 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0 } };
	}
	/**
	 * �ж�һ����ά�����ܹ�ת���ɾ��� 
	 * ����Ҫ������ĵڶ�ά���ȱ���һ����
	 * @param data
	 * @return
	 */
	private boolean canConvert2Matrix(double[][] data) {
		if (data == null) {
			return false;
		}
		// ����Ƚϣ�����г��Ȳ��ȵģ��㷵��false
		for (int i = 0; i < data.length - 1; i++) {
			if (data[i].length != data[i + 1].length) {
				return false;
			}
		}
		return true;
	}
	/**
	 * ��¡һ����ά����
	 * @param src	Դ��ά����
	 * @return
	 */
	private double[][] cloneArray(double[][] src) {
		if (src == null) {
			return null;
		}
		// ʹ������Ŀ�¡����
		return (double[][]) src.clone();
	}
	/**
	 * �������ұ߼���һ����λ����
	 * @return
	 */
	private Matrix appendUnitMatrix() {
		Matrix c = null;
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length * 2;
		// �������������������о������������о����������
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				if (j < this.matrixData[0].length) {
					cData[i][j] = this.matrixData[i][j];
				} else {
					if ((j - i) == this.matrixData[0].length) {
						// �ұߵĵ�λ�����ǶԽ�����ֵΪ1������λ��Ϊ0
						cData[i][j] = 1.0;
					} else {
						cData[i][j] = 0;
					}
				}
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * ��ά������ַ�����ʽ
	 * @param array
	 * @return
	 */
	private String arrayToString(double[][] array) {
		//�����ݽ��и�ʽ��������ֻ����2λС��
		DecimalFormat df = new DecimalFormat("0.00");

		//ʹ��StringBuffer������String��Ϊ�˱����½�̫���String����
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				sb.append(df.format(array[i][j])).append(" ");
			}
			// �����һ�����ݾͻ���
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// Ĭ�Ϲ��캯��
		Matrix defaultM = new Matrix();
		System.out.println("Ĭ�Ͼ���:");
		defaultM.display();
		// �������Ĺ��캯��
		// �������ĳ�ʼ����ά����
		double[][] data0 = new double[3][3];
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				data0[i][j] = i*3 + j;
			}
		}
		Matrix m0 = new Matrix(data0);
		System.out.println("����m0:");
		m0.display();
		// �ó�ʼ���ķ�ʽ����һ����ά����
		double[][] data1 = new double[][] { { 4.0, 5.0, 3.0 },
				{ 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };

		Matrix m1 = new Matrix(data1);
		System.out.println("����m1:");
		m1.display();
		//�жϾ����Ƿ�Ϊ����
		System.out.println("����m0�Ƿ��� " + m0.isSquareMatrix());
		System.out.println("����m1�Ƿ��� " + m1.isSquareMatrix());
		System.out.println();
		// ����ӷ�
		System.out.println("����m0+m1�Ľ��:");
		m0.add(m1).display();
		// �������
		System.out.println("����m0-m1�Ľ��:");
		m0.sub(m1).display();
		//��������
		System.out.println("����m0*2�Ľ��:");
		m0.multiplyNum(2).display();
		// ����˷�
		System.out.println("����m0*m1�Ľ��:");
		Matrix mTemp = m0.multiply(m1);
		if (mTemp != null) {
			mTemp.display();
		} else {
			System.out.println("����m0��m1�������˷����㣡");
		}
		
		// ��ȡ�����ת�þ���
		System.out.println("����m0��ת�þ���:");
		m0.transposeMatrix().display();
		// ��ȡ����������
		System.out.println("����m1�������:");
		mTemp = m1.inverseMatrix();
		if (mTemp != null) {
			mTemp.display();
			//���������������ĳ˻�
			System.out.println("����m1�����������ĳ˻�:");
			m1.multiply(mTemp).display();
		} else {
			System.out.println("����m0û�������");
		}
		// �������
		System.out.println("����m0/m1�Ľ��:");
		mTemp = m0.divide(m1);
		if (mTemp != null) {
			mTemp.display();
		} else {
			System.out.println("����m0��m1�������������㣡");
		}
		
		// �����¡
		System.out.println("����m0��¡��m2:");
		Matrix m2 = (Matrix) m1.clone();
		m2.display();
		// �ȽϾ����С
		System.out.println("m1.equals(m2) = " + m1.equals(m2));
		//��ȡ�����hashCode
		System.out.println("m1��hashCode: " + m1.hashCode());
		System.out.println("m2��hashCode: " + m2.hashCode());
	}
}
