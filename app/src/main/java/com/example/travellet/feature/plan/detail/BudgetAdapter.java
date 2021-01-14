package com.example.travellet.feature.plan.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.databinding.ItemBudgetBinding;

class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBudgetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull BudgetAdapter.ViewHolder holder, int position) {

    }

    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return 0;
    }

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        ItemBudgetBinding binding;
        public ViewHolder(@NonNull ItemBudgetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
