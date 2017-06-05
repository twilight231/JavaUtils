package com.zstax.util.常用类.applet.tower;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ��Ϸ������������Ϸ�Ĺ�������Ϸ����Ҫʵ����
 */
public class GameGroup {
	// ��ʾ��Ϣ
	private String noteMsg;
	// ��ʾ�Ƿ��ڽ��е�����ʾ
	private boolean isStep;
	// ����ִ��ʱ�Ĳ�����
	private int stepActionCode;
	// ˢ����Ϸ������ģʽ
	private int drawMode;
	// ��ʾ��ǰ�����Ƿ��Ѿ��ɹ�����
	private boolean diskMoved;
	// ������
	private Tower[] towerArray;
	// ���ϵ�����
	private int disksNum;
	// ��Ų����Ķ�ջ
	private java.util.Stack paramsStack;
	// ����
	private Params theseParams;
	
	// �˴β���Ҫ����Ҫ�ƶ�������
	private int needMoveDisksNum;
	// �˴β���Ҫ���̴��ĸ���������
	private int fromTower;
	// �˴β���Ҫ�����Ƶ��ĸ�����ȥ
	private int toTower;
	// �˴β������м������ĸ�
	private int interTower;
	
	// ��ʶ��Ϸ�Ƿ��ڳɹ�
	private boolean isDoneFlag;
	
	/**
	 * ���캯������Ϸ�����ϵ�����
	 * @param disksNum
	 */
	public GameGroup(int disksNum) {
		this.disksNum = disksNum;
		// ��֮��Ŀ�ȱ�������
		int widthFactor = Constants.DISK_MAX_WIDTH / disksNum;
		// ����3����
		towerArray = new Tower[3];
		towerArray[0] = new Tower(Constants.TOWER_FIRST_XPOS, 'A', disksNum);
		towerArray[1] = new Tower(Constants.TOWER_FIRST_XPOS + Constants.TOWER_X_DISTANCE, 'B', disksNum);
		towerArray[2] = new Tower(Constants.TOWER_FIRST_XPOS + 2 * Constants.TOWER_X_DISTANCE, 'C', disksNum);
		// Ϊ��A�����disksNum����
		for (int j = 0; j < disksNum; j++) {
			// ����̵Ŀ�ȣ�����µ��̣�������
			int diskWidth = Constants.DISK_MAX_WIDTH - j * widthFactor;
			// ��������̵���ɫ
			int colorR = 75 + (int) (Math.random() * 180D);
			int colorG = 75 + (int) (Math.random() * 180D);
			int colorB = 75 + (int) (Math.random() * 180D);
			Color color = new Color(colorR, colorG, colorB);
			// �½�һ���̣����ҷ���A���Ķ���
			Disk disk1 = new Disk(diskWidth, color, String.valueOf(disksNum - j));
			towerArray[0].insertDisk(disk1);
		}

		paramsStack = new java.util.Stack();
		noteMsg = "Press any button, or drag disk to another post";
		diskMoved = false;
		isDoneFlag = false;
		drawMode = Constants.ALL_DRAWMODE;
	}
	/**
	 * ������Ϣ
	 */
	public void creationError() {
		noteMsg = "Before using New, enter number of disks (1 to 10)";
		drawMode = Constants.ONLY_DISK_DRAWMODE;
	}
	/**
	 * ȷ������Ϸ
	 */
	public void warningNew() {
		noteMsg = "ARE YOU SURE? Press again to reset game";
	}
	/**
	 * ��Ϸ�Ƿ�ɹ�
	 */
	public boolean isDone() {
		return isDoneFlag;
	}
	/**
	 * ������Ϸ�Ƿ�ɹ�
	 */
	public void setDone(boolean flag) {
		isDoneFlag = flag;
	}

