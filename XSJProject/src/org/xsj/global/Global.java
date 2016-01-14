package org.xsj.global;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Global {

	/**
	 * 在线用户username , socket
	 */
	private static Map<String, Socket> userMap = new HashMap<String, Socket>();
	
	
	/**
	 * 在线socket 客户端；socket   , username
	 */
	private static Map<Socket, String> onlineSocketMap = new HashMap<Socket, String>();
	
	/**
	 * 存放客户端ip , socket
	 */
	private static Map<String, Socket> socketMap = new HashMap<String, Socket>();

 
	/**
	 * 存放在线消息Socket ,Vector<TransObj>
	 */
	private static Map<Socket,Vector<TransObj>> writeOnlineVectorMap = new HashMap<Socket, Vector<TransObj>>();
	
	/**
	 * 存放离线 消息 username,Vector<TransObj>
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
