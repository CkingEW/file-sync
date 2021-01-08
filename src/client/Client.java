package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import com.MyStreamSocket;

import ui.Client_Frame;

public class Client extends Thread{
	
//	private static final String SERVER_IP = "192.168.1.106";
//	private static final int SERVER_PORT = 10086;
	private static String SERVER_IP;
	private static int SERVER_PORT;
	private static final int WAIT_TIME = 1000*5;//ms
//	private static int mod;//同步模式
//	private static boolean clicked;//是否已经点击同步
//	private static boolean asking_for_sync;//是否已经点击同步
	private HashMap<String, Boolean> file_map;
	MyStreamSocket mss = null;
	
	public Client(){
		file_map = new HashMap<String, Boolean>();
//		mod = 3;
//		clicked = false;
//		asking_for_sync = false;
	}
	
	public void sync(String ip, int port, String path) {
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
					System.out.println("已同步第"+(i+1)+"个文件: "+file_name);
				}
				
//				System.out.println("\n所有文件已同步完成。");
							
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	public void sync_scheduling(String ip, int port){//获取同步类型，0为即时同步（10s），1为按小时同步，2为按天同步，3为手动同步
//		try {
//			if(mod == 0){
//				sleep(10*1000);//10s
//				asking_for_sync = true;
//			}
//			else if(mod == 1) {
//				sleep(60*60*1000);//1小时
//				asking_for_sync = true;
//			}
//			else if(mod == 2) {
//				sleep(24*60*60*1000);//1天
//				asking_for_sync = true;
//			}
//			else if(mod == 3){
//				if(clicked == true)//如果有点击
//				{
//					asking_for_sync = true;
//				}
//			}
//			if(asking_for_sync == true)
//			{
//				Client c = new Client();
//				c.sync(ip, port, "sync");
//				asking_for_sync = false;
//			}
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
		//while循环中调用sync_scheduling
		Client_Frame mf = new Client_Frame("客户端");
		ActionListener actionlistener = new ActionListener(	) {
			public void actionPerformed(ActionEvent aa) {
				SERVER_IP = mf.getHostIP();
				SERVER_PORT = mf.getPort();
				Client c = new Client();
				c.sync(SERVER_IP, SERVER_PORT, "sync1");
			}
		};
		mf.setListener(actionlistener);
	}
	
}
