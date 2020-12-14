package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class PlaceLikeData {
    @SerializedName("contentId")
    public int contentId;

    public PlaceLikeData(int contentId) {
        this.contentId = contentId;
    }
}
