package com.example.travellet.feature.sign;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.travellet.R;
import com.example.travellet.data.SignUpData;
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
 * Class: SignUp2Activity (네트워킹 미완료)
 * Description: 회원가입2 클래스
 */
public class SignUp2Activity extends BaseActivity {
    private ActivitySignUp2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        RetrofitClient.getService().userSignUp(data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {

            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Toast.makeText(SignUp2Activity.this, "SignUp Error", Toast.LENGTH_SHORT).show();
                Log.e("SignUp Error", Objects.requireNonNull(t.getMessage()));
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
        binding.buttonCountry.setOnClickListener(v -> {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
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

            //테스트 코드 => 추후 삭제
//            Log.d("이메일", email);
//            Log.d("비밀번호", encryptPwd);
//            Log.d("이름", name);
//            Log.d("국가", country);

            //회원가입 요청 메소드 호출
//            reqeustSignUp(new SignUpData(email, encryptPwd, name, country));

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
