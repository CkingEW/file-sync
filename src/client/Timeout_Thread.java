package client;

public class Timeout_Thread extends Thread{
	
	public static int sync_timeout = 0;
	public static int syncing_rest = 0;
	private String ip, sync_path;
	private int port;
	private int tens = 10*1000;
	
	public Timeout_Thread(String ip, int port, String sync_path) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.port = port;
		this.sync_path = sync_path;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		if(sync_timeout != 0) {
			while(true) {
				int t = sync_timeout;
				try {
					sleep(sync_timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sync_timeout != t)
					break;
				while(syncing_rest != 0);
				new Sync_Thread(ip, port, sync_path).start();
			}
		}
		else {
			new Sync_Thread(ip, port, sync_path).start();
		}
	}
}
