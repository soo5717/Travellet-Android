package com.example.travellet.data.viewpager;

import android.content.Context;

import com.example.travellet.data.responseBody.TravelResponse;

import java.util.ArrayList;

/**
 * Created by 수연 on 2020-12-25.
 * Class: TravelViewPagerData
 * Description: 리사이클러뷰 사용 뷰페이저 데이터
 * => Context와 데이터를 담고 있는 ArrayList가 있음.
 */
public class TravelViewPagerData {
    private Context context;
    private ArrayList<TravelResponse.Data.Travel> arrayList;

    public TravelViewPagerData(Context context, ArrayList<TravelResponse.Data.Travel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<TravelResponse.Data.Travel> getData() {
        return arrayList;
    }

    public void setData(ArrayList<TravelResponse.Data.Travel> arrayList) {
        this.arrayList = arrayList;
    }
}
