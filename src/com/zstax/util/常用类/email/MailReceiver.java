package com.zstax.util.常用类.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * �ʼ���������Ŀǰ֧��pop3Э�顣
 * �ܹ������ı���HTML�ʹ��и������ʼ�
 */
public class MailReceiver {
	// ���ʼ��Ĳ�������
	private MailReceiverInfo receiverInfo;
	// ���ʼ����������Ӻ�õ�������
	private Store store;
	// �ռ���
	private Folder folder;
	// �ռ����е��ʼ���Ϣ
	private Message[] messages;
	// ��ǰ���ڴ�����ʼ���Ϣ
	private Message currentMessage;

	private String currentEmailFileName;

	public MailReceiver(MailReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}
	/**
	 * ���ʼ�
	 */
	public void receiveAllMail() throws Exception{
		if (this.receiverInfo == null){
			throw new Exception("�����ṩ�����ʼ��Ĳ�����");
		}
		// ���ӵ�������
		if (this.connectToServer()) {
			// ���ռ���
			if (this.openInBoxFolder()) {
				// ��ȡ�����ʼ�
				this.getAllMail();
				this.closeConnection();
			} else {
				throw new Exception("���ռ���ʧ�ܣ�");
			}
		} else {
			throw new Exception("�����ʼ�������ʧ�ܣ�");
		}
	}
	
	/**
	 * ��½�ʼ�������
	 */
	private boolean connectToServer() {
		// �ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		if (this.receiverInfo.isValidate()) {
			// �����Ҫ�����֤���򴴽�һ��������֤��
			authenticator = new MyAuthenticator(this.receiverInfo.getUserName(),
					this.receiverInfo.getPassword());
		}
		//����session
		Session session = Session.getInstance(this.receiverInfo
				.getProperties(), authenticator);

		//����store,��������
		try {
			this.store = session.getStore(this.receiverInfo.getProtocal());
		} catch (NoSuchProviderException e) {
			System.out.println("���ӷ�����ʧ�ܣ�");
			return false;
		}

