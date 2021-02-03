package com.example.travellet.feature.report;

public class ReportCategoryItem {
    String memo, date, expense;
    int category, payment;

    public ReportCategoryItem(String memo, String date, int category, int payment, String expense) {
        this.memo = memo;
        this.date = date;
        this.category = category;
        this.payment = payment;
        this.expense = expense;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}
