package com.example.travellet.data.sign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SignUpData
 * Description: 회원가입 요청 데이터
 */
public class SignUpData {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("name")
    public String name;
    @SerializedName("country")
    public String country;

    public SignUpData(String email, String password, String name, String country) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.country = country;
    }
}
