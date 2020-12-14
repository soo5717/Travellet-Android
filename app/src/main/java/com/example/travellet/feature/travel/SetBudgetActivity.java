package com.example.travellet.feature.travel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.SignUpData;
import com.example.travellet.data.requestBody.TravelCreateData;
import com.example.travellet.data.responseBody.TravelCreateResponse;
import com.example.travellet.databinding.ActivitySetBudgetBinding;
import com.example.travellet.databinding.ActivitySignUp1Binding;
import com.example.travellet.feature.place.PlaceActivity;
import com.example.travellet.feature.plan.AddPlaceActivity;
import com.example.travellet.feature.plan.AddPlanActivity;
import com.example.travellet.feature.sign.SignInActivity;
import com.example.travellet.feature.sign.SignUp2Activity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetBudgetActivity extends BaseActivity {
    private ActivitySetBudgetBinding binding; //바인딩 선언
    EditText Edittext_budget;
    Button btn_Next;

    String startDate, endDate;
    double budget;

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivitySetBudgetBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Edittext_budget = (EditText) findViewById(R.id.budget_edit_set_budget);
        btn_Next = (Button) findViewById(R.id.next_button_set_budget);
        //btn_back = (ImageButton) findViewById(R.id.back_button_set_budget);

        //next 버튼 누르면 다음 화면으로 가자
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetStr = Edittext_budget.getText().toString();
                //이전 화면에서 인텐트로 넘긴 정보 저장하기
                Intent intent2 = getIntent();
                int startYear = intent2.getIntExtra("startYear", 0); //여행 시작 날짜
                int startMonth = intent2.getIntExtra("startMonth", 0);
                int startDay = intent2.getIntExtra("startDay", 0);
                int endYear = intent2.getIntExtra("endYear", 0);//여행 종료 날짜
                int endMonth = intent2.getIntExtra("endMonth", 0);
                int endDay = intent2.getIntExtra("endDay", 0);
                String title = intent2.getStringExtra("travelTitle"); // 여행 타이틀

                //예산 입력하지 않으면 다음으로 넘어가지 못하게
                if(budgetStr == null || budgetStr.length() == 0){
                    Edittext_budget.setHintTextColor(getColor(R.color.coral_red));
                    //btn_Next.isClickable(false);
                }else{
                    //startDate, endDate 만들기
                    startDate = String.valueOf(startYear)+"-"+String.valueOf(startMonth)+"-"+String.valueOf(startDay);
                    endDate = String.valueOf(endYear)+"-"+String.valueOf(endMonth)+"-"+String.valueOf(endDay);
                    //budget도 Double type
                    budget = Double.parseDouble(budgetStr);

                    Log.d("여행 생성 중", title+" / "+startDate+ " / " + endDate + " / " + budgetStr);
                    reqeustCreateTravel(new TravelCreateData(title, startDate, endDate, budget));
                }

            }
        });
    }

    //여행 생성 - POST : Retrofit2
    private void reqeustCreateTravel(TravelCreateData data) {
        RetrofitClient.getService().createTravel(data).enqueue(new Callback<TravelCreateResponse>() {
            @Override
            public void onResponse(@NotNull Call<TravelCreateResponse> call, @NotNull Response<TravelCreateResponse> response) {
                if(response.isSuccessful()) { //상태코드 200~300일 경우 (요청 성공 시)
                    TravelCreateResponse result = response.body();
                    Toast.makeText(SetBudgetActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    //여행 메인 페이지로 이동
                    Intent intent = new Intent(SetBudgetActivity.this, TravelActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TravelCreateResponse> call, @NotNull Throwable t) {
                Toast.makeText(SetBudgetActivity.this, "Travel Create Error", Toast.LENGTH_SHORT).show();
                Log.e("여행 생성 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}
