package com.example.travellet.feature.plan.distribute;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.DistributeBudgetData;
import com.example.travellet.data.responseBody.DistributeBudgetResponse;
import com.example.travellet.databinding.ActivityDistributeBudgetBinding;
import com.example.travellet.feature.plan.PlanActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class DistributeBudgetActivity extends BaseActivity {
    private ActivityDistributeBudgetBinding binding;

    //리사이클러뷰에 필요한 변수 선언
    DistributeBudgetAdapter budgetAdapter = null ;
    ArrayList<DistributeBudgetItem> budgetItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    //여행 아이디
    int travelId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //여행 아이디 받아오기
        Intent intent = getIntent();
        travelId = intent.getIntExtra("travelId", 0);

        //라사이클럽 어댑터
        budgetAdapter = new DistributeBudgetAdapter(budgetItems);
        binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewDistributeBudget.setLayoutManager(layoutManager);

        //리사이클러뷰 시크바 이벤트
        budgetAdapter.setOnSeekbarChangeListener(
                (s, position) -> {
                    binding.textTotalBudget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " +Double.toString(budgetAdapter.getTotalBudget()));
                }
        );

        //예산 분배 서버에서 읽어오기
        requestReadDistribute();
        binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);

        //OK버튼 누르면 예산 분배
        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distribution();
                finish();
            }
        });
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityDistributeBudgetBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //budget 어댑터 아이템 추가
    public void addItem(int category, int count, double budget) {
        DistributeBudgetItem item = new DistributeBudgetItem(category, count, budget);
        item.setCategory(category);
        item.setCount(count);
        item.setBudget(budget);
        budgetItems.add(item);
    }

    //예산 분배해주는 메소드
    public void distribution(){
        int lodgingBudget=0, foodBudget=0, shoppingBudget=0, tourismBudget=0, etcBudget=0;
        for(int i=0; i<budgetItems.size(); i++) {
            switch (budgetItems.get(i).getCategory()) {
                case 1:
                    lodgingBudget = (int)(budgetItems.get(i).getBudget() / budgetItems.get(i).getCount());
                case 2:
                    foodBudget = (int)(budgetItems.get(i).getBudget() / budgetItems.get(i).getCount());
                case 3:
                    shoppingBudget = (int)(budgetItems.get(i).getBudget() / budgetItems.get(i).getCount());
                case 4:
                    tourismBudget = (int)(budgetItems.get(i).getBudget() / budgetItems.get(i).getCount());
                case 6:
                    etcBudget = (int)(budgetItems.get(i).getBudget() / budgetItems.get(i).getCount());
            }
        }
        requestUpdateDistribute(lodgingBudget, foodBudget, shoppingBudget, tourismBudget, etcBudget);
    }

    //예산 분배 조회 - GET: Retrofit2
    private void requestReadDistribute() {
        RetrofitClient.getService().readDistributeBudget(travelId).enqueue(new Callback<DistributeBudgetResponse>() {
            @Override
            public void onResponse(Call<DistributeBudgetResponse> call, Response<DistributeBudgetResponse> response) {
                if (response.isSuccessful()) {
                    DistributeBudgetResponse result = response.body();
                    binding.textTotalBudget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " + result.getData().getSumBudget());
                    budgetAdapter.setTotalBudget(result.getData().getSumBudget());
                    budgetItems.clear();
                    for(int i=0; i<result.getData().getCountCategory().size(); i++){
                        addItem(result.getData().getCountCategory().get(i).getCategory(), result.getData().getCountCategory().get(i).getCount(), 0);

                    }
                    binding.recyclerViewDistributeBudget.setAdapter(budgetAdapter);
                }
            }

            @Override
            public void onFailure(Call<DistributeBudgetResponse> call, Throwable t) {

            }

        });
    }

    // 예산 분배 수정 - PATCH: Retrofit2
    private void requestUpdateDistribute(int lodging, int food, int shopping, int tourism, int etc){
        RetrofitClient.getService().updateDistributeBudget(new DistributeBudgetData(travelId, lodging, food, shopping, tourism, etc)).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(DistributeBudgetActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                Log.e("distribution failure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}