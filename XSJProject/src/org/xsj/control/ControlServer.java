package org.xsj.control;

import java.net.Socket;
import java.util.Map;
import java.util.Vector;

import org.xsj.constant.Constants;
import org.xsj.global.Global;
import org.xsj.global.TransObj;
import org.xsj.security.LoginServer;
import org.xsj.security.User;
import org.xsj.util.MyUtil;

public class ControlServer {

	/**
	 * tag@ip@port@info
	 * 
	 * @param msg
	 */
	public static String analysisMsg(Socket so, TransObj transObj) {

		if (transObj != null && transObj.getHead() != null) {
			String msg = transObj.getHead();
			String[] s = msg.split("@");
			String tag = s[0];
			// String ip = s[1];
			// String port = s[2];
			String info = s[3];

			if (Constants.LOGIN_TAG.equals(tag)) {// 登录
				String[] uinfo = info.split("%");
				String username = uinfo[0];
				String password = uinfo[1];
				login(username, password, so);
			} else if (Constants.MSG_TAG.equals(tag)) {// 信息
				String sUser = s[3];
				String rUser = s[4];
				msgTalk(sUser, rUser, transObj);
			}

		}
		return null;
	}

	private static void login(String username, String password, Socket so) {
		System.out.println(username + "========>请求登录");
		boolean islogin = LoginServer.login(username, password);
		String remsg = Constants.LOGIN_FALSE_R;
		if (islogin) {
			// 登录成功
			remsg = Constants.LOGIN_SUCCESS_R;
			// 存放用户 socket
			Global.getUserMap().put(username, so);
			Global.getOnlineSocketMap().put(so, username);
			System.out.println(username + "========>登录成功");

			// 构建发送对象
			Vector<User> vu = LoginServer.findFirends();
			TransObj tobj = new TransObj();
			tobj.setHead(Constants.LOGIN_TAG + "@" + so.getInetAddress().toString()
					+ "@" + so.getPort() + "@" + remsg);
			tobj.setObj(vu);

			// ============添加登录返回信息===============

			Map<Socket, Vector<TransObj>> map = Global.getWriteOnlineVectorMap();
			Socket sok = getWSocket(tobj);
			if (sok != null) {
				Vector<TransObj> vt = map.get(sok);
				if (vt != null) {
					vt.add(tobj);
				} else {
					vt = new Vector<TransObj>();
					vt.add(tobj);
					map.put(sok, vt);
				}
			}

			
			// ====== 处理离线消息====================
			
			changeMessage(username);
		}

		
	}

	private static void msgTalk(String sUser, String rUser, TransObj otobj) {
		Map<String, Socket> smap = Global.getSocketMap();
		Map<String, Socket> umap = Global.getUserMap();
		Socket rs = umap.get(rUser);
		if (rs != null) {// 对方在线
			Socket sop = smap.get(rs.getInetAddress().toString());
			sop.getInetAddress();
			// 获取目标端ip
			if (sop != null && sop.isConnected() && !sop.isClosed()
					&& !sop.isInputShutdown() && !sop.isOutputShutdown()) {

				TransObj tobj = new TransObj();
				tobj.setHead(MyUtil.talkMsg(rs.getInetAddress().toString(),
						sop.getPort() + "", sUser, rUser));
				tobj.setObj(otobj.getObj());
				// 添加发送信息
				Map<Socket, Vector<TransObj>> map = Global.getWriteOnlineVectorMap();
				Socket sok = getWSocket(tobj);
				if (sok != null) {
					Vector<TransObj> vt = map.get(sok);
					if (vt != null) {
						vt.add(tobj);
					} else {
						vt = new Vector<TransObj>();
						vt.add(tobj);
						map.put(sok, vt);
					}
				}
			}
		} else {// 对方离线 存放离线消息
			Vector<TransObj> off = Global.getWriteOfflineVectorMap().get(rUser);
			if (off != null && off.size() > 0) {
				off.add(otobj);
				Global.getWriteOfflineVectorMap().put(rUser, off);
			} else {
				Vector<TransObj> offv = new Vector<TransObj>();
				offv.add(otobj);
				Global.getWriteOfflineVectorMap().put(rUser, offv);
			}
		}
	}
	
	/**
	 * 将离线消息 添加到发送消息中
	 * @param user
	 */
	public static void changeMessage(String user){
		
		Vector<TransObj>   offVt =    Global.getWriteOfflineVectorMap().get(user);
		if(offVt != null && offVt.size()>0){
			System.out.println("转换离线消息:"+ user +  ":" + offVt.size()  + "条数");
			Socket sok = Global.getUserMap().get(user);
			for (TransObj transObj : offVt) {
				Global.getWriteOnlineVectorMap().get(sok).add(transObj);
			}
			offVt.clear();
		}
		
		
	}

	public static void clearUser(Socket sok) {

		if (sok != null) {
			System.out.println("关闭 断开socket : " + sok.getInetAddress());

			// 移除连接socket
			Global.getSocketMap().remove(sok.getInetAddress().toString());
			String username = Global.getOnlineSocketMap().get(sok);
			System.out.println("移除用户 : " + username);

			// 移除用户在线socket
			Global.getUserMap().remove(username);
			// 移除在线用户
			Global.getOnlineSocketMap().remove(sok);
		}
	}

	/**
	 * 通过ip 获取socket
	 * 
	 * @param tobj
	 * @return
	 */
	public static Socket getWSocket(TransObj tobj) {
		if (tobj != null && tobj != null && tobj.getHead() != null) {
			String ip = tobj.getHead().split("@")[1];
			Map<String, Socket> map = Global.getSocketMap();
			return map.get(ip);
		}
		return null;
	}

}