package com.zstax.util.常用类.j2se5;
/**
 * J2SE 5.0��ö������
 * Enum��ΪSunȫ��������һ���ؼ��֣������������������class, ��Ҳ�������Լ��ı�����
 * ���Զ����Լ��ķ���������ʵ��һ�����߶���ӿڡ����������ص㣺
 * ��1��������public�Ĺ��캯�������������Ա�֤�ͻ�����û�а취�½�һ��enum��ʵ����
 * ��2������ö��ֵ����public , static , final�ġ�ע����һ��ֻ�������ö��ֵ��
 * ���Ժ�����ͨ�����涨�����һ�����������κ����͵ķ�ö�ٱ�������Щ�����������κ����η���
 * ��3�������ͷ�����������������ö��ֵ����ĺ��档
 */
public class EnumType {

	/**
	 * ��Person��ö������
	 */
	enum Person{
		CHINESE,   // �й���
		AMERICAN,  // ������
		ENGLISHMAN;// Ӣ����
	}

	public static void main(String[] args) {
		System.out.println("Personö��ֵ����Ŀ��" + Person.values().length);
		// ����ö�����������е�ֵ
		System.out.println("Personö��ֵ���£�");
		Person[] ps = Person.values();
		for (Person p : ps){
			System.out.print(p + "   ");
		}
		System.out.println();
		Person p = Person.CHINESE;
		// �Ƚ�ö��ֵ
		if (p == Person.CHINESE){
			System.out.println("p'value equals Person.CHINESE");
		}
		// ʹ��valueOf����ַ���������ö��ֵ
		 p = Person.valueOf("AMERICAN");
		// ��Switch��ʹ��ö��ֵ
		switch(p){
		case CHINESE: 
			System.out.println("p is Chinese");
			break;
		case AMERICAN: 
			System.out.println("p is American");
			break;
		case ENGLISHMAN: 
			System.out.println("p is Englishman");
			break;
		}
		
		// ȡ��ö��ֵ��ö��������������˳��
		System.out.println("AMERICAN����ţ�" + Person.AMERICAN.ordinal());
		System.out.println("CHINESE����ţ�" + Person.CHINESE.ordinal());
		System.out.println("ENGLISHMAN����ţ�" + Person.ENGLISHMAN.ordinal());
		System.out.println();
		
		// ʹ�ø����ӵ�ö������ComplexPerson
		ComplexPerson cp = ComplexPerson.CHINESE;
		// ��ΪΪCHINESEö��ֵ������toString���������Ե��õ���CHINESE��toString����
		System.out.println("cp.toString(): "+ cp);
		cp = ComplexPerson.AMERICAN;
		// ��Ϊû��ΪAMERICANö��ֵ����toString���������Ե���Ĭ�ϵ�toString����
		System.out.println("cp.toString(): "+ cp);
		// ����ö��ֵ�ķ���
		cp = ComplexPerson.OTHER;
		System.out.println("cp.getValue(): "+ cp.getValue());
	}
	/**
	 * һ�����Ӹ��ӵ�ö������
	 */
	enum ComplexPerson{
		// ö��ֵ
		// CHINESE��value����Ϊ"�й���"
		CHINESE("�й���"){
			public String toString(){
				return "���Ǹ��й���";
			}},
		AMERICAN("������"), 
		ENGLISHMAN("Ӣ����"){
			public String toString(){
				return "���Ǹ�Ӣ����";
		}},
		OTHER{
			public String toString(){
				return "���Ǹ��������ҵ���";
		}};
		
		// ö��ֵ��value���ԣ�ֻ��������ö��ֵ�ĺ���
		private String value = null;
		// Ĭ�Ϲ��췽��
		ComplexPerson(){
			value = "������";
		}
		// �������Ĺ��췽��
		ComplexPerson(String value){
			this.value = value;
		}
		// ��ȡvalue����
		public String getValue(){
			return this.value;
		}
	}
}