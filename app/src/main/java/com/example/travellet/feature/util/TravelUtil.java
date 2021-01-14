package com.example.travellet.feature.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 수연 on 2020-12-21.
 * Class: TravelUtil
 * Description: 여행 목록 조회 관련 유틸들
 */
public class TravelUtil {
    String mStrFormat = null;

    //날짜 형식 변경 (yyyy-mm-dd => mm/dd/yyyy)
    public String dateFormat(String target) {
        try {
            //문자열을 파싱해서 원하는 날짜 형식으로 변환
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(target);

            assert date != null;
            return simpleDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //시작일-종료일 (mm/dd/yyyy-mm/dd/yyyy)
    public String getDate(String start, String end) {
        mStrFormat = "%s-%s";
        String startDate = dateFormat(start);
        String endDate = dateFormat(end);
        return String.format(Locale.getDefault(), mStrFormat, startDate, endDate);
    }

    //D-DAY 계산 (D-0/D-DAY/D+0)
    public String getDday(String target) {
        try{
            String[] arr = target.split("-");

            Calendar todayCal = Calendar.getInstance(); //현재 날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //디데이 초기화

            int year = Integer.parseInt(arr[0]);
            int moth = Integer.parseInt(arr[1]) -1;
            int day = Integer.parseInt(arr[2]);
            ddayCal.set(year, moth, day);

            //밀리 초 -> 초로 변환
            final int ONE_DAY = 24 * 60 * 60 * 1000;
            long dday = ddayCal.getTimeInMillis()/ ONE_DAY;
            long today = todayCal.getTimeInMillis()/ ONE_DAY;

            long result = dday - today;

            if(result > 0)
                mStrFormat = "D-%d";
            else if(result == 0)
                mStrFormat = "D-DAY";
            else {
                result *= -1;
                mStrFormat = "D+%d";
            }
            return String.format(mStrFormat, result);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //지출 퍼센트 계산
    public int getProgress(Double budget, Double sumBudget, Double sumExpense) {
        return budget >= sumBudget ? (int)((sumExpense/budget)*100) : (int)((sumExpense/sumBudget)*100);
    }

    //지출 금액 (expense of total budget)
    public String getExpense(Double budget, Double sumBudget, Double sumExpense) {
        mStrFormat = "￦%,.0f of ￦%,.0f"; //3자리마다 , 표시
        if(budget >= sumBudget)
            return String.format(Locale.getDefault(), mStrFormat, sumExpense, budget);
        else //여행 생성 시 입력값보다 실제 입력한 예산의 합이 클 경우
            return String.format(Locale.getDefault(), mStrFormat, sumExpense, sumBudget);
    }

    //지출 퍼센트 (0%)
    public String getExpensePercent(Double budget, Double sumBudget, Double sumExpense) {
        return String.format(Locale.getDefault(), "%d%%", getProgress(budget, sumBudget, sumExpense));
    }

    //오늘 날짜 가져오기 (yyyy-mm-dd)
    public String getToday() {
        //현재 시간 가져오기
        long now = System.currentTimeMillis();
        //Date 형변환
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
