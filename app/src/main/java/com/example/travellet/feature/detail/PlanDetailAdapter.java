package com.example.travellet.feature.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.data.responseBody.PlanDetailResponse;
import com.example.travellet.databinding.ItemPriceBinding;
import com.example.travellet.feature.travel.TravelAdapter;
import com.example.travellet.feature.util.PlanDetailUtil;

import java.util.ArrayList;

/**
 * Created by 수연 on 2021-01-17.
 * Class: PlanDetailAdapter
 * Description: 예산/지출 목록 리사이클러뷰 어댑터
 */
public class PlanDetailAdapter extends RecyclerView.Adapter<PlanDetailAdapter.ViewHolder> {
    private final ArrayList<PlanDetailResponse.Data.Datum> data;

    //커스텀 리스너 인터페이스 정의 {1번}
    public interface  OnItemLongClickListener {
        void onItemLongClick(View v, int pos);
    }

    //리스너 객체 참조를 저장하는 변수 {2번}
    private OnItemLongClickListener mOnItemLongClickListener = null;
    //리스너 객체 참조를 어댑터에 전달하는 메소드 {2번}
    public void setmOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    //생성자
    public PlanDetailAdapter(ArrayList<PlanDetailResponse.Data.Datum> data) {
        this.data = data;
    }

    //아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemPriceBinding binding;
        public ViewHolder(@NonNull ItemPriceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // 리스너 객체 메소드 호출 {3번}
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
    public PlanDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPriceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull PlanDetailAdapter.ViewHolder holder, int position) {
        //출력 형식 변환 관련 유틸
        PlanDetailUtil planDetailUtil = new PlanDetailUtil();

        //각 데이터 항목 get
        String currency = data.get(position).getCurrency();
        Double price = data.get(position).getPrice();
        Double priceKrw = data.get(position).getPriceKrw();
        String memo = data.get(position).getMemo();
        Integer category = data.get(position).getCategory();
        Boolean payment = data.get(position).getPayment();

        holder.binding.textViewTitle.setText(memo);
        holder.binding.textViewPrice.setText(planDetailUtil.getPrice(priceKrw));
        holder.binding.textViewCategory.setText(planDetailUtil.getCategory(category));
        holder.binding.textViewCredit.setText(planDetailUtil.getPayment(payment));
        holder.binding.textViewExchange.setText(planDetailUtil.getExchange(currency, price));
    }

    //리사이클러뷰 아이템 개수 리턴 : 필수적으로 지정해주어야 함!
    @Override
    public int getItemCount() {
        return data.size();
    }
    
    //예산 아이디 반환 : 수정 및 삭제를 위해 필요
    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }
    
    //예산 삭제 : 아이템 삭제 후 갱신
    public void removeItem(int postion) {
        data.remove(postion);
        notifyItemRemoved(postion);
    }
}
