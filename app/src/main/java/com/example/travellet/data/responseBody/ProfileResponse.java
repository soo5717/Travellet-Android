package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SettingResponse
 * Description: 프로필 조회 응답 데이터
 */
public class ProfileResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("email")
        private String email;
        @SerializedName("name")
        private String name;
        @SerializedName("country")
        private String country;

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
}
