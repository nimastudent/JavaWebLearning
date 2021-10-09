package com.nima.exer;

import com.nima.utils.JDBCUtils;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer2{


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
        System.out.println("请输入type");
        int type = sc.nextInt();
        System.out.println("请输入身份证");
        String IDCard = sc.next();
        System.out.println("请输入考生号");
        String examCard = sc.next();
        System.out.println("请输入学生姓名");
        String studentName = sc.next();
        System.out.println("请输入地址");
        String locatioin = sc.next();
        System.out.println("请输入成绩");
        int grade = sc.nextInt();



        String res = update(sql,type,IDCard,examCard,studentName,locatioin,grade) > 0 ? "信息录入成功！" : "失败";
        System.out.println(res);
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
}
