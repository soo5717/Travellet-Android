package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 수연 on 2021-01-30.
 * Class: PlanDetailResponse
 * Description: 예산/지출 목록 조회 요청 데이터
 */
public class PlanDetailResponse {
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
        @SerializedName("id")
        private Integer id;
        @SerializedName("date")
        private String date;
        @SerializedName("place")
        private String place;
        @SerializedName("memo")
        private String memo;
        @SerializedName("Travel.startDate")
        private String travelStartDate;
        @SerializedName("currency")
        private String currency;
        @SerializedName("sumBudget")
        private Integer sumBudget;
        @SerializedName("sumExpense")
        private Integer sumExpense;
        @SerializedName("budget")
        private List<Datum> budget = null;
        @SerializedName("expense")
        private List<Datum> expense = null;

        public Integer getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public String getPlace() {
            return place;
        }

        public String getMemo() {
            return memo;
        }

        public String getTravelStartDate() {
            return travelStartDate;
        }

        public String getCurrency() {
            return currency;
        }

        public Integer getSumBudget() {
            return sumBudget;
        }

        public Integer getSumExpense() {
            return sumExpense;
        }

        public List<Datum> getBudget() {
            return budget;
        }

        public List<Datum> getExpense() {
            return expense;
        }

        public class Datum {
            @SerializedName("id")
            private Integer id;
            @SerializedName("currency")
            private String currency;
            @SerializedName("price")
            private Double price;
            @SerializedName("priceTo")
            private Double priceTo;
            @SerializedName("priceKrw")
            private Double priceKrw;
            @SerializedName("memo")
            private String memo;
            @SerializedName("category")
            private Integer category;
            @SerializedName("payment")
            private Boolean payment;

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

            public Double getPriceKrw() {
                return priceKrw;
            }

            public String getMemo() {
                return memo;
            }

            public Integer getCategory() {
                return category;
            }

            public Boolean getPayment() {
                return payment;
            }
        }
    }
}
