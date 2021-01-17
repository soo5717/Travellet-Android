package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 수연 on 2020-12-21.
 * Class: TravelReadResponse
 * Description: 여행 목록 조회 응답 데이터
 */
public class TravelResponse {
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

        @SerializedName("upcoming")
        private List<Travel> upcoming = null;
        @SerializedName("past")
        private List<Travel> past = null;

        public List<Travel> getUpcoming() {
            return upcoming;
        }

        public List<Travel> getPast() {
            return past;
        }

        public class Travel {

            @SerializedName("id")
            private Integer id;
            @SerializedName("title")
            private String title;
            @SerializedName("start_date")
            private String startDate;
            @SerializedName("end_date")
            private String endDate;
            @SerializedName("budget")
            private Double budget;
            @SerializedName("user_id")
            private Integer userId;
            @SerializedName("sum_budget")
            private String sumBudget;
            @SerializedName("sum_expense")
            private String sumExpense;

            public Integer getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getStartDate() {
                return startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public Double getBudget() {
                return budget;
            }

            public Integer getUserId() {
                return userId;
            }

            public String getSumBudget() {
                return sumBudget;
            }

            public String getSumExpense() {
                return sumExpense;
            }
        }
    }
}
