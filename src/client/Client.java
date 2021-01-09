package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import com.MyStreamSocket;
import ui.Client_Frame;

public class Client{
	
	private String Client_IP;
	private int Client_Port;
	private static String Client_Sync_Path;
	public static Client_Frame cf = null;
	
	public Client(String frame_name){
		cf = new Client_Frame(frame_name);
		cf.setListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client_IP = cf.getHostIP();
				Client_Port = cf.getPort();
				Client_Sync_Path = cf.getSyncPath();
				
				File file=new File(Client_Sync_Path);
				if(!file.exists())//如果文件夹不存在
					file.mkdir();//创建文件夹
			
				int sync_type = cf.getSyncType();
				switch(sync_type) {
				case 0: Timeout_Thread.sync_timeout = 10 * 1000;break;
				case 1: Timeout_Thread.sync_timeout = 60 * 60 * 1000;break;
				case 2: Timeout_Thread.sync_timeout = 24 * 60 * 60 * 1000;break;
				case 3: Timeout_Thread.sync_timeout = 0;break;
				}
				new Timeout_Thread(Client_IP, Client_Port, Client_Sync_Path).start();
			}
		});
	}
	
	
	public static void main(String[] args) {
		//while循环中调用sync_scheduling
		Client client = new Client("客户端");
	}
	
}
