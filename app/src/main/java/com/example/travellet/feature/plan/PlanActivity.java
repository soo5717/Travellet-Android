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
import com.example.travellet.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlanActivity extends BaseActivity {
    private ActivityPlanBinding binding;

    //TODO: 탭 리스트 테스트용 나중에 지우기
    List<String> tabList = Arrays.asList("ALL", "29", "30", "31", "1", "2", "3", "4", "5", "6", "7");

    //리사이클러뷰에 필요한 변수 선언
    RecyclerView planRecyclerView = null ;
    PlanAdapter planAdapter = null ;
    ArrayList<PlanItem> planItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    //plan id 저장
    ArrayList<Integer> planIds = new ArrayList<>();

    //결과코드
    final static int ADD_EDIT_PLAN_RESULT = 103;

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
        requestReadPlan();

        //여행 추가 버튼 클릭 이벤트
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlanActivity.class);
                //TODO: TRAVEL 완성되면 DATE, TRAVEL ID 받아오게 수정해야 함.
                intent.putExtra("date", "2021-01-01");
                intent.putExtra("travelId", 1);
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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        //탭 레이아웃에 탭 추가
        for(int i=0; i<tabList.size(); i++)
            binding.tabs.addTab(binding.tabs.newTab().setText(tabList.get(i)));

        //탭 선택 이벤트 처리리
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 1, x, y, 1), pos);
                    //requestReadPlan();
                    break;
                case "Bus":
                    planItems.get(pos).setTransport(2);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 2, x, y, 1), pos);
                    //requestReadPlan();
                    break;
                case "Subway":
                    planItems.get(pos).setTransport(3);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 3, x, y, 1), pos);
                    //requestReadPlan();
                    break;
                case "Taxi":
                    planItems.get(pos).setTransport(4);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 4, x, y, 1), pos);
                    //requestReadPlan();
                    break;
                case "Car":
                    planItems.get(pos).setTransport(5);
                    requestUpdatePlan(new PlanCreateData(date, time, place, memo, category, 5, x, y, 1), pos);
                    //requestReadPlan();
                    break;
                default:
                    break;
            }
            requestReadPlan();
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
        intent.putExtra("travelId", 1);
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
                requestReadPlan();
                planIds.remove(pos);
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
                requestReadPlan();
            }
        }
    }

    //일정 목록 조회 - GET : Retrofit2
    private void requestReadPlan(){
        RetrofitClient.getService().readPlan(1).enqueue(new Callback<PlanResponse>( ){
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {
                if(response.isSuccessful()){
                    PlanResponse result = response.body();
                    planItems.clear();
                    planIds.clear();
                    String date="", time="", place="", memo="";
                    int category=0, transport=0;
                    double x=0, y=0;
                    for(int i=0; i<result.getData().size(); i++){
                        planIds.add(result.getData().get(i).getId());
                        Log.d("planid", String.valueOf(planIds.get(i)));
                        date = result.getData().get(i).getDate();
                        time = result.getData().get(i).getTime();
                        place = result.getData().get(i).getPlace();
                        memo = result.getData().get(i).getMemo();
                        category = result.getData().get(i).getCategory();
                        transport = result.getData().get(i).getTransport();
                        x = result.getData().get(i).getX();
                        y = result.getData().get(i).getY();
                        addItem(date, time, place, memo, category, transport, 0, 0, x, y);
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
        //TODO: TRAVEL 완성되면 TRAVEL ID 받아오게 수정해야 함.
        RetrofitClient.getService().updatePlan(planId, 1, data).enqueue(new Callback<StatusResponse>() {
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
        //TODO: TRAVEL 완성되면 TRAVEL ID 받아오게 수정해야 함.
        RetrofitClient.getService().deletePlan(planId, 1).enqueue(new Callback<StatusResponse>() {
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