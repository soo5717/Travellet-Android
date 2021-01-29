package com.example.travellet.data.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2021-01-28.
 * Class: ExchangeRateResponse
 * Description: 예산, 지출 생성/수정에 사용되는 환율 정보 응답 데이터
 */
public class ExchangeRateResponse {
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
        @SerializedName("rateTo")
        private Double rateTo;
        @SerializedName("rateKrw")
        private Double rateKrw;
        @SerializedName("currency")
        private String currency;

        public Double getRateTo() {
            return rateTo;
        }

        public Double getRateKrw() {
            return rateKrw;
        }

        public String getCurrency() {
            return currency;
        }
    }
}
