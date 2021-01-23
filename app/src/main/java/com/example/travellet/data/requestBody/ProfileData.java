package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2021-01-24.
 * Class: ProfileData
 * Description: 프로필 수정 요청 데이터
 */
public class ProfileData {
    @SerializedName("name")
    public String name;

    public ProfileData(String name) {
        this.name = name;
    }
}
