package com.example.travellet.feature.travel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.responseBody.TravelReadResponse;
import com.example.travellet.databinding.ActivityTravelBinding;
import com.example.travellet.feature.place.PlaceActivity;
import com.example.travellet.feature.setting.SettingActivity;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.feature.util.TravelUtil;
import com.example.travellet.feature.util.viewpager.ViewPagerAdapter;
import com.example.travellet.feature.util.viewpager.ViewPagerCase;
import com.example.travellet.feature.util.viewpager.ViewPagerData;
import com.example.travellet.network.RetrofitClient;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-22.
 * Class: TravelActivity
 * Description: 메인 클래스
 * 로그인 후 바로 보이는 메인 화면으로 여행 목록를 담고 있음.
 */
public class TravelActivity extends AppCompatActivity {
    private final String[] TABS = {"upcoming", "past"}; //탭 선언
    ArrayList<ViewPagerData> mList = new ArrayList<>(); //뷰페이저 리스트
    private ActivityTravelBinding binding; //바인딩 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity 뷰 바인딩
        binding = ActivityTravelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        requestReadTravel(); //여행 목록 조회 요청
        initButton(); //버튼 클릭 이벤트 설정
    }

    //버튼 클릭 이벤트 설정
    private void initButton() {
        //Add 버튼 클릭 이벤트 : 여행 추가 페이지로 이동
        binding.buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetTitleActivity.class);
            startActivity(intent);
        });
        //Search 버튼 클릭 이벤트 : 장소 검색 페이지로 이동
        binding.buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            startActivity(intent);
        });
        //Setting 버튼 클릭 이벤트 : 설정 페이지로 이동
        binding.buttonSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
    }

    //리사이클러뷰 어댑터 설정
    private void setTravelList(TravelReadResponse.Data data) {
        ArrayList<TravelReadResponse.Data.Travel> upcoming = new ArrayList<>(data.getUpcoming());
        ArrayList<TravelReadResponse.Data.Travel> past = new ArrayList<>(data.getPast());

        //어댑터 설정 + 뷰페이저/탭 스와이프 설정
        mList.add(new ViewPagerData(TravelActivity.this, upcoming));
        mList.add(new ViewPagerData(TravelActivity.this, past));
        binding.viewPager2.setAdapter(new ViewPagerAdapter(mList, ViewPagerCase.TRAVEL));
        new TabLayoutMediator(binding.tabs, binding.viewPager2, (tab, position) -> tab.setText(TABS[position])).attach();
    }

    //여행 목록 조회 요청(Upcoming/Past) - GET : Retrofit2
    private void requestReadTravel() {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readTravel(new TravelUtil().getToday()).enqueue(new Callback<TravelReadResponse>() {
            @Override
            public void onResponse(@NotNull Call<TravelReadResponse> call, @NotNull Response<TravelReadResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    TravelReadResponse result = response.body();
                    setTravelList(result.getData());
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<TravelReadResponse> call, @NotNull Throwable t) {
                Log.e("여행 목록 조회 에러", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }
}