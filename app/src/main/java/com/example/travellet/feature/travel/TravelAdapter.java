package com.example.travellet.feature.travel;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 수연 on 2020-11-22.
 * Class: TravelAdapter
 * Description: 여행 목록 리사이클러뷰 어댑터
 */
public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull TravelAdapter.ViewHolder holder, int position) {

    }


    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return 0;
    }
}
