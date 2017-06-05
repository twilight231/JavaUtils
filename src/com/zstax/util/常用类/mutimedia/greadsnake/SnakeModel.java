package com.zstax.util.常用类.mutimedia.greadsnake;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * ��Ϸ��Model�࣬����������Ϸ������ݼ�����
 */
class SnakeModel extends Observable implements Runnable {
    
	// �����еķ�����
    public static final int UP = 2;
    public static final int DOWN = 4;
    public static final int LEFT = 1;
    public static final int RIGHT = 3;
    
    // ָʾλ������û�����ʳ��
    private boolean[][] matrix; 
    // ����
    private LinkedList nodeArray = new LinkedList(); 
    // ʳ��
    private Node food;
    // �߻�ķ�Χ
    private int maxX;
    private int maxY;
    // �����еķ���
    private int direction = UP;
    // ����״̬
    private boolean running = false;
    // ʱ����������
    private int timeInterval = 200;  
    // ÿ�ε��ٶȱ仯��
    private double speedChangeRate = 0.75; 
    // ��ͣ��־
    private boolean paused = false;

    // �÷�
    private int score = 0; 
    // �Ե�ʳ��ǰ�ƶ��Ĵ���
    private int countMove = 0;
    
    //���췽��
    public SnakeModel(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        reset();
    }
    // ������Ϸ
    public void reset() {
    	// �����еķ����ʼ����
        direction = SnakeModel.UP;
        timeInterval = 200; 
        paused = false;
        score = 0;
        countMove = 0;

        // ��ʼ���߻�ķ�Χ����, ȫ����0
        matrix = new boolean[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            matrix[i] = new boolean[maxY];
            Arrays.fill(matrix[i], false);
        }

        // ��ʼ�����壬�������λ�ó���20��������Ϊ10������Ϊ����λ�õ�һ��
        int initArrayLength = maxX > 20 ? 10 : maxX / 2;
        nodeArray.clear();
        // ��ʼ���������ʾ
        int x = maxX / 2;
        int y = maxY / 2;
        for (int i = 0; i < initArrayLength; i++) {
            nodeArray.addLast(new Node(x, y));
            matrix[x][y] = true;
            x++;
        }

        // ����ʳ��
        food = createFood();
        matrix[food.x][food.y] = true;
    }

    // �ı����˶��ķ���
    public void changeDirection(int newDirection) {
        // �ı�ķ�������ԭ������ͬ�����
        if (direction % 2 != newDirection % 2) {
            direction = newDirection;
        }
    }

    /**
     * ������һ��
     */
    public boolean moveOn() {
    	// �����ͷ��λ��
        Node head = (Node) nodeArray.getFirst();
        int headX = head.x;
        int headY = head.y;

        // �������з�����������ֵ
        switch (direction) {
            case UP:
                headY--;
                break;
            case DOWN:
                headY++;
                break;
            case LEFT:
                headX--;
                break;
            case RIGHT:
                headX++;
                break;
        }

        // ���������������Ч��Χ�ڣ�����д���
        if ((0 <= headX && headX < maxX) && (0 <= headY && headY < maxY)) {
            if (matrix[headX][headY]) { 
                // ���������ĵ����ж������������ʳ�
                if (headX == food.x && headY == food.y) { 
                    // �Ե�ʳ��ɹ�
                    nodeArray.addFirst(food); // ����ͷ����

                    // �����������ƶ��ı䷽��Ĵ������ٶ�����Ԫ���й�
                    int scoreGet = (10000 - 200 * countMove) / timeInterval;
                    score += scoreGet > 0 ? scoreGet : 10;
                    countMove = 0;

                    food = createFood(); // �����µ�ʳ��
                    matrix[food.x][food.y] = true; // ����ʳ������λ��
                    return true;
                } else {
                    // �Ե���������ʧ��
                    return false;
                }
            } else { 
                // ���������ĵ���û�ж��������壩���ƶ�����
                nodeArray.addFirst(new Node(headX, headY));
                matrix[headX][headY] = true;
                head = (Node) nodeArray.removeLast();
                matrix[head.x][head.y] = false;
                countMove++;
                return true;
            }
        }
        // �������ߣ�ʧ��
        return false; 
    }

    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(timeInterval);
            } catch (Exception e) {
                break;
            }

            if (!paused) {
                if (moveOn()) {
                	// Model֪ͨView�����Ѿ����£��������ͼ
                    setChanged(); 
                    notifyObservers();
                } else {
                    JOptionPane.showMessageDialog(null, "you failed", 
                    		"Game Over", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
        if (!running){
            JOptionPane.showMessageDialog(null, "you stoped the game", 
            		"Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // ����ʳ��
    private Node createFood() {
        int x = 0;
        int y = 0;
        // �����ȡһ����Ч�����ڵ��������ʳ�ﲻ�ص���λ��
        do {
            Random r = new Random();
            x = r.nextInt(maxX);
            y = r.nextInt(maxY);
        } while (matrix[x][y]);

        return new Node(x, y);
    }
    // ��������
    public void speedUp() {
        timeInterval *= speedChangeRate;
    }
    // ��������
    public void speedDown() {
        timeInterval /= speedChangeRate;
    }
    // �ı���ͣ״̬
    public void changePauseState() {
        paused = !paused;
    }
    /**
     * @return Returns the running.
     */
    public boolean isRunning() {
        return running;
    }
    /**
     * @param running The running to set.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }    
    /**
     * @return Returns the nodeArray.
     */
    public LinkedList getNodeArray() {
        return nodeArray;
    } 
    /**
     * @return Returns the food.
     */
    public Node getFood() {
        return food;
    }
    /**
     * @return Returns the score.
     */
    public int getScore() {
        return score;
    }
    public String toString() {
        String result = "";
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node n = (Node) nodeArray.get(i);
            result += "[" + n.x + "," + n.y + "]";
        }
        return result;
    }
}

// λ�õ�������
class Node {
	int x;
	int y;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}


