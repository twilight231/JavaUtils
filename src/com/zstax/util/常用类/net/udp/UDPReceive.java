package com.zstax.util.常用类.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * �ó����������ָ���˿ڵ�UDP���ġ�
 **/
public class UDPReceive {
	// ������Ϣ
	public static final String usage = "Usage: java book.net.udp.UDPReceive <port>";

	public static void main(String args[]) {
		try {
			if (args.length != 1){
				throw new IllegalArgumentException("Wrong number of args");
			}
			// ���������л�ȡ�˿ںŲ���
			int port = Integer.parseInt(args[0]);

			// ����һ��socket����������˿ڡ�
			DatagramSocket dsocket = new DatagramSocket(port);

			// ������յ���UDP���ĵ��ֽ�����
			byte[] buffer = new byte[2048];

			// ����һ��DatagramPacket�����յ��ı���д��buffer�С�
			// ע�⣬����ָ���˱��ĵĳ��ȣ�����յ���UDP���ı�2048�󣬶������Ϣ������
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// ����ѭ������������
			for ( ; ;) {
				// �ȴ��յ�һ�����ݰ�
				dsocket.receive(packet);

				// ���յ��ı��ĵ��ֽ������װ���ַ�����
				String msg = new String(buffer, 0, packet.getLength());
				// �����ݰ���ȡ����Ϣ��Դ�ĵ�ַ
				System.out.println("Receive: " + packet.getAddress().getHostAddress() + ": "
						+ msg);

				// ����յ�QUITָ����˳�ѭ����
				if (msg.equals("QUIT")){
					System.out.println("Exit the UDPReceive!");
					break;
				}
				
				// �������ݰ��ĳ���
				packet.setLength(buffer.length);
			}
			// �ر�socket
			dsocket.close();
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(usage);
		}
	}
}
