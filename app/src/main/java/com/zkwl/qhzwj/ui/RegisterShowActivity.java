package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_classroom.model.UserModel;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityRegistershowBinding;
import com.zkwl.qhzwj.util.StatusBar;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class RegisterShowActivity extends BaseActivity<UserModel, ActivityRegistershowBinding> {

    private String mCommunityName;
    private String mCommunityId;



    @Override
    public int getLayoutId() {
        return R.layout.activity_registershow;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this,true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        getViewDataBinding().back.setOnClickListener(view -> finish());
        getViewDataBinding().btRegisterShowNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(mCommunityId)) {
                    ToastUtils.showToast(getContext(),"请选择您所在小区");
                    return;
                }
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra("community_id", mCommunityId);
                startActivity(intent);
                finish();
            }
        });

        getViewDataBinding().tvRegisterShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), RegisterCommunityActivity.class), 888);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == RESULT_OK && data != null && data.getStringExtra("community_id") != null && data.getStringExtra("community_name") != null) {
            mCommunityId = data.getStringExtra("community_id");
            mCommunityName = data.getStringExtra("community_name");
            getViewDataBinding().tvRegisterShowName.setText(mCommunityName);
        }
    }
}
