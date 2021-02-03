package com.example.travellet.feature.report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.travellet.R;
import com.example.travellet.data.responseBody.ReportCategoryResponse;
import com.example.travellet.feature.plan.PlanAdapter;
import com.example.travellet.feature.plan.PlanItem;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ReportCategoryFragment extends Fragment {
    int travelId = 0;
    int changeState = 0;

    TextView categoryText, expenseText, perText;
    View categoryColor;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    //파이차트 만들 때 필요한 애들
    ArrayList<PieEntry> perList = new ArrayList<>();
    PieChart categoryChart;
    PieDataSet dataSet;
    double totalExpense=0;

    //리사이클러뷰에 필요한 변수 선언
    RecyclerView categoryRecyclerView = null ;
    ReportCategoryAdapter categoryAdapter = null ;
    ArrayList<ReportCategoryItem> categoryItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    DecimalFormat formatter = new DecimalFormat("###,###.##");
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    String currency = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            travelId = getArguments().getInt("travel_id", 0);
            changeState = getArguments().getInt("changeState", 0);
            Log.d("fragment changeState", String.valueOf(changeState));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_report_category, container, false);

        //필요한 아이템들 findViewById
        categoryChart = rootView.findViewById(R.id.chart_category);
        categoryText = rootView.findViewById(R.id.text_category);
        expenseText = rootView.findViewById(R.id.text_expense);
        perText = rootView.findViewById(R.id.text_percent);
        categoryColor = rootView.findViewById(R.id.color_category);
        recyclerView = rootView.findViewById(R.id.recyclerview_category_report);
        progressBar = rootView.findViewById(R.id.progress_bar);

        //어댑터 관련 설정

        categoryItems.clear();
        categoryAdapter = new ReportCategoryAdapter(categoryItems);
        recyclerView.setAdapter(categoryAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //그래프에 넣을 리스트(perList) 모두 0으로 초기화
        perList.add(new PieEntry(0, "Lodging"));
        perList.add(new PieEntry(0, "Food"));
        perList.add(new PieEntry(0, "Shopping"));
        perList.add(new PieEntry(0, "Tourism"));
        perList.add(new PieEntry(0, "Transportation"));
        perList.add(new PieEntry(0, "Etc."));
        //카테고리별 지출 읽어오기 + 그래프 그리기
        requestReadCategoryGraph(changeState);

        return rootView;

    }

    public void setPieChart(){
        PieDataSet dataSet = new PieDataSet(perList, " ");

        PieData data = new PieData(dataSet);
        categoryChart.setData(data);
        int colors [] = {getResources().getColor(R.color.category1), getResources().getColor(R.color.category2), getResources().getColor(R.color.category3), getResources().getColor(R.color.category4), getResources().getColor(R.color.category5), getResources().getColor(R.color.category6)};
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);

        categoryChart.setTransparentCircleRadius(80); //가운데 원(투명원) 반경 설정
        Description desc = new Description();
        desc.setText(" ");
        categoryChart.setDescription(desc); // 오른쪽 하단 description 삭제
        Legend legend = categoryChart.getLegend();
        legend.setEnabled(false); //legend 안보이게
        categoryChart.setDrawSliceText(false);
        categoryChart.setUsePercentValues(false); //차트 백분율로 표시 x
        categoryChart.setHoleRadius(80); //가운데 원 반경 설정
        categoryChart.setTransparentCircleColor(R.color.colorPrimary); //투명 원의 색상을 설정
        categoryChart.invalidate(); //차트 새로고침.

        //그래프 값 선택했을 때 이벤트
        categoryChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                categoryColor.setVisibility(View.VISIBLE);
                perText.setVisibility(View.VISIBLE);
                perText.setText(Math.round((e.getY()/totalExpense)*100)+"%");

                //chageState에 따라서 금액 단위 변경해서 표시
                if(changeState == 0){
                    numberFormat.setCurrency(Currency.getInstance(currency));
                    expenseText.setText(numberFormat.format(e.getY()));
                } else
                    expenseText.setText(Currency.getInstance(Locale.KOREA).getSymbol()+formatter.format(e.getY()));

                int i = (int)h.getX();
                switch (i){
                    case 0:
                        categoryText.setText("LODGING");
                        break;
                    case 1:
                        categoryText.setText("FOOD");
                        break;
                    case 2:
                        categoryText.setText("SHOPPING");
                        break;
                    case 3:
                        categoryText.setText("TOURISM");
                        break;
                    case 4:
                        categoryText.setText("TRANSPORTATION");
                        break;
                    case 5:
                        categoryText.setText("ETC.");
                        break;
                }
                requestReadCategoryList(i+1, changeState);
                categoryColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onNothingSelected() {
                //화폐 단위 적용
                if(changeState == 0){
                    numberFormat.setCurrency(Currency.getInstance(currency));
                    //맨 처음 그래프 중앙 텍스트에 총 비용 보여주기
                    expenseText.setText(numberFormat.format(totalExpense));
                } else
                    expenseText.setText(Currency.getInstance(Locale.KOREA).getSymbol()+formatter.format(totalExpense));
                categoryText.setText("TOTAL EXPENSE");
                categoryColor.setVisibility(View.GONE);
                perText.setVisibility(View.GONE);
                categoryItems.clear();
                recyclerView.setAdapter(categoryAdapter);
            }
        });
    }

    //category adapter 아이템 추가 메소드
    public void addItem(String memo, String date, int category, int payment, String expense){
        ReportCategoryItem item = new ReportCategoryItem(memo, date, category, payment, expense);
        item.setDate(date);
        item.setMemo(memo);
        item.setCategory(category);
        item.setPayment(payment);
        item.setExpense(expense);
        categoryItems.add(item);
    }

    //카테고리 레포트 조회 - GET: Retrofit2
    private void requestReadCategoryGraph(int changeState){
        totalExpense = 0;
        final double[] value = {0};
        ProgressBarManager.showProgress(progressBar, true);
        RetrofitClient.getService().readCategoryReport(travelId).enqueue(new Callback<ReportCategoryResponse>(){
            @Override
            public void onResponse(@NotNull Call<ReportCategoryResponse> call, @NotNull Response<ReportCategoryResponse> response) {
                if(response.isSuccessful()  && response.body() != null ){
                    ReportCategoryResponse result = response.body();
                    //화폐 단위
                    currency = result.getData().getCurrency();
                    //카테고리별로 그래프에 넣기(최대 6개)
                    for(int i=0; i<result.getData().getCategoryGraph().size(); i++){
                        if(changeState == 0)
                            value[0] = result.getData().getCategoryGraph().get(i).getPriceTo();
                        else
                            value[0] = Double.parseDouble(result.getData().getCategoryGraph().get(i).getPriceKrw());
                        //총 비용 계산하기
                        totalExpense += value[0];
                        int category = result.getData().getCategoryGraph().get(i).getCategory();
                        //카테고리별 금액 그래프에 넣기
                        switch (category){
                            case 1:
                                perList.set(category-1, new PieEntry((float) value[0], "Lodging"));
                                break;
                            case 2:
                                perList.set(category-1, new PieEntry((float) value[0], "Food"));
                                break;
                            case 3:
                                perList.set(category-1, new PieEntry((float) value[0], "Shopping"));
                                break;
                            case 4:
                                perList.set(category-1, new PieEntry((float) value[0], "Tourism"));
                                break;
                            case 5:
                                perList.set(category-1, new PieEntry((float) value[0], "Transportation"));
                                break;
                            case 6:
                                perList.set(category-1, new PieEntry((float) value[0], "Etc."));
                                break;
                        }
                    }
                   //화폐 단위 적용
                    if(changeState == 0){
                        numberFormat.setCurrency(Currency.getInstance(currency));
                        //맨 처음 그래프 중앙 텍스트에 총 비용 보여주기
                        expenseText.setText(numberFormat.format(totalExpense));
                    } else
                        expenseText.setText(Currency.getInstance(Locale.KOREA).getSymbol()+formatter.format(totalExpense));
                }
                //그래프에 넣은 데이터 토대로 그래프 그리기
                setPieChart();
                categoryText.setText("TOTAL EXPENSE");
                categoryColor.setVisibility(View.GONE);
                perText.setVisibility(View.GONE);
                categoryItems.clear();
                recyclerView.setAdapter(categoryAdapter);
                ProgressBarManager.showProgress(progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<ReportCategoryResponse> call, Throwable t) {

            }
        });
    }

    //카테고리별 지출 목록 조회 - GET: Retrofit2
    private void requestReadCategoryList(int category, int changeState){
        RetrofitClient.getService().readCategoryReport(travelId).enqueue(new Callback<ReportCategoryResponse>(){
            @Override
            public void onResponse(@NotNull Call<ReportCategoryResponse> call, @NotNull Response<ReportCategoryResponse> response) {
                if(response.isSuccessful()  && response.body() != null ){
                    ReportCategoryResponse result = response.body();
                    categoryItems.clear();
                    //카테고리별로 그래프에 넣기(최대 6개)
                    for(int i=0; i<result.getData().getCategoryList().size(); i++){
                        for(int j=0; j<result.getData().getCategoryList().get(i).size(); j++){
                            if(result.getData().getCategoryList().get(i).get(j).getCategory() != category)
                                break;
                            else{
                                String date = result.getData().getCategoryList().get(i).get(j).getDate();
                                String memo = result.getData().getCategoryList().get(i).get(j).getMemo();
                                int payment = result.getData().getCategoryList().get(i).get(j).getPayment();
                                if(changeState == 0){
                                    addItem(memo, date, category, payment, numberFormat.format(result.getData().getCategoryList().get(i).get(j).getPriceTo() ));
                                } else {
                                    addItem(memo, date, category, payment, Currency.getInstance(Locale.KOREA).getSymbol()+formatter.format(result.getData().getCategoryList().get(i).get(j).getPriceKrw()));
                                }
                            }
                        }
                    }
                    recyclerView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportCategoryResponse> call, Throwable t) {

            }
        });
    }

}