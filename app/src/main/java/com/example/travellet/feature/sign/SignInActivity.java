package com.example.travellet.feature.sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travellet.data.sign.SignInData;
import com.example.travellet.data.sign.SignInResponse;
import com.example.travellet.databinding.ActivitySignInBinding;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignInActivity (네트워킹 미완료)
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

        //EditText 입력 이벤트 (유효성 검증)
        mSignValidation = new SignValidation();
        mSignValidation.emailTextChanged(binding.editTextEmail);
        mSignValidation.pwdTextChanged(binding.editTextPwd);
    }

    //로그인 요청 - POST : Retrofit2 => 미완성 코드
    private void requestSignIn(SignInData data){
        RetrofitClient.getService().userSignIn(data).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignInResponse> call, @NotNull Response<SignInResponse> response) {

            }

            @Override
            public void onFailure(@NotNull Call<SignInResponse> call, @NotNull Throwable t) {
                Toast.makeText(SignInActivity.this, "SignIn Error", Toast.LENGTH_SHORT).show();
                Log.e("SignIn Error", Objects.requireNonNull(t.getMessage()));
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
//            requestSignIn(new SignInData(email, encryptPwd));

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
