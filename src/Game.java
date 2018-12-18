import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
    private static Background backgroundpanel;
    private int position_x;
    private int position_y;
    //显示时间的窗口
    private static JLabel ammoLabel;
    //显示当前得分的窗口
    private static JLabel scoreLabel;
    private JLabel mouseLabel;
    //生成地鼠的间隔
    private int sleepTime=2000;
    //地鼠消失的间隔
    private int show_time = 2000;
    //得分数
    private static int score=0;
    //剩余地鼠的个数（待定）
    private static int mouseNum=50;
    //三个不同的难易程度的按钮
    private static JButton button;
    private static JButton button1;
    private static JButton button2;
    private static mole[] mouse;
    //生成普通地鼠还是加分地鼠
    private int bonus;
    
    public Game(){
    	backgroundpanel= new Background();
        backgroundpanel.setImage(new ImageIcon(getClass().getResource("logo.png")).getImage());// 设置背景图片
        getContentPane().add(backgroundpanel, BorderLayout.CENTER);
        setBounds(100, 100, 1600, 900);
        //添加鼠标点击事件
        addMouseListener(new FrameMouseListener());
        button=new JButton("简单");
        button.setFont(new Font("宋体", Font.PLAIN, 32));
        button.setForeground(Color.BLUE);
        button.setName("start");
        button.setVisible(true);
        button.setSize(200,60);
        button.setLocation(690, 600);
        button.addMouseListener(new MenuMouseListener1());
        backgroundpanel.add(button);
        button1=new JButton("中等");
        button1.setFont(new Font("宋体", Font.PLAIN, 32));
        button1.setForeground(Color.BLUE);
        button1.setName("中等");
        button1.setVisible(true);
        button1.setSize(200,60);
        button1.setLocation(690, 680);
        button1.addMouseListener(new MenuMouseListener2());
        backgroundpanel.add(button1);
        button2=new JButton("困难");
        button2.setFont(new Font("宋体", Font.PLAIN, 32));
        button2.setForeground(Color.BLUE);
        button2.setName("start");
        button2.setVisible(true);
        button2.setSize(200,60);
        button2.setLocation(690, 760);
        button2.addMouseListener(new MenuMouseListener3());
        backgroundpanel.add(button2);
        mouse=new mole[8];
    }
    
    public static void main(String[] args) {
    	Game game=new Game();
        game.setVisible(true);
    }

    private class MenuMouseListener1 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		//如果是简单，每隔2.5秒生成一个地鼠
    		if(e.getButton()==e.BUTTON1){
    			show_time=2500;
    			GameThread gamethread=new GameThread();
    			gamethread.start();
    		}
    	}
    }
    
    private class MenuMouseListener2 extends MouseAdapter {
        public void mousePressed(final MouseEvent e) {
        	//为复杂，1.5秒一个地鼠
        	if(e.getButton()==e.BUTTON1/*button1指鼠标左键*/){
        		show_time=1500;
        		GameThread gamethread=new GameThread();
        		gamethread.start();
        	}
        }
    }
    
    private class MenuMouseListener3 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		//难度为复杂
    		if(e.getButton()==e.BUTTON1){
    			show_time=800;
    			GameThread gamethread=new GameThread();
    			gamethread.start();
    		}
    	}
    }
    
    private void gameOver() {
    	backgroundpanel.setImage(new ImageIcon(getClass().getResource("mole.png")).getImage());
    	//重新绘制backgroundpanel
    	backgroundpanel.repaint();
    	scoreLabel.setFont(new Font("宋体", Font.PLAIN, 48));
    	scoreLabel.setText("你的得分："+score);
    	scoreLabel.setBounds(550, 550, 500, 50);
    	ammoLabel.setVisible(false);
    	mouseLabel.setVisible(false);
    }
    //为窗体添加鼠标左击事件
    private final class FrameMouseListener extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		//传入鼠标事件e来判断是否击中地鼠
    		if(e.getButton()==e.BUTTON1){
    			//击中的话启动地鼠死亡的线程
                mousebeHited mouse_be_hited=new mousebeHited(e);
                mouse_be_hited.start();
            }
        }
    }
    //击中得分
    public static void addScore(int s){
    	score+=s;
	    scoreLabel.setText("当前得分："+score);
    }

    private  class GameThread extends Thread{
    	public void run(){
    		button.setVisible(false);
            button1.setVisible(false);
            button2.setVisible(false);
            // 更换背景图片
            backgroundpanel.setImage(new ImageIcon(getClass().getResource("background0.png")).getImage());
            //重新绘制backgroundpanel
            backgroundpanel.repaint();
            scoreLabel = new JLabel();// 显示分数的标签组件
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scoreLabel.setForeground(Color.blue);
            scoreLabel.setText("当前得分：");
            scoreLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            scoreLabel.setBounds(20, 15, 240, 24);
            backgroundpanel.add(scoreLabel);
            ammoLabel = new JLabel();// 显示自动数量的标签组件
            ammoLabel.setForeground(Color.blue);
            ammoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            ammoLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            ammoLabel.setBounds(400, 15, 240, 24);
            backgroundpanel.add(ammoLabel);
            mouseLabel=new JLabel();
            mouseLabel = new JLabel();// 显示分数的标签组件
            mouseLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mouseLabel.setForeground(Color.blue);
            mouseLabel.setText("剩余地鼠数："+mouseNum);
            mouseLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            mouseLabel.setBounds(780, 15, 240, 24);
            backgroundpanel.add(mouseLabel);
            while(mouseNum>0){
            	for(int i=0;i<8;i++){
            		if(mouse[i]==null){
            			//随机产生加分地鼠
            			if((int)(Math.random()*10)>1){
            				bonus=1;
            			}
            			else{
            				bonus=2;
            			}
            			mouseNum--;
            			mouseLabel.setText("剩余地鼠数："+mouseNum);
            			//让地鼠产生在随机位置，这里位置有问题
            			System.out.println(i);
            			mouse[i]=new mole((int)(Math.random()*3+1)*200,(int)(Math.random()*3+1)*200,show_time,bonus,i);
            			mouse[i].setSize(200, 200);// 设置控件初始大小，即地鼠图标大小
            			//背景添加地鼠
            			System.out.println(">>>"+i);
            			backgroundpanel.add(mouse[i]);
            			System.out.println("???"+i);
            			break;
            		}
            	}
            	try {
            		//这一块没太看懂先放着
            		sleep(sleepTime+(int)Math.random()*1000);
            	} 
            	catch (InterruptedException e) {
            		System.out.println("这里有错");
            		e.printStackTrace();
            	}
            } 
            gameOver();
    	}
    }
    
    //该线程用于监听是否有地鼠被打中
    private class mousebeHited extends Thread{
    	//大写的Mouse指鼠标，小写的指地鼠
        private final MouseEvent Mouse;
        private int i;
        private int j;
        //构造函数
        public mousebeHited(final MouseEvent e){
            this.Mouse=e;
            i=e.getX();
            j=e.getY();
        }
        
        public void run(){
            Boolean flag = false;
            
            for(int a = 0;a<8;a++){
                i=Mouse.getX();
                j=Mouse.getY();
            	if(mouse[a]!=null&&!mouse[a].hit){
            		//如果击中地鼠的范围
            		flag=(i>=mouse[a].getX()&&i<mouse[a].getX()+400)&&(j>=mouse[a].getY()&&j<=mouse[a].getY()+400);
            	}
            	if(flag&&mouse[a]!=null){
            		//调用该地鼠的死亡函数
            		mouse[a].Die();
            	}
            }    
        }
    }
    //加音乐的，可以先删了
    /*public class Music extends JApplet { 
        private static final long serialVersionUID = 1L;
        private URL url;
        public Music(String str){
           try {
                url = new URL("file:"+str);
                AudioClip aau;
                aau = Applet.newAudioClip(url);
                aau.play();
            }catch (Exception e){ 
                e.printStackTrace();
            }
        }
    }*/
    //移走地鼠，这样可以用8个数组形成很多地鼠
    public static void removemouse(int i){
        mouse[i]=null;
    }
}
