package com.example.travellet.data.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("data")
    private ArrayList<Item> data;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ArrayList<Item> getData() {
        return data;
    }

    public class Item{
        @SerializedName("id")
        public Integer id;
        @SerializedName("date")
        public String date;
        @SerializedName("time")
        public String time;
        @SerializedName("place")
        public String place;
        @SerializedName("memo")
        public String memo;
        @SerializedName("category")
        public Integer category;
        @SerializedName("transport")
        public Integer transport;
        @SerializedName("x")
        public Double x;
        @SerializedName("y")
        public Double y;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public Integer getTransport() {
            return transport;
        }

        public void setTransport(Integer transport) {
            this.transport = transport;
        }

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }
    }
}
