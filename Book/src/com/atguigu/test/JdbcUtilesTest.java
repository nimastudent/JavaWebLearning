package com.atguigu.test;

import com.atguigu.utils.JdbcUtils;
import org.junit.jupiter.api.Test;


public class JdbcUtilesTest {
    @Test
    public void testJdbcUtils(){
        System.out.println(JdbcUtils.getConnection());
    }
}
