package com.zstax.util.常用类.gui.dragdrop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * һ��չʾ֧���Ϸŵ�List�����ӡ� ��������Ҫ��ʾ���¼��㣺 
 * ��1��JList��ʹ�ã���������ģ���Զ�����ʾģ�ͣ�����ʾ�����ݷ��롣
 * ��2��JList���ѡ��ʱ���¼�������ListSelectionListener��ʵ�֡�
 * ��3��JListʵ���Ϸţ��ļ��Ϸŵ�JList�У�JList�е����Ϸŵ��ļ�ϵͳ�С�
 */
public class DragDropListDemo extends JFrame implements ListSelectionListener {
	/**
	 * �����Ӱ������������������һ���ı���������һ��List�б� 
	 * ���û������б��е���ʱ���������ı�������ʾ�����ֵ��
	 */
	// �ı���
	private JTextField fileName;

	// ���Ϸŵ�List
	private DroppableList list;

	public DragDropListDemo() {
		super("DragDropListDemo");

		fileName = new JTextField(40);
		// ����һ�����Ϸŵ�List���������������
		list = new DroppableList();
		// ����List�е�Ԫ�����ʾģ�ͣ�Ҳ����˵��List������ģ�������ֵ��"value"��
		// ����ͨ��������ʾģ�ͣ�����ʾListʱ�����ܿ�������"new_value"��
		// ��Ϊ��List����ʾ����ģ�����ֵʱ�������ListCellRender��getListCellRendererComponent������
		// �����Զ���һ��ListCellRender�����ֽ�CustomCellRenderer��
		// ��ʵ����getListCellRendererComponent������
		list.setCellRenderer(new CustomCellRenderer());
		// ����ListΪ��ѡ��һ��ֻ��ѡ��һ��
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScrollPane = new JScrollPane(list);

		// List������ģ��
		DefaultListModel listModel = (DefaultListModel) list.getModel();
		String dirName = new String("/");
		String filelist[] = new File(dirName).list();
		for (int i = 0; i < filelist.length; i++) {
			String thisFileSt = dirName + filelist[i];
			File thisFile = new File(thisFileSt);
			if (thisFile.isDirectory())
				continue;
			try {
				// �������ļ�����Ϣ���һ�����List������ģ���С�
				listModel.addElement(makeNode(thisFile.getName(), thisFile
						.toURL().toString(), thisFile.getAbsolutePath()));
			} catch (java.net.MalformedURLException e) {
			}
		}

		// ΪList���ѡ���¼�������������ΪListSelectionListener��
		// ���û�ѡ��List�е�һ��ʱ�������ListSelectionListener��valueChanged����
		list.addListSelectionListener(this);
		if (listModel.size() > 0) {
			// Ĭ��Ϊѡ���0��
			list.setSelectedIndex(0);
			String name = listModel.getElementAt(list.getSelectedIndex())
					.toString();
			fileName.setText(name);
		} else {
			list.setSelectedIndex(-1);
			fileName.setText("null");
		}

		JPanel buttonPane = new JPanel();
		buttonPane.add(fileName);
		Container contentPane = getContentPane();
		contentPane.add(listScrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.NORTH);
	}

	/**
	 * ʵ�� ListSelectionListener�ӿڶ���ķ����� ���û�ѡ��List�е���ʱ�����ø÷���
	 */
	public void valueChanged(ListSelectionEvent e) {
		// ������¼��Ƕ�������¼�֮һ����getValueIsAdjusting���� true
		if (e.getValueIsAdjusting() == false) {
			// �������¼���һ�������¼���������List�е��
			// ��List�б�ѡ������ֵ��ʾ���ı����С�
			fileName.setText("");
			if (list.getSelectedIndex() != -1) {
				String name = list.getSelectedValue().toString();
				fileName.setText(name);
			}
		}
	}

	/**
	 * �����ļ�����Ϣ����һ���ڵ㣬�����List������ģ���С��ڵ�����Ϊһ��Hashtable
	 */
	private static Hashtable makeNode(String name, String url, String strPath) {
		Hashtable hashtable = new Hashtable();
		hashtable.put("name", name);
		hashtable.put("url", url);
		hashtable.put("path", strPath);
		return hashtable;
	}

	/**
	 * �ڲ��࣬������List��Ԫ��������ʾ�ĸ�ʽ��
	 */
	class CustomCellRenderer implements ListCellRenderer {
		DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();

		/**
		 * ʵ��ListCellRenderer�ӿڶ����getListCellRendererComponent������
		 */
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean selected, boolean hasFocus) {
			// ����Ĭ�ϵĵ�Ԫ��������ʾ��ʽ������ѡ�е����������ɫ��ʾ��
			listCellRenderer.getListCellRendererComponent(list, value, index,
					selected, hasFocus);
			// ���µĸ�ʽ��ʾ���ֵ
			listCellRenderer.setText(getValueString(value));
			return listCellRenderer;
		}

		/**
		 * ����ֵ�����ʾ�ַ���
		 */
		private String getValueString(Object value) {
			String returnString = "null";
			if (value != null) {
				if (value instanceof Hashtable) {
					Hashtable h = (Hashtable) value;
					String name = (String) h.get("name");
					String url = (String) h.get("url");
					// ��ʾHashtable�м�ֵ��
					returnString = name + " ==> " + url;
				} else {
					returnString = "X: " + value.toString();
				}
			}
			return returnString;
		}
	}

	public static void main(String s[]) {
		JFrame frame = new DragDropListDemo();
		// �رմ��ڵ���һ�ַ�ʽ����setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);�ȼ�
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
	}
}
