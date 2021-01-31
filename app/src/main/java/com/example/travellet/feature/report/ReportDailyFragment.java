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
import com.example.travellet.feature.util.BarChartValueFormatter;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDailyFragment extends Fragment {
    private Context mContext; //Context 선언
    private FragmentReportDailyBinding binding; //바인딩 선언

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
            int travelId = getArguments().getInt("travel_id");
            requestReportDaily(travelId); //일별 레포트 조회 요청
        }
    }

    //일별 레포트 조회 요청 - GET : Retrofit2
    void requestReportDaily(int travelId) {
        ProgressBarManager.showProgress(binding.progressBar, true); //TODO (syeon) : 로딩 중에 뒤에 보이지 않게 하자
        RetrofitClient.getService().readDaily(travelId).enqueue(new Callback<ReportDailyResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportDailyResponse> call, @NotNull Response<ReportDailyResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ReportDailyResponse.Data result = response.body().getData();
                    setBarChartData(result);
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

    void setBarChartData(ReportDailyResponse.Data data) {
        //응답 데이터 변수에 저장
        ArrayList<ReportDailyResponse.Data.Chart> chart = new ArrayList<>(data.getChart());
//        ArrayList<String> date = new ArrayList<>(data.getDate());
//        ArrayList<List<ReportDailyResponse.Data.Item>> items = new ArrayList<>(data.getItem());

//        ArrayList<String> xValues = new ArrayList<>();

        //차트 데이터 넣기 (막대 그래프 사용)
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries1.add(new BarEntry(i, chart.get(i).getPriceTo()));
//            xValues.add(chart.get(i).getDate().substring(chart.get(i).getDate().length()-5));
        }
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries1.add(new BarEntry(i+4, chart.get(i).getPriceTo()));
//            xValues.add(chart.get(i).getDate().substring(chart.get(i).getDate().length()-5));
        }
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries1.add(new BarEntry(i+8, chart.get(i).getPriceTo()));
//            xValues.add(chart.get(i).getDate().substring(chart.get(i).getDate().length()-5));
        }
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries2.add(new BarEntry(i, Float.parseFloat(chart.get(i).getPriceKrw())));
        }
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries2.add(new BarEntry(i+4, Float.parseFloat(chart.get(i).getPriceKrw())));
        }
        for(int i=0; i < chart.size(); i++) {
            //차트의 x, y 좌표 리스트 추가
            barEntries2.add(new BarEntry(i+8, Float.parseFloat(chart.get(i).getPriceKrw())));
        }

        //차트 선언
        BarChart barChart = binding.chart;

        //차트 옵션 (공통)
        barChart.getAxisRight().setEnabled(false); //y축 오른쪽
        barChart.getDescription().setEnabled(false); //설명
        barChart.setDrawValueAboveBar(true); ///막대 위 값
        barChart.setDoubleTapToZoomEnabled(false); //더블 클릭 확대
        barChart.setExtraBottomOffset(16f); //그래프 아래와 범례 사이 간격
//        barChart.setMaxVisibleValueCount(2);

        //차트 옵션 (y축 왼쪽)
        YAxis yAxis = barChart.getAxisLeft(); //y축 왼쪽
        yAxis.setDrawGridLines(false); //격자
        yAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.hint_grey)); //외곽선 색
        yAxis.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트
        yAxis.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
        yAxis.setSpaceBottom(0); //아래 여백

        //차트 옵션 (x축)
        XAxis xAxis = barChart.getXAxis(); //x축
        xAxis.setDrawGridLines(false); //격자
//        xAxis.setCenterAxisLabels(true); //라벨 가운데
        xAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.hint_grey)); //외곽선 색
        xAxis.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트
        xAxis.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
        xAxis.setSpaceMin(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //포지션
//        xAxis.setValueFormatter(new BarChartValueFormatter(xValues)); //x좌표 값

        //범례 옵션
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); //수직 위치
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //수평 위치
        legend.setTextColor(ContextCompat.getColor(mContext, R.color.icon_grey)); //텍스트 색
        legend.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_medium)); //폰트


        //막대 차트 개별 데이터 설정 (현금/카드)
        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Cash"); //범례 라벨
        BarDataSet barDataSet2 = new BarDataSet(barEntries2, "Credit");  //범례 라벨

        //막대 차트 옵션
        barDataSet1.setColor(ContextCompat.getColor(mContext, R.color.text_blue)); //컬러
        barDataSet2.setColor(ContextCompat.getColor(mContext, R.color.text_blue_10)); //컬러

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataSet
        float barWidth = 0.45f; // x2 dataSet
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        //막대 차트 최종 데이터 설정
        BarData barData = new BarData(barDataSet1, barDataSet2);
        barData.setBarWidth(barWidth);
        barChart.setData(barData);
        barChart.groupBars(0f, groupSpace, barSpace);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f); //표시 단위
        xAxis.setCenterAxisLabels(true); //라벨 가운데
        barChart.fitScreen();
        barChart.centerViewTo(barChart.getXChartMax(), 0, YAxis.AxisDependency.RIGHT);


        barChart.setVisibleXRangeMaximum(7);
        barChart.moveViewToX(1);
//        barChart.setHorizontalScrollBarEnabled(true);

        barChart.invalidate(); //새로 고침하면 적용 됨 (필수!)
    }
}