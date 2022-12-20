package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityRegisterCommunityBinding;
import com.zkwl.qhzwj.LoginViewModel;
import com.zkwl.qhzwj.ui.adapter.RegisterCommunityAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


/**
 * author : songkai
 * date   : 2021/5/149:21
 * desc   :
 */

@AndroidEntryPoint
public class RegisterCommunityActivity extends BaseActivity<LoginViewModel, ActivityRegisterCommunityBinding>   {


    private RegisterCommunityAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<JSONObject> mList =new ArrayList<>();
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().topbar.setTitle("选择小区");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        mLayoutManager = new LinearLayoutManager(this);
        getViewDataBinding().rvRegisterCommunity.setLayoutManager(mLayoutManager);
        mAdapter = new RegisterCommunityAdapter(this,mList, R.layout.item_register_community_header, new RegisterCommunityAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                Intent intent = new Intent();
                intent.putExtra("community_name", item.getString("community_name"));
                intent.putExtra("community_id", item.getString("community_id"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        getViewDataBinding().rvRegisterCommunity.setAdapter(mAdapter);
        getViewDataBinding().slideBarRegisterCommunity.setDefaultColor(Color.parseColor("#88C947"));
        getViewDataBinding().slideBarRegisterCommunity.setChooseColor(Color.parseColor("#333333"));
        getViewDataBinding().slideBarRegisterCommunity.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(boolean isTouch, String letter) {
                getViewDataBinding().tvRegisterCommunityLetter.setText(letter);
                changePosition(letter);
            }
        });

        getViewModel().RegisterCommunityList();

        getViewModel().getRegisterCommunityList().observe(this,data->{
            mList.addAll(data.getData());
            mAdapter.notifyDataSetChanged();
        });
    }


    private void changePosition(String letter) {
        for (int i = 0; i < mList.size(); i++) {
            if (letter.equalsIgnoreCase(mList.get(i).getString("title"))) {
                Logger.d("点击--->" + letter + "----" + i);
                mLayoutManager.scrollToPositionWithOffset(i, 0);
                return;
            }
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_register_community;
    }


}
