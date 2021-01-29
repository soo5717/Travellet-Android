package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2021-01-26.
 * Class: ExpenseData
 * Description: 지출 생성/수정 요청 데이터
 */
public class ExpenseData {
    @SerializedName("planId")
    public Integer planId;
    @SerializedName("currency")
    public String currency;
    @SerializedName("price")
    public Double price;
    @SerializedName("priceTo")
    public Double priceTo;
    @SerializedName("priceKrw")
    public Integer priceKrw;
    @SerializedName("memo")
    public String memo;
    @SerializedName("category")
    public Integer category;
    @SerializedName("payment")
    public Boolean payment;

    public ExpenseData(Integer planId, String currency, Double price, Double priceTo, Integer priceKrw, String memo, Integer category, Boolean payment) {
        this.planId = planId;
        this.currency = currency;
        this.price = price;
        this.priceTo = priceTo;
        this.priceKrw = priceKrw;
        this.memo = memo;
        this.category = category;
        this.payment = payment;
    }
}
