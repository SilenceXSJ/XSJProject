package org.xsj.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Global {

	private static Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 接收消息map <  发送人 ,  消息>
	 */
	private static Map<String,Vector<TransObj>> talkMsg = new HashMap<String, Vector<TransObj>>();

	/**
	 * 发送消息缓存
	 */
	private static Vector<TransObj> msg = new Vector<TransObj>();

	private Global(){
		
	}
	public synchronized static Map<String, Object> gMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	public synchronized static Vector<TransObj> gMsg() {
		if (msg == null) {
			msg = new Vector<TransObj>();
		}
		return msg;
	}
	public static Map<String,Vector<TransObj>> getTalkMsg() {
		return talkMsg;
	}
	public static void setTalkMsg(Map<String,Vector<TransObj>> talkMsg) {
		Global.talkMsg = talkMsg;
	}
}
