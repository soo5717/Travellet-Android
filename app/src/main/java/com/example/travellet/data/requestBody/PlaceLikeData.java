package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeData {
    @SerializedName("placeID")
    public int placeID;

    public PlaceLikeData(int placeID) {
        this.placeID = placeID;
    }
}
