package com.zstax.util.常用类.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jdk1.4�м�����java.util.regex���ṩ��������ʽ��֧�֡�
 * ����Java.lang.String���е�replaceAll��split����Ҳ�ǵ��õ�������ʽ��ʵ�ֵġ�
 */
public class RegexExpression {

	/**
	 * ����������ʽ����ƥ���ַ���
	 */
	public static void testFind() {
		// ^����ƥ���ַ����Ŀ�ͷ
		// ƥ����abc��ͷ���ַ���
		RegexExpression.find("abcdef", "^abc");
		RegexExpression.find("Aabc def", "^abc");
		System.out.println();

		// $����ƥ���ַ����Ľ�β
		// ƥ����def��β���ַ���
		RegexExpression.find("Aabcdef", "def$");
		RegexExpression.find("AabcdeF", "def$");
		// ���ͬʱʹ��^���ź�$���ţ������о�ȷƥ��
		RegexExpression.find("def", "^def$");
		RegexExpression.find("abcdefg", "^def$");
		System.out.println();

		// *����ƥ��0������ǰ����ַ�
		RegexExpression.find("a", "ab*");
		RegexExpression.find("ab", "ab*");
		RegexExpression.find("abbb", "ab*");
		System.out.println();

		// +����ƥ������һ��ǰ����ַ�
		RegexExpression.find("a", "ab+");
		RegexExpression.find("ab", "ab+");
		RegexExpression.find("abbb", "ab+");
		System.out.println();

		// ?����ƥ��0����1��ǰ����ַ�
		RegexExpression.find("a", "ab?c?");
		RegexExpression.find("ab", "ab?c?");
		RegexExpression.find("abc", "ab?c?");
		RegexExpression.find("abbcb", "ab?c?");
		System.out.println();

		// .����ƥ������з�������κ��ַ���
		RegexExpression.find("a", ".");
		// .��+������ƥ������з�����������ַ���
		RegexExpression.find("dasf4566a`1345=-=4bsd", ".+");
		System.out.println();

		// x|yƥ��"x"��"y"
		// abc|xyz ��ƥ�� "abc"�� "xyz"����"ab(c|x)yz"ƥ�� "abcyz"��"abxyz"
		RegexExpression.find("x", "x|y");
		RegexExpression.find("y", "x|y");
		RegexExpression.find("abc", "abc|xyz");
		RegexExpression.find("xyz", "abc|xyz");
		RegexExpression.find("abc", "ab(c|x)yz");
		RegexExpression.find("abcyz", "ab(c|x)yz");
		System.out.println();

		// {n}ƥ��ǡ��n�Σ�nΪ�Ǹ�������ǰ����ַ�
		RegexExpression.find("aa", "a{3}");
		RegexExpression.find("aaa", "a{3}");
		System.out.println();

		// {n,}ƥ������n�Σ�nΪ�Ǹ�������ǰ����ַ�
		RegexExpression.find("aaa", "a{3,}");
		RegexExpression.find("aaaaa", "a{3,}");
		System.out.println();

		// {m,n}ƥ������m��������n��ǰ����ַ�
		RegexExpression.find("aaa", "a{3,4}");
		RegexExpression.find("aaaa", "a{3,4}");
		RegexExpression.find("aaaaa", "a{3,4}");
		System.out.println();

		// [xyz]��ʾһ���ַ�����ƥ���������ַ�������֮һ
		RegexExpression.find("a", "[abc]");
		RegexExpression.find("b", "[abc]");
		RegexExpression.find("c", "[abc]");
		RegexExpression.find("ab", "[abc]");
		System.out.println();

		// [^xyz]��ʾһ���񶨵��ַ�����ƥ�䲻�ڴ������е��κ��ַ�
		RegexExpression.find("a", "[^abc]");
		RegexExpression.find("x", "[^abc]");
		RegexExpression.find("8", "[^abc]");
		System.out.println();

		// [a-z]ƥ���"a"��"z"֮����κ�һ��Сд��ĸ�ַ�
		RegexExpression.find("c", "[b-d]");
		RegexExpression.find("f", "[b-d]");
		RegexExpression.find("$", "[b-d]");
		System.out.println();

		// [^a-z]��ʾĳ����Χ֮����ַ���ƥ�䲻��ָ����Χ�ڵ��ַ�
		RegexExpression.find("f", "[b-d]");
		RegexExpression.find("b", "[b-d]");
		System.out.println();

		// [a-zA-Z] a��z��A��Z
		RegexExpression.find("B", "[a-cA-F]");
		RegexExpression.find("G", "[a-cA-F]");
		System.out.println();

		// [a-z-[bc]] a��z������b��c
		RegexExpression.find("c", "[a-z-[bcd]]");
		RegexExpression.find("e", "[a-z-[bcd]]");
		RegexExpression.find("f", "[a-z-[e-x]]");
		System.out.println();

		// �����ַ���\n���з���\f��ҳ����\r�س���\t�Ʊ��
		RegexExpression.find("\n", "\n");
		RegexExpression.find("\f", "\f");
		RegexExpression.find("\r", "\r");
		RegexExpression.find("\t", "\t");
		System.out.println();

		// \\��ʾת���\����ʹ��ʱҪ��\\\\����\\��\\����\��
		RegexExpression.find("\\", "\\\\");
		System.out.println();

		// \s �κΰ��ַ��������ո��Ʊ������ҳ���ȡ��ȼ���"[\f\n\r\t]"
		// ʹ��\sʱǰ���ټ�һ��\
		RegexExpression.find("\n", "\\s");
		RegexExpression.find("\f", "\\s");
		RegexExpression.find("\r", "\\s");
		RegexExpression.find("\t", "\\s");
		System.out.println();

		// \S �κηǿհ׵��ַ����ȼ���"[^\f\n\r\t]"
		// ʹ��\sʱǰ���ټ�һ��\
		RegexExpression.find("\n", "\\S");
		RegexExpression.find("\f", "\\S");
		RegexExpression.find("a", "\\S");
		RegexExpression.find("9", "\\S");
		System.out.println();

		// \w �κε����ַ���������ĸ���»��ߡ��ȼ���"[A-Za-z0-9_]"
		// ʹ��ʱ��\\w
		RegexExpression.find("a", "\\w");
		RegexExpression.find("9", "\\w");
		RegexExpression.find("X", "\\w");
		RegexExpression.find("_", "\\w");
		System.out.println();

		// \W �κηǵ����ַ����ȼ���"[^A-Za-z0-9_]"
		// ʹ��ʱ��\\W
		RegexExpression.find("a", "\\W");
		RegexExpression.find("9", "\\W");
		RegexExpression.find("$", "\\W");
		RegexExpression.find("#", "\\W");
		System.out.println();

		// \dƥ��һ�������ַ����ȼ���[0-9]
		RegexExpression.find("6", "\\d");
		RegexExpression.find("9", "\\d");
		RegexExpression.find("A", "\\d");
		System.out.println();

		// \Dƥ��һ���������ַ����ȼ���[^0-9]
		RegexExpression.find("%", "\\D");
		RegexExpression.find("$", "\\D");
		RegexExpression.find("A", "\\D");
		RegexExpression.find("8", "\\D");
		System.out.println();

		// \bƥ�䵥�ʵĽ�β
		RegexExpression.find("love", "ve\\b");
		RegexExpression.find("very", "ve\\b");
		System.out.println();

		// \Bƥ�䵥�ʵĿ�ͷ
		RegexExpression.find("love", "ve\\B");
		RegexExpression.find("very", "ve\\B");
		System.out.println();
	}
	/**
	 * ����������ʽ����ƥ���ַ�����
	 * @param str   ��ƥ����ַ���
	 * @param regex ������ʽ
	 * @return
	 */
	public static boolean find(String str, String regex) {
		// ��������ʽ�����һ��Pattern
		Pattern p = Pattern.compile(regex);
		// ����һ��Matcher
		Matcher m = p.matcher(str);
		// Matcher��find�����������һ���ƥ�䣬ֻҪ���ҵ�����������ʽ���Ӵ����ͷ���true
		boolean b = m.find();
		System.out.println("\"" + str + "\" ƥ��������ʽ \"" + regex + "\" ?  " + b);
		return b;
	}
	/**
	 * ��������������ʽ ��ȷƥ�� �ַ���
	 */
	public static void testMatch() {
		RegexExpression.match("abcdef", "^abc");
		RegexExpression.match("Aabc def", "^abc");
		RegexExpression.match("Aabcdef", "def$");
		RegexExpression.match("AabcdeF", "def$");
		RegexExpression.match("def", "^def$");
	}
	/**
	 * ��ȷƥ���ַ�����������ʽ����ν��ȷƥ�����ַ�����ÿ���ַ�������������ʽ
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean match(String str, String regex) {
		// ��������ʽ�����һ��Pattern
		Pattern p = Pattern.compile(regex);
		// ����һ��Matcher
		Matcher m = p.matcher(str);
		boolean b = m.matches();// ��ȷƥ��
		System.out.println("\"" + str + "\" ��ȷƥ��������ʽ \"" + regex + "\" ?  "
				+ b);
		return b;
	}
	/**
	 * ��������������ʽ�滻�ַ���
	 */
	public static void testReplace() {
		// ���ַ������ظ��Ŀո��滻Ϊһ���ո�
		RegexExpression.replace("a  a    a   a", " {2,}", " ");
		RegexExpression.replace("abcad a", "a", "x");
	}
	/**
	 * ����������ʽ�滻�ַ���
	 * @param str    ���滻���ַ���
	 * @param regex  ������ʽ
	 * @param newStr �����滻���ַ���
	 * @return
	 */
	public static String replace(String str, String regex, String newStr) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		// ���´��滻��������������ʽ���Ӵ�
		String s = m.replaceAll(newStr);
		System.out.println("\"" + str + "\" ��ƥ��������ʽ \"" + regex + "\" ���ֱ� \""
				+ newStr + "\" �滻��: " + s);
		return s;
	}
	/**
	 * ����������ʽ�ָ��ַ���
	 */
	public static void testSplit() {
		// ���ո�ָ��ַ������ո�������������Ķ����
		System.out.println("RegexExpression.split(\"ab  aba a    bbc bc\", \" +\", 5): result");
		RegexExpression.outputStrArray(RegexExpression.split(
				"ab  aba a    bbc bc", " +", 5));
		// ������ĸb�ָ��ַ���
		System.out
				.println("RegexExpression.split(\"ab  aba a    bbc bc\", \"b\", 5): result");
		RegexExpression.outputStrArray(RegexExpression.split(
				"ab  aba a    bbc bc", "b", 5));
	}
	/**
	 * ʹ��������ʽ�ָ��ַ���
	 * @param str   ���ָ���ַ���
	 * @param regex ������ʽ
	 * @param count ���ձ��ֳɵĶ��������ֵ
	 * @return
	 */
	public static String[] split(String str, String regex, int count) {
		Pattern p = Pattern.compile(regex);
		// ���շ���������ʽ���Ӵ��ָ��ַ���
		return p.split(str, count);
	}
	/**
	 * ����ַ�������
	 * @param array
	 */
	public static void outputStrArray(String[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.println(i + ": " + array[i]);
			}
		}
	}
	public static void main(String[] args) {
		RegexExpression.testFind();
		RegexExpression.testMatch();
		System.out.println();
		RegexExpression.testReplace();
		System.out.println();
		RegexExpression.testSplit();
		System.out.println();
		// ���Ҳ��滻�� �滻��ǰ2������������ʽ���Ӵ�
		Pattern p = Pattern.compile("a+");
		Matcher m = p.matcher("bba bb aaa bbabb bab");
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while ((m.find()) && (i < 2)) {
			// ���ַ����е�ƥ��Ĳ����滻��
			// ��������ƥ��֮�䣨������ƥ��Ĳ��֣����ַ���׷�ӵ�sb��
			m.appendReplacement(sb, "XX");
			i++;
		}
		// ���ַ�����û�н���ƥ��Ĳ���ȫ��׷�ӵ�sb����
		m.appendTail(sb);
		System.out.println(sb.toString());
	}
	/**
	 * �ڳ�����ʹ��������ʽ�Ĳ���һ�����£� 1. ����Pattern����ͨ����̬����Pattern.compile(); 2.
	 * ȡ��Matcher����ͨ��pattern.matcher(CharSequence charSequence); 3.
	 * ����Matcher�������ز��ҷ�����
	 * java.lang.CharSequence�ӿ�����JDK1.4�汾�б���ӽ����ģ���Ϊ��ͬ�����char����
	 * �ṩ��ͳһ��ֻ�����ʡ�ʵ�ֵ�����String, StringBuffer, java.nio.CharBuffer
	 * Matcher�ṩ������ͬ�Ĳ��ҷ�������String���matches()�����������£�
	 * match() ʹ�������ַ��������ģʽ��Ƚϣ���String��matches()���
	 * lookingAt() ���ַ�����ͷ�ĵط������ģʽ�Ƚ�
	 * find() ƥ���ַ�����û�б�Ҫ���ַ����ĵ�һ���ַ���ʼ�����ǰһ������ƥ�䣬 ����ƥ����û�����õĻ�����Ӳ�ƥ��ĵ�һ���ַ���ʼ��
	 * ����ÿ������������boolean���ͣ�����true����ζ��ƥ�䣬����false��ƥ�䡣
	 * Ҫ��������String�Ƿ�ƥ�������ģʽ��ֻ�����������Ϳ����ˣ�
	 * Matcher m = Pattern.compile(yourPattern).matcher(yourString);
	 * if (m.find()) { System.out.println(��match��); } else {
	 * System.out.println(��no match��); }
	 */
}