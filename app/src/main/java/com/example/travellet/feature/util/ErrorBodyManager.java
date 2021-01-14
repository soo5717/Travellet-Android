package com.example.travellet.feature.util;

import com.example.travellet.data.StatusResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by 수연 on 2020-12-11.
 * Class: ErrrorManager
 * Description: Retrofit2의 errorBody를 StatusResponse 클래스 형태로 파싱함.
 * Retrofit2의 responseBodyConverter를 활용하는 방법도 있지만,
 * 여기서는 그냥 단순하게 Gson을 활용해서 Json을 파싱함.
 */
public class ErrorBodyManager {
    public static StatusResponse parseError(Response<?> response) {
        StatusResponse statusResponse = null;
        try {
            assert response.errorBody() != null;
            statusResponse = new Gson().fromJson(response.errorBody().string(), StatusResponse.class);
        } catch (IOException e) {
            new StatusResponse();
        }
        return statusResponse;
    }
}
