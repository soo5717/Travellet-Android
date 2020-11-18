package com.example.travellet.network;

import com.example.travellet.feature.util.PreferenceManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 수연 on 2020-11-14.
 * Class: RetrofitClient
 * Description: Retrofit2를 사용한 통신을 위한 클래스
 * => static으로 선언하는 방식이 맞는지 잘 모르겠음!
 * => token에 null이 들어간 경우 테스트 필요함!
 */
public class RetrofitClient {
    private static final String BASE_URL = "http://ec2-3-92-38-139.compute-1.amazonaws.com:3000";
    private static String sAuthToken = null;

    private RetrofitClient() { }

    /**
     * 모든 API Header에 token을 추가해서 보냄
     * Token은 SharedPreference에 저장 된 값을 불러옴
     * Header에 추가하는 것은 OkHttp3을 사용함
     */
    private static Retrofit getClient() {
        sAuthToken = PreferenceManager.getString("user_token");
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("authorization", sAuthToken)
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceAPI getService() {
        return RetrofitClient.getClient().create(ServiceAPI.class);
    }
}
