package com.mac.firstTry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ParameterServler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doget!");
        String name =  req.getParameter("username ");
        String password = req.getParameter("password");
        System.out.printf("namd:"+name+";"+"password:"+password);
        String[] hobby = req.getParameterValues("hobby");
        System.out.println("兴趣爱好"+ Arrays.asList(hobby));
        System.out.println("执行了get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");  //获取请求前使用不然会乱码
        System.out.println("dopost!!!");
        String name =  req.getParameter("username");
        String password = req.getParameter("password");
        System.out.printf("namd:"+name+";"+"password:"+password);
        String[] hobby = req.getParameterValues("hobby");
        System.out.println("兴趣爱好"+ Arrays.asList(hobby));
        System.out.println("执行了post请求");
    }
}
