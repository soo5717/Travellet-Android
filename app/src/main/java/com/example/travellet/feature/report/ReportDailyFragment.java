package com.example.travellet.feature.report;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.travellet.R;
import com.example.travellet.data.responseBody.ReportDailyResponse;
import com.example.travellet.databinding.FragmentReportDailyBinding;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDailyFragment extends Fragment {
    private Context mContext; //Context 선언
    private FragmentReportDailyBinding binding; //바인딩 선언

    private int mChangeState = 0; //환율 변경 관련 변수 (0: priceTo, 1: priceKrw)
    private BarChart mBarChart; //차트 선언

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Fragment 뷰 바인딩
        binding = FragmentReportDailyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override //프래그먼트 생명 주기 가장 처음에 Context 지정
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //액티비티에서 전달 받은 값 받아오기
        if(getArguments() != null) {
            int travelId = getArguments().getInt("travel_id", 0);
            mChangeState = getArguments().getInt("changeState", 0);

            mBarChart = binding.chart; //차트 선언
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
                    ReportDailyResponse.Data result = response.body().getData();
                    setBarChart(result); //차트 설정
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
                binding.constraintLayout.setVisibility(View.VISIBLE); //화면 보이기
            }

            @Override
            public void onFailure(@NotNull Call<ReportDailyResponse> call, @NotNull Throwable t) {
                Log.e("일별 레포트 조회 요청", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    //차트 디자인 설정
    void setBarChartDesign() {
        //옵션 변수 선언
        float baseSpace = 0f;
        float bottomOffset = 16f;
        float granularity = 1f;

        //차트 옵션 (공통)
        mBarChart.getDescription().setEnabled(false); //설명
        mBarChart.setDoubleTapToZoomEnabled(false); //더블 클릭 확대
        mBarChart.setExtraBottomOffset(bottomOffset); //그래프 아래와 범례 사이 간격 => (default 10f)
        mBarChart.setDrawValueAboveBar(true); ///막대 위 값
        mBarChart.getAxisRight().setEnabled(false); //y축 오른쪽

        //차트 옵션 (y축 왼쪽)
        YAxis yAxis = mBarChart.getAxisLeft(); //y축 왼쪽
        yAxis.setDrawGridLines(false); //격자
        yAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.hint_grey)); //외곽선 색
        yAxis.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트
        yAxis.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
        yAxis.setSpaceBottom(baseSpace); //아래 여백

        //차트 옵션 (x축)
        XAxis xAxis = mBarChart.getXAxis(); //x축
        xAxis.setDrawGridLines(false); //격자
        xAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.hint_grey)); //외곽선 색
        xAxis.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트
        xAxis.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //포지션
        xAxis.setSpaceMin(baseSpace); //왼쪽 여백

        xAxis.setCenterAxisLabels(true); //라벨 가운데 정렬
        xAxis.setGranularity(granularity); //간격

        //범례 옵션
        Legend legend = mBarChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); //수직 위치
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //수평 위치
        legend.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트
        legend.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
    }

    //차트 설정
    void setBarChart(ReportDailyResponse.Data data) {
        //차트 디자인 설정
        setBarChartDesign();

        //옵션 변수 선언
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataSet
        float barWidth = 0.45f; // x2 dataSet
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        int groupCount = 12;
        float startPoint = 0f;
        float maxXRange = 7f;

        //차트 데이터 넣기 (막대 그래프 사용)
        ArrayList<BarEntry> barEntries1 = new ArrayList<>(); //현금 막대
        ArrayList<BarEntry> barEntries2 = new ArrayList<>(); //카드 막대
        ArrayList<String> xValues = new ArrayList<>(); //라벨

        for(int i=0; i < groupCount; i++) {
            barEntries1.add(new BarEntry(i, i*5));
            barEntries2.add(new BarEntry(i, i*10));
            xValues.add(String.format(Locale.getDefault(),"%d일", i));
        }

        //막대 차트 개별 데이터 설정 (현금/카드)
        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Cash"); //범례 라벨
        BarDataSet barDataSet2 = new BarDataSet(barEntries2, "Credit");  //범례 라벨
        barDataSet1.setColor(ContextCompat.getColor(mContext, R.color.text_blue)); //막대 컬러
        barDataSet2.setColor(ContextCompat.getColor(mContext, R.color.text_blue_10)); //막대 컬러

        //막대 그룹화
        BarData barData = new BarData(barDataSet1, barDataSet2); //그룹화
        mBarChart.setData(barData); //데이터 넣기
        mBarChart.getBarData().setBarWidth(barWidth); //막대 가로 사이즈
        mBarChart.groupBars(startPoint, groupSpace, barSpace); //명시적인 그룹화

        mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues)); //라벨 지정
        mBarChart.getXAxis().setAxisMinimum(startPoint); //x축 최솟값
        if(groupCount < maxXRange) //x축 최댓값
            mBarChart.getXAxis().setAxisMaximum(startPoint+barData.getGroupWidth(groupSpace, barSpace)*maxXRange);
        else
            mBarChart.getXAxis().setAxisMaximum(startPoint+barData.getGroupWidth(groupSpace, barSpace)*groupCount);

        mBarChart.setVisibleXRangeMaximum(maxXRange); //차트 최대 범위 지정 (스크롤)
        mBarChart.moveViewToX(startPoint); //시작점

        //새로고침 (필수!)
        mBarChart.invalidate();
    }
}