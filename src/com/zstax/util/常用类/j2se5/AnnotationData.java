package com.zstax.util.常用类.j2se5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ʹ��ע��Annotation.
 * Java ��̵�һ���������ƾ���ʹ��Ԫ���ݣ�Ԫ���ݿ������ڴ����ĵ���
 * ���ٴ����е������ԣ�����ִ�л�������ʱ��顣
 * ���Ԫ���ݹ��ߣ���Xdoclet������Щ������ӵ�����Java�����У���ʱ��ΪJava��̹��ܵ�һ���֡�
 * Annotation�ǿ�����ӵ������е����η���
 * �������ڰ��������������������캯����������������������ͱ���
 */
public class AnnotationData {
	@Deprecated private String name;
	public AnnotationData(String name){
		this.name = name;
	}

	// ����������ʹ�������õ�@OverrideԪ���ݣ���ʾ�÷��������˸����ͬ��ͬ��������
	// ������಻���ڸ÷���������벻��ͨ����
	@Override public String toString() {
        return super.toString() + this.name;
	}
	@Override public int hashCode() {
        return toString().hashCode();
	}
	/**
	 * ������ʹ�������õ�@DeprecatedԪ���ݣ���ʾ�÷����Ѿ������Ƽ�ʹ���ˡ�
	 * @return
	 */
	@Deprecated public String getName(){
		return name;
	}
	public String getAnnotationDataName(){
		return this.name;
	}
	
	// �����Զ���Ԫ��������
	// ʹ��@interface����Annotation����
	public @interface MyAnnotation {
		// ��Ԫ�������п��Զ���������
		public enum Severity {
			CRITICAL, IMPORTANT, TRIVIAL, DOCUMENTATION
		};
		// �������ݳ�Ա����Ҫ����getter��setter������
		// ֻ��Ҫ����һ���Գ�Ա���������ķ�������ָ����������Ϊ��Ҫ����������
		// default�ؼ���ΪAnnotation���͵ĳ�Ա����ȱʡֵ
		Severity severity() default Severity.IMPORTANT;
		String item();
	    String assignedTo();
	    String dateAssigned();
	}
	// ʹ���Զ���Annotation���ͣ���ʹ��ʱ��
	// ���Annotation�����������İ��£���Ҫ��ʹ����һ����import��
	@MyAnnotation(
			severity = MyAnnotation.Severity.CRITICAL, 
			item = "Must finish this method carefully", 
			assignedTo = "Programmer A", 
			dateAssigned = "2006/09/10")
	public void doFunction() {
		// do something
	}
	// �����ٶ���һ��Annotation���ͣ�ʹ����Ԫ���ݵ�Ԫ����
	//  @Targetָ��Annotation���Ϳ���Ӧ�õĳ���Ԫ�أ�
	//  ����Ԫ�ص�������java.lang.annotation.ElementTypeö���ඨ��
	
	//  @Retention�� Java ����������Annotation���͵ķ�ʽ�йأ�
	//  ��Щ��ʽ��java.lang.annotation.RetentionPolicy ö���ඨ��
	//  @Documentedָ����Ҫ��Javadoc�а���Annotation��ȱʡ�ǲ������ģ�
	
	//@Retention(RetentionPolicy.RUNTIME)���meta-annotation
	// ��ʾ�˴����͵�annotation�������class�ļ������һ��ܱ��������ȡ��
	//��@Target(ElementType.METHOD)��ʾ�����͵�annotationֻ���������η�������
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyNewAnnotation{
	}
}
