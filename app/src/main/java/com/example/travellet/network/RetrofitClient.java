package com.example.travellet.network;


import com.example.travellet.feature.util.PreferenceManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://ec2-3-226-51-250.compute-1.amazonaws.com:3000";
    private static String authToken = null;

    private RetrofitClient() { }

    private static Retrofit getClient() {
        //모든 API Header -> 토큰 추가
        authToken = PreferenceManager.getString("user_token");
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("authorization", authToken)
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
