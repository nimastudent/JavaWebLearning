package com.nima.parperstatement;


import com.nima.bean.Customer;
import com.nima.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomerForQuery {
//    private String name;

    @Test
    public void testQueryFor(){
        String sql = "select id,name,birth,email from customers where id = ?";
        String sql2 = "select name,email from customers where name = ?";
        Customer customer = queryForCommon(sql2, "周杰伦");
        System.out.println(customer.toString());
    }


    public Customer queryForCommon(String sql,Object ... args){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            for(int i = 0;i <args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                Customer cus = new Customer();

                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);

                    String columnName = rsmd.getColumnName(i + 1);

                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(cus,value);
                }
                return cus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void CustomersQuery() throws Exception{

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

    //            Object[] data = new Object[] {id,name,email,birth};
                Customer cs1 = new Customer(id, name, email, birth);
                System.out.println(cs1.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,resultSet);
        }

    }

}
