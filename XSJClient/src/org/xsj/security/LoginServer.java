package org.xsj.security;

import java.io.Serializable;

import org.xsj.global.Global;
import org.xsj.global.TransObj;
import org.xsj.util.MyUtil;

public class LoginServer implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ÇëÇó·þÎñÆ÷
	 * @param username
	 * @param password
	 */
	public static void login(String username,String password){
		String info = MyUtil.loginMsg( username +"%" + password);
		TransObj transObj = new TransObj();
		transObj.setHead(info);
		Global.gMsg().add(transObj);
	}

}
