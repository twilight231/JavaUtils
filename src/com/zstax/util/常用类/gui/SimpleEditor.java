package com.zstax.util.常用类.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * һ���򵥵��ı��༭�����ܹ��༭html��java��cpp��txt�ļ���
 * ��֧��java�ı���������
 */
public class SimpleEditor extends JFrame {

	/********�˵������˵��Ͳ˵���*********/
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu optionMenu = new JMenu("Option");
	JMenu advanceMenu = new JMenu("Advance");
	JMenu helpMenu = new JMenu("Help");
	JMenu editMenu = new JMenu("Edit");
	JMenuItem newMenuItem = new JMenuItem("new");
	JMenuItem openMenuItem = new JMenuItem("Open");
	JMenuItem saveMenuItem = new JMenuItem("Save");
	JMenuItem exitMenuItem = new JMenuItem("exit");
	JMenuItem findMenuItem = new JMenuItem("Find");
	JMenuItem findNextMenuItem = new JMenuItem("Find Next");
	JMenuItem replaceMenuItem = new JMenuItem("Replace");
	JMenuItem compileMenuItem = new JMenuItem("Compile");
	JMenuItem buildMenuItem = new JMenuItem("Build");
	JMenuItem stopFlashMenuItem = new JMenuItem("Stop Flash");
	JMenuItem startFlashMenuItem = new JMenuItem("Start Flash");
	JMenuItem helpMenuItem = new JMenuItem("Help");
	JMenuItem copyMenuItem = new JMenuItem("Copy");
	JMenuItem cutMenuItem = new JMenuItem("Cut");
	JMenuItem pasteMenuItem = new JMenuItem("Paste");

	
	/********�ļ����ݵ���ʾ****/
	//�ö���ı����Ŷ���ļ����ݣ��ı������JScrollPane��
	//��JScrollPane����JTabbedPane�У������һ����ҳ��Ĳ���
	
	//����ı���ÿ���ı�����ʾһ���ļ�������
	JTextArea[] fileTextAreas = new JTextArea[10];
	//�����������ʱ�Ŀ���̨��Ϣ
	JTextArea consoleTextArea = new JTextArea();
	JScrollPane[] fileScrollPanes = new JScrollPane[10];
	JScrollPane consoleScrollPane;
	//����ı�����ڲ�ͬtab��
	JTabbedPane fileTabbedPane = new JTabbedPane();
	
	/**********�������Լ��������ϵİ�ť**********/
	JToolBar toolBar = new JToolBar();
	JButton openButton = new JButton(new ImageIcon(loadImage("image/open.gif")));
	JButton newButton = new JButton(new ImageIcon(loadImage("image/new.gif")));
	JButton saveButton = new JButton(new ImageIcon(loadImage("image/save.gif")));
	JButton helpButton = new JButton(new ImageIcon(loadImage("image/help.gif")));
	JButton exitButton = new JButton(new ImageIcon(loadImage("image/close.gif")));
	JButton compileButton = new JButton(new ImageIcon(loadImage("image/compile.gif")));
	JButton buildButton = new JButton(new ImageIcon(loadImage("image/build.gif")));
	JButton copyButton = new JButton(new ImageIcon(loadImage("image/copy.gif")));
	JButton cutButton = new JButton(new ImageIcon(loadImage("image/cut.gif")));
	JButton pasteButton = new JButton(new ImageIcon(loadImage("image/paste.gif")));
	
	//���ı�����ʾ��ǰ����ڵ�ǰ�ı����е��к�
	JTextArea showLineNoTextArea = new JTextArea();
	//�Ի����壬���������жԻ�����ʾ�ڸô�����
	JFrame dialogFrame = new JFrame();

	/*****�ļ��������ͽṹ�������ʾ*******/
	JTree tree;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[10];
	
	/*******���֮��ķָ���******/
	JSplitPane leftRightSplitPane;
	JSplitPane lineNoConsoleSplitPane;
	JSplitPane treeFlashSplitPane;
	JSplitPane tabbedLineNoSplitPane;
	
	/**********�ļ�ѡ�񡢴洢���********/
	//�ļ�������
	Filter fileFilter = new Filter();
	//�ļ�ѡ����
	FileChooser fileChooser = new FileChooser();
	// �ļ���д���ƣ�0��ʾ�ļ�ѡ�������ļ���1�ļ�ѡ������ʾд�ļ�
	int fileChooser_control = 0;
	FileWriter fileWriter;

	// tabbedPane��tabҳ�ĵ�ǰ����
	int tb = 1;
	int find_control = 0;
	//�ı���Ŀ�������ָ��ǰ�������ı���
	int textAreas_control = 0;
	//��ǰ�ı����е��ı�
	String currentTextInTextArea;

