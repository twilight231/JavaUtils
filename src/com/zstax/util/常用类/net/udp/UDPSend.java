package com.zstax.util.常用类.net.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * ��ʵ��ʵ��һ������UDP���ĵ��ࡣ
 * UDP��TCP��ͬ���ڣ�UDP�ڷ��ͱ���ǰ���轨�����ӣ�ֱ�ӷ��ͣ���TCP��Ҫ�������ӡ�
 * ��TCP��UDP���ɿ������⣬UDP���Ļ�����������������Ϊ���Ͷ˺ͽ��ն˵ı��Ĵ�С���ܲ�һ�¡�
 * �����ڱ��ʱ��UDP�������ServerSocket��Socket��ֻ��ҪDatagramSocket�༴�ɡ�
 **/
public class UDPSend {
    public static final String usage = 
	"Usage: java book.net.udp.UDPSend <hostname> <port> <msg>...\n" +
	"   or: java book.net.udp.UDPSend <hostname> <port> -f <file>";

    public static void main(String args[]) {
        try { 
            // ����������
            if (args.length < 3){ 
                throw new IllegalArgumentException("Wrong number of args");
            }
            
            // �����Ͷ˿�
            String host = args[0];
            int port = Integer.parseInt(args[1]);
	    
            // ���湹������ͱ��ĵ��ֽ�����  
            byte[] message;
            if (args[2].equals("-f")) {
            	// �������������Ϊ -f�����ʾ���ļ���������UDP��ʽ����
            	// ��ô����͵��ļ������Լ��ļ��ĳ���
                File f = new File(args[3]);
                int len = (int)f.length(); 
                // ����һ���㹻�����ļ����ݵ��ֽ�����
                message = new byte[len]; 
                FileInputStream in = new FileInputStream(f);
                // ���ļ��������ֽڵķ�ʽ�����ֽ�������
                int bytes_read = 0, n;
                do {
                    n = in.read(message, bytes_read, len-bytes_read);
                    bytes_read += n;
                } while((bytes_read < len)&& (n != -1));
            }
            else { 
            	// ����������������� -f���򽫺���Ĳ���������Ϣ����
                String msg = args[2];  
                for (int i = 3; i < args.length; i++){
                	msg += " " + args[i];
                }
                message = msg.getBytes();
            }
            
            // ����������ȡIP��ַ
            InetAddress address = InetAddress.getByName(host);
	    
            // ��ʼ��һ��UDP����
            // DatagramPacket�Ĺ��췽���б���ʹ��InetAddress����������IP��ַ��������
            DatagramPacket packet = new DatagramPacket(message, message.length,
						       address, port);
	    
            // ����һ��DatagramSocket���Է���UDP��
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            System.out.println("send: " + new String(message));
            dsocket.close();
            
            // ע�⣺����ڹ���DatagramPacketʱ�����ṩIP��ַ�Ͷ˿ںţ�
            // ����Ҫ����DatagramSocket��connect�����������޷�����UDP��
            packet = new DatagramPacket(message, message.length);
            dsocket = new DatagramSocket();
            dsocket.connect(address, port);
            dsocket.send(packet);
            System.out.println("Send: " + new String(message));
            dsocket.close();
            
        } catch (Exception e) {
            System.err.println(e);
            System.err.println(usage);
        }
    }
}
