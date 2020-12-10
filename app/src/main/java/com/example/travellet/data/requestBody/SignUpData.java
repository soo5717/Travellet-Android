package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignUpData
 * Description: 회원가입 요청 데이터
 */
public class SignUpData {
    @SerializedName("email")
    public String email;
    @SerializedName("pwd")
    public String pwd;
    @SerializedName("name")
    public String name;
    @SerializedName("country")
    public String country;

    public SignUpData(String email, String pwd, String name, String country) {
        super();
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.country = country;
    }
}
