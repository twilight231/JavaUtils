package com.zstax.util.常用类.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * dom�Ļ���������5����document��node��nodelist��element��attr
 * document�������������xml���ĵ�������������node������һ����˳�������document����֮�ڣ����г�һ�����εĽṹ������Ա����ͨ��������������õ�xml�ĵ������е����ݣ���Ҳ�Ƕ�xml�ĵ���������㡣����������ͨ������xmlԴ�ļ����õ�һ��document����Ȼ������ִ�к����Ĳ���.
 * node������dom�ṹ����Ϊ�����Ķ��󣬴������ĵ����е�һ������Ľڵ㡣��ʵ��ʹ�õ�ʱ�򣬺��ٻ��������õ�node������󣬶����õ�����element��attr��text��node������Ӷ����������ĵ���node����Ϊ��Щ�����ṩ��һ������ġ������ĸ�����Ȼ��node�����ж����˶����ӽڵ���д�ȡ�ķ�����������һЩnode�Ӷ��󣬱���text���������������ӽڵ㣬��һ����Ҫע��ġ�
 * nodelist���󣬹���˼�壬���Ǵ�����һ��������һ�����߶��node���б�.
 * element����������xml�ĵ��еı�ǩԪ�أ��̳���node������node������Ҫ���Ӷ����ڱ�ǩ�п��԰��������ԣ����element�������д�ȡ�����Եķ��������κ�node�ж���ķ�����Ҳ��������element�������档
 * attr���������ĳ����ǩ�е����ԡ�attr�̳���node��������Ϊattrʵ�����ǰ�����element�еģ��������ܱ�������element���Ӷ��������dom��attr������dom����һ���֣�����node�е�getparentnode()��getprevioussibling()��getnextsibling()���صĶ�����null��Ҳ����˵��attr��ʵ�Ǳ�������������element�����һ���֣���������Ϊdom���е�����һ���ڵ���֡���һ����ʹ�õ�ʱ��Ҫͬ������node�Ӷ���������
 */
public class DomXML {

	public static List readXMLFile(String inFile) throws Exception {
		//	�õ�DOM�������Ĺ���ʵ��
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			// ��DOM�������DOM������
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			System.err.println(pce); 
			return null;
		}

		Document doc = null;
		try {
			// ����XML�ĵ������������õ�һ��Document
			doc = db.parse(inFile);
			// ��document�������normalize()������ȥ��xml�ĵ�����Ϊ��ʽ�����ݵĿհף�
			// ��������Щ�հ�ӳ����dom���г�Ϊ����Ҫ��text node����
			// ������õ���dom�����ܲ��������������������
			// �ر����������ʱ�����normalize()��Ϊ���á� 
			doc.normalize();
		} catch (DOMException dom) {
			System.err.println(dom.getMessage());
			return null;
		} catch (IOException ioe) {
			System.err.println(ioe);
			return null;
		}

