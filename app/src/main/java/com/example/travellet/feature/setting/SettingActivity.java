package com.example.travellet.feature.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travellet.R;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SettingActivity (기능 미완료)
 * Description: 마이페이지 클래스
 * 사용자 정보에 대한 READ, UPDATE, DELETE 가능
 * => 이름 다이얼로그 디자인 수정 필요!!
 */
public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding; //바인딩 선언
    private String mName, mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 구현
        setNameAlertDialog();
        setCountryAlertDialog();

        //회원정보 요청
        requestReadProfile();
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

    //회원정보 수정 요청 - PUT : Retrofit2
    private void requestUpdateProfile(ProfileData data) {
        RetrofitClient.getService().updateProfile(data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    binding.textViewName.setText(mName);
                    binding.textViewCountry.setText(mCountry);
                } else {
                    StatusResponse result = ErrorBodyManager.parseError(response);
                    Toast.makeText(SettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Toast.makeText(SettingActivity.this, "회원정보 수정 에러", Toast.LENGTH_SHORT).show();
                Log.e("회원정보 수정 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //TODO (suyeon) : 디자인 수정
    //NameEdit 다이얼로그 설정
    void setNameAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EdiiText 설정
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(editText);
        //다이얼로그 OK 버튼 클릭 이벤트
        builder.setPositiveButton("OK", (dialog, which) -> {
            mName = editText.getText().toString();
            mCountry = binding.textViewCountry.getText().toString();

            //회원정보 수정 요청 메소드 호출
            requestUpdateProfile(new ProfileData(mName, mCountry));
        });
        //다이얼로그 Cancel 버튼 클릭 이벤트
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        //NameEdit 버튼 클릭 이벤트
        final AlertDialog alertDialog = builder.create();
        binding.buttonNameEdit.setOnClickListener(v -> alertDialog.show());
    }

    //CountryEdit 다이얼로그 설정
    void setCountryAlertDialog() {
        //String-Array -> ArrayList로 변환
        ArrayList<String> arrayListCountry =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country)));
        //CountryEdit 다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.country, (dialog, which) -> {
            mName = binding.textViewName.getText().toString();
            mCountry = arrayListCountry.get(which);

            //회원정보 수정 요청 메소드 호출
            requestUpdateProfile(new ProfileData(mName, mCountry));
        });
        //CountryEdit 버튼 이벤트
        final AlertDialog alertDialog = builder.create();
        binding.buttonCountryEdit.setOnClickListener(v -> alertDialog.show());
    }

    //SignOut 버튼 클릭 이벤트 : 로그아웃
    public void signOutButtonClick(View view) {
        PreferenceManager.removeKey("user_token"); //Token 삭제
        //로그인 페이지로 이동 => 스택 비우는 코드 추가 필요!!! 이게 맞는 걸까?
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //DeleteAccount 버튼 클릭 이벤트 : 회원탈퇴
    public void deleteAccountButtonClick(View view) {
        //Alert을 한 번 띄워야할까? 나중에 고민하자..^^
        //회원탈퇴 요청 메소드 호출
        requestDeleteProfile();
    }
}