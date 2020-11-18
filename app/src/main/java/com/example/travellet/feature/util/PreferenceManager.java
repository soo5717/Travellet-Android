package com.example.travellet.feature.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 수연 on 2020-11-14.
 * Class: PreferenceManager
 * Description: SharedPreference을 활용하여 token을 관리함
 */
public class PreferenceManager {
        private static final String PREFERENCES_NAME = "rebuild_preference";
        private static final String DEFAULT_VALUE_STRING = "";

        private static SharedPreferences getPreferences(){
            Application application = MyApplication.getInstance();
            return application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }

        //String 값 저장
        public static void setString(String key, String value){
            SharedPreferences sharedPreferences = getPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply(); //비동기 처리 -> 사용자 측면에서는 비동기 처리가 좋음
    }

    //String 값 로드
    public static String getString(String key){
        SharedPreferences sharedPreferences = getPreferences();
        return sharedPreferences.getString(key, DEFAULT_VALUE_STRING);
    }

    //Key 값 삭제
    public static void removeKey(String key){
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply(); //비동기 처리 -> 사용자 측면에서는 비동기 처리가 좋음
    }
}
