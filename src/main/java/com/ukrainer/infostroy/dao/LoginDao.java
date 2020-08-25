package com.ukrainer.infostroy.dao;

import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.config.DBManager;
import com.ukrainer.infostroy.model.User;

import java.sql.*;
import java.util.regex.Pattern;

public class LoginDao {
    private String email;
    private String password;
    private String role;

    public String authenticateUser(User user) throws DBException {
        String user_mail = user.getEmail();
        String user_pass = user.getPassword();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT email, password, role_id FROM users;");

            while (resultSet.next()) {
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                role = String.valueOf(resultSet.getInt("role_id")).trim();

                if (user_mail.equals(email) && user_pass.equals(password) && role.equals("0")) {
                    return "admin";
                } else if (user_mail.equals(email) && user_pass.equals(password) && role.equals("1")) {
                    return "user";
                }
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return "Invalid user credentials";
    }



    public User getUserFullInformation(String email) throws DBException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select * from users where email =?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setCity(resultSet.getString("city"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleID(resultSet.getInt("role_id"));
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return user;
    }


    /**
     * Email exist
     * @param email
     * @return
     */
    public boolean emailExist(String email){
        boolean status = false;
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select * from users where email=?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                status = true;
                System.out.println(resultSet);
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return status;
    }



    public int checkStatus(int id){
        int res = 0;

        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select user_status from users where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                res = resultSet.getInt("user_status");
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return res;
    }


    public int statusPresent(int id){
        int res = 0;
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("update users set user_status=2 where id=?");
            statement.setInt(1,id);
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return res;
    }



    public int statusAbsent(int id){
        int res = 0;
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("update users set user_status=1 where id=?");
            statement.setInt(1,id);
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return res;
    }





    /**
     * get user status by email
     * @param email
     * @return
     */
    public int getUserStatus(String email){
        int status = 0;
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select user_status from users where email=?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                status = resultSet.getInt("user_status");
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return status;
    }




    public User getUserStatusByID(int id){
        int status = 0;
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        User user = new User();

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select * from users where users_status=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setId(resultSet.getInt("name"));
                user.setId(resultSet.getInt("surname"));
                user.setId(resultSet.getInt("city"));
                user.setId(resultSet.getInt("email"));
                user.setStatus(resultSet.getInt("user_status"));
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return user;
    }





    /**
     * get Password  by email
     * @param email
     * @param password
     * @return
     */
    public String correctPassword(String email, String password){
        String checkPassword = "";
        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("select password from users where email=?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                checkPassword = resultSet.getString("password");
            }
            connection.commit();
        } catch (SQLException | DBException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection,statement,resultSet);
        }
        return checkPassword;
    }




    public boolean checkInputValue(Pattern pattern, String input){
        boolean status = false;

        if(pattern.matcher(input).matches()){
            status = true;
        } else {
            return status;
        }

        return status;
    }

}
