package com.example.travellet.data.travel;

import com.google.gson.annotations.SerializedName;

public class TravelDeleteResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
