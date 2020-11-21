package com.example.travellet.data.utill;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: StatusResponse
 * Description: 공통 응답 데이터
 */
public class StatusResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
