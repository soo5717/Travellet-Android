package com.example.travellet.feature.util.viewpager;

import android.content.Context;
import java.util.ArrayList;

/**
 * Created by 수연 on 2020-12-25.
 * Class: ViewPagerData
 * Description: 리사이클러뷰 사용 뷰페이저 공통 데이터
 * => Context와 데이터를 담고 있는 ArrayList가 있음.
 */
public class ViewPagerData {
    private Context context;
    private ArrayList<?> arrayList;

    public ViewPagerData(Context context, ArrayList<?> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<?> getData() {
        return arrayList;
    }

    public void setData(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
    }
}
