package com.example.travellet.feature.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

/**
 * Created by 수연 on 2021-02-01.
 * Class: ValueFormatter
 * Description: 막대 그래프 x축 포맷 설정 유틸
 */
public class BarChartValueFormatter extends ValueFormatter {
    private final ArrayList<String> mValues; //전역변수 선언

    //생성자
    public BarChartValueFormatter(ArrayList<String> mValues) {
        this.mValues = mValues;
    }

    //전달한 문자열 리스트를 인덱스로 뽑아옴.
    @Override
    public String getFormattedValue(float value) {
        return mValues.get((int)value+1);
    }
}