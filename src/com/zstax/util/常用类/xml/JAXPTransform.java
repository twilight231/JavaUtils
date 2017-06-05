package com.zstax.util.常用类.xml;

import java.io.File;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * ʹ��JAXP����XSLת��XML�ĵ�
 * JAXP��Java API for XML Processing��Ӣ����ͷ��д,
 * ���ĺ�����:����XML�ĵ������ʹ��Java���Ա�д�ı�̽ӿڡ�
 * JAXP֧��DOM��SAX��XSLT�ȱ�׼��
 */
public class JAXPTransform {

	/**
	 * ʹ��XSLT��XML�ĵ�ת����HTML
	 * @param xmlFileName	ԴXML�ļ���
	 * @param xslFileName	XSL�ļ���
	 * @param htmlFileName	�����HTML�ļ���
	 * @return	����HTML�ļ���
	 */
	public static String xml_xslt_html(String xmlFileName, String xslFileName, 
			String htmlFileName)throws Exception{
		// ����XSLT����Ĺ���
		TransformerFactory tFactory = TransformerFactory.newInstance();
		// ����XSLT����Ҫʹ�õ�XSL�ļ�Դ
		StreamSource source = new StreamSource(new File(xslFileName));
		// ����XSLT����
		Transformer tx = tFactory.newTransformer(source);
		
		// ����XSLT�����������ԣ�ʹ֮���ΪHTML��ʽ������֧�����ġ�
		Properties properties = tx.getOutputProperties(); 
		properties.setProperty(OutputKeys.ENCODING,"GB2312");
		properties.setProperty(OutputKeys.METHOD, "html");
		tx.setOutputProperties(properties); 
		
		// ����XML�ļ�Դ��HTML�ļ��Ľ����
		StreamSource xmlSource = new StreamSource(new File(xmlFileName));
		File targetFile = new  File(htmlFileName);
		StreamResult result = new StreamResult(targetFile);
		
		// ʵ��XSLTת��������XSL�ļ�Դ��XML�ļ�Դת����HTML�����
		tx.transform(xmlSource,	result);
		
		return targetFile.getAbsolutePath();
	}
	
	public static void main(String[] args) throws Exception {
		
		String xmlFileName = "students.xml";
		String xslFileName = "students.xsl";
		String targetFileName = "students.html";
		
		JAXPTransform.xml_xslt_html(xmlFileName, xslFileName, targetFileName);
	}
}