package com.zstax.util.常用类.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ʹ��SAX����XML�ĵ���SAX��Simple API for XML����д��
 * ��DOM�Ƚ϶��ԣ�SAX��һ�������͵ķ���������֪�����ڴ���DOM��ʱ��������Ҫ����������XML�ĵ���Ȼ�����ڴ��д���DOM��������DOM���ϵ�ÿ��Node���󡣵��ĵ��Ƚ�С��ʱ���ⲻ�����ʲô���⣬����һ���ĵ�������������DOM�ͻ����൱��ʱ�������ر���������ڴ������Ҳ���ǳɱ�����������������ĳЩӦ����ʹ��DOM��һ���ܲ�������£�������applet�У�����ʱ��һ���Ϻõ���������������SAX��
 * SAX�ڸ�������DOM��ȫ��ͬ�����ȣ���ͬ��DOM���ĵ������������¼������ģ�Ҳ����˵����������Ҫ���������ĵ������ĵ��Ķ������Ҳ����SAX�Ľ������̡���ν�¼���������ָһ�ֻ��ڻص���callback�����Ƶĳ������з�����
 */
public class SaxXML {

	public static List readXML(String fileName) throws Exception {
		// ����SAX��������������
		SAXParserFactory spf = SAXParserFactory.newInstance();
		// ʹ�ý�������������������ʵ��
		SAXParser saxParser = spf.newSAXParser();

		// ����SAX������Ҫʹ�õ��¼�����������
		StudentSAXHandler handler = new StudentSAXHandler();
		// ��ʼ�����ļ�
		saxParser.parse(new File(fileName), handler);

		// ��ȡ���
		return handler.getResult();
	}

	public static void main(String[] args) {

		String filename = "students.xml";
		List studentBeans = null;
		try {
			studentBeans = SaxXML.readXML(filename);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if (studentBeans != null) {
			System.out.println("����student.xml�ĵ��õ���ѧ����Ϣ��");
			for (int i = 0; i < studentBeans.size(); i++) {
				System.out.println(studentBeans.get(i).toString());
			}
		}
	}

	/**
	 * SAX���¼����������������ض���XML�ļ���ʱ��
	 * ����ҪΪ�䴴��һ��ʵ����ContentHandler�����������ض����¼���
	 * ����˵�����ʵ���Ͼ���SAX����XML�ļ��ĺ��ġ�
	 */
	static class StudentSAXHandler extends DefaultHandler {
		// �����Ѿ�����������û�йرյı�ǩ��
		java.util.Stack tagsStatck = new java.util.Stack();
		List studentBeans = new ArrayList();
		StudentBean bean = null;

		/**
		 * �������ĵ��Ŀ�ͷ��ʱ�򣬵������������������������һЩԤ����Ĺ���
		 */
		public void startDocument() throws SAXException {
			System.out.println("------Parse begin--------");
		}

		/**
		 * ���ĵ�������ʱ�򣬵������������������������һЩ�ƺ�Ĺ���
		 */
		public void endDocument() throws SAXException {
			System.out.println("------Parse end--------");
		}
		
		/**
		 * ������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������.
		 * namespaceURI��������localName�Ǳ�ǩ����qName�Ǳ�ǩ������ǰ׺��
		 * atts�������ǩ�������������б�ͨ��atts�����Եõ����е�����������Ӧ��ֵ.
		 * <name="">
		 */
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
				throws SAXException {
			tagsStatck.push(qName);
			
			// ����µı�ǩ�ǡ�ѧ���������ʾ������Ҫ��ȡѧ��������֮������ҪbeanΪ�գ�����Ϊ����ѧ����ǩ���ӱ�ǩҲ�С�ѧ����
			if (bean == null) {
				if (qName.equals("ѧ��")){
					System.out.println("------Processing a student--------");
					bean = new StudentBean();
					bean.setGender(atts.getValue("�Ա�"));
				}
			}
		}

		/**
		 * ������������ǩ��ʱ�򣬵����������
		 */
		public void endElement(String namespaceURI, String localName, String qName)
				throws SAXException {
			// �������ȡ�ı�ǩ����
			String currenttag = (String)tagsStatck.pop();
			// ��������ı�ǩӦ���뼴���رյı�ǩһ����
			if (!currenttag.equals(qName)){
				throw new SAXException("XML�ĵ���ʽ����ȷ����ǩ��ƥ�䣡");
			}
			// ����رյ���"ѧ��"��ǩ�����ʾһ��StudentBean�Ѿ���������ˡ�
			if (qName.equals("ѧ��")){
				System.out.println("------Processing a student end--------");
				// ��beanʵ������ѧ���б��У�ͬʱ�ÿգ��ȴ�������һ��ʵ��
				studentBeans.add(bean);
				bean = null;
			}
		}

		/** 
		 * ������XML�ļ��ж����ַ���
		 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
		 */
		public void characters(char[] chs, int start, int length) throws SAXException {
			//	��ջ�еõ���ǰ�ڵ����Ϣ
			String tag = (String) tagsStatck.peek();
			String value = new String(chs, start, length);
			
			if (tag.equals("����")){
				// �����������ı�ǩ������������ַ�������������ֵ
				bean.setName(value);
			} else if (tag.equals("����")){
				bean.setAge(Integer.parseInt(value));
			} else if (tag.equals("�绰")){
				bean.setPhone(value);
			}
		}
		
		public List getResult(){
			return studentBeans;
		}
	}
}