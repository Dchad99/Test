package com.ukrainer.infostroy.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.config.DBManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class TableDao {


    public int getEntriesSize() throws DBException {
        int res = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM users;");

            while (resultSet.next()) {
                res = resultSet.getInt("total");
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.getCause();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return res;
    }



    public String getSearchItems(String search) {
        String searchRes = "";

        if (!"".equals(search)) {
            return " and id LIKE \"%" + search.trim() + "%\" " +
                    " OR name LIKE \"%" + search.trim() + "%\" " +
                    " OR surname LIKE \"%" + search.trim() + "%\" " +
                    " OR city LIKE \"%" + search.trim() + "%\" " +
                    " OR email LIKE \"%" + search.trim() + "%\" ";
        } else {
            return searchRes;
        }
    }



    public String getSortDir(String dir, String column) {
        String searchRes = "";

        if (dir.equals("none")) {
            return searchRes;
        } else {
            if (!"".equals(dir) && !"".equals(column)) {
                return " ORDER BY " + column + " " + dir;
            } else {
                return searchRes;
            }
        }
    }



    public String getPaginationLimit(int pageSize, int currentPage) {
        String pagination = "";

        currentPage = (currentPage - 1) * pageSize;
        pagination += " LIMIT " + currentPage + ", " + pageSize;

        return pagination;
    }






    public void displayTable(String sql, HttpServletRequest request, HttpServletResponse response) throws IOException, DBException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        Statement statement = null;
        ResultSet rs = null;
        Connection c = null;

        try {
            c = DBManager.getInstance().getConnection();
            statement = c.createStatement();
            rs = statement.executeQuery(sql);

            System.out.println("SQL: " + sql);

            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();


            while (rs.next()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("ID", rs.getInt("id"));
                obj.addProperty("Name", rs.getString("name"));
                obj.addProperty("Surname", rs.getString("surname"));
                obj.addProperty("City", rs.getString("city"));
                obj.addProperty("Email", rs.getString("email"));
                obj.addProperty("Status", getUserStatusByID(rs.getInt("user_status")));

                jsonArray.add(obj);
            }

            int size = getStudentQuntity();

            jsonObject.addProperty("totalEntries", size);
            jsonObject.addProperty("entries", jsonArray.toString());
            writer.print(jsonObject.toString());
            c.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(c);
        } finally {
            DBManager.close(c, statement, rs);
        }
    }


    public static String getAllRows() throws IOException {
        Statement statement = null;
        ResultSet rs = null;
        Connection c = null;

        String sql = "SELECT id, name, surname, city, email, user_status FROM users where role_id = 1 ";

        String str = "";
        try {
            c = DBManager.getInstance().getConnection();
            statement = c.createStatement();
            rs = statement.executeQuery(sql);

            System.out.println("SQL: " + sql);
            JsonArray jsonArray = new JsonArray();

            while (rs.next()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("ID", rs.getInt("id"));
                obj.addProperty("Name", rs.getString("name"));
                obj.addProperty("Surname", rs.getString("surname"));
                obj.addProperty("City", rs.getString("city"));
                obj.addProperty("Email", rs.getString("email"));
                obj.addProperty("Status", getUserStatusByID(rs.getInt("user_status")));

                jsonArray.add(obj);
            }

           str = jsonArray.toString();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(c);
        } finally {
            DBManager.close(c, statement, rs);
        }
        return str;
    }

    public static String getUserStatusByID(int id) {
        String res = "";
        Connection connection  =null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT status FROM users_status WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res = resultSet.getString("status");
            }
        } catch (SQLException | DBException e) {
            e.getCause();
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return res;
    }


    public int getStudentQuntity() throws DBException {
        int res = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM users;");

            while (resultSet.next()) {
                res = resultSet.getInt("total");
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.getCause();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return res;
    }

}
