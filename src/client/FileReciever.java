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

			Client.cf.addString("正在同步文件："+filename);
			while((len = mss.recieveFile(b)) == 1024) {
				fos.write(b, 0, len);
			}
			fos.write(b, 0, len);
			Client.cf.addString("文件："+filename+"同步完成");
			fos.close();
			mss.close();
			Timeout_Thread.syncing_rest --;
			
			if (Timeout_Thread.syncing_rest==0)
				Client.cf.addString("所有文件已同步完成");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
