package com.zstax.util.常用类.basic;

public class Operator {
	/**
	 * ���������: +; -; *; /;
	 */
	private void computeOperator() {
		int a = 8;
		int b = 5;
		// ���ڳ������㣬���ݻ������͵��Զ�ת�����򣬵������ͱ�������������ʱ���õ��Ľ��Ҳ��������
		// ��� 8/5�õ�����1��������1.6
		int f = a / b;
		double g = a / b;
		System.out.println("(f = a / b) = " + f + "; (g = a / b) = " + g);
		// ֻҪ�����ͱ�������һ��Ϊdouble����float������Ͳ�ͬ�ˣ�8/5.0�õ���1.6
		double h = a / (b * 1.0d);
		float i = a / (b * 1.0f);
		System.out.println("(h = a / (b * 1.0d)) = " + h + "; (i = a / (b * 1.0f)) = " + i);
	}

	/**
	 * �Ƚ������: ==; <; >; !=; <=; >=;
	 */
	private void compareOperator() {
		int a = 5;
		int b = 10;
		System.out.println("(a == b) = " + (a == b)); 
		System.out.println("(a < b) = " + (a < b)); 
		System.out.println("(a > b) = " + (a > b)); 
		System.out.println("(a != b) = " + (a != b)); 
		System.out.println("(a <= b) = " + (a <= b)); 
		System.out.println("(a >= b) = " + (a >= b)); 
		// �ж����������Ƿ���ȣ�Ҫ��"=="������"="��ǰ���ǱȽ�������������Ǹ�ֵ����
		System.out.println("(a = b) = " + (a = b)); // 10
	}

	/**
	 * λ�����: &; |; ^; ~; >>; >>>; <<;
	 */
	private void bitOperator() {
		byte a = 23; // "00010111"
		byte b = 26; // "00011010"

		// ��λ��, ������������Ϊ1ʱ�����Ϊ1��������Ϊ0
		int c = a & b; // "00010010" = 18
		System.out.println("(c = a & b) = " + c);
		// ��λ��������������Ϊ0ʱ�����Ϊ0��������Ϊ1
		int d = a | b; // "00011111" = 31
		System.out.println("(d = a | b) = " + d);
		// ��λ���������������ͬʱ���Ϊ0����ͬʱ���Ϊ1
		int e = a ^ b; // "00001101" = 13
		System.out.println("(e = a ^ b) = " + e);
		// ��λ�ǣ�0���1��1���0
		int f = ~a; // "11101000" = -24,
		// ע�⣬Java�в��ò���洢���֣�����������ԭ���벹��һ�£�
		// ���ڸ���������λ���䣬��ԭ��ȡ��Ȼ���1���õ����룬����"11101000"��Ӧ��ԭ����"10011000"��-24
		System.out.println("(f = ~a) = " + f);
		// ���ƣ���߿ճ�λ�Է���λ���
		int g = a >> 1;// "00001011" = 11
		System.out.println("(g = a >> 1) = " + g);
		// ���ƣ���߿ճ�λ��0���
		int h = a >>> 1;// "00001011" = 11
		System.out.println("(h = a >>> 1)" + h);
		// �Ը�������ʱ��>>��>>>�õ�����᲻һ��
		System.out.println("(-24 >> 1) = " + (-24 >> 1) + "\t (-24 >>> 1) = " + (-24 >>> 1));
		// ����
		int i = a << 1; // "00101110" = 46
		System.out.println("(a << 1) = " + i);

		// ���Է��֣�����1λ�൱������������2������1λ�൱������������2��
		// ʵ���ϣ�����nλ���൱������������2��n�η�������nλ���൱������������2��n�η������߿������顣
		// �ڽ��г˳�������ʱ��������Ҫ����ʶ����������ص㣬����������ٶȡ�
	}

