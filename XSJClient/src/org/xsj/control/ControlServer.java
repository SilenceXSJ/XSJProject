package org.xsj.control;

import java.net.Socket;
import java.util.Vector;

import org.xsj.constant.Constants;
import org.xsj.global.Global;
import org.xsj.global.TransObj;

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
			String ip = s[1];
			String port = s[2];
		

			if (Constants.LOGIN_TAG.equals(tag)) {// 登录
				String info = s[3];
				if(Constants.LOGIN_FALSE_R.equals(info)){
					Global.gMap().put(Constants.LOGIN_REQUEST, Constants.LOGIN_FALSE_R);
				}else if(Constants.LOGIN_SUCCESS_R.equals(info)){
					Global.gMap().put(Constants.LOGIN_REQUEST, Constants.LOGIN_SUCCESS_R);
					Global.gMap().put(Constants.TITLE, ip + ":" + port);
					Global.gMap().put(Constants.FRIENDS, transObj.getObj());
				}
			} else if (Constants.MSG_TAG.equals(tag)) {
				readMsg(transObj);
			}
		}
		return null;
	}
	/**
	 * 处理其他客户端发来消息
	 * @param transObj
	 */
	public static void readMsg(TransObj transObj){
		if(transObj != null){
			System.out.println(transObj.getHead());
			System.out.println(transObj.getObj());
			String head = transObj.getHead();
			String[] infos = head.split("@");
			String suser = infos[3];
			Vector<TransObj> vt = Global.getTalkMsg().get(suser);
			if(vt != null){
				vt.add(transObj);
			}else{
				vt = new Vector<TransObj>();
				vt.add(transObj);
			}
			Global.getTalkMsg().put(suser, vt);
		}
		
		
		
	}
}
