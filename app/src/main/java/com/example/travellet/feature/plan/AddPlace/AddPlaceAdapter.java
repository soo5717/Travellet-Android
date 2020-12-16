package com.example.travellet.feature.plan.AddPlace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travellet.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AddPlaceAdapter extends RecyclerView.Adapter<AddPlaceAdapter.ViewHolder> {
    private ArrayList<AddPlaceItem> mData = null ;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    AddPlaceAdapter(ArrayList<AddPlaceItem> list) {
        mData = list ;
    }

    // 클릭 이벤트 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null; //전달된 리스너 객체를 저장할 변수

    //리스터 객체를 전달하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    @Override
    public AddPlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_add_place, parent, false) ;
        AddPlaceAdapter.ViewHolder vh = new AddPlaceAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(AddPlaceAdapter.ViewHolder holder, int position) {
        AddPlaceItem item = mData.get(position) ;
        //장소 이름, 타입, 주소
        holder.title.setText(item.getTitle()) ;
        holder.type.setText(item.getType()) ;
        holder.addr.setText(item.getAddr()) ;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, type, addr ;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.textview_add_place_title) ;
            addr = itemView.findViewById(R.id.textview_add_place_place) ;
            type = itemView.findViewById(R.id.textview_add_place_type) ;

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
        }
    }
}
