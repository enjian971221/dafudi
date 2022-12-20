package com.zkwl.qhzwj.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseFragment;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.FragmentMineBinding;
import com.zkwl.qhzwj.model.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MineFragment extends BaseFragment<HomeViewModel, FragmentMineBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getViewDataBinding().topbar.setTitle("我的");

    }
}
