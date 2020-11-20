package com.example.travellet.data;

import com.google.gson.annotations.SerializedName;

public class TravelDeleteResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;

    public TravelDeleteResponse(Integer status, Boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}
