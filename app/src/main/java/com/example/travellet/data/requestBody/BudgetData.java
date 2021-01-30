package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2021-01-26.
 * Class: BudgetData
 * Description: 예산 생성/수정 요청 데이터
 */
public class BudgetData {
    @SerializedName("PlanId")
    private Integer planId;
    @SerializedName("currency")
    private String currency;
    @SerializedName("price")
    private Double price;
    @SerializedName("priceTo")
    private Double priceTo;
    @SerializedName("priceKrw")
    private Integer priceKrw;
    @SerializedName("memo")
    private String memo;
    @SerializedName("category")
    private Integer category;

    public BudgetData(Integer planId, String currency, Double price, Double priceTo, Integer priceKrw, String memo, Integer category) {
        this.planId = planId;
        this.currency = currency;
        this.price = price;
        this.priceTo = priceTo;
        this.priceKrw = priceKrw;
        this.memo = memo;
        this.category = category;
    }
}
