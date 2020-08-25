package com.ukrainer.infostroy.controller;

import com.ukrainer.infostroy.dao.LoginDao;
import com.ukrainer.infostroy.model.User;
import com.ukrainer.infostroy.socket.UserWebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int id = ((User)session.getAttribute("user")).getId();

        LoginDao loginDao = new LoginDao();
        int status = loginDao.checkStatus(id);
        System.out.println(status);

        if(status == 2){
            loginDao.statusAbsent(id);
        }

        if (session != null) {
            session.invalidate();

        }

        response.sendRedirect("/");
    }
}
