package org.xsj.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Global {

	private static Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * ������Ϣmap <  ������ ,  ��Ϣ>
	 */
	private static Map<String,Vector<TransObj>> talkMsg = new HashMap<String, Vector<TransObj>>();

	/**
	 * ������Ϣ����
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
