package com.example.focus_on.user;

public class User {
    private String eMail;
    private String phoneNumber;
    private String userName;
    private String password;
    private String key;

    public User() {
    }

    public User(String eMail, String phoneNumber, String userName, String password, String key) {
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.key = key;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