	/**
	 * ��ʼ������ƶ�һ����
	 * @param x	����X����
	 * @param y	����Y����
	 */
	public void startDrag(int x, int y) {
		// ��ʱ�̻�û�б��ƶ�
		diskMoved = false;
		// ���������������ʼ���ı��
		fromTower = getClosedTower(x, y);
		if (fromTower == -1) {
			noteMsg = "DRAG the CENTER of the disk";
			return;
		}
		// �ж���ʼ�����Ƿ����̣�û���������ƶ�
		if (towerArray[fromTower].isEmpty()) {
			noteMsg = "NO DISKS on tower " + towerArray[fromTower].label;
			fromTower = -1;
		} else {
			// ���̣����ƶ��̣�ˢ��ʱֻ��Ҫ���̾����ˣ�����Ҫ�ػ�������
			noteMsg = "Dragging from tower " + towerArray[fromTower].label;
			drawMode = Constants.ONLY_DISK_DRAWMODE;
		}
	}
	/**
	 * ����������ƶ���
	 * @param x	����X����
	 * @param y	����Y����
	 */
	public void endDrag(int x, int y) {
		// ��ʱ�̻�û�б��ƶ�
		diskMoved = false;
		// ���Ŀ�����ı��
		toTower = getClosedTower(x, y);
		// ������û��ѡ��Ŀ����������û����ʼ����������ʼ����Ŀ����һ�����������ƶ���
		if (fromTower == -1 || toTower == -1 || fromTower == toTower) {
			noteMsg = "Drag a colored DISK to a different black TOWER";
			return;
		}
		// ��ʼ�ƶ���
		noteMsg = "Dragged to tower " + towerArray[toTower].label;
		// �ƶ���ʱ��Ҫע�⣬Ŀ�����ϵ���Ӧ�ñȱ��ƶ����̿�
		if (!towerArray[toTower].isEmpty()
				&& towerArray[fromTower].peekDisk().width > towerArray[toTower]
						.peekDisk().width) {
			noteMsg = "Must put a SMALLER disk ON a LARGER disk";
			return;
		}
		// ����ʼ�������ߴ��ƶ����̣��������̣������뵽Ŀ������
		Disk disk1 = towerArray[fromTower].removeDisk();
		towerArray[toTower].insertDisk(disk1);
		// ��ʱ���Ѿ����ƶ��ˡ�
		diskMoved = true;
		noteMsg = "Moved disk " + disk1.label + " from " + towerArray[fromTower].label
				+ " to " + towerArray[toTower].label;
		// �����C�ϵ������ˣ�����Ϸ�ɹ��ˡ�
		if (towerArray[2].isFull()) {
			noteMsg = "Congratulations! You moved all the disks!";
		} else {
			// û�ɹ��������ˢ�£�����ˢ��ʱֻ����
			drawMode = Constants.ONLY_DISK_DRAWMODE;
		}
	}

	/**
	 * ��þ��������������ı��
	 * @param x	����X����
	 * @param y	����Y����
	 * @return
	 */
	public int getClosedTower(int x, int y) {
		// ������Ϊֻ�о�����������35���ص�λ�ã������ڸ���
		byte range = 35;
		if (Math.abs(x - Constants.TOWER_FIRST_XPOS) < range) {
			// ������A
			return 0;
		} else if (Math.abs(x - Constants.TOWER_FIRST_XPOS
				- Constants.TOWER_X_DISTANCE) < range) {
			// ������B
			return 1;
		}
		// ��C���߲������κ���
		return Math.abs(x - Constants.TOWER_FIRST_XPOS - 2
				* Constants.TOWER_X_DISTANCE) >= range ? -1 : 2;
	}

	/**
	 * �жϵ�ǰ�����Ƿ�����Զ�����
	 * @return
	 */
	public boolean canAutoRun(){
		if (towerArray[0].isEmpty() || !towerArray[1].isEmpty()
				|| !towerArray[2].isEmpty()) {
			noteMsg = "You must begin with ALL DISKS ON TOWER A";
			return false;
		} 
		return true;
	}
	/**
	 * ������Ϸ�Ƿ��������ִ��
	 * @param step
	 */
	public void setStep(boolean step){
		isStep = step;
	}
	
