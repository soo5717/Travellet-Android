package com.example.travellet.feature.travel; /***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2017 - 2019 LeeYongBeom( top6616@gmail.com )
 * https://github.com/yongbeam
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travellet.R;
import com.yongbeom.aircalendar.core.AirCalendarIntent;
import com.yongbeom.aircalendar.core.AirMonthAdapter;
import com.yongbeom.aircalendar.core.DatePickerController;
import com.yongbeom.aircalendar.core.DayPickerView;
import com.yongbeom.aircalendar.core.SelectModel;
import com.yongbeom.aircalendar.core.util.AirCalendarUtils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


public class SetDateActivity extends AppCompatActivity implements DatePickerController {

    public final static String EXTRA_FLAG = "FLAG";
    public final static String EXTRA_IS_BOOIKNG = "IS_BOOING";
    public final static String EXTRA_IS_SELECT = "IS_SELECT";
    public final static String EXTRA_BOOKING_DATES = "BOOKING_DATES";
    public final static String EXTRA_SELECT_DATE_SY = "SELECT_START_DATE_Y";
    public final static String EXTRA_SELECT_DATE_SM = "SELECT_START_DATE_M";
    public final static String EXTRA_SELECT_DATE_SD = "SELECT_START_DATE_D";
    public final static String EXTRA_SELECT_DATE_EY = "SELECT_END_DATE_Y";
    public final static String EXTRA_SELECT_DATE_EM = "SELECT_END_DATE_M";
    public final static String EXTRA_SELECT_DATE_ED = "SELECT_END_DATE_D";
    public final static String EXTRA_IS_MONTH_LABEL = "IS_MONTH_LABEL";
    public final static String EXTRA_IS_SINGLE_SELECT = "IS_SINGLE_SELECT";
    public final static String EXTRA_ACTIVE_MONTH_NUM = "ACTIVE_MONTH_NUMBER";
    public final static String EXTRA_MAX_YEAR = "MAX_YEAR";
    public final static String EXTRA_START_YEAR = "START_YEAR";

    public final static String SELECT_TEXT = "SELECT_TEXT";
    public final static String RESET_TEXT = "RESET_TEXT";
    public final static String CUSTOM_WEEK_ABREVIATIONS = "CUSTOM_WEEK_ABREVIATIONS";
    public final static String WEEK_LANGUAGE = "WEEK_LANGUAGE";
    public final static String WEEK_START = "WEEK_START";

    public final static String RESULT_SELECT_START_DATE = "start_date";
    public final static String RESULT_SELECT_END_DATE = "end_date";
    public final static String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public final static String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public final static String RESULT_FLAG = "flag";
    public final static String RESULT_TYPE = "result_type";
    public final static String RESULT_STATE = "result_state";

    private DayPickerView pickerView;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_popup_msg;
    private TextView tv_done_btn;
    private TextView tv_reset_btn;

    private TextView tv_day_one;
    private TextView tv_day_two;
    private TextView tv_day_three;
    private TextView tv_day_four;
    private TextView tv_day_five;
    private TextView tv_day_six;
    private TextView tv_day_seven;

    Button next;
    ImageButton remove;
    int mainPosition;


    private RelativeLayout rl_popup_select_checkout_info_ok;
    private RelativeLayout rl_checkout_select_info_popup;
    private RelativeLayout rl_iv_back_btn_bg;

    private String SELECT_START_DATE = "";
    private String SELECT_END_DATE = "";
    private int BASE_YEAR = 2018;
    private int firstDayOfWeek = Calendar.SUNDAY;

    private String FLAG = "all";
    private boolean isSelect = false;
    private boolean isBooking = false;
    private boolean isMonthLabel = false;
    private boolean isSingleSelect = false;
    private List<String> weekDays = new ArrayList<>();
    private ArrayList<String> dates;
    private SelectModel selectDate;

    private int sYear = 0;
    private int sMonth = 0;
    private int sDay = 0;
    private int eYear = 0;
    private int eMonth = 0;
    private int eDay = 0;

    private AirCalendarIntent.Language language = AirCalendarIntent.Language.EN;

    private int maxActivieMonth = -1;
    private int maxYear = -1;
    private int mSetStartYear = -1;

    int start_year_int, start_month_int, start_day_int, start_dow_int, end_year_int, end_month_int, end_day_int, end_dow_int;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);

        Intent getData = getIntent();
        FLAG = getData.getStringExtra(EXTRA_FLAG) != null ? getData.getStringExtra(EXTRA_FLAG) : "all";
        isBooking = getData.getBooleanExtra(EXTRA_IS_BOOIKNG, false);
        isSelect = getData.getBooleanExtra(EXTRA_IS_SELECT, false);
        isMonthLabel = getData.getBooleanExtra(EXTRA_IS_MONTH_LABEL, false);
        isSingleSelect = getData.getBooleanExtra(EXTRA_IS_SINGLE_SELECT, false);
        dates = getData.getStringArrayListExtra(EXTRA_BOOKING_DATES);
        maxActivieMonth = getData.getIntExtra(EXTRA_ACTIVE_MONTH_NUM, -1);
        maxYear = getData.getIntExtra(EXTRA_MAX_YEAR, -1);
        mSetStartYear = getData.getIntExtra(EXTRA_START_YEAR, -1);

        sYear = getData.getIntExtra(EXTRA_SELECT_DATE_SY, 0);
        sMonth = getData.getIntExtra(EXTRA_SELECT_DATE_SM, 0);
        sDay = getData.getIntExtra(EXTRA_SELECT_DATE_SD, 0);

        eYear = getData.getIntExtra(EXTRA_SELECT_DATE_EY, 0);
        eMonth = getData.getIntExtra(EXTRA_SELECT_DATE_EM, 0);
        eDay = getData.getIntExtra(EXTRA_SELECT_DATE_ED, 0);

        if (sYear == 0 || sMonth == 0 || sDay == 0
                || eYear == 0 || eMonth == 0 || eDay == 0) {
            selectDate = new SelectModel();
            isSelect = false;
        }

        if (getIntent().hasExtra(CUSTOM_WEEK_ABREVIATIONS)) {
            weekDays.clear();
            weekDays.addAll(getIntent().getStringArrayListExtra(CUSTOM_WEEK_ABREVIATIONS));
            AirCalendarUtils.setWeekdays(weekDays);
        }

        if (getIntent().hasExtra(WEEK_LANGUAGE)) {
            language = AirCalendarIntent.Language.valueOf(getIntent().getStringExtra(WEEK_LANGUAGE));
            AirCalendarUtils.setLanguage(AirCalendarIntent.Language.EN);
        }

        init();

    }

    private void init() {
        //remove = findViewById(R.id.btn_back);
        next = findViewById(R.id.nextButton);

        tv_day_one = findViewById(com.yongbeom.aircalendar.R.id.tv_day_one);
        tv_day_two = findViewById(com.yongbeom.aircalendar.R.id.tv_day_two);
        tv_day_three = findViewById(com.yongbeom.aircalendar.R.id.tv_day_three);
        tv_day_four = findViewById(com.yongbeom.aircalendar.R.id.tv_day_four);
        tv_day_five = findViewById(com.yongbeom.aircalendar.R.id.tv_day_five);
        tv_day_six = findViewById(com.yongbeom.aircalendar.R.id.tv_day_six);
        tv_day_seven = findViewById(com.yongbeom.aircalendar.R.id.tv_day_seven);

        if (getIntent().hasExtra(SELECT_TEXT)) {
            //tv_done_btn.setText(getIntent().getStringExtra(SELECT_TEXT));
        }


        if (weekDays.isEmpty()) {
            switch (language) {
                case EN:
                    List<String> enList = new ArrayList<>(Arrays.asList(getResources().getStringArray(com.yongbeom.aircalendar.R.array.label_calendar_en)));
                    weekDays.addAll(enList);
                    break;

            }
        }

        firstDayOfWeek = getIntent().getIntExtra(WEEK_START, Calendar.SUNDAY);

        if (weekDays.isEmpty()) {
            throw new RuntimeException("Language not supported or non existent");
        } else {
            int weekStart = firstDayOfWeek - 2;

            for (int week = 0; week < 7; week++) {
                int weekDay = weekStart + week;
                if (weekDay < 0) {
                    weekDay += 7;
                }
                if (weekDay > 6) {
                    weekDay -= 7;
                }
                Log.d("DOW", week + "/" + weekDay + ":" + weekDays.get(weekDay));
                switch (week) {
                    case 0:
                        tv_day_one.setText(weekDays.get(weekDay));
                        tv_day_one.setTextColor(Color.parseColor("#f3534a"));
                        break;
                    case 1:
                        tv_day_two.setText(weekDays.get(weekDay));
                        break;
                    case 2:
                        tv_day_three.setText(weekDays.get(weekDay));
                        break;
                    case 3:
                        tv_day_four.setText(weekDays.get(weekDay));
                        break;
                    case 4:
                        tv_day_five.setText(weekDays.get(weekDay));
                        break;
                    case 5:
                        tv_day_six.setText(weekDays.get(weekDay));
                        break;
                    case 6:
                        tv_day_seven.setText(weekDays.get(weekDay));
                        break;
                }
            }

        }

        pickerView = findViewById(com.yongbeom.aircalendar.R.id.pickerView);
        pickerView.setIsMonthDayLabel(isMonthLabel);
        pickerView.setIsSingleSelect(isSingleSelect);
        pickerView.setMaxActiveMonth(maxActivieMonth);
        pickerView.setStartYear(mSetStartYear);

        pickerView.setFirstDayOfWeek(firstDayOfWeek);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);

        if (maxYear != -1 && maxYear > Integer.parseInt(new DateTime().toString("yyyy"))) {
            BASE_YEAR = maxYear;
        } else {
            // default : now year + 2 year
            BASE_YEAR = Integer.valueOf(dTime) + 2;
        }

        if (dates != null && dates.size() != 0 && isBooking) {
            pickerView.setShowBooking(true);
            pickerView.setBookingDateArray(dates);
        }

        if (isSelect) {
            selectDate = new SelectModel();
            selectDate.setSelectd(true);
            selectDate.setFristYear(sYear);
            selectDate.setFristMonth(sMonth);
            selectDate.setFristDay(sDay);
            selectDate.setLastYear(eYear);
            selectDate.setLastMonth(eMonth);
            selectDate.setLastDay(eDay);
            pickerView.setSelected(selectDate);
        }

        pickerView.setController(this);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                String title = intent2.getStringExtra("travelTitle");

                if ((SELECT_START_DATE == null || SELECT_START_DATE.equals("")) && (SELECT_END_DATE == null || SELECT_END_DATE.equals(""))) {
                    SELECT_START_DATE = "";
                    SELECT_END_DATE = "";
                } else {
                    if (SELECT_START_DATE == null || SELECT_START_DATE.equals("")) {
                        //Toast.makeText(com.google.cloud.solutions.calendar.TravelCalendarSet.this, "Please select all dates.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (SELECT_END_DATE == null || SELECT_END_DATE.equals("")) {

                        //Toast.makeText(com.google.cloud.solutions.calendar.TravelCalendarSet.this, "Please select all dates.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent resultIntent = new Intent(getApplicationContext(), SetBudgetActivity.class);

                resultIntent.putExtra("startYear", start_year_int);
                resultIntent.putExtra("startMonth", start_month_int);
                resultIntent.putExtra("startDay", start_day_int);
                resultIntent.putExtra("endYear", end_year_int);
                resultIntent.putExtra("endMonth", end_month_int);
                resultIntent.putExtra("endDay", end_day_int);
                resultIntent.putExtra("travelTitle", title);
                resultIntent.putExtra(RESULT_FLAG, FLAG);
                resultIntent.putExtra(RESULT_TYPE, FLAG);
                resultIntent.putExtra(RESULT_STATE, "done");
                //Toast.makeText(getApplicationContext(), title+"\n"+ start_year_int+" "+start_month_int+" "+start_day_int+" "+start_dow_int+
                //       "\n"+end_year_int+" "+end_month_int+" "+end_day_int+" "+end_dow_int, Toast.LENGTH_LONG).show();
                startActivity(resultIntent);
                overridePendingTransition(0, 0);
            }
        });

        /*remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public int getMaxYear() {
        return BASE_YEAR;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        // TODO Single Select Event
        try {
            String start_month_str = String.format("%02d", (month + 1));
            // 일
            String start_day_str = String.format("%02d", day);
            String startSetDate = year + start_month_str + start_day_str;

            String startDateDay = AirCalendarUtils.getDateDay(this, startSetDate, "yyyyMMdd", firstDayOfWeek);

                /* tv_start_date.setText(year + "-" + start_month_str + "-" + start_day_str + " " + startDateDay);
                tv_start_date.setTextColor(0xff4a4a4a);

                tv_end_date.setText("-");
                tv_end_date.setTextColor(0xff1abc9c);*/
            SELECT_END_DATE = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateRangeSelected(AirMonthAdapter.SelectedDays<AirMonthAdapter.CalendarDay> selectedDays) {

        try {
            Calendar cl = Calendar.getInstance();

            cl.setTimeInMillis(selectedDays.getFirst().getDate().getTime());

            start_year_int = cl.get(Calendar.YEAR);
            // 월
            start_month_int = (cl.get(Calendar.MONTH) + 1);
            String start_month_str = String.format("%02d", start_month_int);

            // 일
            start_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String start_day_str = String.format("%02d", start_day_int);

            //요일
            start_dow_int = cl.get(Calendar.DAY_OF_WEEK);
            String start_day_of_week="";
            switch (start_dow_int) {
                case 1:
                    start_day_of_week = "SUN";
                    break;
                case 2:
                    start_day_of_week = "MON";
                    break;
                case 3:
                    start_day_of_week = "TUE";
                    break;
                case 4:
                    start_day_of_week = "WED";
                    break;
                case 5:
                    start_day_of_week = "THU";
                    break;
                case 6:
                    start_day_of_week = "FRI";
                    break;
                case 7:
                    start_day_of_week = "SAT";
                    break;
            }

            String startSetDate = cl.get(Calendar.YEAR) + start_month_int + start_day_int + start_day_of_week;
//            String startDateDay = AirCalendarUtils.getDateDay(this, startSetDate, "yyyyMMdd", firstDayOfWeek);
            String startDate = cl.get(Calendar.YEAR) + "-" + start_month_str + "-" + start_day_str+"-"+start_day_of_week;

            cl.setTimeInMillis(selectedDays.getLast().getDate().getTime());

            end_year_int = cl.get(Calendar.YEAR);
            // 월
            end_month_int = (cl.get(Calendar.MONTH) + 1);
            String end_month_str = String.format("%02d", end_month_int);

            // 일
            end_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String end_day_str = String.format("%02d", end_day_int);

            //요일
            end_dow_int = cl.get(Calendar.DAY_OF_WEEK);
            String end_day_of_week="";
            switch (end_dow_int) {
                case 1:
                    end_day_of_week = "SUN";
                    break;
                case 2:
                    end_day_of_week = "MON";
                    break;
                case 3:
                    end_day_of_week = "TUE";
                    break;
                case 4:
                    end_day_of_week = "WED";
                    break;
                case 5:
                    end_day_of_week = "THU";
                    break;
                case 6:
                    end_day_of_week = "FRI";
                    break;
                case 7:
                    end_day_of_week = "SAT";
                    break;
            }

            String endSetDate = cl.get(Calendar.YEAR) + end_month_str + end_day_str + end_day_of_week;
            // String endDateDay = AirCalendarUtils.getDateDay(this, endSetDate, "yyyyMMdd", firstDayOfWeek);
            String endDate = cl.get(Calendar.YEAR) + "-" + end_month_str + "-" + end_day_str + "-" + end_day_of_week;

                    /*tv_start_date.setText(startDate + " " + startDateDay);
                    tv_start_date.setTextColor(0xff4a4a4a);

                    tv_end_date.setText(endDate + " " + endDateDay);
                    tv_end_date.setTextColor(0xff4a4a4a);*/

            SELECT_START_DATE = startDate;
            SELECT_END_DATE = endDate;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
}

