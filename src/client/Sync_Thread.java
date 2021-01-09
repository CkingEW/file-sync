package client;

import java.io.File;
import java.util.HashMap;

import com.MyStreamSocket;

public class Sync_Thread extends Thread {
	private HashMap<String, Boolean> file_map;
	private String ip, sync_path;
	private int WAIT_TIME = 5000;
	private int port;
	
	public Sync_Thread(String ip, int port, String sync_path) {
		this.ip = ip;
		this.port = port;
		this.sync_path = sync_path;
		file_map = new HashMap<String, Boolean>();
		File file = new File(sync_path);
		File[] filelist = file.listFiles();
		for(File f : filelist) 
			if(!f.isDirectory())
				file_map.put(f.getName(), true);
	}
	
	private void sync() {
		MyStreamSocket mss = null;
		try{
			mss = new MyStreamSocket(ip, port, WAIT_TIME);
			Client.cf.addString("当前客户端IP: "+mss.getLocalIP());
		}catch (Exception e) {
			Client.cf.addString("连接服务器失败");
		}
		if(mss != null) {
			try {
				byte[] data  = new byte[1024*1024];
				int len = 0, file_number;
				String file_name;
				
				mss.sendString("nice");	//对服务器发送同步请求
													
				file_number = mss.recieveNumber();//接收同步文件的数量
				Client.cf.addString("共需同步"+file_number+"个文件");
				Timeout_Thread.syncing_rest = file_number;
				
				for (int i=1;i<=file_number;i++) {
					
					file_name = mss.recieveString();	//获得文件名
					
					//检查本地是否已有该文件
					if(file_map.containsKey(file_name)) { //如果文件已存在
						mss.sendString("exist");
						Client.cf.addString("文件：" + file_name + "已存在，将跳过同步");
						Timeout_Thread.syncing_rest --;
					}					
					else {
						mss.sendString(file_name);
						MyStreamSocket mss1 = new MyStreamSocket(ip, port, WAIT_TIME);
						sleep(500); //确保等待mss1成功连接
						mss.sendString(mss1.getLocalIP());
						new Thread(new FileReciever(sync_path, file_name, mss1)).start();
					}
				}
				
				if (Timeout_Thread.syncing_rest==0)
					Client.cf.addString("所有文件已同步完成");
				
				mss.close();
							
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		sync();
	}
}
