package com.zstax.util.常用类.email;

import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * �����ʼ�����������ߡ������ʼ�
 */
public class MultiMailsender {

	/**
	 * �����ʼ������������
	 * @param mailInfo	�������ʼ�����Ϣ
	 * @return
	 */
	public static boolean sendMailtoMultiReceiver(MultiMailSenderInfo mailInfo){
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailInfo
				.getProperties(), authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address[] tos = null;
			String[] receivers = mailInfo.getReceivers();
			if (receivers != null){
				// Ϊÿ���ʼ������ߴ���һ����ַ
				tos = new InternetAddress[receivers.length + 1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
				for (int i=0; i<receivers.length; i++){
					tos[i+1] = new InternetAddress(receivers[i]);
				}
			} else {
				tos = new InternetAddress[1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
			}
			// �����н����ߵ�ַ����ӵ��ʼ�������������
			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			// �����ʼ�����
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ���ʹ����͵��ʼ�
	 * @param mailInfo	�������ʼ�����Ϣ
	 * @return
	 */
	public static boolean sendMailtoMultiCC(MultiMailSenderInfo mailInfo){
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailInfo
				.getProperties(), authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			
			// ��ȡ��������Ϣ
			String[] ccs = mailInfo.getCcs();
			if (ccs != null){
				// Ϊÿ���ʼ������ߴ���һ����ַ
				Address[] ccAdresses = new InternetAddress[ccs.length];
				for (int i=0; i<ccs.length; i++){
					ccAdresses[i] = new InternetAddress(ccs[i]);
				}
				// ����������Ϣ���õ��ʼ���Ϣ�У�ע������ΪMessage.RecipientType.CC
				mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
			} 
			
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			// �����ʼ�����
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// �����ʼ���Ϣ
		MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
		mailInfo.setMailServerHost("smtp.sina.com.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("***");
		mailInfo.setPassword("***");
		mailInfo.setFromAddress("***@sina.com");
		mailInfo.setToAddress("***@163.com");
		mailInfo.setSubject("MyMail����");
		mailInfo.setContent("�ҵ��ʼ�����\n\rMy test mail\n\r");

		String[] receivers = new String[]{"***@163.com", "***@tom.com"};
		String[] ccs = receivers;
		mailInfo.setReceivers(receivers);
		mailInfo.setCcs(ccs);
		
		MultiMailsender.sendMailtoMultiReceiver(mailInfo);
		MultiMailsender.sendMailtoMultiCC(mailInfo);
	}
	
	/**
	 * ���Ͷ�����������ʼ��Ļ�����Ϣ
	 */
	public static class MultiMailSenderInfo extends MailSenderInfo{
		// �ʼ��Ľ����ߣ������ж��
		private String[] receivers;
		// �ʼ��ĳ����ߣ������ж��
		private String[] ccs;
		
		public String[] getCcs() {
			return ccs;
		}
		public void setCcs(String[] ccs) {
			this.ccs = ccs;
		}
		public String[] getReceivers() {
			return receivers;
		}
		public void setReceivers(String[] receivers) {
			this.receivers = receivers;
		}
	}
}
