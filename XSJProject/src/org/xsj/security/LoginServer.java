package org.xsj.security;

import java.util.ArrayList;
import java.util.Vector;

import org.xsj.util.SqlHelper;



/**
 * µÇÂ¼
 * @author xsj
 *
 */
public class LoginServer {

	
	/**
	 * µÇÂ¼
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean login(String username,String password){
		SqlHelper sh = new SqlHelper();
		ArrayList<Object[]> list = sh.executeQuery("select id,lastip from security_user t  where t.username = ? and t.password = ? ",new String[]{username,password});
		if(list !=null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static Vector<User> findFirends(){
		Vector<User> vu = new Vector<User>();
		SqlHelper sh = new SqlHelper();
		ArrayList<Object[]> list = sh.executeQuery("select id,username,password,lastip from security_user t",null);
		if(list != null && list.size() >0){
			for (Object[] userStr : list) {
				User u = new User();
				u.setId(String.valueOf(userStr[0]));
				u.setUsername(String.valueOf(userStr[1]));
				u.setPassword(String.valueOf(userStr[2]));
				u.setLastip(String.valueOf(userStr[3]));
				vu.add(u);
			}
		}
		return vu;
		
	}
}
