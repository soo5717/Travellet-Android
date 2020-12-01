package com.example.travellet.data.place;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }
}
