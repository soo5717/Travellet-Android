package com.example.travellet.feature.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travellet.R;
import com.example.travellet.databinding.ActivitySetBudgetBinding;
import com.example.travellet.databinding.ActivitySignUp1Binding;
import com.example.travellet.feature.place.PlaceActivity;
import com.example.travellet.feature.plan.AddPlaceActivity;
import com.example.travellet.feature.plan.AddPlanActivity;
import com.example.travellet.feature.util.BaseActivity;

public class SetBudgetActivity extends BaseActivity {
    private ActivitySetBudgetBinding binding; //바인딩 선언
    EditText Edittext_budget;
    Button btn_Next;
    ImageButton btn_back;
    String budget;

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
                String budget = Edittext_budget.getText().toString();
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
                if(budget == null || budget.length() == 0){
                    Edittext_budget.setHintTextColor(getColor(R.color.coral_red));
                    //btn_Next.isClickable(false);
                }else{
                    Intent intent = new Intent(SetBudgetActivity.this, PlaceActivity.class); //나중에 내비게이션으로 변경

                    intent.putExtra("startYear", startYear);
                    intent.putExtra("startMonth", startMonth);
                    intent.putExtra("startDay", startDay);
                    intent.putExtra("endYear", endYear);
                    intent.putExtra("endMonth", endMonth);
                    intent.putExtra("endDay", endDay);
                    intent.putExtra("travelTitle", title);
                    //Toast.makeText(getApplicationContext(), title+"\n"+ startYear+" "+startMonth+" "+startDay+" "+startDoW+
                    //       "\n"+endYear+" "+endMonth+" "+endDay+" "+endDoW
                    //      +"\n"+budgetType+"\n"+budget, Toast.LENGTH_LONG).show();

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            }
        });
    }

}
