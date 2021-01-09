package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.MyStreamSocket;

public class Recieve_Thread implements Runnable {
	
	protected MyStreamSocket mss;
	protected String FILE_PATH;
	
	public Recieve_Thread(MyStreamSocket mss, String filepath) {
		// TODO Auto-generated constructor stub
		this.mss = mss;
		this.FILE_PATH = filepath;
	}
	
	@Override
	public void run() {
		String flag;
		
		// TODO Auto-generated method stub
		while(true) {
			try {
					
				flag = mss.recieveString();	//读取客户端请求
				if (flag.equals("nice")) {
					File file = new File(FILE_PATH);		//获取其file对象
					File[] filelist = file.listFiles();	//遍历path下的文件和目录，放在File数组中
					
					//发送同步文件的数量				
					mss.sendNumber(filelist.length);
					Server.sf.addString("\n共有"+filelist.length+"个文件待同步");
					
					int i = 1;
					for(File f:filelist){	//遍历File[]数组
						if(!f.isDirectory()) {	//若非目录(即文件)，则打印		
															
							mss.sendString(f.getName()); //发送文件名		
							Server.sf.addString("\n正在同步第"+i+"个文件: "+f.getName());
							
							String filename	= mss.recieveString();//客户端如果没有这个文件就会请求这个文件
							

							if(filename.equals("exist")) {
								Server.sf.addString("第"+i+"个文件: "+f.getName()+" 被跳过");	
							}
							else {
								String ip = mss.recieveString();
								new Thread(new FileSender(FILE_PATH,filename, Sync_Thread.ip_map.get(ip))).start();
							}
						}
						i++;
					}
					
				}
									
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}	
}
