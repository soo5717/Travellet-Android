package com.example.travellet.feature.travel;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.R;
import com.example.travellet.data.StatusResponse;
import com.example.travellet.data.responseBody.TravelReadResponse;
import com.example.travellet.databinding.ItemTravelBinding;
import com.example.travellet.feature.plan.PlanActivity;
import com.example.travellet.feature.util.TravelUtil;
import com.example.travellet.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 수연 on 2020-11-22.
 * Class: TravelAdapter
 * Description: 여행 목록 리사이클러뷰 어댑터
 * => viewbinding을 사용하였음!
 */
public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private final ArrayList<TravelReadResponse.Data.Travel> data;

    //커스텀 리스너 인터페이스 정의 {1번}
    public interface  OnItemClickListener {
        void onItemClick(View v, int pos);
    }
    public interface  OnItemLongClickListener {
        void onItemLongClick(View v, int pos);
    }

    //리스너 객체 참조를 저장하는 변수 {2번}
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    //리스너 객체 참조를 어댑터에 전달하는 메소드 {2번}
    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setmOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    //생성자
    public TravelAdapter(ArrayList<TravelReadResponse.Data.Travel> data) {
        this.data = data;
    }

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemTravelBinding binding;
        public ViewHolder(@NonNull ItemTravelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            
            // 리스너 객체 메소드 호출 {3번}
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                }
            });
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    if(mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(v, pos);
                    }
                }
                return false;
            });
        }
    }

    //아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTravelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull TravelAdapter.ViewHolder holder, int position) {
        //출력 형식 변환 관련 유틸
        TravelUtil travelUtil = new TravelUtil();

        //각 데이터 항목 get
        String title = data.get(position).getTitle();
        String startDate = data.get(position).getStartDate();
        String endDate = data.get(position).getEndDate();
        Double budget = data.get(position).getBudget();
        Double sumBudget = Double.parseDouble(data.get(position).getSumBudget());
        Double sumExpense = Double.parseDouble(data.get(position).getSumExpense());

        //출력 형식 변환 관련 유틸로 변환 후, setText
        holder.binding.textViewTitle.setText(title);
        holder.binding.textViewDate.setText(travelUtil.getDate(startDate, endDate));
        holder.binding.textViewDday.setText(travelUtil.getDday(startDate));
        holder.binding.progressBarExpense.setProgress(travelUtil.getProgress(budget, sumBudget, sumExpense));
        holder.binding.textViewExpense.setText(travelUtil.getExpense(budget, sumBudget, sumExpense));
        holder.binding.textViewExpensePercent.setText(travelUtil.getExpensePercent(budget, sumBudget, sumExpense));
    }

    //리사클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return data.size();
    }

    //여행 아이디 리턴 : 페이지 이동 및 삭제를 위해 필요
    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    //여행 삭제 : 아이템 삭제 후 갱신
    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
}