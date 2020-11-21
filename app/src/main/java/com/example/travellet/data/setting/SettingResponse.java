package com.example.travellet.data.setting;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SettingResponse
 * Description: 프로필 조회 요청 데이터
 */
public class SettingResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
