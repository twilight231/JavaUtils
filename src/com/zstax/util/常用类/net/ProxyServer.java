package com.zstax.util.常用类.net;

import java.io.*;
import java.net.*;

/**
 * ����Ϊ֧�ֶ��̷߳�������ܵ�GeneralServer���ṩ��һ����صķ���Service��
 * ʹGeneralServer�ṩ��������ڲ���ProxyServiceʵ����GeneralServer.Service�ӿڡ�
 **/
public class ProxyServer {
	// �����еĸ�ʽ�ǣ�������������ӵ�Ŀ����������������˿��Լ�����������ı��ض˿�
	public static final String usage =  
		"Usage: java book.net.ProxyServer <remotehost> <remoteport> <localport> ...";
    /** 
     * ����һ��GeneralServer���󣬲���Ӵ������ProxyService��������������ϡ�
     **/
    public static void main(String[] args) {
        try {
            // �������
            if ((args.length == 0) || (args.length % 3 != 0)){
                throw new IllegalArgumentException("Wrong number of args");
            }
	    
            // ����һ����������GeneralServer�������ڹ������
            // ��һ������Ϊnull��ʾ����������־����ڱ�׼����ϣ��ڶ�������12��ʾ���������
            GeneralServer server = new GeneralServer(null, 12);
	    
            // ���������н������������Դ�������������
            int i = 0;
            while(i < args.length) {
            	// Ŀ��������������͵�ַ
                String remotehost = args[i++];
                int remoteport = Integer.parseInt(args[i++]);
                // ��������ڱ��صĶ˿�
                int localport = Integer.parseInt(args[i++]);
                // ����һ�����ӵ�Ŀ��������Ĵ��������󣬲������ڱ��ط������ϵ�localport�˿��ϡ�
                server.addService(new ProxyService(remotehost, remoteport), localport);
            }
        }
        catch (Exception e) {
            System.err.println(e);
            System.err.println(usage);
            System.exit(1);
        }
    }
    
    /**
     * ʵ�ִ������Ĺ��ܣ�ʵ����GeneralServer.Service�ӿڵ�serve������
     * ������Ŀ��������������ӣ�Ȼ�����Կͻ��˵������������Ŀ��������ˣ�
     * ������Ŀ���������������������ͻ��ˡ�
     * ��������������������ͨ��2���߳�ʵ�ֵġ�
     */
    public static class ProxyService implements GeneralServer.Service {
    	// Ŀ��������������Ͷ˿�
        String remotehost;
        int remoteport;
	
        /**
         * ���췽��
         */
        public ProxyService(String host, int port) {
            this.remotehost = host;
            this.remoteport = port;
        }
	
        /**
         * ���ͻ��˵���GeneralServer�ϵĴ������ʱ��������serve����
         */
        public void serve(InputStream in, OutputStream out) {
        	// ���Կͻ��˵�������
            final InputStream from_client = in;
            // ������ͻ��˵������
            final OutputStream to_client = out;
            // ��Ŀ������������������
            final InputStream from_server;
            final OutputStream to_server;

            // ���ӵ�Ŀ���������socket����
            final Socket server;
			try {
				// ����Ŀ�����������ʼ��from_server��to_server��
				server = new Socket(remotehost, remoteport);
				from_server = server.getInputStream();
				to_server = server.getOutputStream();
			} catch (Exception e) {
				// �������ʧ�ܣ�����ͻ��˷���ʧ�ܵ���Ϣ
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				pw.print("Proxy server could not connect to " + remotehost
						+ ":" + remoteport + "\n");
				pw.flush();
				pw.close();
				try {
					in.close();
				} catch (IOException ex) {
				}
				return;
			}
	    
            // ����һ���̣߳������Կͻ��˵��������ֽ������Ŀ���������
            Thread client2server = new Thread() {
				public void run() {
					byte[] buffer = new byte[2048];
					int bytes_read;
					try {
						// �����Կͻ��˵��������ж�ȡ���ݣ�д��Ŀ���������
						while ((bytes_read = from_client.read(buffer)) != -1) {
							to_server.write(buffer, 0, bytes_read);
							to_server.flush();
						}
					} catch (IOException e) {
					} finally {
						// ���߳̽���ʱ���ر���Ŀ���������socket���Ӻ�2�����������
						try {
							server.close(); 
							to_client.close(); 
							from_client.close();
						} catch (IOException e) {
						}
					}
				}
			};

			// ����һ���̣߳����ڽ�����Ŀ���������������������ͻ���
			Thread server2client = new Thread() {
				public void run() {
					byte[] buffer = new byte[2048];
					int bytes_read;
					try {
						// ��ȡ����Ŀ���������������from_server��Ȼ�������to_client
						while ((bytes_read = from_server.read(buffer)) != -1) {
							to_client.write(buffer, 0, bytes_read);
							to_client.flush();
						}
					} catch (IOException e) {
					} finally {
						try {
							server.close(); 
							to_client.close();
							from_client.close();
						} catch (IOException e) {
						}
					}
				}
			};

			// �����������߳�
			client2server.start();
			server2client.start();

			// ����������߳����н���ʱ�����η���Ž���
			try {
				client2server.join();
				server2client.join();
			} catch (InterruptedException e) {
			}
        }
    }
}
