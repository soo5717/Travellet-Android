package com.example.travellet.feature.plan.distribute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.travellet.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistributeBudgetAdapter extends RecyclerView.Adapter<DistributeBudgetAdapter.ViewHolder>{
    private ArrayList<DistributeBudgetItem> mData = null;
    Context context;

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
        Log.d("category", item.getCategory());
        Log.d("budget", Double.toString(item.getBudget()));
        holder.category.setText(item.getCategory());
        holder.budget.setText(Currency.getInstance(Locale.KOREA).getSymbol() + " " + item.getBudget());
    }
    @Override
    public int getItemCount() {
        return mData.size();
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
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(sListener != null){
                            sListener.onSeekbarChange(seekBar, position);
                            budget.setText(String.valueOf(i));
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }
}
