package com.my.util.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;



public class EmailRecieve {
	public static void main(String[] args) {
		String host = "pop3.163.com";

		String username = "mchotdog_011";

		String password = "mchotdog";

		// Create empty properties

		try{
		Properties props = new Properties();

		// Get session

		Session session = Session.getDefaultInstance(props, null);

		// Get the store

		Store store = session.getStore("pop3");

		store.connect(host, username, password);

		// Get folder
		Folder folder = store.getDefaultFolder();
		
		if(folder == null) throw new Exception("No default folder");
		folder = folder.getFolder("INBOX");
		if(folder == null) throw new Exception("No POP3 INBOX");
		folder.open(Folder.READ_ONLY);

		// Get directory

		Message message[] = folder.getMessages();

		for (int i = 0, n = message.length; i < n; i++) {
			System.out.println(message[i]);
			System.out.println(i + ": " + message[i].getFrom()

			+ "\t" + message[i].getSubject());

		}
		// Close connection
		folder.close(false);
		store.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}
}
