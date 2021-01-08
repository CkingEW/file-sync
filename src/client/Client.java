package client;

import java.util.HashMap;

import com.MyStreamSocket;

public class Client extends Thread{
	
	private static final String SERVER_IP = "192.168.1.106";
	private static final int SERVER_PORT = 10086;
	private static final int WAIT_TIME = 1000*5;//ms
	private HashMap<String, Boolean> file_map;
	MyStreamSocket mss = null;
	
	public Client(){
		file_map = new HashMap<String, Boolean>();
	}
	
	public void sync(String ip, int port, String path) {
		//System.out.println((file_map == null));
		try{
			mss = new MyStreamSocket(ip, port, WAIT_TIME);
			System.out.println("当前客户端IP: "+mss.getLocalIP());
		}catch (Exception e) {
			System.out.println("连接服务器失败");
		}
		if(mss != null) {
			try {
				byte[] data  = new byte[1024*1024];
				int len = 0, file_number;
				String file_name;
				
				mss.sendString("nice");	//对服务器发送同步请求
													
				file_number = mss.recieveNumber();//接收同步文件的数量
				System.out.println("共需同步"+file_number+"个文件");
				
				for (int i=1;i<=file_number;i++) {
					
					file_name = mss.recieveString();	//获得文件名
					System.out.println("\n正在同步第"+i+"个文件: "+file_name);
					
					//检查本地是否已有该文件
					if(file_map.containsKey(file_name)) { //如果文件已存在
						mss.sendString("exist");			
					}
					
					else {
						mss.sendString(file_name);
						MyStreamSocket mss1 = new MyStreamSocket(ip, port, WAIT_TIME);
						sleep(500); //确保等待mss1成功连接
						mss.sendString(mss1.getLocalIP());
						new Thread(new FileReciever(path, file_name, mss1)).start();
					}
					
				}
				
//				System.out.println("\n所有文件已同步完成。");
							
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	
	public static void main(String[] args) {
		Client c = new Client();
		c.sync(SERVER_IP, SERVER_PORT, "sync1");
	}
	
}