		List studentBeans = new ArrayList();
		StudentBean studentBean = null;
		//	�õ�XML�ĵ��ĸ��ڵ㡰ѧ�������ᡱ
		Element root = doc.getDocumentElement();
		//	ȡ"ѧ��"Ԫ���б�
		NodeList students = root.getElementsByTagName("ѧ��");
		for (int i = 0; i < students.getLength(); i++) {
			//	����ȡÿ��"ѧ��"Ԫ��
			Element student = (Element) students.item(i);
			//	����һ��ѧ����Beanʵ��
			studentBean = new StudentBean();
			//	ȡѧ�����Ա�����
			studentBean.setGender(student.getAttribute("�Ա�"));
			
			//	ȡ��������Ԫ��
			NodeList names = student.getElementsByTagName("����");
			if (names.getLength() == 1) {
				Element e = (Element) names.item(0);
				// ȡ����Ԫ�صĵ�һ���ӽڵ㣬��Ϊ������ֵ�ڵ�
				Text t = (Text) e.getFirstChild();
				// ��ȡֵ�ڵ��ֵ
				studentBean.setName(t.getNodeValue());
			}

			// ȡ�����䡱Ԫ��
			NodeList ages = student.getElementsByTagName("����");
			if (ages.getLength() == 1) {
				Element e = (Element) ages.item(0);
				Text t = (Text) e.getFirstChild();
				studentBean.setAge(Integer.parseInt(t.getNodeValue()));
			}

			//	ȡ���绰��Ԫ��
			NodeList phones = student.getElementsByTagName("�绰");
			if (phones.getLength() == 1) {
				Element e = (Element) phones.item(0);
				Text t = (Text) e.getFirstChild();
				studentBean.setPhone(t.getNodeValue());
			}
			// ���½���Bean�ӵ�����б���
			studentBeans.add(studentBean);
		}
		// ���ؽ���б�
		return studentBeans;
	}
	
	/**
	 * ��DOMдXML�ĵ�����ѧ����Ϣ��XML�ĵ�����ʽ�洢
	 * @param outFile	���XML�ĵ���·��
	 * @param studentGeans	ѧ����Ϣ
	 * @throws Exception
	 */
	public static String writeXMLFile(String outFile, List studentGeans) throws Exception {
		//Ϊ����XML��׼��������DocumentBuilderFactoryʵ��,ָ��DocumentBuilder 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			System.err.println(pce);
			return null;
		}
		// �½�һ�����ĵ�
		Document doc = null;
		doc = db.newDocument();

		// �����ǽ���XML�ĵ����ݵĹ���.
		// �Ƚ�����Ԫ��"ѧ��������"������ӵ��ĵ��� 
		Element root = doc.createElement("ѧ��������");
		doc.appendChild(root);

		//ȡѧ����Ϣ��Bean�б� 
		for (int i = 0; i < studentGeans.size(); i++) {
			//	����ȡÿ��ѧ������Ϣ 
			StudentBean studentBean = (StudentBean) studentGeans.get(i);
			
			//	������ѧ����Ԫ�أ���һ�����Ա����ԣ�Ȼ����ӵ���Ԫ�� 
			Element student = doc.createElement("ѧ��");
			student.setAttribute("�Ա�", studentBean.getGender());
			root.appendChild(student);
			
			//	����"����"Ԫ�أ���ӵ�ѧ������ 
			Element name = doc.createElement("����");
			student.appendChild(name);
			// Ϊ��������Ԫ�ظ�ֵ
			Text tName = doc.createTextNode(studentBean.getName());
			name.appendChild(tName);
			
			// ���������䡱Ԫ�أ�Ȼ���Ԫ�ظ�ֵ
			Element age = doc.createElement("����");
			student.appendChild(age);
			Text tAge = doc	.createTextNode(
					String.valueOf(studentBean.getAge()));
			age.appendChild(tAge);
			
			// �������绰��Ԫ�أ�Ȼ���Ԫ�ظ�ֵ
			Element phone = doc.createElement("�绰");
			student.appendChild(phone);
			Text tPhone = doc.createTextNode(studentBean.getPhone());
			phone.appendChild(tPhone);
		}
		
		//	��XML�ĵ������ָ�����ļ� 
		return domDocToFile(doc, outFile, "GB2312");
	}
	
	/**
	 * ʹ��JAXP��DOM����д��XML�ĵ���
	 * @param doc	DOM���ĵ�����
	 * @param fileName	д���XML�ĵ�·��
	 * @param encoding	XML�ĵ��ı���
	 * @throws TransformerException
	 */
	public static String domDocToFile(Document doc, String fileName, String encoding)
			throws TransformerException {
		// ���ȴ���һ��TransformerFactory����,���ɴ˴���Transformer����
		// Transformer���൱��һ��XSLT���档ͨ������ʹ����������XSL�ļ�,
		// ��������������ʹ���������XML�ĵ���
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		
		// ��ȡTransformser������������,�༴XSLT�����ȱʡ�������,��java.util.Properties����
		Properties properties = transformer.getOutputProperties();
		// �����µ��������:����ַ�����ΪGB2312,��������֧�������ַ�,
		// XSLT�����������XML�ĵ���������������ַ�,����������ʾ��
		properties.setProperty(OutputKeys.ENCODING, "GB2312");
		// �����������ΪXML��ʽ��ʵ��������XSLT�����Ĭ�������ʽ
		properties.setProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperties(properties);
		
		// ����һ��DOMSource����,�ù��캯���Ĳ���������һ��Document����
		DOMSource source = new DOMSource(doc);
		// ����XSLT���������������ｫ���д���ļ�
		File file = new File(fileName);
		StreamResult result = new StreamResult(file);
		
		// ִ��DOM�ĵ���XML�ļ���ת��
		transformer.transform(source, result);
		
		// ������ļ���·������
		return file.getAbsolutePath();
	}

	public static void main(String[] args) {
		String inFileName = "students.xml";
		String outFileName = "students_new.xml";
		try {
			List studentBeans = DomXML.readXMLFile(inFileName);
			DomXML.writeXMLFile(outFileName, studentBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}