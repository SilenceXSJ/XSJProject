package org.xsj.socketserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

import org.xsj.global.Global;
import org.xsj.global.TransObj;

/**
 * 写 socket 输出流
 * 
 * @author xsj
 * 
 */
public class MySocketWrite extends Thread {

	ObjectOutputStream pw = null;
	private Socket s = null;

	static int i = 0;

	public MySocketWrite(Socket so) {
		System.out.println("激活线程: " + Thread.activeCount());
		this.s = so;
		// if (remsg != null && remsg.getHead() != null) {
		// String ip = remsg.getHead().split("@")[1];
		// Map<String, Socket> map = Global.getSocketMap();
		// this.s = map.get(ip);
		// }
	}

	@Override
	public void run() {
		i++;
		System.out.println("发送信息线程 :" + i);
		while (true) {
			try {
				if (s != null && s.isConnected() && !s.isClosed()) {
					Map<Socket, Vector<TransObj>> map = Global
							.getWriteOnlineVectorMap();
					Vector<TransObj> vt = map.get(s);
					if (vt != null && vt.size() > 0) {
						pw = new ObjectOutputStream(s.getOutputStream());
						pw.writeObject(vt.get(0));
						pw.flush();
						vt.remove(0);
						Global.getWriteOnlineVectorMap().put(s, vt);
					}

				} else {
					if (pw != null) {
						pw.close();
					}
					if (s != null) {
						s.close();
					}
				}
				sleep(1000);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

		}
	}

}
