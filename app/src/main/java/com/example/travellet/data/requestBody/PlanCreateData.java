package com.example.travellet.data.requestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanCreateData {
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

    public PlanCreateData(String date, String time, String place, String memo, Integer category, Integer transport, Double x, Double y, Integer travelId) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.memo = memo;
        this.category = category;
        this.transport = transport;
        this.x = x;
        this.y = y;
        this.travelId = travelId;
    }
}
