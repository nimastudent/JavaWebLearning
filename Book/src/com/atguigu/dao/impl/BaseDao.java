package com.atguigu.dao.impl;

import com.atguigu.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

public abstract class BaseDao {
    //使用Dbuitls
    private QueryRunner queryRunner = new QueryRunner();


    //返回-1表示失败
    public int update(String sql,Object ... args){
        Connection connection = JdbcUtils.getConnection();
        try {
           return queryRunner.update(connection,sql,connection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return -1;
    }

    /*
        查询返回一个Javabean的sql语句
        @param type 返回的对象类型
        @param sql 执行的SQL语句
        @param args sql对应的参数值
        @param <t> 返回的类型的泛型
        @return
     */

    public <T>T queryForOne(Class<T> type,String sql,Object ... args){
        Connection con = JdbcUtils.getConnection();
        try {
            queryRunner.query(con,sql,new BeanHandler<T>(type),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(con);
        }
        return  null;
    }


    public <T>List<T> queryForList(Class<T> type,String sql,Object ... args){
        Connection con = JdbcUtils.getConnection();
        try {
            queryRunner.query(con,sql,new BeanHandler<T>(type),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(con);
        }
        return  null;
    }
    /*
    执行返回一列的sql语句


     */

    public Object queryForSingleValue(String sql,Object ... args){
        Connection conn = JdbcUtils.getConnection();

        try{
         return queryRunner.query(conn,sql,new ScalarHandler(),args);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
