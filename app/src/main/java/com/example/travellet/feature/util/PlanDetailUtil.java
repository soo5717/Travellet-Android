package com.example.travellet.feature.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 수연 on 2021-01-17.
 * Class: PlanDetailUtil
 * Description: 일정 조회 관련 유틸
 */
public class PlanDetailUtil {
    String mStrFormat = null;

    //여행 DAY 계산 (DAY 1, DAY 2 ...)
    public String getDay(String start, String target) {
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date startDate = simpleDateFormat.parse(start);
            Date targetDate = simpleDateFormat.parse(target);

            assert startDate != null;
            assert targetDate != null;

            //밀리 초 -> 초로 변환
            final int ONE_DAY = 24 * 60 * 60 * 1000;
            long result = (startDate.getTime() - targetDate.getTime())/ONE_DAY;
            result = Math.abs(result) + 1;

            mStrFormat = "DAY %d";
            return String.format(Locale.getDefault(), mStrFormat, result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //합계 (TOTAL ￦1000)
    public String getTotal(int total) {
        mStrFormat = "TOTAL ￦%,d";
        return String.format(Locale.getDefault(), mStrFormat, total);
    }

    //카테고리 (숙박, 푸드, 쇼핑, 투어, 교통, 기타)
    public String getCategory(int category) {
        switch (category) {
            case 1:
                return "Lodging";
            case 2:
                return "Food";
            case 3:
                return "Shopping";
            case 4:
                return "Tourism";
            case 5:
                return "Transport";
            default:
                return "Etc";
        }
    }

    //결제 방식 (현금, 카드)
    public String getPayment(boolean payment) {
        if(!payment) //false
            return "Cash";
        else //true
            return "Credit";
    }

    //환율 표기
    public String getPrice(String currency, Double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        numberFormat.setCurrency(Currency.getInstance(currency));

        return numberFormat.format(price);
    }
}
