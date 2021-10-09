package com.nima.parperstatement;

import com.nima.bean.Order;
import com.nima.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementQueryTest {

    @Test
    public void testGetForList(){
//        String sql = "select id,name,email from customers where id < ?";
//        List<Customer> forList = getForList(Customer.class, sql,12);
//        forList.forEach(System.out::println);

        String sql2 = "select order_id orderId,order_name orderName from `order` where order_id < ?";
        List<Order> list = getForList(Order.class,sql2,5);
        System.out.println(list.toString());
    }

    public <T>List<T> getForList(Class<T> clazz, String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0;i < args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            ArrayList<T> list = new ArrayList<>();

            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);

//                    rsmd.getColumnName()  获取列名
//                    rsmd.getColumnLabel()   获取别名 没有别名获取列名 ！！！推荐使用这个
                    String columnName = rsmd.getColumnLabel(i + 1);

                    //t.getClass()
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columValue);

                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }


    @Test
    public void testGetInstance(){
//        String sql = "select id,name,email from customers where id = ?";
//        Customer instance = getInstance(Customer.class, sql, 2);
//        System.out.println(instance);

        String slq2 = "select order_id orderId,order_name orderName from `order` where order_id = ?";
        Order order = getInstance(Order.class, slq2, 2);
        System.out.println(order);
    }

    //针对不同表的查询
    public <T>T getInstance(Class<T> clazz, String sql,Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0;i < args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);

//                    rsmd.getColumnName()  获取列名
//                    rsmd.getColumnLabel()   获取别名 没有别名获取列名 ！！！推荐使用这个
                    String columnName = rsmd.getColumnLabel(i + 1);

                                  //t.getClass()
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columValue);

                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);

        }

        return null;
    }




}