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
	
	public Server(String frame_name) {
		sf = new Server_Frame(frame_name);
		sf.setListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!ison) {
					new Sync_Thread(sf.getHostIP(), sf.getPort(), sf.getSyncPath()).start();
					ison = true;
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new Server("服务器");
	}
}
