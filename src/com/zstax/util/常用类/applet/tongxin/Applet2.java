package com.zstax.util.常用类.applet.tongxin;

import java.applet.Applet;
import java.awt.*;

public class Applet2 extends Applet {
	TextField text = new TextField("", 30);

	public void init() {
		setLayout(new FlowLayout());
		add(text);
	}

	// ����Ϊ���������Ա�applet1����
	public void AppendText(String msg) {
		text.setText("���յ���!��\"" + msg + "\"");
	}
}
