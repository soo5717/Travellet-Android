package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: ProfileData
 * Description: 프로필 조회 요청 데이터
 */
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
