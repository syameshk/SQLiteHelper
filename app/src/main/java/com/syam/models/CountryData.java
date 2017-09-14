package com.syam.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 30/05/17.
 */

public class CountryData {
    @SerializedName("dialingCode")
    String dialingCode;

    @SerializedName("name")
    String name;

    @SerializedName("countryCode")
    String countryCode;

    @SerializedName("countryCodeId")
    int countryCodeId;

    public CountryData(CountryData countryData) {
        this.dialingCode = countryData.dialingCode;
        this.name = countryData.name;
        this.countryCode = countryData.countryCode;
        this.countryCodeId = countryData.countryCodeId;
    }

    public CountryData() {}

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getCountryCodeId() {
        return countryCodeId;
    }

    public void setCountryCodeId(int countryCodeId) {
        this.countryCodeId = countryCodeId;
    }
}