	//��־�ļ��Ƿ�Ϊ�½��ģ�������½����ļ���Ϊtrue
	boolean[] newFileFlags = new boolean[10];
	//��Ŵ��ļ����ڵ�Ŀ¼
	String[] directory = new String[10];
	
	/********�����滻���****/
	//���ڲ��ҵ��ַ������䳤��
	String findWord;
	int fingWordLength;
	//�������ڲ��ҵ��ַ������ı����е��ı���λ��
	int findIndex;
	//���滻���ı��ĳ���
	int replaceLength = 0;
	
	/********������ʾFlash�Ŀ�����****/
	JLabel flashLabel = new JLabel(new ImageIcon(loadImage("image/Juggler0.gif")));
	Timer timer = new Timer(100, new Act_timer());
	int timerControl = 0;

	/********�������****/
	Font font = new Font("Courier", Font.TRUETYPE_FONT, 14);
	JTextArea helpTextArea = new JTextArea();
	JFrame helpFrame = new JFrame("Help");
	
	//���캯��
	public SimpleEditor() {
		super("�ı��༭��");
		
		//Ϊ������Ӽ����¼�������
		//������һ�зǳ���Ҫ����ʾ�����ܹ����ܽ��㡣
		//���û����һ�䣬�����̻���Ч��
		this.setFocusable(true);
		this.addKeyListener(new MyKeyListener());
		
		//Ϊ������Ӵ����¼�������
		this.addWindowListener(new WindowListener());

		//��ʼ��
		init();
		setLocation(200, 200);
		setVisible(true);
		pack();
		//��ʼʱ��������
		timer.start();
	}
	
