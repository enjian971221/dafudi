package com.zkwl.qhzwj.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityShopgoodinfoBinding;
import com.zkwl.qhzwj.model.StoreViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class ShopGoodInfoActivity extends BaseActivity<StoreViewModel, ActivityShopgoodinfoBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_shopgoodinfo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
