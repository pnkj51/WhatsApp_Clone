package com.example.mywhatsapp.Models;

public class Users {

    String profilepic, username,mail,password,userId,lastMessage;

    public Users(String profilepic, String username, String mail, String password, String userId, String lastMessage) {
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    //for firebase
    public Users(){}

    //sign up
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
        }


    public String getProfilepic() {
        return profilepic;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId(String key) {
        return userId;
    }
    public String getUserId() {
        return userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
