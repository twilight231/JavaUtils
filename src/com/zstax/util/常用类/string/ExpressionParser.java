package com.zstax.util.常用类.string;

/**
 * һ���򵥵ı��ʽ��������������������Լ��������֡��������������ɵı��ʽ��ֵ�����ܴ��������
 * Ϊ�˴���򵥣���������ֻ֧��һ����ĸ�ı����������ֱ�����ĸ�Ĵ�Сд����ˣ����ֻ�ܴ洢26��������
 * ����û��ı��������ȴ���һ����ĸ����ֻȡ��һ����ĸ����������
 */
public class ExpressionParser {

	// 4�ֱ������
	/**	���Ϊ�ջ��߽�����	*/
	public static final int NONE_TOKEN = 0;
	/**	���Ϊ�ָ���*/
	public static final int DELIMITER_TOKEN = 1;
	/**	���Ϊ����*/
	public static final int VARIABLE_TOKEN = 2;
	/**	���Ϊ����*/
	public static final int NUMBER_TOKEN = 3;

	// 4�ִ�������
	/**	�﷨����	*/
	public static final int SYNTAX_ERROR = 0;
	/**	����û�н�������	*/
	public static final int UNBALPARENS_ERROR = 1;
	/**	���ʽΪ�մ���	*/
	public static final int NOEXP_ERROR = 2;
	/**	��0������	*/
	public static final int DIVBYZERO_ERROR = 3;
	//������ִ������ͣ�������ĸ�������ʾ
	public static final String[] ERROR_MESSAGES = { "Syntax Error", "Unbalanced Parentheses",
			"No Expression Present", "Division by Zero" };
	/**	���ʽ�Ľ������*/
	public static final String EOE = "\0";
	
	/**	���ʽ�ַ���*/
	private String exp;  
	/**	��������ǰָ���ڱ��ʽ�е�λ��*/
	private int expIndex;   
	/**	��������ǰ����ı��*/
	private String token;  
	/**	��������ǰ�����ǵ�����*/
	private int tokenType; 

	/**	����������*/  
	private double vars[] = new double[26];

	/**
	 * ����һ�����ʽ�����ر��ʽ��ֵ��
	 * @param expStr	���ʽ�ַ���
	 * @return
	 * @throws Exception
	 */  
	public double evaluate(String expStr) throws Exception {
		double result;
		this.exp = expStr;
		this.expIndex = 0;

		//��ȡ��һ�����
		this.getToken();
		if (this.token.equals(EOE)){
			//û�б��ʽ�쳣
			this.handleError(NOEXP_ERROR); // no expression present
		}

		//����ֵ���
		result = this.parseAssign();
		//�����긳ֵ��䣬Ӧ�þ��Ǳ��ʽ��������������ǣ��򷵻��쳣
		if (!this.token.equals(EOE)){  
			this.handleError(SYNTAX_ERROR);
		}

		return result;
	}

	/**
	 * ����ֵ���
	 */  
	private double parseAssign() throws Exception {
		double result;//���
		int varIndex;//�����±�
		String oldToken;//�ɱ��
		int oldTokenType;//�ɱ�ǵ�����

		//�����������Ǳ���
		if (this.tokenType == VARIABLE_TOKEN) {
			// ���浱ǰ���  
			oldToken = new String(this.token);
			oldTokenType = this.tokenType;
			// ȡ�ñ�������������������ֻ֧��һ����Ŀ�ı�����
			//����û��ı�����ĸ���ȴ���1����ȡ��һ����ĸ��������  
			varIndex = Character.toUpperCase(this.token.charAt(0)) - 'A';
			
			//�����һ�����
			this.getToken();
			//�����ǰ��ǲ��ǵȺ�=
			if (!this.token.equals("=")) {
				//�ع�
				this.putBack();  
				// ����һ����ֵ��䣬����ǻָ�����һ�����  
				this.token = new String(oldToken);
				this.tokenType = oldTokenType;
			} else {
				//�����ǰ����ǵȺ�=������������ֵ����ʽ��a = 3 + 5;
				//�����Ⱥź�����ʽ��ֵ��Ȼ�󽫵õ���ֵ��������
				this.getToken();
				//��Ϊ�Ӽ��������ȼ���ͣ����Լ���Ӽ������ʽ��
				result = this.parseAddOrSub();
				//�����ʽ��ֵ����������������ʵ������vars�С�
				this.vars[varIndex] = result;
				//���ر��ʽ��ֵ
				return result;
			}
		}
		//�����ǰ������Ͳ��Ǳ��������߲��Ǹ�ֵ��䣬���üӼ���������ʽ��ֵ��
		return this.parseAddOrSub();
	}

