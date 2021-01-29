package com.example.travellet.data.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2021-01-26.
 * Class: BudgetResponse
 * Description: 예산 내용 조회 응답 데이터
 */
public class BudgetDetailResponse {
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
        @SerializedName("id")
        private Integer id;
        @SerializedName("currency")
        private String currency;
        @SerializedName("price")
        private Double price;
        @SerializedName("priceTo")
        private Double priceTo;
        @SerializedName("memo")
        private String memo;
        @SerializedName("category")
        private Integer category;
        @SerializedName("exchangeRate")
        private ExchangeRateResponse.Data exchangeRate;

        public Integer getId() {
            return id;
        }

        public String getCurrency() {
            return currency;
        }

        public Double getPrice() {
            return price;
        }

        public Double getPriceTo() {
            return priceTo;
        }

        public String getMemo() {
            return memo;
        }

        public Integer getCategory() {
            return category;
        }

        public ExchangeRateResponse.Data getExchangeRate() {
            return exchangeRate;
        }
    }
}
