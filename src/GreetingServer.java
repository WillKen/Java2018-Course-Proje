
import java.net.*;
import java.io.*;
 
public class GreetingServer extends Thread
{
   private ServerSocket serverSocket;
   private static String information;
   
   public GreetingServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
//      serverSocket.setSoTimeout(10000);
   }
   
   public static void DataExchange(String info,boolean rd,boolean wt){
	   
	information="NULL";
	PrintWriter pw=null;
	File file = new File("Whac-a-Mole.csv");
	FileWriter fw = null;
	try {
		fw = new FileWriter(file, true);
	   } catch (IOException e3) {
	    // TODO Auto-generated catch block
	    e3.printStackTrace();
	   }
	if(wt==true) {
		   pw=new PrintWriter(fw);
		   pw.println(info);
		   pw.flush();
		   try {
		    fw.flush();
		   } catch (IOException e2) {
		    // TODO Auto-generated catch block
		    e2.printStackTrace();
		   }
		   pw.close();
		   try {
			   fw.close();
		   } catch (IOException e2) {
		    // TODO Auto-generated catch block
		    e2.printStackTrace();
		   }
	}
	if(rd==true) {
	       BufferedReader reader = null;
	       try {
	    	   reader = new BufferedReader(new FileReader(file));
	       } catch (FileNotFoundException e1) {
	    	   // TODO Auto-generated catch block
	    	   e1.printStackTrace();
	       }
	       String tempString = null;
	       int line = 1;
	       try {
	    	   while ((tempString = reader.readLine()) != null) {
			   		System.out.println("line " + line + ": " + tempString);
			   		String[] strarray=tempString.split(",");
			   		System.out.println(strarray[0]);
			   		System.out.println(info);
			   		if(strarray[0].equals(info)) {
			   			information=tempString;
			   		}
			   		else {
			   			System.out.println("not equal");
			   		}
			   		line++;
			   }
	       } catch (IOException e) {
	    	   // TODO Auto-generated catch block
	    	   e.printStackTrace();
	       }
	       try {
	    	   reader.close();
	       } catch (IOException e) {
	    	   // TODO Auto-generated catch block
	    	   e.printStackTrace();
	       }
	   }
   }
   
   
   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

            //获得输入流
            InputStream is=server.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //获得输出流
            OutputStream os=server.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            //读取用户输入信息
            String info=null;
            while(!((info=br.readLine())==null)){
            	int pos=info.indexOf(',');
            	if(pos==-1) {
            		DataExchange(info,true,false);
            	}
            	else{
            		DataExchange(info,false,true);
            	}
            }
            //给客户一个响应
            String reply=information;
            pw.write(reply);
            pw.flush();
            //5.关闭资源
            pw.close();
            os.close();
            br.close();
            is.close();
            server.close();
         }catch(SocketTimeoutException s)
         {
//            System.out.println("Socket timed out!");
//            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
  
   public static void main(String [] args)
   {
	  
      int port = 8080;
      try
      {
         Thread t = new GreetingServer(port);
         t.run();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}

