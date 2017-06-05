package com.zstax.util.常用类.mutimedia;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GraphicsConfiguration;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * ��ά���򶯻�
 * 
 * ��Ҫ�����������Ҫ�ڶ�����ÿһ֡���ƶ����塣�����ʹ�ü�ʱ������ÿ�ξ���һ���̵ܶ�ʱ���ƶ����塣
 * ��Ҳ������������ʽ�޸����壬���������������С��ʹ����������ÿ����ײʱ��ѹ�⡣ Ϊ�˺��û�����������Դ�������ť��������������
 * ��һ����Ҫע����������ͨ�����ù������Ը���Java 3D��Ҫ�ƶ����塣����һ�����屻�����㽫�������ƶ����ǡ�
 * ����������������Щ�����������ťʹ��������С��ʼ����������Ȼ������԰���a��s�����ƶ�С��
 */
public class BouncingBall extends JFrame implements ActionListener, KeyListener {

	// ������ť
	private Button go = new Button("�� ʼ");
	// ��ά�任�ͱ任��
	private TransformGroup objTrans;
	private Transform3D trans = new Transform3D();

	// ���Y����λ�ã�Ϊ0��ʾ�����ģ�Ϊ������ʾ�����棬������ʾ������
	private float hPosition = 0.0f;
	// ��ĺ�����λ�ã�λ0��ʾ���������ģ�Ϊ������ʾ����ߣ�������ʾ���ұ�
	private float xPosition = 0.0f;
	// �������˶��ķ���1��ʾ�����˶���-1��ʾ�����˶�
	private float sign = 1.0f;
	// ��ʱ�������ϵĽ��������ƶ�
	private Timer timer;

	/**
	 * ���캯��
	 */
	public BouncingBall() {
		this.getContentPane().setLayout(new BorderLayout());
		// ������������
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		// �����������ô���һ��Canvas3D��3D�Ļ���
		Canvas3D c = new Canvas3D(config);

		this.getContentPane().add("Center", c);
		c.addKeyListener(this);

		// ����һ����ʱ��
		timer = new Timer(100, this);

		Panel p = new Panel();
		p.add(go);
		this.getContentPane().add("North", p);

		go.addActionListener(this);
		go.addKeyListener(this);

		// ����һ���򵥳������ӵ���������
		BranchGroup scene = createSceneGraph();
		SimpleUniverse u = new SimpleUniverse(c);
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);
	}
	/**
	 * ������ά��
	 */
	public BranchGroup createSceneGraph() {

		// ����������Ľṹ
		BranchGroup objRoot = new BranchGroup();

		// ������ά�򣬲�������õ�ԭ��
		Sphere sphere = new Sphere(0.25f);
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos1 = new Transform3D();
		pos1.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		objTrans.setTransform(pos1);
		objTrans.addChild(sphere);
		objRoot.addChild(objTrans);

		// ���������Դ
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				1000.0);
		Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		// ���û�����
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		return objRoot;
	}
	/**
	 * �������ʱ��
	 */
	public void keyPressed(KeyEvent e) {
		// ����������ʱ����
		if (e.getKeyChar() == 's') {
			xPosition = xPosition + 0.1f;
		}
		if (e.getKeyChar() == 'a') {
			xPosition = xPosition - 0.1f;
		}
	}

	public void keyTyped(KeyEvent e) {
		// do nothing
	}
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	/**
	 * ʱ�䴦��������ť�¼��Ͷ�ʱ�����¼�
	 */
	public void actionPerformed(ActionEvent e) {

		// ��ť����ʱ������ʱ��
		if (e.getSource() == go) {
			if (!timer.isRunning()) {
				timer.start();
			}
		} else {
			// ��ʱ���¼�
			hPosition += 0.1 * sign;
			// ���׻��ߵ���ʱת���˶�����
			if (Math.abs(hPosition * 2) >= 1) {
				sign = -1.0f * sign;
			}

			// ������촥��������߸��뿪����ʱ������ĸ߶ȱ�С��ʹ���������������ˡ�
			// ���������������С��ʾ��
			if (hPosition < -0.4f) {
				trans.setScale(new Vector3d(1.0, 0.8, 1.0));
			} else {
				trans.setScale(new Vector3d(1.0, 1.0, 1.0));
			}
			// �����ڱ任��ָ����λ��
			trans.setTranslation(new Vector3f(xPosition, hPosition, 0.0f));
			objTrans.setTransform(trans);
		}
	}

	public static void main(String[] args) {

		System.out.println("Program Started");
		BouncingBall bb = new BouncingBall();
		bb.addKeyListener(bb);
		bb.setSize(500, 400);
		bb.setTitle("��ά���򶯻�");
		bb.setVisible(true);
		bb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
