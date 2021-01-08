package client;

import java.io.FileOutputStream;

import com.MyStreamSocket;

public class FileReciever implements Runnable {

	protected String filepath = null;
	protected MyStreamSocket mss = null;
	
	public FileReciever(String filepath, MyStreamSocket mss) {
		// TODO Auto-generated constructor stub
		this.filepath = filepath;
		this.mss = mss;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filepath);
			byte[] b = new byte[1024];
			int len = 0;
			while((len = mss.recieveFile(b)) != -1) {
				fos.write(b, 0, len);
			}
			
			fos.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
