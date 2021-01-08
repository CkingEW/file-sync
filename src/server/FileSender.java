package server;

import java.io.File;
import java.io.FileInputStream;

import com.MyStreamSocket;

public class FileSender implements Runnable {
	
	protected File file = null;
	protected MyStreamSocket mss = null;
	
	public FileSender(File file, MyStreamSocket mss) {
		// TODO Auto-generated constructor stub
		this.file = file;
		this.mss = mss;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] file_byte = new byte[1024*1024];
		try {
			FileInputStream fis = new FileInputStream(file);
			int len = 0;
			while((len = fis.read(file_byte)) != -1) {
				mss.sendFile(file_byte, 0, len);
			}
			
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
