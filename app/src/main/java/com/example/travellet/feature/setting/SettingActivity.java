package com.example.travellet.feature.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travellet.R;
import com.example.travellet.data.SettingResponse;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.databinding.ActivitySettingBinding;
import com.example.travellet.feature.sign.SignInActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.PreferenceManager;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-18.
 * Class: SettingActivity (네트워킹 + 기능 미완료)
 * Description: 마이페이지 클래스
 * 사용자 정보에 대한 READ, UPDATE, DELETE 가능
 * => 이름 다이얼로그 디자인 수정 필요!!
 * => 회원정보 수정 요청 메소드 추가 필요!!
 */
public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding; //바인딩 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 구현
        setNameAlertDialog();
//        setCountryAlertDialog();

        //회원정보 요청 메소드 호출
//        requestSetting();
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //회원정보 요청 - GET : Retroifit2
    private void requestSetting() {
        RetrofitClient.getService().userSetting().enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(@NotNull Call<SettingResponse> call, @NotNull Response<SettingResponse> response) {

            }

            @Override
            public void onFailure(@NotNull Call<SettingResponse> call, @NotNull Throwable t) {
                Log.e("Setting Read Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //회원탈퇴 요청 - DELETE : Retrofit2
    private void requestDelteAccount() {
        RetrofitClient.getService().userDeleteAccount().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {

            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Toast.makeText(SettingActivity.this, "Delete Account Error", Toast.LENGTH_SHORT).show();
                Log.e("Delete Account Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /** 디자인 + 에러 수정 필요함! */
    //NameEdit 다이얼로그 설정
    void setNameAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EdiiText 설정
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(editText);
        //다이얼로그 OK 버튼 클릭 이벤트
        builder.setPositiveButton("OK", (dialog, which) -> {
            binding.textViewName.setText(editText.getText().toString());
            //회원정보 수정 요청 메소드 호출

        });
        //다이얼로그 Cancel 버튼 클릭 이벤트
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        //NameEdit 버튼 클릭 이벤트
        binding.buttonNameEdit.setOnClickListener(v -> {
            builder.show();
        });
    }

    //CountryEdit 다이얼로그 설정
    void setCountryAlertDialog() {
        //String-Array -> ArrayList로 변환
        ArrayList<String> arrayListCountry =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country)));
        //CountryEdit 다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.country, (dialog, which) -> {
            binding.textViewCountry.setText(arrayListCountry.get(which));
            //회원정보 수정 요청 메소드 호출

        });
        //CountryEdit 버튼 이벤트
        binding.buttonCountryEdit.setOnClickListener(v -> {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    //SignOut 버튼 클릭 이벤트 : 로그아웃
    public void signOutButtonClick(View view) {
        PreferenceManager.removeKey("user_token"); //Token 삭제
        //로그인 페이지로 이동 => 스택 비우는 코드 추가 필요!!!
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    //DeleteAccount 버튼 클릭 이벤트 : 회원탈퇴
    public void deleteAccountButtonClick(View view) {
        //회원탈퇴 요청 메소드 호출
//        requestDelteAccount();
    }
}