package com.syam.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 30/05/17.
 */

public class ResponseUser {

    @SerializedName("status")
    boolean isSuccessful;

    @SerializedName("message")
    String message;

    @SerializedName("userData")
    User user;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
