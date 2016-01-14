package org.xsj.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Constants {

	public static   String SERVER_PORT ;

	public  static  String IP_ADDR  ;

	public static final String LOGIN_TAG = "L";

	public static final String MSG_TAG = "M";

	public static final String LOGIN_FALSE_R = "FALSE_R";

	public static final String LOGIN_SUCCESS_R = "SUCCESS_R";
	
	public static int i = 0 ;
	
	

	static {   
        Properties prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream("/server.properties");   
        try {   
            prop.load(in);   
            SERVER_PORT = prop.getProperty("port").trim();   
            IP_ADDR = prop.getProperty("ip").trim();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   


}
