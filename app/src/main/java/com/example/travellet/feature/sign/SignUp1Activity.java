package com.example.travellet.feature.sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.travellet.databinding.ActivitySignUp1Binding;
import com.example.travellet.feature.util.BaseActivity;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignUp1Activity (완료)
 * Description: 회원가입1 클래스
 * 이메일과 패스워드 입력 받아서 회원가입2로 전달
 */
public class SignUp1Activity extends BaseActivity {
    private ActivitySignUp1Binding binding; //바인딩 선언
    private SignValidation mSignValidation; //클래스 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EditText 포커스
        binding.editTextEmail.requestFocus();

        //EditText 입력 이벤트 (유효성 검증)
        mSignValidation = new SignValidation();
        mSignValidation.emailTextChanged(binding.editTextEmail);
        mSignValidation.pwdTextChanged(binding.editTextPwd);
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //Next 버튼 클릭 이벤트
    public void nextButtonClick(View view){
        String email, pwd;
        email = binding.editTextEmail.getText().toString();
        pwd = binding.editTextPwd.getText().toString();

        //이메일, 비밀번호 유효성 검증
        boolean isValidEmail, isValidPwd;
        isValidEmail = mSignValidation.isValidEmail(email);
        isValidPwd = mSignValidation.isVaildPwd(pwd);

        if (isValidEmail && isValidPwd) { //Success
            //회원가입2 페이지로 이메일, 비밀번호 전달
            Intent intent = new Intent(this, SignUp2Activity.class);
            intent.putExtra("email", email);
            intent.putExtra("pwd", pwd);
            startActivity(intent);

            //전환 애니메이션 없애기
            overridePendingTransition(0, 0);
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

    //SignIn 버튼 클릭 이벤트 : 로그인 페이지로 이동
    public void signInButtonClick(View view){
        finish();
    }
}
