package com.zkwl.app_healthy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthyAdapter;
import com.zkwl.app_healthy.databinding.ActivitySearchgoodsBinding;
import com.zkwl.app_healthy.model.HealthyModel;
import com.zkwl.app_healthy.util.SpsUtil;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchGoodsActivity extends BaseActivity<HealthyModel, ActivitySearchgoodsBinding> {


    private HealthyAdapter healthyAdapter;
    private String shop_id;
    private List<JSONObject> imglist = new ArrayList<>();
    public int mPage = 1;
    public int mPageSize = 10;
    private String goodstype="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_searchgoods;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        StatusBar.setStatusBar(this,true);
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        getViewDataBinding().topbar.setTitle("搜索商品");

        if (StringUtils.isNotBlank(getIntent().getStringExtra("title"))){
            getViewDataBinding().topbar.setTitle(getIntent().getStringExtra("title"));
        }
        if (StringUtils.isNotBlank(getIntent().getStringExtra("type"))){
            goodstype= getIntent().getStringExtra("type");
        }

        healthyAdapter = new HealthyAdapter(getContext(), imglist, R.layout.item_healthyhome, new HealthyAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                startActivity(new Intent(getContext(),GoodsDetailsActivity.class)
                        .putExtra("goods_type",item.getIntValue("goods_type"))
                        .putExtra("id",item.getString("id"))
                );
            }
        });

        getViewDataBinding().recyclerView.setAdapter(healthyAdapter);

        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getViewModel().goodsAlllist(getViewDataBinding().edSearch.getText().toString(), shop_id, goodstype, mPage + "", mPageSize + "", "");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                imglist.clear();
                mPage = 1;
                getViewModel().goodsAlllist(getViewDataBinding().edSearch.getText().toString(), shop_id, goodstype, mPage + "", mPageSize + "", "");
            }
        });

        getViewDataBinding().edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    StatusBar.chageInputState(getContext());
                    mPage=1;
                    imglist.clear();
                    getViewModel().goodsAlllist(getViewDataBinding().edSearch.getText().toString(), shop_id, goodstype, mPage + "", mPageSize + "", "");
                    return true;
                }
                return false;
            }
        });



        shop_id = SpsUtil.getString(getContext(),"shop_id","");
        getViewModel().goodsAlllist(getViewDataBinding().edSearch.getText().toString(), shop_id, goodstype, mPage + "", mPageSize + "", "");



        getViewModel().getadlistLiveData().observe(this,data ->{

            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            if (data.getData().getJSONArray("list").size() == 0) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i = 0; i < data.getData().getJSONArray("list").size(); i++) {
                imglist.add(data.getData().getJSONArray("list").getJSONObject(i));
            }
            healthyAdapter.notifyDataSetChanged();
        });


    }
}
