package com.example.travellet.feature.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.responseBody.PlanDetailResponse;
import com.example.travellet.data.viewpager.PlanDetailViewPagerData;
import com.example.travellet.data.viewpager.ViewPagerCase;
import com.example.travellet.databinding.ActivityPlanDetailBinding;
import com.example.travellet.feature.detail.budget.AddBudgetActivity;
import com.example.travellet.feature.detail.expense.AddExpenseActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.feature.util.ResultCode;
import com.example.travellet.network.RetrofitClient;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2021-01-29.
 * Class: PlanDetailActivity
 * Description: 일정 세부 조회(예산/지출) 페이지
 */
public class PlanDetailActivity extends BaseActivity implements ResultCode {
    private ActivityPlanDetailBinding binding; //바인딩 선언

    private final String[] TABS = {"budget", "expense"}; //탭 선언
    ArrayList<PlanDetailViewPagerData> mList = new ArrayList<>(); //뷰페이저 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PlanId 받아오기
        Intent intent = getIntent();
        int planId= intent.getIntExtra("plan_id", 0);

        if(planId != 0) {
            initButton(planId); //버튼 클릭 이벤트 설정
            requestReadPlanDetail(planId); //일정 조회 요청
        }
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityPlanDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    
    //리사이클러뷰 어댑터 설정
    private void setPlanDetailList(PlanDetailResponse.Data data, int id) {
        String place = data.getPlace();
        String memo = data.getMemo();
        String startDate = data.getTravelStartDate();
        String date = data.getDate();
        int totalBudget = data.getSumBudget();
        int totalExpense = data.getSumExpense();
        String currency = data.getCurrency();
        ArrayList<PlanDetailResponse.Data.Datum> budgets = new ArrayList<>(data.getBudget());
        ArrayList<PlanDetailResponse.Data.Datum> expenses = new ArrayList<>(data.getExpense());

        mList.add(new PlanDetailViewPagerData(this, ViewPagerCase.BUDGET, id, place, memo, startDate, date, totalBudget, currency, budgets));
        mList.add(new PlanDetailViewPagerData(this, ViewPagerCase.EXPENSE, id, place, memo, startDate, date, totalExpense, currency, expenses));
        binding.viewPager2.setAdapter(new PlanDetailViewPagerAdapter(mList));
        new TabLayoutMediator(binding.tabs, binding.viewPager2, ((tab, position) -> tab.setText(TABS[position]))).attach();
    }

    //일정 조회 요청 - GET : Retrofit2
    void requestReadPlanDetail(int id) {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readPlanDetail(id).enqueue(new Callback<PlanDetailResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlanDetailResponse> call, @NotNull Response<PlanDetailResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    PlanDetailResponse result = response.body();
                    setPlanDetailList(result.getData(), id);
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<PlanDetailResponse> call, @NotNull Throwable t) {
                Log.e("일정 조회 에러", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    //예산 삭제 요청 - DELETE : Retrofit2
    void requestDeleteBudget(int id, PlanDetailAdapter planDetailAdapter, int pos) {
        RetrofitClient.getService().deleteBudget(id).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    planDetailAdapter.removeItem(pos);
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Log.d("예산 삭제 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //지출 삭제 요청 - DELETE : Retroift2
    void requestDeleteExpense(int id, PlanDetailAdapter planDetailAdapter, int pos) {
        RetrofitClient.getService().deleteExpense(id).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    planDetailAdapter.removeItem(pos);
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Log.d("지출 삭제 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //버튼 클릭 이벤트 설정
    private void initButton(int id) {
        //Add 버튼 클릭 이벤트 : 예산, 지출 추가 페이지로 이동
        binding.buttonAdd.setOnClickListener(v -> {
            Intent intent;
            switch (binding.tabs.getSelectedTabPosition()) {
                case 0: // 예산 탭일 경우 예산 추가 페이지로 이동
                    intent = new Intent(this, AddBudgetActivity.class);
                    intent.putExtra("plan_id", id);
                    startActivityForResult(intent, DETAIL_PLAN_RESULT);
                    break;
                case 1: // 지출 탭일 경우 지출 추가 페이지로 이동
                    intent = new Intent(this, AddExpenseActivity.class);
                    intent.putExtra("plan_id", id);
                    startActivityForResult(intent, DETAIL_PLAN_RESULT);
                    break;
            }
        });
    }

    //뒤로 가기 버튼(다시 plan 화면으로)
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }
}