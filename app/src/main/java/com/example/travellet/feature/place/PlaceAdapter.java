package com.example.travellet.feature.place;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        /*GradientDrawable drawable = (GradientDrawable) getApplicationContext().getDrawable(R.drawable.image_rounding);
        placeListThumb.setBackground(drawable);
        if(!thumb.equals("NULL")){
            placeListThumb.setClipToOutline(true);
            Glide.with(getContext()).load(thumb).into(placeListThumb);
        }
        else{
        }*/
        //holder.icon.setImageDrawable(item.getPlaceListThumb()) ;
        holder.title.setText(item.getPlaceListTitle()) ;
        holder.addr.setText(item.getPlaceListAddr()) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb ;
        TextView title ;
        TextView addr ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            thumb = itemView.findViewById(R.id.imageview_place_thumbnail) ;
            title = itemView.findViewById(R.id.textview_place_title) ;
            addr = itemView.findViewById(R.id.textview_place_place) ;
        }
    }

}
