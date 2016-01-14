package org.xsj.test;

import java.net.ServerSocket;

import org.xsj.socketserver.MyRecSocket;
import org.xsj.socketserver.MySocketServer;

public class TestS {
	
	public static void main(String[] args) {
		ServerSocket s = MySocketServer.getInstance();
		MyRecSocket a = new MyRecSocket(s);
		a.start();
	}
}
