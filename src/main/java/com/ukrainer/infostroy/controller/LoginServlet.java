package com.ukrainer.infostroy.controller;

import com.google.gson.JsonObject;
import com.ukrainer.infostroy.Util;
import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.dao.LoginDao;
import com.ukrainer.infostroy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        JsonObject item = new JsonObject();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginDao loginDao = new LoginDao();
        Util util = new Util();
        User user = new User();

        user.setEmail(email);
        user.setPassword(password);

        String userValidate = "";

        try {
            userValidate = loginDao.authenticateUser(user);
        } catch (NullPointerException | DBException e) {
            e.getCause();
        }

        User user_test = new User();

        if (userValidate.equals("admin")) {
            System.out.println(" ---- Welcome, you're ADMIN ---- ");
            try {
                HttpSession session = request.getSession();
                session.setAttribute("user", loginDao.getUserFullInformation(email));
                user_test = loginDao.getUserFullInformation(email);
                loginDao.statusPresent(user_test.getId());
            } catch (NullPointerException | DBException e) {
                e.getCause();
            }
            util.setUrl("/student.jsp");
        } else if (userValidate.equals("user")) {
            try {
                HttpSession session = request.getSession();
                session.setAttribute("user", loginDao.getUserFullInformation(email));
                user_test = loginDao.getUserFullInformation(email);

                System.out.println("REsult -> " +loginDao.statusPresent(user_test.getId()));
            } catch (NullPointerException | DBException e) {
                e.getCause();
            }
            util.setUrl("/usertable.jsp");
        }


        item.addProperty("url", util.getUrl());
        writer.print(item.toString());
    }


}
