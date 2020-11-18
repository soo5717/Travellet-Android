package com.example.travellet.data;

import com.google.gson.annotations.SerializedName;

public class SettingResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
