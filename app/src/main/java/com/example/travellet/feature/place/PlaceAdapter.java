package com.example.travellet.feature.place;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travellet.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private ArrayList<PlaceItem> mData = null ;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    PlaceAdapter(ArrayList<PlaceItem> list) {
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

    //좋아요 클릭 이벤트 인터페이스
    public interface OnLikeClickListener {
        void onLikeClick(View v, int pos);
    }

    private OnLikeClickListener lListener = null;

    public void setOnLikeClickListener(OnLikeClickListener listener) { this.lListener = listener; }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_place, parent, false) ;
        PlaceAdapter.ViewHolder vh = new PlaceAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        PlaceItem item = mData.get(position) ;

        //썸네일(둥글게, 이미지 센터 크롭)
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getContext().getDrawable(R.drawable.image_rounding);
        holder.thumb.setBackground(drawable);
        if(!item.getPlaceListThumb().equals("NULL")){
            holder.thumb.setClipToOutline(true);
            Glide.with(holder.itemView.getContext()).load(item.getPlaceListThumb()).apply(new RequestOptions().centerCrop()).into(holder.thumb);
        }
        else{
            holder.thumb.setBackground(drawable);
        }
        //장소 이름, 주소
        holder.title.setText(item.getPlaceListTitle()) ;
        holder.addr.setText(item.getPlaceListAddr()) ;

        //좋아요 여부
        if(item.getlikeState()){
            holder.like.setImageResource(R.drawable.ic_favorite_selected_24_dp);
        } else{
            holder.like.setImageResource(R.drawable.ic_favorite_border_list_24dp);
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb, like ;
        TextView title ;
        TextView addr ;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            thumb = itemView.findViewById(R.id.imageview_place_thumbnail) ;
            title = itemView.findViewById(R.id.textview_place_title) ;
            addr = itemView.findViewById(R.id.textview_place_place) ;
            like = itemView.findViewById(R.id.button_place_like);

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

            //좋아요 버튼 클릭 이벤트
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(lListener != null){
                            lListener.onLikeClick(view, position);
                        }
                    }

                }
            });
        }
    }

}
