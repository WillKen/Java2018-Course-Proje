import java.net.*;
import java.io.*;
 
public class Client{
	private String info;
	private String name;
	private int score;
	private int HP;
	private int money;
	
	public Client() {
	}
	
	public Client(String ss){
	  name = ss;
	  score=0;
	  HP = 4;
	  money=0;
      String serverName = "localhost";
      String info=ss;
      int port = 6666;
      try{
         System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
         Socket client = new Socket(serverName, port);
         System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
         //输出流
         OutputStream os=client.getOutputStream();
         PrintWriter pw=new PrintWriter(os);
         //输入流
         InputStream is=client.getInputStream();
         BufferedReader br=new BufferedReader(new InputStreamReader(is));
         //利用流按照一定的操作，对socket进行读写操作
         pw.write(info);
         pw.flush();
         client.shutdownOutput();
         //接收服务器的相应
         String reply=null;
         if(!((reply=br.readLine())==null)){
             System.out.println("接收服务器的信息："+reply);
             info=reply;
             int pos=info.indexOf(',');
             if(pos!=-1) {
            	 String[] strarray=info.split(",");
                 name=strarray[0];
                 score=Integer.valueOf(strarray[1]).intValue();
                 HP=Integer.valueOf(strarray[2]).intValue();
                 money=Integer.valueOf(strarray[3]).intValue();
             }   
         }
         //关闭资源
         br.close();
         is.close();
         pw.close();
         os.close();
         client.close();
      }catch(IOException e){
         e.printStackTrace();
      }
	}
	
	public String getInfo() {
		return info;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHP() {
		return HP;
	}
	
	public int getMoney() {
		return money;
	}
}
