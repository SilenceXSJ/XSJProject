package org.xsj.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.xsj.global.Global;

/**
 * ¼àÌýsocket
 * 
 * @author xsj
 * 
 */
public class MyRecSocket extends Thread {

	ServerSocket s;

	public MyRecSocket(ServerSocket s) {
		this.s = s;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket soc = s.accept();
				Global.getSocketMap().put(soc.getInetAddress().toString(), soc);
				if (soc != null && soc.isConnected()) {
					new MySocketRead(soc).start();
					new MySocketWrite(soc).start();
				}
				sleep(1000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
