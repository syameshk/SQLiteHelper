package com.syam.models;

/**
 * Created by shubham on 31/05/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ARContent {

    @SerializedName("itemId")
    private Integer itemId;
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("customizationData")
    private String customizationData;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("assetBundleUrl")
    private String assetBundleUrl;
    @SerializedName("categories")
    private List<ARCategory> categories = null;
    @SerializedName("itemDesc")
    private String itemDesc;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCustomizationData() {
        return customizationData;
    }

    public void setCustomizationData(String customizationData) {
        this.customizationData = customizationData;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAssetBundleUrl() {
        return assetBundleUrl;
    }

    public void setAssetBundleUrl(String assetBundleUrl) {
        this.assetBundleUrl = assetBundleUrl;
    }

    public List<ARCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ARCategory> categories) {
        this.categories = categories;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

}