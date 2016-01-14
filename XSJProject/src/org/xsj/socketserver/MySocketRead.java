package org.xsj.socketserver;

import java.io.IOException;
import java.net.Socket;

import org.xsj.constant.Constants;
import org.xsj.control.ControlServer;
import org.xsj.global.MyObjectInPutStream;
import org.xsj.global.TransObj;

/**
 * 读取socket 输入流
 * @author xsj
 *
 */
public class MySocketRead extends Thread {


	MyObjectInPutStream bfr = null;
	private Socket s = null ;
	
//	private static ExecutorService es = Executors.newCachedThreadPool();
	public MySocketRead(Socket s ) {
		 this.s = s;
//		 if(es ==null){
//			 es = Executors.newCachedThreadPool();
//		 }
//		 es.submit(this);
//		 es.execute(this);
	}

	@Override
	public void run() {
		while(true){
			try {
				System.out.println("读取"+ Constants.i + "__" + s.getInetAddress().toString() +":" +  s.getPort());
				if(s != null && s.isConnected() && !s.isClosed() && !s.isInputShutdown()){
					bfr =  new MyObjectInPutStream(s.getInputStream());
					Constants.i ++ ;
					TransObj tobj = (TransObj) bfr.readObject();
					if(tobj != null && tobj.getHead() != null){
						 ControlServer.analysisMsg(s,tobj);
					}
				}else{
					bfr.close();
					s.close();
					ControlServer.clearUser(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
				ControlServer.clearUser(s);
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
}
