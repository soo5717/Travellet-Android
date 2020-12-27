package com.example.travellet.feature.plan;

public class PlanItem {
    String date, time, place, memo;
    int type, transport;
    double budget, expense, x, y;

    public PlanItem(String date, String time, String place, String memo, int type, int transport, double budget, double expense, double x, double y) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.memo = memo;
        this.transport = transport;
        this.type = type;
        this.budget = budget;
        this.expense = expense;
        this.x = x;
        this.y = y;
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

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
