package com.example.travellet.data;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;

    public PlaceLikeResponse(Integer status, Boolean success) {
        this.status = status;
        this.success = success;
    }
}
