package com.zkwl.app_healthy.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.FragmentAdapter;
import com.zkwl.app_healthy.databinding.ActivityOrderlistBinding;
import com.zkwl.app_healthy.fragment.OrderListFragment;
import com.zkwl.app_healthy.model.OrderModel;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderListActivity extends BaseActivity<OrderModel, ActivityOrderlistBinding> {

    private List<Fragment> mFragments;
    List<String> titles = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_orderlist;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("订单详情");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(v -> finish());
        mFragments = new ArrayList<>();


        titles.add("全部");
        titles.add("待付款");
        titles.add("待消费");
        titles.add("已完成");
        for (int i=0;i<titles.size();i++){

            Bundle bundle =new Bundle();
            bundle.putString("type",i+"");
            OrderListFragment orderListFragment = new OrderListFragment();
            orderListFragment.setArguments(bundle);
            mFragments.add(orderListFragment);
        }




        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        getViewDataBinding().viewPager.setAdapter(adatper);
        getViewDataBinding().viewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        getViewDataBinding().xTablayout.setupWithViewPager(getViewDataBinding().viewPager);

    }
}
