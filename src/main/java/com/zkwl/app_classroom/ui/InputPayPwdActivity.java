package com.zkwl.app_classroom.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ActivityInputpaypwdBinding;
import com.zkwl.app_classroom.model.UserModel;
import com.zkwl.app_classroom.util.StatusBar;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * 设置密码
 */

@AndroidEntryPoint
public class InputPayPwdActivity extends BaseActivity<UserModel, ActivityInputpaypwdBinding> {

    private String tump1pass;
    private String tump2pass;
    private String tumpcode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_inputpaypwd;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this,true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        getViewDataBinding().title.setTitle("设置支付密码");
        getViewDataBinding().title.addLeftBackImageButton().setOnClickListener(v -> finish());

        getViewModel().getadlistliveDatasend_sms().observe(this,data ->{
            getViewDataBinding().tvInputPayPwdTitleOne.setText("请输入短信验证码");
            getViewDataBinding().tvInputPayPwdTitleTwo.setText("已发送至您的手机，用于验证");
        });

        getViewModel().getadliveDatasetPayPwd().observe(this,data ->{
            finish();
        });

        getViewDataBinding().inputPaySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tump1pass)){
                    if (getViewDataBinding().passLayout.getPassString().length() < 6) {
                        ToastUtils.showToast(getContext(), "请输入密码");
                        return;
                    }
                    tump1pass =getViewDataBinding().passLayout.getPassString();

                    getViewDataBinding().tvInputPayPwdTitleOne.setText("请再次输入密码");
                    getViewDataBinding().tvInputPayPwdTitleTwo.setText("请输入第二次支付密码，用于验证");

                    getViewDataBinding().passLayout.removeAllPwd();
                    return;
                }
                if (TextUtils.isEmpty(tump2pass)){
                    if (getViewDataBinding().passLayout.getPassString().length() < 6) {
                        ToastUtils.showToast(getContext(), "请再次输入密码");
                        return;
                    }
                    tump2pass =getViewDataBinding().passLayout.getPassString();
                    if (!tump1pass.equals(tump2pass)){
                        ToastUtils.showToast(getContext(),"两次密码不正确，请重新输入!");
                        tump2pass="";
                        tump1pass="";
                        return;
                    }
                    getViewModel().sendVerificationCode("4");
                    return;
                }
                if (TextUtils.isEmpty(tumpcode)){
                    if (getViewDataBinding().passLayout.getPassString().length() < 6) {
                        ToastUtils.showToast(getContext(), "请输入验证码");
                        return;
                    }
                    tumpcode =getViewDataBinding().passLayout.getPassString();
                    if (TextUtils.isEmpty(tumpcode)) {
                        ToastUtils.showToast(getContext(), "请输入验证码");
                        return;
                    }

                    getViewModel().setPayPwd(tumpcode,tump1pass);

                }
            }
        });
    }
}
