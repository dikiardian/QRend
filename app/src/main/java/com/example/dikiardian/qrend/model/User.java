package com.example.dikiardian.qrend.model;

/**
 * Created by Diki on 4/23/2018.
 */

public class User {
    private int userId;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private Event[] events;

    public User(int userId, String username, String fullname, String email, String password, Event[] events) {
        this.userId = userId;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.events = events;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
