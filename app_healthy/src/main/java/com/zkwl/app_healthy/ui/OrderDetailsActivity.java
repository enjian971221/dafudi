package com.zkwl.app_healthy.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.zxing.util.CodeUtils;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.databinding.ActivityOrderdetailBinding;
import com.zkwl.app_healthy.model.OrderModel;
import com.zkwl.app_healthy.util.StatusBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderDetailsActivity extends BaseActivity<OrderModel, ActivityOrderdetailBinding> {

    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_orderdetail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("订单详情");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(v -> finish());
        id = getIntent().getStringExtra("id");

        getViewModel().getOrderdetail(id);
        getViewModel().getliveDataOrderdetail().observe(this,data->{
            if (data.getData().getIntValue("goods_type")==2 && data.getData().getString("consumption_img")!=null){
                getViewDataBinding().receiveQr.setImageBitmap(CodeUtils.createQRCode(data.getData().getString("consumption_img"), 120));
            }

            getViewDataBinding().goodsName.setText(data.getData().getString("goods_name"));
            getViewDataBinding().count.setText(data.getData().getString("count"));
            getViewDataBinding().orderPrice.setText(data.getData().getString("order_price"));
            getViewDataBinding().mobile.setText(data.getData().getString("mobile"));

        });
    }
}
