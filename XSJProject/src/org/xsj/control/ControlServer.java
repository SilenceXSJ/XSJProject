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

			if (Constants.LOGIN_TAG.equals(tag)) {// ��¼
				String[] uinfo = info.split("%");
				String username = uinfo[0];
				String password = uinfo[1];
				login(username, password, so);
			} else if (Constants.MSG_TAG.equals(tag)) {// ��Ϣ
				String sUser = s[3];
				String rUser = s[4];
				msgTalk(sUser, rUser, transObj);
			}

		}
		return null;
	}

	private static void login(String username, String password, Socket so) {
		System.out.println(username + "========>�����¼");
		boolean islogin = LoginServer.login(username, password);
		String remsg = Constants.LOGIN_FALSE_R;
		if (islogin) {
			// ��¼�ɹ�
			remsg = Constants.LOGIN_SUCCESS_R;
			// ����û� socket
			Global.getUserMap().put(username, so);
			Global.getOnlineSocketMap().put(so, username);
			System.out.println(username + "========>��¼�ɹ�");

			// �������Ͷ���
			Vector<User> vu = LoginServer.findFirends();
			TransObj tobj = new TransObj();
			tobj.setHead(Constants.LOGIN_TAG + "@" + so.getInetAddress().toString()
					+ "@" + so.getPort() + "@" + remsg);
			tobj.setObj(vu);

			// ============��ӵ�¼������Ϣ===============

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

			
			// ====== ����������Ϣ====================
			
			changeMessage(username);
		}

		
	}

	private static void msgTalk(String sUser, String rUser, TransObj otobj) {
		Map<String, Socket> smap = Global.getSocketMap();
		Map<String, Socket> umap = Global.getUserMap();
		Socket rs = umap.get(rUser);
		if (rs != null) {// �Է�����
			Socket sop = smap.get(rs.getInetAddress().toString());
			sop.getInetAddress();
			// ��ȡĿ���ip
			if (sop != null && sop.isConnected() && !sop.isClosed()
					&& !sop.isInputShutdown() && !sop.isOutputShutdown()) {

				TransObj tobj = new TransObj();
				tobj.setHead(MyUtil.talkMsg(rs.getInetAddress().toString(),
						sop.getPort() + "", sUser, rUser));
				tobj.setObj(otobj.getObj());
				// ��ӷ�����Ϣ
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
		} else {// �Է����� ���������Ϣ
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
	 * ��������Ϣ ��ӵ�������Ϣ��
	 * @param user
	 */
	public static void changeMessage(String user){
		
		Vector<TransObj>   offVt =    Global.getWriteOfflineVectorMap().get(user);
		if(offVt != null && offVt.size()>0){
			System.out.println("ת��������Ϣ:"+ user +  ":" + offVt.size()  + "����");
			Socket sok = Global.getUserMap().get(user);
			for (TransObj transObj : offVt) {
				Global.getWriteOnlineVectorMap().get(sok).add(transObj);
			}
			offVt.clear();
		}
		
		
	}

	public static void clearUser(Socket sok) {

		if (sok != null) {
			System.out.println("�ر� �Ͽ�socket : " + sok.getInetAddress());

			// �Ƴ�����socket
			Global.getSocketMap().remove(sok.getInetAddress().toString());
			String username = Global.getOnlineSocketMap().get(sok);
			System.out.println("�Ƴ��û� : " + username);

			// �Ƴ��û�����socket
			Global.getUserMap().remove(username);
			// �Ƴ������û�
			Global.getOnlineSocketMap().remove(sok);
		}
	}

	/**
	 * ͨ��ip ��ȡsocket
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