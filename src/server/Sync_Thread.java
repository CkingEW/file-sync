package server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.MyStreamSocket;

public class Sync_Thread extends Thread {
	
	private String ip, sync_path;
	private int port;
	public static HashMap<String, MyStreamSocket> ip_map = null;
	
	public Sync_Thread(String ip, int port, String sync_path) {
		this.ip = ip;
		this.port = port;
		this.sync_path = sync_path;
	}
	
	private void sync() {
		ip_map = new HashMap<>();
		ServerSocket serversocket = null;
		ExecutorService pool = Executors.newFixedThreadPool(5);
		String client_IP;
		
		try{
			serversocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
			Server.sf.addString("--------Server start--------");
		}catch (IOException e) {
			// TODO: handle exception		
			Server.sf.addString("服务器创建失败");
			e.printStackTrace();
		}
		if(serversocket != null) {
			try {
				while(true) {
					Socket socket = serversocket.accept();
					MyStreamSocket mss = new MyStreamSocket(socket);
					client_IP = mss.getIP();
					ip_map.put(client_IP, mss);
					Server.sf.addString("当前连接的客户端：" + client_IP);
					Recieve_Thread rt = new Recieve_Thread(mss, sync_path);
					pool.execute(rt);
					
				}
																
			} catch (IOException e) {
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
