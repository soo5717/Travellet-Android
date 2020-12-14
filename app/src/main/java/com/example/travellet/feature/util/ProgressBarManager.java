package com.example.travellet.feature.util;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by 수연 on 2020-12-14.
 * Class: ProgressBarManager
 * Description: 네트워킹 동안 보여줄 진행바 유틸.
 * => 더 복잡한 구현도 있긴 할텐데 일단은 간단하게 구현함.
 */
public class ProgressBarManager {
    public static void showProgress(ProgressBar progressBar, boolean show) {
        progressBar.setVisibility(show? View.VISIBLE : View.GONE);
    }
}
