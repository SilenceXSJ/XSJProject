package org.xsj.socketserver;

import java.io.IOException;
import java.net.ServerSocket;

import org.xsj.constant.Constants;

/**
 * �˿ڷ�������
 * 
 * @author xsj
 * 
 */
public class MySocketServer {

	private static ServerSocket s = null;

	private MySocketServer() {
		try {
			if (null == s) {
				s = new ServerSocket(Integer.valueOf(Constants.SERVER_PORT));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡΨһ ServerSocket
	 * 
	 * @return
	 */
	public static ServerSocket getInstance(){
		 if(s ==null){
			 try {
				 s =  new ServerSocket(Integer.valueOf(Constants.SERVER_PORT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return s;
	}
}
