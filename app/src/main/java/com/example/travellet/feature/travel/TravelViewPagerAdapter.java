package com.example.travellet.feature.travel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.R;
import com.example.travellet.databinding.ItemRecyclerviewBinding;
import com.example.travellet.feature.plan.PlanActivity;
import com.example.travellet.data.viewpager.TravelViewPagerData;

import java.util.ArrayList;

/**
 * Created by 수연 on 2020-12-27.
 * Class: TravelViewPagerAdapter
 * Description: Travel 뷰페이저 어댑터
 */
public class TravelViewPagerAdapter extends RecyclerView.Adapter<TravelViewPagerAdapter.TravelViewHolder> {
    private final ArrayList<TravelViewPagerData> arrayList;

    //생성자
    public TravelViewPagerAdapter(ArrayList<TravelViewPagerData> arrayList) {
        this.arrayList = arrayList;
    }

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public static class TravelViewHolder extends RecyclerView.ViewHolder {
        ItemRecyclerviewBinding binding;
        public TravelViewHolder(@NonNull ItemRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public TravelViewPagerAdapter.TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull TravelViewPagerAdapter.TravelViewHolder holder, int position) {
        TravelAdapter travelAdapter = new TravelAdapter(arrayList.get(position).getData());
        //커스텀 리스너 객체 생성 및 전달 {4번} => TravelAdapter와 이어짐.
        travelAdapter.setmOnItemClickListener((v, pos) -> {
            //아이템 클릭 이벤트: 일정 페이지로 이동 (travel_id 함께 전달)
            Intent intent = new Intent(v.getContext(), PlanActivity.class);
            intent.putExtra("travel_id", (int)travelAdapter.getItemId(pos));
            intent.putExtra("startDate", arrayList.get(position).getData().get(pos).getStartDate());
            intent.putExtra("endDate", arrayList.get(position).getData().get(pos).getEndDate());
            ((Activity)v.getContext()).startActivityForResult(intent, 200);
        });
        travelAdapter.setmOnItemLongClickListener((v, pos) -> {
            //아이템 롱 클릭 이벤트:
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialog);
            builder.setItems(R.array.delete, ((dialog, which) ->
                    new TravelActivity().requestDeleteTravel((int)travelAdapter.getItemId(pos), travelAdapter, pos)));
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(arrayList.get(position).getContext()));
        holder.binding.recyclerView.setAdapter(travelAdapter);
    }

    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
