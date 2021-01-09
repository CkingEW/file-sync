package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import com.MyStreamSocket;

import ui.Client_Frame;

public class Client{
	
	public static Client_Frame cf = null;
	
	public Client(String frame_name){
		cf = new Client_Frame(frame_name);
		cf.setListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int sync_type = cf.getSyncType();
				switch(sync_type) {
				case 0: Timeout_Thread.sync_timeout = 10 * 1000;break;
				case 1: Timeout_Thread.sync_timeout = 60 * 60 * 1000;break;
				case 2: Timeout_Thread.sync_timeout = 24 * 60 * 60 * 1000;break;
				case 3: Timeout_Thread.sync_timeout = 0;break;
				}
				new Timeout_Thread(cf.getHostIP(), cf.getPort(), cf.getSyncPath()).start();
			}
		});
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
		Client client = new Client("客户端");
	}
	
}
