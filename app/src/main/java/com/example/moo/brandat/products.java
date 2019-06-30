package com.example.moo.brandat;

import android.widget.EditText;

public class products {
    String  likes="0";
    public products() {

    }
    String imagesrc;

    public products(String fname, String category, String product_case, String cost, String product_des, String location, String ownername, String phone, String email, String imagesrc,String img_url,String user_id,String product_key,String quantity,String time,String  likes) {
        this.likes=likes;
        this.product_name = fname;
        this.category = category;
        this.product_case = product_case;
        this.cost = cost;
        this.product_des = product_des;
        this.location = location;
        this.ownername = ownername;
        this.phone = phone;
        this.email = email;
        this.imagesrc=imagesrc;
        this.img_url=img_url;
        this.user_id=user_id;
        this.product_key=product_key;
        this.quantity=quantity;
        this.time=time;

    }

    public String getFname() {
        return product_name;
    }

    public void setFname(String fname) {
        this.product_name = fname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getproduct_case() {
        return product_case;
    }

    public void setproduct_case(String product_case) {
        this.product_case = product_case;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getproduct_des() {
        return product_des;
    }

    public void setproduct_des(String pdescribe) {
        this.product_des = pdescribe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String product_name;String category;String product_case;String cost;String product_des;String location;String ownername;String phone;String email;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String quantity;String time;

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    String product_key;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String user_id;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    String img_url;

    public String getImgesrc() {
        return imagesrc;
    }

    public void setImgesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }








}
