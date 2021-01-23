package com.example.travellet.feature.sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.SignInData;
import com.example.travellet.data.responseBody.SignInResponse;
import com.example.travellet.databinding.ActivitySignInBinding;
import com.example.travellet.feature.travel.TravelActivity;
import com.example.travellet.feature.util.ErrorBodyManager;
import com.example.travellet.feature.util.PreferenceManager;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignInActivity
 * Description: 로그인 클래스 (유효성 검증 포함)
 */
public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding; //바인딩 선언
    private SignValidation mSignValidation; //클래스 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity 뷰 바인딩
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //EditText 포커스
        binding.editTextEmail.requestFocus();

        //EditText 입력 이벤트 (유효성 검증)
        mSignValidation = new SignValidation();
        mSignValidation.emailTextChanged(binding.editTextEmail);
        mSignValidation.pwdTextChanged(binding.editTextPwd);
    }

    //로그인 요청 - POST : Retrofit2
    private void requestSignIn(SignInData data) {
        RetrofitClient.getService().signIn(data).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignInResponse> call, @NotNull Response<SignInResponse> response) {
                if(response.isSuccessful() && response.body() != null) { //상태코드 200~300일 경우 (요청 성공 시)
                    SignInResponse result = response.body();
                    Toast.makeText(SignInActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    //Shared Preferences 토큰 저장
                    Log.d("TOKEN    :   ", result.getData().getAccessToken());
                    PreferenceManager.setString("user_token", result.getData().getAccessToken());
                    //여행 페이지로 이동
                    Intent intent = new Intent(SignInActivity.this, TravelActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //로그인 실패 시 토스트 메세지
                    StatusResponse result = ErrorBodyManager.parseError(response);
                    Toast.makeText(SignInActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SignInResponse> call, @NotNull Throwable t) {
                Toast.makeText(SignInActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //Ok 버튼 클릭 이벤트
    public void okButtonClick(View view) {
        String email, pwd;
        email = binding.editTextEmail.getText().toString();
        pwd = binding.editTextPwd.getText().toString();

        //이메일, 비밀번호 유효성 검증
        boolean isValidEmail, isValidPwd;
        isValidEmail = mSignValidation.isValidEmail(email);
        isValidPwd = mSignValidation.isVaildPwd(pwd);

        if (isValidEmail && isValidPwd) { //Success
            //비밀번호 암호화
            String encryptPwd = EncryptSHA512.encryptSHA512(pwd);
            Log.d("Encrypt Password: ", encryptPwd);

            //로그인 요청 메소드 호출
            requestSignIn(new SignInData(email, encryptPwd));

        } else if (isValidEmail) { //Email
            Toast.makeText(getApplicationContext(), "Please enter at least 6 digits for the password!", Toast.LENGTH_SHORT).show();
            binding.editTextPwd.requestFocus();
        } else if (isValidPwd) { //Pwd
            Toast.makeText(getApplicationContext(), "Please enter it in email format!", Toast.LENGTH_SHORT).show();
            binding.editTextEmail.requestFocus();
        } else { //Fail
            Toast.makeText(getApplicationContext(), "Please enter it according to the format!", Toast.LENGTH_SHORT).show();
            binding.editTextEmail.requestFocus();
        }
    }

    //SignUp 버튼 클릭 이벤트 : 회원가입1 페이지로 이동
    public void signUpButtonClick(View view) {
        Intent intent = new Intent(this, SignUp1Activity.class);
        startActivity(intent);
    }
}
