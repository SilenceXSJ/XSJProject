package org.xsj.socketclient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.xsj.constant.Constants;

public class SocketInstance {

	private static Socket s =null;
	
	private SocketInstance(){
		
	}
	
	public static Socket getInstance(){
		if(s == null){
			try {
				s =  new Socket(Constants.IP_ADDR, Constants.SERVER_PORT);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
}