	/**
	 * ���������: &&; ||; !; &; |; ^;
	 */
	private void booleanOperator() {
		boolean b1 = true;
		boolean b2 = true;
		boolean b3 = false;

		// &&��������Բ��������������㣬�����в�������Ϊtrueʱ�����Ϊtrue��������Ϊfalse��
		if (b1 && b2 && b3) {
			System.out.println("����b1, b2, b3��ֵ��Ϊtrue");
		} else {
			System.out.println("����b1, b2, b3��������һ����ֵΪfalse");
		}
		// ע��&&�Ƕ�·�룬��˼�ǣ����Բ������ı��ʽ���д���������ʱ���������в�������ֵΪfalse����������㣬�������Ϊfalse��
		int i = 2;
		int j = 0;
		if (b3 && ((j = i) == 2)) {
		}
		System.out.println("j = " + j); 
		if (b1 && ((j = i) == 2)) {
		}
		System.out.println("j = " + j); 

		// ||��������Բ��������л����㣬�����в�������Ϊfalseʱ�����Ϊfalse��������Ϊtrue��
		if (b1 || b2 || b3) {
			System.out.println("����b1, b2, b3��������һ����ֵΪtrue");
		} else {
			System.out.println("����b1, b2, b3��ֵ��Ϊfalse");
		}
		// ͬ����||�Ƕ�·����˼�ǣ����Բ������ı��ʽ���д���������ʱ���������в�������ֵΪtrue���ͽ������㣬�������Ϊtrue��
		if (b1 || ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j);
		if (b3 || ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j);

		// !��������Բ�������ֵ����ȡ�����㣬������Ϊtrue��ȡ��Ϊfalse��
		if (!b1) {
			System.out.println("����b1 Ϊ false����Ϊb1ȡ�����ֵΪtrue");
		} else {
			System.out.println("����b1 Ϊ true����Ϊb1ȡ�����ֵΪfalse");
		}

		// &���������&&һ�����Բ����������������ͬ���������Ƕ�·�ģ��������������еĲ��������ʽ
		if (b3 & ((j = i) == 2)) {
			System.out.println("b3 & ((j=i) == 2): true");
		} else {
			System.out.println("b3 & ((j=i) == 2): false");
		}
		System.out.println("j = " + j); 
		if (b1 & ((j = j - 1) == 0)) {
		}
		System.out.println("j = " + j);

		// |���������||һ�����Բ��������л�������������Ƕ�·�ġ�
		if (b1 | ((j = i) == 2)) {
			System.out.println("b1 | ((j=i) == 2): true");
		} else {
			System.out.println("b1 | ((j=i) == 2): false");
		}
		System.out.println("j = " + j); 
		if (b3 | ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j); 

		// ^��������Բ�����������������ͬΪfalse����ͬΪtrue
		if (b1 ^ b2) {
			System.out.println("����b1��b2��ֵ��ͬ��");
		} else {
			System.out.println("����b1��b2��ֵһ����");
		}

		// ע�⣺������ԱӦ�þ���ʹ�ö�·��&&���߶�·��||��������&��|����������Ϊ�����Ƕ������������ʽ�ļ���Ľ����һ���ġ�
		// ʹ��&&��||���Լ�������ʱ�䡣������Ҫ���������ʽ�н��и�ֵ�����㣬���Խ��������ŵ�if֮�⡣
		// j = i;
		// if (b3 && (j==2)){
		// }
	}

	/**
	 * ��ֵ�����: =; +=; -=; *=; /=; &=; |=; ^=;
	 */
	private void evaluateOperator() {
		// =����õĸ�ֵ�����
		int i = 5;// ������i��ֵ��Ϊ5
		// +=��������Ƚ��������ߵĲ�������ֵ�����ұߵĲ�������ֵ��������ٸ�ֵ���������ߵĲ�����
		i += 3;// �ȼ��� i = i + 3;
		i -= 3;// �ȼ���i = i - 3;
		i *= 3;// �ȼ���i = i * 3;
		i /= 3;// �ȼ���i = i / 3;
		i &= 3;// �ȼ���i = i & 3
		System.out.println("(i &= 3) = " + i);
		i |= 3;// �ȼ���i = i | 3
		System.out.println("(i |= 3) = " + i);
		i ^= 3;// �ȼ���i = i ^ 3
		System.out.println("(i ^= 3) = " + i);
	}

	/**
	 * ���������: ��Ԫ�����; ++; --; -;
	 * 
	 */
	private void otherOperator() {
		int i = 5;
		// ++�ǽ���������1
		// ++i��ʾ�Ƚ�i��1���ٽ�������
		if (i++ == 5) {
			System.out.println("���ʽ(i++ == 5)��ֵΪtrue");
		} else {
			System.out.println("���ʽ(i++ == 5)��ֵΪfalse");
		}
		System.out.println("i = " + i);
		// i++��ʾ�Ƚ������㣬�ٽ�i��ֵ��1
		i = 5;
		if (++i == 5) {
			System.out.println("���ʽ(++i == 5)��ֵΪtrue");
		} else {
			System.out.println("���ʽ(++i == 5)��ֵΪfalse");
		}
		System.out.println("i = " + i);
		// --����������1��ͬ����--i���Ƚ�i��1���ٽ������㣻i++���Ƚ������㣬�ٽ�i��1
		i--;
		--i;

		// ��������Ŀ���������򵥵�if���
		// ��ʽ�ǣ� x = a ? b : c�����a��ֵΪtrue����b��ֵ����x������c��ֵ����x��
		int x = (i >= 0) ? i : -i; //��һ������-���������ã���ȡ���ĸ���
		System.out.println("i�ľ���ֵΪ: " + x); 
	}

	public static void main(String[] args) {
		Operator test = new Operator();
		System.out.println("��������������������");
		test.computeOperator();
		System.out.println("�Ƚ�����������������");
		test.compareOperator();
		System.out.println("λ����������������");
		test.bitOperator();
		System.out.println("��������������������");
		test.booleanOperator();
		System.out.println("��ֵ����������������");
		test.evaluateOperator();
		System.out.println("��������������������");
		test.otherOperator();
	}
}
