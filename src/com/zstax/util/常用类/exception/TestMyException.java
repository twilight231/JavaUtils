package com.zstax.util.常用类.exception;
/**
 * �Զ����쳣���ʹ��
 * @author new
 *
 */
public class TestMyException {

	public static void firstException() throws MyFirstException{
		throw new MyFirstException("\"firstException()\" method occurs an exception!");
	}
	
	public static void secondException() throws MySecondException{
		throw new MySecondException("\"secondException()\" method occurs an exception!");
	}

	public static void main(String[] args) {
		try {
			TestMyException.firstException();
			TestMyException.secondException();
		} catch (MyFirstException e1){
			System.out.println("Exception: " + e1.getMessage());
			e1.printStackTrace();
		} catch (MySecondException e2){
			System.out.println("Exception: " + e2.getMessage());
			e2.printStackTrace();
		}
		//��һ��try�������Ŷ��catch��ʱ������������쳣ƥ���һ��catch��Ĳ������㽫�쳣����Ȩ��������һ��catch�顣
		//����������쳣���һ��catch�鲻ƥ�䣬�㿴�Ƿ���ڶ���catch��ƥ�䣬������ȥ������������Ȼ�޷�ƥ����쳣��
		//����Ҫ�ڷ������������һ��throw��䣬�����쳣�׳���
		//��ˣ����ж��catch�飬����ÿ�δ�����쳣���;��м̳й�ϵʱ��Ӧ������catch�����쳣����catch�����쳣��
		//���磬���MySecondException�̳�MyFirstException����ô��ý�catch (MySecondException e2)����ǰ�棬
		//��catch (MyFirstException e1)���ں��档
	}
}
