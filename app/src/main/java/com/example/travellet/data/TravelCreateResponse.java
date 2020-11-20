package com.example.travellet.data;

import com.google.gson.annotations.SerializedName;

public class TravelCreateResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("title")
    public String title;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("totalBudget")
    public double totalBudget;

    public TravelCreateResponse(Integer status, Boolean success, String title, String startDate, String endDate, double totalBudget) {
        this.status = status;
        this.success = success;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
    }
}
