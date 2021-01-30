package com.example.travellet.feature.plan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.BudgetData;
import com.example.travellet.data.requestBody.PlanData;
import com.example.travellet.data.responseBody.ExchangeRateResponse;
import com.example.travellet.data.responseBody.PlanCreateResponse;
import com.example.travellet.databinding.ActivityAddPlanBinding;
import com.example.travellet.feature.detail.PlanDetailActivity;
import com.example.travellet.feature.detail.budget.AddBudgetActivity;
import com.example.travellet.feature.plan.AddPlace.AddPlaceActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.feature.util.ResultCode;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlanActivity extends BaseActivity implements ResultCode {
    private ActivityAddPlanBinding binding;

    int travelId, planId, hour, min, transport, category; //category -> (1:lodging, 2:food, 3:shopping, 4:tourism, 5:etc)
    double x, y;
    String place, time, date;
    String memo="null";
    boolean editState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setAddPlanActivity();

        //place 선택 화면으로 이동
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
                startActivityForResult(intent, ADD_PLACE_RESULT);
            }
        });

        //type 선택 이벤트
        binding.btnLodging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 1;
                binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_selected_48dp);
                binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_48dp);
                binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_48dp);
                binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_48dp);
                binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_48dp);

            }
        });

        binding.btnFood.setOnClickListener((View.OnClickListener) v -> {
            category = 2;
            binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_48dp);
            binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_selected_48dp);
            binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_48dp);
            binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_48dp);
            binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_48dp);
        });

        binding.btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 3;
                binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_48dp);
                binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_48dp);
                binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_selected_48dp);
                binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_48dp);
                binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_48dp);

            }
        });

        binding.btnTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 4;
                binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_48dp);
                binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_48dp);
                binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_48dp);
                binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_selected_48dp);
                binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_48dp);

            }
        });
        binding.btnEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 6;
                binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_48dp);
                binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_48dp);
                binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_48dp);
                binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_48dp);
                binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_selected_48dp);
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(place == null){
                    binding.place.setHintTextColor(getColor(R.color.coral_red));
                }

                else
                    addPlaceAndBack();
            }
        });
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityAddPlanBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //add plan 화면 세팅하기(일정 추가인지 수정인지에 따라서)
    public void setAddPlanActivity(){
        //plan에서 화면에서 넘긴 데이터들(plan 수정시 세팅해놓을 것들)
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        Log.d("date", date);
        travelId = intent.getIntExtra("travelId", 0);
        Log.d("travel id", String.valueOf(travelId));
        planId = intent.getIntExtra("planId", 0);
        x = intent.getDoubleExtra("x", 0);
        y = intent.getDoubleExtra("y", 0);
        transport = intent.getIntExtra("transport", 0);
        //선택되어 있는 place 보여주기
        if(intent.getStringExtra("place")!=null){
            editState = true;
            binding.btnAdd.setText("EDIT");
            place = intent.getStringExtra("place");
            binding.place.setText(place);
            binding.place.setTextColor(getColor(R.color.soft_black));
        }
        //선택되어 있는 time 보여주기
        if(intent.getStringExtra("time")!=null){
            time = intent.getStringExtra("time");
            String[] arr = time.split(":"); //"00:00:00" string을 ':' 기준으로 split하고
            binding.time.setHour(Integer.parseInt(arr[0]));
            binding.time.setMinute(Integer.parseInt(arr[1]));
        }

        //입력되어 있는 메모 보여주기
        if(intent.getStringExtra("memo")!=null)
            binding.memo.setText(intent.getStringExtra("memo"));
        //선택되어 있는 type 보여주기
        category = intent.getIntExtra("type", 1);
        switch(category){
            case 1:
                binding.btnLodging.setBackgroundResource(R.drawable.ic_input_lodging_selected_48dp);
                break;
            case 2:
                binding.btnFood.setBackgroundResource(R.drawable.ic_input_food_selected_48dp);
                break;
            case 3:
                binding.btnShopping.setBackgroundResource(R.drawable.ic_input_shopping_selected_48dp);
                break;
            case 4:
                binding.btnTourism.setBackgroundResource(R.drawable.ic_input_tourism_selected_48dp);
                break;
            case 6:
                binding.btnEtc.setBackgroundResource(R.drawable.ic_input_etc_selected_48dp);
                break;
            default:
                break;
        }
    }

    //plan 목록 화면으로 저장한 plan data 전송
    public void addPlaceAndBack(){
        hour = binding.time.getHour();
        min = binding.time.getMinute();
        memo = binding.memo.getText().toString();
        if(memo.length()<1){
            switch (category){
                case 1:
                    memo = "Lodging";
                    break;

                case 2:
                    memo = "Food";
                    break;

                case 3:
                    memo = "Shopping";
                    break;

                case 4:
                    memo = "Tourism";
                    break;

                case 6:
                    memo = "etc";
                    break;

                default:
                    memo = "null";
                    break;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("title", place);
        intent.putExtra("memo", memo);
        intent.putExtra("hour", hour);
        intent.putExtra("min", min);
        intent.putExtra("type", category);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        intent.putExtra("planId", planId);

        setResult(RESULT_OK, intent);
        if(!editState) {
            requestCreatePlan(new PlanData(date, hour + ":" + min + ": ", place, memo, category, 1, x, y, travelId,
                    new PlanData.Budgets(memo, category), new PlanData.Budgets("Walk", 5)));
        }

        else {
            requestUpdatePlan(new PlanData(date, hour + ":" + min + ": ", place, memo, category, transport, x, y, travelId, null, null), planId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == ADD_PLACE_RESULT){
            if(intent != null){
                place = intent.getStringExtra("title");
                x = intent.getDoubleExtra("x", 0);
                y = intent.getDoubleExtra("y", 0);
                binding.place.setTextColor(getColor(R.color.soft_black));
                binding.place.setText(place);
            }
        }
    }

    //일정 생성 - POST: Retrofit2
    private void requestCreatePlan(PlanData data){
        RetrofitClient.getService().createPlan(travelId, data).enqueue(new Callback<PlanCreateResponse>() {
            @Override
            public void onResponse(Call<PlanCreateResponse> call, Response<PlanCreateResponse> response) {
                if(response.isSuccessful()) {
                    PlanCreateResponse result = response.body();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PlanCreateResponse> call, Throwable t) {
                Toast.makeText(AddPlanActivity.this, "Plan Create Error", Toast.LENGTH_SHORT).show();
                Log.e("일정 추가 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //일정 수정 - PUT : Retrofit2
    private void requestUpdatePlan(PlanData data, int planId){
        RetrofitClient.getService().updatePlan(planId, travelId, data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse result = response.body();
                finish();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(AddPlanActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                Log.e("일정 수정 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
