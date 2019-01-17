import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.applet.AudioClip; 

public class Game extends JFrame {
	private static player Player = player.getInstance();
	private static final long serialVersionUID = 1L;
    private static Background backgroundpanel;
    private int position_x;
    private int position_y;
    private static int basicnum = 10;
    private boolean is_pass = false;
    private int target = 5;
    private static JLabel ammoLabel;
    private static JLabel scoreLabel;
    private static JLabel targetLabel;
    private static JLabel HPLabel;
    private static JLabel MoneyLabel;
    private JLabel mouseLabel;
    private int sleepTime=2000;

    //地鼠消失的间隔
    private int show_time = 1000;
    //剩余地鼠的个数（待定）
    private static int mouseNum = 10;
    //三个不同的难易程度的按钮
    private static JButton button;
    private static JButton shop;
    private static JButton nextLevel;
    private static JButton store;
    private static mole[] mouse;
    //生成普通地鼠还是加分地鼠
    private int bonus;
	private static Game instance = null;
	public static synchronized Game getInstance(){
		if(instance==null)
			instance = new Game();
		return instance;
	}
	
    public Game(){
    	backgroundpanel= new Background();
        backgroundpanel.setImage(new ImageIcon(getClass().getResource("logo.jpg")).getImage());// 设置背景图片
        getContentPane().add(backgroundpanel, BorderLayout.CENTER);
        setBounds(100, 100, 1600, 900);
        addMouseListener(new FrameMouseListener());
        button=new JButton("开始游戏");
        button.setFont(new Font("宋体", Font.PLAIN, 32));
        button.setForeground(Color.blue);
        button.setName("start");
        button.setVisible(true);
        button.setSize(200,60);
        button.setLocation(690, 600);
        button.addMouseListener(new MenuMouseListener1());
        backgroundpanel.add(button);               
        mouse = new mole[8];
    }
    
