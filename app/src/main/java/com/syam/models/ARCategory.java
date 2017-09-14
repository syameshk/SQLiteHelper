package com.syam.models;

/**
 * Created by shubham on 31/05/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ARCategory {

    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("categoryId")
    private Integer categoryId;
    @SerializedName("categoryDesc")
    private String categoryDesc;
    @SerializedName("items")
    private List<ARContent> arContents = null;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public List<ARContent> getArContents() {
        return arContents;
    }

    public void setArContents(List<ARContent> arContents) {
        this.arContents = arContents;
    }
}