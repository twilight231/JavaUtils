package com.zstax.util.常用类.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ��ȡIP��ַ�ͻ�����
 */
public class GetIPAddress {

	/**
	 * ��ȡ������IP��ַ
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIP() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	/**
	 * ��ȡ�����Ļ�����
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}
	/**
	 * �����������������IP��ַ
	 * @param hostName	����
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIPByName(String hostName)
			throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(hostName);
		return addr.getHostAddress();
	}
	/**
	 * ������������������е�IP��ַ
	 * @param hostName	����
	 * @return
	 * @throws UnknownHostException
	 */
	public static String[] getAllIPByName(String hostName)
			throws UnknownHostException {
		InetAddress[] addrs = InetAddress.getAllByName(hostName);
		String[] ips = new String[addrs.length];
		for (int i = 0; i < addrs.length; i++) {
			ips[i] = addrs[i].getHostAddress();
		}
		return ips;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		// ��ȡ������IP��ַ�ͻ�����
		System.out.println("Local IP: " + GetIPAddress.getLocalIP());
		System.out.println("Local HostName: " + GetIPAddress.getLocalHostName());

		// ���΢����վ��IP
		String hostName = "www.microsoft.com";
		System.out.println("����Ϊ" + hostName + "��������IP��ַ��");
		System.out.println(GetIPAddress.getIPByName(hostName));

		System.out.println("����Ϊ" + hostName + "������������IP��ַ��");
		String[] ips = GetIPAddress.getAllIPByName(hostName);
		for (int i = 0; i < ips.length; i++) {
			System.out.println(ips[i]);
		}
	}
}
