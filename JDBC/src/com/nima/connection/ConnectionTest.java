package com.nima.connection;


import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
//方式一
    @Test
    public void testConnection1() throws SQLException {
    //获取driver实现类对象
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPulicKeyRetrieval=true&serverTimerzone=UTC";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","mlx010123");

        Connection conn = driver.connect(url,info);

        System.out.println(conn);
    }

    //方式二：无第三方接口
    @Test
    public void testConnection2() throws Exception {
        //反射
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPulicKeyRetrieval=true&serverTimerzone=UTC";

        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","mlx010123");

        Connection conn = driver.connect(url,info);
        System.out.println(conn);
    }

    //方式三：drivermanagement替换Drive
    @Test
    public void testConnection3()throws Exception{
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPulicKeyRetrieval=true&serverTimerzone=UTC";
        String user = "root";
        String password = "mlx010123";


        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //方式四：
    @Test
    public void testConnection4()throws Exception{
        //连接信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPulicKeyRetrieval=true&serverTimerzone=UTC";
        String user = "root";
        String password = "mlx010123";
        //加载驱动  注册驱动 mysql替你做了
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Driver driver = (Driver) clazz.newInstance();
//        DriverManager.registerDriver(driver);
        //连接数据库
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //方式五：将数据库需要的配置文件 声明在配置文件中
    @Test
    public void testConnection5() throws Exception{
        //读配置文件中的信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driveClass = pros.getProperty("driverClass");

        Class.forName(driveClass);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

}
