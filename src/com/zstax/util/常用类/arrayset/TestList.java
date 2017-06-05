package com.zstax.util.常用类.arrayset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Vector;

/**
 * ��ʾ����List��ʹ��
 * List����ά��Ԫ�صĴ���������Ԫ���ظ�
 */
public class TestList {
	
	/**
	 * ��ʼ��һ��List
	 * @param list
	 */
	public static void init(List list){
		if(list != null){
			list.add("aaa");
			list.add("ccc");
			list.add("bbb");
			list.add("eee");
			list.add("ddd");
		}
	}
	/**
	 * ���List������
	 * @param list
	 */
	public static void output(List list){
		if (list != null){
			//�����б��±������ʹ��list.size()��ȡ�б���Ԫ�صĸ���
			for (int i=0; i<list.size(); i++){
				System.out.print(list.get(i) + "  ");
			}
			//�����õ���������
			Iterator it  = list.iterator();
			Object value = null;
			while (it.hasNext()){
				value = it.next();
				//System.out.println(value);
			}
		}
		System.out.println();
	}
	/**
	 * ʹ��ArrayList
	 */
	public static void testArrayList(){
		List list = new ArrayList();
		init(list);
		System.out.println("ʹ��ArrayList: ");
		output(list);
	}
	/**
	 * ʹ��Vector
	 */
	public static void testVector(){
		List list = new Vector();
		init(list);
		System.out.println("ʹ��Vector: ");
		output(list);
	}
	/**
	 * ʹ��LinkedList
	 */
	public static void testLinkedList(){
		List list = new LinkedList();
		init(list);
		System.out.println("ʹ��LinkedList: ");
		output(list);
	}
	
