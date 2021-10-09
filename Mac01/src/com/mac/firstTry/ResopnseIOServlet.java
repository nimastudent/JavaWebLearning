package com.mac.firstTry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResopnseIOServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type","text/html; charset=UTF-8");

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = re sp.getWriter();
        writer.write("这是一段中文!!");

    }
}