	/**
	 * ����ִ����Ϸ
	 */
	public void step() {
		// �̻�û�б��ƶ�
		diskMoved = false;
		if (!isStep) {
			// �տ�ʼ����ִ����Ϸ
			isStep = true;
			// ִ����Ϊ1
			stepActionCode = 1;
		}
		switch (stepActionCode) {
		default:
			break;

		case 1: // ������Ϸ�ĵ�һ�����������������
			if (towerArray[0].isEmpty() || !towerArray[1].isEmpty()
					|| !towerArray[2].isEmpty()) {
				// �ж��Ƿ����㵥��ִ�еĵ�һ�������������������̶�����A�ϡ�
				noteMsg = "You must begin with ALL DISKS ON TOWER A";
				stepActionCode = 1;
			} else {
				// ��������ʱ����ʼִ�е����������е��̴�A�Ƶ�C
				noteMsg = "Will shift all disks from A to C";
				// �����ƶ���������������캯���Ĳ�������ֱ��ǣ�
				// Ҫ�ƶ�����������ʼ���š�Ŀ�����š���ת���š��ò������񱻳ɹ�ִ��ʱ�ĺ��������
				theseParams = new Params(disksNum, 0, 2, 1, 8);
				// �����������ջ
				paramsStack.push(theseParams);
				// ��һ��Ϊ�ڶ���
				stepActionCode = 2;
			}
			break;

		case 2: //ִ�еڶ�������ȡ��һ������Ĳ�������ʾ��һ����ʲô
			// ȡ��ջ�����������������ȡ��ϸ��Ϣ
			theseParams = (Params)paramsStack.peek();
			needMoveDisksNum = theseParams.num;
			fromTower = theseParams.from;
			toTower = theseParams.to;
			interTower = theseParams.inter;
			// ��ʾ������Ϣ
			noteMsg = "Entering function with " + needMoveDisksNum + " disks";
			// ��һ��Ϊ������
			stepActionCode = 3;
			break;

		case 3: // ִ�е������������ʼ����һ���̣��򽫸����Ƶ�Ŀ���������򣬲�������
			// �����Ҫ�ƶ�������Ϊ1
			if (needMoveDisksNum == 1) {
				// ȡ����ʼ�������̣�������������ɾ��
				Disk disk1 = towerArray[fromTower].removeDisk();
				// ���뵽Ŀ������
				towerArray[toTower].insertDisk(disk1);
				// ��ʱ���Ѿ����ƶ���
				diskMoved = true;
				// ��Ϊ���ƶ�����ֻ��һ���������ƶ��������һ����
				noteMsg = "Moved last disk " + disk1.label + " from "
						+ towerArray[fromTower].label + " to "
						+ towerArray[toTower].label;
				// ���C�����ˣ�����Ϸ�ɹ�
				if (towerArray[2].isFull()){
					noteMsg = "Congratulations! You moved all the disks!";
				}
				// ��һ��ִ�е�7��
				stepActionCode = 7;
			} else {
				// ������ƶ����̲�ֻ1��������һ��ִ�е�4��
				noteMsg = "More than one disk, will continue";
				stepActionCode = 4;
			}
			break;

		case 4: //ִ�е�4��������ʼ�������һ����������������Ƶ�Ŀ����
			// ��Ҫ�Ƚ�ǰneedMoveDisksNum - 1���̴���ʼ���ƶ���Ŀ���������ܹ��������
			noteMsg = "Will move top " + (needMoveDisksNum - 1) + " disks from "
					+ towerArray[fromTower].label + " to " + towerArray[interTower].label;
			// ���Է�һ���������������ǰneedMoveDisksNum - 1���̴���ʼ���ƶ���Ŀ������������ִ�����ִ�е�5����
			theseParams = new Params(needMoveDisksNum - 1, fromTower, interTower, toTower, 5);
			paramsStack.push(theseParams);
			// ��һ��ִ�е�2��
			stepActionCode = 2;
			break;

		case 5: //ִ�е�5��������ʼ�����һ�����Ƶ�Ŀ����
			// ȡ����ʼ��������
			Disk disk2 = towerArray[fromTower].removeDisk();
			// ���뵽Ŀ��������
			towerArray[toTower].insertDisk(disk2);
			// �̱��ƶ���
			diskMoved = true;
			// ��ʱ��ʼ���ϵ������̶��������ˣ��������һ���̱��Ƶ�����C�������̱��ƶ�������B
			noteMsg = "Moved remaining disk " + needMoveDisksNum + " from "
					+ towerArray[fromTower].label + " to " + towerArray[toTower].label;
			// ��һ��ִ�е�6��
			stepActionCode = 6;
			break;

		case 6: // ִ�е�6��������ת��B�ϵ��̣�needMoveDisksNum - 1�����ƶ���Ŀ������
			noteMsg = "Will move top " + (needMoveDisksNum - 1) + " disks from "
					+ towerArray[interTower].label + " to " + towerArray[toTower].label;
			// �½�һ�������������
			theseParams = new Params(needMoveDisksNum - 1, interTower, toTower, fromTower, 7);
			paramsStack.push(theseParams);
			// ��һ��ִ�е�2��
			stepActionCode = 2;
			break;

		case 7: // ִ�е�7������һ������ִ����ʱ����ò�
			// ��ø������ƶ��˵�����
			int i = needMoveDisksNum;
			// ȡ���������һ������
			stepActionCode = theseParams.actionCode;
			// ����������������������Ѿ������
			paramsStack.pop();
			if (!paramsStack.isEmpty()) {
				// �����������������ȡ��ջ����������ȡ����
				theseParams = (Params)paramsStack.peek();
				needMoveDisksNum = theseParams.num;
				fromTower = theseParams.from;
				toTower = theseParams.to;
				interTower = theseParams.inter;
			}
			noteMsg = "Returning from function with " + i + " disks";
			break;

		case 8: // ��ִ������������ʱ������ò�����ʱ��Ϸ�ɹ���
			noteMsg = "Press New to reset";
			isDoneFlag = true;
			stepActionCode = 1;
			break;
		}
		// ���ڵ���ִ����Ϸ����ֻ��Ҫ���̣�����Ҫ����
		drawMode = Constants.ONLY_DISK_DRAWMODE;
	}

