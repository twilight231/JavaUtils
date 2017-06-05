package com.zstax.util.常用类.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/**
 * ���Ĵ��塢��������
 */
public class LookAndFeel {

	public static void main(String[] args) {
		/**
		 * Metal��񡪡�"javax.swing.plaf.metal.MetalLookAndFeel"
		 * Motif��񡪡�"com.sun.java.swing.plaf.motif.MotifLookAndFeel"
		 * Windows��񡪡�"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
		 */
		Calculator calculator1 = new Calculator();
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//3����ͬ�ķ��
		/**
		 * LookAndFeel��һ�������࣬�����ṩ��һЩstatic��������������һЩ����ĸ��Ի����÷�����������ʵ�֡� 
		 ��JDK1.1.3��ʼ��Sun�ṩ������LookAndFeel������
		 */
		String lnfName = "javax.swing.plaf.metal.MetalLookAndFeel";
//		String lnfName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//		String lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			// UIManager������ٵ�ǰ����ۼ���Ĭ�����ã�setLookAndFeel��������������LookAndFeel����ȫ��
		    UIManager.setLookAndFeel(lnfName);
		    // setLookAndFeel������ֱ���������ö���
		    UIManager.setLookAndFeel(new MetalLookAndFeel());
//		    UIManager.setLookAndFeel(new MotifLookAndFeel());
//		    UIManager.setLookAndFeel(new WindowsLookAndFeel());
		    
		    //SwingUtilities��һ��Swing�Ĺ����࣬�ṩ�˺ܶྲ̬������
		    //updateComponentTreeUI������calculator1��������������
		    SwingUtilities.updateComponentTreeUI(calculator1);
		}
		catch (UnsupportedLookAndFeelException ex1) {
		    System.err.println("Unsupported LookAndFeel: " + lnfName);
		}
		catch (ClassNotFoundException ex2) {
		    System.err.println("LookAndFeel class not found: " + lnfName);
		}
		catch (InstantiationException ex3) {
		    System.err.println("Could not load LookAndFeel: " + lnfName);
		}
		catch (IllegalAccessException ex4) {
		    System.err.println("Cannot use LookAndFeel: " + lnfName);
		}
		calculator1.setVisible(true);
	}
}
