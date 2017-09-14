package com.syam.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 30/05/17.
 */

public class CommonResponse {
    @SerializedName("status")
    boolean isSuccessful;

    @SerializedName("message")
    String message;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
