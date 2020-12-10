package com.example.travellet.network;

import com.example.travellet.data.responseBody.ProfileResponse;
import com.example.travellet.data.requestBody.SignInData;
import com.example.travellet.data.responseBody.SignInResponse;
import com.example.travellet.data.requestBody.SignUpData;
import com.example.travellet.data.StatusResponse;

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

    @POST("/users/signin") //로그인
    Call<SignInResponse> signIn(@Body SignInData data);
    @POST("/users/signup") //회원가입
    Call<StatusResponse> signUp(@Body SignUpData data);
    @GET("/users") //프로필 조회
    Call<ProfileResponse> readProfile();
    @DELETE("/users") //회원탈퇴
    Call<StatusResponse> deleteProfile();
}
