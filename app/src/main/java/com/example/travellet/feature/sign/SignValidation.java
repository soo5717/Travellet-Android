package com.example.travellet.feature.sign;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;

import com.example.travellet.R;

/**
 * Created by 수연 on 2020-11-18.
 * Class: Validation (완료)
 * Description: 유효성 검사 클래스 (이메일, 패스워드 검증)
 */
class SignValidation {

    //이메일 유효성 검사
    boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //패스워드 유효성 검사
    boolean isVaildPwd(String pwd) {
        return pwd.length() >= 6;
    }

    //이메일 입력 변화 이벤트
    void emailTextChanged(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override //입력 전
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override //입력 중
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isValidEmail(editText.getText().toString())){ //유효성 검사 success
                    editText.setBackgroundResource(R.drawable.bg_underline_vaild_success);
                } else { //유효성 검사 fail
                    editText.setBackgroundResource(R.drawable.bg_underline_vaild_fail);
                }
            }

            @Override //입력 후
            public void afterTextChanged(Editable s) { }
        });
    }

    //비밀번호 입력 변화 이벤트
    void pwdTextChanged(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override //입력 전
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override //입력 중
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isVaildPwd(editText.getText().toString())){ //유효성 검사 success
                    editText.setBackgroundResource(R.drawable.bg_underline_vaild_success);
                } else { //유효성 검사 fail
                    editText.setBackgroundResource(R.drawable.bg_underline_vaild_fail);
                }
            }

            @Override //입력 후
            public void afterTextChanged(Editable s) { }
        });
    }
}
