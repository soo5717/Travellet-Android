package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignInData
 * Description: 로그인 요청 데이터
 */
public class SignInData {
    @SerializedName("email")
    public String email;
    @SerializedName("pwd")
    public String pwd;

    public SignInData(String email, String pwd) {
        super();
        this.email = email;
        this.pwd = pwd;
    }
}
