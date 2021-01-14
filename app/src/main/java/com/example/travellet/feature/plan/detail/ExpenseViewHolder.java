package com.example.travellet.feature.plan.detail;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travellet.data.responseBody.TravelReadResponse;
import com.example.travellet.databinding.ItemRecyclerviewBinding;
import com.example.travellet.feature.travel.TravelAdapter;
import com.example.travellet.feature.util.viewpager.ViewHolderPage;
import com.example.travellet.feature.util.viewpager.ViewPagerData;

import java.util.ArrayList;

public class ExpenseViewHolder extends ViewHolderPage {
    ItemRecyclerviewBinding binding;
    public ExpenseViewHolder(@NonNull ItemRecyclerviewBinding binding) {
        super(binding);
        this.binding = binding;
    }
    public void onBind(ViewPagerData arrayList) {

    }
}
