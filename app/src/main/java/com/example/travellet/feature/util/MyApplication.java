package com.example.travellet.feature.util;

import android.app.Application;

/**
 * Created by 수연 on 2020-11-14.
 * Class: MyApplication
 * Description: Context를 가져오려고 만든 클래스
 * Manifest에 등록 후 사용 가능 (android:name)
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    //인스턴스 반환
    public static MyApplication getInstance(){
        return sInstance;
    }
}

