package com.zstax.util.常用类.net.http;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * HTTP�ķ��������������Կͻ��˵�HTTP����
 * ��Ҫ������HTML�ļ������ڹ��̵ĸ�Ŀ¼�£�
 * Ȼ�������������������"http://localhost:80/"����ַ��
 * ���ܹ���ʾ��ҳ�����ݡ�
 */
public class HttpServer {
	// ���������Ͷ˿�
	String serverName;
	int serverPort;

	// ����server�����֡��汾�š��˿� 
	public HttpServer(String name, int port) {
		this.serverName = name;
		this.serverPort = port;
	}

	public void run() {
		// ��ʾ���ֺͶ˿ں� 
		System.out.println("HttpServer: " + serverName + ": " + serverPort);
		try {
			// �õ������������˿� 
			ServerSocket server = new ServerSocket(serverPort);
			do {
				// �ȴ��������� 
				Socket client = server.accept();
				// Ϊ�����������һ���߳� 
				(new HTTPServerThread(client)).start();
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	// ����һ��server�������� 
	public static void main(String args[]) {
		HttpServer server = new HttpServer("MyHTTPServer", 80);
		server.run();
	}
}

/**
 * ����HTTP������̣߳�һ��HTTP�����Ӧһ���߳�
 */
class HTTPServerThread extends Thread {
	// ��������ͻ���֮���socket
	Socket client;
	public HTTPServerThread(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			// ��ʾ������Ϣ
			describeConnectionInfo(client);
			// ��ȡ���򵽿ͻ��˵������
			BufferedOutputStream outStream = new BufferedOutputStream(client
					.getOutputStream());
			// ��ȡ���Կͻ��˵������� 
			HTTPInputStream inStream = new HTTPInputStream(client
					.getInputStream());
			// �õ��ͻ��˵�����ͷ���Զ����ࣩ 
			HTTPRequest request = inStream.getRequest();
			// ��ʾͷ��Ϣ 
			request.log();
			// Ŀǰֻ����GET���� 
			if (request.isGetRequest()){
				processGetRequest(request, outStream);
			}
			System.out.println("Request completed. Closing connection.");
			//�ر�socket 
			client.close();
		} catch (IOException e) {
			System.out.println("IOException occurred .");
			e.printStackTrace();
		}
	}

	// ��ʾsocket������Ϣ
	void describeConnectionInfo(Socket client) {
		//�ͻ��˵������� 
		String destName = client.getInetAddress().getHostName();
		//�ͻ��˵�IP��ַ 
		String destAddr = client.getInetAddress().getHostAddress();
		//�ͻ��˵Ķ˿� 
		int destPort = client.getPort();
		//��ӡ��Ϣ����ʾ�ͻ����Ѿ����ӵ����������� 
		System.out.println("Accepted connection to " + destName + " ("
				+ destAddr + ")" + " on port " + destPort + ".");
	}

	// ����GET���� 
	void processGetRequest(HTTPRequest request, BufferedOutputStream outStream)
			throws IOException {
		// ��ÿͻ���Ҫget���ļ���
		String fileName = request.getFileName();
		File file = new File(fileName);
		// ����ļ����ڣ����ļ����ݷ��͵�socket������������ͻ��ˡ� 
		if (file.exists()){
			sendFile(outStream, file);
		} else {
			System.out.println("File " + file.getCanonicalPath()
					+ " does not exist.");
		}
	}

	//  �����ļ����ݵ��ͻ��ˣ�������HTTP 1.1��Э��ʵ�ֵ�
	void sendFile(BufferedOutputStream out, File file) {
		try {
			// ���ļ�����ȫ�����뵽һ���ֽ�������
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int len = (int) file.length();
			byte buffer[] = new byte[len];
			// ��ȫ��ȡ��Ȼ��ر��ļ���
			in.readFully(buffer);
			in.close();
			
			// д��socket���������
			out.write("HTTP/1.1 200 OK\r\n".getBytes());
			out.write(("Content-Length: " + buffer.length + "\r\n").getBytes());
			out.write("Content-Type: text/HTML\r\n\r\n".getBytes());
			out.write(buffer);
			out.flush();
			out.close();
			// д�ļ����ݽ�����log��Ϣ
			System.out.println("File sent: " + file.getCanonicalPath());
			System.out.println("Number of bytes: " + len);
		} catch (Exception e) {
			try {
				// ����ʧ��
				out.write(("HTTP/1.1 400 " + "No can do" + "\r\n").getBytes());
				out.write("Content-Type: text/HTML\r\n\r\n".getBytes());
			} catch (IOException ioe) {
			}
			System.out.println("Error retrieving " + file);
		}
	}
}

/**
 * ʵ�ֶ��ͻ�������İ�����
 */
class HTTPInputStream extends FilterInputStream {
	public HTTPInputStream(InputStream in) {
		super(in);
	}

	// ��һ�У�����������û�����ݣ����߶���"\r\n"ʱ��һ�н����� 
	public String readLine() throws IOException {
		StringBuffer result = new StringBuffer();
		boolean finished = false;
		//'\r'Ϊ�س�����ֵΪ13��'\n'Ϊ���з���ֵΪ10
		// cr������ʾ�Ƿ��Ѿ��������з�
		boolean cr = false;
		do {
			int ch = -1;
			// ��һ���ֽ�
			ch = read();
			if (ch == -1){
				return result.toString();
			}
			result.append((char) ch);
			//ȥ������'\r\n' 
			if (cr && (ch == 10)) {
				result.setLength(result.length() - 2);
				return result.toString();
			}
			//�����س��������ñ�ʶ 
			if (ch == 13){
				cr = true;
			} else {
				cr = false;
			}
		} while (!finished);
		return result.toString();
	}

	// �õ����е����� 
	public HTTPRequest getRequest() throws IOException {
		HTTPRequest request = new HTTPRequest();
		String line;
		do {
			// ���ζ�ȡ
			line = readLine();
			//�������������� 
			if (line.length() > 0){
				request.addLine(line);
			} else {
				break;
			}
		} while (true);
		//������
		return request;
	}
}

// �ͻ�������ķ�װ�� 
class HTTPRequest {
	// ��������ݣ����д洢
	Vector lines = new Vector();
	public HTTPRequest() {
	}

	public void addLine(String line) {
		lines.addElement(line);
	}

	// �ж��Ƿ���Get���� 
	boolean isGetRequest() {
		if (lines.size() > 0) {
			// ��ȡ�������ݵĵ�һ�У����ͷ�����ַ���"GET"����ΪGet����
			String firstLine = (String) lines.elementAt(0);
			if (firstLine.length() > 0){
				if (firstLine.substring(0, 3).equalsIgnoreCase("GET")){
					return true;
				}
			}
		}
		return false;
	}

	// �������н������ļ��� 
	// һ���һ�е���Ϣ������ʽ��"GET /hehe.htm HTTP/1.1"
	/**
	 * �������н������ļ�����ֻ��Ҫ�����һ�м��ɡ�
	 * ��һ�еĸ�ʽ�����¼��֣�
	 * ��1����������URLΪ"http://localhost:80/test/hehe.htm"��
	 * ���һ�е�����Ϊ"GET /test/hehe.htm HTTP/1.1"��
	 * ��2����������URLΪ"http://localhost:80"��
	 * ���һ�е�����Ϊ"GET HTTP/1.1"����ʱӦ����Ĭ�ϵ�html�ļ�����index.htm
	 * ��3����������URLΪ"http://localhost:80/test"��
	 * ���һ�е�����Ϊ"GET /test/ HTTP/1.1"����ʱӦ����testĿ¼��Ĭ�ϵ�html�ļ�
	 */
	String getFileName() {
		if (lines.size() > 0) {
			//�õ�vector�е�һ��Ԫ�� 
			String firstLine = (String) lines.elementAt(0);

			System.out.println("firstLine:  " + firstLine);
			//����http��Ϣ��ʽ�õ��ļ���
			String fileName = firstLine.substring(firstLine.indexOf(" ") + 1);
			int n = fileName.indexOf(" ");
			//URL�������ո�֮�� 
			if (n != -1){
				fileName = fileName.substring(0, n);
			}

			//ȥ����һ��'/' 
			try {
				if (fileName.charAt(0) == '/'){
					fileName = fileName.substring(1);
				}
			} catch (StringIndexOutOfBoundsException ex) {
			}

			//Ĭ����ҳ��������Ϊindex.htmΪĬ�ϵ���ҳ
			//������'http://localhost:80'����� 
			if (fileName.equals("")){
				fileName = "index.htm";
			}
			//������'http://localhost:80/download/'����� 
			if (fileName.charAt(fileName.length() - 1) == '/'){
				fileName += "index.htm";
			}
			System.out.println("fileName:  " + fileName);
			return fileName;
		} else {
			return "";
		}
	}

	// ��ʾ������Ϣ 
	void log() {
		System.out.println("Received the following request:");
		for (int i = 0; i < lines.size(); ++i){
			System.out.println((String) lines.elementAt(i));
		}
	}
}