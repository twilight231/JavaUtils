package com.zstax.util.常用类.exception;
/**
 *  �׳��쳣�������쳣
 *	throws���ڷ��������У����������÷������ܻ��׳����쳣������throws����쳣��
 *  throw���ڷ������ڣ���������ִ��ʱ�����쳣��������Խ��쳣�����װΪ�쳣����Ȼ��throw�����׳��쳣
 */
public class ThrowAndThrows {
	/**
	 * ����һ�����ֵ�ƽ����
	 * @param nStr	���ַ�������ʽ�ṩ����
	 * @return
	 * @throws Exception	���û�������ַ���Ϊ�գ�
	 * 			�����ַ����޷�ת�������֣�����ת���ɵ�����С��0�������׳��쳣
	 */
	public static double sqrt(String nStr) throws Exception{
		if (nStr == null){
			//��throw�ؼ����׳��쳣�����쳣���׳�ʱ������������÷���
			throw new Exception("������ַ�������Ϊ�գ�");
		}
		double n = 0;
		try {
			n = Double.parseDouble(nStr);
		} catch (NumberFormatException e){
			//��parseDouble���������׳����쳣NumberFormatException����
			//Ȼ�󽫲�����쳣���·�װ���׳�
			throw new Exception("������ַ��������ܹ�ת�������֣�", e);
		}
		if (n < 0){
			throw new Exception("������ַ���ת���ɵ����ֱ�����ڵ���0��");
		}
		//����ƽ����
		return Math.sqrt(n);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//����sqrt��������������throws �ؼ��֣����ԣ��ڵ��ø÷���ʱ��
		//�����throws�����������쳣���д��ã����÷�ʽ������: 
		//��1���������������쳣���ڱ�������������ͬ������throws�����쳣�����׳����������е�main����һ����
		//��2��������������쳣���ڵ���sqrt����ʱ��ʹ��try...catch��䣬�����ܻ�����쳣�Ĵ����catch��Ȼ����д��á�
		
		try{
			ThrowAndThrows.sqrt("-124.56");
		} catch (Exception e){
			//��sqrt���������Ŀ����׳���Exception�쳣����
			//��ӡ������쳣�Ķ�ջ��Ϣ���Ӷ�ջ��Ϣ�п��Է����쳣������λ�ú�ԭ��
			System.out.println("Got a Exception: " + e.getMessage());
			e.printStackTrace();
			//������һ���������쳣�����׳���
			throw e;
		}
		
		//��sqrt�����˿��ܻ��׳����쳣�����ף������ڷ���������ʹ��throws��
		ThrowAndThrows.sqrt("-124.56");
	}
}
