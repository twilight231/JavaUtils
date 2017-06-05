package com.zstax.util.常用类.io;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * �ܶ�Ӧ�ó���Ҫ��̬���� PDF �ĵ�������Ӧ�ó�����������������ڵ����ʼ�Ͷ�ݵĿͻ����������߹����ض�ͼ���½ڲ��� PDF ��ʽ������Щ�ĵ�������������ȥ�Ǻܶ�ġ��ڱ����У���ʹ�� iText Java ������ PDF �ĵ��������������һ��ʾ��Ӧ�ó�����ʹ���ܹ����õ�����ʹ�� iText�� 
����iText �� Lowagie.com վ�㣨����� �ο����ϣ�����ṩ�� Java �⡣iText ��Ĺ��ܺ�ǿ��֧�� HTML��RTF �� XML �ĵ������ɣ����⻹�ܹ����� PDF �ĵ������ԴӶ���������ѡ���ĵ�����ʹ�õ����塣ͬʱ��iText �Ľṹ����ʹ����ͬ�Ĵ������������������͵��ĵ���
 * http://www.lowagie.com/iText/
 * iText API��������۲�
����com.lowagie.text.Document ������ PDF ����Ҫ���ࡣ������Ҫʹ�õĵ�һ���ࡣһ����ʼ�����ĵ�������Ҫһ��д�������ĵ���д�����ݡ�com.lowagie.text.pdf.PdfWriter ����һ�� PDF д�����������г���ͨ����Ҫʹ�õ��ࣺ
����com.lowagie.text.Paragraph ���� ������ʾһ�������Ķ��䡣 
����com.lowagie.text.Chapter ���� ������ʾ PDF �ĵ��е��½ڡ�ʹ�� Paragraph ��Ϊ��Ŀ��ʹ�� int ��Ϊ�½ں�������������
����com.lowagie.text.Font ���� ����������ȫ��������淶���������塢��С����ʽ����ɫ���������嶼�������������Ϊ��̬������ 
����com.lowagie.text.List ���� ������ʾһ���б���˳�������� ListItems��
����com.lowagie.text.Table ���� ������ʾ������Ԫ��ı���Ԫ������������ھ����С�
 */
public class PDFFile {

	/**
	 * дPDF�ļ���չʾ��PDF�ĵ����½ڡ�С�ڡ����塢���䡢����б��ʹ��
	 * ���չʾ���ʹ��д�����ġ�
	 * @param fileName
	 */
	public void writePDF(String fileName) {
		File file = new File(fileName);
		FileOutputStream out = null;

		try {
			//��1��ʵ�����ĵ�����
			//��һ��������ҳ���С���������Ĳ����ֱ������ҡ��Ϻ���ҳ�߾ࡣ
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);

			//��2������д����
			//��һ�������Ƕ��ĵ���������ã��ڶ���������������ļ�����out��document��������
			out = new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			//���ĵ�׼��д������
			document.open();
			
			//��3�����洴���½ڶ���
			//���ȴ������������Ϊ�½ڵı��⡣FontFactory����ָ����������塣
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 
					18, Font.BOLDITALIC, new Color(0, 0, 255));
			Paragraph chapter1_title = new Paragraph("Chapter 1",font);
			//������һ���½ڶ��󣬱���Ϊ"Chapter 1"
			Chapter chapter1 = new Chapter(chapter1_title, 1);
			//����ż�����Ϊ 0 �Ͳ�����ҳ������ʾ�½ڱ��
			chapter1.setNumberDepth(0);
			//��4������С�ڶ���
			//����С�ڶ���ı���
			font = FontFactory.getFont(FontFactory.HELVETICA, 16, 
					Font.BOLD, new Color(255, 0, 0));
			Paragraph section1_title1 = new Paragraph("Section 1 of Chapter 1", font);
			//����һ��С�ڶ��󣬱���Ϊ"This is Section 1 in Chapter 1"������chapter1��
			Section section1 = chapter1.addSection(section1_title1);
			//��5����С����д�ı�����
			Paragraph text = new Paragraph("This is the first text in section 1 of chapter 1.");
			section1.add(text);
			text = new Paragraph("Following is a 5��5 table:");
			section1.add(text);
			
