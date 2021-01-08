package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyStreamSocket{
	
	protected DataInputStream in = null;
	protected InputStream inn = null;
	protected DataOutputStream out = null;
	protected Socket socket = null;
	
	public MyStreamSocket(String ip, int port, int timeout) throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(ip),port), timeout);
		inn = socket.getInputStream();
		in = new DataInputStream(inn);// 读取客户端传过来信息的DataInputStream                   
        out = new DataOutputStream(socket.getOutputStream());// 向客户端发送信息的DataOutputStream  
	}
	
	public MyStreamSocket(Socket so) throws IOException{
		socket = so;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	public void sendFile(byte[] data, int off, int len) throws IOException {
		out.write(data, off, len);
	}
	
	public void sendNumber(int file_number) throws IOException {
		out.writeInt(file_number);
	}
	
	public void sendString(String str) throws IOException {
		out.writeUTF(str);
	}
	
	public int recieveFile(byte[] data) throws IOException {
		return in.read(data);
	}
	
	public int recieveNumber() throws IOException {
		return in.readInt();
	}
	
	public String recieveString() throws IOException {
		return in.readUTF();
	}
	
	public String getIP() throws IOException{
		return socket.getInetAddress().getHostAddress()+":"+socket.getPort();
	}
	
	public String getLocalIP() throws IOException{
		return socket.getLocalAddress().getHostAddress()+":"+socket.getLocalPort();
	}
	
}
