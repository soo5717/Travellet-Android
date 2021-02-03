package com.example.travellet.data.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportCategoryResponse {

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

    public class Data {
        @SerializedName("currency")
        private String currency;
        @SerializedName("categoryGraph")
        private List<CategoryGraph> categoryGraph = null;
        @SerializedName("categoryList")
        private List<List<CategoryList>> categoryList = null;

        public String getCurrency() {
            return currency;
        }

        public List<CategoryGraph> getCategoryGraph() {
            return categoryGraph;
        }

        public List<List<CategoryList>> getCategoryList() {
            return categoryList;
        }

        public class CategoryGraph {

            @SerializedName("category")
            private Integer category;
            @SerializedName("priceTo")
            private Double priceTo;
            @SerializedName("priceKrw")
            private String priceKrw;

            public Integer getCategory() {
                return category;
            }

            public Double getPriceTo() {
                return priceTo;
            }

            public String getPriceKrw() {
                return priceKrw;
            }

        }

        public class CategoryList {

            @SerializedName("date")
            private String date;
            @SerializedName("category")
            private Integer category;
            @SerializedName("priceTo")
            private Double priceTo;
            @SerializedName("priceKrw")
            private Integer priceKrw;
            @SerializedName("memo")
            private String memo;
            @SerializedName("payment")
            private Integer payment;

            public String getDate() {
                return date;
            }

            public Integer getCategory() {
                return category;
            }

            public Double getPriceTo() {
                return priceTo;
            }

            public Integer getPriceKrw() {
                return priceKrw;
            }

            public String getMemo() {
                return memo;
            }

            public Integer getPayment() {
                return payment;
            }
        }
    }

}
