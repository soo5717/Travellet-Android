package com.example.travellet.feature.sign;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.travellet.R;
import com.example.travellet.data.requestBody.SignUpData;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.databinding.ActivitySignUp2Binding;
import com.example.travellet.feature.util.BaseActivity;
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
 * Class: SignUp2Activity
 * Description: 회원가입2 클래스
 */
public class SignUp2Activity extends BaseActivity {
    private ActivitySignUp2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EditText 포커스
        binding.editTextName.requestFocus();

        //다이얼로그 설정
        setCountryAlertDialog();
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //회원가입 요청 - POST : Retrofit2
    private void reqeustSignUp(SignUpData data) {
        RetrofitClient.getService().signUp(data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) { //상태코드 200~300일 경우 (요청 성공 시)
                    StatusResponse result = response.body();
                    Toast.makeText(SignUp2Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    //로그인 페이지로 이동
                    Intent intent = new Intent(SignUp2Activity.this, SignInActivity.class);
                    //회원가입 엑티비티 스택에서 제거하고 로그인만 남김
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Toast.makeText(SignUp2Activity.this, "회원가입 에러", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //Country 다이얼로그 설정
    void setCountryAlertDialog() {
        //String-Array -> ArrayList로 변환
        ArrayList<String> arrayListCountry =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country)));
        //Country 다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.country, (dialog, which) ->
                binding.buttonCountry.setText(arrayListCountry.get(which)));
        //Country 버튼 이벤트
        final AlertDialog alertDialog = builder.create();
        binding.buttonCountry.setOnClickListener(v -> alertDialog.show());
    }

    //Ok 버튼 클릭 이벤트 : 로그인 페이지로 이동
    public void okButtonClick(View view){
        //Success
        if(binding.editTextName.getText().length() > 0 && binding.buttonCountry.getText().length() > 0){
            String email, pwd, name, country;

            //회원가입1 페이지 데이터 받기
            Intent intent = getIntent();
            email = intent.getStringExtra("email");
            pwd = intent.getStringExtra("pwd");

            //비밀번호 암호화
            String encryptPwd = EncryptSHA512.encryptSHA512(pwd);

            //이름, 국가
            name = binding.editTextName.getText().toString();
            country = binding.buttonCountry.getText().toString();

            //회원가입 요청 메소드 호출
            reqeustSignUp(new SignUpData(email, encryptPwd, name, country));

        } else { //Fail
            Toast.makeText(getApplicationContext(), "Please complete the entry!", Toast.LENGTH_SHORT).show();
        }
    }

    //SignIn 버튼 클릭 이벤트 : 로그인 페이지로 이동
    public void signInButtonClick(View view){
        Intent intent = new Intent(this, SignInActivity.class);
        //스택 비우고 로그인만 남김
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
