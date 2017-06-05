package com.zstax.util.常用类.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * �������л�������ѡ������л����󣬶����ǰ����ж����һ�����ݽ������л���
 * SerializeObject��ʾ��ʹ��transit�ؼ��ֿ��Կ��Ʊ����������л���
 * ��������ʾͨ��ʵ��Externalizable�ӿڿ��ƶ�������л��ͷ����л���
 */
public class SerializeControl {
	/**
	 * �ڲ��࣬���ڲ��Կ��ƶ�������л��ͷ����л���
	 * �����л��Ķ������ʵ��Externalizable�ӿڣ��ýӿ���2��������
     * writeExternal������readExternal������
     * �����л������ʱ��writeExternal�������Զ�ִ�У����еĶ���д������ɸ÷������ơ�
     * �����л������ʱ��readExternal�������Զ�ִ�У���������writeExternalд������ݣ�
     * Ҳֻ���ڸ÷������д�ָ�����Ĵ��롣
	 */
	static class MyClassControl implements Externalizable{
	     int serialClassInt;
	     int a=3, b=4;
	     public MyClassControl(){
	         System.out.println("MyClass constructor!");
	         this.serialClassInt = 9;
	     }
	     
	     public void show(){
	         System.out.println("serialClassInt: " + serialClassInt);
	     }
	    //�����л������ʱ�򣬸÷����Զ�������
	    public void writeExternal(ObjectOutput out) throws IOException{
	        System.out.println("run writeExternal");
	        //���������л�ʱд������ı�����
	        Date d = new Date();
	        out.writeObject(d);
	        //ֻ���л�serialClassInt������a��b���������������л�
	        out.writeInt(this.serialClassInt);        
	    }
	    // �������л������ʱ�� �÷����Զ�������
		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			System.out.println("run readExternal");
			Date d = (Date) in.readObject();
			System.out.println(d);
			this.serialClassInt = in.readInt();
		} 
	}
	/**
	 * ���л�����
	 */
    public static void serializable(String fileName) throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        MyClassControl my1 = new MyClassControl();
        out.writeObject(my1);
        out.close();        
    }
    /**
     * �����л�
     */
    public static void deserializable(String fileName)throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        MyClassControl my2 = (MyClassControl)in.readObject();
        my2.show();
        in.close();
    }

	public static void main(String[] args) throws Exception{
		String fileName = "c:/temp/MyClassControl.ser";
		SerializeControl.serializable(fileName);
		SerializeControl.deserializable(fileName);
	}
}
