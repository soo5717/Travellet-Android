package com.example.travellet.feature.plan.distribute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.travellet.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistributeBudgetAdapter extends RecyclerView.Adapter<DistributeBudgetAdapter.ViewHolder>{
    private ArrayList<DistributeBudgetItem> mData = null;
    Context context;
    double totalBudget, itemBudget; //totalBudget -> 모든 카테고리의 총 예산, itemBudget -> 하나의 카테고리 예산
    DecimalFormat formatter = new DecimalFormat("###,###.##");

    //생성자에서 데이터 리스트 객체를 전달받음.
    DistributeBudgetAdapter(ArrayList<DistributeBudgetItem> list) {
        mData = list;
    }

    //클릭 이벤트 리스너 인터페이스 정의
    public interface OnSeekbarChangeListener
    {
        void onSeekbarChange(SeekBar s, int pos);
    }

    private OnSeekbarChangeListener sListener = null; // 전달된 리스너 객체를 저장하는 변수

    //리스너 객체 전달하는 메소드
    public void setOnSeekbarChangeListener(OnSeekbarChangeListener listener)
    {
        this.sListener = listener;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public DistributeBudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_distribute_budget, parent, false) ;
        DistributeBudgetAdapter.ViewHolder vh = new DistributeBudgetAdapter.ViewHolder(view) ;
        return vh ;
    }
    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull DistributeBudgetAdapter.ViewHolder holder, int position) {
        DistributeBudgetItem item = mData.get(position);
        //예산 0인 애가 없는 카테고리는 표시하지 않음.
        String category = "";
        switch (item.getCategory()) {
            case 1:
                category = "LODGING - ";
                break;
            case 2:
                category = "FOOD - ";
                break;
            case 3:
                category = "SHOPPING - ";
                break;
            case 4:
                category = "TOURISM - ";
                break;
            case 6:
                category = "ETC. - ";
                break;

        }

        holder.category.setText(category + item.getCount());

        holder.budget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " + formatter.format(item.getBudget()));

        holder.seekBar.setMax((int)totalBudget);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setTotalBudget(double budget){
        totalBudget = budget;
        itemBudget = budget;
    }

    public double getTotalBudget(){
        return itemBudget;
    }

// 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, budget;
        SeekBar seekBar;

        ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.text_view_category);
            budget = itemView.findViewById(R.id.text_view_price);
            seekBar = itemView.findViewById(R.id.seek_bar_budget);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    double settingBudget = 0;
                    int position = getAdapterPosition();
                    DistributeBudgetItem item = mData.get(position);
                    if(position != RecyclerView.NO_POSITION){
                        if (i % 10 == 0) {
                            budget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " +formatter.format(i));
                            item.setBudget(i);
                        } else {
                            seekBar.setProgress((i / 10) * 10);
                            budget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " + formatter.format((i / 10) * 10));
                            item.setBudget((i / 10) * 10);
                        }
                        for(int j=0; j<mData.size(); j++){
                            settingBudget += mData.get(j).getBudget();
                            Log.d("settingBudgetProgress "+ j, Double.toString(settingBudget));
                        }
                        itemBudget = totalBudget - settingBudget;
                        Log.d("itemBudgetProgress ", Double.toString(itemBudget));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    double settingBudget = 0;
                    int position = getAdapterPosition();
                    DistributeBudgetItem item = mData.get(position);
                    if(position != RecyclerView.NO_POSITION){
                        if(sListener != null){
                            sListener.onSeekbarChange(seekBar, position);
                            for(int j=0; j<mData.size(); j++){
                                settingBudget += mData.get(j).getBudget();
                                Log.d("settingBudgetProgress "+ j, Double.toString(settingBudget));
                            }
                            itemBudget = totalBudget - settingBudget;
                            Log.d("itemBudgetProgress ", Double.toString(itemBudget));
                        }
                    }
                }
            });
        }
    }

}
