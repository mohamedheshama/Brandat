package com.example.moo.brandat.chat;

public class UserListData  {
    private String name;
    private String userId;
    private String img_url;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public  UserListData(){

    }
    public UserListData(String name,String userId,String img_url){
        this.name=name;
        this.userId=userId;
        this.img_url=img_url;
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

    public String getImg_Url() {
        return img_url;
    }

    public void setImg_Url(String photoUrl) {
        this.img_url = photoUrl;
    }
}
