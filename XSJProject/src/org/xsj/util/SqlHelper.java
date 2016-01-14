package org.xsj.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
 
public final class SqlHelper {
     
    //������Ҫ�ı���
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
     
    JdbcUtils jdbcUtils = JdbcUtils.getInstance();
     
    /**
     * �÷���ִ��һ��update/delete/insert���
     * @param sql
     * @param parameters
     */
    //sql����Ǵ��ʺŵĸ�ʽ���磺update table_name set column_name = ? where ...
    //parameters = {"...", "..."...}��
    public  void executeUpdate(String sql, String[] parameters) {
         
            try {
                conn = jdbcUtils.getConnection();
                ps = conn.prepareStatement(sql);
                //������ֵ
                if (parameters != null) {
                    for (int i=0; i<parameters.length; i++) {
                        ps.setString(i+1, parameters[i]);
                    }
                }
                //ִ�����
                ps.executeUpdate();
                 
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                //�ر���Դ
                jdbcUtils.free(conn, ps, rs);
            }
    }
     
    /**
     * ����ִ�ж��update��delete��insert��䣨��������
     * @param sqls
     * @param parameters
     */
    public void executeUpdate(String[] sqls, String[][] parameters) {
        try {
            //�õ�����
            conn = jdbcUtils.getConnection();
            //���sql��䣬��������
            conn.setAutoCommit(false);
             
            for (int i=0; i<sqls.length; i++) {
                if (parameters[i] != null) {
                    ps = conn.prepareStatement(sqls[i]);
                     
                    for (int j=0; j<parameters[i].length; j++) {
                        ps.setString(j+1, parameters[i][j]);
                    }
                     
                    ps.executeUpdate();
                }
 
            }
             
            conn.commit();
        } catch (SQLException e) {
            //�ع�
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcUtils.free(conn, ps, rs);
        }
    }
     
    
        /**
         * ͳһ��select��䣬Ϊ���ܹ����ʽ�����������������ArrayList����������ֱ�ӹر���Դ
         * @param sql
         * @param parameters
         * @return
         */
    public ArrayList<Object[]> executeQuery(String sql, String[] parameters) {
        ArrayList<Object[]> results = new ArrayList<Object[]>();
         
        try {
            conn = jdbcUtils.getConnection();
            ps = conn.prepareStatement(sql);
             
            if (parameters != null) {
                for (int i=0; i<parameters.length; i++) {
                    ps.setString(i+1, parameters[i]);
                }
            }
             
            rs = ps.executeQuery();
             
            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();
             
            while (rs.next()) {
                Object[] objects = new Object[column];
                 
                for (int i=1; i<=column; i++) {
                    objects[i-1] = rs.getObject(i);
                }
                 
                results.add(objects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcUtils.free(conn, ps, rs);
        }
        return results; 
    }
     
}