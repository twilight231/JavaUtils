package com.zstax.util.常用类.applet.tongxin;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.TextField;

/**
 * ˵��:Applet���п���ȡ����ҳ�������ľ��,���,ͬ��ҳ�ڵ�����Applet�ǿ��Ի�����ʵ���,
 * �ڱ�����ע��Applet2��HTLM����һ��Ҫ����name="Applet2",�����޷�ȡ��Applet2�þ��.
 * �����ʱ���ȱ���Applet2,����Applet1�в�������Applet2�����.
 */
public class Applet1 extends Applet {
	TextField tf = new TextField("Applet2,�����յ���?", 20);

	//���Ͱ�ť
	Button b = new Button("���͵�Applet2");

	public void init() {
		// ���ò��ֹ�����ΪFlowLayout
		setLayout(new FlowLayout());
		add(tf);
		add(b);
	}

	// ����ť�¼�
	public boolean action(Event ev, Object obj) {
		// ����¼��ǴӰ�ť����
		if (ev.target instanceof Button) {
			String msg = tf.getText();
			// ȡApplet2�ľ��
			Applet2 applet2 = (Applet2) getAppletContext().getApplet("Applet2");
			if (applet2 != null) {
				//	 ����applet2�еĺ���
				applet2.AppendText(msg);
				return true;
			} else {
				tf.setText("û���ҵ�Applet2");
				return false;
			}
		}
		return false;
	}
}