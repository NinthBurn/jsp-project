package com.webforum.lab9web.backend.model;

import java.util.Date;

public class Topic {
    private int id;
    private int userId;
    private String title;
    private String text;
    private Date uploadTime;

    public Topic(){
        this.userId = -1;
        this.title = "";
        this.text = "";
        this.uploadTime = new Date();
        this.id = -1;
    }

    public Topic(int userId, String title, String text, Date uploadDate) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.uploadTime = uploadDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
