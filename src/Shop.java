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

public class Shop extends JFrame{
	private player Player = player.getInstance();
	private static final long serialVersionUID = 1L;
    private static Background backgroundpanel;
    private static JLabel message;
    private static JButton buy_hp;
    private static JButton gameOver;
    private static JButton nextLevel;
	private static Shop instance = null;
	private Game game = Game.getInstance(); 
	public static synchronized Shop getInstance()
	{
		if(instance==null)
			instance = new Shop();
		return instance;
	}
    public Shop()
    {
    	message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.blue);
        message.setText(" ");
        message.setFont(new Font("宋体", Font.PLAIN, 24));
        message.setBounds(20, 15, 1000, 24);

    	backgroundpanel= new Background();
        backgroundpanel.setImage(new ImageIcon(getClass().getResource("timg (1).jpg")).getImage());// 设置背景图片
        backgroundpanel.add(message);
        getContentPane().add(backgroundpanel, BorderLayout.CENTER);
        setBounds(100, 100, 1600, 900);
    	buy_hp = new JButton("购买HP药剂：100$");
    	buy_hp.setFont(new Font("宋体", Font.PLAIN, 32));
    	buy_hp.setForeground(Color.BLUE);
    	buy_hp.setName("start");
    	buy_hp.setVisible(true);
    	buy_hp.setSize(500,60);
    	buy_hp.setLocation(520, 520);
    	buy_hp.addMouseListener(new MenuMouseListener_HP());
        backgroundpanel.add(buy_hp);
        
		nextLevel = new JButton("下一关");
		nextLevel.setFont(new Font("宋体", Font.PLAIN, 32));
		nextLevel.setForeground(Color.BLUE);
		nextLevel.setName("start");
		nextLevel.setSize(200,60);
		nextLevel.setLocation(700, 600);
		nextLevel.addMouseListener(new MenuMouseListener3());
		backgroundpanel.add(nextLevel); 
		
		gameOver = new JButton("结束游戏");
		gameOver.setFont(new Font("宋体", Font.PLAIN, 32));
		gameOver.setForeground(Color.BLUE);
		gameOver.setName("start");
		gameOver.setSize(200,60);
		gameOver.setLocation(700, 700);
		gameOver.addMouseListener(new MenuMouseListener4());
		backgroundpanel.add(gameOver); 
    }
    
	private class MenuMouseListener3 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			setVisible(false);
    			message.setVisible(false);
    			game.restart();
    		}
    	}
    }
	
	private class MenuMouseListener4 extends MouseAdapter {
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			String msg = Player.name+","+Player.get_score()+","+Player.get_hp()+","+Player.get_money();
    			GreetingClient write = new GreetingClient(msg);
    			backgroundpanel.setImage(new ImageIcon(getClass().getResource("timg.jpg")).getImage());
    			backgroundpanel.repaint();
    			buy_hp.setVisible(false);
    			nextLevel.setVisible(false);
    			gameOver.setVisible(false);
    			message.setBounds(250, 200, 1000, 500);
    			message.setFont(new Font("宋体", Font.PLAIN, 88));
    			message.setText("你的得分：" + Player.get_score());
    		}
    	}
    }
	
    private class MenuMouseListener_HP extends MouseAdapter{
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			message.setVisible(true);
    			if(Player.get_money()>=100)
    			{
    				Player.addhp(1);
    				Player.addmoney(-100);
    				message.setText("你购买了HP药剂，HP+1，现在你还剩："+Player.get_money()+"$.");
    			}
    			else
    			{
    				message.setText("你没有足够的钱！");
    			}
    		}
    	}	
    }

}
