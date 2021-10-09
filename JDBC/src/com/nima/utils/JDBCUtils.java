package com.nima.utils;

import com.nima.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *
 *
 */

public class JDBCUtils {

    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driveClass = pros.getProperty("driverClass");

        Class.forName(driveClass);

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    public static void closeResource(Connection conn, Statement ps){
        try {
            if (ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeResource(Connection conn, Statement ps,ResultSet rs){
        try {
            if (ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            if (rs != null){
                rs.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
