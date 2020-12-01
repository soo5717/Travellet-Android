package com.example.travellet.data.place;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeCancelResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public Boolean message;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getMessage() {
        return message;
    }
}
