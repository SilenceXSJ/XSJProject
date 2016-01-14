package org.xsj.socketclient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.xsj.control.ControlServer;
import org.xsj.global.TransObj;

public class MyClientInRead extends Thread  {

	Socket s = null;

	ObjectInputStream br = null;

	public MyClientInRead(Socket s  ) {
		this.s = s;
	}


	@Override
	public void run() {

		while (true) {
			if (s.isConnected() && !s.isClosed() && !s.isInputShutdown() && !s.isOutputShutdown()) {
				try {
					br = new ObjectInputStream(s.getInputStream());
					TransObj trobj =  (TransObj) br.readObject();
					if (trobj != null && trobj.getHead() != null) {
						ControlServer.analysisMsg(s,trobj);
					}
					sleep(500);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					if (s != null) {
						s.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}

	}


}
