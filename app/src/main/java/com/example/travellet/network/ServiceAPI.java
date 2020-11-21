package com.example.travellet.network;

import com.example.travellet.data.setting.SettingResponse;
import com.example.travellet.data.sign.SignInData;
import com.example.travellet.data.sign.SignInResponse;
import com.example.travellet.data.sign.SignUpData;
import com.example.travellet.data.utill.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 수연 on 2020-11-18.
 * Class: ServiceAPI
 * Description: 서버와 통신을 위한 API 인터페이스
 */
public interface ServiceAPI {

    //로그인 요청
    @POST("/sign-in")
    Call<SignInResponse> userSignIn(@Body SignInData data);
    //회원가입 요청 - UPDATE
    @POST("/sign-up")
    Call<StatusResponse> userSignUp(@Body SignUpData data);

    //회원탈퇴 요청 - DELETE
    @DELETE("/users")
    Call<StatusResponse> userDeleteAccount();
    //회원정보 요청 - GET
    @GET("/users")
    Call<SettingResponse> userSetting();
}