	private void init(){
		
		/*******��ʼ���ı���Ŀ¼�����ڵ�*********/
		for (int i = 0; i < 10; i++) {
			nodes[i] = new DefaultMutableTreeNode("File" + (i + 1));
			newFileFlags[i] = true;
			fileTextAreas[i] = new JTextArea();
			// �����ı������ı�������
			fileTextAreas[i].setFont(font);
			// Ϊ�ı���Ĳ�����������ɫ
			fileTextAreas[i].setCaretColor(Color.yellow);
			// �����ı���ı�����ǰ����ɫ
			fileTextAreas[i].setBackground(new Color(70, 80, 91));
			fileTextAreas[i].setForeground(Color.pink);
			// Ϊ�ı�������������¼�������
			fileTextAreas[i].addCaretListener(new CaretLis_lineNo());
			// Ϊ�ı�����������¼�������
			fileTextAreas[i].addKeyListener(new MyKeyListener());
			directory[i] = new String("/");
			fileScrollPanes[i] = new JScrollPane(fileTextAreas[i],
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		root = new DefaultMutableTreeNode("���ڱ༭���ļ�");
		root.add(nodes[0]);
		tree = new JTree(root);
		tree.setEditable(false);
		tree.setForeground(new Color(200, 150, 10));
		tree.setBackground(new Color(70, 80, 91));
		
		//��ʼ������̨�ı���
		consoleTextArea.setFont(font);
		consoleScrollPane = new JScrollPane(consoleTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		consoleTextArea.setForeground(new Color(200, 150, 10));
		consoleTextArea.setBackground(new Color(70, 80, 91));
		consoleTextArea.addMouseListener(new MouseListener_console());
		
		//��ʼ����ʾ��ǰ����������ı���
		showLineNoTextArea.setEnabled(false);
		showLineNoTextArea.setFont(font);
		showLineNoTextArea.setBackground(new Color(70, 80, 91));
		showLineNoTextArea.setDisabledTextColor(Color.yellow);

		/**************��ʼ���˵�*************/
		//��ʼ���˵���
		newMenuItem.addActionListener(new Act_NewFile());
		openMenuItem.addActionListener(new Act_OpenFile());
		saveMenuItem.addActionListener(new Act_SaveFile());
		exitMenuItem.addActionListener(new Act_ExitEditor());
		findMenuItem.addActionListener(new Act_Find());
		findNextMenuItem.addActionListener(new Act_FindNext());
		replaceMenuItem.addActionListener(new Act_Replace());
		compileMenuItem.addActionListener(new Act_Compile());
		buildMenuItem.addActionListener(new Act_Build());
		helpMenuItem.addActionListener(new Act_Help());
		copyMenuItem.addActionListener(new Act_Copy());
		cutMenuItem.addActionListener(new Act_Cut());
		pasteMenuItem.addActionListener(new Act_Paste());
		stopFlashMenuItem.addActionListener(new Act_StopFlash());
		startFlashMenuItem.addActionListener(new Act_StartFlash());
		startFlashMenuItem.setEnabled(false);
		//��ʼ���˵�
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		optionMenu.add(findMenuItem);
		optionMenu.add(findNextMenuItem);
		optionMenu.add(replaceMenuItem);
		advanceMenu.add(compileMenuItem);
		advanceMenu.add(buildMenuItem);
		advanceMenu.addSeparator();
		advanceMenu.add(stopFlashMenuItem);
		advanceMenu.add(startFlashMenuItem);
		helpMenu.add(helpMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(cutMenuItem);
		editMenu.add(pasteMenuItem);
		//��ʼ���˵���
		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		menuBar.add(advanceMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		//���˵�����ӵ�������
		setJMenuBar(menuBar);
		
		/***********��ʼ���������Լ���ť**********/
		//��ʼ����ť
		newButton.addActionListener(new Act_NewFile());
		openButton.addActionListener(new Act_OpenFile());
		saveButton.addActionListener(new Act_SaveFile());
		exitButton.addActionListener(new Act_ExitEditor());
		compileButton.addActionListener(new Act_Compile());
		buildButton.addActionListener(new Act_Build());
		helpButton.addActionListener(new Act_Help());
		copyButton.addActionListener(new Act_Copy());
		cutButton.addActionListener(new Act_Cut());
		pasteButton.addActionListener(new Act_Paste());
		// Ϊ������������ʾ��Ϣ��������ڹ�������ť��ͣ��һ��ʱ��ʱ������ʾ��ʾ��Ϣ
		newButton.setToolTipText("New");
		openButton.setToolTipText("Open");
		saveButton.setToolTipText("Save");
		exitButton.setToolTipText("Exit");
		helpButton.setToolTipText("Help");
		compileButton.setToolTipText("Compile");
		buildButton.setToolTipText("Build");
		copyButton.setToolTipText("Copy");
		cutButton.setToolTipText("Cut");
		pasteButton.setToolTipText("Paste");
		newButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		openButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		saveButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		exitButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		helpButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		compileButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		buildButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		cutButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		copyButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		pasteButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		//��ʼ��������
		toolBar.add(newButton);
		toolBar.add(openButton);
		toolBar.add(saveButton);
		toolBar.add(copyButton);
		toolBar.add(cutButton);
		toolBar.add(pasteButton);
		toolBar.add(compileButton);
		toolBar.add(buildButton);
		toolBar.add(exitButton);
		toolBar.add(helpButton);

		/********��ʼ��tabҳ���������ķָ���*********/
		fileTabbedPane.addTab("File1", fileScrollPanes[0]);
		fileTabbedPane.addChangeListener(new Act_ChangeTab());
		//�ļ�Ŀ¼����Flash֮��ķָ���
		treeFlashSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, flashLabel, tree);
		//�ļ��ı��������ָʾ����������ı������֮��ķָ���
		tabbedLineNoSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, fileTabbedPane, showLineNoTextArea);
		//�ұ���2����������̨���ķָ���
		lineNoConsoleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, tabbedLineNoSplitPane,
				consoleScrollPane);
		//��ߴ�������ұߴ����֮��ķָ���
		leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treeFlashSplitPane,
				lineNoConsoleSplitPane);
		// ���÷ָ������������ʾ�Ŀ�ȣ�����ָ��������ҷָ�������ʾ�ָ����ĺ�����
		// ����ָ��������·ָ�������ʾ�ָ�����������
		leftRightSplitPane.setDividerLocation(150);
		tabbedLineNoSplitPane.setDividerLocation(460);
		lineNoConsoleSplitPane.setDividerLocation(500);
		treeFlashSplitPane.setDividerLocation(120);
		
		//��ʼ������
		initHelp();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(leftRightSplitPane);
	}
	
	/**
	 * ��ʼ��������Ϣ
	 */
	private void initHelp(){
		//������Ϣ��Ҫ��ʾ�˵��Ŀ�ݷ�ʽ
		
		// field���JTable�ı�ͷ��Ϣ������ı���
		String[] field = { "MenuItem", "ShortCut Key" };
		// data���JTable�����ݡ�
		Object[][] data = { { "     New           ", "    Ctrl+N    " },
				{ "    Open          ", "    F12       " },
				{ "    Save          ", "    Ctrl+S    " },
				{ "    Exit          ", "    Ctrl+X    " },
				{ "    Find          ", "    Alt       " },
				{ "    Find Next     ", "    F3        " },
				{ "    Compile       ", "    F9        " },
				{ "    Build         ", "    F5        " },
				{ "    Copy          ", "    Ctrl+C    " },
				{ "    Cut           ", "    Ctrl+X    " },
				{ "    Paste         ", "    Ctrl+Y    " },
				{ "    Help          ", "    Ctrl+H    " }, };
		// �ñ�ͷ�����ݹ���һ����
		JTable help_Table = new JTable(data, field);
		help_Table.setFont(font);
		//���ɱ༭������Ϣ��
		help_Table.setEnabled(false);
		// Ϊ����ı������ñ�����ǰ����ɫ
		helpTextArea.setFont(new Font("Courier", Font.TRUETYPE_FONT, 16));
		helpFrame.getContentPane().setLayout(new BorderLayout());
		help_Table.setForeground(Color.pink);
		helpTextArea.setForeground(Color.pink);
		help_Table.setBackground(new Color(70, 80, 91));
		help_Table.setSelectionBackground(new Color(70, 80, 91));
		helpTextArea.setBackground(new Color(70, 80, 91));
		helpTextArea.setText(" If you want to use this software with all functions,\n" 
						+ "     You must do the things following:\n" 
						+ "     1: install jdk_1.3 or Higher than it ;\n" 
						+ "     2: set your classpath and path correctly;\n" 
						+ "     3: if you want to use the compile and build functions,\n" 
						+ "       you should save your edited File in the save directory\n" 
						+ "       with  this software.\n ");
		// ���ı���ͱ�ӵ�������
		helpFrame.getContentPane().add(
				new JScrollPane(help_Table,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		helpFrame.getContentPane().add(
				new JScrollPane(helpTextArea,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.NORTH);
	}
	
	// �˳��༭��
	private void exitEditor(){
		// ����һ��ѡ��ȷ�϶Ի�������ȷ���˳�
		if ((JOptionPane.showConfirmDialog(this, "��ȷ���ı��༭����", "�˳�",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)){
			// ���ѡ��YES�����˳���
			//dispose���������ͷ���Դ
			//�ͷ��ɴ� Window�������������ӵ�е������������ʹ�õ����б�����Ļ��Դ��
			//����Щ Component ����Դ�����ƻ�������ʹ�õ������ڴ涼�����ص�����ϵͳ���������Ǳ��Ϊ������ʾ��
			//ͨ������ pack �� show �ĵ������¹��챾����Դ�������ٴ���ʾ Window �����������
			//���´����� Window �����������״̬���Ƴ� Window �ĵ�������Щ�����״̬����һ���ģ���������Щ����֮����������ģ��� 
			dispose();
			System.exit(0);
		}
	}
	/**
	 * �����¼�������
	 */
	public class MyKeyListener extends KeyAdapter {
		// ���Ǹ����keyPressed�����������������ʱ���¼���
		public void keyPressed(KeyEvent e) {
			// ��F12���ļ�
			if (e.getKeyCode() == KeyEvent.VK_F12){
				(new Act_OpenFile()).actionPerformed(null);
			}
			// ��Ctrl��S�������ļ�
			else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
				(new Act_SaveFile()).actionPerformed(null);
			}
			// ��Alt������
			else if (e.isAltDown()){
				(new Act_Find()).actionPerformed(null);
			}
			// ��F3������һ��
			else if (e.getKeyCode() == KeyEvent.VK_F3){
				(new Act_FindNext()).actionPerformed(null);
			}
			// ��F4�滻
			else if (e.getKeyCode() == KeyEvent.VK_F4){
				(new Act_Replace()).actionPerformed(null);
			}
			// ��Ctrl��N�½��ļ�
			else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
				(new Act_NewFile()).actionPerformed(null);
			}
			// ��Ctrl��E�˳��༭��
			else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E){
				(new Act_ExitEditor()).actionPerformed(null);
			}
			// ��F5����Java����
			else if (e.getKeyCode() == KeyEvent.VK_F5){
				(new Act_Build()).actionPerformed(null);
			}
			// ��F9����JavaԴ����
			else if (e.getKeyCode() == KeyEvent.VK_F9){
				(new Act_Compile()).actionPerformed(null);
			}
			// ��Ctrl��H��ʾ����
			else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_H){
				(new Act_Help()).actionPerformed(null);
			}
		}
	}
	/**
	 * �����¼�������
	 */
	public class WindowListener extends WindowAdapter {
		// ����رմ����¼�
		public void windowClosing(WindowEvent e) {
			exitEditor();
		}
	}
	/**
	 * �ļ�ѡ����
	 */
	class FileChooser extends JFileChooser {
		public FileChooser() {
			//�ļ�ѡ����Ĭ��λ��Ϊ��ǰ�ļ���
			super("./");
		}
		/**
		 * �ύѡ��
		 */
		public void approveSelection() {
			String filename = null;
			//fileChooser_control == 0��ʾ�����Ǵ��ļ�����Ҫ��
			if (fileChooser_control == 0) {
				//���ļ�ʱ������ߵ��������һ���ڵ�
				root.add(nodes[tb]);
				remove(tree);
				tree = new JTree(root);
				tree.setBackground(new Color(70, 80, 91));
				treeFlashSplitPane.setRightComponent(tree);
				treeFlashSplitPane.setDividerLocation(120);
				// �½�һ��tabҳ������װ�´򿪵��ļ�
				fileTabbedPane.addTab("File" + (tb + 1), fileScrollPanes[tb]);
				fileTabbedPane.setSelectedIndex(tb);
				//����ǰ�ı������õ��´򿪵��ļ���
				textAreas_control = tb;
				tb++;
				
				//��ȡ���򿪵��ļ���
				filename = fileChooser.getSelectedFile().getName();
				//��ȡ���򿪵��ļ����ڵ�Ŀ¼����Ŀ¼���������飬�����ڱ����ļ���ʱ���ܹ����ļ������浽Ŀ¼��
				directory[textAreas_control] = fileChooser.getCurrentDirectory().toString();
				fileTextAreas[textAreas_control].setText(null);
				try {
					//���ļ�������ʾ���ı�����
					String str;
					BufferedReader breader = new BufferedReader(new FileReader(
							directory[textAreas_control] + "/" + filename));
					while (true) {
						str = breader.readLine();
						if (str == null) {
							break;
						}
						fileTextAreas[textAreas_control].append(str + '\n');
					}
				} catch (Exception e_open) {
					JOptionPane
							.showMessageDialog(dialogFrame.getContentPane(), "��ȡ��������");
				}
				
			} else if (fileChooser_control == 1) {
				//	fileChooser_control == 1��ʾ�����Ǳ������ļ�����Ҫд
				filename = fileChooser.getSelectedFile().getName();
				directory[textAreas_control] = fileChooser.getCurrentDirectory().toString();
				try {
					//���ı����е�����д���ļ���
					fileWriter = new FileWriter(directory[textAreas_control] + "/"
							+ filename);
					fileWriter.write(fileTextAreas[textAreas_control].getText());
					fileWriter.close();
				} catch (Exception e_save) {
					JOptionPane
							.showMessageDialog(dialogFrame.getContentPane(), "��ȡ��������");
				}

			}
			
			//�رնԻ���
			dialogFrame.dispose();
			
			//�����ļ�Ŀ¼���е�����
			root.remove(nodes[textAreas_control]);
			nodes[textAreas_control] = new DefaultMutableTreeNode(filename);
			root.add(nodes[textAreas_control]);
			//����ǰ��ʾ����ɾ������ʾ�µ���
			remove(tree);
			tree = new JTree(root);
			tree.setBackground(new Color(70, 80, 91));
			treeFlashSplitPane.setRightComponent(tree);
			treeFlashSplitPane.setDividerLocation(120);
			//��tabҳ�ı����Ϊ�ļ���
			fileTabbedPane.setTitleAt(textAreas_control, filename);
			//�����Ǵ򿪡����Ǳ��棬����ļ������Ѿ��½��ģ�������Ϊfalse
			newFileFlags[textAreas_control] = false;
		}
		/**
		 * ȡ��ѡ��
		 */
		public void cancelSelection() {
			dialogFrame.dispose();
		}
	}

	/**
	 * �ļ���������ֻ֧�ֱ༭".java,*.html,*.txt,*.cpp"�ļ�
	 */
	class Filter extends FileFilter {
		// ����FileFilter��accept����
		public boolean accept(File file1) {
			return (file1.getName().endsWith(".java") || file1.isDirectory()
					|| file1.getName().endsWith(".html")
					|| file1.getName().endsWith(".txt") || file1.getName()
					.endsWith(".cpp"));
		}

		public String getDescription() {
			return (".java,*.html,*.txt,*.cpp");
		}
	}

	/**
	 * ���������ı�����������λ�ø��ĵ�������
	 * ��ȡ��ǰ������ļ��е��к�
	 */
	class CaretLis_lineNo implements CaretListener {
		public void caretUpdate(CaretEvent e) {
			try {
				showLineNoTextArea.setText("          Cursor at the "
						+ (fileTextAreas[textAreas_control]
								.getLineOfOffset(fileTextAreas[textAreas_control]
										.getCaretPosition()) + 1)
						+ " line in the file of "
						+ fileTabbedPane.getTitleAt(textAreas_control));
			} catch (BadLocationException eB) {
				System.out.println("IO Wrong");
			}
		}
	}

	/**
	 * ����̨�ı���������¼���������
	 * ������̨���д�����Ϣʱ������JavaԴ�ļ����������⣬��λ��������кš�
	 */
	class MouseListener_console extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// ��ȡ����ڿ���̨�ı����е�λ��
			int off_err = consoleTextArea.getCaretPosition();
			int line_no = 0;
			try {
				// ���ݹ��λ�ã���λ���ڿ���̨�ı����е���
				int index = -1;
				int line_err = consoleTextArea.getLineOfOffset(off_err - 1);
				// ��ȡ��������е���ʼ�ַ����ı����е�λ��
				int start_err = consoleTextArea.getLineStartOffset(line_err);
				// ��ȡ��������е���ʼλ�õ����λ��֮���ı�����������Ϣ
				String err_str = consoleTextArea.getText(start_err, off_err - start_err);
				// ������ʾ������Ϣ
				consoleTextArea.select(start_err, off_err);
				// ����������Ϣ���Ƿ��б�����ָ����JavaԴ�ļ��кţ�����ٶ����10000��Դ����
				for (line_no = 0; line_no < 10000; line_no++) {
					index = err_str.indexOf(":" + line_no + ":");
					if (index > 0) {
						break;
					}
				}
				// ����ܹ�������JavaԴ�ļ����кţ������ļ��ı����и�����ʾ��
				if (index != -1) {
					fileTextAreas[textAreas_control].requestFocus();
					fileTextAreas[textAreas_control].select(fileTextAreas[textAreas_control]
							.getLineStartOffset(line_no - 1), fileTextAreas[textAreas_control]
							.getLineEndOffset(line_no - 1));
				}
			} catch (BadLocationException eB) {
				System.out.println("IO Wrong");
			}
		}
	}
	/**
	 * �л�tabҳ�¼�
	 */
	class Act_ChangeTab implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			// �л�tabҳʱ������textAreas_control��ֵ��
			textAreas_control = fileTabbedPane.getSelectedIndex();
		}
	}
	/**
	 * �½��ļ��¼�
	 */
	class Act_NewFile implements ActionListener {
		public void actionPerformed(ActionEvent e_ji0) {
			//�������ļ�ʱ����������tabҳ��
			root.add(nodes[tb]);
			remove(tree);
			tree = new JTree(root);
			tree.setBackground(new Color(70, 80, 91));
			treeFlashSplitPane.setRightComponent(tree);
			treeFlashSplitPane.setDividerLocation(120);
			fileTabbedPane.addTab("File" + (tb + 1), fileScrollPanes[tb]);
			fileTabbedPane.setSelectedIndex(tb);
			textAreas_control = tb;
			tb++;
		}
	}
	/**
	 * ���ļ��¼�
	 */
	class Act_OpenFile implements ActionListener {
		public void actionPerformed(ActionEvent e_ji1) {
			//�������ļ�
			//���ļ�ѡ������Ϊ���ļ�״̬
			fileChooser_control = 0;
			fileChooser.setApproveButtonText("��");
			fileChooser.addChoosableFileFilter(fileFilter);
			dialogFrame.getContentPane().add(fileChooser);
			dialogFrame.setSize(550, 350);
			dialogFrame.setTitle("��ѡ��Ҫ�򿪵��ļ�!");
			dialogFrame.setVisible(true);
			fileTextAreas[textAreas_control].setCaretPosition(0);
		}
	}
	/**
	 * �����ļ��¼�
	 */
	class Act_SaveFile implements ActionListener {
		public void actionPerformed(ActionEvent e_ji2) {
			System.out.println("Act_SaveFile" + textAreas_control);
			if (newFileFlags[textAreas_control]) {
				//�����½����ļ�����Ҫָ���ļ��洢·������˵������ļ�ѡ����洢�ļ�
				//���ļ�ѡ������Ϊ�����ļ�״̬
				fileChooser_control = 1;
				fileChooser.setApproveButtonText("����");
				fileChooser.addChoosableFileFilter(fileFilter);
				dialogFrame.getContentPane().add(fileChooser);
				dialogFrame.setTitle("�������ļ���!");
				dialogFrame.setSize(550, 350);
				dialogFrame.setVisible(true);

			} else {
				try {
					//�����Ѿ����ڵ��ļ���ֱ�ӱ���
					//д�ļ�������ǰ�ı������ı�д�뵽�ļ���
					fileWriter = new FileWriter(directory[textAreas_control] + "/"
							+ fileTabbedPane.getTitleAt(textAreas_control));
					fileWriter.write(fileTextAreas[textAreas_control].getText());
					fileWriter.close();
				} catch (Exception e_save) {
					JOptionPane.showMessageDialog(null, "��ȡ��������");
				}
			}
		}
	}

	/**
	 * �˳��༭���¼�
	 */
	class Act_ExitEditor implements ActionListener {
		public void actionPerformed(ActionEvent e_ji3) {
			//�˳��༭��
			exitEditor();
		}
	}
	/**
	 * �����¼�
	 */
	class Act_Find implements ActionListener {
		public void actionPerformed(ActionEvent e_ji4) {
			//���ҶԻ���
			findWord = JOptionPane
					.showInputDialog("�������������");
			if (findWord == null) {
				JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
			} else {
				//���ݲ��������ڵ�ǰ�ı����н���ƥ��
				fingWordLength = findWord.length();
				currentTextInTextArea = fileTextAreas[textAreas_control].getText();
				findIndex = currentTextInTextArea.indexOf(findWord);
				if (findIndex < 0){
					JOptionPane.showMessageDialog(null,
							"   �������ݲ�����  ");
				}  else {
					//����ҵ��ˣ��������̽�����ڵ�ǰ�ı����У�����ƥ���ַ�����ʾ����
					fileTextAreas[textAreas_control].requestFocus();
					fileTextAreas[textAreas_control].select(findIndex, findIndex + fingWordLength);
				}
			}
		}
	}
	/**
	 * ������һ���¼�
	 */
	class Act_FindNext implements ActionListener {
		public void actionPerformed(ActionEvent e_ji4_next) {
			//������һ��ƥ����ַ���
			currentTextInTextArea = fileTextAreas[textAreas_control].getText();
			findIndex = currentTextInTextArea.indexOf(findWord, findIndex + 1);
			if (findIndex < 0) {
				JOptionPane.showMessageDialog(null,
						" �����Ѿ������ļ�β�� ");
			} else {
				fileTextAreas[textAreas_control].select(findIndex, findIndex + fingWordLength);
			}
		}
	}
	/**
	 * �滻�¼�
	 */
	class Act_Replace implements ActionListener {
		public void actionPerformed(ActionEvent e_ji5) {
			//�滻�Ի���
			Object[] endButton1 = { "Replace", "Cancel" };
			String message1 = "ȷ���滻��";
			currentTextInTextArea = fileTextAreas[textAreas_control].getText();
			
			//��ȡ���滻������
			String seek_word = JOptionPane
					.showInputDialog("�������������");
			//��ȡ�滻�������
			String replace_word = JOptionPane
					.showInputDialog("�������滻����");
			//����û�����Ĳ������ݲ�Ϊnull����ʼ�����滻����
			if (seek_word != null) {
				//��ȡ�������ݵĳ��ȣ�Ҳ���ǽ����滻�ĳ���
				replaceLength = seek_word.length();
				while (true) {
					//�Ȼ�ȡ��ǰ�ı�����ı����ٽ��в���
					currentTextInTextArea = fileTextAreas[textAreas_control].getText();
					findIndex = currentTextInTextArea.indexOf(seek_word, findIndex + replaceLength);
					if (findIndex < 0) {
						//�ı��в����ڲ�������
						JOptionPane.showMessageDialog(null,
								"�����Ѿ������ļ�β��");
						break;
					} else {
						//���ҳɹ������ʾ����������
						fileTextAreas[textAreas_control].requestFocus();
						fileTextAreas[textAreas_control].select(findIndex, findIndex + replaceLength);
						//�滻ȷ��
						JOptionPane end1 = new JOptionPane(message1,
								JOptionPane.WARNING_MESSAGE,
								JOptionPane.DEFAULT_OPTION, null, endButton1);
						JDialog endD1 = end1.createDialog(end1, "��ѡ��");
						endD1.setVisible(true);
						Object push1 = end1.getValue();
						if (push1 == endButton1[0]){
							//����û�ѡ���滻�����ı����б���ʾ��������replace�滻
							fileTextAreas[textAreas_control].replaceSelection(replace_word);
						}
					}
				}
			}
		}
	}
	/**
	 * ����compile�¼�
	 */
	class Act_Compile implements ActionListener {
		public void actionPerformed(ActionEvent e_ji6) {
			//compileһ��javaԴ�ļ�
			//����̨��Ϣ
			consoleTextArea.setText(null);
			//Ŀǰֻ֧�ֱ���java��
			if ((fileTabbedPane.getTitleAt(textAreas_control)).indexOf(".java") > -1) {
				try {
					int count;
					byte input[] = new byte[256];
					String InputString;
					// ����javaԴ�ļ�������"javac"
					String[] command = {
							"javac",
							directory[textAreas_control] + "/"
							+ fileTabbedPane.getTitleAt(textAreas_control) };
					//��ǰӦ�ó�������һ���½��̣�����ִ������
					Process p = Runtime.getRuntime().exec(command);
					//��ȡִ������ʱ�Ĵ������
					BufferedInputStream bufin = new BufferedInputStream(p.getErrorStream());
					//���������ȡǰ1024���ֽ�
					bufin.mark(1024);
					count = bufin.read(input);
					if (count <= 0){
						//����û�д���
						consoleTextArea.append("Compile to " + fileTabbedPane.getTitleAt(textAreas_control)
								+ " Success");
					} else {
						//����ʧ��
						InputString = new String(input, 0, count);
						consoleTextArea.append("Compile to " + fileTabbedPane.getTitleAt(textAreas_control)
								+ " Fail\n" + InputString);
					}
				} catch (IOException e) {
					System.err.println("IO error: " + e);
				}
			} else {
				consoleTextArea.append(fileTabbedPane.getTitleAt(textAreas_control)
						+ " is not a java File !\n Please Check it again!");
			}
		}
	}
	/**
	 * ����build�¼�
	 */
	class Act_Build implements ActionListener {
		public void actionPerformed(ActionEvent e_ji7) {
			//buildһ��Java��
			consoleTextArea.setText(null);
			if ((fileTabbedPane.getTitleAt(textAreas_control)).indexOf(".java") > -1) {
				try {
					int count;
					byte input[] = new byte[256];
					String InputString;
					//��ȡjava������֣�ȥ��javaԴ�ļ����ĺ������ĸ".java"
					String class_name;
					int length = (fileTabbedPane.getTitleAt(textAreas_control)).length();
					class_name = (fileTabbedPane.getTitleAt(textAreas_control)).substring(0,
							length - 5);
					//����java�������"java"
					String[] command = { "java", "-classpath",
							directory[textAreas_control], class_name };
					Process p = Runtime.getRuntime().exec(command);
					BufferedInputStream bufin = new BufferedInputStream(p
							.getErrorStream());
					bufin.mark(256);
					count = bufin.read(input);
					if (count <= 0){
						//����û�д���
						consoleTextArea.append("Build to " + fileTabbedPane.getTitleAt(textAreas_control)
								+ " Success");
					} else {
						//�����д���
						InputString = new String(input, 0, count);
						consoleTextArea.append("Builld to " + fileTabbedPane.getTitleAt(textAreas_control)
								+ " Fail\n" + InputString);
					}
				} catch (IOException e) {
					System.err.println("IO error: " + e);
				} catch (IndexOutOfBoundsException e2) {
					System.err.println("IO error: " + e2);
				}
			} else {
				consoleTextArea.append(fileTabbedPane.getTitleAt(textAreas_control)
						+ " is not a java File !\n Please Check it again!");
			}
		}
	}
	/**
	 * ��ʾ����Help�¼�
	 */
	class Act_Help implements ActionListener {
		public void actionPerformed(ActionEvent e_ji9) {
			helpFrame.pack();
			helpFrame.setVisible(true);
			helpFrame.requestFocus();
			helpFrame.setLocation(200, 0);
		}
	}
	
	class Act_Copy implements ActionListener {
		public void actionPerformed(ActionEvent e_ji9) {
			//�����¼���ֱ�ӵ���JTextArea��copy����
			fileTextAreas[textAreas_control].copy();
		}
	}

	class Act_Cut implements ActionListener {
		public void actionPerformed(ActionEvent e_ji10) {
			//�����¼���ֱ�ӵ���JTextArea��cut����
			fileTextAreas[textAreas_control].cut();
		}
	}

	class Act_Paste implements ActionListener {
		public void actionPerformed(ActionEvent e_ji11) {
			//ճ���¼���ֱ�ӵ���JTextArea��paste����
			fileTextAreas[textAreas_control].paste();
		}
	}

	/**
	 * ������ʾ�¼�
	 */
	class Act_timer implements ActionListener {
		public void actionPerformed(ActionEvent e_time) {
			//Flash��ʾ��һ����4��ͼƬ�ļ�����˳����ʾ
			if (timerControl > 4){
				timerControl = 0;
			}
			flashLabel.setIcon(new ImageIcon(loadImage("image/Juggler" + timerControl
					+ ".gif")));
			timerControl++;
		}
	}

	/**
	 * ֹͣ�����¼�
	 */
	class Act_StopFlash implements ActionListener {
		public void actionPerformed(ActionEvent E_stop) {
			//Flash���ơ�ֹͣ����
			timer.stop();
			startFlashMenuItem.setEnabled(true);
			stopFlashMenuItem.setEnabled(false);
		}
	}

	/**
	 * ���������¼�
	 */
	class Act_StartFlash implements ActionListener {
		public void actionPerformed(ActionEvent E_start) {
			//Flash���ơ���������
			timer.start();
			startFlashMenuItem.setEnabled(false);
			stopFlashMenuItem.setEnabled(true);
		}
	}

	/**
	 * ��jar���ж�ȡͼƬ�ļ�
	 * @param name
	 * @return	����һ��ͼƬ����
	 */
	private java.awt.Image loadImage(String name) {
		try {
			java.net.URL url = getClass().getResource(name);
			//����URL�������½�һ��ͼƬ�ļ�
			return createImage((java.awt.image.ImageProducer) url.getContent());
		} catch (Exception ex) {
			return null;
		}
	}
	public static void main(String args[]) {
		new SimpleEditor();
	}
}
