package com.example.travellet.data.requestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanData {
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
    @SerializedName("TravelId")
    public Integer travelId;
    @SerializedName("Budgets")
    public ArrayList<Budgets> budgets = new ArrayList<Budgets>();

    public static class Budgets{
        @SerializedName("currency")
        public String currency;
        @SerializedName("price")
        public Double price;
        @SerializedName("priceTo")
        public Double priceTo;
        @SerializedName("priceKrw")
        public Integer priceKrw;
        @SerializedName("memo")
        public String memo;
        @SerializedName("category")
        public Integer category;

        public Budgets(String memo, Integer category) {
            this.currency = "KRW";
            this.price = 0.0;
            this.priceTo = 0.0;
            this.priceKrw = 0;
            this.memo = memo;
            this.category = category;
        }
    }

    public PlanData(String date, String time, String place, String memo, Integer category, Integer transport, Double x, Double y, Integer travelId, Budgets budget1, Budgets budget2) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.memo = memo;
        this.category = category;
        this.transport = transport;
        this.x = x;
        this.y = y;
        this.travelId = travelId;
        this.budgets.add(budget1);
        this.budgets.add(budget2);
    }
}
