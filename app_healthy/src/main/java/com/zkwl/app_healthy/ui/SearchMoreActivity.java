package com.zkwl.app_healthy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthySearchmoreAdapter;
import com.zkwl.app_healthy.databinding.ActivitySearchmoreBinding;
import com.zkwl.app_healthy.model.HealthyModel;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchMoreActivity extends BaseActivity<HealthyModel, ActivitySearchmoreBinding> {


    private HealthySearchmoreAdapter searchmoreAdapter;

    private List<JSONObject> imglist = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_searchmore;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        StatusBar.setStatusBar(this,true);
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        getViewDataBinding().topbar.setTitle("搜索门店");


        searchmoreAdapter = new HealthySearchmoreAdapter(this, imglist, R.layout.item_healthysearch, new HealthySearchmoreAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {

                //数据是使用Intent返回
                Intent intent = new Intent();
                intent.putExtra("item", item.toString());
                setResult(RESULT_OK, intent);//RESULT_OK为自定义常量，为结果码
                finish();

            }
        });
        getViewDataBinding().recyclerView.setAdapter(searchmoreAdapter);


        getViewDataBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getViewDataBinding().refreshLayout.finishRefresh();
                imglist.clear();
                getViewModel().getshoplist(getViewDataBinding().edSearch.getText().toString(), "", "");
            }
        });

        getViewModel().getshoplist(getViewDataBinding().edSearch.getText().toString(), "", "");
        getViewModel().getshoplistLiveData().observe(this, data -> {

            imglist.clear();
            for (int i = 0; i < data.getData().size(); i++) {
                imglist.add(data.getData().getJSONObject(i));
            }
            searchmoreAdapter.notifyDataSetChanged();

        });


        getViewDataBinding().edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    StatusBar.chageInputState(getContext());
                    getViewModel().getshoplist(getViewDataBinding().edSearch.getText().toString(), "", "");
                    return true;
                }
                return false;
            }
        });

    }
}
