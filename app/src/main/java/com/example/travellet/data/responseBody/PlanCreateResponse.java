package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

public class PlanCreateResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;
    @SerializedName("date")
    public String date;
    @SerializedName("time")
    public String time;
    @SerializedName("place")
    public String place;
    @SerializedName("memo")
    public String memo;
    @SerializedName("category")
    public Integer category;
    @SerializedName("transport")
    public Integer transport;
    @SerializedName("x")
    public Double x;
    @SerializedName("y")
    public Double y;
    @SerializedName("travelId")
    public Integer travelId;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getMemo() {
        return memo;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getTransport() {
        return transport;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Integer getTravelId() {
        return travelId;
    }
}
