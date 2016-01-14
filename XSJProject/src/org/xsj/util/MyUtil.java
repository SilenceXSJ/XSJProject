package org.xsj.util;

import org.xsj.constant.Constants;

public class MyUtil {

	public static String loginMsg(String info) {
		return Constants.LOGIN_TAG + "@" + Constants.IP_ADDR + "@"
				+ Constants.SERVER_PORT+ "@" + info;
	}

	public static String talkMsg(String sip, String sport, String sUser,
			String rUser) {
		return Constants.MSG_TAG + "@" + sip + "@" + sport + "@" + sUser + "@"
				+ rUser;
	}
}
