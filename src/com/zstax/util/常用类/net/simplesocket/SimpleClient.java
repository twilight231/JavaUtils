package com.zstax.util.常用类.net.simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * һ���򵥵�socket�ͻ��ˣ��ܹ�������������socket����
 */
public class SimpleClient {
	
	// �ͻ������������
	PrintStream out;
	BufferedReader in;

	// ���췽��
	public SimpleClient(String serverName, int port) {
		try {
			// ���ݷ��������Ͷ˿ںţ����ӷ�����
			Socket clientSocket = new Socket(serverName, port); 
			// ��ȡsocket�����������
			out = new PrintStream(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket
					.getInputStream()));
		} catch (Exception e) {
			System.out.println("�޷����ӷ�����!");
		}
	}

	// ��������
	public void sendRequest(String request) {
		// ��socket�������д����
		out.println(request); 
		System.out.println("Client ��������: " + request);
	}

	public String getResponse() {
		String str = new String();
		try {
			// ��socket���������ж�ȡ����
			str = in.readLine();
			System.out.println("Client�յ�Server����: " + str);
		} catch (IOException e) {
		} 
		return str;
	}
}

