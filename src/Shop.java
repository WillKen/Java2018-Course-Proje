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
	private boolean can_buy_w1 = true;
	private boolean can_buy_w2 = true;
	private player Player = player.getInstance();
	private static final long serialVersionUID = 1L;
    private static Background backgroundpanel;
    private static JButton buy_hp;
    private static JButton buy_weapon_1;
    private static JButton buy_weapon_2;
    private static JLabel message;
    public Shop()
    {
    	message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.blue);
        message.setText(" ");
        message.setFont(new Font("宋体", Font.PLAIN, 24));
        message.setBounds(20, 15, 1000, 24);

    	backgroundpanel= new Background();
        backgroundpanel.setImage(new ImageIcon(getClass().getResource("logo.png")).getImage());// 设置背景图片
        backgroundpanel.add(message);
        getContentPane().add(backgroundpanel, BorderLayout.CENTER);
        setBounds(100, 100, 1600, 900);
    	buy_hp = new JButton("购买HP药剂：100$");
    	buy_hp.setFont(new Font("宋体", Font.PLAIN, 32));
    	buy_hp.setForeground(Color.BLUE);
    	buy_hp.setName("start");
    	buy_hp.setVisible(true);
    	buy_hp.setSize(500,60);
    	buy_hp.setLocation(690, 600);
    	buy_hp.addMouseListener(new MenuMouseListener_HP());
        backgroundpanel.add(buy_hp);
        
        
        buy_weapon_1 = new JButton("购买屠龙宝刀：1000$");
        buy_weapon_1.setFont(new Font("宋体", Font.PLAIN, 32));
        buy_weapon_1.setForeground(Color.BLUE);
        buy_weapon_1.setName("start");
        buy_weapon_1.setVisible(true);
        buy_weapon_1.setSize(500,60);
        buy_weapon_1.setLocation(690, 680);
        buy_weapon_1.addMouseListener(new MenuMouseListener_W1());
        backgroundpanel.add(buy_weapon_1);
        
        buy_weapon_2 = new JButton("购买尚方宝剑：1000$");
        buy_weapon_2.setFont(new Font("宋体", Font.PLAIN, 32));
        buy_weapon_2.setForeground(Color.BLUE);
        buy_weapon_2.setName("start");
        buy_weapon_2.setVisible(true);
        buy_weapon_2.setSize(500,60);
        buy_weapon_2.setLocation(690, 760);
        buy_weapon_2.addMouseListener(new MenuMouseListener_W2());
        backgroundpanel.add(buy_weapon_2);    
    }
    private class MenuMouseListener_HP extends MouseAdapter{
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
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
    private class MenuMouseListener_W1 extends MouseAdapter{
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			if(Player.get_money()>=1000&&can_buy_w1)
    			{
    				Player.addatk(1);
    				Player.addmoney(-1000);
    				can_buy_w1 = false;
    				buy_weapon_1.setText("已售罄"); 	               
    				message.setText("你购买了屠龙宝刀，ATK+1，现在你还剩："+Player.get_money()+"$.");
    			}else if(Player.get_money()>=1000&&!can_buy_w2)
    			{
    				message.setText("商品已售罄，无法再次购买！");
    			}
    			else 
    			{
    				message.setText("你没有足够的钱！");
    			}
    		}
    	}	
    }
    private class MenuMouseListener_W2 extends MouseAdapter{
    	public void mousePressed(final MouseEvent e) {
    		if(e.getButton()==e.BUTTON1){
    			if(Player.get_money()>=1000&&can_buy_w2)
    			{
    				Player.addatk(2);
    				Player.addmoney(-1000);
    				can_buy_w2 = false;
    				buy_weapon_2.setText("已售罄"); 	               
    				message.setText("你购买了尚方宝剑，ATK+2，现在你还剩："+Player.get_money()+"$.");
    			}else if(Player.get_money()>=1000&&!can_buy_w2)
    			{
    				message.setText("商品已售罄，无法再次购买！");
    			}
    			else 
    			{
    				message.setText("你没有足够的钱！");
    			}
    		}
    	}	
    }
    public static void main(String[] args) {
    	Shop game = new Shop();
        game.setVisible(true);
    }
}
