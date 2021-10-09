package com.atguigu.dao;

import com.atguigu.pojo.User;

public interface UserDao {


    /*
    根据用户名查询
    如果返回null 没这人
     */
    public User queryUsername(String username);

    public User queryUserByPasswordUsername(String username,String password);

    public int saveUser(User user);


}
