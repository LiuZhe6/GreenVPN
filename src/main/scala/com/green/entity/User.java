package com.green.entity;

/**
 * Created by coder on 17-7-2.
 */
public class User {

    private String uid;

    private String password;

    private String token;

    public User() {
    }

    public User(String uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    public User(String uid, String password, String token) {
        this.uid = uid;
        this.password = password;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
