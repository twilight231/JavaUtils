package com.zstax.util.常用类.graphic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Label;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * ȥhttp://java.sun.com/products/java-media/3D/download.html����Java 3D��API��Ȼ��װ��
 * 
 * ��������ǳ����Ľ��ܶ��Java 3D�����ӡ�
 * 
 */
public class Graphic3D {
	
	/**
	 * ����������ӣ���Ҫ����Java 3D�����5���������衣
	 * ���ӽ���ʾ��һ������������壬�۲���ֱ��ע�������еĺ�ɫ�棬
	 * ʵ�ʿ�������һ����ɫ�����ϵĺ�ɫ���顣
	 */
	static class Basic3D{
		public Basic3D(){ 
			// ��1������һ������������ĳ�������������(Virtual Universe)��
			SimpleUniverse universe = new SimpleUniverse();
			// ��2������һ����������һ����������ݽṹ��
			BranchGroup group = new BranchGroup();
			// ��3��������������塣
			group.addChild(new ColorCube(0.3));
			// ��4�����ù۲���(Viewer)ʹ֮������塣
			universe.getViewingPlatform().setNominalViewingTransform();
			// ��5������������������档
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * ��������ӵ�ƹ⡣�ƹ��䵽�����ϲ������������԰���������3D�ռ��й۲�ͼ�Ρ�
	 * �������˵��������ʾһ���������������
	 */
	static class LightBall3D {
		public LightBall3D() {
			//	��������
			SimpleUniverse universe = new SimpleUniverse();
			// ������������Ľṹ
			BranchGroup group = new BranchGroup();
			// ����һ�����壨Ĭ���ǰ�ɫ�ģ������뵽�����顣0.5��ʾ��İ뾶
			Sphere sphere = new Sphere(0.5f);
			group.addChild(sphere);
			
			// ����һ����ԭ������1000�׵ĺ�ɫ��Դ��Color3f�Ĳ����ֱ�Ϊ�졢�̡���
			Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			// ����������4.0���ң�-7.0���£�-12.0����
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			//�����Դ������ָ����������ľ���ͷ���
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			
			group.addChild(light1);

			// ���ù۲��ߣ�ע������
			universe.getViewingPlatform().setNominalViewingTransform();

			// ��������鵽������
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * �����������Ӷ��������м�����һ����ͬ�ĵط��������塣
	 * ��Java 3D�У�λ����x,y,z����������X������ֵ�����ҷ���������Y�����ϣ�Z������Ļ���⡣
	 * 
	 * ���������ÿ������������ʾ��ͬ����
	 */
	static class TransformPositon {
		public TransformPositon() {
			// ��������
			SimpleUniverse universe = new SimpleUniverse();
			// ������������Ľṹ
			BranchGroup group = new BranchGroup();

			// X�����������
			for (float x = -1.0f; x <= 1.0f; x = x + 0.1f)	{
				// �����뾶Ϊ0.05�İ�ɫ��
				Sphere sphere = new Sphere(0.05f);
				
				// ����һ���任��
				TransformGroup tg = new TransformGroup();
				// ����һ���任
				Transform3D transform = new Transform3D();
				// Ϊ��ָ��λ��
				Vector3f vector	= new Vector3f(x, 0.0f, 0.0f);
				// �������ƶ���ָ��λ��
				transform.setTranslation(vector);
				// ���任��ӵ��任����
				tg.setTransform(transform);
				// ������뵽�任����
				tg.addChild(sphere);
				// ���任����뵽��������Ľṹ��
				group.addChild(tg);
			}

			// Y����׶�����
			for (float y = -1.0f; y <= 1.0f; y = y + 0.1f)	{
				// ����һ��׶�壬����뾶Ϊ0.05���߶�Ϊ0.1
				Cone cone = new Cone(0.05f, 0.1f);
				// �����任��ͱ任
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				// Ϊ׶��ָ��λ��
				Vector3f vector = new Vector3f(0.0f, y, 0.0f);
				// ����׶���ƶ���ָ��λ��
				transform.setTranslation(vector);
				// ���任����ӵ��任����
				tg.setTransform(transform);
				tg.addChild(cone);

				group.addChild(tg);
			}
			
			// Z�����������
			for (float z = -1.0f; z <= 1.0f; z = z + 0.1f)	{
				// ����һ�����壬����뾶Ϊ0.05���߶�Ϊ0.1
				Cylinder cylinder = new Cylinder(0.05f, 0.1f);
				// ��������ʾ��ָ��λ����
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				Vector3f vector = new Vector3f(0.0f, 0.0f, z);
				transform.setTranslation(vector);
				tg.setTransform(transform);
				tg.addChild(cylinder);
				group.addChild(tg);
			}
			
			// ������ɫ��Դ
			Color3f light1Color = new Color3f(0.1f, 1.4f, 0.1f); // green light
			BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight light1	= new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			group.addChild(light1);
			
			//���ù۲���
			universe.getViewingPlatform().setNominalViewingTransform();

			// ���������������
			universe.addBranchGraph(group);
/**
 * ����һ�����嵽�����У��㽫�ӵ�(0,0,0)��ʼ��Ȼ���ƶ����嵽����Ҫ�ĵط���
 * �ƶ����屻��Ϊ���任(transformation)��,������Ҫʹ�õ�����:TransformGroup��Transform3D��
 * Ҫ�Ȱ������Transform3D�������TransformGroup���ٰ�TransformGroup���볡���С�
 */
		}
	}
	
	/**
	 * ��������������Ĳ��ʺ�����ʹ�������ӷḻ������һ������ͼ��
	 */
	static class PictureBall {
		public PictureBall() {
			//	 ��������
			SimpleUniverse universe = new SimpleUniverse();
			// ������������Ľṹ
			BranchGroup group = new BranchGroup();

			// ������ɫ
			Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
			Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
			Color3f red = new Color3f(0.7f, 0.15f, 0.15f);

			// ����������ͼ
			
			// ����װ����(TextureLoader)������װ��һ��ͼƬ��Ϊ����ͼƬ�ĳߴ�����ǣ����ݣ�����128���ؿ�256���ظߡ�
			// ������ͼƬ��Ҳ����ָ����Ҫ���ʹ��ͼƬ�����磬RGBʹ��ͼƬ����ɫ��LUMINANCEʹͼƬ��ʾΪ�ڰס�
			TextureLoader loader = new TextureLoader("C:/temp/scenery.jpg",
					"RGB", new Container());
			Texture texture = loader.getTexture();
			texture.setBoundaryModeS(Texture.WRAP);
			texture.setBoundaryModeT(Texture.WRAP);
			texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

			// ������������ 

			//������REPLACE, BLEND �� DECAL ���� MODULATE
			TextureAttributes texAttr = new TextureAttributes();
			texAttr.setTextureMode(TextureAttributes.MODULATE);

			Appearance ap = new Appearance();
			ap.setTexture(texture);
			ap.setTextureAttributes(texAttr);

			//�������ʣ���������ֱ��ǣ����������䡢���䡢���䡢���⣩
			ap.setMaterial(new Material(red, red, black, black, 50.0f));

			// ����һ������չʾ����
			// ���ʹ��һ�������������壬����Ҫͨ�����á�ԭʼ��ǡ�����������
			int primflags = Primitive.GENERATE_NORMALS
					+ Primitive.GENERATE_TEXTURE_COORDS;
			Sphere sphere = new Sphere(0.5f, primflags, ap);
			group.addChild(sphere);

			// �����ƹ⣬�����Դ
			Color3f light1Color = new Color3f(1f, 1f, 1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			group.addChild(light1);

			// ����һ��������Դ
			AmbientLight ambientLight = new AmbientLight(new Color3f(0.5f, 0.5f,
					0.5f));
			ambientLight.setInfluencingBounds(bounds);
			group.addChild(ambientLight);

			// ע������
			universe.getViewingPlatform().setNominalViewingTransform();

			// ���������������
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * ������ӽ�չʾ3D��Swing�Ľ�ϡ�
	 * ��һ�����¶��б�ǩ(JLabel)��JFrame�д���Canvas3D
	 */
	static class Canvas3DAndSwing extends JFrame{
		public Canvas3DAndSwing(){
			// ���ò���
			this.getContentPane().setLayout(new BorderLayout());
			
			// ������������
	        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	        // �����������ô���һ��Canvas3D��3D�Ļ���
	        Canvas3D canvas = new Canvas3D(config);  
	        
	        // ������
	        this.getContentPane().add("North",new Label("����ı�ǩ"));
	        this.getContentPane().add("Center", canvas);
	        this.getContentPane().add("South",new Label("����ı�ǩ"));
	        
	        // ��3D�����л�3D����
	        BranchGroup group = new BranchGroup();
	        Sphere sphere = new Sphere(0.5f);
	        group.addChild(sphere);
	        
			// ����һ����ԭ������1000�׵ĺ�ɫ��Դ��Color3f�Ĳ����ֱ�Ϊ�졢�̡���
			Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			// ����������4.0���ң�-7.0���£�-12.0����
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			//�����Դ������ָ����������ľ���ͷ���
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			
			group.addChild(light1);
	        
	        // ����һ������3D����������
	        SimpleUniverse universe = new SimpleUniverse(canvas);
	        universe.getViewingPlatform().setNominalViewingTransform();
	        universe.addBranchGraph(group); 
	        
	        // ���ô�������
	        this.setSize(400, 400);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
		}
	} 

	public static void main(String[] args) {
//		Basic3D basic3D = new Basic3D();
//		LightBall3D lightBall3D = new LightBall3D();
//		TransformPositon transformPositon = new TransformPositon();
//		PictureBall pictureBall = new PictureBall();
		Canvas3DAndSwing canvas3DSwingDemo = new Canvas3DAndSwing();
	}
}
