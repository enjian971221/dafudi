package com.zkwl.app_classroom.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ActivityPaysuccessBinding;
import com.zkwl.app_classroom.model.ClassRomModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class PaySuccessActivity extends BaseActivity<ClassRomModel, ActivityPaysuccessBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_paysuccess;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().sybmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
