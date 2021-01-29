package com.example.travellet.feature.detail;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.R;
import com.example.travellet.data.viewpager.PlanDetailViewPagerData;
import com.example.travellet.data.viewpager.ViewPagerCase;
import com.example.travellet.databinding.ItemPlanDetailBinding;
import com.example.travellet.feature.detail.budget.AddBudgetActivity;
import com.example.travellet.feature.detail.expense.AddExpenseActivity;
import com.example.travellet.feature.util.PlanDetailUtil;

import java.util.ArrayList;

public class PlanDetailViewPagerAdapter extends RecyclerView.Adapter<PlanDetailViewPagerAdapter.PlanDetailViewHolder> {
    private final ArrayList<PlanDetailViewPagerData> arrayList;

    //생성자
    public PlanDetailViewPagerAdapter(ArrayList<PlanDetailViewPagerData> arrayList) {
        this.arrayList = arrayList;
    }

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public static class PlanDetailViewHolder extends RecyclerView.ViewHolder {
        ItemPlanDetailBinding binding;
        public PlanDetailViewHolder(@NonNull ItemPlanDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public PlanDetailViewPagerAdapter.PlanDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanDetailViewHolder(ItemPlanDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull PlanDetailViewPagerAdapter.PlanDetailViewHolder holder, int position) {
        //출력 형식 변환 관련 유틸
        PlanDetailUtil planDetailUtil = new PlanDetailUtil();

        //각 데이터 항목 get
        ViewPagerCase viewPagerCase = arrayList.get(position).getViewPagerCase();
        int id = arrayList.get(position).getId();
        String title = arrayList.get(position).getPlace();
        String memo = arrayList.get(position).getMemo();
        String startDate = arrayList.get(position).getStartDate();
        String date = arrayList.get(position).getDate();
        int total = arrayList.get(position).getTotal();
        String currency = arrayList.get(position).getCurrency();

        //출력 형식 변환 관련 유틸로 변환 후, setText
        holder.binding.textViewTitle.setText(title);
        holder.binding.textViewMemo.setText(memo);
        holder.binding.textViewDday.setText(planDetailUtil.getDay(startDate, date));
        holder.binding.textViewTotal.setText(planDetailUtil.getTotal(total));

        //리사이클러뷰 어댑터 설정
        PlanDetailAdapter planDetailAdapter = new PlanDetailAdapter(arrayList.get(position).getData(), currency);
        //커스텀 리스너 객체 생성 및 전달 {4번} => PlanDetailAdapter와 이어짐.
        planDetailAdapter.setmOnItemLongClickListener((v, pos) -> {
            //아이템 롱 클릭 이벤트:
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialog);
            builder.setItems(R.array.edit_delete, ((dialog, which) -> {
                if(which == 0) { //수정
                    Intent intent;
                    switch (viewPagerCase) {
                        case BUDGET:
                            intent = new Intent(v.getContext(), AddBudgetActivity.class);
                            intent.putExtra("plan_id", id);
                            intent.putExtra("budget_id", (int)planDetailAdapter.getItemId(pos));
                            intent.putExtra("budget", false);
                            v.getContext().startActivity(intent);
                            break;
                        case EXPENSE:
                            intent = new Intent(v.getContext(), AddExpenseActivity.class);
                            intent.putExtra("plan_id", id);
                            intent.putExtra("expense_id", (int)planDetailAdapter.getItemId(pos));
                            intent.putExtra("expense", false);
                            v.getContext().startActivity(intent);
                            break;
                    }
                } else { //삭제
                    switch (viewPagerCase) {
                        case BUDGET:
                            new PlanDetailActivity().requestDeleteBudget((int)planDetailAdapter.getItemId(pos), planDetailAdapter, pos);
                            break;
                        case EXPENSE:
                            new PlanDetailActivity().requestDeleteExpense((int)planDetailAdapter.getItemId(pos), planDetailAdapter, pos);
                            break;
                    }
                }
            }));
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(arrayList.get(position).getContext()));
        holder.binding.recyclerView.setAdapter(planDetailAdapter);
    }

    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
