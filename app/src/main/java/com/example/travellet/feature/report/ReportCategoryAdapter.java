package com.example.travellet.feature.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travellet.R;
import com.example.travellet.feature.plan.PlanItem;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportCategoryAdapter extends RecyclerView.Adapter<ReportCategoryAdapter.ViewHolder>{
    private ArrayList<ReportCategoryItem> mData = null;
    Context context;

    DecimalFormat formatter = new DecimalFormat("###,###.##");

    //생성자에서 데이터 리스트 객체를 전달받음.
    ReportCategoryAdapter(ArrayList<ReportCategoryItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ReportCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_report, parent, false) ;
        ReportCategoryAdapter.ViewHolder vh = new ReportCategoryAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ReportCategoryAdapter.ViewHolder holder, int position) {
        ReportCategoryItem item = mData.get(position);

        holder.title.setText(item.getMemo());
        holder.date.setText(item.date);
        switch (item.getPayment()){
            case 0:
                holder.payment.setText("CASH");
                break;
            case 1:
                holder.payment.setText("CREDIT");
        }
        holder.expense.setText(item.getExpense());
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, payment, expense;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.text_view_title);
            date = itemView.findViewById(R.id.text_view_category);
            payment = itemView.findViewById(R.id.text_view_credit);
            expense = itemView.findViewById(R.id.text_view_price);
        }
    }
}
