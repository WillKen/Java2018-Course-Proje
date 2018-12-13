import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.*;
import javax.swing.*;


public class monster extends JLabel implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    //地鼠的位置
    private int position_x = 0;
    private int position_y = 0;
    
    //判断地鼠标号用的
    private int num = 0;
    
    //地鼠是否被击中
    public boolean hited=false;
    private Thread thread;// 将线程作为成员变量
    
    //容器，原来好像是判断鸟是否飞过屏幕边缘的，现在貌似没什么用
    private Container father;
    
    //地鼠持续的时间
    private double show_time = 10;
    
    //emmmm这个貌似之前是为了让鸟产生向前飞的效果，地鼠不会动貌似没什么用，不过目测可以改成动态png。。。
    private int sleepTime=5;
    
    //加速比，随分数的增加地鼠的持续时间减少
    private double acceleration = 0;
    
    //打掉地鼠的分数
    private int bonus = 1;
        
    public monster(int x,int y,int time,int score,int num) {
                super();
                // 创建地鼠图标对象，这里的图片都没有，找到图片重命名一下放在文件目录下即可
                ImageIcon icon = new ImageIcon(getClass().getResource("bird1.gif"));
                setIcon(icon);// 设置控件图标
                // 添加控件事件监听器
                addComponentListener(new ComponentAction());
                this.show_time=time;
                this.position_x=x;
                this.position_y=y;
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
    @Override
    public void run() {
        try {
        	//随着加速比的增大，i会越来越快达到show_time
	        for (int i = 0; i <show_time; i++) {
	        		//这里的x,y都不会变的，所以地鼠位置不动，这段等能跑了可以改
	                  setLocation(position_x, position_y);
	                  thread.sleep(sleepTime);
	        }
	        System.out.println("显示结束"+this.num);
	        Game.removemouse(num);
	        destory();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
    }
    //地鼠被打中后
    public void Die(){
        hited=true;
        ImageIcon icon = new ImageIcon(getClass().getResource("bird1die.png"));
        setIcon(icon);// 设置控件图标
        
        Game.addScore(bonus);
        //这里的+0.1只是为了测试用
        acceleration+=0.1;
        DieThread diethread=new DieThread();
        diethread.start();
    }
    public class DieThread extends Thread{

        public DieThread(){
   
        }
        public void run(){
        try {
            //thread.stop();

                float time=0;

                ImageIcon icon = new ImageIcon(getClass().getResource("bird1die.png"));
                setIcon(icon);// 设置控件图标
                for (int i = 0; i < 100; i++) {
                    setLocation(position_x, position_y--);// 向上移动组件
                    thread.sleep(sleepTime);// 休眠片刻
                    time+=sleepTime;
                }
                sleep(500);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                Game.removemouse(num);
                destory();// 移动完毕，销毁本组件
        }
    }
    
}
