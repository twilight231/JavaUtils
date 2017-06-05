package com.zstax.util.常用类.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Telnet�ͻ��ˣ����Ե�½��Telnet��������
 */
public class TelnetClient {

	// Ĭ�ϵ�Telnet��������
	public static final String DEFAULT_HOST = "127.0.0.1";
	// Ĭ�ϵ�Telnet�������˿�
	public static final int DEFAULT_PORT = 23;
	
	// ������������IP���Ͷ˿ں�
    private String host;
    private int port;

    // ����������socket
    Socket socket = null;
    // �������ݺͽ������ݵĹܵ���PipeΪ�Զ�����
    Pipe sendPipe = null;
    Pipe receivePipe = null;
    
    // Ĭ�Ϲ��췽��
    public TelnetClient(){
    	this.host = DEFAULT_HOST;
    	this.port = DEFAULT_PORT;
    }
    public TelnetClient(String host, int port){
    	this.host = host;
    	this.port = port;
    }

    /**
     * ��½��������
     */
    public void telnet() {
        System.out.println("Connecting to telnet server " + host + ": " + port);
        try {
            socket = new Socket(host, port);

            // ��socket����������ݣ����Է������ˣ����򱾵ر�׼���������������
            receivePipe = new Pipe(socket.getInputStream(), System.out);
            receivePipe.start();
            // �����صı�׼������������socket������ˣ�������������ˣ���������
            sendPipe = new Pipe(System.in, socket.getOutputStream());
            sendPipe.start();
        } catch(IOException e) {
            System.out.println("����ʧ�ܣ�" + e);
            return;
        }
        System.out.println("���ӳɹ�");
    }
    /**
     * �Ͽ�����
     */
    public void disconnect() {
    	if (socket != null){
    		try {
				socket.close();
				System.out.println("�ɹ��Ͽ�����");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void main(String[] argv) {
    	new TelnetClient().telnet();
    }
}

/**
 * �ܵ��࣬��������������д�뵽������С�
 * ��һ���̣߳����Զ�������
 */
class Pipe extends Thread {

	// �ܵ����������������
    BufferedReader is;
    PrintStream os;

    /**
     * ���췽�����������������
     * @param is
     * @param os
     */
    Pipe(InputStream is, OutputStream os) {
        this.is = new BufferedReader(new InputStreamReader(is));
        this.os = new PrintStream(os);
    }
    
    /**
     * �߳��巽������������������д�뵽�����
     */
    public void run() {

        String line;
        try {
        	// ��ȡ������������
            while ((line = is.readLine()) != null) {
            	// д���������
                os.print(line);
                os.print("\r\n");
                os.flush();
            }
        } catch(IOException e){
        }
    }
}
