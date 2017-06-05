package com.zstax.util.常用类.email;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * ���ʹ��������ʼ�
 */
public class AttachmentMailSender {

	public static boolean sendMail(MailSenderInfo mailInfo) {
		// �ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			// �����Ҫ�����֤���򴴽�һ��������֤��
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// �����ʼ����͵����Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getInstance(mailInfo
				.getProperties(), authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO,to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵�ʱ��
			mailMessage.setSentDate(new Date());
			
			// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
			Multipart mainPart = new MimeMultipart();
			// ����һ������HTML���ݵ�MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// ����HTML����
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			// Ϊ�ʼ���Ӹ���
			String[] attachFileNames = mailInfo.getAttachFileNames();
			if (attachFileNames != null && attachFileNames.length > 0) {
				// ����ʼ�������MimeBodyPart
				MimeBodyPart attachment = null;
				File file = null;
				for (int i = 0; i < attachFileNames.length; i++) {
					attachment = new MimeBodyPart();
					// ���ݸ����ļ������ļ�����Դ
					file = new File(attachFileNames[i]);
					FileDataSource fds = new FileDataSource(file);
					attachment.setDataHandler(new DataHandler(fds));
					// Ϊ���������ļ���
					attachment.setFileName(MimeUtility.encodeWord(file.getName(), "GBK",
							null));
					mainPart.addBodyPart(attachment);
				}
			}
			// ��MiniMultipart��������Ϊ�ʼ�����
			mailMessage.setContent(mainPart);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		// �����ʼ���Ϣ
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.sina.com.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("***");
		mailInfo.setPassword("***");
		mailInfo.setFromAddress("***@sina.com");
		mailInfo.setToAddress("***@163.com");
		mailInfo.setSubject("MyMail����");
		mailInfo.setContent("�ҵ��ʼ�����\n\rMy test mail\n\r");

		String[] fileNames = new String[3];
		fileNames[0] = "C:/temp/new.txt";
		fileNames[1] = "C:/temp/test.wav";
		fileNames[2] = "C:/temp/mary_photo.jpg";
		mailInfo.setAttachFileNames(fileNames);
		
		AttachmentMailSender.sendMail(mailInfo);
	}
}
