package com.chat.net;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.chat.bean.Comfirm;
import com.chat.bean.UserInformation;
import com.chat.util.SendMail;

public class Clientr {
	public static Socket client = null;
	public static void main(String[] args) {
		try {
			InetAddress a = InetAddress.getByName("114.115.205.143");
			client = new Socket("114.115.205.143",9999);
			//Comfirm c = new Comfirm();
			//c.setConfirm("m13814545863@163.com");
			UserInformation u = new UserInformation();
			u.setNickname("ben12");
			File file = new File("");
			//new SendLoginThread(client).send(c);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
						Object obj = ois.readObject();
						if(obj instanceof Comfirm){
							System.out.println(((Comfirm)obj).getYanZhengMa());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
