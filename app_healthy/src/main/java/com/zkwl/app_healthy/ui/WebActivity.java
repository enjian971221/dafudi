package com.zkwl.app_healthy.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.databinding.ActivityWebBinding;
import com.zkwl.app_healthy.model.OrderModel;
import com.zkwl.app_healthy.util.StatusBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WebActivity extends BaseActivity<OrderModel, ActivityWebBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle(getIntent().getStringExtra("title"));
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        String content =getIntent().getStringExtra("content");

        content = getHtmlData(content);
        getViewDataBinding().web.loadDataWithBaseURL(null, content, "text/html;charset=utf-8", null, null);

    }
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
