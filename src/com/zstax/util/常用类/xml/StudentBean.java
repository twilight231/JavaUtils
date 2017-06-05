package com.zstax.util.常用类.xml;

/**
 * ����ѧ����JavaBean
 */
public class StudentBean {
	// ѧ������
	private String name;
	// ѧ���Ա� 
	private String gender;
	// ѧ������
	private int age;
	// ѧ���绰����
	private String phone;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("������").append(this.name).append("��  ");
		sb.append("�Ա�").append(gender).append("��  ");
		sb.append("���䣺").append(age).append("��  ");
		sb.append("�绰��").append(phone);
		return sb.toString();
	}
	
	/**
	 * @return ���� age��
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age Ҫ���õ� age��
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return ���� gender��
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender Ҫ���õ� gender��
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return ���� name��
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Ҫ���õ� name��
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return ���� phone��
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone Ҫ���õ� phone��
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}