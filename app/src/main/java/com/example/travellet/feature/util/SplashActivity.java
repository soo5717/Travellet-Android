package com.example.travellet.feature.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.travellet.data.responseBody.ProfileResponse;
import com.example.travellet.feature.sign.SignInActivity;
import com.example.travellet.feature.travel.TravelActivity;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-20.
 * Class: SplashActivity
 * Description: 스플래쉬 클래스
 * 자동 로그인을 위한 토큰 검증 요청이 포함되어 있음.
 * => 일단은 회원정보 조회 API를 이용해서 구현함.
 * => 나중에 시간이 된다면 refresh 토큰도 추가하고 제대로 구현 할 것임!
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

    //토큰 검증 요청 - POST : Retrofit2
    private void requestAuth() {
        RetrofitClient.getService().readProfile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                Intent intent;
                //토큰 인증 성공 시 (원래는 인증 API 따로 만들어야하는데 나중에 할 예정..^^)
                if(response.isSuccessful() && response.body() != null) {
                    //여행 메인 페이지로 이동
                    intent = new Intent(SplashActivity.this, TravelActivity.class);
                } else { //토큰 인증 실패 시
                    //로그인 페이지로 이동
                    intent = new Intent(SplashActivity.this, SignInActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                Log.e("자동 로그인 에러", Objects.requireNonNull(t.getMessage()));
                //로그인 페이지로 이동
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}