			//��6����С����д���
			//����������
			Table table = new Table(5, 5);
			//���ñ��߿���ɫ
			table.setBorderColor(new Color(220, 255, 100));
			//���õ�Ԫ��ı߾�����
			table.setPadding(1);
			table.setSpacing(1);
			table.setBorderWidth(1);
			//��Ԫ�����
			Cell cell = null;
			//��ӱ�ͷ��Ϣ
			for (int colNum=0; colNum<5; colNum++){
				cell = new Cell("header-" + colNum);
				cell.setHeader(true);
				table.addCell(cell);
			}
			table.endHeaders();
			//��ӱ������
			for (int rowNum=1; rowNum<5; rowNum++){
				for (int colNum=0; colNum<5; colNum++){
					cell= new Cell("value-" + rowNum + "-" + colNum);
					table.addCell(cell);
				}
			}
			//����������ӵ�С�ڶ�����
			section1.add(table); 
			
			//��7������б�
			// �б����һ�������� ListItem�����Զ��б���б�ţ�Ҳ���Բ���š�
			// ����һ����������Ϊ true �����봴��һ�����б�ŵ��б�
			// �ڶ�����������Ϊtrue��ʾ�б������ĸ���б�ţ�Ϊfalse�������ֽ��б�ţ�
			// ����������Ϊ�б���������֮��ľ��롣
			List list = new List(true, false, 20);
			ListItem item = new ListItem("First item of list;");
			list.add(item);
			item = new ListItem("Second item of list;");
			list.add(item);
			item = new ListItem("Third item of list.");
			list.add(item);
			//���б������ӵ�С�ڶ�����
			section1.add(list);
			
			//��8���������
			//������PDF��д�����ģ��������ļ�����classPath�С�
			//simfang.ttf�Ƿ��ε������ļ�
			BaseFont bfChinese = BaseFont.createFont("simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//���Ĵ�СΪ20���Ӵ�
			font = new Font(bfChinese, 20, Font.BOLD);
			text = new Paragraph("PDF���Ĳ���", font);
			section1.add(text);
			
			//��9�����½ڶ�����뵽�ĵ���
			document.add(chapter1);
			
			//��10���ر��ĵ�
			document.close();
			System.out.println("PDF�ļ����ɳɹ���PDF�ļ�����" + file.getAbsolutePath());
		} catch (DocumentException e) {
			System.out.println("PDF�ļ�"+ file.getAbsolutePath() + "����ʧ�ܣ�" + e);
			e.printStackTrace();
		} catch (IOException ee) {
			System.out.println("PDF�ļ�"+ file.getAbsolutePath() + "����ʧ�ܣ�" + ee);
			ee.printStackTrace();
		} finally {
			if (out != null){
				try {
					//�ر�����ļ���
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * ��PDF�ļ���ʹ����pdfbox��Դ��Ŀ���µİ汾�Ѿ�֧�������ˡ�
	 * ��www.pdfbox.org���ض�PDF��jar��
	 * @param fileName
	 */
	public void readPDF(String fileName) {
		File file = new File(fileName);
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			//�½�һ��PDF����������
			PDFParser parser = new PDFParser(in);
			//��PDF�ļ����н���
			parser.parse();
			//��ȡ������õ���PDF�ĵ�����
			PDDocument pdfdocument = parser.getPDDocument();
			//�½�һ��PDF�ı�������
			PDFTextStripper stripper = new PDFTextStripper();
			//��PDF�ĵ������а����ı�
			String result = stripper.getText(pdfdocument);
			System.out.println("PDF�ļ�" + file.getAbsolutePath() + "���ı��������£�");
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println("��ȡPDF�ļ�"+ file.getAbsolutePath() + "��ʧ�ܣ�" + e);
			e.printStackTrace();
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main(String[] args) {
		PDFFile pdf = new PDFFile();
		String fileName = "C:/temp/tempPDF.pdf";
		pdf.writePDF(fileName);
		pdf.readPDF(fileName);
	}
}
