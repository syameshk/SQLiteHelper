package com.syam.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 30/05/17.
 */

public class User {



    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    //@SerializedName("countryData")
    //private CountryData countryData;

    @SerializedName("name")
    private String name;

    private String displayName;

    @SerializedName("userType")
    private String userType;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("pass")
    private String password;

    @SerializedName("accessToken")
    private String accessToken;

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String phone, String name, String userType) {
        this.phone = phone;
        this.name = name;
        this.userType = userType;

        this.userId = 0;
        this.displayName = name;
    }

    public User(String imgUrl, String phone, String email, String name, String displayName, String userType, Integer userId) {
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.displayName = displayName;
        this.userType = userType;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.phone.equals(((User)obj).getPhone());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
