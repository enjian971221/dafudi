package com.zkwl.app_healthy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthyclassifyAdapter;
import com.zkwl.app_healthy.databinding.ActivityBuydetailsBinding;
import com.zkwl.app_healthy.fragment.PayDetailFragment;
import com.zkwl.app_healthy.fragment.PayDialogListener;
import com.zkwl.app_healthy.model.HealthyGoodsModel;
import com.zkwl.app_healthy.util.IsInstallWeChatOrAliPayUtil;
import com.zkwl.app_healthy.util.SpsUtil;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class BuyDetailsActivity extends BaseActivity<HealthyGoodsModel, ActivityBuydetailsBinding> {

    private String id;
    private HealthyclassifyAdapter healthyclassifyAdapter;
    private List<JSONObject> mclassifyList = new ArrayList<>();
    private Double banlnce;
    private JSONObject tump;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buydetails;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("购买详情");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        id = getIntent().getStringExtra("id");
        getViewModel().getGoodsmoney(id, SpsUtil.getString(getContext(), "shop_id", ""));


        getViewModel().getBalanceinfo();
        getViewModel().getliveDataBalanceinfo().observe(this, data -> {
            banlnce = data.getData().getDouble("balance");
        });


        getViewModel().getgoodmoneyLiveData().observe(this, data -> {
            if (data.getData().getIntValue("goods_type") == 2) {
                getViewDataBinding().recyclerView.setVisibility(View.GONE);
            } else {
                getViewDataBinding().recyclerView.setVisibility(View.VISIBLE);
                mclassifyList.clear();
                for (int i = 0; i < data.getData().getJSONArray("goods_list").size(); i++) {
                    mclassifyList.add(data.getData().getJSONArray("goods_list").getJSONObject(i));
                }
                healthyclassifyAdapter.notifyDataSetChanged();
            }

            getViewDataBinding().goodsName.setText(data.getData().getString("goods_name"));
            getViewDataBinding().orderTotalNum.setText(data.getData().getIntValue("order_total_num") + "");
            getViewDataBinding().orderPayPrice.setText(data.getData().getString("order_pay_price") + "");
            getViewDataBinding().orderPayPrice1.setText("应支付：" + data.getData().getString("order_pay_price") + "");
            getViewDataBinding().noticeContent.setText(data.getData().getString("notice_content") + "");
            tump = data.getData();
        });

        healthyclassifyAdapter = new HealthyclassifyAdapter(getContext(), mclassifyList, R.layout.item_healthyhomeclassify, new HealthyclassifyAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
            }
        });
        getViewDataBinding().recyclerView.setAdapter(healthyclassifyAdapter);


        getViewDataBinding().paytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payShowDialog(tump.getDouble("order_pay_price"));
            }
        });


        getViewModel().getgoodpaywechatLiveData().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                payWChatPay(data.getData().getJSONObject("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(), "支付成功");
                startActivity(new Intent(getContext(), OrderListActivity.class));
                finish();
            }
        });
        getViewModel().getgoodpayaliLiveData().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                aliPay(data.getData().getString("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(), "支付成功");
                startActivity(new Intent(getContext(), OrderListActivity.class));
                finish();
            }
        });


    }

    private void payShowDialog(Double price) {
        PayDetailFragment payDetailFragment = new PayDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("good_order_type", "订单");
        bundle.putDouble("owner_balance", banlnce);
        bundle.putDouble("pay_money", price);
        payDetailFragment.setArguments(bundle);
        payDetailFragment.setPayDialogListener(new PayDialogListener() {
            @Override
            public void payNextAli() {
                //支付宝
                if (IsInstallWeChatOrAliPayUtil.checkAliPayInstalled(BuyDetailsActivity.this)) {
                    getViewModel().getGoodspayAli( id, SpsUtil.getString(getContext(), "shop_id", ""));
                } else {
                    ToastUtils.showToast(BuyDetailsActivity.this, "未安装支付宝，请下载....");
                }
            }

            @Override
            public void payNextWchat() {
                //微信
                if (IsInstallWeChatOrAliPayUtil.isWeixinAvilible(BuyDetailsActivity.this)) {
                    getViewModel().getGoodspayWechat( id, SpsUtil.getString(getContext(), "shop_id", ""));
                } else {
                    ToastUtils.showToast(BuyDetailsActivity.this, "未安装微信，请下载....");
                }
            }

            @Override
            public void payNextMe() {

            }

            @Override
            public void payCancel() {
            }
        });
        payDetailFragment.show(getSupportFragmentManager(), "payInfoFragment");
    }




    private void payWChatPay(JSONObject data) {

        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", data.toString());
        intent.putExtra("type", "wechat");
        sendBroadcast(intent);
    }


    public void aliPay(String pay_params) {


        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", pay_params);
        intent.putExtra("type", "alipay");
        sendBroadcast(intent);

    }
}
