package com.example.travellet.feature.detail.budget;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.BudgetData;
import com.example.travellet.data.responseBody.BudgetDetailResponse;
import com.example.travellet.data.responseBody.ExchangeRateResponse;
import com.example.travellet.databinding.ActivityAddBudgetBinding;
import com.example.travellet.databinding.IncludeNumberBinding;
import com.example.travellet.feature.detail.PlanDetailActivity;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2021-01-29.
 * Class: AddBudgetActivity
 * Description: 예산 생성, 수정 페이지
 */
public class AddBudgetActivity extends BaseActivity {
    private ActivityAddBudgetBinding binding; //바인딩 선언
    private IncludeNumberBinding numberBinding; //Include 바인딩 선언

    private int mPlanId = 0; //일정 아이디
    private int mBudgetId = 0; //예산 아이디
    private boolean mResultCode = true; //생성, 수정 구분

    private double mRateTo = 0, mRateKrw = 0; //환율 (자국, 한국)
    private int mCategory = 0; //카테고리 (1~6까지)

    private AlertDialog mAlertDialog  = null; //다이얼로그 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent로 생성/수정 구분하기 (true 일 경우 생성, false 일 경우 수정)
        Intent intent = getIntent();
        mPlanId= intent.getIntExtra("plan_id", 0);
        mBudgetId = intent.getIntExtra("budget_id", 0);
        mResultCode = intent.getBooleanExtra("budget", true);

        initButton(); //버튼 클릭 이벤트 설정
        mAlertDialog = setCountryAlertDialog(); //다이얼로그 설정

