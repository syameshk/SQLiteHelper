package com.syam.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 30/05/17.
 */

public class SMSGatewayResponse {
    @SerializedName("Status")
    String status;

    @SerializedName("Details")
    String details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
