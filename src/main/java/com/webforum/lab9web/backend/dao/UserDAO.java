package com.webforum.lab9web.backend.dao;

import java.sql.*;

import com.webforum.lab9web.backend.model.User;

public class UserDAO {
    public int registerUser(User user) throws ClassNotFoundException, SQLException {
        String insert_sql = "INSERT INTO Users (user_name, user_email, user_password) VALUES (?, ?, ?)";

        int result = 0;
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(insert_sql);
        command.setString(1, user.getUserName());
        command.setString(2, user.getEmail());
        command.setString(3, user.getPassword());

        System.out.println(command);

        result = command.executeUpdate();

        return result;
    }

    public void loginUser(User user) throws ClassNotFoundException, SQLException {
        String query_sql = "SELECT * FROM Users WHERE user_email = ? AND user_password = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setString(1, user.getEmail());
        command.setString(2, user.getPassword());

        System.out.println(command);

        ResultSet rs = command.executeQuery();

        if(rs.next()){
            user.setId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
        }
    }

    public User getUser(int userId) throws ClassNotFoundException, SQLException {
        User user = new User();
        String query_sql = "SELECT * FROM Users WHERE user_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, userId);

        System.out.println(command);

        ResultSet rs = command.executeQuery();

        if(rs.next()){
            user.setId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("user_password"));
            user.setEmail(rs.getString("user_email"));
        }

        return user;
    }
}
