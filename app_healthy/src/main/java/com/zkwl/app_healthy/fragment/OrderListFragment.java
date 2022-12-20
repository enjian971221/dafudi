package com.zkwl.app_healthy.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.OrderAdapter;
import com.zkwl.app_healthy.databinding.FragmentOrderlistBinding;
import com.zkwl.app_healthy.model.OrderModel;
import com.zkwl.app_healthy.ui.GoodsDetailsActivity;
import com.zkwl.app_healthy.ui.OrderDetailsActivity;
import com.zkwl.app_healthy.ui.OrderListActivity;
import com.zkwl.app_healthy.util.IsInstallWeChatOrAliPayUtil;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderListFragment extends BaseFragment<OrderModel, FragmentOrderlistBinding> {

    private List<JSONObject> mList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private String type;
    public int mPage = 1;
    public int mPageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_orderlist;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getArguments().getString("type");

        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getViewModel().getOrderList(type, mPage + "", mPageSize + "");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                mList.clear();
                mPage = 1;
                getViewModel().getOrderList(type, mPage + "", mPageSize + "");
            }
        });


        orderAdapter = new OrderAdapter(getContext(), mList, R.layout.item_order, new OrderAdapter.click() {
            @Override
            public void itemdetails(JSONObject item, int position) {
                startActivity(new Intent(getContext(), OrderDetailsActivity.class).putExtra("id", item.getString("id")));
            }

            @Override
            public void itembuy(JSONObject item, int position) {
                startActivity(new Intent(getContext(), GoodsDetailsActivity.class)
                        .putExtra("goods_type",item.getIntValue("goods_type"))
                        .putExtra("id",item.getString("id"))
                );
            }

            @Override
            public void itempay(JSONObject item, int position) {
                payShowDialog(item.getDouble("order_price"), item.getString("id"));
            }

        });

        getViewDataBinding().recyclerView.setAdapter(orderAdapter);

        mPage = 1;
        getViewModel().getOrderList(type, mPage + "", mPageSize + "");


        getViewModel().getadlistLiveData().observe(this, data -> {

            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            if (data.getData().getJSONArray("list").size() == 0) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i = 0; i < data.getData().getJSONArray("list").size(); i++) {
                mList.add(data.getData().getJSONArray("list").getJSONObject(i));
            }
            orderAdapter.notifyDataSetChanged();
        });

        getViewModel().getliveDataGospayAli().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                payWChatPay(data.getData().getJSONObject("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(), "支付成功");
                startActivity(new Intent(getContext(), OrderListActivity.class));
                finish();
            }
        });
        getViewModel().getliveDataGospayWechat().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                aliPay(data.getData().getString("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(), "支付成功");
                startActivity(new Intent(getContext(), OrderListActivity.class));
                finish();
            }
        });
    }

    private void payShowDialog(Double price, String id) {
        PayDetailFragment payDetailFragment = new PayDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("good_order_type", "订单");
        bundle.putDouble("owner_balance", 0.0);
        bundle.putDouble("pay_money", price);
        payDetailFragment.setArguments(bundle);
        payDetailFragment.setPayDialogListener(new PayDialogListener() {
            @Override
            public void payNextAli() {
                //支付宝
                if (IsInstallWeChatOrAliPayUtil.checkAliPayInstalled(getActivity())) {
                    getViewModel().getGospayAli(id);

                } else {
                    ToastUtils.showToast(getActivity(), "未安装支付宝，请下载....");
                }
            }

            @Override
            public void payNextWchat() {
                //微信
                if (IsInstallWeChatOrAliPayUtil.isWeixinAvilible(getActivity())) {
                    getViewModel().getGospayWechat(id);
                } else {
                    ToastUtils.showToast(getActivity(), "未安装微信，请下载....");
                }
            }

            @Override
            public void payNextMe() {

            }

            @Override
            public void payCancel() {
            }
        });
        payDetailFragment.show(getChildFragmentManager(), "payInfoFragment");
    }




    private void payWChatPay(JSONObject data) {

        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", data.toString());
        intent.putExtra("type", "wechat");
        getActivity().sendBroadcast(intent);
    }


    public void aliPay(String pay_params) {


        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", pay_params);
        intent.putExtra("type", "alipay");
        getActivity().sendBroadcast(intent);

    }
}
