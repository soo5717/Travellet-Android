package com.example.travellet.feature.plan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.requestBody.PlanCreateData;
import com.example.travellet.data.responseBody.PlanCreateResponse;
import com.example.travellet.data.responseBody.PlanResponse;
import com.example.travellet.databinding.ActivityPlanBinding;
import com.example.travellet.feature.util.BaseActivity;
import com.example.travellet.feature.util.ProgressBarManager;
import com.example.travellet.feature.util.ResultCode;
import com.example.travellet.feature.util.TravelUtil;
import com.example.travellet.network.RetrofitClient;
import com.example.travellet.network.ServiceAPI;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class PlanActivity extends BaseActivity implements ResultCode {
    private ActivityPlanBinding binding;

    ArrayList<String> tabList = new ArrayList<>();
    //요일 구하고 저장하는 arrayList
    ArrayList<String> dateList = new ArrayList<>();
    int pagePosition = 0;

    //리사이클러뷰에 필요한 변수 선언
    RecyclerView planRecyclerView = null ;
    PlanAdapter planAdapter = null ;
    ArrayList<PlanItem> planItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    //plan id 저장
    ArrayList<Integer> planIds = new ArrayList<>();
    //travel id 저장
    int travelId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //탭바 초기화
        initTab();

        planAdapter = new PlanAdapter(planItems);
        binding.recyclerViewPlan.setAdapter(planAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewPlan.setLayoutManager(layoutManager);

        //처음 들어왔을 때 여행 목록 조회하기
        requestReadPlan(0);

        //여행 추가 버튼 클릭 이벤트
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlanActivity.class);
                //travel date 넘기기
                String[] dateArr = dateList.get(pagePosition).split(" |/");
                String date = dateArr[0]+"-"+dateArr[1]+"-"+dateArr[2];
                intent.putExtra("date", date);
                intent.putExtra("travelId", travelId);
                startActivityForResult(intent, ADD_EDIT_PLAN_RESULT);
            }
        });

        planAdapter.setOnItemLongClickListener(
                (v, position) -> {
                    selectEditOfDelete(position);
                }
        );

        //교통 수단 변경
        planAdapter.setOnTransportClickListener(
                (v, position) ->{
                    setTransportDialog(position);
                }
        );
    }

    @Override //Activity 뷰 바인딩
    protected View getLayoutResource() {
        binding = ActivityPlanBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    //메뉴 이용해서 툴바 초기화
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_plan, menu);

        return true;
    }

    //툴바 버튼 눌렀을 때
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼 눌렀을 때
                finish();
                return true;
            }
            case R.id.menu_calculation: { // 오른쪽 상단 버튼 눌렀을 때
                //Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //탭 초기화 메소드
    private void initTab() {
        tabList.add("ALL");
        Intent intent = getIntent();

        //travelActivity에서 받아온 데이터 저장
        travelId = intent.getIntExtra("travel_id", 0);
        Log.d("travel adapter id: ", String.valueOf(travelId));
        //날짜 테스트 데이터
        String[] startDate = intent.getStringExtra("startDate").split("-");
        String[] endDate = intent.getStringExtra("endDate").split("-");

        addDay(Integer.valueOf(startDate[0]), Integer.valueOf(startDate[1]), Integer.valueOf(startDate[2]), Integer.valueOf(endDate[0]), Integer.valueOf(endDate[1]), Integer.valueOf(endDate[2]));

        //탭 레이아웃에 탭 추가
        for(int i=0; i<tabList.size(); i++)
            binding.tabs.addTab(binding.tabs.newTab().setText(tabList.get(i)));

        //탭 선택 이벤트 처리리
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                if(pos == 0){
                    binding.textViewDatetime.setText(dateList.get(0));
                    pagePosition = 0;
                    requestReadPlan(0);
                }
                else {
                    binding.textViewDatetime.setText(dateList.get(pos));
                    pagePosition = pos;
                    requestReadPlan(pagePosition);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //날짜 계산 메소드
    private void addDay(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); //연원일 표시

        startMonth -= 1;
        endMonth -= 1; //월에서 숫자 하나씩 빼줘야 함.(이유가 뭐였지 ㅇㅅㅇ 계산때문인건 기억남 쨌든)

        //시작 날짜, 끝 날짜 하나로 합쳐줌(이 과정이 필요하지 않을 수 있음)
        startCal.set(startYear, startMonth, startDay);
        endCal.set(endYear, endMonth, endDay);
        String startDate = dateFormat.format(startCal.getTime());
        String endDate = dateFormat.format(endCal.getTime());
        //All에서 표시해줄 날짜 형식 먼저 저장
        dateList.add(startDate + " - " + endDate);

        endDay += 1; //endDay 하나 더한 건 날짜 계산 똑바로 하기 위해서
        //수정된 endDay로 endDate 다시 세팅
        endCal.set(endYear, endMonth, endDay);
        endDate = dateFormat.format(endCal.getTime());

        int i = 0;
        do { //startDate가 endDate와 같아질 때까지 반복
            int day = startCal.get(Calendar.DAY_OF_MONTH); //해당 일(day) 구하기
            int dow = startCal.get(Calendar.DAY_OF_WEEK); //해당 요일 구하기
            tabList.add(String.valueOf(day)); //탭 리스트에 일 추가
            //요일 리스트에 요일 추가
            switch (dow) {
                case 1:
                    dateList.add(startDate+" Sunday");
                    break;
                case 2:
                    dateList.add(startDate+" Monday");
                    break;
                case 3:
                    dateList.add(startDate+" Tuesday");
                    break;
                case 4:
                    dateList.add(startDate+" Wednesday");
                    break;
                case 5:
                    dateList.add(startDate+" Thursday");
                    break;
                case 6:
                    dateList.add(startDate+" Friday");
                    break;
                case 7:
                    dateList.add(startDate+" Saturday");
                    break;
            }
            startCal.add(Calendar.DATE, 1); //1일 더해줌
            startDate = dateFormat.format(startCal.getTime()); //비교를 위한 값 셋팅
            i++;

        } while (!startDate.equals(endDate));
        binding.textViewDatetime.setText(dateList.get(0));
        pagePosition = 0;
    }

    //plan 어댑터 아이템 추가
    public void addItem(String date, String time, String place, String memo, int type, int transport, double budget, double expense, double x, double y) {
        PlanItem item = new PlanItem(date, time, place, memo, type, transport, budget, expense, x, y);
        item.setDate(date);
        item.setTime(time);
        item.setPlace(place);
        item.setMemo(memo);
        item.setTransport(transport);
        item.setType(type);
        item.setBudget(budget);
        item.setExpense(expense);
        item.setX(x);
        item.setY(y);
        planItems.add(item);
    }

    //교통 선택 다이얼로그 구현
    public void setTransportDialog(int pos) {
        String date = planItems.get(pos).getDate();
        String time = planItems.get(pos).getTime();
        String place = planItems.get(pos).getPlace();
        String memo = planItems.get(pos).getMemo();
        int category = planItems.get(pos).getType();
        double x = planItems.get(pos).getX();
        double y = planItems.get(pos).getY();
        //교통 배열 리스트 갖고 오기
        ArrayList<String> transportArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.transport)));
        //다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.transport, (dialog, which) -> {
            //다이얼로그에서 선택한 값에 따라 transport update함.
            switch (transportArray.get(which)){
                case "Walk":
                    planItems.get(pos).setTransport(1);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 1, x, y, travelId), pos);
                    //requestReadPlan();
                    break;
                case "Bus":
                    planItems.get(pos).setTransport(2);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 2, x, y, travelId), pos);
                    //requestReadPlan();
                    break;
                case "Subway":
                    planItems.get(pos).setTransport(3);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 3, x, y, travelId), pos);
                    //requestReadPlan();
                    break;
                case "Taxi":
                    planItems.get(pos).setTransport(4);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 4, x, y, travelId), pos);
                    //requestReadPlan();
                    break;
                case "Car":
                    planItems.get(pos).setTransport(5);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 5, x, y, travelId), pos);
                    //requestReadPlan();
                    break;
                default:
                    break;
            }
            requestReadPlan(pos);
        });
        //plan 목록 조회해서 바뀐 결과 보여줌.
        //다이얼로그 창 뜨게 하는 거
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 일정 수정 or 삭제 다이얼로그 구현
    public void selectEditOfDelete(int pos){
        //다이얼로그 리스트
        ArrayList<String> selectArr = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.edit_delete)));
        //다이얼로그 구현
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setItems(R.array.edit_delete, (dialog, which) -> {
            //edit 클릭 시
            if(selectArr.get(which).equals("Edit")){
                editPlan(pos);
            }
            //delete 클릭 시
            else
                deletePlan(pos);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //일정 수정할 때 필요한 데이터 넘기고 addPlan 화면 띄우는 메소드
    public void editPlan(int pos){
        Intent intent = new Intent(getApplicationContext(), AddPlanActivity.class);
        //일정 수정 시 보여줘야 할 데이터 넘기기
        //TODO: TRAVEL 완성되면 TRAVEL ID 직접 받아오도록 해야 함.
        intent.putExtra("travelId", travelId);
        intent.putExtra("planId", planIds.get(pos));
        intent.putExtra("date", planItems.get(pos).getDate());
        intent.putExtra("time", planItems.get(pos).getTime());
        intent.putExtra("place", planItems.get(pos).getPlace());
        intent.putExtra("memo", planItems.get(pos).getMemo());
        intent.putExtra("type", planItems.get(pos).getType());
        intent.putExtra("transport", planItems.get(pos).getTransport());
        startActivityForResult(intent, ADD_EDIT_PLAN_RESULT);
    }

    //일정 삭제하기(경고메세지 띄우기)
    public void deletePlan(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete it?"); //삭제 확인 메세지
        //OK 버튼 누르면 삭제하도록
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int planId = planIds.get(pos);
                requestDeletePlan(planId);
                planItems.remove(pos);
                planIds.remove(pos);
                binding.recyclerViewPlan.setAdapter(planAdapter);
            }
        });
        //Cancel 버튼 누르면 아예 취소
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == ADD_EDIT_PLAN_RESULT){
            if(intent != null){
                requestReadPlan(pagePosition);
            }
        }
    }

    //일정 목록 조회 - GET : Retrofit2
    private void requestReadPlan(int day){
        RetrofitClient.getService().readPlan(travelId).enqueue(new Callback<PlanResponse>( ){
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {
                if(response.isSuccessful()){
                    PlanResponse result = response.body();
                    planItems.clear();
                    planIds.clear();
                    for(int i=0; i<result.getData().size(); i++){
                        if(day == 0){
                            planIds.add(result.getData().get(i).getId());
                            String date = result.getData().get(i).getDate();
                            String time = result.getData().get(i).getTime();
                            String place = result.getData().get(i).getPlace();
                            String memo = result.getData().get(i).getMemo();
                            int category = result.getData().get(i).getCategory();
                            int transport = result.getData().get(i).getTransport();
                            double x = result.getData().get(i).getX();
                            double y = result.getData().get(i).getY();
                            double budget = result.getData().get(i).getSumBudget();
                            double expense = result.getData().get(i).getSumExpense();
                            addItem(date, time, place, memo, category, transport, budget, expense, x, y);
                        } else{
                            String[] planDate = result.getData().get(i).getDate().split("-");
                            String[] pageDate = dateList.get(day).split(" |/");
                            String planYear = planDate[0];
                            String planMonth = planDate[1];
                            String planDay = planDate[2];
                            String pageYear = pageDate[0];
                            String pageMonth = pageDate[1];
                            String pageDay = pageDate[2];
                            Log.d("date", planYear + " " + planMonth + " " + planDay + "-" + pageYear + " " + pageMonth + " " + pageDay);
                            if(planYear.equals(pageYear) && planMonth.equals(pageMonth) && planDay.equals(pageDay)){
                                planIds.add(result.getData().get(i).getId());
                                String date = result.getData().get(i).getDate();
                                String time = result.getData().get(i).getTime();
                                String place = result.getData().get(i).getPlace();
                                String memo = result.getData().get(i).getMemo();
                                int category = result.getData().get(i).getCategory();
                                int transport = result.getData().get(i).getTransport();
                                double x = result.getData().get(i).getX();
                                double y = result.getData().get(i).getY();
                                double budget = result.getData().get(i).getSumBudget();
                                double expense = result.getData().get(i).getSumExpense();
                                addItem(date, time, place, memo, category, transport, budget, expense, x, y);
                            }
                        }
                    }
                    binding.recyclerViewPlan.setAdapter(planAdapter);
                }
            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                Toast.makeText(PlanActivity.this, "Read Error", Toast.LENGTH_SHORT).show();
                Log.e("일정 조회 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
     //일정 수정(여기서는 transport 수정) - PUT : Retrofit2
    private void requestUpdatePlan(PlanCreateData data, int pos){
        int planId = planIds.get(pos);
        RetrofitClient.getService().updatePlan(planId, travelId, data).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse result = response.body();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(PlanActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                Log.e("일정 수정 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    //일정 삭제 - DELETE : Retrofit2
    private void requestDeletePlan(int planId) {
        RetrofitClient.getService().deletePlan(planId, travelId).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                Toast.makeText(PlanActivity.this, "Successfully deleted.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(PlanActivity.this, "Delete Error", Toast.LENGTH_SHORT).show();
                Log.e("일정 삭제 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}