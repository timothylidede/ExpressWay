package com.savala.expressway.model;

public class User {
    private String first_name, last_name, email, userID;

    public User(){}

    public User(String first_name, String last_name, String email, String userID) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.userID = userID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
