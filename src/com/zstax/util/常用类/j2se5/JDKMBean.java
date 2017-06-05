package com.zstax.util.常用类.j2se5;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * �û�����ͨ��MXBean���Թ���ͼ����������
 */
public class JDKMBean {

	/**
	 * MemoryMXBean��Java ��������ڴ�ϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ�һʵ��
	 */
	public static void printMemoryMXBean() {
		// ��õ�һʵ��
		MemoryMXBean instance = ManagementFactory.getMemoryMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// �������ڶ������Ķѵĵ�ǰ�ڴ�ʹ����
		System.out.printf("%s: %s%n", "HeapMemoryUsage", instance
				.getHeapMemoryUsage());
		// ���� Java �����ʹ�õķǶ��ڴ�ĵ�ǰʹ����
		System.out.printf("%s: %s%n", "getNonHeapMemoryUsage", instance
				.getNonHeapMemoryUsage());
		// ��������������
		instance.gc();
	}

	/**
	 * ClassLoadingMXBean��Java ������������ϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printClassLoadingMXBean() {
		// ��õ�һʵ��
		ClassLoadingMXBean instance = ManagementFactory.getClassLoadingMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ���ص�ǰ���ص� Java ������е��������
		System.out.printf("%s: %s%n", "LoadedClassCount", instance
				.getLoadedClassCount());
		// ������ Java �������ʼִ�е�Ŀǰ�Ѿ����ص��������
		System.out.printf("%s: %s%n", "TotalLoadedClassCount", instance
				.getTotalLoadedClassCount());
		// ������ Java �������ʼִ�е�Ŀǰ�Ѿ�ж�ص��������
		System.out.printf("%s: %s%n", "UnloadedClassCount", instance
				.getUnloadedClassCount());
	}

	/**
	 * ThreadMXBean��Java ������߳�ϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printThreadMXBean() {
		// ��õ�һʵ��
		ThreadMXBean instance = ManagementFactory.getThreadMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ���ػ�̵߳ĵ�ǰ��Ŀ�������ػ��̺߳ͷ��ػ��̡߳�
		System.out.printf("%s: %s%n", "ThreadCount", instance.getThreadCount());
		// ���ػ�߳� ID
		System.out.printf("%s: %n", "Thread IDs");
		long[] ids = instance.getAllThreadIds();
		for (long id : ids) {
			System.out.printf("%s;  ", id);
		}
		System.out.printf("%n");
		// ���ػ�ػ��̵߳ĵ�ǰ��Ŀ
		System.out.printf("%s: %s%n", "DaemonThreadCount", instance
				.getDaemonThreadCount());
		// �����Դ� Java ������������ֵ����������ֵ��̼߳���
		System.out.printf("%s: %s%n", "PeakThreadCount", instance
				.getPeakThreadCount());
		// ���ص�ǰ�̵߳��� CPU ʱ��
		System.out.printf("%s: %s%n", "CurrentThreadCpuTime", instance
				.getCurrentThreadCpuTime());
		// ���ص�ǰ�߳����û�ģʽ��ִ�е� CPU ʱ��
		System.out.printf("%s: %s%n", "CurrentThreadUserTime", instance
				.getCurrentThreadUserTime());
	}

	/**
	 * RuntimeMXBean��Java �����������ʱϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printRuntimeMXBean() {
		// ��õ�һʵ��
		RuntimeMXBean instance = ManagementFactory.getRuntimeMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ����������������������������ļ���������·��
		System.out.printf("%s: %s%n", "BootClassPath", instance
				.getBootClassPath());
		// ����ϵͳ������������������ļ��� Java ��·��
		System.out.printf("%s: %s%n", "ClassPath", instance.getClassPath());
		// ���ش��ݸ� Java �������������������в��������ݸ� main �����ı���
		System.out.printf("%s%n", "InputArguments");
		List<String> args = instance.getInputArguments();
		for (String arg : args) {
			System.out.printf("%s;  ", arg);
		}
		// ���� Java ��·��
		System.out.printf("%s: %s%n", "LibraryPath", instance.getLibraryPath());
		// �����������е� Java �����ʵ�ֵĹ���ӿڵĹ淶�汾
		System.out.printf("%s: %s%n", "ManagementSpecVersion", instance
				.getManagementSpecVersion());
		// ���ر�ʾ�������е� Java �����������
		System.out.printf("%s: %s%n", "Name", instance.getName());

		// ���� Java ������淶����
		System.out.printf("%s: %s%n", "SpecName", instance.getSpecName());
		// ���� Java ������淶��Ӧ��
		System.out.printf("%s: %s%n", "SpecVendor", instance.getSpecVendor());
		// ���� Java ������淶�汾
		System.out.printf("%s: %s%n", "SpecVersion", instance.getSpecVersion());

		// ���� Java �����ʵ������
		System.out.printf("%s: %s%n", "VmName", instance.getVmName());
		// ���� Java �����ʵ�ֹ�Ӧ��
		System.out.printf("%s: %s%n", "VmVendor", instance.getVmVendor());
		// ���� Java �����ʵ�ְ汾
		System.out.printf("%s: %s%n", "VmVersion", instance.getVmVersion());

		// ���� Java �����������ʱ��
		System.out.printf("%s: %s%n", "StartTime", instance.getStartTime());
		// ���� Java ���������������ʱ��
		System.out.printf("%s: %s%n", "Uptime", instance.getUptime());
	}

	/**
	 * OperatingSystemMXBean�ǲ���ϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printOperatingSystemMXBean() {
		// ��õ�һʵ��
		OperatingSystemMXBean instance = ManagementFactory
				.getOperatingSystemMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ���ز���ϵͳ�ļܹ�
		System.out.printf("%s: %s%n", "Arch", instance.getArch());
		// ���� Java ���������ʹ�õĴ�������Ŀ
		System.out.printf("%s: %s%n", "AvailableProcessors", instance
				.getAvailableProcessors());
		// ���ز���ϵͳ����
		System.out.printf("%s: %s%n", "Name", instance.getName());
		// ���ز���ϵͳ�汾
		System.out.printf("%s: %s%n", "Version", instance.getVersion());
	}

	/**
	 * CompilationMXBean��Java ������ı���ϵͳ�Ĺ���ӿ� 
	 * Java ��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printCompilationMXBean() {
		// ��õ�һʵ��
		CompilationMXBean instance = ManagementFactory.getCompilationMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ���ؼ�ʱ (JIT) ������������
		System.out.printf("%s: %s%n", "Name", instance.getName());
		// �����ڱ����ϻ��ѵ��ۻ��ķ�ʱ��Ľ���ֵ
		System.out.printf("%s: %s%n", "TotalCompilationTime", instance
				.getTotalCompilationTime());
	}

	/**
	 * GarbageCollectorMXBean��Java ��������������յĹ���ӿ� 
	 * Java ��������ܾ��д˽ӿڵ�ʵ�����һ������ʵ��
	 */
	public static void printGarbageCollectorMXBean() {
		// �������ʵ��
		List<GarbageCollectorMXBean> instances = ManagementFactory
				.getGarbageCollectorMXBeans();
		System.out.printf("%n---%s---%n", GarbageCollectorMXBean.class
				.getName());
		// ����ÿ��ʵ��
		for (GarbageCollectorMXBean instance : instances) {
			// ���������ռ���������
			System.out.printf("***%s: %s***%n", "Name", instance.getName());
			// �����ѷ����Ļ��յ��ܴ���
			System.out.printf("%s: %s%n", "CollectionCount", instance
					.getCollectionCount());
			// ���ؽ��Ƶ��ۻ�����ʱ��
			System.out.printf("%s: %s%n", "CollectionTime", instance
					.getCollectionTime());
		}
	}

	public static void main(String[] args) {
		JDKMBean.printOperatingSystemMXBean();
		JDKMBean.printRuntimeMXBean();
		JDKMBean.printClassLoadingMXBean();
		JDKMBean.printCompilationMXBean();
		JDKMBean.printMemoryMXBean();
		JDKMBean.printThreadMXBean();
		JDKMBean.printGarbageCollectorMXBean();
	}
}
