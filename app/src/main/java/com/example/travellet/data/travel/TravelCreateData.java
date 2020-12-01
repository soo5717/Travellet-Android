package com.example.travellet.data.travel;

import com.google.gson.annotations.SerializedName;

public class TravelCreateData {
    @SerializedName("title")
    public String title;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("totalBudget")
    public double totalBudget;

    public TravelCreateData(String title, String startDate, String endDate, double totalBudget){
        super();
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
    }
}
