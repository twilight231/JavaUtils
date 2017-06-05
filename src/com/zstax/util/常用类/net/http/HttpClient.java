package com.zstax.util.常用类.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ����HTTP�Ŀͻ��ˡ�����ҳ��������ʾ�ڿ���̨�С�����õ�����һ��HTML����
 */
public class HttpClient {
	//��ַURL 
	String urlString;

	public static void main(String[] args) throws Exception {
		// ��һ������Ϊ��ַ
		if (args.length != 1) {
			System.out.println("Usage: java book.net.http.HttpClient url");
			System.exit(1);
		}
		HttpClient client = new HttpClient(args[0]);
		client.run();
	}

	public HttpClient(String urlString) {
		this.urlString = urlString;
	}

	public void run() throws Exception {
		//����һ��URL���� 
		URL url = new URL(urlString);
		//��URL 
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		//��ӡͷ��Ϣ 
		System.out.println("THE HEADERS");
		System.out.println("-----------");
		for (int i = 1;; ++i) {
			String key;
			String value;
			if ((key = urlConnection.getHeaderFieldKey(i)) == null)
				break;
			if ((value = urlConnection.getHeaderField(i)) == null)
				break;
			System.out.print(key);
			System.out.println(" is: " + value);
		}
		//�õ������������������ҳ������ 
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection
				.getInputStream()));
		String line;
		System.out.println("-----CONTENT------");
		// ��ȡ�����������ݣ�����ʾ
		while ((line = reader.readLine()) != null){
			System.out.println(line);
		}
	}
}