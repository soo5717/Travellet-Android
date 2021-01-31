package com.example.travellet.feature.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.travellet.databinding.ActivityReportBinding;
import com.example.travellet.feature.util.BaseActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 수연 on 2021-01-31.
 * Class: ReportActivity
 * Description: 레포트(일별, 카테고리별) 페이지
 */
public class ReportActivity extends BaseActivity {
    private ActivityReportBinding binding; //바인딩 선언

    private final String[] TABS = {"daily", "category"}; //탭 선언
    private final List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initButton(); //버튼 클릭 이벤트 설정
        initView(); //뷰 설정
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //뷰 설정 : 탭, 뷰페이저, 프래그먼트
    private void initView() {
        //프래그먼트 리스트에 추가
        fragmentList.add(new ReportDailyFragment());
        fragmentList.add(new ReportCategoryFragment());

        //뷰페이저 어댑터 설정
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });

        //탭과 뷰페이저 연결
        new TabLayoutMediator(binding.tabs, binding.viewPager2, (tab, position) -> tab.setText(TABS[position])).attach();
    }

    //버튼 클릭 이벤트 설정
    private void initButton() {
        binding.buttonChange.setOnClickListener(v -> {
            //버튼 이벤트 추후 추가 예정
        });
    }
}