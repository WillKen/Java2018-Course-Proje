import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.*;

public class mole extends JLabel implements Runnable {
	protected static final long serialVersionUID = 1L;
	//地鼠的位置
    public int position_x = 0;
    public int position_y = 0;
    //判断地鼠标号用的
    public int num = 0;
    //地鼠是否被击中
    public boolean hit = false;
    protected Thread thread;// 将线程作为成员变量

    protected Container father;
    //地鼠持续的时间
    protected int show_time = 1000;

    protected int sleepTime=5;

    protected int bonus = 1;
    
    public mole(int x,int y,int time,int score,int num) {
    	super();
    	// 创建地鼠图标对象
    	ImageIcon icon1 = new ImageIcon(getClass().getResource("mole.png"));
    	ImageIcon icon2 = new ImageIcon(getClass().getResource("monster.png"));
    	ImageIcon icon3 = new ImageIcon(getClass().getResource("boom.jpg"));
    	if(score==1)
    		setIcon(icon1);// 设置控件图标
    	else if(score==2)
    		setIcon(icon2);
    	else if(score == -1)
    		setIcon(icon3);
    	// 添加控件事件监听器
    	addComponentListener(new ComponentAction());
    	this.show_time = time;
    	this.position_x = x;
    	this.position_y = y;
    	this.num = num;
    	this.bonus = score;
    	thread = new Thread(this);// 创建线程对象
    }
    
    //销毁地鼠图片
    protected void destory() {
    	father = getParent();
    	if (father == null)
    		return;
    	father.remove(this);
        father.repaint();
        father = null; 
    }
    
    
    protected class ComponentAction extends ComponentAdapter {
    	public void componentResized(final ComponentEvent e) {
    		thread.start();// 线程启动
    	}
    }

    public void run() {
    	try {
    		setLocation(position_x, position_y);
    		Thread.sleep(show_time);
	        System.out.println("显示结束"+this.num);
	        Game.removemouse(num);
	        destory();
    	}
    	catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    public void Die(){
    	hit=true;
    	if(bonus == -1)
    		Game.addHp(-1);
        Game.addScore(bonus);
        DieThread diethread=new DieThread(bonus);
        diethread.start();
    }
    
    public class DieThread extends Thread{
    	private int bonus = 1;
    	public DieThread(int score){
    		bonus = score;
    	}
    	
        public void run(){
        	try {
        		thread.stop();
        		float time=0;
        		ImageIcon icon = new ImageIcon(getClass().getResource("die.png"));
        		if(bonus!=-1)
        		{
        			icon = new ImageIcon(getClass().getResource("die.png"));
        		}
        		else
        		{
        			icon = new ImageIcon(getClass().getResource("blom.jpg"));
        		}
                setIcon(icon);// 设置控件图标
                for (int i = 0; i < 100; i++) {
                	if(bonus!=-1)
                		setLocation(position_x, position_y--);// 向上移动组件
                	else
                		setLocation(position_x, position_y);// 向上移动组件
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