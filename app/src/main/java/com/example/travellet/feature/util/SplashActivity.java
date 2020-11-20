package com.example.travellet.feature.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.travellet.feature.sign.SignInActivity;

/**
 * Created by 수연 on 2020-11-20.
 * Class: SplashActivity
 * Description: 스플래쉬 클래스
 * 자동 로그인을 위한 토큰 검증 요청이 포함되어 있음.
 * => 서버 구현하면서 토큰 검증 요청 코드 추가해야 함.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Splash에 임의로 지연 시간 추가 0.5초
        Handler handler = new Handler(Looper.getMainLooper());
        //토큰 검증 요청 메소드 호출
        handler.postDelayed(this::requestAuth, 500);
    }

    /** 추후 추가해야 할 부분 */
    //토큰 검증 요청 - POST : Retrofit2
    private void requestAuth() {
        //토큰 인증 실패 시 -> 로그인 페이지로 이동
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
        //토큰 인증 성공 시 -> 메인 페이지로 이동
    }
}