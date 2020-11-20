package com.example.travellet.data;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeCancelResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public Boolean message;
}
