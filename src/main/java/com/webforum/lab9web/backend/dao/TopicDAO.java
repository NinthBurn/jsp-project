package com.webforum.lab9web.backend.dao;

import com.webforum.lab9web.backend.model.Topic;
import com.webforum.lab9web.backend.model.User;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    public int registerTopic(Topic topic) throws ClassNotFoundException, SQLException {
        String insert_sql = "INSERT INTO Topics (user_id, topic_date, topic_title, topic_text) VALUES (?, ?, ?, ?)";

        int result = 0;
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");


        PreparedStatement command = connection.prepareStatement(insert_sql);
        command.setInt(1, topic.getUserId());
        command.setTimestamp(2, new Timestamp(topic.getUploadTime().getTime()));
        command.setString(3, topic.getTitle());
        command.setString(4, topic.getText());

        System.out.println(command);

        result = command.executeUpdate();

        return result;
    }

    public List<Topic> getAllTopics() throws ClassNotFoundException, SQLException {
        List<Topic> topics = new ArrayList<>();
        String query_sql = "SELECT * FROM Topics ORDER BY topic_date DESC";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);

        System.out.println(command);

        ResultSet rs = command.executeQuery();

        while(rs.next()){
            Topic topic = new Topic();
            topic.setId(rs.getInt("topic_id"));
            topic.setUserId(rs.getInt("user_id"));
            topic.setText(rs.getString("topic_text"));
            topic.setTitle(rs.getString("topic_title"));
            topic.setUploadTime(new java.util.Date(rs.getTimestamp("topic_date").getTime()));
            topics.add(topic);
        }

        return topics;
    }

    public Topic getTopic(int topicId) throws ClassNotFoundException, SQLException {
        Topic topic = new Topic();

        String query_sql = "SELECT * FROM Topics WHERE topic_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, topicId);

        System.out.println(command);

        ResultSet rs = command.executeQuery();

        if(rs.next()){
            topic.setId(topicId);
            topic.setUserId(rs.getInt("user_id"));
            topic.setText(rs.getString("topic_text"));
            topic.setTitle(rs.getString("topic_title"));
            topic.setUploadTime(new java.util.Date(rs.getTimestamp("topic_date").getTime()));
        }

        return topic;
    }

    public void updateTopic(Topic topic) throws ClassNotFoundException, SQLException {
        String query_sql = "UPDATE Topics " +
                "SET user_id = ?, topic_text = ?, topic_title = ?, topic_date = ? " +
                "WHERE topic_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, topic.getUserId());
        command.setString(2, topic.getText());;
        command.setString(3, topic.getTitle());
        command.setTimestamp(4, new Timestamp(topic.getUploadTime().getTime()));
        command.setInt(5, topic.getId());

        System.out.println(command);

        int result = command.executeUpdate();
    }

    public void deleteTopic(int topicId) throws ClassNotFoundException, SQLException {
        String query_sql = "DELETE FROM Topics WHERE topic_id = ?";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/lab9?useSSL=false", "root", "");

        PreparedStatement command = connection.prepareStatement(query_sql);
        command.setInt(1, topicId);

        System.out.println(command);

        int result = command.executeUpdate();
    }
}
