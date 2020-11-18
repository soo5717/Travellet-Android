package com.example.travellet.feature.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.travellet.R;

/**
 * Created by 수연 on 2020-11-18.
 * Class: BaseActivity
 * Description: Toolbar Setting을 위한 추상 클래스 (뒤로가기 버튼 있는 툴바)
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        configureToolbar();
    }

    //Layout Resource 반환
    protected abstract View getLayoutResource();

    private void configureToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar); //Toolbar 사용

            //Title 사용 안함
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //Back button 활성화 및 아이콘 변경
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_backspace_24dp);
        }
    }

    @Override //Back button 동작 설정 : 뒤로가기
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //Back button id
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}