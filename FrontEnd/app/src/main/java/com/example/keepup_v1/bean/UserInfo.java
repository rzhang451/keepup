package com.example.keepup_v1.bean;

public class UserInfo {
    private static String userId;
    private String email;
    private String pwd;

    public UserInfo(String email,String pwd,String userId){
        this.email=email;
        this.pwd=pwd;
        this.userId=userId;
    }
    public static String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}