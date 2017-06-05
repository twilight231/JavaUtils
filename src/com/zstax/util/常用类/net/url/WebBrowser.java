package com.zstax.util.常用类.net.url;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;

/**
 * ʵ��һ���򵥵�Web�������֧��HTML��HTMҳ�����ʾ��ʹ����JEditorPane���
 **/
public class WebBrowser extends JFrame implements HyperlinkListener,
		PropertyChangeListener {
    
    /**������ʹ�õ�Swing���**/
	
	// ��ʾHTML�����
    JEditorPane textPane; 
    // ����µ�״̬��
    JLabel messageLine; 
    // ��ַURL������
    JTextField urlField;
    // �ļ�ѡ�������򿪱����ļ�ʱ��
    JFileChooser fileChooser;
    
    // ���˺�ǰ�� ��ť
    JButton backButton;
    JButton forwardButton;

    // ������ʷ��¼���б�
    java.util.List history = new ArrayList(); 
    // ��ǰҳ�������ʷ��¼�б���λ��
    int currentHistoryPage = -1;  
    // ����ʷ��¼����MAX_HISTORYʱ������ɵ���ʷ
    public static final int MAX_HISTORY = 50;

    // ��ǰ�Ѿ��򿪵������������
    static int numBrowserWindows = 0;
    // ��ʶ��������������ڶ����ر�ʱ���Ƿ��˳�Ӧ�ó���
    static boolean exitWhenLastWindowClosed = false;

    // Ĭ�ϵ���ҳ
    String home = "http://www.google.com";

    /**
     * ���캯��
     */
    public WebBrowser() {
        super("WebBrowser"); 

        // �½���ʾHTML����壬�����������ɱ༭
        textPane = new JEditorPane(); 
        textPane.setEditable(false); 

        // ע���¼������������ڳ������¼���
        textPane.addHyperlinkListener(this);
        // ע���¼������������ڴ������Ըı��¼�����ҳ����ؽ���ʱ���������¼�
        textPane.addPropertyChangeListener(this);

        // ��HTML��ʾ�����������ڣ�������ʾ
        this.getContentPane().add(new JScrollPane(textPane),
                                  BorderLayout.CENTER);

        // ����״̬����ǩ�������������ڵײ�
        messageLine = new JLabel(" ");
        this.getContentPane().add(messageLine, BorderLayout.SOUTH);

        // ��ʼ���˵��͹�����
        this.initMenu();
        this.initToolbar();
        
        // ����ǰ�򿪴���������1
        WebBrowser.numBrowserWindows++;
        
        // ���رմ���ʱ������close��������
        this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
    }
    
    /**
     * ��ʼ���˵���
     */
    private void initMenu(){
    	
    	// �ļ��˵����������ĸ��˵���½����򿪡��رմ��ڡ��˳�
    	JMenu fileMenu = new JMenu("�ļ�");
    	fileMenu.setMnemonic('F');
    	JMenuItem newMenuItem = new JMenuItem("�½�");
    	newMenuItem.setMnemonic('N');
    	// �����½���ʱ��һ�����������
    	newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	newBrowser();
            }
        });
    	
    	JMenuItem openMenuItem = new JMenuItem("��");
    	openMenuItem.setMnemonic('O');
    	// �����򿪡��Ǵ��ļ�ѡ������ѡ����򿪵��ļ�
    	openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	openLocalPage();
            }
        });
    	
    	JMenuItem closeMenuItem = new JMenuItem("�رմ���");
    	closeMenuItem.setMnemonic('C');
    	// �����رմ��ڡ�ʱ�رյ�ǰ����
    	closeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
    	
    	JMenuItem exitMenuItem = new JMenuItem("�˳�");
    	exitMenuItem.setMnemonic('E');
    	// �����˳���ʱ�˳�Ӧ�ó���
    	exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit();
            }
        });
    	
    	fileMenu.add(newMenuItem);
    	fileMenu.add(openMenuItem);
    	fileMenu.add(closeMenuItem);
    	fileMenu.add(exitMenuItem);
    	
    	//�����˵�����һ���˵������
    	JMenu helpMenu = new JMenu("����");
    	fileMenu.setMnemonic('H');
    	JMenuItem aboutMenuItem = new JMenuItem("����");
    	aboutMenuItem.setMnemonic('A');
    	helpMenu.add(aboutMenuItem);
    	
    	JMenuBar menuBar = new JMenuBar();
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	
    	// ���ò˵�����������
    	this.setJMenuBar(menuBar);
    }
    
    /**
     * ��ʼ��������
     */
    private void initToolbar(){
    	// ���˰�ť���˵�ǰһҳ�档��ʼ����¸ð�ť������
        backButton = new JButton("����");
        backButton.setEnabled(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	back();
            }
        });
        
        // ǰ����ť������ǰһҳ�档��ʼ����¸ð�ť������
        forwardButton = new JButton("ǰ��");
        forwardButton.setEnabled(false);
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	forward();
            }
        });
        
        // ǰ����ť������ǰһҳ�档��ʼ����¸ð�ť������
        JButton refreshButton = new JButton("ˢ��");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	reload();
            }
        });
        
        // ��ҳ��ť������ҳ
        JButton homeButton = new JButton("��ҳ");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	home();
            }
        });
        
        JToolBar toolbar = new JToolBar();
        toolbar.add(backButton);
        toolbar.add(forwardButton);
        toolbar.add(refreshButton);
        toolbar.add(homeButton);

        // ������ַ���ı���
        urlField = new JTextField();
        // ���û�������ַ�����»س���ʱ���������¼�
        urlField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayPage(urlField.getText());
                }
            });

        // ��ַ��ǩ
        toolbar.add(new JLabel("         ��ַ��"));
        toolbar.add(urlField);

        // �����������������ڵı���
        this.getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * ����������Ƿ������д��ڶ��ر�ʱ�˳�
     * @param b
     */
    public static void setExitWhenLastWindowClosed(boolean b) {
        exitWhenLastWindowClosed = b;
    }

    /**
	 * ������ҳ
	 * @param home	����ҳ
	 */
	public void setHome(String home) {
		this.home = home;
	}
	/**
	 * ��ȡ��ҳ
	 */
    public String getHome() {
		return home;
	}

    /**
     * ������ַURL
     */
    private boolean visit(URL url) {
        try {
            String href = url.toString();
            // ��������������ҳ���������ʱ������propertyChanged�¼�������ֹͣ��
            startAnimation("���� " + href + "...");
            
            // ������ʾHTML����page����Ϊ�����ʵ�URL��
            // ��������������URL����URL������ʾ��textPane�С�
            // ����ȫ�򿪺󣬸÷����Ž�����
            textPane.setPage(url); 
            
            // ҳ��򿪺󣬽���������ڵı�����ΪURL
            this.setTitle(href);  
            // ��ַ����������Ҳ����ΪURL
            urlField.setText(href); 
            return true;
        } catch (IOException ex) { 
        	// ֹͣ����
            stopAnimation();
            // ״̬������ʾ������Ϣ
            messageLine.setText("���ܴ�ҳ�棺" + ex.getMessage());
            return false;
        }
    }

    /**
     * �������URLָ����ҳ�棬����ɹ�����URL������ʷ�б���
     */
    public void displayPage(URL url) {
    	// ���Է���ҳ��
        if (visit(url)) { 
        	// ����ɹ�����URL������ʷ�б��С�
            history.add(url); 
            int numentries = history.size();
            if (numentries > MAX_HISTORY+10) { 
                history = history.subList(numentries-MAX_HISTORY, numentries);
                numentries = MAX_HISTORY;
            }
            // ����ǰҳ���±���Ϊnumentries-1
            currentHistoryPage = numentries - 1;
            // �����ǰҳ�治�ǵ�һ��ҳ�棬����Ժ��ˣ����������˰�ť��
            if (currentHistoryPage > 0){
            	backButton.setEnabled(true);
            }
        }
    }

    /**
     * ��������ַ���ָ����ҳ��
     * @param href	��ַ
     */
    public void displayPage(String href) {
        try {
        	// Ĭ��ΪHTTPЭ��
        	if (!href.startsWith("http://")){
        		href = "http://" + href;
        	}
            displayPage(new URL(href));
        }
        catch (MalformedURLException ex) {
            messageLine.setText("�������ַ: " + href);
        }
    }

    /**
     * �򿪱����ļ�
     */
    public void openLocalPage() {
        // ʹ�á���������ģʽ������Ҫʱ���Ŵ����ļ�ѡ������
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            // ʹ���ļ�����������ֻ�ܹ�HTML��HTM�ļ�
            FileFilter filter = new FileFilter() {
                    public boolean accept(File f) {
                        String fn = f.getName();
                        if (fn.endsWith(".html") || fn.endsWith(".htm")){
                            return true;
                        }else {
                        	return false;
                        }
                    }
                    public String getDescription() { 
                    	return "HTML Files"; 
                    }
                };
            fileChooser.setFileFilter(filter);
            // ֻ����ѡ��HTML��HTM�ļ�
            fileChooser.addChoosableFileFilter(filter);
        }

        // ���ļ�ѡ����
        int result = fileChooser.showOpenDialog(this);
        // ���ȷ�����ļ������ڵ�ǰ�����д�ѡ����ļ�
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile( );
            try {
				displayPage(selectedFile.toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
    }
    /**
     * ���ˣ��ص�ǰһҳ
     */
    public void back() {
        if (currentHistoryPage > 0){
        	// ����ǰһҳ
            visit((URL)history.get(--currentHistoryPage));
        }
        // �����ǰҳ���±����0���������
        backButton.setEnabled((currentHistoryPage > 0));
        // �����ǰҳ���±겻���������ǰ��
        forwardButton.setEnabled((currentHistoryPage < history.size()-1));
    }
    /**
     * ǰ�����ص���һҳ
     */
    public void forward() {
        if (currentHistoryPage < history.size( )-1){
            visit((URL)history.get(++currentHistoryPage));
        }
        // �����ǰҳ���±����0���������
        backButton.setEnabled((currentHistoryPage > 0));
        // �����ǰҳ���±겻���������ǰ��
        forwardButton.setEnabled((currentHistoryPage < history.size()-1));
    }
    /**
     * ���¼��ص�ǰҳ��
     */
    public void reload() {
        if (currentHistoryPage != -1) {
            // ����ʾΪ�հ�ҳ
            textPane.setDocument(new javax.swing.text.html.HTMLDocument());
            // �ٷ��ʵ�ǰҳ
            visit((URL)history.get(currentHistoryPage));
        }
    }
    /**
     * ��ʾ��ҳ 
     */
    public void home() {
    	displayPage(getHome()); 
    }
    /**
     * ���µ���������� 
     */
    public void newBrowser() {
        WebBrowser b = new WebBrowser();
        // �´��ڴ�С�͵�ǰ����һ����
        b.setSize(this.getWidth(), this.getHeight());
        b.setVisible(true);
    }
    /**
     * �رյ�ǰ���ڣ�������д��ڶ��رգ������exitWhenLastWindowClosed���ԣ�
     * �ж��Ƿ��˳�Ӧ�ó���
     */
    public void close() {
    	// �����ص�ǰ���ڣ����ٴ����е������
        this.setVisible(false);
        this.dispose();
        // ����ǰ�򿪴�������1��
        // ������д��ڶ��ѹرգ�����exitWhenLastWindowClosedΪ�棬���˳�
        // ���������synchronized�ؼ��֣���֤�̰߳�ȫ
        synchronized(WebBrowser.class) {    
            WebBrowser.numBrowserWindows--; 
            if ((numBrowserWindows==0) && exitWhenLastWindowClosed){
                System.exit(0);
            }
        }
    }
    /**
     * �˳�Ӧ�ó���
     */
    public void exit() {
    	// �����Ի�������ȷ�ϣ����ȷ���˳������˳�Ӧ�ó���
		if ((JOptionPane.showConfirmDialog(this, "��ȷ���˳�Web�������", "�˳�",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)){
			System.exit(0);
		}
	}
    /**
     * ʵ��HyperlinkListener�ӿڡ����������¼�
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
    	// ��ȡ�¼�����
        HyperlinkEvent.EventType type = e.getEventType();
        // ����ǵ����һ�������ӣ�����ʾ�����������
        if (type == HyperlinkEvent.EventType.ACTIVATED) {
            displayPage(e.getURL());
        }
        // ���������ƶ����������ϣ�����״̬������ʾ�����ӵ���ַ
        else if (type == HyperlinkEvent.EventType.ENTERED) {
            messageLine.setText(e.getURL().toString());  
        }
        // ���������뿪�˳����ӣ���״̬����ʾΪ��
        else if (type == HyperlinkEvent.EventType.EXITED) { 
            messageLine.setText(" ");
        }
    }

    /**
     * ʵ��PropertyChangeListener�ӿڡ��������Ըı��¼���
     * ��ʾHTML���textPane�����Ըı�ʱ���ɸ÷�������
     * ��textPane������setPage����ʱ��page���Ա�ı��ˡ�
     */
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("page")) {
        	// ҳ��������ʱ��textPane��page���Է����ı䣬��ʱֹͣ������
            stopAnimation();
        }
    }

    // ������Ϣ����ʾ�������״̬����ǩ�ϣ����ڷ����������״̬
    String animationMessage;
    // ������ǰ��֡������
    int animationFrame = 0;
    // �������õ���֡����һЩ�ַ���
    String[] animationFrames = new String[] {
        "-", "\\", "|", "/", "-", "\\", "|", "/", 
        ",", ".", "o", "0", "O", "#", "*", "+"
    };

    /**
     * �½�һ��Swing�Ķ�ʱ����ÿ��125�������һ��״̬����ǩ���ı�
     */
    javax.swing.Timer animator =
        new javax.swing.Timer(125, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	animate(); 
                }
            });

    /**
     * ��ʾ�����ĵ�ǰ֡��״̬����ǩ�ϣ�����֡��������
     */
    private void animate() {
        String frame = animationFrames[animationFrame++];
        messageLine.setText(animationMessage + " " + frame);
        animationFrame = animationFrame % animationFrames.length;
    }

    /**
     * ��������
     */
    private void startAnimation(String msg) {
        animationMessage = msg;
        animationFrame = 0; 
        // ������ʱ��
        animator.start();
    }

    /**
     * ֹͣ����
     */
    private void stopAnimation() {  
    	// ֹͣ��ʱ��
        animator.stop();
        messageLine.setText(" ");
    }
    
    public static void main(String[] args) throws IOException {
        // �������������������������ڶ����ر�ʱ���˳�Ӧ�ó���
		WebBrowser.setExitWhenLastWindowClosed(true);
		// ����һ�����������
		WebBrowser browser = new WebBrowser(); 
		// ������������ڵ�Ĭ�ϴ�С
		browser.setSize(800, 600);
		// ��ʾ����
        browser.setVisible(true); 

        // ����ҳ
        browser.displayPage(browser.getHome());
    }
}