package com.nima.blob;

import com.nima.bean.Customer;
import com.nima.utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class BolbTest {

    //向数据表customers中插入Bolb类型
    @Test
    public void testInsert(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
            ps = conn.prepareStatement(sql);

            ps.setObject(1,"newTest");
            ps.setObject(2,"newtest@qq.com");
            ps.setObject(3,"2000-01-23");
            FileInputStream is = new FileInputStream(new File(""));
            ps.setBlob(4,is);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);

        }

    }


    //查询
    @Test
    public void testQuery(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,19);

            rs = ps.executeQuery();

            if (rs.next()){
//                int id = rs.getInt(1);
//                String name = rs.getString(2);
//                String email = rs.getString(3);
//                Date birth = rs.getDate(4);
//                Blob blob = rs.getBlob(5);

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customer cust = new Customer(id, name, email, birth);
                System.out.println(cust);

                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("fengding.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
