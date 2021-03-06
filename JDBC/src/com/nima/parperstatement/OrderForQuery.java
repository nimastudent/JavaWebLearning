package com.nima.parperstatement;

import com.nima.bean.Order;
import com.nima.utils.JDBCUtils;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class OrderForQuery {

    @Test
    public void testOrderForQuery(){
//        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
//        Order order = orderForQuery(sql, 1);

        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order`";
        Order order = orderForQuery(sql);
        System.out.println(order);
    }

    public Order orderForQuery(String sql,Object ... args){
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
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);

//                    rsmd.getColumnName()  获取列名
//                    rsmd.getColumnLabel()   获取别名 没有别名获取列名 ！！！推荐使用这个
                    String columnName = rsmd.getColumnLabel(i + 1);

                    Field field = Order.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(order,columValue);

                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);

        }

        return null;
    }

    @Test
    public void testQuery1()throws Exception{
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();

            if (rs.next()){
               int id = (int) rs.getObject(1);
               String name = (String) rs.getObject(2);
               Date date = (Date) rs.getObject(3);

               Order order = new Order(id,name,date);
                System.out.println(order.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);

        }


    }
}
