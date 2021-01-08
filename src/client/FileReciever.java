package client;

import java.io.FileOutputStream;

import com.MyStreamSocket;

public class FileReciever implements Runnable {

	protected String filepath = null, filename = null;
	protected MyStreamSocket mss = null;
	
	public FileReciever(String filepath, String filename, MyStreamSocket mss) {
		// TODO Auto-generated constructor stub
		this.filepath = filepath;
		this.filename = filename;
		this.mss = mss;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filepath + "/" + filename);
			byte[] b = new byte[1024];
			int len = 0;
			System.out.println(filename+" start");
			while((len = mss.recieveFile(b)) == 1024) {
				fos.write(b, 0, len);
			}
			fos.write(b, 0, len);
			System.out.println(filename+" finish");
			
			fos.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
