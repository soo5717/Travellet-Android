package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class TravelCreateData {
    @SerializedName("title")
    public String title;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("budget")
    public double budget;

    public TravelCreateData(String title, String startDate, String endDate, double budget){
        super();
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }
}
