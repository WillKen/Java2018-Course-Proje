import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.*;

public class mole extends JLabel implements Runnable {
	private static final long serialVersionUID = 1L;
	//地鼠的位置
    private int position_x = 0;
    private int position_y = 0;
    //判断地鼠标号用的
    private int num = 0;
    //地鼠是否被击中
    public boolean hit = false;
    private Thread thread;// 将线程作为成员变量
    //容器，原来好像是判断鸟是否飞过屏幕边缘的，现在貌似没什么用
    private Container father;
    //地鼠持续的时间
    private int show_time = 1000;

    private int sleepTime=5;

    private int bonus = 1;
    
    public mole(int x,int y,int time,int score,int num) {
    	super();
    	// 创建地鼠图标对象，这里的图片都没有，找到图片重命名一下放在文件目录下即可
    	ImageIcon icon = new ImageIcon(getClass().getResource("mole.png"));
    	setIcon(icon);// 设置控件图标
    	// 添加控件事件监听器
    	addComponentListener(new ComponentAction());
    	this.show_time = time;
    	this.position_x = x;
    	this.position_y = y;
    	this.num = num;
    	thread = new Thread(this);// 创建线程对象
    }
    
    //销毁地鼠图片，这段没改，不知道有没有问题
    private void destory() {
    	father = getParent();
    	if (father == null)
    		return;
    	father.remove(this);// 从父容器中移除本逐渐
        father.repaint();
        father = null; // 通过该语句终止线程循环
    }
    
    
    private class ComponentAction extends ComponentAdapter {
    	public void componentResized(final ComponentEvent e) {
    		thread.start();// 线程启动
    	}
    }

    public void run() {
    	try {
    		setLocation(position_x, position_y);
    		Thread.sleep(show_time);
	        System.out.println("显示结束"+this.num);
	        Game.removemouse(num);                                               //*************************************************
	        destory();
    	}
    	catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    public void Die(){
    	hit=true;
        Game.addScore(bonus);
        DieThread diethread=new DieThread();
        diethread.start();
    }
    
    public class DieThread extends Thread{
    	public DieThread(){
    	}
    	
        public void run(){
        	try {
        		thread.stop();
        		float time=0;
        		ImageIcon icon = new ImageIcon(getClass().getResource("bird1die.png"));
                setIcon(icon);// 设置控件图标
                for (int i = 0; i < 100; i++) {
                	setLocation(position_x, position_y--);// 向上移动组件
                    Thread.sleep(sleepTime);// 休眠片刻
                }
        	} 
        	catch (InterruptedException e) {
        		System.out.println("Diethread这里有错");
        		e.printStackTrace();
        	}
        	Game.removemouse(num);
        	destory();// 移动完毕，销毁本组件
        }
    }
}