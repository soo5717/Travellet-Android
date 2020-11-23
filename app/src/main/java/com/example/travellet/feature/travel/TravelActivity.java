package com.example.travellet.feature.travel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.travellet.R;
import com.example.travellet.databinding.ActivityTravelBinding;
import com.example.travellet.feature.place.PlaceActivity;
import com.example.travellet.feature.setting.SettingActivity;

/**
 * Created by 수연 on 2020-11-22.
 * Class: TravelActivity
 * Description: 메인 클래스 (미완성)
 * 로그인 후 바로 보이는 메인 화면으로 여행 목록를 담고 있음.
 */
public class TravelActivity extends AppCompatActivity {
    private ActivityTravelBinding binding; //바인딩 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity 뷰 바인딩
        binding = ActivityTravelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    //Upcoming 목록 요청 - GET : Retrofit2 => 미완성 코드
    void requestUpcoming() {
        
    }
    
    //Past 목록 요청 메소드 호출 - GET : Retrofit2 => 미완성 코드
    void requestPast() {

    }

    //Upcoming 버튼 클릭 이벤트
    public void upcomingButtonClick(View view) {
        //버튼 선택 시 Underline 및 text color 변경
        binding.buttonUpcoming.setBackgroundResource(R.drawable.bg_underline_gradation);
        binding.buttonUpcoming.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.text_blue));
        binding.buttonPast.setBackgroundColor(Color.WHITE);
        binding.buttonPast.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.icon_grey));

        //Upcoming 목록 요청 메소드 호출
//        requestUpcoming();
    }

    //Past 버튼 클릭 이벤트
    public void pastButtonClick(View view) {
        //버튼 선택 시 Underline 및 text color 변경
        binding.buttonUpcoming.setBackgroundColor(Color.WHITE);
        binding.buttonUpcoming.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.icon_grey));
        binding.buttonPast.setBackgroundResource(R.drawable.bg_underline_gradation);
        binding.buttonPast.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.text_blue));

        //Past 목록 요청 메소드 호출
//        requestPast();
    }

    //Add 버튼 클릭 이벤트 : 여행 추가 페이지로 이동
    public void addButtonClick(View view) {
        Intent intent = new Intent(this, SetTitleActivity.class);
        startActivity(intent);
    }

    //Search 버튼 클릭 이벤트 : 장소 검색 페이지로 이동
    public void searchButtonClick(View view) {
        Intent intent = new Intent(this, PlaceActivity.class);
        startActivity(intent);
    }

    //Setting 버튼 클릭 이벤트 : 설정 페이지로 이동
    public void settingButtonClick(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}