		System.out.println("connecting");
		try {
			this.store.connect();
		} catch (MessagingException e) {
			System.out.println("���ӷ�����ʧ�ܣ�");
			return false;
		}
		System.out.println("���ӷ������ɹ�");
		return true;
	}
	/**
	 * ���ռ���
	 */
	private boolean openInBoxFolder() {
		try {
			this.folder = store.getFolder("INBOX");
			// ֻ��
			folder.open(Folder.READ_ONLY);
			return true;
		} catch (MessagingException e) {
			System.err.println("���ռ���ʧ�ܣ�");
		}
		return false;
	}
	/**
	 * �Ͽ����ʼ�������������
	 */
	private boolean closeConnection() {
		try {
			if (this.folder.isOpen()) {
				this.folder.close(true);
			}
			this.store.close();
			System.out.println("�ɹ��ر����ʼ������������ӣ�");
			return true;
		} catch (Exception e) {
			System.out.println("�رպ��ʼ�������֮������ʱ����");
		}
		return false;
	}
	
	/**
	 * ��ȡmessages�е������ʼ�
	 * @throws MessagingException 
	 */
	private void getAllMail() throws MessagingException {
		//���ʼ��ļ��л�ȡ�ʼ���Ϣ
		this.messages = this.folder.getMessages();
		System.out.println("�ܵ��ʼ���Ŀ��" + messages.length);
		System.out.println("���ʼ���Ŀ��" + this.getNewMessageCount());
		System.out.println("δ���ʼ���Ŀ��" + this.getUnreadMessageCount());
		//��Ҫ���ص��ʼ���������
		int mailArrayLength = this.getMessageCount();
		System.out.println("һ�����ʼ�" + mailArrayLength + "��");
		int errorCounter = 0; //�ʼ����س��������
		int successCounter = 0;
		for (int index = 0; index < mailArrayLength; index++) {
			try {
				this.currentMessage = (messages[index]); //���õ�ǰmessage
				System.out.println("���ڻ�ȡ��" + index + "���ʼ�......");
				this.showMailBasicInfo();
				getMail(); //��ȡ��ǰmessage
				System.out.println("�ɹ���ȡ��" + index + "���ʼ�");
				successCounter++;
			} catch (Throwable e) {
				errorCounter++;
				System.err.println("���ص�" + index + "���ʼ�ʱ����");
			}
		}
		System.out.println("------------------");
		System.out.println("�ɹ�������" + successCounter + "���ʼ�");
		System.out.println("ʧ��������" + errorCounter + "���ʼ�");
		System.out.println("------------------");
	}

	/**
	 * ��ʾ�ʼ��Ļ�����Ϣ
	 */
	private void showMailBasicInfo() throws Exception{
		showMailBasicInfo(this.currentMessage);
	}
	private void showMailBasicInfo(Message message) throws Exception {
		System.out.println("-------- �ʼ�ID��" + this.getMessageId()
				+ " ---------");
		System.out.println("From��" + this.getFrom());
		System.out.println("To��" + this.getTOAddress());
		System.out.println("CC��" + this.getCCAddress());
		System.out.println("BCC��" + this.getBCCAddress());
		System.out.println("Subject��" + this.getSubject());
		System.out.println("����ʱ�䣺��" + this.getSentDate());
		System.out.println("�����ʼ���" + this.isNew());
		System.out.println("Ҫ���ִ��" + this.getReplySign());
		System.out.println("����������" + this.isContainAttach());
		System.out.println("------------------------------");
	}

	/**
	 * ����ʼ����ռ��ˣ����ͣ������͵ĵ�ַ�����������������ݵĲ����Ĳ�ͬ 
	 * "to"----�ռ��� "cc"---�����˵�ַ "bcc"---�����˵�ַ
	 */
	private String getTOAddress() throws Exception {
		return getMailAddress("TO", this.currentMessage);
	}

	private String getCCAddress() throws Exception {
		return getMailAddress("CC", this.currentMessage);
	}

	private String getBCCAddress() throws Exception {
		return getMailAddress("BCC", this.currentMessage);
	}

	/**
	 * ����ʼ���ַ
	 * @param type		���ͣ����ռ��ˡ������ˡ�������
	 * @param mimeMessage	�ʼ���Ϣ
	 * @return
	 * @throws Exception
	 */
	private String getMailAddress(String type, Message mimeMessage)
			throws Exception {
		String mailaddr = "";
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		if (addtype.equals("TO") || addtype.equals("CC")
				|| addtype.equals("BCC")) {
			if (addtype.equals("TO")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.TO);
			} else if (addtype.equals("CC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.CC);
			} else {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.BCC);
			}
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					// �Ȼ�ȡ�ʼ���ַ
					String email = address[i].getAddress();
					if (email == null){
						email = "";
					}else {
						email = MimeUtility.decodeText(email);
					}
					// ��ȡ�ø���������Ϣ
					String personal = address[i].getPersonal();
					if (personal == null){
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					// ������������Ϣ���ʼ���ַ������
					String compositeto = personal + "<" + email + ">";
					// �����ַʱ���ö��ŷֿ�
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new Exception("����ĵ�ַ���ͣ�!");
		}
		return mailaddr;
	}

	/**
	 * ��÷����˵ĵ�ַ������
	 * @throws Exception
	 */
	private String getFrom() throws Exception {
		return getFrom(this.currentMessage);
	}

	private String getFrom(Message mimeMessage) throws Exception {
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		// ��÷����˵�����
		String from = address[0].getAddress();
		if (from == null){
			from = "";
		}
		// ��÷����˵�������Ϣ
		String personal = address[0].getPersonal();
		if (personal == null){
			personal = "";
		}
		// ƴ�ɷ�����������Ϣ
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * ��ȡmessages��message������
	 * @return
	 */
	private int getMessageCount() {
		return this.messages.length;
	}

	/**
	 * ����ռ��������ʼ�������
	 * @return
	 * @throws MessagingException
	 */
	private int getNewMessageCount() throws MessagingException {
		return this.folder.getNewMessageCount();
	}

	/**
	 * ����ռ�����δ���ʼ�������
	 * @return
	 * @throws MessagingException
	 */
	private int getUnreadMessageCount() throws MessagingException {
		return this.folder.getUnreadMessageCount();
	}

	/**
	 * ����ʼ�����
	 */
	private String getSubject() throws MessagingException {
		return getSubject(this.currentMessage);
	}

	private String getSubject(Message mimeMessage) throws MessagingException {
		String subject = "";
		try {
			// ���ʼ��������
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null){
				subject = "";
			}
		} catch (Exception exce) {
		}
		return subject;
	}

	/**
	 * ����ʼ���������
	 */
	private Date getSentDate() throws Exception {
		return getSentDate(this.currentMessage);
	}

	private Date getSentDate(Message mimeMessage) throws Exception {
		return mimeMessage.getSentDate();
	}

	/**
	 * �жϴ��ʼ��Ƿ���Ҫ��ִ�������Ҫ��ִ����"true",���򷵻�"false"
	 */
	private boolean getReplySign() throws MessagingException {
		return getReplySign(this.currentMessage);
	}

	private boolean getReplySign(Message mimeMessage) throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage
				.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * ��ô��ʼ���Message-ID
	 */
	private String getMessageId() throws MessagingException {
		return getMessageId(this.currentMessage);
	}

	private String getMessageId(Message mimeMessage) throws MessagingException {
		return ((MimeMessage) mimeMessage).getMessageID();
	}

	/**
	 * �жϴ��ʼ��Ƿ��Ѷ������δ�����ط���false,��֮����true
	 */
	private boolean isNew() throws MessagingException {
		return isNew(this.currentMessage);
	}
	private boolean isNew(Message mimeMessage) throws MessagingException {
		boolean isnew = false;
		Flags flags = mimeMessage.getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}

	/**
	 * �жϴ��ʼ��Ƿ��������
	 */
	private boolean isContainAttach() throws Exception {
		return isContainAttach(this.currentMessage);
	}
	private boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		if (part.isMimeType("multipart/*")) {
			// ����ʼ�������ಿ��
			Multipart mp = (Multipart) part.getContent();
			// ����ÿ����
			for (int i = 0; i < mp.getCount(); i++) {
				// ���ÿ���ֵ�����
				BodyPart bodyPart = mp.getBodyPart(i);
				String disposition = bodyPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
								.equals(Part.INLINE)))){
					attachflag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) bodyPart);
				} else {
					String contype = bodyPart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1){
						attachflag = true;
					}
					if (contype.toLowerCase().indexOf("name") != -1){
						attachflag = true;
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	
	/**
	 * ��õ�ǰ�ʼ�
	 */
	private void getMail() throws Exception {
		try {
			this.saveMessageAsFile(currentMessage);
			this.parseMessage(currentMessage);
		} catch (IOException e) {
			throw new IOException("�����ʼ�������鱣��·��");
		} catch (MessagingException e) {
			throw new MessagingException("�ʼ�ת������");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("δ֪����");
		}
	}
	
	/**
	 * �����ʼ�Դ�ļ�
	 */
	private void saveMessageAsFile(Message message) {
		try {
			// ���ʼ���ID�м������еĲ�����Ϊ�ʼ����ļ���
			String oriFileName = getInfoBetweenBrackets(this.getMessageId(message)
					.toString());
			//�����ļ���׺�������Ǹ������跨ȡ�����ļ���׺����Ϊ��Ҫ�����ļ��ĺ�׺����
			//�������Ĳ�������.htm����׺��
			String emlName = oriFileName;
			String fileNameWidthExtension = this.receiverInfo.getEmailDir()
					+ oriFileName + this.receiverInfo.getEmailFileSuffix();
			File storeFile = new File(fileNameWidthExtension);
			for (int i = 0; storeFile.exists(); i++) {
				emlName = oriFileName + i;
				fileNameWidthExtension = this.receiverInfo.getEmailDir()
						+ emlName + this.receiverInfo.getEmailFileSuffix();
				storeFile = new File(fileNameWidthExtension);
			}
			this.currentEmailFileName = emlName;
			System.out.println("�ʼ���Ϣ�Ĵ洢·��: " + fileNameWidthExtension);
			// ���ʼ���Ϣ������д��ByteArrayOutputStream����
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			// ��ȡ�ʼ���Ϣ���е�����
			StringReader in = new StringReader(baos.toString());
			// �洢���ļ�
			saveFile(fileNameWidthExtension, in);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * �����ʼ�
	 */
	private void parseMessage(Message message) throws IOException,
			MessagingException {
		Object content = message.getContent();
		// ����ಿ���ʼ�
		if (content instanceof Multipart) {
			handleMultipart((Multipart) content);
		} else {
			handlePart(message);
		}
	}

	/*
	 * ����Multipart
	 */
	private void handleMultipart(Multipart multipart) throws MessagingException,
			IOException {
		for (int i = 0, n = multipart.getCount(); i < n; i++) {
			handlePart(multipart.getBodyPart(i));
		}
	}
	/*
	 * ����ָ��part,������ȡ�ļ�
	 */
	private void handlePart(Part part) throws MessagingException, IOException {
		String disposition = part.getDisposition();
		String contentType = part.getContentType();
		String fileNameWidthExtension = "";
		// ����ʼ�������������
		InputStreamReader sbis = new InputStreamReader(part.getInputStream());
		// û�и��������
		if (disposition == null) {
			if ((contentType.length() >= 10)
					&& (contentType.toLowerCase().substring(0, 10)
							.equals("text/plain"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".txt";
			} else if ((contentType.length() >= 9) // Check if html
					&& (contentType.toLowerCase().substring(0, 9)
							.equals("text/html"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".html";
			} else if ((contentType.length() >= 9) // Check if html
					&& (contentType.toLowerCase().substring(0, 9)
							.equals("image/gif"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".gif";
			} else if ((contentType.length() >= 11)
					&& contentType.toLowerCase().substring(0, 11).equals(
							"multipart/*")) {
//				System.out.println("multipart body: " + contentType);
				handleMultipart((Multipart) part.getContent());
			} else { // Unknown type
//				System.out.println("Other body: " + contentType);
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".txt";
			}
			// �洢�����ļ�
			System.out.println("�����ʼ����ݵ���" + fileNameWidthExtension);
			saveFile(fileNameWidthExtension, sbis);

			return;
		}

		// �����и��������
		String name = "";
		if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
			name = getFileName(part);
//			System.out.println("Attachment: " + name + " : "
//					+ contentType);
			fileNameWidthExtension = this.receiverInfo.getAttachmentDir() + name;
		} else if (disposition.equalsIgnoreCase(Part.INLINE)) {
			name = getFileName(part);
//			System.out.println("Inline: " + name + " : "
//					+ contentType);
			fileNameWidthExtension = this.receiverInfo.getAttachmentDir() + name;
		} else {
//			System.out.println("Other: " + disposition);
		}
		// �洢���฽��
		if (!fileNameWidthExtension.equals("")) {
			System.out.println("�����ʼ���������" + fileNameWidthExtension);
			saveFile(fileNameWidthExtension, sbis);
		}
	}
	private String getFileName(Part part) throws MessagingException,
			UnsupportedEncodingException {
		String fileName = part.getFileName();
		fileName = MimeUtility.decodeText(fileName);
		String name = fileName;
		if (fileName != null) {
			int index = fileName.lastIndexOf("/");
			if (index != -1) {
				name = fileName.substring(index + 1);
			}
		}
		return name;
	}
	/**
	 * �����ļ�����
	 * @param fileName	�ļ���
	 * @param input		������
	 * @throws IOException
	 */
	private void saveFile(String fileName, Reader input) throws IOException {

		// Ϊ�˷����ļ������������������ļ���������������
		File file = new File(fileName);
		// ��ȡ���ļ����ĺ�׺
		int lastDot = fileName.lastIndexOf(".");
		String extension = fileName.substring(lastDot);
		fileName = fileName.substring(0, lastDot);
		for (int i = 0; file.exists(); i++) {
			//������ļ������������i
			file = new File(fileName + i + extension);
		}
		// ���������ж�ȡ���ݣ�д���ļ������
		FileWriter fos = new FileWriter(file);
		BufferedWriter bos = new BufferedWriter(fos);
		BufferedReader bis = new BufferedReader(input);
		int aByte;
		while ((aByte = bis.read()) != -1) {
			bos.write(aByte);
		}
		// �ر���
		bos.flush();
		bos.close();
		bis.close();
	}

	/**
	 * ��ü�����֮����ַ�
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private String getInfoBetweenBrackets(String str) throws Exception {
		int i, j; //���ڱ�ʶ�ַ����е�"<"��">"��λ��
		if (str == null) {
			str = "error";
			return str;
		}
		i = str.lastIndexOf("<");
		j = str.lastIndexOf(">");
		if (i != -1 && j != -1){
			str = str.substring(i + 1, j);
		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		MailReceiverInfo receiverInfo = new MailReceiverInfo();
		receiverInfo.setMailServerHost("pop.163.com");
		receiverInfo.setMailServerPort("110");
		receiverInfo.setValidate(true);
		receiverInfo.setUserName("***");
		receiverInfo.setPassword("***");
		receiverInfo.setAttachmentDir("C:/temp/mail/");
		receiverInfo.setEmailDir("C:/temp/mail/");

		MailReceiver receiver = new MailReceiver(receiverInfo);
		receiver.receiveAllMail();
	}
}