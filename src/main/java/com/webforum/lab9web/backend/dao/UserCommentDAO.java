package com.webforum.lab9web.backend.dao;

import com.webforum.lab9web.backend.model.Topic;
import com.webforum.lab9web.backend.model.UserComment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCommentDAO {
    public int registerComment(UserComment comment) throws ClassNotFoundException, SQLException {
        String insert_sql = "INSERT INTO Comments (user_id, topic_id, comment_text, comment_date) VALUES (?, ?, ?, ?)";

        int result = 0;
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");


        PreparedStatement command = connection.prepareStatement(insert_sql);
        command.setInt(1, comment.getUserId());
        command.setInt(2, comment.getTopicId());
        command.setString(3, comment.getText());
        command.setTimestamp(4, new Timestamp(comment.getUploadTime().getTime()));

        System.out.println(command);

        result = command.executeUpdate();

        return result;
    }

    public UserComment getComment(int commentId) throws ClassNotFoundException, SQLException {
        UserComment comment = new UserComment();

        String query_sql = "SELECT * FROM Comments WHERE comment_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, commentId);

        System.out.println(command);

        ResultSet rs = command.executeQuery();

        if(rs.next()){
            comment.setId(commentId);
            comment.setUserId(rs.getInt("user_id"));
            comment.setTopicId(rs.getInt("topic_id"));
            comment.setText(rs.getString("comment_text"));
            comment.setUploadTime(new java.util.Date(rs.getTimestamp("comment_date").getTime()));
        }

        return comment;
    }

    public List<UserComment> getAllCommentsByTopic(int topicId) throws ClassNotFoundException, SQLException {
        List<UserComment> comments = new ArrayList<>();
        String query_sql = "SELECT * FROM Comments WHERE topic_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, topicId);
        System.out.println(command);

        ResultSet rs = command.executeQuery();

        while(rs.next()){
            UserComment comment = new UserComment();
            comment.setId(rs.getInt("comment_id"));
            comment.setUserId(rs.getInt("user_id"));
            comment.setTopicId(rs.getInt("topic_id"));
            comment.setText(rs.getString("comment_text"));
            comment.setUploadTime(new java.util.Date(rs.getTimestamp("comment_date").getTime()));
            comments.add(comment);
        }

        return comments;
    }
    public void updateComment(UserComment comment) throws ClassNotFoundException, SQLException {
        String query_sql = "UPDATE Comments " +
                "SET user_id = ?, topic_id = ?, comment_text = ?, comment_date = ? " +
                "WHERE comment_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, comment.getUserId());
        command.setInt(2, comment.getTopicId());
        command.setString(3, comment.getText());;
        command.setTimestamp(4, new Timestamp(comment.getUploadTime().getTime()));
        command.setInt(5, comment.getId());

        System.out.println(command);

        int result = command.executeUpdate();
    }

    public void deleteComment(int commentId) throws ClassNotFoundException, SQLException {
        String query_sql = "DELETE FROM Comments WHERE comment_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, commentId);

        System.out.println(command);

        int result = command.executeUpdate();
    }
}