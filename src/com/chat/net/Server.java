package com.chat.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chat.util.OnLineUser;
import com.cl253.smssdk.lib.SMSSDK;
import com.cl253.smssdk.listener.IGetVerificationCodeCallBack;

public class Server {
	
	public static List<Socket> socketList = new ArrayList<Socket>();
	
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(8888);
			while (true) {
				Socket client = server.accept();
				System.out.println(client);
				if(!socketList.contains(client)){
					socketList.add(client);
					new Thread(new ReceiveThread(client)).start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
