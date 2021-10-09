package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void queryUsername() {
        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.queryUsername("test"));
    }

    @Test
    void queryUserByPasswordUsername() {
    }

    @Test
    void saveUser() {
    }
}