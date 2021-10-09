package com.nima.exer;

import com.nima.bean.Student;
import com.nima.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
//
//    public static void main(String[] args) throws ParseException {
//        Scanner sc = new Scanner(System.in);
//
//        String sql = "insert into `order`(order_id,order_name,order_date) values (?,?,?)";
//        System.out.print("请输入姓名");
//
//        String name = sc.next();
//        System.out.print("请输入id");
//
//        int id = sc.nextInt();
//
//        System.out.print("请输入生日 格式1999-02-23");
//
//        String da = sc.next();
//
//
//        update(sql,id,name,da);
//
//    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("请选择您查询的类型");
        System.out.println("a:准考证号");
        System.out.println("b:身份证号");
        String choise = sc.next();
        String res;
        String chooseType;
        String sql;
        if (choise.equals("a")){
            chooseType = "ExamCard";
            System.out.println("请输入准考证号：");
            res = sc.next();
            sql = "select Type type,IDCard idCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from " +
                    "examStudent where ExamCard = ?";
        }else if (choise.equals("b")){
            chooseType = "IDCard";
            System.out.println("请输入身份证号：");
            res = sc.next();
            sql = "select Type type,IDCard idCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from " +
                    "examStudent where IDCard = ?";
        }else {
            System.out.println("输入不合法！");
            sc.close();
            return;
        }
        List<Student> list = getForTestList(Student.class, sql,res);
        System.out.println("=============查询结果==============");
        System.out.println(list.);

    }

    public static int update(String sql,Object ... args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0;i < args.length;i++){
                ps.setObject(i + 1,args[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,ps);
        }
        return 0;

    }

    public static <T>List<T> getForTestList(Class<T> clazz,String sql,Object ...args) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            ArrayList<T> list = new ArrayList<>();

            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
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
}
