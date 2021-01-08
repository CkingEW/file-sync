package client;

import com.MyStreamSocket;

public class Client {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 10086;
	private static final int WAIT_TIME = 1000*5;//ms
	
	public static void main(String[] args) {
		MyStreamSocket mss = null;
		try{
			mss = new MyStreamSocket(SERVER_IP, SERVER_PORT, WAIT_TIME);
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
				
				for (int i=0;i<file_number;i++) {
					
					file_name = mss.recieveString();	//获得文件名
//					len = mss.recieveFile(data);	//同步文件,返回单个文件的字节数
//					System.out.println(len);
														
					mss.sendString("OK");
					System.out.println("已同步第"+(i+1)+"个文件: "+file_name);
				}
				
				System.out.println("\n所有文件已同步完成。");
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
