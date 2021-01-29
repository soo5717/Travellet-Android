package com.example.travellet.data.viewpager;

import android.content.Context;

import com.example.travellet.data.responseBody.PlanDetailResponse;

import java.util.ArrayList;

/**
 * Created by 수연 on 2021-01-17.
 * Class: PlanDetailViewPagerData
 * Description: 리사이클러뷰 사용 뷰페이저 데이터
 * => Context와 데이터를 담고 있는 ArrayList가 있음.
 */
public class PlanDetailViewPagerData {
    private Context context;
    private ViewPagerCase viewPagerCase;
    private Integer id;
    private String place;
    private String memo;
    private String startDate;
    private String date;
    private Integer total;
    private String currency;
    private ArrayList<PlanDetailResponse.Data.Datum> arrayList;

    public PlanDetailViewPagerData(Context context, ViewPagerCase viewPagerCase, Integer id, String place, String memo, String startDate, String date, Integer total, String currency, ArrayList<PlanDetailResponse.Data.Datum> arrayList) {
        this.context = context;
        this.viewPagerCase = viewPagerCase;
        this.id = id;
        this.place = place;
        this.memo = memo;
        this.startDate = startDate;
        this.date = date;
        this.total = total;
        this.currency = currency;
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ViewPagerCase getViewPagerCase() {
        return viewPagerCase;
    }

    public void setViewPagerCase(ViewPagerCase viewPagerCase) {
        this.viewPagerCase = viewPagerCase;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<PlanDetailResponse.Data.Datum> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PlanDetailResponse.Data.Datum> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<PlanDetailResponse.Data.Datum> getData() {
        return arrayList;
    }

    public void setData(ArrayList<PlanDetailResponse.Data.Datum> arrayList) {
        this.arrayList = arrayList;
    }
}
