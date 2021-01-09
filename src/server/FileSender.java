package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.MyStreamSocket;

public class FileSender implements Runnable {
	
	protected String filename = null;
	protected MyStreamSocket mss = null;
	protected static final String FILE_PATH = "SyncFiles";
	
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
