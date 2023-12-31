package com.raven.model;

public class Model_User_Account {

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Model_User_Account(int userID, String userName, String gender, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.status = status;
    }

    public Model_User_Account() {
    }

    private int userID;
    private String userName;
    private String gender;
    private boolean status;
}
