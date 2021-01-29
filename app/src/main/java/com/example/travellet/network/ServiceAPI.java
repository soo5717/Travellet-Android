package com.example.travellet.network;

import com.example.travellet.data.requestBody.BudgetData;
import com.example.travellet.data.requestBody.DistributeBudgetData;
import com.example.travellet.data.requestBody.ExpenseData;
import com.example.travellet.data.requestBody.PlaceLikeData;
import com.example.travellet.data.requestBody.PlanData;
import com.example.travellet.data.requestBody.ProfileData;
import com.example.travellet.data.requestBody.TravelData;
import com.example.travellet.data.responseBody.BudgetDetailResponse;
import com.example.travellet.data.responseBody.DistributeBudgetResponse;
import com.example.travellet.data.responseBody.ExchangeRateResponse;
import com.example.travellet.data.responseBody.ExpenseDetailResponse;
import com.example.travellet.data.responseBody.PlaceLikeResponse;
import com.example.travellet.data.responseBody.PlanCreateResponse;
import com.example.travellet.data.responseBody.PlanDetailResponse;
import com.example.travellet.data.responseBody.PlanResponse;
import com.example.travellet.data.responseBody.ProfileResponse;
import com.example.travellet.data.requestBody.SignInData;
import com.example.travellet.data.responseBody.SignInResponse;
import com.example.travellet.data.requestBody.SignUpData;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.responseBody.TravelCreateResponse;
import com.example.travellet.data.responseBody.TravelResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("/users") //회원정보 조회
    Call<ProfileResponse> readProfile();
    @DELETE("/users") //회원탈퇴
    Call<StatusResponse> deleteProfile();
    @GET("/users/exchange-rate") //환율 조회
    Call<ExchangeRateResponse> readExchangeRate(@Query("base") String base);

    @POST("/likes") // 장소 좋아요 추가
    Call<StatusResponse> createPlaceLike(@Body PlaceLikeData data);
    @HTTP(method = "DELETE", path = ("/likes"), hasBody = true)//장소 좋아요 취소
    Call<StatusResponse> cancelPlaceLike(@Body PlaceLikeData data);
    @GET("/likes") //장소 목록 조회
    Call<PlaceLikeResponse> readPlaceLike();

    @POST("/travels") //여행 생성
    Call<TravelCreateResponse> createTravel(@Body TravelData data);
    @GET("/travels") //여행 목록 조회 요청
    Call<TravelResponse> readTravel(@Query("date") String date);
    @DELETE("/travels/{id}") //여행 삭제
    Call<StatusResponse> deleteTravel(@Path("id") int id);

    @POST("/plans") //일정 추가
    Call<PlanCreateResponse> createPlan(@Query("travelid") int travelId,  @Body PlanData data);
    @GET("/plans/{id}") //일정 조회
    Call<PlanDetailResponse> readPlanDetail(@Path("id") int id);
    @GET("/plans") //일정 목록 조회
    Call<PlanResponse> readPlan(@Query("travelid") int travelId);
    @PUT("/plans/{id}") // 일정 수정
    Call<StatusResponse> updatePlan(@Path("id") int planId, @Query("travelid") int travelId, @Body PlanData data);
    @DELETE("/plans/{id}") // 일정 삭제
    Call<StatusResponse> deletePlan(@Path("id") int planId, @Query("travelid") int travelId);

    @POST("/budgets") //예산 생성
    Call<StatusResponse> createBudget(@Body BudgetData data);
    @GET("/budgets/{id}") //예산 내용 조회
    Call<BudgetDetailResponse> readBudget(@Path("id") int id);
    @PATCH("/budgets/{id}") //예산 수정
    Call<StatusResponse> updateBudget(@Path("id") int id, @Body BudgetData data);
    @DELETE("/budgets/{id}") //예산 삭제
    Call<StatusResponse> deleteBudget(@Path("id") int id);
    @GET("/budgets/distribution") //예산 분배 조회
    Call<DistributeBudgetResponse> readDistributeBudget(@Query("travelid") int travelId);
    @PATCH("/budgets/distribution") //예산 분배
    Call<StatusResponse> updateDistributeBudget(@Body DistributeBudgetData data);

    @POST("/expenses") //지출 생성
    Call<StatusResponse> createExpense(@Body ExpenseData data);
    @GET("/expenses/{id}") //지출 내용 조회
    Call<ExpenseDetailResponse> readExpense(@Path("id") int id);
    @PATCH("/expenses/{id}") //지출 수정
    Call<StatusResponse> updateExpense(@Path("id") int id, @Body ExpenseData data);
    @DELETE("/expenses/{id}") //지출 삭제
    Call<StatusResponse> deleteExpense(@Path("id") int id);


}
