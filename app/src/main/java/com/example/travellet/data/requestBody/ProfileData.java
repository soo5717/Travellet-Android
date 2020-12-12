package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class ProfileData {
    @SerializedName("name")
    public String name;
    @SerializedName("country")
    public String country;

    public ProfileData(String name, String country) {
        this.name = name;
        this.country = country;
    }
}
