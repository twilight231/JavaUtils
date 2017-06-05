package com.zstax.util.常用类.mutimedia.greadsnake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 	�ߵĿ����࣬�������̰���ߡ�MVC�е�Control
 */
public class SnakeControl implements KeyListener{ 
    
	// �����Ƶ�̰���ߵ�ģ�Ͷ���
    private SnakeModel model;
    // ̰���ߵ���ͼ����
    private SnakeView view;
    
    // ̰���ߵ����������С
    private int district_maxX;
    private int district_maxY;    
    
    // Ĭ�Ϲ��췽��
    public SnakeControl(){
        this.district_maxX = 30;
        this.district_maxY = 40;
        init();
    }
    
    // �������Ĺ��췽��
    public SnakeControl(int maxX, int maxY) {
    	// �жϲ����Ƿ�Ϸ������ﶨ��������������������С��Χ
        if ((10 < maxX) && (maxX < 100) && (10 < maxY) && (maxY < 100)){
            this.district_maxX = maxX;
            this.district_maxY = maxY;
        } else {
        	System.out.println("��ʼ������������Ĭ�ϲ����������");
            this.district_maxX = 30;
            this.district_maxY = 40;
        }
        init();
    }
    
    // ��ʼ��
    private void init(){
    	// ����̰����ģ��
        this.model = new SnakeModel(district_maxX, district_maxY);
        // ����̰������ͼ
        this.view = new SnakeView(district_maxX, district_maxY, 500, 200);
        // Ϊģ�������ͼ����ģ�͸ı�ʱ���ܹ�֪ͨ������ͼ
        this.model.addObserver(this.view);
        // Ϊ��ͼ��Ӽ����¼�������
        this.view.addKeyListener(this);
        // �߿�ʼ����
        (new Thread(this.model)).start();
    }
    
    // ��������¼�
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // ֻ����̰���ߴ�������״̬�£��Ŵ���İ����¼�
        if (model.isRunning()) {
            switch (keyCode) {
            	// ���������еķ���
                case KeyEvent.VK_UP:
                    model.changeDirection(SnakeModel.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.changeDirection(SnakeModel.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    model.changeDirection(SnakeModel.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.changeDirection(SnakeModel.RIGHT);
                    break;
                // ���������е��ٶ�
                case KeyEvent.VK_ADD:
                case KeyEvent.VK_PAGE_UP:
                    model.speedUp();
                    break;
                case KeyEvent.VK_SUBTRACT:
                case KeyEvent.VK_PAGE_DOWN:
                    model.speedDown();
                    break;
                // �����ߵ���ͣ��ָ�
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_P:
                    model.changePauseState();
                    break;
                default:
            }
        }

        // ������Ϸ�����¿�ʼ
        if (keyCode == KeyEvent.VK_R || keyCode == KeyEvent.VK_S 
        		|| keyCode == KeyEvent.VK_ENTER) {
            model.reset();
        }
        // ֹͣ��Ϸ
        if (keyCode == KeyEvent.VK_Q) {
            model.setRunning(false);
        }
    }

    public void keyReleased(KeyEvent e) {
    	// do nothing
    }
    public void keyTyped(KeyEvent e) {
    	// do nothing
    }
}