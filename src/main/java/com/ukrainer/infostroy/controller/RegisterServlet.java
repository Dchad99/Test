package com.ukrainer.infostroy.controller;

import com.google.gson.JsonObject;
import com.ukrainer.infostroy.Util;
import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.dao.RegistrationDao;
import com.ukrainer.infostroy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.ukrainer.infostroy.Source.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        JsonObject message = new JsonObject();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String password = request.getParameter("password");

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setCity(city);
        user.setPassword(password);


        RegistrationDao registrationDao = new RegistrationDao();
        Util util = new Util();


        if(!name.isEmpty() && !surname.isEmpty() && !city.isEmpty() && !password.isEmpty()){
            try {
                if (registrationDao.checkInputValue(EMAIL_PATTERN, email)) {
                    user.setEmail(email);
                    message.addProperty("message", INCORRECT_PASS);
                    if (registrationDao.isUserExist(user.getEmail())) {
                        message.addProperty("message", USER_ALREADY_EXIST);
                        System.out.println("ALready exist");
                    } else {
                        registrationDao.registerUser(user);
                        message.addProperty("message", SUCCESS_REGISTRATION);
                        System.out.println("Registered");
                    }

                } else {
                    message.addProperty("message", INCORRECT_EMAIL);
                }

            } catch (SQLException | DBException e) {
                e.printStackTrace();

            }
        } else{
            message.addProperty("message", NULL);
        }

        writer.print(message.toString());
    }

}
