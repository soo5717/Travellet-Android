package com.example.travellet.feature.util.viewpager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.databinding.ItemRecyclerviewBinding;
import com.example.travellet.feature.plan.detail.BudgetViewHolder;
import com.example.travellet.feature.plan.detail.ExpenseViewHolder;
import com.example.travellet.feature.travel.TravelViewHolder;

import java.util.ArrayList;

/**
 * Created by 수연 on 2020-12-27.
 * Class: ViewPagerAdapter
 * Description: 공통 뷰페이저 어댑터
 */
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewHolderPage> {
    private final ArrayList<ViewPagerData> arrayList;
    private final ViewPagerCase viewPagerCase;

    public ViewPagerAdapter(ArrayList<ViewPagerData> arrayList, ViewPagerCase viewPagerCase) {
        this.arrayList = arrayList;
        this.viewPagerCase = viewPagerCase;
    }

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewPagerCase) {
            case TRAVEL:
                return new TravelViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case BUDGET:
                return new BudgetViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case EXPENSE:
                return new ExpenseViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            default:
                return null;
        }
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPage holder, int position) {
        //instaceof는 객체 타입 확인하는데 사용.
        if(holder instanceof TravelViewHolder) { //여행
            TravelViewHolder travelViewHolder = (TravelViewHolder) holder;
            travelViewHolder.onBind(arrayList.get(position));
        } else if(holder instanceof BudgetViewHolder) { //예산
            BudgetViewHolder budgetViewHolder = (BudgetViewHolder) holder;
            budgetViewHolder.onBind(arrayList.get(position));

        } else if(holder instanceof ExpenseViewHolder) { //지출
            ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder) holder;
            expenseViewHolder.onBind(arrayList.get(position));
        }
    }

    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
