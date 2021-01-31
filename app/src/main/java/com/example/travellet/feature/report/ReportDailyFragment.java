package com.example.travellet.feature.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travellet.data.responseBody.ReportDailyResponse;
import com.example.travellet.databinding.FragmentReportDailyBinding;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDailyFragment extends Fragment {
    private FragmentReportDailyBinding binding; //바인딩 선언

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Fragment 뷰 바인딩
        binding = FragmentReportDailyBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //액티비티에서 전달 받은 값 받아오기
        if(getArguments() != null) {
            int travelId = getArguments().getInt("travel_id");
            requestReportDaily(travelId); //일별 레포트 조회 요청
        }
    }

    //일별 레포트 조회 요청 - GET : Retrofit2
    void requestReportDaily(int travelId) {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readDaily(travelId).enqueue(new Callback<ReportDailyResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportDailyResponse> call, @NotNull Response<ReportDailyResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ReportDailyResponse result = response.body();
                    setBarChart(result);
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<ReportDailyResponse> call, @NotNull Throwable t) {
                Log.e("일별 레포트 조회 요청", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    void setBarChart(ReportDailyResponse data) {

    }
}