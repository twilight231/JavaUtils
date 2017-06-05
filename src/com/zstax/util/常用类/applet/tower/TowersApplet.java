package com.zstax.util.常用类.applet.tower;

import java.applet.Applet;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ��ŵ����Ϸ��Applet
 */
public class TowersApplet extends Applet implements ActionListener,
		MouseListener {

	// ��Ϸ�Զ�ִ�е����
	private AutoPlayer autoPlayer;
	// ��Ϸ����
	public GameGroup theGameGroup;

	// �Ƿ����¿�ʼ��Ϸ��ǩ
	private boolean restartFlag;
	// ��Ϸ�����ϵ�����
	private TextField diskNumTF;

	// ������Ϸ��ť
	public Button newButton;
	// ������Ϸ��ť
	public Button stepButton;
	// �Զ���Ϸ��ť
	public Button autoRunButton;
	
	public TowersApplet() {
		restartFlag = false;
		diskNumTF = new TextField("4", 5);
	}
	
	// ��ʼ��Applet
	public void init() {
		// ��Ӱ�ť���ı�������AWT���
		addMouseListener(this);
		setLayout(new FlowLayout());
		Panel panel = new Panel();
		add(panel);
		panel.setLayout(new FlowLayout());
		Panel panel1 = new Panel();
		panel.add(panel1);
		panel1.setLayout(new FlowLayout(0));
		newButton = new Button("New");
		panel1.add(newButton);
		newButton.addActionListener(this);
		stepButton = new Button("Step");
		panel1.add(stepButton);
		stepButton.addActionListener(this);
		autoRunButton = new Button("Run");
		panel1.add(autoRunButton);
		autoRunButton.addActionListener(this);
		Panel panel2 = new Panel();
		panel.add(panel2);
		panel2.setLayout(new FlowLayout(2));
		panel2.add(new Label("Enter number: "));
		panel2.add(diskNumTF);
		theGameGroup = new GameGroup(Constants.INIT_DISK_NUMS);
		repaint();
	}

	public void start() {
	}

	public void stop() {
		if (autoPlayer != null) {
			autoPlayer.running = false;
		}
	}
	// �ػ���Ļʱ��ˢ����Ϸ����
	public void paint(Graphics g) {
		theGameGroup.draw(g);
	}
	public void update(Graphics g) {
		paint(g);
	}

	// ����ť�¼�
	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == newButton) {
			theGameGroup.setStep(false);
			//�½���Ϸ��������������������¹�����Ϸ����
			// ֻ���������ε���ð�ť�����½���Ϸ�����а���һ�������������û��ģ��ڶ��β���Ч
			if (restartFlag) {
				restartFlag = false;
				// �ж�����������Ƿ�Ϊ����
				String s = diskNumTF.getText();
				boolean isNumber = true;
				int GPNumber = 0;
				try {
					GPNumber = Integer.parseInt(s);
				} catch (NumberFormatException _ex) {
					isNumber = false;
				}
				// �������Ĳ�������������������Ϣ
				if (!isNumber || GPNumber > 10 || GPNumber < 1){
					theGameGroup.creationError();
				} else {
					// �����½���Ϸ����
					theGameGroup = new GameGroup(GPNumber);
				}
			} else {
				// ��һ�ε���ð�ťʱ�����û�ȷ���Ƿ�����Ϸ
				restartFlag = true;
				theGameGroup.warningNew();
			}
			
		} else if (actionevent.getSource() == stepButton) {
			// ����ִ����Ϸ
			restartFlag = false;
			theGameGroup.step();
			
		} else if (actionevent.getSource() == autoRunButton) {
			// �Զ�ִ����Ϸ������һ���̡߳� 
			restartFlag = false;
			theGameGroup.setStep(false);
			if (theGameGroup.canAutoRun()){
				theGameGroup.setDone(false);
				autoPlayer = new AutoPlayer(this);
				autoPlayer.start();
			}
		}
		repaint();
		// ������������ĵ����ť����Ŀ�����
		try {
			Thread.sleep(10);
			return;
		} catch (InterruptedException _ex) {
			return;
		}
	}

	public void mousePressed(MouseEvent mouseevent) {
		// ��ȡ����갴�µ�����
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		// ��������Ϸ��ǩΪfalse����Ϊ���Ѿ����������������Ϸ��ť�ˡ�
		restartFlag = false;
		// ����ǵ�������ʼ�ƶ���
		if (mouseevent.getClickCount() == 1){
			theGameGroup.startDrag(i, j);
		}
	}
	public void mouseReleased(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		// �ɿ���ťʱ��ֹͣ�ƶ�
		theGameGroup.endDrag(i, j);
		repaint();
	}
	public void mouseEntered(MouseEvent mouseevent) {
	}
	public void mouseExited(MouseEvent mouseevent) {
	}
	public void mouseClicked(MouseEvent mouseevent) {
	}
}

/**
 * ��Ϸ�Զ���ң�ģ���û��溺ŵ����Ϸ
 */
class AutoPlayer extends Thread{
	TowersApplet towers;
	public boolean running = false;
	public AutoPlayer(TowersApplet towers){
		this.towers = towers;
	}
	public void run(){
		running = true;
		// �Զ�����Ϸʱ�����а�ťʧЧ
		this.towers.stepButton.setEnabled(false);
		this.towers.newButton.setEnabled(false);
		this.towers.autoRunButton.setEnabled(false);
		// �����Ϸû�гɹ�����һֱ����һ��
		while (running && !this.towers.theGameGroup.isDone()){
			this.towers.theGameGroup.step();
			this.towers.repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException _ex) {
			}
		}
		// �Զ�����Ϸ�����ǽ���ť�ָ�
		this.towers.stepButton.setEnabled(true);
		this.towers.newButton.setEnabled(true);
		this.towers.autoRunButton.setEnabled(true);
	}
}