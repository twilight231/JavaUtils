package com.zstax.util.src.demo.others;

public class AppletDemo {
	/**
	 * Called by the browser or applet viewer to inform this applet that it has
	 * been loaded into the system. It is always called before the first time
	 * that the <code>start</code> method is called.
	 * <p>
	 * A subclass of <code>Applet</code> should override this method if it has
	 * initialization to perform. For example, an applet with threads would use
	 * the <code>init</code> method to create the threads and the
	 * <code>destroy</code> method to kill them.
	 * <p>
	 * The implementation of this method provided by the <code>Applet</code>
	 * class does nothing.
	 * ���䣺JVM����applet�࣬���������applet�����������init�������г�ʼ�������Applet����
	 * ����г�ʼ������Ӧ���Ǵ˷�����ͨ�����÷���ʵ�ֵĹ��ܰ��������û����������װ��ͼ�����Ƶ����Դ
	 * �Լ���HTML��ҳ��<applet>����л�ȡ����
	 */
	public void init() {
	}

	/**
	 * Called by the browser or applet viewer to inform this applet that it
	 * should start its execution. It is called after the <code>init</code>
	 * method and each time the applet is revisited in a Web page.
	 * <p>
	 * A subclass of <code>Applet</code> should override this method if it has
	 * any operation that it wants to perform each time the Web page containing
	 * it is visited. For example, an applet with animation might want to use
	 * the <code>start</code> method to resume animation, and the
	 * <code>stop</code> method to suspend the animation.
	 * <p>
	 * Note: some methods, such as <code>getLocationOnScreen</code>, can only
	 * provide meaningful results if the applet is showing. Because
	 * <code>isShowing</code> returns <code>false</code> when the applet's
	 * <code>start</code> is first called, methods requiring
	 * <code>isShowing</code> to return <code>true</code> should be called from
	 * a <code>ComponentListener</code>.
	 * <p>
	 * The implementation of this method provided by the <code>Applet</code>
	 * class does nothing.
	 * ���䣺init������ɺ󣬵���start����������������ҳ֮�����Ҳ���ô˷���
	 */
	public void start() {
	}

	/**
	 * Called by the browser or applet viewer to inform this applet that it
	 * should stop its execution. It is called when the Web page that contains
	 * this applet has been replaced by another page, and also just before the
	 * applet is to be destroyed.
	 * <p>
	 * A subclass of <code>Applet</code> should override this method if it has
	 * any operation that it wants to perform each time the Web page containing
	 * it is no longer visible. For example, an applet with animation might want
	 * to use the <code>start</code> method to resume animation, and the
	 * <code>stop</code> method to suspend the animation.
	 * <p>
	 * The implementation of this method provided by the <code>Applet</code>
	 * class does nothing.
	 * ���䣺�뿪ҳ��ʱ����stop����
	 */
	public void stop() {
	}

	/**
	 * Called by the browser or applet viewer to inform this applet that it is
	 * being reclaimed and that it should destroy any resources that it has
	 * allocated. The <code>stop</code> method will always be called before
	 * <code>destroy</code>.
	 * <p>
	 * A subclass of <code>Applet</code> should override this method if it has
	 * any operation that it wants to perform before it is destroyed. For
	 * example, an applet with threads would use the <code>init</code> method to
	 * create the threads and the <code>destroy</code> method to kill them.
	 * <p>
	 * The implementation of this method provided by the <code>Applet</code>
	 * class does nothing.
	 */
	public void destroy() {
	}

}
