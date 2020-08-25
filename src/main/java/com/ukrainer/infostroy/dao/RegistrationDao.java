package com.ukrainer.infostroy.dao;

import com.ukrainer.infostroy.config.DBException;
import com.ukrainer.infostroy.config.DBManager;
import com.ukrainer.infostroy.model.User;

import java.sql.*;
import java.util.regex.Pattern;

public class RegistrationDao {
    public int registerUser(User user) throws DBException {
        int res = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement("INSERT INTO users (name, surname, city, email, password) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getCity());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

            res = statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            connection.commit();
        } catch (SQLException | DBException e) {
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return res;
    }


    /**
     * isUserExist by email
     * @param email
     * @return
     * @throws DBException
     */
    public boolean isUserExist(String email) throws DBException, SQLException {
        boolean status = false;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBManager.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("select * from users where email=?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status = true;

            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBManager.rollback(connection);
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return status;
    }


    /**
     * Input is not null
     * @param str
     * @return
     */
    public boolean IsNUll(String str){
        boolean res = false;
        if(!str.equals("")){
            res = true;
        }
        return res;
    }

    /**
     * Check input value on regex
     * @param pattern
     * @param input
     * @return
     */
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