        if(mResultCode) //생성일 경우 true
            requestReadExchangeRate("KRW"); //환율 조회 요청 (비동기)
        else if(!mResultCode && mBudgetId != 0) //수정일 경우 false이고 budgetId 있어야 함.
            requestReadBudget(mBudgetId); //예산 내용 조회 요청 (비동기)
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityAddBudgetBinding.inflate(getLayoutInflater());
        numberBinding = binding.number; //Include 바인딩
        return binding.getRoot();
    }

    //환율 조회 요청 - GET : Retrofit2
    void requestReadExchangeRate(String base) {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readExchangeRate(base).enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(@NotNull Call<ExchangeRateResponse> call, @NotNull Response<ExchangeRateResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ExchangeRateResponse.Data result = response.body().getData();

                    //전역 변수 값 변경
                    mRateTo = result.getRateTo();
                    mRateKrw = result.getRateKrw();

                    //텍스트 뷰 초기화
                    binding.textViewCurrency1.setText(base);
                    binding.textViewCurrency2.setText(result.getCurrency());

                    //환율 변경 시 입력 값 변경
                    if(binding.textViewPrice1.getText().length() > 0) {
                        double price = Double.parseDouble(binding.textViewPrice1.getText().toString());
                        price = Math.round(price * mRateTo * 100)/100.0; //소수점 2자리 반올림
                        binding.textViewPrice2.setText(String.valueOf(price));
                    }
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<ExchangeRateResponse> call, @NotNull Throwable t) {
                Log.e("환율 조회 에러", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    //예산 생성 요청 - POST : Retrofit2
    void requestCreateBudget(BudgetData data) {
        RetrofitClient.getService().createBudget(data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //일정 세부 페이지로 이동
                    Intent intent = new Intent(AddBudgetActivity.this, PlanDetailActivity.class);
                    intent.putExtra("plan_id", mPlanId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Log.e("예산 생성 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //예산 내용 조회 요청 - GET : Retrofit2
    void requestReadBudget(int id) {
        ProgressBarManager.showProgress(binding.progressBar, true);
        RetrofitClient.getService().readBudget(id).enqueue(new Callback<BudgetDetailResponse>() {
            @Override
            public void onResponse(@NotNull Call<BudgetDetailResponse> call, @NotNull Response<BudgetDetailResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    BudgetDetailResponse.Data result = response.body().getData();

                    //전역 변수 값 변경
                    mRateTo = result.getExchangeRate().getRateTo();
                    mRateKrw = result.getExchangeRate().getRateKrw();

                    //데이터 setText
                    binding.textViewCurrency1.setText(result.getCurrency());
                    binding.textViewCurrency2.setText(result.getExchangeRate().getCurrency());
                    binding.textViewPrice1.setText(String.valueOf(result.getPrice()));
                    binding.textViewPrice2.setText(String.valueOf(result.getPriceTo()));
                    binding.editTextMemo.setText(result.getMemo());
                    //카테고리 지정
                    Button[] buttonCategories = {
                            binding.buttonLodging, binding.buttonFood, binding.buttonShopping,
                            binding.buttonTourism, binding.buttonTransport, binding.buttonEtc
                    };
                    buttonCategories[result.getCategory() - 1].performClick();
                }
                ProgressBarManager.showProgress(binding.progressBar, false);
            }

            @Override
            public void onFailure(@NotNull Call<BudgetDetailResponse> call, @NotNull Throwable t) {
                Log.e("예산 내용 조회 에러", Objects.requireNonNull(t.getMessage()));
                ProgressBarManager.showProgress(binding.progressBar, false);
            }
        });
    }

    //예산 수정 요청 - PATCH : Retrofit2
    void requestUpdateBudget(int id, BudgetData data) {
        RetrofitClient.getService().updateBudget(id, data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //일정 세부 페이지로 이동
                    Intent intent = new Intent(AddBudgetActivity.this, PlanDetailActivity.class);
                    intent.putExtra("plan_id", mPlanId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Log.e("예산 수정 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override //오른쪽 툴바 메뉴 생성
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override //오른쪽 툴바 메뉴 아이템 클릭 이벤트
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //TODO (suyeon) : 폰트 변경
        if (item.getItemId() == R.id.currency) {
            mAlertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Country 다이얼로그 설정
    AlertDialog setCountryAlertDialog() {
        //String-Array -> ArrayList로 변환
        ArrayList<String> arrayListCountry =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country)));
        //Country 다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.country, (dialog, which) -> {
            String country = arrayListCountry.get(which);
            requestReadExchangeRate(country.substring(country.length()-3));//환율 조회 요청
        });
        //Country 버튼 이벤트
        return builder.create();
    }

    //환율에 따른 금액 계산
    String calculateExchange(String price1) {
        if(price1.length() > 0 && mRateTo != 0)  {
            double price2 = Math.round(mRateTo * Double.parseDouble(price1) * 100)/100.0; //소수점 2자리 반올림
            return String.valueOf(price2);
        }
        return null;
    }

    //버튼 클릭 이벤트 설정
    private void initButton() {
        //배열 선언 => 반복문 돌리기 위함.
        Button[] buttonCategories = {
                binding.buttonLodging, binding.buttonFood, binding.buttonShopping,
                binding.buttonTourism, binding.buttonTransport, binding.buttonEtc
        };
        Button[] buttonNumbers = {
                numberBinding.button1, numberBinding.button2, numberBinding.button3,
                numberBinding.button4, numberBinding.button5, numberBinding.button6,
                numberBinding.button7, numberBinding.button8, numberBinding.button9,
                numberBinding.buttonDot, numberBinding.button0
        };

        //카테고리 클릭 이벤트: 카테고리 선택
        for (int i = 0; i < buttonCategories.length; i++) {
            int finalI = i;
            buttonCategories[i].setOnClickListener(v -> {
                mCategory = finalI + 1; //카테고리 값 저장 (1~6까지)
                Button button = (Button) v;
                button.setSelected(true); //이미지 색 변경
                button.setTextColor(ContextCompat.getColor(this, R.color.text_blue)); //텍스트 색 변경
                for (Button buttonCategory : buttonCategories) {
                    if (buttonCategory != button) {
                        buttonCategory.setSelected(false); //이미지 색 변경
                        buttonCategory.setTextColor(ContextCompat.getColor(this, R.color.hint_grey)); //텍스트 색 변경
                    }
                }
            });
        }

        //숫자 입력 클릭 이벤트: 숫자 입력 및 지우기
        for (Button value : buttonNumbers) {
            value.setOnClickListener(v -> {
                Button button = (Button) v;
                String originText = binding.textViewPrice1.getText().toString();
                String inputText = button.getText().toString();

                String newText = String.format("%s%s", originText, inputText);
                binding.textViewPrice1.setText(newText);
                binding.textViewPrice2.setText(calculateExchange(newText)); //환율에 따른 금액 계산
            });
        }
        numberBinding.buttonRemove.setOnClickListener(v -> {
            String originText = binding.textViewPrice1.getText().toString();
            if (originText.length() > 0) {
                String newText = originText.substring(0, originText.length() - 1);
                binding.textViewPrice1.setText(newText);
                binding.textViewPrice2.setText(calculateExchange(newText)); //환율에 따른 금액 계산
            }
        });

        //Ok 버튼 클릭 이벤트 : 예산 생성 요청
        binding.buttonOk.setOnClickListener(v -> {
            //입력 값 받아오기
            String currency = binding.textViewCurrency1.getText().toString();
            String price1 = binding.textViewPrice1.getText().toString();
            String price2 = binding.textViewPrice2.getText().toString();
            String memo = binding.editTextMemo.getText().toString();
            int category = mCategory;

            //입력 값 타입 변환 및 계산
            double price = price1.length() > 0 ? Double.parseDouble(price1) : 0;
            double priceTo = price2.length() > 0 ? Double.parseDouble(price2) : 0;
            int priceKrw = price != 0 && mRateKrw != 0 ? (int)Math.round(price*mRateKrw) : 0;

            //입력 값 유효성 검사 => 모두 입력 받았을 경우 성공
            if(mPlanId != 0 && currency.length() > 0 && price != 0 && priceTo != 0 && priceKrw != 0 && memo.length() > 0 && category != 0 ) {
                if(mResultCode) //생성일 경우 true
                    requestCreateBudget(new BudgetData(mPlanId, currency, price, priceTo, priceKrw, memo, category)); //예산 생성 요청
                else if(!mResultCode && mBudgetId != 0) //수정일 경우 false이고 budgetId 있어야 함.
                    requestUpdateBudget(mBudgetId, new BudgetData(null, currency, price, priceTo, priceKrw, memo, category)); //예산 수정 요청
            } else {
                //TODO (suyeon) : 토스트 메시지 안 뜸. 확인 필요!
                Toast.makeText(AddBudgetActivity.this, "Please enter all values.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}