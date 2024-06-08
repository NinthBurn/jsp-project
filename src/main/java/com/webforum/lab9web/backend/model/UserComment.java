package com.webforum.lab9web.backend.model;

import java.time.LocalDateTime;
import java.util.Date;

public class UserComment {
    private int id;
    private int userId;
    private int topicId;
    private String text;
    private Date uploadTime;

    public UserComment() {
        this.userId = -1;
        this.topicId = -1;
        this.text = "";
        this.uploadTime = new Date();
        this.id = -1;
    }

    public UserComment(int userId, int topicId, String text, Date uploadTime) {
        this.userId = userId;
        this.topicId = topicId;
        this.text = text;
        this.uploadTime = uploadTime;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
