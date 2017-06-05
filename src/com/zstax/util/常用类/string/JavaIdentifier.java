package com.zstax.util.常用类.string;
/**
 * �ж�һ���ַ����Ƿ��ܹ���Java�ı�ʶ��
 */
public class JavaIdentifier {

	/**
	 * �ж��ַ����Ƿ��ǺϷ���Java��ʶ��
	 * @param s	���жϵ��ַ���
	 * @return
	 */
	public static boolean isJavaIdentifier(String s){
        //����ַ���Ϊ�ջ��߳���Ϊ0������false
		if ((s == null) || (s.length() == 0)) {
            return false;
        }
        //�ַ�����ÿһ���ַ���������Java��ʶ����һ����
        for (int i=0; i<s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                return false;
            }
        }
        return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\"my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("my_var"));
		System.out.println("\"my_var.1\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("my_var.1"));
		System.out.println("\"$my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("$my_var"));
		System.out.println("\"\u0391var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("\u0391var"));
		System.out.println("\"\1$my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("\1$my_var"));
	}
}
