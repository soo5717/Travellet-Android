package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

public class TravelCreateResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;
    @SerializedName("title")
    public String title;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("budget")
    public double budget;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }
}
