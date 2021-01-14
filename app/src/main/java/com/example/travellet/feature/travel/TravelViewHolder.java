package com.example.travellet.feature.travel;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.responseBody.TravelReadResponse;
import com.example.travellet.databinding.ItemRecyclerviewBinding;
import com.example.travellet.feature.plan.PlanActivity;
import com.example.travellet.feature.util.viewpager.ViewHolderPage;
import com.example.travellet.feature.util.viewpager.ViewPagerData;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelViewHolder extends ViewHolderPage {
    ItemRecyclerviewBinding binding;
    TravelAdapter mTravelAdapter;

    public TravelViewHolder(@NonNull ItemRecyclerviewBinding binding) {
        super(binding);
        this.binding = binding;
    }

    public void onBind(ViewPagerData arrayList) {
        //타입 체크 관련 방식 => https://dwenn.tistory.com/91
        ArrayList<TravelReadResponse.Data.Travel> data = new ArrayList<>();
        if(arrayList.getData() != null) {
            for (Object object : arrayList.getData()) {
                if (object instanceof TravelReadResponse.Data.Travel) {
                    data.add((TravelReadResponse.Data.Travel) object);
                }
            }
            mTravelAdapter = new TravelAdapter(data);
            //커스텀 리스너 객체 생성 및 전달 {4번} => TravelAdapter와 이어짐.
            mTravelAdapter.setmOnItemClickListener((v, pos) -> {
                //아이템 클릭 이벤트: 일정 페이지로 이동 (travel_id 함께 전달)
                Intent intent = new Intent(v.getContext(), PlanActivity.class);
                intent.putExtra("travel_id", (int)mTravelAdapter.getItemId(pos));
                v.getContext().startActivity(intent);
            });
            mTravelAdapter.setmOnItemLongClickListener((v, pos) -> {
                //아이템 롱 클릭 이벤트:
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialog);
                builder.setItems(R.array.delete, ((dialog, which) -> {
                    requestDeleteTravel((int)mTravelAdapter.getItemId(pos), pos);
                }));
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(arrayList.getContext()));
            binding.recyclerView.setAdapter(mTravelAdapter);
        }
    }

    //여행 삭제 요청 - DELTE : Retrofit2
    private void requestDeleteTravel(int id, int pos) {
        RetrofitClient.getService().deleteTravel(id).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //리사이클러뷰 아이템 삭제
                    mTravelAdapter.removeItem(pos);
                }
            }
            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {
                Log.d("여행 삭제 에러", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
