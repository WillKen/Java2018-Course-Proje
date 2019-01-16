import java.io.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
public class Signin extends JFrame implements ActionListener{
	 JButton b;JTextField t;
	 public Signin(){
	  super("登录");
	  JLabel l=new JLabel("输入用户名：");
	  t=new JTextField(20);
	  b=new JButton("写入");
	  b.addActionListener(this);
	  this.add(l);
	  this.add(t);
	  this.add(b);
	  this.setLayout(new FlowLayout());
	  this.pack();
	  this.setVisible(true);
	 }
	 public void actionPerformed(ActionEvent e) {
	  if(e.getSource()==b){
	   if(t.getText().equals("")){
	    JOptionPane.showMessageDialog(null,"请输入内容~","错误",JOptionPane.ERROR_MESSAGE);
	    t.grabFocus();
	   }else{
	    write(t.getText());
	    JOptionPane.showMessageDialog(null,"写入成功","提示",JOptionPane.INFORMATION_MESSAGE);
	   }
	  }
	 }
	 public void write(String line){
	  try{
	   File f=new File("c:/文本框.txt");//向指定文本框内写入
	   FileWriter fw=new FileWriter(f);
	   fw.write(line);
	   fw.close();
	  }catch(Exception e){
	 
	  }
	 }
	 public static void main(String[] args) {
		 new Signin();
	 }
 
}
