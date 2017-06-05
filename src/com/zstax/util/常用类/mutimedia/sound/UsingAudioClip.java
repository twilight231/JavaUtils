package com.zstax.util.常用类.mutimedia.sound;

import java.io.File;
import java.net.MalformedURLException;

/**
 * ֧��.wav/.au/.aiff/.mid�����ļ��Ĳ���
 */
public class UsingAudioClip {
	public static void main(String[] args) {
		// ��wav�ļ�
		String fileName = "C:/temp/test.wav";
		File file = new File(fileName);
		java.applet.AudioClip clip;
		try {
			// ����URL����һ��AudioClip����AudioClip��һ���ӿڡ�
			clip = java.applet.Applet.newAudioClip(file.toURL());
			// ������Ƶ�ļ�
			clip.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
