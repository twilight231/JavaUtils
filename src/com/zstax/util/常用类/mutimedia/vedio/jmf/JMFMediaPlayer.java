package com.zstax.util.常用类.mutimedia.vedio.jmf;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * ��Java��JMFʵ��һ��ý�岥���������Բ�����Ƶ����Ƶ
 */
public class JMFMediaPlayer extends JFrame implements ActionListener,
		ControllerListener, ItemListener {
	// JMF�Ĳ�����
	Player player;
	// ����������Ƶ����Ϳ������
	Component vedioComponent; 
	Component controlComponent;

	// ��ʾ�Ƿ��ǵ�һ�δ򿪲�����
	boolean first = true;
	// ��ʾ�Ƿ���Ҫѭ��
	boolean loop = false;
	// �ļ���ǰĿ¼
	String currentDirectory;

	// ���췽��
	public JMFMediaPlayer(String title) {
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				// �û��������ϵͳ�˵��Ĺرհ�ť
				// ����dispose��ִ��windowClosed
				dispose();
			}
			public void windowClosed(WindowEvent e){
				if (player != null){
					// �ر�JMF����������
					player.close();
				}
				System.exit(0);
			}
		});
		// �����������Ĳ˵�
		JMenu fileMenu = new JMenu("�ļ�");
		JMenuItem openMemuItem = new JMenuItem("��");
		openMemuItem.addActionListener(this);
		fileMenu.add(openMemuItem);
		// ���һ���ָ���
		fileMenu.addSeparator();
		// ����һ����ѡ��˵���
		JCheckBoxMenuItem loopMenuItem = new JCheckBoxMenuItem("ѭ��", false);
		loopMenuItem.addItemListener(this);
		fileMenu.add(loopMenuItem);
		fileMenu.addSeparator();
		JMenuItem exitMemuItem = new JMenuItem("�˳�");
		exitMemuItem.addActionListener(this);
		fileMenu.add(exitMemuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		this.setSize(200, 200);
		
		try {
			// ���ý������ۣ�Ϊϵͳ���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setVisible(true);
	}

	/**
	 * ʵ����ActionListener�ӿڣ���������Ļ�¼�
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("�˳�")) {
			// ����dispose�Ա�ִ��windowClosed
			dispose();
			return;
		}
		FileDialog fileDialog = new FileDialog(this, "��ý���ļ�", FileDialog.LOAD);
		fileDialog.setDirectory(currentDirectory);
		fileDialog.setVisible(true);
		// ����û�����ѡ���ļ����򷵻�
		if (fileDialog.getFile() == null){
			return;
		}
		currentDirectory = fileDialog.getDirectory();
		if (player != null){
			// �ر��Ѿ�����JMF����������
			player.close();
		}
		try {
			// ����һ����ѡ���ļ��Ĳ�����
			player = Manager.createPlayer(new MediaLocator("file:"
					+ fileDialog.getDirectory() + fileDialog.getFile()));
		} catch (java.io.IOException e2) {
			System.out.println(e2);
			return;
		} catch (NoPlayerException e2) {
			System.out.println("�����ҵ�������.");
			return;
		}
		if (player == null) {
			System.out.println("�޷�����������.");
			return;
		}
		first = false;
		this.setTitle(fileDialog.getFile());
		// �������Ŀ����¼�����
		player.addControllerListener(this);
		// Ԥ���ļ�����
		player.prefetch();
	}
	/**
	 * ʵ��ControllerListener�ӿڵķ��������������Ŀ����¼�
	 */
	public void controllerUpdate(ControllerEvent e) {
		// ����player.close()ʱControllerClosedEvent�¼����֡�
		// ��������Ӿ���������ò���Ӧ�ò����Ϊһ�������
		// ���ǶԿ�����岿��Ҳִ��ͬ���Ĳ�����
		if (e instanceof ControllerClosedEvent) {
			if (vedioComponent != null) {
				this.getContentPane().remove(vedioComponent);
				this.vedioComponent = null;
			}
			if (controlComponent != null) {
				this.getContentPane().remove(controlComponent);
				this.controlComponent = null;
			}
			return;
		}
		// �����ý���ļ�����β���¼�
		if (e instanceof EndOfMediaEvent) {
			if (loop) {
				// �������ѭ���������¿�ʼ����
				player.setMediaTime(new Time(0));
				player.start();
			}
			return;
		}
		// ����ǲ�����Ԥ���¼�
		if (e instanceof PrefetchCompleteEvent) {
			// ����������
			player.start();
			return;
		}
		// ������ļ�����ȫ�¼�������ʾ��Ƶ����Ϳ��������
		if (e instanceof RealizeCompleteEvent) {
			vedioComponent = player.getVisualComponent();
			if (vedioComponent != null){
				this.getContentPane().add(vedioComponent);
			}
			controlComponent = player.getControlPanelComponent();
			if (controlComponent != null){
				this.getContentPane().add(controlComponent, BorderLayout.SOUTH);
			}
			this.pack();
		}
	}
	
	// ����ѭ������ѡ��˵���ĵ���¼�
	public void itemStateChanged(ItemEvent e) {
		loop = !loop;
	}

	public static void main(String[] args){
		new JMFMediaPlayer("JMFý�岥����");
	}
}