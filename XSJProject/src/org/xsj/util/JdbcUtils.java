package org.xsj.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
 
public final class JdbcUtils {
     
    
    //�������ݿ�Ĳ���
   	private static String driver ;
    private static String url ;
    private static String user;
    private static String password ;
       
    
    

	static {   
        Properties prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream("/server.properties");   
        try {   
            prop.load(in);   
            driver = prop.getProperty("jdbc.driver").trim();   
            url = prop.getProperty("jdbc.url").trim();   
            user = prop.getProperty("jdbc.user").trim();
            password = prop.getProperty("jdbc.password").trim();
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   

       
    private JdbcUtils () {
 
    }
 
    private static JdbcUtils instance = null;
 
    public static JdbcUtils getInstance() {
        if (instance == null) {
            synchronized (JdbcUtils.class) {
                if (instance == null) {
                    instance = new JdbcUtils();
                }
 
            }
        }
 
        return instance;
    }
    static {
    	
    	try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
     
//    //�����ļ�
//    private static Properties prop = new Properties();
//     
//    //ע������
//    static {
//        try {
//            //�������������ȡ�����ļ�
//            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbInfo.properties");
//            prop.load(is);
//            url = prop.getProperty("url");
//            user = prop.getProperty("user");
//            driver = prop.getProperty("driver");
//            password = prop.getProperty("password");
//             
//            Class.forName(driver);
//             
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//     
    //�÷����������
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
     
    //�ͷ���Դ
    public void free(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                  
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                          
                        e.printStackTrace();
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                  
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
     public static void main(String[] args) {
		getInstance();
	}
}
