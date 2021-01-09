package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.MyStreamSocket;

public class FileSender implements Runnable {
	
	protected String FILE_PATH = null;
	protected String filename = null;
	protected MyStreamSocket mss = null;
	
	public FileSender(String FILE_PATH, String filename, MyStreamSocket mss) {
		// TODO Auto-generated constructor stub
		this.FILE_PATH = FILE_PATH;
		this.filename = filename;
		this.mss = mss;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] file_byte = new byte[1024];
		try {
			FILE_PATH = Server.getSyncPath();
			FileInputStream fis = new FileInputStream(FILE_PATH+"/"+filename);
			int len = 0;
			while((len = fis.read(file_byte)) != -1) {
				mss.sendFile(file_byte, 0, len);
			}
			Server.sf.addString("文件："+filename+"同步完成");
			
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
