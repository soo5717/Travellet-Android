package com.example.travellet.feature.place;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travellet.R;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceActivity extends AppCompatActivity {
    //탭 리스트
    List<String> tabList = Arrays.asList("all", "lodging", "food", "shopping",
                                                    "tourism", "culture","leisure");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        initToolbar();
        initTab();
    }

    //툴바 초기화 메소드
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_place);
        setSupportActionBar(toolbar);

        //추가적으로 searchView 구현 필요함.
    }

    //탭 초기화 메소드
    private void initTab() {
        TabLayout tabLayout = findViewById(R.id.tab_place);
        //탭 레이아웃에 탭 추가
        for(int i=0; i<tabList.size(); i++)
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
    }
}