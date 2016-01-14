package org.xsj.test;

import java.net.Socket;

import org.xsj.socketclient.MyClientInRead;
import org.xsj.socketclient.MyClientOutWrite;
import org.xsj.socketclient.SocketInstance;
import org.xsj.ui.GUITalk;

public class Test {

	public static void main(String[] args) {
		Socket s = SocketInstance.getInstance();// 连接 服务器
//		GUITalk qq = new GUITalk();
		 new GUITalk();
		
		MyClientOutWrite w = new MyClientOutWrite(s);
		w.start();
		MyClientInRead r = new MyClientInRead(s);
		r.start();
	}
}
