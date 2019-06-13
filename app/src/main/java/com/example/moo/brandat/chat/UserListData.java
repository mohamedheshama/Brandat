package com.example.moo.brandat.chat;

public class UserListData  {
    private String name, userId,photoUrl;

    public  UserListData(){

    }
    public UserListData(String name,String userId,String photoUrl){
        this.name=name;
        this.userId=userId;
        this.photoUrl=photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
