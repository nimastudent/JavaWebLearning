package com.nima.blob;

/*
使用perparstatement批量插入数据
update 和 delete 本身有批量操作
 */

import com.nima.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class InsertTest {

    @Test
    public void insertIntoGoodsStatement(){
        Connection conn = null;
        Statement st = null;
        try {
            conn = JDBCUtils.getConnection();
            st = conn.createStatement();
//            for (int i = 0;i < 20000;i++){
                String sql = "insert into goods(name)values(lalalalala)";
                st.execute(sql);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,st);
        }
    }

    /*
     conn.setAutoCommit = false //取消自动提交 一次性提交bash里的操作 最快！
     */

    @Test
    public  void insertIntoGoodsPreparment(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);

            for (int i = 1; i < 40000; i++) {
                ps.setObject(1,"name_"+i);
                ps.addBatch();

                if (i % 500 == 0){
                    ps.executeBatch();
                    ps.clearBatch();
                }

                if (i == 39999){
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }
            long end = System.currentTimeMillis();
            System.out.println("插入操作时间为:"+(start-end));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }




    }

}
