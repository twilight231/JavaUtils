package com.zstax.util.常用类.gui.dragdrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * ֧���Ϸŵ�List�����Խ��ļ��Ϸŵ�List�У�Ҳ���԰�List�е����Ϸŵ��ļ�ϵͳ�����ı����С�
 * ֧���Ϸű���ʵ��3���ӿڣ�DropTargetListener��DragSourceListener��DragGestureListener��
 * ���϶�List�е����ݿ�ʼʱ��DragGestureListener����ķ��������á�
 * ���϶�List�е����ݹ����У�DragSourceListener����ķ��������á�
 * ����List�з�������ʱ��DragSourceListener����ķ��������á�
 */
public class DroppableList extends JList implements DropTargetListener,
		DragSourceListener, DragGestureListener {
	// �Ϸŵ�Ŀ�ĵأ������õ㣬��List���շ���drop����
	DropTarget dropTarget = new DropTarget(this, this);

	// ʹ��Ĭ�ϵ��Ϸ�Դ
	DragSource dragSource = DragSource.getDefaultDragSource();

	public DroppableList() {
		// ��ʼ���Ϸ�Դ����һ������ָ��Դ���󣬼������󣻵ڶ�������ָ���϶�֧�ֵķ�ʽ��
		// ����������ָ���϶�����ʱDragGestureListener��ʵ���࣬��������
		dragSource.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_COPY_OR_MOVE, this);
		// ����List������ģ�͡�
		DefaultListModel model = new DefaultListModel();
		setModel(model);
	}

	// �����������ʵ����DragSourceListener�ӿڶ���ķ��������϶�����������ݵ������ط�ʱ��������Щ������
	public void dragDropEnd(DragSourceDropEvent DragSourceDropEvent) {
		System.out
				.println("method: dragDropEnd(DragSourceDropEvent DragSourceDropEvent)");
	}

	public void dragEnter(DragSourceDragEvent DragSourceDragEvent) {
		System.out
				.println("method: dragEnter(DragSourceDragEvent DragSourceDragEvent)");
	}

	public void dragExit(DragSourceEvent DragSourceEvent) {
		System.out.println("method: dragExit(DragSourceEvent DragSourceEvent)");
	}

	public void dragOver(DragSourceDragEvent DragSourceDragEvent) {
		// System.out.println("method: dragOver(DragSourceDragEvent
		// DragSourceDragEvent) called!");
	}

	public void dropActionChanged(DragSourceDragEvent DragSourceDragEvent) {
		System.out
				.println("method: dropActionChanged(DragSourceDragEvent DragSourceDragEvent)");
	}

	// ����5������ʵ����DropTargetListener�ӿڶ���ķ��������϶������Ž�������ʱ����Щ���������á�
	public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
		System.out
				.println("method: dragEnter(DropTargetDragEvent dropTargetDragEvent)");
		dropTargetDragEvent.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	}

	public void dragExit(DropTargetEvent dropTargetEvent) {
		System.out.println("method: dragExit(DropTargetEvent dropTargetEvent)");
	}

	public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
		// System.out.println("method: dragOver(DropTargetDragEvent
		// dropTargetDragEvent) called!");
	}

	public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
		System.out
				.println("method: dropActionChanged(DropTargetDragEvent dropTargetDragEvent)");
	}

	/**
	 * ���϶�ϵͳ�е��ļ�����drop����List��ʱ�����ô˷�����
	 */
	public synchronized void drop(DropTargetDropEvent dropTargetDropEvent) {
		System.out
				.println("method: drop(DropTargetDropEvent dropTargetDropEvent)");
		try {
			// ��ȡ�����Transfer����
			Transferable tr = dropTargetDropEvent.getTransferable();
			// ���Transfer����֧���ļ����õ�javaƽ̨���������һ������
			if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// ʹ�á��������ƶ�����ʽ���շ��ò�����
				dropTargetDropEvent
						.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				// ��Transfer��ȡ���ݣ��õ�����һ���ļ��б�������һ���ϷŶ���ļ�
				java.util.List fileList = (java.util.List) tr
						.getTransferData(DataFlavor.javaFileListFlavor);
				Iterator iterator = fileList.iterator();
				while (iterator.hasNext()) {
					// ���ϷŽ������ļ�����Ϣ��ӵ�List������ģ����
					File file = (File) iterator.next();
					Hashtable hashtable = new Hashtable();
					hashtable.put("name", file.getName());
					hashtable.put("url", file.toURL().toString());
					hashtable.put("path", file.getAbsolutePath());
					((DefaultListModel) getModel()).addElement(hashtable);
				}
				// ���ò����ɹ�����
				dropTargetDropEvent.getDropTargetContext().dropComplete(true);
			} else {
				// Transferable����֧���ļ����õ�java�У��ܾ���
				System.err.println("Rejected");
				dropTargetDropEvent.rejectDrop();
			}
		} catch (IOException io) {
			io.printStackTrace();
			dropTargetDropEvent.rejectDrop();
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
			dropTargetDropEvent.rejectDrop();
		}
	}

	/**
	 * ����List��ѡ��һ���Ϸŵ��ļ�ϵͳ���������ط�ʱ�����ô˷����� ����DragGestureListener�ӿڶ���ķ�����
	 */
	public void dragGestureRecognized(DragGestureEvent dragGestureEvent) {
		System.out
				.println("method: dragGestureRecognized(DragGestureEvent dragGestureEvent)");
		if (getSelectedIndex() == -1)
			return;
		// ��ñ�ѡ���������ݡ�
		Object obj = getSelectedValue();
		if (obj == null) {
			// ���û��ѡ���б��е��������Ϊһ���϶����������
			System.out.println("Nothing selected - beep");
			getToolkit().beep();
		} else {
			// ��List�б�ѡ���������һ��Transfer����
			Hashtable table = (Hashtable) obj;
			FileSelection transferable = new FileSelection(new File(
					(String) table.get("path")));
			// ��ʼ�Ϸţ���һ������Ϊ�Ϸ�ʱ�Ĺ�ꣻ�ڶ�������Ϊ���Ϸŵ����ݶ��󣻵��������Ϸ��Ϸ�Դ������
			dragGestureEvent.startDrag(DragSource.DefaultCopyDrop,
					transferable, this);
		}
	}

	/**
	 * �ڲ��࣬������һ��֧���ļ��Ϸŵ�Transfer���� ���̳�Vector����ʾ������һ�δ��Ͷ���ļ�
	 */
	class FileSelection extends Vector implements Transferable {
		final static int FILE = 0;

		final static int STRING = 1;

		final static int PLAIN = 2;

		// �����Transfer�ܹ����͵��������ͣ������ļ����ַ������޸�ʽ���ı���
		DataFlavor flavors[] = { DataFlavor.javaFileListFlavor,
				DataFlavor.stringFlavor, DataFlavor.plainTextFlavor };

		public FileSelection(File file) {
			// ���ļ�����
			addElement(file);
		}

		/* ���ظ�Transfer�ܹ����ݵ��������� */
		public synchronized DataFlavor[] getTransferDataFlavors() {
			return flavors;
		}

		/* �жϸ�Transfer�Ƿ�֧��flavor�������͵Ĵ��� */
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			boolean b = false;
			b |= flavor.equals(flavors[FILE]);
			b |= flavor.equals(flavors[STRING]);
			b |= flavor.equals(flavors[PLAIN]);
			return (b);
		}

		/**
		 * ����Transfer�����з�װ������
		 */
		public synchronized Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.equals(flavors[FILE])) {
				// �����Ҫ��ȡ�ļ����ݡ����Լ����أ���һ���ļ���Vector��
				System.out.println("return flavors[FILE]");
				return this;
			} else if (flavor.equals(flavors[PLAIN])) {
				// �����Ҫ��ȡһ���ı����򽫵�һ���ļ����ļ����ݵ��������÷��ء�
				System.out.println("return flavors[PLAIN]");
				return new StringReader(((File) elementAt(0)).getAbsolutePath());
			} else if (flavor.equals(flavors[STRING])) {
				// �����Ҫ��ȡһ���ַ������򽫵�һ���ļ����ļ�·��������
				System.out.println("return flavors[STRING]");
				return ((File) elementAt(0)).getAbsolutePath();
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}
	}

	/**
	 * ����ϷŵĻ���ԭ��
	 * ��1����List���϶�ʱ��dragGestureRecognized������JList�б�ѡ���������һ��Transferable����
	 * ��2���������ݵ�List��ʱ��drop������Transferable�����е����ݷŵ�List�С�
	 */
}
