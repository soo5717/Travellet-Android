package com.example.travellet.feature.util.viewpager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellet.databinding.ItemRecyclerviewBinding;

/**
 * Created by 수연 on 2020-12-25.
 * Class: ViewHolderPage
 * Description: 모듈화를 위한 기본 뷰 홀더
 */
public class ViewHolderPage extends RecyclerView.ViewHolder{
    public ViewHolderPage(@NonNull ItemRecyclerviewBinding binding) {
        super(binding.getRoot());
    }
}
