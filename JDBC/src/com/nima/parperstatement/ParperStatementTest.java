package com.nima.parperstatement;

import com.nima.connection.ConnectionTest;
import com.nima.utils.JDBCUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

//对数据库 增删改,  查(有返回）
public class ParperStatementTest {

    //通用的增删改操作
    @Test
    public void testCommonUpdate(){
//        String sql = "delete from customers where id = ?";
        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql,"DD",2);

    }



    //向数据库中增删改
    public void update(String sql,Object ... args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0;i < args.length;i++){
                ps.setObject(i + 1,args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,ps);
        }

    }


    //添加数据
    @Test
    public void testInsert() throws Exception{
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driveClass = pros.getProperty("driverClass");

            Class.forName(driveClass);

            conn = DriverManager.getConnection(url, user, password);

            System.out.println(conn );

            String sql = "insert into customers(name,email,birth)values(?,?,?)";

            ps = conn.prepareStatement(sql);

            ps.setString(1,"nima");
            ps.setString(2,"123444@qq.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date data = simpleDateFormat.parse("2000-01-01");

            ps.setDate(3, new Date(data.getTime()));

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
    }

    @Test
    public void testUpdate(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1获取数据库连接
            conn = JDBCUtils.getConnection();

            //2编织sql语句，返回preparedStatement的实例

            String sql = "update customers set name = ? where id = ?";

            ps = conn.prepareStatement(sql);

            //3填充占位符
            ps.setObject(1,"nima2");
            ps.setObject(2,20);

            //4执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //5资源的关闭
            JDBCUtils.closeResource(conn,ps);
        }

    }
}
