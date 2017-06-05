package com.zstax.util.常用类.applet;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.text.DateFormat;
import java.util.Date;

/**
 * ����ʵ��һ��Appletʱ�ӣ���ʾApplet�����������ڡ�
 */
public class Clock extends Applet implements Runnable {
	// ��ʾʱ��ı�ǩ
    Label time; 
    // ʱ��ĸ�ʽ
    DateFormat timeFormat;
    // ����ʱ����߳�
    Thread timer; 
    // �߳��Ƿ����еı�־λ
    boolean running;

    /**
     * ��ʼ��Applet���ڵ�һ�μ���Appletʱʹ��
     **/
    public void init() {
    	// ����ʱ���ǩ
        time = new Label();
        time.setFont(new Font(null, Font.BOLD, 15));
        time.setBounds(0,0, 100, 50);
        time.setAlignment(Label.CENTER);
        // ����ǩ���뵽Applet����
        setLayout(new BorderLayout());
        add(time, BorderLayout.CENTER);
        timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    }
    
    /**
     * ����Appletʱ���ô˷������������������߳�
     */
    public void start() {
        running = true;  
        if (timer == null) { 
            timer = new Thread(this);
            timer.start();
        }
    }

    /**
     * �̵߳������壬���ϵ�ˢ��ʱ���ǩ��ֵ
     */
    public void run() {
		while (running) {
			time.setText(timeFormat.format(new Date()));
			// ����1����
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		timer = null;
	}

    /**
     * �����ֹͣAppletʱ���ø÷���
     */
    public void stop() {
    	// �ر��߳�
    	running = false;
	} 
    /**
     * ��������Appletʱ���ø÷���
     */
    public void destroy() {
	} 
}
