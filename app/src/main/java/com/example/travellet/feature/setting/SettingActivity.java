package com.example.travellet.feature.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.ProfileData;
import com.example.travellet.data.responseBody.ProfileResponse;
import com.example.travellet.databinding.ActivitySettingBinding;
import com.example.travellet.feature.sign.SignInActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.ErrorBodyManager;
import com.example.travellet.feature.util.PreferenceManager;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SettingActivity
 * Description: 마이페이지 클래스
 * 사용자 정보에 대한 READ, UPDATE, DELETE 가능
 * => 이름 다이얼로그 디자인 수정 필요!!
 */
public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding; //바인딩 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestReadProfile(); //회원정보 요청
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //회원정보 요청 - GET : Retroifit2
    private void requestReadProfile() {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readProfile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                if(response.isSuccessful() && response.body() != null) { //상태코드 200~300일 경우 (요청 성공 시)
                    ProfileResponse result = response.body();
                    //프로필 setText
                    binding.textViewName.setText(result.getData().getName());
                    binding.textViewCountry.setText(result.getData().getCountry());
                    binding.textViewEmail.setText(result.getData().getEmail());
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                Log.e("프로필 조회 에러", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    //회원탈퇴 요청 - DELETE : Retrofit2
    private void requestDeleteProfile() {
        RetrofitClient.getService().deleteProfile().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    StatusResponse result = response.body();
                    Toast.makeText(SettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    //로그인 페이지로 이동
                    Intent intent = new Intent(SettingActivity.this, SignInActivity.class);
                    //엑티비티 스택에서 제거하고 로그인만 남김
                    //TODO (suyeon) : 액티비티 스택 관련 조사 필요 => 제대로 된 코드 아닐 수도 있음.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    //회원탈퇴 실패 시 토스트 메세지
                    StatusResponse result = ErrorBodyManager.parseError(response);
                    Toast.makeText(SettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Toast.makeText(SettingActivity.this, "회원탈퇴 에러", Toast.LENGTH_SHORT).show();
                Log.e("회원탈퇴 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //SignOut 버튼 클릭 이벤트 : 로그아웃
    public void signOutButtonClick(View view) {
        PreferenceManager.removeKey("user_token"); //Token 삭제
        //로그인 페이지로 이동 => 스택 비우는 코드
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //DeleteAccount 버튼 클릭 이벤트 : 회원탈퇴
    public void deleteAccountButtonClick(View view) {
        //회원탈퇴 요청 메소드 호출
        requestDeleteProfile();
    }
}