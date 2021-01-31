package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class DistributeBudgetData {
    @SerializedName("TravelId")
    private Integer travelId;
    @SerializedName("lodging")
    private Integer lodging;
    @SerializedName("food")
    private Integer food;
    @SerializedName("shopping")
    private Integer shopping;
    @SerializedName("tourism")
    private Integer tourism;
    @SerializedName("etc")
    private Integer etc;

    public DistributeBudgetData(Integer travelId, Integer lodging, Integer food, Integer shopping, Integer tourism, Integer etc) {
        this.travelId = travelId;
        this.lodging = lodging;
        this.food = food;
        this.shopping = shopping;
        this.tourism = tourism;
        this.etc = etc;
    }
}
