package com.example.travellet.feature.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.travellet.R;
import com.example.travellet.databinding.ActivityPlanDetailBinding;
import com.example.travellet.feature.util.BaseActivity;

public class PlanDetailActivity extends BaseActivity {
    private ActivityPlanDetailBinding binding; //바인딩 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityPlanDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}