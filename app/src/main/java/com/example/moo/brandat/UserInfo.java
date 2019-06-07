package com.example.moo.brandat;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.List;

public class UserInfo {
    private String name;
    private String photoUrl;
   private String email;
    private String confirmpass;
    private String phone;
    private String location;
    private Boolean isShop;
    private List<products> userProducts;
    public UserInfo(String name, String photoUrl, String email, String confirmpass, String phone, String location, Boolean isShop, List<products> userProducts) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
        this.confirmpass = confirmpass;
        this.phone = phone;
        this.location = location;
        this.isShop = isShop;
        this.userProducts = userProducts;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmpass() {
        return confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getShop() {
        return isShop;
    }

    public void setShop(Boolean shop) {
        isShop = shop;
    }

    public List<products> getUserProducts() {
        return userProducts;
    }

    public void setUserProducts(List<products> userProducts) {
        this.userProducts = userProducts;
    }




    public UserInfo() {

    }
}



