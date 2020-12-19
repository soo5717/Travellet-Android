package com.example.travellet.feature.plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.travellet.R;
import com.example.travellet.data.requestBody.PlanCreateData;
import com.example.travellet.data.responseBody.PlanCreateResponse;
import com.example.travellet.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    //탭 리스트
    List<String> tabList = Arrays.asList("ALL", "29", "30", "31", "01", "02", "03", "04", "05", "06", "07");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_plan);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        //Title 사용 안함
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initTab();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_plan, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    //탭 초기화 메소드
    private void initTab() {
        TabLayout tabLayout = findViewById(R.id.tab_plan);
        //탭 레이아웃에 탭 추가
        for(int i=0; i<tabList.size(); i++)
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));

        //탭 선택 이벤트 처리리
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시
            }
        });
    }

    private void requestCreatePlan(PlanCreateData data){
        RetrofitClient.getService().createPlan(data).enqueue(new Callback<PlanCreateResponse>() {
            @Override
            public void onResponse(Call<PlanCreateResponse> call, Response<PlanCreateResponse> response) {
                if(response.isSuccessful()) {
                    PlanCreateResponse result = response.body();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<PlanCreateResponse> call, Throwable t) {

            }
        });
    }
}