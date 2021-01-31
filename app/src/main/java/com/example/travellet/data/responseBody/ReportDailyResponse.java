package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportDailyResponse {
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
        @SerializedName("chart")
        private final List<Chart> chart = null;
        @SerializedName("date")
        private final List<String> date = null;
        @SerializedName("item")
        private final List<List<Item>> item = null;

        public List<Chart> getChart() {
            return chart;
        }

        public List<String> getDate() {
            return date;
        }

        public List<List<Item>> getItem() {
            return item;
        }

        public static class Chart {
            @SerializedName("date")
            private String date;
            @SerializedName("payment")
            private Integer payment;
            @SerializedName("priceTo")
            private Float priceTo;
            @SerializedName("priceKrw")
            private String priceKrw;

            public String getDate() {
                return date;
            }

            public Integer getPayment() {
                return payment;
            }

            public Float getPriceTo() {
                return priceTo;
            }

            public String getPriceKrw() {
                return priceKrw;
            }
        }

        public static class Item {
            @SerializedName("date")
            private String date;
            @SerializedName("memo")
            private String memo;
            @SerializedName("category")
            private Integer category;
            @SerializedName("payment")
            private Integer payment;
            @SerializedName("priceTo")
            private Float priceTo;
            @SerializedName("priceKrw")
            private Float priceKrw;

            public String getDate() {
                return date;
            }

            public String getMemo() {
                return memo;
            }

            public Integer getCategory() {
                return category;
            }

            public Integer getPayment() {
                return payment;
            }

            public Float getPriceTo() {
                return priceTo;
            }

            public Float getPriceKrw() {
                return priceKrw;
            }
        }
    }
}
