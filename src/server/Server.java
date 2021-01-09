package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.*;

import ui.Server_Frame;

public class Server {
	
	public static Server_Frame sf = null;
	private boolean ison = false;
	private String Server_IP;
	private int Server_Port;
	private static String Server_Sync_Path;
		
	public Server(String frame_name) {
		sf = new Server_Frame(frame_name);
		sf.setListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Server_IP = sf.getHostIP();
				Server_Port = sf.getPort();
				Server_Sync_Path = sf.getSyncPath();
				
				if(!ison) {
					new Sync_Thread(Server_IP, Server_Port, Server_Sync_Path).start();
					ison = true;
				}
			}
		});
	}
		
	public static void main(String[] args) {
		new Server("服务器");
	}
}
