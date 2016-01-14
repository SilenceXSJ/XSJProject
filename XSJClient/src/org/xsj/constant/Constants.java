package org.xsj.constant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Constants {
	
	
	public static  int SERVER_PORT   ;
	
	public static  String IP_ADDR  ;
	
	public static final String LOGIN_TAG = "L" ;
	
	public static final String MSG_TAG = "M" ;
	
	public static final String LOGIN_FALSE_R = "FALSE_R";
	
	public static final String LOGIN_SUCCESS_R = "SUCCESS_R";
	
	public static final String LOGIN_REQUEST = "LOGIN_REQUEST";
	
	public static final String FRIENDS = "FRIENDS";
	
	public static final String TITLE = "TITLE";
	
	static{
		Properties p = new Properties();
		try {
			p.load(p.getClass().getResourceAsStream("/config.properties"));
			IP_ADDR = p.get("serverip").toString().trim();
			SERVER_PORT = Integer.valueOf(p.get("serverport").toString().trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
