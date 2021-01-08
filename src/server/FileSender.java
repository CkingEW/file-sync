package server;

import java.io.File;
import java.io.FileInputStream;

import com.MyStreamSocket;

public class FileSender implements Runnable {
	
	protected String filename = null;
	protected MyStreamSocket mss = null;
	
	public FileSender(String filename, MyStreamSocket mss) {
		// TODO Auto-generated constructor stub
		this.filename = filename;
		this.mss = mss;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] file_byte = new byte[1024];
		try {
			FileInputStream fis = new FileInputStream(filename);
			int len = 0;
			System.out.println(filename+" start");
			while((len = fis.read(file_byte)) != -1) {
				mss.sendFile(file_byte, 0, len);
			}
			System.out.println(filename+" finish");
			
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
