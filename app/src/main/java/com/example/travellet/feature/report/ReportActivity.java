package com.example.travellet.feature.report;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travellet.R;

public class ReportActivity extends AppCompatActivity {
    private final String[] TABS = {"DAILY", "CATEGORY"}; //탭 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}