	public static void main(String[] args) {
		TestList.testArrayList();
		TestList.testVector();
		TestList.testLinkedList();
		
		List list = new ArrayList();
		init(list);
		//List֧��Ԫ���ظ�
		list.add("aaa");
		list.add("bbb");
		System.out.println("����Ԫ��aaa, bbb��");
		output(list);
		//ָ��Ԫ�ز����λ��
		list.add(1, "fff");
		System.out.println("���±�Ϊ1������fff��");
		output(list);
		List list2 = new ArrayList();
		list2.add("ggg");
		list2.add("hhh");
		//����һ�б��е�Ԫ�ز��뵽�б���
		list.addAll(list2);
		System.out.println("���list2��Ԫ�غ�");
		output(list);

		//�ж��б��Ƿ����ĳһԪ��
		//ͨ��Ԫ�ص�equals�������ж�Ԫ���Ƿ����
		System.out.println("list����aaa? " + list.contains("aaa"));
		//�ж��б����Ƿ����������һ���б��е�����Ԫ�ء�
		System.out.println("list����list2�е�����Ԫ��? " + list.containsAll(list2));
		//��λһ��Ԫ�����б������ȳ��ֵ�λ��
		System.out.println("aaa��list�е�һ�γ��ֵ�λ��: " + list.indexOf("aaa"));
		//��λһ��Ԫ�����б��������ֵ�λ��
		System.out.println("aaa��list�����һ�γ��ֵ�λ��: " + list.lastIndexOf("aaa"));
		
		//�����б���ĳ��λ�õ�Ԫ��ֵ
		list.set(2, "xxx");
		System.out.println("����λ��Ϊ2��Ԫ��Ϊxxx��");
		output(list);
		//ɾ���б��е�ĳ��Ԫ�أ�ֻɾ����һ�γ��ֵ��Ǹ�
		list.remove("aaa");
		System.out.println("ɾ��Ԫ��aaa��");
		output(list);
		//ɾ���б���ָ��λ�õ�Ԫ��
		list.remove(1);
		System.out.println("ɾ���±�Ϊ1��Ԫ�غ�");
		output(list);
		//ɾ���б��е�����Ԫ�أ�ֻ������һ���б��а�����Ԫ��
		list.retainAll(list2);
		System.out.println("ɾ����list2�����������Ԫ�غ�");
		output(list);
		//ɾ���б�������һ�б���Ҳ�����˵�Ԫ��
		list.removeAll(list2);
		System.out.println("ɾ��list2������Ԫ�غ�");
		output(list);
		
		//����б�
		list.clear();
		//�ж��б����Ƿ�������
		System.out.println("���List��listΪ��ô?  " + list.isEmpty());
		init(list);
		//���б��е�ĳЩԪ�ع���һ���µ��б�
		list2 = list.subList(1,3);
		System.out.println("��list�ĵ�1������3��Ԫ�ع���һ���µ�List:");
		output(list2);
		
		//��List���еı�����ListIterator�����б�
		//����ͨ��Iterator���ã�������������������б�
		ListIterator listIt = list.listIterator();
		System.out.println("��������б�");
		while (listIt.hasNext()){
			System.out.print(listIt.next());
		} 
		System.out.println();
		System.out.println("��������б�");
		while (listIt.hasPrevious()){
			System.out.print(listIt.previous());
		} 
		System.out.println();
		//Ҳ����ʹ��ListIterator��List�м�����ɾ��Ԫ�أ�
		//ֻ���ڱ�������ǰλ����Ӻ�ɾ����
		listIt.add("newadd");
		System.out.println("��ListIterator���б������Ԫ��newadd��: ");
		output(list);
		listIt.next();
		listIt.remove();
		System.out.println("��ListIteratorɾ���б���Ԫ�غ�: ");
		output(list);

		//LinkedList�Զ���ķ���
		LinkedList linklist = new LinkedList();
		init(linklist);
		//���Ԫ�ص��б�ͷ
		linklist.addFirst("fff");
		System.out.println("��fff�ŵ��б�ͷ��");
		output(linklist);
		//���Ԫ�ص��б�β
		linklist.addLast("eee");
		System.out.println("��eee�ŵ��б�β��");
		output(linklist);
		//��ȡ��ͷԪ��
		System.out.println("�б�ͷԪ�أ�" + linklist.getFirst());
		//��ȡ��βԪ��
		System.out.println("�б�βԪ�أ�" + linklist.getLast());
		//ɾ���б�ͷ��Ԫ��
		linklist.removeFirst();
		System.out.println("ɾ���б�ͷԪ�غ�");
		output(linklist);
		//ɾ���б�β��Ԫ��
		linklist.removeLast();
		System.out.println("ɾ���б�βԪ�غ�");
		output(linklist);
		
		//��ջStack�࣬���̳���Stack��
		Stack myStack =  new Stack();
		//����Ԫ�أ��ǲ��뵽β��
		myStack.push("aaa");
		myStack.push("bbb");
		myStack.push("ccc");
		myStack.push("ddd");
		myStack.push("aaa");
		myStack.push("ddd");
		System.out.println("��ջ�е�Ԫ����: ");
		output(myStack);
		System.out.println("��ջβ��Ԫ��: " + myStack.peek());
		System.out.println("������ջβ��Ԫ��: " + myStack.pop());
		
		/**
		 * ��1��ArrayList����õ�Listʵ���࣬�ڲ���ͨ������ʵ�ֵģ��������Ԫ�ؽ��п���������ʡ������ȱ����ÿ��Ԫ��֮�䲻�ܺ��С���϶����
		 * �������С������ʱ�����Ӵ洢�������������������ݸ��Ƶ��µĴ洢�ռ��С�����ArrayList���м�λ�ò������ɾ��Ԫ��ʱ����Ҫ��������п������ƶ������۱Ƚϸߡ���ˣ����ʺ��漴���Һͱ��������ʺϲ����ɾ����
		 * ��2��Vector��ArrayListһ����Ҳ��ͨ������ʵ�ֵġ���ͬ������֧���̵߳�ͬ����
		 * ��һʱ��ֻ��һ���߳��ܹ�дVector��������߳�ͬʱд����Ĳ�һ���ԡ���ʵ��ͬ����Ҫ�ܸߵĻ��ѣ�
		 * ��ˣ��������ȷ���ArrayList����
		 * ��3��LinkedList��������ṹ�洢���ݵģ����ʺ����ݵĶ�̬�����ɾ�����漴���ʺͱ����ٶȱȽ��������⣬�����ṩ��Listû�ж���ķ�����ר�����ڲ�����ͷ�ͱ�βԪ�أ����Ե�����ջ�����к�˫�����ʹ�á�
		 * ��4����ΪArrayList��Vector����������ʵ�ֵģ����ԣ�����ӺͲ���ʱ����ôӱ�β����������Ҫ���м���߱�ͷ��ʼ���Ա��������ƶ�����Ŀ�����
		 * ��5������Ϊÿ��List����ListIterator��֧��˫�����List�������ܹ���ListIteratorλ�ò����ɾ��Ԫ�ء�
		 * ��6����ջ��̳�Vector�������Ƕ��б��β�����ݽ��в������������Ƚ�����Ĳ��ԣ��Զ����˲��롢�鿴�͵���Ԫ������������
		 */
	}
}
