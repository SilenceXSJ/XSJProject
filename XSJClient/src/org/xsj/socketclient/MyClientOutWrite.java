package org.xsj.socketclient;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import org.xsj.global.Global;
import org.xsj.global.MyObjectOutPutStream;
import org.xsj.global.TransObj;

public class MyClientOutWrite extends Thread {

	Socket s = null;
	
	MyObjectOutPutStream pw = null;
	
	public MyClientOutWrite(Socket s ){
		this.s = s;
	}
	
	@Override
	public void run() {
		
		while(true){
			if (s.isConnected() && !s.isClosed() && !s.isInputShutdown() && !s.isOutputShutdown()) {
				try {
					Vector<TransObj>  msgs = Global.gMsg();
					if(msgs != null && msgs.size() > 0){
						pw = new MyObjectOutPutStream(s.getOutputStream());
						pw.writeObject(msgs.get(0));
						pw.flush();
						msgs.remove(0);
					}
					sleep(500);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}else{
				try {
					s.close();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
	}
}
