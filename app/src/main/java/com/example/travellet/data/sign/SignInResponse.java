package com.example.travellet.data.sign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignInResponse
 * Description: 로그인 응답 데이터
 */
public class SignInResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