	/**
	 * ����Ϸ����
	 * @param g
	 */
	public void draw(Graphics g) {
		
		// ���ǵ�ˢ����Ҫ���ۣ�����Ҫ�������ٻ�������
		// ��Ϊ����ÿ�β����˶���Ҫ�ػ����ж����ġ����Զ�����2��ģʽ��
		
		// ���Ҫ�����е��̺���
		if (drawMode == Constants.ALL_DRAWMODE) {
			// �Ȼ���Ϸ����
			g.setColor(Color.lightGray);
			g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
			// ���м����Ϸ����
			g.setColor(Color.black);
			g.drawRect(Constants.GAME_DISTRICT_XPOS, Constants.GAME_DISTRICT_YPOS,
					Constants.GAME_DISTRICT_WIDTH, Constants.GAME_DISTRICT_HEIGHT);
			// ����
			for (int i = 0; i < 3; i++){
				towerArray[i].drawTower(g, drawMode, 0);
			}
		} else {
			// ���ֻ��Ҫ���̣�����
			for (int j = 0; j < 3; j++){
				if (diskMoved && j == fromTower) {
					towerArray[j].drawTower(g, drawMode, Constants.DELETEFROM_OP);
				} else if (diskMoved && j == toTower) {
					towerArray[j].drawTower(g, drawMode, Constants.ADDTO_OP);
				}
			}
		}
		// ����Ϸ�ϲ��İ�ť�������ڵı߿�
		g.setColor(Color.lightGray);
		g.fillRect(10, 45, 430, 25);
		// ����ʾ��Ϣ
		g.setColor(Color.black);
		g.drawString(noteMsg, 16, 64);
		drawMode = Constants.ALL_DRAWMODE;
	}
}