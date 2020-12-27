package com.example.travellet.feature.plan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travellet.R;
import com.example.travellet.feature.place.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private ArrayList<PlanItem> mData = null;
    Context context;

    //생성자에서 데이터 리스트 객체를 전달받음.
    PlanAdapter(ArrayList<PlanItem> list) {
        mData = list;
    }

    //클릭 이벤트 리스너 인터페이스 정의
    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    //롱 클릭 이벤트 인터페이스 정의의
   public interface OnItemLongClickListener{
        void onItemLongClick(View v, int pos);
    }

    private OnItemClickListener mListener = null; // 전달된 리스너 객체를 저장하는 변수
    private OnItemLongClickListener mLongListener = null;

    //리스너 객체 전달하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    //얘는 롱 클릭
    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        this.mLongListener = listener;
    }


    //교통 선택 클릭 이벤트 인터페이스
    public interface OnTransportClickListener {
        void onTransportClick(View v, int pos);
    }

    private OnTransportClickListener tListener = null;

    public void setOnTransportClickListener(OnTransportClickListener listener) {
        this.tListener = listener;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_plan, parent, false) ;
        PlanAdapter.ViewHolder vh = new PlanAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {
        PlanItem item = mData.get(position);

        String[] arr = item.getTime().split(":");
        String meridiem, hour, min, time;
        if(Integer.parseInt(arr[0])<12) {
            meridiem = "AM";
            hour = arr[0];
        }
        else {
            meridiem = "PM";
            hour = String.valueOf(Integer.parseInt(arr[0]) - 12);
            if(Integer.parseInt(arr[0]) - 12 < 10)
                hour = "0"+hour;
        }
        min = arr[1];
        time = meridiem + " " + hour + ":" + min;

        holder.time.setText(time);
        holder.place.setText(item.getPlace());

        switch (item.getTransport()){
            case 1:
                holder.transport.setText("Walk");
                holder.transport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_walk_16dp, 0, 0, 0);
                break;
            case 2:
                holder.transport.setText("Bus");
                holder.transport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bus_16_dp, 0, 0, 0);
                break;
            case 3:
                holder.transport.setText("Subway");
                holder.transport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_subway_16dp, 0, 0, 0);
                break;
            case 4:
                holder.transport.setText("Taxi");
                holder.transport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_taxi_16dp, 0, 0, 0);
                break;
            case 5:
                holder.transport.setText("Car");
                holder.transport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_car_16dp, 0, 0, 0);
                break;
            default:
                break;
        }

        switch (item.getType()){
            case 1:
                holder.category.setText("Lodging");
                holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lodging_16dp, 0, 0, 0);
                break;
            case 2:
                holder.category.setText("Food");
                holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_food_16dp, 0, 0, 0);
                break;
            case 3:
                holder.category.setText("Shopping");
                holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_shopping_16dp, 0, 0, 0);
                break;
            case 4:
                holder.category.setText("Tourism");
                holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tourism_16dp, 0, 0, 0);

                break;
            case 5:
                holder.category.setText("Etc.");
                holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_etc_16dp, 0, 0, 0);
                break;
            default:
                break;
        }

        holder.budget.setText("Budget " + String.valueOf(item.getBudget()));
        holder.expense.setText("Expense " + String.valueOf(item.getExpense()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView time, place, transport, category, budget, expense;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            time = itemView.findViewById(R.id.text_view_time);
            place = itemView.findViewById(R.id.text_view_title);
            category = itemView.findViewById(R.id.text_view_category);
            transport = itemView.findViewById(R.id.text_view_transport);
            budget = itemView.findViewById(R.id.text_view_budget);
            expense = itemView.findViewById(R.id.text_view_expense);


            //아이템 클릭이벤트처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });

            //아이템 롱클릭 이벤트 처리
            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                        mLongListener.onItemLongClick(v, position);
                    return true;
                }
            });

            //교통 선택 클릭 이벤트 처리
            transport.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(tListener != null)
                            tListener.onTransportClick(view, position);
                    }
                }
            });
        }
    }
}
