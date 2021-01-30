package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DistributeBudgetResponse {
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
        @SerializedName("sumBudget")
        private Double sumBudget;
        @SerializedName("countCategory")
        private ArrayList<Item> countCategory;

        public Double getSumBudget() {
            return sumBudget;
        }

        public ArrayList<Item> getCountCategory() {
            return countCategory;
        }
        public static class Item {
            @SerializedName("category")
            private Integer category;
            @SerializedName("count")
            private Integer count;

            public Integer getCategory() {
                return category;
            }

            public Integer getCount() {
                return count;
            }
        }
    }
}
