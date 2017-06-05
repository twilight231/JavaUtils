package com.zstax.util.常用类.net.simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * һ���򵥵�socket���������ܽ��ܿͻ������󣬲������󷵻ظ��ͻ���
 */
public class SimpleServer {
	// �����������Socket
	ServerSocket serverSkt = null;
	// �ͻ���
	Socket clientSkt = null;

	// �ͻ���������
	BufferedReader in = null;
	// �ͻ��������
	PrintStream out = null;

	// ���췽��
	public SimpleServer(int port){
		System.out.println("�������������ڼ������˿ڣ�" + port);
		try {
			// ��������socket
			serverSkt = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("�����˿�" + port + "ʧ��");
		}
		try {
			// ������������
			clientSkt = serverSkt.accept();
		} catch (IOException e) {
			System.out.println("����ʧ��");
		}
		try {
			// �����������
			in = new BufferedReader(new InputStreamReader(clientSkt
					.getInputStream()));
			out = new PrintStream(clientSkt.getOutputStream());
		} catch (IOException e) {
		}
	}

	// �յ��ͻ�������
	public String getRequest() {
		String frmClt = null;
		try {
			// �ӿͻ��˵��������ж�ȡһ������
			frmClt = in.readLine();
			System.out.println("Server �յ�����:" + frmClt);
		} catch (Exception e) {
			System.out.println("�޷���ȡ�˿�.....");
			System.exit(0);
		}
		return frmClt;
	}

	// ������Ӧ���ͻ���
	public void sendResponse(String response) {
		try {
			// ���ͻ����������д����
			out.println(response);
			System.out.println("Server ��Ӧ����:" + response);
		} catch (Exception e) {
			System.out.println("д�˿�ʧ��......");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {
		// ����������
		SimpleServer sa = new SimpleServer(8888);
		while (true) {
			// ��ȡ�ͻ��˵����벢���ظ��ͻ��ˡ�
			sa.sendResponse(sa.getRequest());
		}
	}

}