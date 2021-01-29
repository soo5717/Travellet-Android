package com.example.travellet.feature.plan.distribute;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.travellet.data.responseBody.DistributeBudgetResponse;
import com.example.travellet.databinding.ActivityDistributeBudgetBinding;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.network.RetrofitClient;

import java.util.ArrayList;

public class DistributeBudgetActivity extends BaseActivity {
    private ActivityDistributeBudgetBinding binding;

    //리사이클러뷰에 필요한 변수 선언
    DistributeBudgetAdapter budgetAdapter = null ;
    ArrayList<DistributeBudgetItem> budgetItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetAdapter = new DistributeBudgetAdapter(budgetItems);
        binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewDistributeBudget.setLayoutManager(layoutManager);

        budgetAdapter.setOnSeekbarChangeListener(
                (s, position) -> {

                }
        );
        requestReadDistribute();
        binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityDistributeBudgetBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //budget 어댑터 아이템 추가
    public void addItem(String category, double budget) {
        DistributeBudgetItem item = new DistributeBudgetItem(category, budget);
        item.setCategory(category);
        item.setBudget(budget);
        budgetItems.add(item);
    }

    //예산 분배 조회 - GET: Retrofit2
    private void requestReadDistribute() {
        //TODO: travelid 바꾸기
        RetrofitClient.getService().readDistributeBudget(1).enqueue(new Callback<DistributeBudgetResponse>() {
            @Override
            public void onResponse(Call<DistributeBudgetResponse> call, Response<DistributeBudgetResponse> response) {
                if (response.isSuccessful()) {
                    DistributeBudgetResponse result = response.body();
                    Log.d("result", String.valueOf(result.getData().getCountCategory().get(0).getCategory()));
                    budgetItems.clear();
                    for(int i=0; i<result.getData().getCountCategory().size(); i++){
                        //예산 0인 애가 없는 카테고리는 표시하지 않음.
                        String category = "";
                        switch (result.getData().getCountCategory().get(i).getCategory()) {
                            case 1:
                                category = "LODGING - ";
                                break;
                            case 2:
                                category = "FOOD - ";
                                break;
                            case 3:
                                category = "SHOPPING - ";
                                break;
                            case 4:
                                category = "TOURISM - ";
                                break;
                            case 5:
                                category = "TRANSPORT - ";
                                break;
                            case 6:
                                category = "ETC. - ";

                        }
                        addItem(category + Integer.toString(result.getData().getCountCategory().get(i).getCount()), 0);

                    }
                    binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);
                }
            }

            @Override
            public void onFailure(Call<DistributeBudgetResponse> call, Throwable t) {

            }

        });
    }


}