package com.example.travellet.feature.util;

import android.app.Application;

//manifests 등록 후 사용 가능
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    //인스턴스 반환 메소드
    public static MyApplication getInstance(){
        return instance;
    }
}

