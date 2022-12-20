package com.zkwl.qhzwj.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityPrivacyBinding;
import com.zkwl.qhzwj.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * author : songkai
 * date   : 2020/8/1416:15
 * desc   :
 */

@AndroidEntryPoint
public class PrivacyActivity extends BaseActivity<LoginViewModel, ActivityPrivacyBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().topbar.setTitle("用户隐私");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(v -> finish());


        WebSettings webSettings = getViewDataBinding().webPrivacy.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗


        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
//        mWebView.loadUrl("http://cdn.sdzkworld.com/html/contract.html");
        getViewDataBinding().webPrivacy.loadUrl("http://cdn.sdzkworld.com/doc/property/contract.html");
    }
}