	/**
	 * ����Ӽ������ʽ
	 */ 
	private double parseAddOrSub() throws Exception {
		char op;//������
		double result;//���
		double partialResult;//�ӱ��ʽ�Ľ��
		//�ó˳������㵱ǰ�ӱ��ʽ��ֵ
		result = this.parseMulOrDiv();
		//�����ǰ��ǵĵ�һ����ĸ�ǼӼ��ţ���������мӼ������㡣
		while ((op = this.token.charAt(0)) == '+' || op == '-') {
			//ȡ��һ�����
			this.getToken();
			//�ó˳������㵱ǰ�ӱ��ʽ��ֵ
			partialResult = this.parseMulOrDiv();
			switch (op) {
			case '-':
				//����Ǽ����������Ѵ�����ӱ��ʽ��ֵ��ȥ��ǰ�ӱ��ʽ��ֵ
				result = result - partialResult;
				break;
			case '+':
				//����Ǽӷ������Ѵ�����ӱ��ʽ��ֵ���ϵ�ǰ�ӱ��ʽ��ֵ
				result = result + partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * ����˳������ʽ������ȡģ����
	 */ 
	private double parseMulOrDiv() throws Exception {
		char op;//������
		double result;//���
		double partialResult;//�ӱ��ʽ�Ľ��
		//��ָ��������㵱ǰ�ӱ��ʽ��ֵ
		result = this.parseExponent();
		//�����ǰ��ǵĵ�һ����ĸ�ǳˡ�������ȡģ���������������г˳������㡣
		while ((op = this.token.charAt(0)) == '*' || op == '/' || op == '%') {
			//ȡ��һ�����
			this.getToken();
			//��ָ��������㵱ǰ�ӱ��ʽ��ֵ
			partialResult = this.parseExponent();
			switch (op) {
			case '*':
				//����ǳ˷��������Ѵ����ӱ��ʽ��ֵ���Ե�ǰ�ӱ��ʽ��ֵ
				result = result * partialResult;
				break;
			case '/':
				//����ǳ��������жϵ�ǰ�ӱ��ʽ��ֵ�Ƿ�Ϊ0�����Ϊ0�����׳���0���쳣
				//��������Ϊ0
				if (partialResult == 0.0){
					this.handleError(DIVBYZERO_ERROR);
				}
				//������Ϊ0������г�������
				result = result / partialResult;
				break;
			case '%':
				//�����ȡģ���㣬ҲҪ�жϵ�ǰ�ӱ��ʽ��ֵ�Ƿ�Ϊ0
				//���Ϊ0�����׳���0���쳣
				if (partialResult == 0.0){
					this.handleError(DIVBYZERO_ERROR);
				}
				//����ȡģ����
				result = result % partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * ����ָ�����ʽ
	 * @throws Exception
	 */  
	private double parseExponent() throws Exception {
		double result;//���
		double partialResult;//�ӱ��ʽ��ֵ
		double ex;//ָ���ĵ���
		int t;//ָ������

		//��һԪ������㵱ǰ�ӱ��ʽ��ֵ��������
		result = this.parseUnaryOperator();
		//�����ǰ���Ϊ"^"���������Ϊָ������
		if (this.token.equals("^")) {
			//��ȡ��һ����ǣ�����ȡָ������
			this.getToken();
			partialResult = this.parseExponent();
			ex = result;
			if (partialResult == 0.0) {
				//���ָ������Ϊ0����ָ����ֵΪ1
				result = 1.0;
			} else {
				//����ָ����ֵΪ����Ϊָ���ݵĵ�����˵Ľ����
				for (t = (int) partialResult - 1; t > 0; t--){
					result = result * ex;
				}
			}
		}
		return result;
	}

	/**
	 * ����һԪ���㣬+��-����ʾ�����͸���
	 */  
	private double parseUnaryOperator() throws Exception {
		double result;//���
		String op;//������

		op = "";
		//�����ǰ�������Ϊ�ָ��������ҷָ�����ֵ����+����-��
		if ((this.tokenType == DELIMITER_TOKEN) && 
				this.token.equals("+") || this.token.equals("-")) {
			op = this.token;
			this.getToken();
		}
		//������������㵱ǰ�ӱ��ʽ��ֵ
		result = this.parseBracket();
		if (op.equals("-")){
			//���������Ϊ-�����ʾ���������ӱ��ʽ��ֵ��Ϊ����
			result = -result;
		}

		return result;
	}

	/**
	 * ������������
	 */  
	private double parseBracket() throws Exception {
		double result;//���
		//�����ǰ���Ϊ�����ţ����ʾ��һ����������
		if (this.token.equals("(")) {
			//ȡ��һ�����
			this.getToken();
			//�üӼ�����������ӱ��ʽ��ֵ
			result = this.parseAddOrSub();
			//�����ǰ��ǲ����������ţ��׳����Ų�ƥ���쳣
			if (!this.token.equals(")")){
				this.handleError(UNBALPARENS_ERROR);
			}
			//����ȡ��һ�����
			this.getToken();
		} else {
			//�����ǰ��ǲ��������ţ���ʾ����һ���������㣬����ԭ��Ԫ����������ӱ��ʽ��ֵ
			result = this.parseAtomElement();
		}

		return result;
	}

	/**
	 * ����ԭ��Ԫ�����㣬��������������
	 * @return
	 * @throws Exception
	 */  
	private double parseAtomElement() throws Exception {
		double result = 0.0;//���
		
		switch (this.tokenType) {
		case NUMBER_TOKEN:
			//�����ǰ�������Ϊ����
			try {
				//�����ֵ��ַ���ת��������ֵ
				result = Double.parseDouble(this.token);
			} catch (NumberFormatException exc) {
				this.handleError(SYNTAX_ERROR);
			}
			//ȡ��һ�����
			this.getToken();
			break;
		case VARIABLE_TOKEN:
			//�����ǰ��������Ǳ�������ȡ������ֵ
			result = this.findVar(token);
			this.getToken();
			break;
		default:
			this.handleError(SYNTAX_ERROR);
			break;
		}
		return result;
	}

	/**
	 * ���ݱ�������ȡ������ֵ��������������ȴ���1����ֻȡ�����ĵ�һ���ַ�
	 * @param vname	������
	 * @throws Exception
	 */ 
	private double findVar(String vname) throws Exception {
		//��������ĵ�һ���ַ�������ĸ�����׳��﷨�쳣
		if (!Character.isLetter(vname.charAt(0))) {
			handleError(SYNTAX_ERROR);
			return 0.0;
		}
		//��ʵ����������vars��ȡ���ñ�����ֵ
		return vars[Character.toUpperCase(vname.charAt(0)) - 'A'];
	}

	/**
	 * �ع�������������ǰָ����ǰ�Ƶ���ǰ���λ��
	 */
	private void putBack() {
		if (this.token == EOE){
			return;
		}
		//��������ǰָ����ǰ�ƶ�
		for (int i = 0; i < this.token.length(); i++) {
			this.expIndex--;
		}
	}

	/**
	 * �����쳣���
	 * @param errorType	��������
	 * @throws Exception
	 */
	private void handleError(int errorType) throws Exception {
		//�����쳣���ʱ�����ݴ������ͣ�ȡ���쳣��ʾ��Ϣ������ʾ��Ϣ��װ���쳣���׳�
		//���Կ������Զ����쳣��������Exception
		throw new Exception(ERROR_MESSAGES[errorType]);
	}

	/**
	 * ��ȡ��һ�����
	 */  
	private void getToken() {
		//��ʼֵ
		this.tokenType = NONE_TOKEN;
		this.token = "";

		// �����ʽ�Ƿ����
		// �����������ǰָ���Ѿ��������ַ����ĳ��ȣ���������ʽ�Ѿ��������õ�ǰ��ǵ���ΪEOE
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// �������ʽ�еĿհ׷� 
		while (this.expIndex < this.exp.length()
				&& Character.isWhitespace(this.exp.charAt(this.expIndex))) {
			++this.expIndex;
		}

		// �ٴμ����ʽ�Ƿ���� 
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}
		
		//ȡ�ý�������ǰָ��ָ����ַ�
		char currentChar = this.exp.charAt(this.expIndex);
		//�����ǰ�ַ���һ���ָ���������Ϊ����һ���ָ�����ǣ�����ǰ��Ǻͱ�����͸�ֵ������ָ�����
		if (isDelim(currentChar)) {  
			this.token += currentChar;
			this.expIndex++;
			this.tokenType = DELIMITER_TOKEN;
			
		} else if (Character.isLetter(currentChar)) {
			//�����ǰ�ַ���һ����ĸ������Ϊ��һ��������ǡ�
			//��������ָ�������ƣ�ֱ������һ���ָ�����֮����ַ����Ǳ�������ɲ���
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			//���ñ������Ϊ����
			this.tokenType = VARIABLE_TOKEN;
			
		} else if (Character.isDigit(currentChar)) { 
			// �����ǰ�ַ���һ�����֣�����Ϊ��ǰ��ǵ�����Ϊ����
			// ��������ָ�������ƣ�ֱ������һ���ָ�����֮����ַ����Ǹ����ֵ���ɲ���
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()){
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			//���ñ������Ϊ����
			this.tokenType = NUMBER_TOKEN;
		} else { 
			//�޷�ʶ����ַ�������Ϊ���ʽ����
			this.token = EOE;
			return;
		}
	}

	/**
	 * �ж�һ���ַ��Ƿ�Ϊ�ָ�����
	 * ���ʽ�е��ַ�����: 
	 * ��"+"����"-"����"*"����"/"��ȡģ"%"��ָ��"^"����ֵ"="��������"("��������")"
	 * @param c
	 * @return
	 */ 
	private boolean isDelim(char c) {
		if ((" +-/*%^=()".indexOf(c) != -1))
			return true;
		return false;
	}

	public static void main(String[] args) throws Exception {
		ExpressionParser test = new ExpressionParser();
		String exp1 = "a = 5.0";
		System.out.println("exp1(\"a = 4.0\") = " + test.evaluate(exp1));
		String exp2 = "b = 3.0";
		System.out.println("exp2(\"b = 3.0\") = " + test.evaluate(exp2));

		String exp3 = "(a+b) * (a-b)";
		System.out.println("exp3(\"(a+b) * (a-b)\") = " + test.evaluate(exp3));

		String exp4 = "3*5-4/2";
		System.out.println("exp4(\"3*5-4/2\") = " + test.evaluate(exp4));

		String exp5 = "(4-2)*((a+b)/(a-b))";
		System.out.println("exp5(\"(4-2)*((a+b)/(a-b))\") = " + test.evaluate(exp5));
		
		String exp6 = "5 % 2";
		System.out.println("exp6(\"5%2\") = " + test.evaluate(exp6));
		String exp7 = "3^2 * 5 + 4";
		System.out.println("exp7(\"3^2 * 5 + 4\") = " + test.evaluate(exp7));
		
		/**
		 * һ���򵥵ı��ʽ��������ʱ�����ȼ��Ӹߵ���Ϊ��
		 * ��1��ԭ��Ԫ�ر��ʽ���������ֺͱ�����
		 * ��2�����ű��ʽ��
		 * ��3��һԪ���ʽ��ȡ���ĸ�����
		 * ��4��ָ�����ʽ��
		 * ��5���ˡ�����ȡģ���ʽ��
		 * ��6���ӡ������ʽ
		 * ��7����ֵ���ʽ��
		 * ��ˣ��ڼ���һ�����ʽ��ֵʱ��Ӧ�ð����ȼ��Ӹߵ��׽������㡣
		 * �ڱ������У�ÿ�����ȼ��ı��ʽ�����㶼��һ��˽�з���ʵ�֡���˽�з����У����ȼ���������ȼ��ı��ʽ��
		 * �����������Ƶݹ���õķ�ʽ��������evaluate���������ȵ��õ������ȼ���͵ı��ʽ��ֵ��
		 * ����ʵ����ȴ�����ȼ���ߵı��ʽ��˽�з������ȱ�ִ���ꡣ��ͱ�֤�˱��ʽ�������ǰ������ȼ��Ӹߵ��׵�˳��ִ�еġ�
		 */
	}
}
