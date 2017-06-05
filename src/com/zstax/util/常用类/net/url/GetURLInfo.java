package com.zstax.util.常用类.net.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * ������ʾͨ��URL�ܹ��õ�����Ϣ
 **/
public class GetURLInfo {
	/**
	 * ���һ��URL��ص���Ϣ����Ҫʹ����URLConnection��
	 */
    public static void printInfo(URL url) throws IOException {
    	// URL������Ϣ
    	System.out.println("  File: " + url.getFile());
    	System.out.println("  Protocol: " + url.getProtocol());
    	System.out.println("  Host: " + url.getHost());
    	System.out.println("  Port: " + url.getPort());
    	System.out.println("  Path: " + url.getPath());
    	
    	// ��ȡURLConnection����
        URLConnection c = url.openConnection();
        // ���ӵ�URL������������ӵ�URL�������ֳ�ʱ��Ϣ��
        c.connect();
	
        // ��ʾ��Ϣ
        System.out.println("  Content Type: " + c.getContentType());
        System.out.println("  Content Encoding: " + c.getContentEncoding());
        System.out.println("  Content Length: " + c.getContentLength());
        System.out.println("  Date: " + new Date(c.getDate()));
        System.out.println("  Last Modified: " +new Date(c.getLastModified()));
        System.out.println("  Expiration: " + new Date(c.getExpiration()));
	
        // �����HTTP���ӣ������ṩ���ḻ����Ϣ
        if (c instanceof HttpURLConnection) {
            HttpURLConnection h = (HttpURLConnection) c;
            System.out.println("  Request Method: " + h.getRequestMethod());
            System.out.println("  Response Message: " +h.getResponseMessage());
            System.out.println("  Response Code: " + h.getResponseCode());
        }
    }
    
	public static void main(String[] args) {
		try {
			String urlStr = "http://www.sina.com.cn:80/index.htm";
			URL url = new URL(urlStr);
			printInfo(url);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
