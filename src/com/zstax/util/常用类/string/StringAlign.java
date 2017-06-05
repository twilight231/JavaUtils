package com.zstax.util.常用类.string;

/**
 * �ַ���������������ָ�������ʽ��һ�е�����ַ���
 * �����ʽ������롢���С��ֶ���
 */
public class StringAlign {

	/** ������ʽ */
	public static final int JUST_LEFT = 0;
	/** ���и�ʽ*/
	public static final int JUST_CENTER = 1;
	/** �Ҷ����ʽ */
	public static final int JUST_RIGHT = 2;
	
	/** ��ǰ�����ʽ */
	private int just;
	/** һ�е���󳤶� */
	private int maxChars;

	/**
	 * Ĭ�Ϲ��캯��
	 */
	public StringAlign(){
		//Ĭ��Ϊ���ж���
		this.just = JUST_CENTER;
		//Ĭ��һ�е���󳤶�Ϊ80
		this.maxChars = 80;
	}
	/** 
	 * ����һ���ַ�������������Ҫ����һ�е���󳤶ȺͶ���ĸ�ʽ��
	 * @param maxChars
	 * @param just
	 */
	public StringAlign(int maxChars, int just) {
		//���ȹ���һ��Ĭ���ַ���������
		this();
		//���ݴ�������޸��ַ���������������
		this.setJust(just);
		this.setMaxChars(maxChars);
	}

	/** ����һ���ַ���   
	 * @param obj 	��������ַ���
	 */
	public String format(String s) {
		StringBuffer where = new StringBuffer();
		//�Ӵ�������ַ�����ȡ��һ�����ַ������Ӵ��ĳ���Ϊ����󳤶Ⱥ�s���ȵĽ�Сֵ
		int wantedLength = Math.min(s.length(), this.maxChars);
		String wanted = s.substring(0, wantedLength);
		// ���ݶ���ģʽ�����ո���ں��ʵ�λ��
		switch (this.just) {
		case JUST_RIGHT:
			//������Ҷ��룬��ô��ȱ�ٵĵ��ַ��ÿո����������
			pad(where, maxChars - wantedLength);
			//���ַ���������ұ�
			where.append(wanted);
			break;
		case JUST_CENTER:
			//���ж��룬���ո��ַ�ƽ�������ַ������ߡ�
			int startPos = where.length();
			pad(where, (maxChars - wantedLength) / 2);
			where.append(wanted);
			pad(where, (maxChars - wantedLength) / 2);
			//�����������
			pad(where, maxChars - (where.length() - startPos));
			break;
		case JUST_LEFT:
			//�Ҷ��룬���ո��ַ������ַ����ұߡ�
			where.append(wanted);
			pad(where, maxChars - wantedLength);
			break;
		}
		//���ԭ�ַ����ĳ��ȴ���һ�е���󳤶ȣ������²��ַ�����һ��
		if (s.length() > wantedLength){
			String remainStr = s.substring(wantedLength);
			where.append("\n" + this.format(remainStr));
		}
		return where.toString();
	}

	/**
	 * ��to�ĺ���append howMany���ո��ַ���
	 * @param to
	 * @param howMany
	 */
	protected final void pad(StringBuffer to, int howMany) {
		for (int i = 0; i < howMany; i++)
			to.append(" ");
	}
	
    public int getJust() {
		return just;
	}
    /**
     * �����ַ����������Ķ����ʽ
     * @param just
     */
	public void setJust(int just) {
		switch (just) {
		case JUST_LEFT:
		case JUST_CENTER:
		case JUST_RIGHT:
			this.just = just;
			break;
		default:
			System.out.println("invalid justification arg.");
		}
	}
	public int getMaxChars() {
		return maxChars;
	}
	/**
	 * �����ַ�����������һ������ַ���
	 * @param maxChars
	 */
	public void setMaxChars(int maxChars) {
		if (maxChars < 0) {
			System.out.println("maxChars must be positive.");
		} else {
			this.maxChars = maxChars;
		}
	}
	public static void main(String[] args) {
    	//һ�����70���ַ���������ʾ��
        StringAlign formatter = new StringAlign(20, StringAlign.JUST_CENTER);
        // ������ʾҳ��
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
        //�����
        System.out.println();
        formatter = new StringAlign(20, StringAlign.JUST_LEFT);
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
        //�Ҷ���
        System.out.println();
        formatter = new StringAlign(20, StringAlign.JUST_RIGHT);
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
    }
}
