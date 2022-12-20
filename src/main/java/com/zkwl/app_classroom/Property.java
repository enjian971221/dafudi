package com.zkwl.app_classroom;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class Property {

    @BindingAdapter("status")
    public static void setStatus(View view, Object o) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = QMUIDisplayHelper.getStatusBarHeight(view.getContext());
        view.setLayoutParams(params);
    }
}

