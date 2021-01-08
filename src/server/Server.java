package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.*;

public class Server {
	
	private static final String SERVER_IP = "192.168.1.106";
	public static HashMap<String, MyStreamSocket> ip_map = null;
	
	public static void main(String[] args) {
		ip_map = new HashMap<>();
		ServerSocket serversocket = null;
		ExecutorService pool = Executors.newFixedThreadPool(5);
		String client_IP;
		
		try{
			serversocket = new ServerSocket(10086, 1, InetAddress.getByName(SERVER_IP));
			System.out.println("--------Server start--------");
		}catch (IOException e) {
			// TODO: handle exception		
			System.out.println("服务器创建失败");
			e.printStackTrace();
		}
		if(serversocket != null) {
			try {
				System.out.println("当前已连接的客户端IP：");
				int i = 0;
				while(true) {
					Socket socket = serversocket.accept();
					MyStreamSocket mss = new MyStreamSocket(socket);
					client_IP = mss.getIP();
					ip_map.put(client_IP, mss);
					if (i==0) {
						System.out.println(client_IP);
						i = 1;	
					}								
					Recieve_Thread rt = new Recieve_Thread(mss);
					pool.execute(rt);
					
				}
																
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