    public static void main(String[] args) {
    	Frame f = new Frame("请输入用户名");
        TextField tf = new TextField(20);
        Button bu = new Button("OK");
        f.setBounds(400, 200, 400, 100);
        f.setLayout(new FlowLayout());
        bu = new Button("OK");
        f.add(tf);
        f.add(bu);
        bu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = tf.getText().toString();
				Client t = new Client(s);
				Player.name = t.getName();
				System.out.println(Player.name);
				Player.setscore(0);
				Player.sethp(t.getHP());
				Player.setmoney(t.getMoney());
				if(Player.get_hp()==0)
				{
					Player.setmoney(0);
					Player.sethp(4);
				}
		    	Game game = Game.getInstance();
		        game.setVisible(true);
		        f.setVisible(false);
			}
        });
        f.setVisible(true);
    }

    public void restart(){
    	scoreLabel.setVisible(false);
    	basicnum+=5;
    	target+=3;
    	mouseNum = basicnum;
    	show_time-=100;
    	Player.addscore(-1*Player.get_score());
    	shop.setVisible(false);
		GameThread nextgame=new GameThread();
		nextgame.start();
    }
    
    private class MenuMouseListener1 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			show_time=1500;
    			GameThread gamethread=new GameThread();
	    		gamethread.start();
    		}
    	}
    }
    private class MenuMouseListener2 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			ShopThread shopthread = new ShopThread();
    			shopthread.run();
    		}
    	}
    }
 
    
    private void gameOver() {
    	targetLabel.setVisible(false);
    	HPLabel.setVisible(false);
    	MoneyLabel.setVisible(false);
    	for(int i = 0;i<8;i++){
    		if(mouse[i] != null)
    			mouse[i].setVisible(false);
    	}
    	
    	backgroundpanel.setImage(new ImageIcon(getClass().getResource("timg.jpg")).getImage());
        scoreLabel.setForeground(Color.blue);
    	if(Player.get_score()>=target){
    		this.is_pass = true;
	    	backgroundpanel.repaint();
	    	scoreLabel.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 88));
	    	scoreLabel.setText("Congratulations!");
	    	scoreLabel.setBounds(250, 200, 1000, 500);
	    	ammoLabel.setVisible(false);
	    	mouseLabel.setVisible(false);
	    	
	    	shop = new JButton("商店");
	    	shop.setFont(new Font("宋体", Font.PLAIN, 32));
	    	shop.setForeground(Color.blue);
	    	shop.setName("start");
	    	shop.setVisible(true);
	    	shop.setSize(200,60);
	    	shop.setLocation(690, 620);
	    	shop.addMouseListener(new MenuMouseListener2());
	        
	    	backgroundpanel.add(shop);
    	}
    	else{
    		this.is_pass = false;
	    	backgroundpanel.repaint();
	    	scoreLabel.setFont(new Font("宋体", Font.PLAIN, 88));
	    	scoreLabel.setText("你的得分："+Player.get_score());
	    	scoreLabel.setBounds(250, 200, 1000, 500);
	    	ammoLabel.setVisible(false);
	    	mouseLabel.setVisible(false);
    	}
    }
    
   public boolean getPass()
   {
	   return this.is_pass;
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
    	Player.addscore(s);
    	Player.addmoney(10*s);
	    scoreLabel.setText("当前得分："+Player.get_score());
	    MoneyLabel.setText("$ "+Player.get_money());
    }
    
    public static void addHp(int s){
    	Player.addhp(s);
	    HPLabel.setText("HP "+Player.get_hp());
    }
     private class GameThread extends Thread{
    	public void run(){
    		button.setVisible(false);
            backgroundpanel.setImage(new ImageIcon(getClass().getResource("bg.jpg")).getImage());
            backgroundpanel.repaint();
            scoreLabel = new JLabel();// 显示分数的标签组件
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scoreLabel.setForeground(Color.white);
            scoreLabel.setText("当前得分：");
            scoreLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            scoreLabel.setBounds(20, 15, 240, 24);
            backgroundpanel.add(scoreLabel);
            
            targetLabel = new JLabel();// 显示目标的标签组件
            targetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            targetLabel.setForeground(Color.white);
            targetLabel.setText("本关目标："+target);
            targetLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            targetLabel.setBounds(20, 15, 400, 24);
            backgroundpanel.add(targetLabel);
            
            HPLabel = new JLabel();// 显示目标的标签组件
            HPLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            HPLabel.setForeground(Color.white);
            HPLabel.setText("HP "+Player.get_hp());
            HPLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            HPLabel.setBounds(20, 15, 500, 24);
            backgroundpanel.add(HPLabel);
            
            MoneyLabel = new JLabel();// 显示目标的标签组件
            MoneyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            MoneyLabel.setForeground(Color.white);
            MoneyLabel.setText("$ "+Player.get_money());
            MoneyLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            MoneyLabel.setBounds(30, 15, 600, 24);
            backgroundpanel.add(MoneyLabel);
            
            ammoLabel = new JLabel();// 显示自动数量的标签组件
            ammoLabel.setForeground(Color.white);
            ammoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            ammoLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            ammoLabel.setBounds(400, 15, 240, 24);
            backgroundpanel.add(ammoLabel);
            mouseLabel=new JLabel();
            mouseLabel = new JLabel();// 显示分数的标签组件
            mouseLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mouseLabel.setForeground(Color.white);
            mouseLabel.setText("剩余地鼠数："+mouseNum);
            mouseLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            mouseLabel.setBounds(780, 15, 240, 24);
            backgroundpanel.add(mouseLabel);
            try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		gaming();
            gameOver();
    }
}
    
    private void gaming(){
        while(mouseNum>0&&Player.get_hp()!=0){
        	for(int i=0;i<8;i++){
        		if(mouse[i]==null){
        			if((int)(Math.random()*10)>3&&(int)(Math.random()*10) < 9){
        				bonus = 1;
        			}
        			else if((Math.random()*10)<3){
        				System.out.println("这是加分地鼠");
        				bonus = 2;
        			}
        			else{
        				bonus = -1;
        			}
        			if(bonus!=-1)
        				mouseNum--;
	        		mouseLabel.setText("剩余地鼠数："+mouseNum);
	        		System.out.println(i);
	        		int x = (int)(Math.random()*3+1)*400;
	        		int y = (int)(Math.random()*3+1)*200;
	        		mouse[i]=new mole(x,y,show_time,bonus,i);
	        		mouse[i].setSize(200, 200);// 设置控件初始大小，即地鼠图标大小
	        		backgroundpanel.add(mouse[i]);
	        		break;
        		}
        	}
        	try {
        		sleep(1000+(int)Math.random()*1000);
        	} 
        	catch (InterruptedException e) {
        		System.out.println("这里有错");
        		e.printStackTrace();
        	}
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
            		flag=(i>=mouse[a].getX()&&i<mouse[a].getX()+300)&&(j>=mouse[a].getY()&&j<=mouse[a].getY()+300);
            	}
            	if(flag&&mouse[a]!=null){
            		//调用该地鼠的死亡函数
            		mouse[a].Die();
            	}
            }    
        }
    }

    //移走地鼠
    public static void removemouse(int i){
        mouse[i]=null;
    }
}
