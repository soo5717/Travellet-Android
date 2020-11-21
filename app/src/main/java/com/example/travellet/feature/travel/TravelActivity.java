package com.example.travellet.feature.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travellet.R;

/**
 * Created by 수연 on 2020-11-22.
 * Class: TravelActivity
 * Description: 메인 클래스
 * 로그인 후 바로 보이는 메인 화면으로 여행 리스트를 담고 있음.
 */
public class TravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
    }
}