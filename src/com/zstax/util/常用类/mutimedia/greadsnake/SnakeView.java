package com.zstax.util.常用类.mutimedia.greadsnake;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ̰���ߵ���ͼ��MVC�е�View
 */
public class SnakeView extends JFrame implements Observer{
    
	// �����з�Χ���ӵĿ�Ⱥ͸߶�
	public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;
    
    // ��Ϸ����Ŀ�Ⱥ͸߶�
    private int canvasWidth;
    private int canvasHeight;
    // ��Ϸ�������Ͻǵ�λ��
    private int startX = 0;
    private int startY = 0;
    
    // ��ʾ�����ı�ǩ
    JLabel labelScore;
    // ���ߺ͸��ӵĻ���
    Canvas paintCanvas;
    
    // Ĭ�Ϲ��췽��
    public SnakeView(){
        this(30, 40, 0, 0);
    }
    // �������Ĺ��췽��
    public SnakeView(int maxX, int maxY){
        this(maxX, maxY, 0, 0);
    }
    // �������Ĺ��췽��
    public SnakeView(int maxX, int maxY, int startX, int startY){
        this.canvasWidth = maxX * nodeWidth;
        this.canvasHeight = maxY * nodeHeight;
        this.startX = startX;
        this.startY = startY;
        init();
    }
    
    // ��ʼ����ͼ
    private void init(){
        
        this.setName("̰����");
        this.setLocation(startX, startY);
        Container cp = this.getContentPane();

        // ���������ķ�����ʾ
        labelScore = new JLabel("Score:");
        cp.add(labelScore, BorderLayout.NORTH);

        // �����м����Ϸ��ʾ����
        paintCanvas = new Canvas();
        paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
        cp.add(paintCanvas, BorderLayout.CENTER);

        // �������µİ�����
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JLabel labelHelp;
        labelHelp = new JLabel("PageUp, PageDown for speed", 
        		JLabel.CENTER);
        panel1.add(labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel("ENTER or R or S for start", 
        		JLabel.CENTER);
        panel1.add(labelHelp, BorderLayout.CENTER);
        panelBottom.add(panel1, BorderLayout.NORTH);
        
        labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
        panelBottom.add(labelHelp);
        labelHelp = new JLabel("q for stop", JLabel.CENTER);
        panelBottom.add(labelHelp);
        
        cp.add(panelBottom, BorderLayout.SOUTH);

        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    // ʵ��Observer�ӿڶ����update����
    public void update(Observable o, Object arg){
        
    	// ��ȡ����ص�ģ��
        SnakeModel model = (SnakeModel)o;
        // ��û���
        Graphics g = paintCanvas.getGraphics();

        // ������
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // ����
        g.setColor(Color.BLACK);
        LinkedList na = model.getNodeArray();
        Iterator it = na.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        // ��ʳ��
        g.setColor(Color.RED);
        Node n = model.getFood();
        drawNode(g, n);
        // ���·���
        this.updateScore(model.getScore());
    }
    
    // ������
    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth, n.y * nodeHeight, 
        		nodeWidth - 1, nodeHeight - 1);
    }

    public void updateScore(int score) {
        String s = "Score: " + score;
        labelScore.setText(s);
    }

}
