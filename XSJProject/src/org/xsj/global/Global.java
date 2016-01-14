package org.xsj.global;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Global {

	/**
	 * �����û�username , socket
	 */
	private static Map<String, Socket> userMap = new HashMap<String, Socket>();
	
	
	/**
	 * ����socket �ͻ��ˣ�socket   , username
	 */
	private static Map<Socket, String> onlineSocketMap = new HashMap<Socket, String>();
	
	/**
	 * ��ſͻ���ip , socket
	 */
	private static Map<String, Socket> socketMap = new HashMap<String, Socket>();

 
	/**
	 * ���������ϢSocket ,Vector<TransObj>
	 */
	private static Map<Socket,Vector<TransObj>> writeOnlineVectorMap = new HashMap<Socket, Vector<TransObj>>();
	
	/**
	 * ������� ��Ϣ username,Vector<TransObj>
	 */
	private static Map<String,Vector<TransObj>> writeOfflineVectorMap = new HashMap<String, Vector<TransObj>>();
	
	
	
	private Global(){
		
	}

	public synchronized static Map<String, Socket> getSocketMap() {
		return socketMap;
	}
	public synchronized static void setSocketMap(Map<String, Socket> socketMap) {
		Global.socketMap = socketMap;
	}

	public static Map<String, Socket> getUserMap() {
		return userMap;
	}

	public static void setUserMap(Map<String, Socket> userMap) {
		Global.userMap = userMap;
	}

	public static Map<Socket, String> getOnlineSocketMap() {
		return onlineSocketMap;
	}

	public static void setOnlineSocketMap(Map<Socket, String> onlineSocketMap) {
		Global.onlineSocketMap = onlineSocketMap;
	}

	public static Map<Socket, Vector<TransObj>> getWriteOnlineVectorMap() {
		return writeOnlineVectorMap;
	}

	public static void setWriteOnlineVectorMap(
			Map<Socket, Vector<TransObj>> writeOnlineVectorMap) {
		Global.writeOnlineVectorMap = writeOnlineVectorMap;
	}

	public static Map<String, Vector<TransObj>> getWriteOfflineVectorMap() {
		return writeOfflineVectorMap;
	}

	public static void setWriteOfflineVectorMap(
			Map<String, Vector<TransObj>> writeOfflineVectorMap) {
		Global.writeOfflineVectorMap = writeOfflineVectorMap;
	}
 
}
