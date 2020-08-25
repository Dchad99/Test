package com.ukrainer.infostroy.controller;

import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.dao.TableDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/StudentTableServlet")
public class StudentTableServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TableDao tableDao = new TableDao();

        String search = request.getParameter("filters[search]");
        int pageNum = Integer.parseInt(request.getParameter("filters[pageNum]"));
        String sortColumn = request.getParameter("sort[col]");
        String sortDirection = request.getParameter("sort[dir]");
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));


        String searchSQL = tableDao.getSearchItems(search);
        String sort = tableDao.getSortDir(sortDirection, sortColumn);
        String pagination = tableDao.getPaginationLimit(pageNum, currentPage);

        String sql = "SELECT id, name, surname, city, email, user_status FROM users where role_id = 1 ";

        if(!"".equals(searchSQL)){
            sql += searchSQL + sort;
        }else{
            sql += sort + pagination;
        }


        try {
            tableDao.displayTable(sql, request, response);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
