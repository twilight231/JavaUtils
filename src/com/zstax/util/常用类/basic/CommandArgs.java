package com.zstax.util.常用类.basic;

public class CommandArgs {
	/**
	 * ��������ʱ����ӡ������Ϣ�����˳�����
	 * @param errmsg  ������Ϣ
	 */
	public static void error(String errmsg) {
		System.err.println(errmsg);
		System.exit(1);
	}

	public static void main(String[] args) {
		//�������µİ�����Ϣ
		String usageMsg = "Usage: CommandArgs [options]\n"
				+ "Where [options] are:\n"
				+ " -help                    ��ʾ������Ϣ\n"
				+ " -n <name>                ���ò���������\n"
				+ " -v <value>               ���ò�����ֵ\n";

		String name = null;
		String value = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-n")) {
				if ((i + 1) == args.length) {
					error("Error: -n requires an argument.");
				}
				name = args[++i];
			} else if (args[i].equals("-v")){
				if ((i + 1) == args.length) {
					error("Error: -v requires an argument.");
				}
				value = args[++i];
			} else if (args[i].equalsIgnoreCase("-help")) {
				System.out.println(usageMsg);
				System.exit(0);
			} else {
				error("Error: argument not recognized: " + args[i]);
			}
		}
		System.out.println("[�����н������] name: " + name + "; value: " + value);
	}
}
